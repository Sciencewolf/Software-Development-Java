import model.Direction;
import model.Position;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    Position position;

    /**
     * Custom assert for Posiiton
     * @param expectedRow
     * @param expectedCol
     * @param position
     */
    public void asserPosition(int expectedRow, int expectedCol, Position position){
        assertAll("position",
                () -> assertEquals(expectedRow, position.row()),
                () -> assertEquals(expectedCol,position.column())
        );
    }

    @Test
    public void test_move() {
        position = new Position(0,0);
        asserPosition(-1,0, position.move(Direction.UP));
        asserPosition(0,1, position.move(Direction.RIGHT));
        asserPosition(1,0, position.move(Direction.DOWN));
        asserPosition(0,-1, position.move(Direction.LEFT));
    }

    @Test
    public void test_moveUp() {
        position = new Position(0,0);
        asserPosition(-1,0, position.move(Direction.UP));
    }

    @Test
    public void test_moveRight() {
        position = new Position(0,0);
        asserPosition(0,1, position.move(Direction.RIGHT));
    }

    @Test
    public void test_moveDown() {
        position = new Position(0,0);
        asserPosition(1,0, position.move(Direction.DOWN));
    }

    @Test
    public void test_moveLeft() {
        position = new Position(0,0);
        asserPosition(0,-1, position.move(Direction.LEFT));
    }

}
