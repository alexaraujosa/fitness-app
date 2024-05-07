package cli.components;

import cli.util.ColorPair;

public class TextInput extends CLIComponent {
    private int posX;
    private int posY;
    private boolean selected;
    private String placeholder;
    private StringBuilder text;
    private int cursor;
    private boolean cursorVisible;


    private static final int MAX_TICKS = 60;
    private static final ColorPair CURSOR_HIGHLIGHT = ColorPair.HIGHLIGHT;

    public TextInput() {
        super();
        this.placeholder = "";
        this.text = new StringBuilder();
        this.cursor = 0;
        this.cursorVisible = false;
    }

    public TextInput(int posX, int posY, boolean selected, String placeholder, String text, int cursor) {
        super(posX, posY, selected);
        this.placeholder = placeholder;
        this.text = new StringBuilder(text);
        this.cursor = cursor;
        this.cursorVisible = false;
    }

    @Override
    public void deselect() {
        super.deselect();
        this.tick = 0;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tick == MAX_TICKS) {
            this.cursorVisible = !this.cursorVisible;
            this.tick = 0;
        }
    }

    @Override
    public void draw() {
        this.gotoCurrentPos(true);
        this.terminal.printf(this.text.toString());

        if (this.cursorVisible) {
            this.gotoPos(this.posX + this.cursor, this.posY, false);
            this.colorize(CURSOR_HIGHLIGHT);
            this.terminal.printf("%c", this.text.charAt(this.cursor));
            this.resetEffects();
        }
    }

    public void handleInput(char ch) {
        if (ch == 0x1E) { // Backspace
            this._deleteChar(this.cursor--);
        } else {
            this.text.append(ch);
            this.cursor++;
        }
    }

    public void deleteChar(int pos) {
        if (pos < 0 || pos >= this.text.length()) return;
        this._deleteChar(pos);
    }

    private void _deleteChar(int pos) {
        this.text.deleteCharAt(pos);
    }
}
