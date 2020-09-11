import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Objects;

public class Solver {

    private boolean solvable;
    private final Stack<Board> solution = new Stack<>();

    private class SearchNode implements Comparable<SearchNode> {

        private final Board board;
        private final int moves;
        private final SearchNode prev;

        private SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        public int compareTo(SearchNode searchNode) {
            int m1 = this.board.manhattan();
            int m2 = searchNode.board.manhattan();
            int p1 = this.moves + m1;
            int p2 = searchNode.moves + m2;

            if (p1 < p2) {
                return -1;
            }
            if (p1 > p2) {
                return 1;
            }
            if (m1 < m2) {
                return -1;
            }
            if (m1 > m2) {
                return 1;
            }
            return 0;
        }

        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;
            SearchNode that = (SearchNode) other;
            return board.equals(that.board);
        }

        public int hashCode() {
            return Objects.hash(board);
        }

        public String toString() {
            return "SearchNode{" +
                    "board=" + board +
                    '}';
        }
    }

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        MinPQ<SearchNode> primaryQueue = new MinPQ<>();
        MinPQ<SearchNode> twinQueue = new MinPQ<>();
        primaryQueue.insert(new SearchNode(initial, 0, null));
        twinQueue.insert(new SearchNode(initial.twin(), 0, null));

        SearchNode finalNode = null;
        SearchNode tMin = null;
        while (finalNode == null && tMin == null) {
            SearchNode currentNode = primaryQueue.delMin();
            SearchNode twinNode = twinQueue.delMin();

            if (currentNode.board.isGoal()) {
                finalNode = currentNode;
            }
            if (twinNode.board.isGoal()) {
                tMin = twinNode;
            }

            for (Board n : currentNode.board.neighbors()) {
                SearchNode searchNode = new SearchNode(n, currentNode.moves + 1, currentNode);
                if (currentNode.prev == null || !currentNode.prev.board.equals(n)) {
                    primaryQueue.insert(searchNode);
                }
            }

            for (Board n : twinNode.board.neighbors()) {
                SearchNode searchNode = new SearchNode(n, twinNode.moves + 1, twinNode);
                if (twinNode.prev == null || !twinNode.prev.board.equals(n)) {
                    twinQueue.insert(searchNode);
                }
            }
        }

        if (finalNode == null) {
            solvable = false;
        }
        else {
            solution.push(finalNode.board);
            while (finalNode.prev != null) {
                solution.push(finalNode.prev.board);
                finalNode = finalNode.prev;
            }
            solvable = true;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        return solvable ? solution.size() - 1 : -1;
    }

    public Iterable<Board> solution() {
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
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
