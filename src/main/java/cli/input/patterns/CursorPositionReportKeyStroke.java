package cli.input.patterns;

import cli.TerminalPosition;
import cli.input.KeyKind;
import cli.input.KeyStroke;

public class CursorPositionReportKeyStroke extends KeyStroke {
    private final TerminalPosition position;

    /**
     * Constructs a ScreenInfoAction based on a location on the screen
     * @param position the TerminalPosition reported from terminal
     */
    public CursorPositionReportKeyStroke(TerminalPosition position) {
        super(KeyKind.INTERNAL_CURSOR_LOCATION, null, false, false, false);
        this.position = position;
    }

    /**
     * The location of the mouse cursor when this event was generated.
     * @return Location of the mouse cursor
     */
    public TerminalPosition getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "ScreenInfoAction{position=" + position + '}';
    }
}
