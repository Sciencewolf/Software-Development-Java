package model;

import javafx.beans.property.*;
import puzzle.TwoPhaseMoveState;

import java.util.*;
import org.tinylog.Logger;

/**
 * This class implements puzzle.TwoPhaseMoveState interface.
 * Stores state of the game.
 */
public class PuzzleState implements TwoPhaseMoveState<Position> {
    /**
     * Max row size on a board.
     */
    private static final int BOARD_ROW_SIZE = 5;
    /**
     * Max column on a board.
     */
    private static final int BOARD_COLUMN_SIZE = 3;
    /**
     * Max pieces on a board.
     */
    private static final int PIECES_COUNT = 6;
    private final ReadOnlyBooleanWrapper isSolved;
    private final ReadOnlyObjectWrapper<Position>[] piecesPosition;

    /**
     * enum for set index of green and red pieces. [0-5]
     */
    public enum PIECES {
        G_TOP_LEFT(0),
        G_TOP_MID(1),
        G_TOP_RIGHT(2),
        R_BOTTOM_LEFT(3),
        R_BOTTOM_MID(4),
        R_BOTTOM_RIGHT(5);

        private int value;

        PIECES(int value) {
            this.value = value;
        }
    }

    /**
     * Uses multiple param constructor to fill positions with green and red pieces when zero-param constructor called.
     * Start positions is full first(top) green row and full last(bottom) red row.
     * @see <a href="https://github.com/INBPM0420L/homework-project-2024-Sciencewolf/blob/master/abra.png" target="_blank">Image</a>
     */
    public PuzzleState() {
        this(
                new Position(0, 0),
                new Position(0, 1),
                new Position(0, 2),
                new Position(4, 0),
                new Position(4, 1),
                new Position(4, 2)
        );
        Logger.info("Puzzle state initialised");
    }

    /**
     * Multiple param constructor.
     * @param positions array of Position's.
     */
    public PuzzleState(Position... positions) {
        checkPositions(positions);
        this.piecesPosition = new ReadOnlyObjectWrapper[PIECES_COUNT];
        for(var index = 0; index < positions.length; index++) {
            this.piecesPosition[index] = new ReadOnlyObjectWrapper<>(positions[index]);
        }
        isSolved = new ReadOnlyBooleanWrapper();
        Logger.info("Puzzle state initialised and board is filled");
    }

    /**
     * Method for getting Position object by index.
     * @param index index of piece [0-5].
     * @return Position object.
     */
    public Position getPosition(int index) {
        Logger.info("Getting position for index " + index);
        return piecesPosition[index].get();
    }

    /**
     * Method for getting ReadOnlyObjectProperty of Position object by giving index.
     * @param index index of piece [0-5].
     * @return ReadOnlyObjectProperty from Position object.
     */
    public ReadOnlyObjectProperty<Position> getPositionProperty(int index) {
        Logger.info("Getting positionProperty for index " + index);
        return piecesPosition[index].getReadOnlyProperty();
    }

    /**
     * Method for getting ReadOnlyBooleanProperty of isSolved field.
     * @return ReadOnlyBooleanProperty for isSolved object.
     */
    public ReadOnlyBooleanProperty getIsSolvedProperty() {
        Logger.info("Getting isSolvedProperty");
        return isSolved.getReadOnlyProperty();
    }

    /**
     * Method for returning piece given by index.
     * @param index searched piece by index.
     * @return given piece by index.
     */
    public PIECES getPieceByIndex(int index) {
        Logger.info("Getting pieceByIndex for index " + index);
        switch (index) {
            case 0: return PIECES.G_TOP_LEFT;
            case 1: return PIECES.G_TOP_MID;
            case 2: return PIECES.G_TOP_RIGHT;
            case 3: return PIECES.R_BOTTOM_LEFT;
            case 4: return PIECES.R_BOTTOM_MID;
            case 5: return PIECES.R_BOTTOM_RIGHT;
        }

        return PIECES.R_BOTTOM_LEFT;
    }

    /**
     * Method for returning index of given piece.
     * @param row which row.
     * @param column which column.
     * @return index of piece or -1 if not found.
     */
    public int getIndexOfPiece(int row, int column) {
        Logger.info("Getting indexOfPiece for row " + row + " and column " + column);
        var position = new Position(row, column);
        for (var index = 0; index < PIECES_COUNT; index++) {
            if(piecesPosition[index].get().equals(position)) {
                Logger.info("Found piece at index " + index);
                return index;
            }
        }
        Logger.info("No piece found for row " + row + " and column " + column);
        return -1;
    }

    /**
     * Method for check if target position is achieved (green's at bottom, red's at top).
     * @return true if game state is at target position's else false.
     */
    public boolean isTargetPositionAchieved() {
        Logger.info("Checking if target position is achieved");
        return (
                getPosition(PIECES.R_BOTTOM_LEFT.value).row() == 0
                && getPosition(PIECES.R_BOTTOM_MID.value).row() == 0
                && getPosition(PIECES.R_BOTTOM_RIGHT.value).row() == 0
                && getPosition(PIECES.G_TOP_LEFT.value).row() == 4
                && getPosition(PIECES.G_TOP_MID.value).row() == 4
                && getPosition(PIECES.G_TOP_RIGHT.value).row() == 4
        );
    }

