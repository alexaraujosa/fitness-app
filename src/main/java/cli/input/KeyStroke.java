package cli.input;

import java.util.Objects;

public class KeyStroke {
    private final KeyKind keyKind;
    private final Character character;
    private final boolean ctrlDown;
    private final boolean altDown;
    private final boolean shiftDown;

    public KeyStroke(KeyKind keyKind) {
        this(keyKind, null, false, false, false);
    }

    public KeyStroke(Character character, boolean ctrlDown, boolean altDown) {
        this(KeyKind.CHARACTER, character, ctrlDown, altDown, false);
    }

    public KeyStroke(KeyKind keyKind, Character character, boolean ctrlDown, boolean altDown, boolean shiftDown) {
        if(keyKind == KeyKind.CHARACTER && character == null) {
            throw new IllegalArgumentException("Cannot construct a KeyStroke with type KeyKind.CHARACTER but no character information");
        }

        // Special sequences, store them as single character.
        switch(keyKind) {
            case BACKSPACE:
                character = '\b';
                break;
            case ENTER:
                character = '\n';
                break;
            case TAB:
                character = '\t';
                break;
            default:
        }

        this.keyKind = keyKind;
        this.character = character;
        this.shiftDown = shiftDown;
        this.ctrlDown = ctrlDown;
        this.altDown = altDown;
    }

    /**
     * An F3 KeyStroke that can be differentiated from a CursorPositionReport.
     */
    public static class RealF3 extends KeyStroke {
        public RealF3() { super(KeyKind.F3, null,false, false, false); }
    }

    /**
     * Kind of key that was pressed on the keyboard. If the value iis of Kind CHARACTER, you need to call getCharacter()
     * to get the value of the key pressed.
     *
     * @return Type of key on the keyboard that was pressed
     */
    public KeyKind getKeyKind() {
        return keyKind;
    }

    /**
     * For keystrokes of ordinary keys (letters, digits, symbols), this method returns the actual character value of the
     * key. For all other key types, it returns null.
     *
     * @return Character value of the key pressed, or null if it was a special key
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * Returns true if ctrl was held down while the key was typed.
     */
    public boolean isCtrlDown() {
        return ctrlDown;
    }

    /**
     * Returns true if alt was held down while the key was typed.
     */
    public boolean isAltDown() {
        return altDown;
    }

    /**
     * Returns true if shift was held down while the key was typed.
     */
    public boolean isShiftDown() {
        return shiftDown;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("KeyStroke{keyKind=").append(this.keyKind);
        if (character != null) {
            char ch = character;
            sb.append(", character='");
            if (ch <= 26) {
                sb.append('^').append((char)(ch+64));
            } else { sb.append(ch); }
            sb.append('\'');
        }
        if (ctrlDown || altDown || shiftDown) {
            String sep=""; sb.append(", modifiers=[");
            if (ctrlDown) {  sb.append(sep).append("ctrl"); sep=","; }
            if (altDown) {   sb.append(sep).append("alt"); sep=","; }
            if (shiftDown) { sb.append(sep).append("shift"); }
            sb.append("]");
        }
        return sb.append('}').toString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (this.keyKind != null ? this.keyKind.hashCode() : 0);
        hash = 41 * hash + (this.character != null ? this.character.hashCode() : 0);
        hash = 41 * hash + (this.ctrlDown ? 1 : 0);
        hash = 41 * hash + (this.altDown ? 1 : 0);
        hash = 41 * hash + (this.shiftDown ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final KeyStroke other = (KeyStroke)obj;
        if (this.keyKind != other.keyKind) {
            return false;
        }

        if (!Objects.equals(this.character, other.character)) {
            return false;
        }

        return this.ctrlDown == other.ctrlDown && this.altDown == other.altDown && this.shiftDown == other.shiftDown;
    }

}
