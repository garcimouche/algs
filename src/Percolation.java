import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int TOP_SITE = 0;

    private boolean[][] grid;

    private final WeightedQuickUnionUF uf;

    private final int bottomSite;
    private int openedSite;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        grid = new boolean[n][n];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = false;
            }
        }

        bottomSite = n * n + 1;

        uf = new WeightedQuickUnionUF(n * n + 2);

    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            openedSite++;
            connectNeighbor(row, col);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isInBound(row, col))
            throw new IllegalArgumentException("out of bound");
        return grid[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        return uf.connected(getNodeIndex(row, col), TOP_SITE);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openedSite;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(TOP_SITE, bottomSite);
    }

    // test client (optional)
    public static void main(String[] args) {

        System.out.println("starting...");

        int n = 200;

        Percolation pr = new Percolation(n);

        while (!pr.percolates()) {
            int row = StdRandom.uniform(n) + 1;
            int col = StdRandom.uniform(n) + 1;
            if (!pr.isOpen(row, col))
                pr.open(row, col);
        }

        Stopwatch sw = new Stopwatch();

        double d = (double) pr.numberOfOpenSites() / (n * n);
        System.out.println(String.format("completed! p*=%f, time spent:%f", d, sw.elapsedTime()));

    }

    private void connectNeighbor(int row, int col) {

        int nodeIndex = getNodeIndex(row, col);

        if (row == 1)
            uf.union(nodeIndex, TOP_SITE);
        else if (row == grid.length)
            uf.union(nodeIndex, bottomSite);

        if (isInBound(row - 1, col) && isOpen(row - 1, col))
            uf.union(nodeIndex, getNodeIndex(row - 1, col));
        if (isInBound(row + 1, col) && isOpen(row + 1, col))
            uf.union(nodeIndex, getNodeIndex(row + 1, col));
        if (isInBound(row, col - 1) && isOpen(row, col - 1))
            uf.union(nodeIndex, getNodeIndex(row, col - 1));
        if (isInBound(row, col + 1) && isOpen(row, col + 1))
            uf.union(nodeIndex, getNodeIndex(row, col + 1));

    }

    private int getNodeIndex(int row, int col) {
        return (row - 1) * grid.length + col;
    }

    private boolean isInBound(int row, int col) {
        return row >= 1 && row <= grid.length && col >= 1 && col <= grid.length;
    }

}