    /**
     * Method for check if position is on table.
     * @param position given position.
     * @return true if position is on table else false.
     */
    private boolean isPositionOnTable(Position position) {
        Logger.info("Checking if position is on table");
        return (
                (position.row() == 0 || position.row() == BOARD_ROW_SIZE - 1)
                        && (position.column() >= 0 && BOARD_COLUMN_SIZE > position.column()))
                || ((position.row() == 1 || position.row() == 3) && position.column() == 1)
                || (position.row() == 2 && (position.column() == 0 || position.column() == 1)
        );
    }

    /**
     * Method for move piece on table by calling move[UP, Down, Left, Right] method.
     * @param direction direction where to move.
     * @param piece which piece to move.
     */
    public void move(Direction direction, PIECES piece) {
        Logger.info("Moving piece " + piece);
        switch (direction){
            case UP -> moveUp(piece);
            case RIGHT -> moveRight(piece);
            case DOWN -> moveDown(piece);
            case LEFT -> moveLeft(piece);
        }
    }

    /**
     * Method for check if piece can move to given direction.
     * @param direction direction where to move.
     * @param piece which piece to move.
     * @return true if piece can move to given direction else false.
     */
    public boolean canMove(Direction direction, PIECES piece) {
        Logger.info("Checking if canMove piece " + piece);
        return switch (direction) {
            case UP -> canMoveUp(piece);
            case RIGHT -> canMoveRight(piece);
            case DOWN -> canMoveDown(piece);
            case LEFT -> canMoveLeft(piece);
        };
    }

    /**
     * Method for check if piece can move UP.
     * @param piece which piece to check.
     * @return true if piece can move up else false.
     */
    private boolean canMoveUp(PIECES piece) {
        Logger.info("Checking if canMoveUp piece " + piece);
        var value = piece.value;
        return getPosition(value).column() == 1 && getPosition(value).row() != 0
                && isEmpty(getPosition(value).move(Direction.UP));
    }

    /**
     * Method for check if piece can move RIGHT.
     * @param piece which piece to check.
     * @return true if piece can move right else false.
     */
    private boolean canMoveRight(PIECES piece) {
        Logger.info("Checking if canMoveRight piece " + piece);
        var value = piece.value;
        return (
                ((getPosition(value).column() != BOARD_COLUMN_SIZE - 1 )
                        && (getPosition(value).row() == 0 || getPosition(value).row() == 4))
                || (getPosition(value).column() == 0 && getPosition(value).row() == 2))
                        && isEmpty(getPosition(value).move(Direction.RIGHT));
    }

    /**
     * Method for check if piece can move DOWN.
     * @param piece which piece to check.
     * @return true if piece can move down else false.
     */
    private boolean canMoveDown(PIECES piece) {
        Logger.info("Checking if canMoveDown piece " + piece);
        var value = piece.value;
        return (
                (getPosition(value).row() != BOARD_ROW_SIZE - 1) && getPosition(value).column() == 1 )
                && isEmpty(getPosition(value).move(Direction.DOWN)
        );
    }

    /**
     * Method for check if piece can move LEFT.
     * @param piece which piece to check.
     * @return true if piece can move left else false.
     */
    private boolean canMoveLeft(PIECES piece) {
        Logger.info("Checking if canMoveLeft piece " + piece);
        var value = piece.value;
        return (
                getPosition(value).column() > 0 && (getPosition(value).row() == 0
                        || getPosition(value).row() == 2 || getPosition(value).row() == 4))
                        && isEmpty(getPosition(value).move(Direction.LEFT));
    }

    /**
     * Method for moving piece up on table.
     * @param piece any piece.
     */
    private void moveUp(PIECES piece) {
        movePiece(piece.value, Direction.UP);
        Logger.info("Moving up piece " + piece);
    }

    /**
     * Method for moving piece down on table.
     * @param piece any piece.
     */
    private void moveDown(PIECES piece) {
        movePiece(piece.value, Direction.DOWN);
        Logger.info("Moving down piece " + piece);
    }

    /**
     * Method for moving piece right on table.
     * @param piece any piece.
     */
    private void moveRight(PIECES piece){
        movePiece(piece.value, Direction.RIGHT);
        Logger.info("Moving right piece " + piece);
    }

    /**
     * Method for moving piece left on table.
     * @param piece any piece.
     */
    private void moveLeft(PIECES piece) {
        movePiece(piece.value, Direction.LEFT);
        Logger.info("Moving left piece");
    }

    /**
     * Method for moving piece on table.
     * @param index index of piece.
     * @param direction direction where to move.
     */
    private void movePiece(int index, Direction direction) {
        piecesPosition[index].set(getPosition(index).move(direction));
        Logger.info("Moving piece " + piecesPosition[index]);
    }

    private boolean isEmpty(Position position) {
        Logger.info("Checking if position is empty");
        for (var pos : piecesPosition){
            if (pos.get().equals(position)){
                Logger.info("Position is not empty");
                return false;
            }
        }
        Logger.info("Position is empty");
        return true;
    }

