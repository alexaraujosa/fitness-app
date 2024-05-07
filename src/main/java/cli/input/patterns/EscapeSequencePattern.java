package cli.input.patterns;

import cli.input.KeyKind;
import cli.input.KeyPattern;
import cli.input.KeyStroke;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class matches two similar patterns of Escape sequences, that many terminals produce for special keys.
 *
 * These sequences all start with Escape, followed by either an open bracket or a capital letter O (these two are
 * treated as equivalent).
 *
 * Then follows a list of zero or up to two decimals separated by a semicolon, and a non-digit last character.
 *
 * If the last character is a tilde (~) then the first number defines the key (through stdMap), otherwise the last
 * character itself defines the key (through finMap).
 *
 * The second number, if provided by the terminal, specifies the modifier state (shift,alt,ctrl).
 * The value is 1 + sum(modifiers), where shift is 1, alt is 2 and ctrl is 4.
 *
 * Examples: (on a gnome terminal)
 * ArrowUp is "Esc [ A"; Alt-ArrowUp is "Esc [ 1 ; 3 A" both are handled by finMap mapping 'A' to ArrowUp
 *
 * F6 is "Esc [ 1 7 ~"; Ctrl-Shift-F6 is "Esc [ 1 7 ; 6 R" both are handled by stdMap mapping 17 to F6
 */

public class EscapeSequencePattern implements KeyPattern {
    private enum State {
        START, INTRO, OCT1, OCT2, DONE
    }
    public static final char ESC_CODE = 0x1b;

    // bit-values for modifier keys: only used internally
    public static final int SHIFT = 1, ALT = 2, CTRL = 4;

    /**
     *  Map of recognized "standard pattern" sequences.
     *    Ex: 24 -> F12 : "Esc [ **24** ~"
     */
    protected final Map<Integer, KeyKind> stdMap = new HashMap<>();
    
    /**
     *  Map of recognized "finish pattern" sequences:<br>
     *    Ex: 'A' -> ArrowUp : "Esc [ **A**"
     */
    protected final Map<Character, KeyKind> finMap = new HashMap<>();

    /**
     *  A flag to control, whether an Escape prefix for an Escape sequence is to be treated as Alt-pressed, as some
     *  terminals report sequences in a weird way.
     *
     *  If there is a need to separate Escape sequences and plain Arrow keys, a profile should be used that replaces
     *  this class by a subclass that sets this flag to false.
     */
    protected boolean altTables = true;

    public EscapeSequencePattern() {
        finMap.put('A', KeyKind.ARROW_UP);
        finMap.put('B', KeyKind.ARROW_DOWN);
        finMap.put('C', KeyKind.ARROW_RIGHT);
        finMap.put('D', KeyKind.ARROW_LEFT);
        finMap.put('E', KeyKind.KEY_TYPE); // gnome-terminal center key on numpad
        finMap.put('F', KeyKind.END);
        finMap.put('H', KeyKind.HOME);
        finMap.put('P', KeyKind.F1);
        finMap.put('Q', KeyKind.F2);
        finMap.put('R', KeyKind.F3);
        finMap.put('S', KeyKind.F4);

        stdMap.put(1,  KeyKind.HOME);
        stdMap.put(2,  KeyKind.INSERT);
        stdMap.put(3,  KeyKind.DELETE);
        stdMap.put(4,  KeyKind.END);
        stdMap.put(5,  KeyKind.PAGE_UP);
        stdMap.put(6,  KeyKind.PAGE_DOWN);
        stdMap.put(11, KeyKind.F1);
        stdMap.put(12, KeyKind.F2);
        stdMap.put(13, KeyKind.F3);
        stdMap.put(14, KeyKind.F4);
        stdMap.put(15, KeyKind.F5);
        stdMap.put(16, KeyKind.F5);
        stdMap.put(17, KeyKind.F6);
        stdMap.put(18, KeyKind.F7);
        stdMap.put(19, KeyKind.F8);
        stdMap.put(20, KeyKind.F9);
        stdMap.put(21, KeyKind.F10);
        stdMap.put(23, KeyKind.F11);
        stdMap.put(24, KeyKind.F12);
    }

