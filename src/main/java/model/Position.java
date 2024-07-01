package model;

import lombok.ToString;
import org.tinylog.Logger;

/**
 * Represents a 2D position.
 */
public record Position(int row, int column) {
    /**
     * {@return the position whose vertical and horizontal distances from this
     * position are equal to the coordinate changes of the direction given}
     *
     * @param direction a direction that specifies a change in the coordinates.
     *
     */
    public Position move(Direction direction){
        Logger.info("Moving to " + direction);
        return new Position(row + direction.getRowChange(),column + direction.getColChange());
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", row, column);
    }
}
