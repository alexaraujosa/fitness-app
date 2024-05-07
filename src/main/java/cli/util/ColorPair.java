package cli.util;

import java.util.Objects;

public class ColorPair {
    public static final ColorPair DEFAULT = new ColorPair(Color.DEFAULT, Color.DEFAULT);
    public static final ColorPair HIGHLIGHT = new ColorPair(Color.BLACK, Color.WHITE);

    private Color fg;
    private Color bg;

    public ColorPair() {
        this.fg = new Color();
        this.bg = new Color();
    }

    public ColorPair(Color fg, Color bg) {
        this.fg = new Color(fg);
        this.bg = new Color(bg);
    }

    public ColorPair(ColorPair that) {
        this.fg = that.getFg();
        this.bg = that.getBg();
    }

    public Color getFg() {
        return fg.clone();
    }

    public void setFg(Color fg) {
        this.fg = fg.clone();
    }

    public Color getBg() {
        return bg.clone();
    }

    public void setBg(Color bg) {
        this.bg = bg.clone();
    }

    public String toANSIPart() {
        return String.format("38;2;%s48;2;%s", this.fg.toANSIPart(), this.bg.toANSIPart());
    }

    @Override
    public String toString() {
        return "ColorPair{" +
                "fg=" + fg +
                ", bg=" + bg +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorPair colorPair = (ColorPair) o;

        return Objects.equals(getFg(), colorPair.getFg()) && Objects.equals(getBg(), colorPair.getBg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFg(), getBg());
    }

    protected Object clone() {
        return new ColorPair(this);
    }
}
