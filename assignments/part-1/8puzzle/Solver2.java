import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
public class Solver2 {
    private Collection<Board> solution;
    private boolean isSolveable;
    // find a solution to the initial board (using the A* algorithm)
    public Solver2(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        MinPQ<SearchNode> primaryQueue = new MinPQ<>();
        MinPQ<SearchNode> twinQueue = new MinPQ<>();
        primaryQueue.insert(new SearchNode(initial));
        twinQueue.insert(new SearchNode(initial.twin()));
        solve(primaryQueue, twinQueue);
    }
    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolveable;
    }
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        //size -1 because eg. to get to two boards in the collection, you need 1 move
        return solution.size() - 1;
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        return solution;
    }
    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver2 solver = new Solver2(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
    private void solve(MinPQ<SearchNode> primaryQueue, MinPQ<SearchNode> twinQueue) {
        SearchNode currentNode;
        do {
            currentNode = expand(primaryQueue);
        } while (currentNode == null && expand(twinQueue) == null);
        //Twin queue found a solution while the current one didn't. This means the initial board is not solveable
        if (currentNode == null) {
            solution = Collections.emptyList();
            isSolveable = false;
        } else {
            solution = createSolution(currentNode);
            isSolveable = true;
        }
    }
    private SearchNode expand(MinPQ<SearchNode> moves) {
        if (moves.isEmpty()) return null;
        SearchNode bestMove = moves.delMin();
        if (bestMove.board.isGoal()) return bestMove;
        for (Board neighbor : bestMove.board.neighbors()) {
            if (bestMove.previous == null || !neighbor.equals(bestMove.previous.board)) {
                moves.insert(new SearchNode(neighbor, bestMove));
            }
        }
        return null;
    }
    private Collection<Board> createSolution(SearchNode currentNode) {
        List<Board> solution = new ArrayList<>();
        solution.add(currentNode.board);
        while (currentNode.previous != null) {
            solution.add(currentNode.previous.board);
            currentNode = currentNode.previous;
        }
        Collections.reverse(solution);
        return solution;
    }
    private static class SearchNode implements Comparable<SearchNode> {
        private final int priority;
        private final int moves;
        private final Board board;
        private final SearchNode previous;
        public SearchNode(Board board, SearchNode previous) {
            this.board = board;
            this.previous = previous;
            this.priority = board.manhattan();
            this.moves = this.previous.moves + 1;
        }
        public SearchNode(Board board) {
            this.board = board;
            this.previous = null;
            this.priority = board.manhattan();
            this.moves = 0;
        }
        @Override
        public int compareTo(SearchNode node) {
            return (this.priority - node.priority) + (this.moves - node.moves);
        }
    }
}
