package cli.components;

import cli.Terminal;
import cli.util.ColorPair;

import java.io.Console;

public abstract class CLIComponent {
    /** The position in the X axis for this Component. */
    protected int posX;

    /** The position in the Y axis for this Component. */
    protected int posY;

    /** Whether this Component is selected. */
    protected boolean selected;

    /** The {@link Terminal} instance for the JVM. */
    protected Terminal terminal;

    /** The current tick step for this Component. */
    protected int tick;

    public CLIComponent() {
        this.posX = 0;
        this.posY = 0;
        this.selected = false;
        this.terminal = Terminal.getTerminal();
        this.tick = 0;
    }

    public CLIComponent(int posX, int posY, boolean selected) {
        this.posX = posX;
        this.posY = posY;
        this.selected = selected;
        this.terminal = Terminal.getTerminal();
        this.tick = 0;
    }


    /** Gets the position in the X axis for this Component. */
    public int getPosX() {
        return this.posX;
    }

    /** Sets the position in the Y axis for this Component. */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /** Gets the position in the Y axis for this Component. */
    public int getPosY() {
        return this.posY;
    }

    /** Sets the position in the Y axis for this Component. */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /** Marks this Component as selected. */
    public void select() {
        this.selected = true;
    }

    /** Marks this Component as deselected. */
    public void deselect() {
        this.selected = false;
    }

    /** Sets the selection status for this Component. */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /** Updates this Component one tick forward. */
    public void tick() {
        this.tick++;
    }

    /** Draws this Component on the console screen. */
    public abstract void draw();

    //region ======= UTILITIES =======
    protected void gotoCurrentPos(boolean flush) {
        this.terminal.printf("\033[%d;%dH", this.posY, this.posX);
        if (flush) this.terminal.flush();
    }
    protected void gotoPos(int posX, int posY, boolean flush) {
        this.terminal.printf("\033[%d;%dH", posY, posX);
        if (flush) this.terminal.flush();
    }

    protected void colorize(ColorPair cp) {
        this.terminal.printf("\033[%s", cp.toANSIPart());
    }

    protected void resetEffects() {
        this.terminal.printf("\033[0m");
    }
    //endregion
}