    /**
     * Method for check if array of position's is correct.
     * @param positions array of position's.
     */
    public void checkPositions(Position[] positions) {
        if (positions.length != PIECES_COUNT) {
            Logger.error("Incorrect number of positions");
            throw new IllegalArgumentException(String.format("Positions must have the same length as: %s", PIECES_COUNT));
        }

        for(var position : positions) {
            if (!isPositionOnTable(position)) {
                Logger.error("Incorrect position");
                throw new IllegalArgumentException(String.format("Position %s is not on table", position));
            }
        }
    }

    /* Below only implemented methods from TwoPhaseMoveState<Position> and other */

    @Override
    public boolean isLegalToMoveFrom(Position position) {
        Logger.info("Checking if position is legal to move from " + position);
        return Arrays
                .stream(piecesPosition)
                .anyMatch(p -> p.get().equals(position));
    }

    @Override
    public boolean isSolved() {
        Logger.info("Checking if game is solved");
        return isTargetPositionAchieved();
    }

    @Override
    public boolean isLegalMove(TwoPhaseMove<Position> positionTwoPhaseMove) {
        Logger.info("Checking if position is legal to move from " + positionTwoPhaseMove);
        return canMove(
                Direction.of(
                        positionTwoPhaseMove.to().row(),
                        positionTwoPhaseMove.to().column()
                ),
                getPieceByIndex(
                        getIndexOfPiece(
                                positionTwoPhaseMove.from().row(),
                                positionTwoPhaseMove.from().column()
                        )
                )
        );
    }

    @Override
    public void makeMove(TwoPhaseMove<Position> positionTwoPhaseMove) {
        Logger.info("Checking if position is legal to move from " + positionTwoPhaseMove);
        move(
                Direction.of(
                        positionTwoPhaseMove.to().row(),
                        positionTwoPhaseMove.to().column()
                ),
                getPieceByIndex(
                        getIndexOfPiece(
                                positionTwoPhaseMove.from().row(),
                                positionTwoPhaseMove.from().column()
                        )
                )
        );

        if(isTargetPositionAchieved()) {
            isSolved.set(true);
            Logger.info("Target position is achieved");
        }
    }

    @Override
    public Set<TwoPhaseMove<Position>> getLegalMoves() {
        Set<TwoPhaseMove<Position>> setOfLegalMoves = new HashSet<>(Set.of());
        TwoPhaseMove<Position> twoPhaseMove;
        for (var direction : Direction.values()) {
            for (var piece : PIECES.values()) {
                if (canMove(direction, piece)) {
                    twoPhaseMove = new TwoPhaseMove<>(
                            getPosition(piece.value),
                            new Position(
                                    direction.getRowChange(),
                                    direction.getColChange()
                            )
                    );
                    if (isLegalMove(twoPhaseMove)) {
                        setOfLegalMoves.add(twoPhaseMove);
                    }
                }
            }
        }
        Logger.info("Getting legal moves");
        return setOfLegalMoves;
    }

    @Override
    public PuzzleState clone() {
        return new PuzzleState(
                getPosition(PIECES.G_TOP_LEFT.value),
                getPosition(PIECES.G_TOP_MID.value),
                getPosition(PIECES.G_TOP_RIGHT.value),
                getPosition(PIECES.R_BOTTOM_LEFT.value),
                getPosition(PIECES.R_BOTTOM_MID.value),
                getPosition(PIECES.R_BOTTOM_RIGHT.value)
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        return (obj instanceof PuzzleState other)
                && getPosition(PIECES.G_TOP_LEFT.value)
                .equals(other.getPosition(PIECES.G_TOP_LEFT.value))
                && getPosition(PIECES.G_TOP_MID.value)
                .equals(other.getPosition(PIECES.G_TOP_MID.value))
                && getPosition(PIECES.G_TOP_RIGHT.value)
                .equals(other.getPosition(PIECES.G_TOP_RIGHT.value))
                && getPosition(PIECES.R_BOTTOM_LEFT.value)
                .equals(other.getPosition(PIECES.R_BOTTOM_LEFT.value))
                && getPosition(PIECES.R_BOTTOM_MID.value)
                .equals(other.getPosition(PIECES.R_BOTTOM_MID.value))
                && getPosition(PIECES.R_BOTTOM_RIGHT.value)
                .equals(other.getPosition(PIECES.R_BOTTOM_RIGHT.value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getPosition(PIECES.G_TOP_LEFT.value),
                getPosition(PIECES.G_TOP_MID.value),
                getPosition(PIECES.G_TOP_RIGHT.value),
                getPosition(PIECES.R_BOTTOM_LEFT.value),
                getPosition(PIECES.R_BOTTOM_MID.value),
                getPosition(PIECES.R_BOTTOM_RIGHT.value)
        );
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nPS[");
        for(var piece : piecesPosition) {
            sb.append(piece.get().toString());
            sb.append(" ");
        }
        sb.append(']');
        return sb.toString();
    }
}
