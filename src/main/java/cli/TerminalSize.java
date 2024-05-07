package cli;

public class TerminalSize {
    public static final TerminalSize ZERO = new TerminalSize(0, 0);
    public static final TerminalSize UNIT = new TerminalSize(1, 1);

    private final int columns;
    private final int rows;

    /**
     * Creates a new terminal size representation with a given width (columns) and height (rows)
     *
     * @param columns Width, in number of columns
     * @param rows    Height, in number of columns
     */
    public TerminalSize(int columns, int rows) {
        if (rows < 0 || columns < 0) {
            throw new IllegalArgumentException("TerminalSize dimensions cannot be less than 0: [columns: " + columns + "rows: " + rows + "]");
        }

        this.columns = columns;
        this.rows = rows;
    }

    /**
     * @return Returns the width of this size representation, in number of columns
     */
    public int getColumns() {
        return this.columns;
    }

    /**
     * @return Returns the width of this size representation, in number of columns
     */
    public int getRows() {
        return this.rows;
    }

    @Override
    public String toString() {
        return "{" + columns + "x" + rows + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (!(obj instanceof TerminalSize other)) return false;

        return columns == other.columns && rows == other.rows;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.columns;
        hash = 53 * hash + this.rows;
        return hash;
    }
}
