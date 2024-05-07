package cli;

import cli.input.InputDecoder;
import cli.input.KeyStroke;
import cli.input.patterns.CursorPositionReportKeyStroke;
import cli.input.patterns.CursorPositionReportPattern;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Terminal {
    // Definition Constants
    protected static final byte[] ANSI_HEADER = { 0x1b, '[' };

    // Utility Constants
    private static Terminal curTerm = null;

    // Private Variables
    private List<String> sttyOverride = null;
    private final InputStream terminalInput;
    private final OutputStream terminalOutput;
    private volatile TerminalPosition lastReportedCursorPosition;

    // - Input-related Variables
    private final InputDecoder inputDecoder;
    private final Queue<KeyStroke> keyQueue;
    private final Lock readLock;

    public Terminal() {
        this.terminalInput = System.in;
        this.terminalOutput = System.out;

        this.inputDecoder = new InputDecoder(new InputStreamReader(this.terminalInput, Charset.defaultCharset()));
        this.keyQueue = new LinkedList<>();
        this.readLock = new ReentrantLock();
    }

    public Terminal(InputStream terminalInput, OutputStream terminalOutput) {
        this.terminalInput = terminalInput;
        this.terminalOutput = terminalOutput;

        this.inputDecoder = new InputDecoder(new InputStreamReader(this.terminalInput, Charset.defaultCharset()));
        this.keyQueue = new LinkedList<>();
        this.readLock = new ReentrantLock();
    }

    public static Terminal getTerminal() {
        if (curTerm == null) curTerm = new Terminal();

        return curTerm;
    }

    public void setup() throws IOException {
        canonicalMode(false);
        keyEchoEnabled(false);
        extendedCharacterMode(false);
        interruptMode(false);

//        keyEchoEnabled(false);
//        try {
//            Runtime.getRuntime().exec(new String [] {"sh", "-c", "stty -echo < /dev/tty"}).waitFor();
//        } catch (Exception e) {
//            // Do jack shit
//        }

//        runSTTYCommand("raw");
    }

    /**
     * Outputs a formatted String to the Terminal output.
     * Follows the same syntax as {@link java.io.PrintStream#printf(String, Object...)}.
     */
    public Terminal printf(String format, Object ... args) {
        System.out.printf((format) + "%n", args);
        return this;
    }

    public void flush() {
        try {
            this._flush();
        } catch (IOException ignore) {
            // Ignore the exception
        }
    }

    private void _flush() throws IOException {
        this.terminalOutput.flush();
    }

    //region ============== TERMINAL ACCESSORS ==============

    public void writeANSISequence(byte... tail) throws IOException {
        byte[] completeSequence = new byte[tail.length + 2];
        System.arraycopy(ANSI_HEADER, 0, completeSequence, 0, 2);
        System.arraycopy(tail, 0, completeSequence, 2, tail.length);
        writeToTerminal(completeSequence);
    }


    /**
     * This method will write a list of bytes directly to the output stream of the terminal.
     * @param bytes Bytes to write to the terminal (synchronized)
     * @throws java.io.IOException If there was an underlying I/O error
     */
    @SuppressWarnings("WeakerAccess")
    protected void writeToTerminal(byte... bytes) throws IOException {
        synchronized(terminalOutput) {
            terminalOutput.write(bytes);
        }
    }

    protected TerminalSize getTerminalSize() throws IOException {
        saveCursorPosition();
        setCursorPosition(5000, 5000);
        resetCursorPositionMemory();
        reportPosition();
        _flush();
        restoreCursorPosition();

        TerminalPosition terminalPosition = waitForCursorPositionReport();
        if (terminalPosition == null) {
            terminalPosition = new TerminalPosition(80,24);
        }

        return new TerminalSize(terminalPosition.getColumn(), terminalPosition.getRow());
    }

    //region ======= CURSOR OPERATORS =======
    public void setCursorVisible(boolean visible) throws IOException {
        writeANSISequence(("?25" + (visible ? "h" : "l")).getBytes());
    }

    public void reportPosition() throws IOException {
        writeANSISequence("6n".getBytes());
    }

    public void restoreCursorPosition() throws IOException {
        writeANSISequence("u".getBytes());
    }

    public void saveCursorPosition() throws IOException {
        writeANSISequence("s".getBytes());
    }

    public synchronized TerminalPosition getCursorPosition() throws IOException {
        resetCursorPositionMemory();
        reportPosition();

        // ANSI terminal positions are 1-indexed so top-left corner is 1x1 instead of 0x0, that's why we need to adjust it here
        TerminalPosition terminalPosition = waitForCursorPositionReport();
        if (terminalPosition == null) {
            terminalPosition = TerminalPosition.MARGIN_TOP_1x1;
        }

        return terminalPosition.addRelativeDelta(-1, -1);
    }

    public void setCursorPosition(int x, int y) throws IOException {
        writeANSISequence(((y + 1) + ";" + (x + 1) + "H").getBytes());
    }

    public void setCursorPosition(TerminalPosition position) throws IOException {
        setCursorPosition(position.getColumn(), position.getRow());
    }
    //endregion

    /**
     * Used by the cursor reporting methods to reset any previous position memorized, so we're guaranteed to return the
     * next reported position
     */
    void resetCursorPositionMemory() {
        lastReportedCursorPosition = null;
    }

    /**
     * Waits for up to 5 seconds for a terminal cursor position report to appear in the input stream. If the timeout
     * expires, it will return null. You should have sent the cursor position query already before
     * calling this method.
     * @return Current position of the cursor, or null if the terminal didn't report it in time.
     * @throws IOException If there was an I/O error
     */
    synchronized TerminalPosition waitForCursorPositionReport() throws IOException {
        long startTime = System.currentTimeMillis();
        TerminalPosition cursorPosition = lastReportedCursorPosition;

        while(cursorPosition == null) {
            if(System.currentTimeMillis() - startTime > 100) {
                return null;
            }

            KeyStroke keyStroke = readInput(false, false);
            if(keyStroke != null) {
                keyQueue.add(keyStroke);
            } else {
                try { Thread.sleep(1); } catch(InterruptedException ignored) {}
            }
            cursorPosition = lastReportedCursorPosition;
        }
        return cursorPosition;
    }

    /**
     * Reads a {@link KeyStroke} from the Terminal InputStream.
     */
    public KeyStroke readInput() throws IOException {
        return readInput(true, true);
    }

    /**
     * Reads a {@link KeyStroke} from the Terminal InputStream.
     *
     * @param blocking Whether to lock the process until the read is completed.
     * @param useKeyQueue
     */
    private KeyStroke readInput(boolean blocking, boolean useKeyQueue) throws IOException {
        while(true) {
            if(useKeyQueue) {
                KeyStroke previouslyReadKey = keyQueue.poll();
                if(previouslyReadKey != null) {
                    return previouslyReadKey;
                }
            }

            if(blocking) {
                readLock.lock();
            } else {
                // Don't wait for the lock
                if(!readLock.tryLock()) {
                    return null;
                }
            }

            try {
                KeyStroke key = inputDecoder.getNextCharacter(blocking);
                CursorPositionReportKeyStroke report = CursorPositionReportPattern.tryToAdopt(key);
                if (lastReportedCursorPosition == null && report != null) {
                    lastReportedCursorPosition = report.getPosition();
                } else {
                    return key;
                }
            } finally {
                readLock.unlock();
            }
        }
    }
    //endregion

    //region ============== TERMINAL MODIFIERS ==============
    public void echoMode(boolean enabled) throws IOException {
//        writeANSISequence((enabled ? "?25h" : "?25l").getBytes());
    }

    protected void restoreSane() throws IOException {
        runSTTYCommand("sane");
    }

    protected void canonicalMode(boolean enabled) throws IOException {
        runSTTYCommand(enabled ? "icanon" : "-icanon");
        if(!enabled) {
            runSTTYCommand("min", "1");
        }
    }

    protected void extendedCharacterMode(boolean enabled) throws IOException {
        runSTTYCommand(enabled ? "iexten" : "-iexten");
    }

    protected void interruptMode(boolean enabled) throws IOException {
        runSTTYCommand(enabled ? "isig" : "-isig");
    }

    protected void keyEchoEnabled(boolean enabled) throws IOException {
        runSTTYCommand(enabled ? "echo" : "-echo");
    }

    protected void keyStrokeSignalsEnabled(boolean enabled) throws IOException {
        if(enabled) {
            runSTTYCommand("intr", "^C");
        }
        else {
            runSTTYCommand("intr", "undef");
        }
    }
    //endregion

    //region ============== NATIVE ACCESS PROVIDERS ==============
    protected void runSTTYCommand(String... parameters) throws IOException {
        List<String> commandLine = new ArrayList<>();
        if (sttyOverride != null) {
            commandLine.addAll(sttyOverride);
        } else {
            commandLine.add("/usr/bin/env");
            commandLine.add("stty");
        }

        commandLine.addAll(Arrays.asList(parameters));
//        exec(commandLine.toArray(new String[0]));
        exec(commandLine);
    }

    protected String exec(List<String> cmd) throws IOException {
        try {
//            List<String> fullCmd = new ArrayList<>(Arrays.asList(new String[]{"sh", "-c"}));
//            fullCmd.addAll(cmd);
//
//            System.out.println("EXEC ARGS: " + fullCmd);
//
//            Runtime.getRuntime().exec(fullCmd.toArray(new String[0])).waitFor();

            List<String> _cmd = new ArrayList<>(cmd);
            _cmd.add(" < /dev/tty");

            String[] fullCmd = {"sh", "-c", String.join(" ", _cmd)};

            System.out.println("EXEC ARGS: " + Arrays.toString(fullCmd));

            Runtime.getRuntime().exec(fullCmd).waitFor();
            return "";
        } catch (Exception e) {
            throw new IOException("Exec failed: " + e.toString());
        }
    }

//    protected String exec(String... cmd) throws IOException {
//        System.out.println("EXEC ARGS: " + Arrays.toString(cmd));
//        ProcessBuilder pb = new ProcessBuilder(cmd);
//
//        Process process = pb.start();
//        pb.redirectInput(ProcessBuilder.Redirect.from(new File("/dev/tty")));
//
//        // Capture executed process' output
//        ByteArrayOutputStream stdoutBuffer = new ByteArrayOutputStream();
//        InputStream stdout = process.getInputStream();
//
//        // Read process output byte by byte
//        int readByte = stdout.read();
//        while(readByte >= 0) {
//            stdoutBuffer.write(readByte);
//            readByte = stdout.read();
//        }
//
//        // Pipe process stdout through a buffered reader.
//        ByteArrayInputStream stdoutBufferInputStream = new ByteArrayInputStream(stdoutBuffer.toByteArray());
//        BufferedReader reader = new BufferedReader(new InputStreamReader(stdoutBufferInputStream));
//        StringBuilder builder = new StringBuilder();
//
//        // Read input buffer line by line.
//        String line;
//        while((line = reader.readLine()) != null) {
//            builder.append(line);
//        }
//
//        reader.close();
//        return builder.toString();
//    }
    //endregion
}
