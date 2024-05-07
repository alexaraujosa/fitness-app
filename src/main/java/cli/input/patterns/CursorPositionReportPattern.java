package cli.input.patterns;

import cli.TerminalPosition;
import cli.input.KeyStroke;

import java.util.List;

public class CursorPositionReportPattern extends EscapeSequencePattern {
    public CursorPositionReportPattern() {
        altTables = false; // stdMap and finMap don't matter here.
    }

    public static CursorPositionReportKeyStroke tryToAdopt(KeyStroke ks) {
        if(ks == null) {
            return null;
        }

        switch (ks.getKeyKind()) {
            case INTERNAL_CURSOR_LOCATION: return (CursorPositionReportKeyStroke)ks;
            case F3: // Reconstruct position from POSIX F3's modifiers.
                if (ks instanceof KeyStroke.RealF3) { return null; }
                int col = 1 + (ks.isAltDown()  ? ALT  : 0)
                        + (ks.isCtrlDown() ? CTRL : 0)
                        + (ks.isShiftDown()? SHIFT: 0);
                TerminalPosition pos = new TerminalPosition(col,1);
                return new CursorPositionReportKeyStroke(pos);
            default:  return null;
        }
    }

    protected KeyStroke getKeyStrokeRaw(char first, int num1, int num2, char last, boolean bEsc) {
        if (first != '[' || last != 'R' || num1 == 0 || num2 == 0 || bEsc) {
            return null; // Not a valid candidate
        }

        if (num1 == 1 && num2 <= 8) {
            return null; // F3 with modifiers
        }

        TerminalPosition pos = new TerminalPosition(num2, num1);
        return new CursorPositionReportKeyStroke(pos);
    }

    @Override
    public Match match(List<Character> seq) {
        return null;
    }
}
