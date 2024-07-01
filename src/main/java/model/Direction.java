package model;

import org.tinylog.Logger;
/**
 * Represents the four main directions.
 */
public enum Direction {

    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    private final int rowChange;
    private final int colChange;

    Direction(int rowChange, int colChange) {
        Logger.info("Creating Direction");
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    /**
     * {@return the change in the row coordinate when moving to the direction}
     */
    public int getRowChange() {
        Logger.info("getRowChange");
        return rowChange;
    }

    /**
     * {@return the change in the column coordinate when moving to the
     * direction}
     */
    public int getColChange() {
        Logger.info("getColChange");
        return colChange;
    }

    /**
     * {@return the direction that corresponds to the coordinate changes
     * specified}
     *
     * @param rowChange the change in the row coordinate
     * @param colChange the change in the column coordinate
     */
    public static Direction of(int rowChange, int colChange) {
        for (var direction : values()) {
            if (direction.rowChange == rowChange && direction.colChange == colChange) {
                Logger.info("Direction found");
                return direction;
            }
        }
        Logger.warn("Direction not found");
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("rowChange=").append(rowChange);
        sb.append(" colChange=").append(colChange);
        sb.append(" | ");
        return sb.toString();
    }
}