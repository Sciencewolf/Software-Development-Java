package solver;

import model.PuzzleState;
import puzzle.solver.BreadthFirstSearch;

public class Main {
    public static void main(String[] args) {
        var ps = new PuzzleState();
        var bfs = new BreadthFirstSearch();

        bfs.solveAndPrintSolution(ps);
    }
}
