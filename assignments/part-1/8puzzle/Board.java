import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {

    private enum Direction {
        UP, LEFT, RIGHT, DOWN
    }

    private final int[][] tiles;
    private final int n;

    public Board(int[][] tiles) {
        if (tiles == null || tiles.length == 0) throw new IllegalArgumentException();
        if (tiles.length != tiles[0].length) throw new IllegalArgumentException();
        this.tiles = copy(tiles);
        this.n = tiles.length;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(n);
        builder.append(System.lineSeparator());
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                builder.append(" ");
                builder.append(tiles[i][j]);
            }
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }

    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] == 0) continue;
                int value = tiles[i][j];
                if (value != i * tiles.length + j + 1) count++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

        int count = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {

                if (tiles[i][j] == 0) continue;
                int value = tiles[i][j];
                count += Math.abs(i - getRow(value)) + Math.abs(j - getCol(value));
            }
        }
        return count;
    }

    public boolean isGoal() {
        int[][] goal = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n - 1 && j == n - 1) {
                    goal[i][j] = 0;
                }
                else goal[i][j] = i * n + j + 1;
                if (tiles[i][j] != goal[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Board that = (Board) other;
        if (this.dimension() != that.dimension()) {
            return false;
        }
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] != 0) {
                    continue;
                }
                // find adj indexes
                int bottomIndex = i + 1;
                int upIndex = i - 1;
                int rightIndex = j + 1;
                int leftIndex = j - 1;

                if (isValidCoordinate(bottomIndex, j)) {
                    neighbors.push(new Board(
                            createBoardNeighborBoard(i, j, bottomIndex, Direction.DOWN)));
                }
                if (isValidCoordinate(upIndex, j)) {
                    neighbors.push(new Board(
                            createBoardNeighborBoard(i, j, upIndex, Direction.UP)));
                }
                if (isValidCoordinate(i, rightIndex)) {
                    neighbors.push(new Board(
                            createBoardNeighborBoard(i, j, rightIndex, Direction.RIGHT)));
                }
                if (isValidCoordinate(i, leftIndex)) {
                    neighbors.push(new Board(
                            createBoardNeighborBoard(i, j, leftIndex, Direction.LEFT)));
                }
            }
        }
        return neighbors;
    }

    private int[][] createBoardNeighborBoard(int i, int j, int targetIndex, Direction direction) {
        int[][] copy = copy(tiles);
        int tmp = copy[i][j];
        if (Direction.DOWN == direction || Direction.UP == direction) {
            copy[i][j] = copy[targetIndex][j];
            copy[targetIndex][j] = tmp;
        }
        else if (Direction.LEFT == direction || Direction.RIGHT == direction) {
            copy[i][j] = copy[i][targetIndex];
            copy[i][targetIndex] = tmp;
        }
        return copy;
    }

    private void swap(int i, int j, int targetIndex, int[][] twinCopy, Direction direction) {
        int tmp = twinCopy[i][j];
        if (Direction.DOWN == direction || Direction.UP == direction) {
            twinCopy[i][j] = twinCopy[targetIndex][j];
            twinCopy[targetIndex][j] = tmp;
        }
        else if (Direction.LEFT == direction || Direction.RIGHT == direction) {
            twinCopy[i][j] = twinCopy[i][targetIndex];
            twinCopy[i][targetIndex] = tmp;
        }
    }

    private int[][] copy(int[][] board) {
        int[][] copy = board.clone();
        for (int ii = 0; ii < copy.length; ii++) {
            copy[ii] = copy[ii].clone();
        }
        return copy;
    }

    public Board twin() {
        int[][] twin = copy(tiles);

        int i1 = 0;
        int j1 = 0;
        int i2 = 1;
        int j2 = 1;
        if (twin[i1][j1] == 0) j1++;
        if (twin[i2][j2] == 0) j2--;


        int temp = twin[i1][j1];
        twin[i1][j1] = twin[i2][j2];
        twin[i2][j2] = temp;
        return new Board(twin);
    }

    private boolean isValidCoordinate(int i, int j) {
        if (i >= 0 && i < n && j >= 0 && j < n) {
            return true;
        }
        return false;
    }

    private int getCol(int value) {
        return (value - 1) % n;
    }

    private int getRow(int value) {
        return (value - 1) / n;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        StdOut.println(initial.dimension());
        StdOut.println(initial.manhattan());
        StdOut.println(initial.hamming());
        StdOut.println(initial.isGoal());
        StdOut.println(initial.neighbors());
        StdOut.println(initial.twin());
    }

}
