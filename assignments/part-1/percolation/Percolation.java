import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int gridSize;
    private final boolean[][] grid;
    private final int[][] gridIndexValue;
    private int openSiteCount = 0;

    private final WeightedQuickUnionUF uf;
    private final int virtualTop;
    private final int virtualBottom;


    public Percolation(int gridSize) {
        validateGridSize(gridSize);
        this.gridSize = gridSize;
        this.grid = new boolean[gridSize][gridSize];
        this.gridIndexValue = new int[gridSize][gridSize];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = false;
                gridIndexValue[i][j] = (i * grid.length) + j;
            }
        }
        this.virtualTop = gridSize * gridSize + 1;
        this.virtualBottom = gridSize * gridSize + 2;

        uf = new WeightedQuickUnionUF(gridSize * gridSize + 3);
    }

    public boolean isOpen(int row, int col) {
        validateRowCol(row, col);
        return grid[row - 1][col - 1];
    }

    public void open(int row, int col) {
        validateRowCol(row, col);
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            openSiteCount++;

            if (isSiteInTop(row)) {
                uf.union(virtualTop, gridIndexValue[row - 1][col - 1]);
            }
            if (isSiteInBottom(row)) {
                uf.union(virtualBottom, gridIndexValue[row - 1][col - 1]);
            }

            if (isSiteRightEdge(col)) {
                if (isLeftSiteOpen(row, col)) {
                    uf.union(gridIndexValue[row - 1][col - 1], gridIndexValue[row - 1][col - 2]);
                }
                if (isBottomSiteOpen(row, col)) {
                    uf.union(gridIndexValue[row - 1][col - 1], gridIndexValue[row][col - 1]);
                }
            }
            if (isSiteLeftEdge(col)) {
                if (isRightSiteOpen(row, col)) {
                    uf.union(gridIndexValue[row - 1][col - 1], gridIndexValue[row - 1][col]);
                }
                if (isBottomSiteOpen(row, col)) {
                    uf.union(gridIndexValue[row - 1][col - 1], gridIndexValue[row][col - 1]);
                }
            }
            if (isRightSiteOpen(row, col)) {
                uf.union(gridIndexValue[row - 1][col - 1], gridIndexValue[row - 1][col]);
            }
            if (isLeftSiteOpen(row, col)) {
                uf.union(gridIndexValue[row - 1][col - 1], gridIndexValue[row - 1][col - 2]);
            }
            if (isBottomSiteOpen(row, col)) {
                uf.union(gridIndexValue[row - 1][col - 1], gridIndexValue[row][col - 1]);
            }
            if (isTopSiteOpen(row, col)) {
                uf.union(gridIndexValue[row - 1][col - 1], gridIndexValue[row - 2][col - 1]);
            }
        }
    }

    public boolean isFull(int row, int col) {
        validateRowCol(row, col);
        int site = gridIndexValue[row - 1][col - 1];
        int p = uf.find(site);
        int vTop = uf.find(virtualTop);
        return isOpen(row, col) && p == vTop;
    }

    public boolean percolates() {
        // check from top to bottom if there is a connection
        int top = uf.find(virtualTop);
        int bottom = uf.find(virtualBottom);
        return top == bottom;
    }

    public int numberOfOpenSites() {
        return openSiteCount;
    }

    private boolean isSiteLeftEdge(int col) {
        return col - 1 == 0;
    }

    private boolean isSiteRightEdge(int col) {
        return col - 1 == gridSize - 1;
    }

    private boolean isSiteInTop(int row) {
        return row - 1 == 0;
    }

    private boolean isSiteInBottom(int row) {
        return row - 1 == gridSize - 1;
    }

    private boolean isLeftSiteOpen(int row, int col) {
        if (!isInbound(row - 1, col - 2)) return false;
        return grid[row - 1][col - 2];
    }

    private boolean isRightSiteOpen(int row, int col) {
        if (!isInbound(row - 1, col)) return false;
        return grid[row - 1][col];
    }

    private boolean isTopSiteOpen(int row, int col) {
        if (!isInbound(row - 2, col - 1)) return false;
        return grid[row - 2][col - 1];
    }

    private boolean isBottomSiteOpen(int row, int col) {
        if (!isInbound(row, col - 1)) return false;
        return grid[row][col - 1];
    }

    private boolean isInbound(int row, int col) {
        if (row < 0 || row >= gridSize) return false;
        if (col < 0 || col >= gridSize) return false;
        return true;
    }

    private void validateRowCol(int row, int col) {
        if (row < 1 || row > gridSize) {
            throw new IllegalArgumentException("row out of range");
        }
        if (col < 1 || col > gridSize) {
            throw new IllegalArgumentException("col out of range");
        }
    }


    private void validateGridSize(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be greator than 0");
    }
}