    @Override
    public Match match(List<Character> cur) {
        State state = State.START;
        int oct1 = 0, oct2 = 0;
        char first = '\0', tail = '\0';
        boolean bEsc = false;

        for (char ch : cur) {
            switch (state) {
                case START: {
                    // If the sequence doesn't start with an Escape, it is not a valid escape sequence.
                    if (ch != ESC_CODE) {
                        return null;
                    }
                    state = State.INTRO;
                    continue;
                }
                case INTRO: {
                    // Recognize a second Escape to mean "Alt is pressed".
                    if (altTables && ch == ESC_CODE && !bEsc) {
                        bEsc = true;
                        continue;
                    }

                    // Key sequences supported by this class must start either with Esc-[ or Esc-O
                    if (ch != '[' && ch != 'O') {
                        return null;
                    }

                    first = ch;
                    state = State.OCT1;
                    continue;
                }
                case OCT1: {
                    if (ch == ';') { // If it's an octet separator, skip to second octet.
                        state = State.OCT2;
                    } else if (Character.isDigit(ch)) { // If it's a digit, convert it to its sequential value.
                        oct1 = oct1 * 10 + Character.digit(ch, 10);
                    } else { // Else, skip to the tail octet.
                        tail = ch;
                        state = State.DONE;
                    }
                    continue;
                }
                case OCT2: {
                    if (Character.isDigit(ch)) { // If it's a digit, convert it to its sequential value.
                        oct2 = oct2 * 10 + Character.digit(ch, 10);
                    } else { // Else, skip to the tail octet.
                        tail = ch;
                        state = State.DONE;
                    }
                    continue;
                }
                case DONE: // Sequence recognized, do not read any more characters.
                    return null;
            }
        }

        if (state == State.DONE) {
            KeyStroke ks = getKeyStrokeRaw(first, oct1, oct2, tail, bEsc);
            return ks != null ? new Match( ks ) : null;
        } else {
            return Match.INCOMPLETE;
        }
    }

    /**
     * combines a KeyKind and the sequence modifiers into a KeyStroke.
     *
     * @param key The KeyKind as determined by parsing the sequence.
     *   It will be null if the pattern was an unidentified sequence candidate.
     * @param modifiers The bitmask of the modifier keys pressed along with the key.
     * @return either null (to report mismatch), or a valid KeyStroke.
     */
    protected KeyStroke getKeyStroke(KeyKind key, int modifiers) {
        boolean bShift = false, bCtrl = false, bAlt = false;

        if (key == null) return null;
        if (modifiers >= 0) {
            bShift = (modifiers & SHIFT) != 0;
            bAlt   = (modifiers & ALT)   != 0;
            bCtrl  = (modifiers & CTRL)  != 0;
        } else if (modifiers == -1 && key == KeyKind.F3) {
            return new KeyStroke.RealF3();
        }

        return new KeyStroke(key, null, bCtrl, bAlt, bShift);
    }

    protected KeyStroke getKeyStrokeRaw(char first, int oct1, int oct2, char tail, boolean bEsc) {
        KeyKind kind;
        boolean bRealF3 = false;

        if (tail == '~' && stdMap.containsKey(oct1)) {
            kind = stdMap.get(oct1);
        } else if (finMap.containsKey(tail)) {
            kind = finMap.get(tail);
            if (first == 'O') {
                // ^[OR is a "real" F3 Key, ^[[1;1R may be F3 or a CursorLocation report.
                if (tail == 'R') { bRealF3 = true; }
            }
        } else {
            // Tf is this key?
            kind = null;
        }

        int modifiers = oct2 - 1;
        if (bEsc) {
            if (modifiers >= 0) modifiers |= ALT;
            else modifiers = ALT;
        }

        if (bRealF3) modifiers = -1;

        return getKeyStroke(kind, modifiers);
    }
}
