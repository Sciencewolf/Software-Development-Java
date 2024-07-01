import model.Direction;
import model.PuzzleState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PuzzleStateTest {
    PuzzleState ps = new PuzzleState();

    @Test
    public void test_canMove() {
        assertFalse(ps.canMove(Direction.UP, PuzzleState.PIECES.G_TOP_MID));
        assertFalse(ps.canMove(Direction.LEFT, PuzzleState.PIECES.G_TOP_MID));
        assertFalse(ps.canMove(Direction.RIGHT, PuzzleState.PIECES.G_TOP_MID));
        assertTrue(ps.canMove(Direction.DOWN, PuzzleState.PIECES.G_TOP_MID));

        assertFalse(ps.canMove(Direction.UP, PuzzleState.PIECES.G_TOP_LEFT));
        assertFalse(ps.canMove(Direction.LEFT, PuzzleState.PIECES.G_TOP_LEFT));
        assertFalse(ps.canMove(Direction.RIGHT, PuzzleState.PIECES.G_TOP_LEFT));
        assertFalse(ps.canMove(Direction.DOWN, PuzzleState.PIECES.G_TOP_LEFT));

        assertFalse(ps.canMove(Direction.UP, PuzzleState.PIECES.G_TOP_RIGHT));
        assertFalse(ps.canMove(Direction.LEFT, PuzzleState.PIECES.G_TOP_RIGHT));
        assertFalse(ps.canMove(Direction.RIGHT, PuzzleState.PIECES.G_TOP_RIGHT));
        assertFalse(ps.canMove(Direction.DOWN, PuzzleState.PIECES.G_TOP_RIGHT));

        assertTrue(ps.canMove(Direction.UP, PuzzleState.PIECES.R_BOTTOM_MID));
        assertFalse(ps.canMove(Direction.LEFT, PuzzleState.PIECES.R_BOTTOM_MID));
        assertFalse(ps.canMove(Direction.RIGHT, PuzzleState.PIECES.R_BOTTOM_MID));
        assertFalse(ps.canMove(Direction.DOWN, PuzzleState.PIECES.R_BOTTOM_MID));

        assertFalse(ps.canMove(Direction.UP, PuzzleState.PIECES.R_BOTTOM_LEFT));
        assertFalse(ps.canMove(Direction.LEFT, PuzzleState.PIECES.R_BOTTOM_LEFT));
        assertFalse(ps.canMove(Direction.RIGHT, PuzzleState.PIECES.R_BOTTOM_LEFT));
        assertFalse(ps.canMove(Direction.DOWN, PuzzleState.PIECES.R_BOTTOM_LEFT));

        assertFalse(ps.canMove(Direction.UP, PuzzleState.PIECES.R_BOTTOM_RIGHT));
        assertFalse(ps.canMove(Direction.LEFT, PuzzleState.PIECES.R_BOTTOM_RIGHT));
        assertFalse(ps.canMove(Direction.RIGHT, PuzzleState.PIECES.R_BOTTOM_RIGHT));
        assertFalse(ps.canMove(Direction.DOWN, PuzzleState.PIECES.R_BOTTOM_RIGHT));
    }

    @Test
    public void test_toString() {
        assertEquals("\nPS[(0, 0) (0, 1) (0, 2) (4, 0) (4, 1) (4, 2) ]", ps.toString());
    }
}
