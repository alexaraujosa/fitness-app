package cli;

public class TerminalPosition implements Comparable<TerminalPosition> {
    public static final TerminalPosition TOP_LEFT_CORNER = new TerminalPosition(0, 0);
    public static final TerminalPosition MARGIN_TOP_1x1 = new TerminalPosition(1, 1);

    private final int column;
    private final int row;

    /**
     * Creates a new TerminalPosition object, which represents a location on the screen. There is no check to verify
     * that the position you specified is within the size of the current terminal and you can specify negative positions
     * as well.
     *
     * @param column Column of the location, or the "x" coordinate, zero indexed (the first column is 0)
     * @param row    Row of the location, or the "y" coordinate, zero indexed (the first row is 0)
     */
    public TerminalPosition(int column, int row) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the index of the column this position is representing, zero indexed (the first column has index 0).
     * @return Index of the column this position has
     */
    public int getColumn() {
        return column;
    }

    /**
     * Returns the index of the row this position is representing, zero indexed (the first row has index 0)
     * @return Index of the row this position has
     */
    public int getRow() {
        return row;
    }

    /**
     * Creates a new TerminalPosition object representing a position with the same column index as the current position
     * but with a supplied row index.
     *
     * @param row Index of the row for the new position
     * @return A TerminalPosition object with the same column as this but with a specified row index
     */
    public TerminalPosition withRow(int row) {
        if(row == 0 && this.column == 0)  return TOP_LEFT_CORNER;

        return new TerminalPosition(this.column, row);
    }

    /**
     * Creates a new TerminalPosition object representing a position with the same row index as the current position but
     * with a supplied column index.
     *
     * @param column Index of the column for the new position
     * @return A TerminalPosition object with the same row as this but with a specified column index
     */
    public TerminalPosition withColumn(int column) {
        if(column == 0 && this.row == 0) return TOP_LEFT_CORNER;

        return new TerminalPosition(column, this.row);
    }

    /**
     * Creates a new TerminalPosition object representing a position on the same row, but with a column offset by a
     * delta value. Calling this method with delta 0 will return a clone of the callee, calling it with a positive delta
     * will return a terminal position <i>delta</i> number of columns to the right and for negative numbers the same to
     * the left.
     *
     * @param delta The column offset to offset the current position with.
     * @return New terminal position based off this one but with an applied offset.
     */
    public TerminalPosition addRelativeColumnDelta(int delta) {
        if(delta == 0) {
            return this.clone();
        }
        return withColumn(column + delta);
    }

    /**
     * Creates a new TerminalPosition object representing a position on the same column, but with a row offset by a
     * delta value. Calling this method with delta 0 will return a clone of the callee, calling it with a positive delta
     * will return a terminal position <i>delta</i> number of rows downwards and for negative numbers the same to
     * upwards.
     *
     * @param delta The row offset to offset the current position with.
     * @return New terminal position based off this one but with an applied offset.
     */
    public TerminalPosition addRelativeRowDelta(int delta) {
        if(delta == 0) {
            return this.clone();
        }
        return withRow(row + delta);
    }

    /**
     * Creates a new TerminalPosition object that is 'translated' by an amount of rows and columns specified by the two
     * parameters.
     *
     * @param deltaColumn The number of columns to translate the current position in the new TerminalPosition.
     * @param deltaRow The number rows to translate the current position in the new TerminalPosition.
     * @return New TerminalPosition that is the result of the original position translated.
     */
    public TerminalPosition addRelativeDelta(int deltaColumn, int deltaRow) {
        return addRelativeRowDelta(deltaRow).addRelativeColumnDelta(deltaColumn);
    }

    public TerminalPosition plus(TerminalPosition position) {
        return addRelativeDelta(position.column, position.row);
    }

    public TerminalPosition minus(TerminalPosition position) {
        return addRelativeDelta(-position.getColumn(), -position.getRow());
    }

    @Override
    public int compareTo(TerminalPosition o) {
        if(row < o.row) {
            return -1;
        }
        else if(row == o.row) {
            if(column < o.column) {
                return -1;
            }
            else if(column == o.column) {
                return 0;
            }
        }
        return 1;
    }

    @Override
    public String toString() {
        return "[" + column + ":" + row + "]";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.row;
        hash = 23 * hash + this.column;
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
        final TerminalPosition other = (TerminalPosition) obj;
        return this.row == other.row && this.column == other.column;
    }

    @Override
    protected TerminalPosition clone() {
        return new TerminalPosition(this.column, this.row);
    }
}
