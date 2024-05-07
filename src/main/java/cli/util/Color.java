package cli.util;

import java.util.Objects;

public class Color {
    public static final Color DEFAULT = new Color(true);
    public static final Color WHITE = new Color((byte)0xFF, (byte)0xFF, (byte)0xFF);
    public static final Color BLACK = new Color((byte)0x00, (byte)0x00, (byte)0x00);
    public static final Color RED = new Color((byte)0xFF, (byte)0x00, (byte)0x00);
    public static final Color GREEN = new Color((byte)0x00, (byte)0xFF, (byte)0x00);
    public static final Color BLUE = new Color((byte)0x00, (byte)0x00, (byte)0xFF);


    private boolean _reset;
    private byte red;
    private byte green;
    private byte blue;

    private Color(boolean reset) {
        this._reset = true;
        this.red = 0;
        this.green = 0;
        this.blue = 0;
    }

    public Color() {
        this.red = 0;
        this.green = 0;
        this.blue = 0;
    }
    public Color(byte red, byte green, byte blue) {
        this._reset = false;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Color(Color that) {
        this._reset = false;
        this.red = that.getRed();
        this.green = that.getGreen();
        this.blue = that.getBlue();
    }

    public byte getRed() {
        return red;
    }

    public void setRed(byte red) {
        this.red = red;
    }

    public byte getGreen() {
        return green;
    }

    public void setGreen(byte green) {
        this.green = green;
    }

    public byte getBlue() {
        return blue;
    }

    public void setBlue(byte blue) {
        this.blue = blue;
    }

    public String toANSIPart() {
        return String.format("%d;%d;%d;m", this.red, this.green, this.blue);
    }

    @Override
    public String toString() {
        return "Color{" +
                ", red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Color color = (Color) o;
        return red == color.red && green == color.green && blue == color.blue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(red, green, blue);
    }

    protected Color clone() {
        return new Color(this.red, this.green, this.blue);
    }
}
