package cli;

import cli.components.TextInput;
import cli.input.KeyKind;
import cli.input.KeyStroke;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Scanner;

public class CLI {
    public static void main(String[] args) throws IOException {
        // Scanner scanner = new Scanner(System.in);

        Terminal term = Terminal.getTerminal();

        System.out.println("\n\n\n\n");

        try {
            term.setup();
            term.setCursorVisible(false);
            term.echoMode(true);
            TerminalSize size = term.getTerminalSize();
            term.echoMode(false);

            System.out.println("Size: " + size);
        } catch (IOException err) {
            System.out.println("Unable to setup terminal: " + err);
        }
        System.out.println("\n\n\n\n");

//        TextInput test = new TextInput(5, 5, true, "abc", "Lorem Ipsum", 0);
//        test.draw();

        System.out.println("Hello world!");
//        System.out.print("\033[" + 2 + ";" + 2 + "H");
//        System.out.print("\u001B[31m");
//        System.out.print("Moved?");
//        System.out.print("\u001B[0m");

//        scanner.next();

        do {
            if (Keyboard.isKeyPressed(KeyEvent.VK_W)) System.out.println("W is pressed!");
        } while (!Keyboard.isKeyPressed(KeyEvent.VK_ESCAPE));

        KeyStroke keyStroke = term.readInput();
        /*
           The KeyStroke class has a couple of different methods for getting details on the particular input that was
           read. Notice that some keys, like CTRL and ALT, cannot be individually distinguished as the standard input
           stream doesn't report these as individual keys. Generally special keys are categorized with a special
           KeyType, while regular alphanumeric and symbol keys are all under KeyType.Character. Notice that tab and
           enter are not considered KeyType.Character but special types (KeyType.Tab and KeyType.Enter respectively)
         */
        while(keyStroke.getKeyKind() != KeyKind.ESCAPE) {
            term.printf("KeyStroke: '%s'\n" + keyStroke.toString());
            term.flush();
            keyStroke = term.readInput();
        }
    }
}
