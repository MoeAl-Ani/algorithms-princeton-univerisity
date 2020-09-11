import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] result;

    public PercolationStats(int n, int trials) {
        validate(n, trials);
        this.result = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            int openCount = 0;
            while (!percolation.percolates()) {

                int x = StdRandom.uniform(1, n + 1);
                int y = StdRandom.uniform(1, n + 1);

                if (!percolation.isOpen(x, y)) {
                    percolation.open(x, y);
                    openCount++;
                }
                result[i] = ((double) openCount / (double) (n * n));
            }
        }
    }

    private void validate(int n, int trials) {
        if (n <= 0) throw new IllegalArgumentException("grid size was less/equal zero");
        if (trials <= 0) throw new IllegalArgumentException("trials must be greator than 0");
    }

    public double mean() {
        return StdStats.mean(result);
    }

    public double stddev() {
        return StdStats.stddev(result);
    }

    public double confidenceLo() {
        return StdStats.min(result);
    }

    public double confidenceHi() {
        return StdStats.max(result);
    }

    public static void main(String[] args) {
        int n = 10;
        int trials = 30;
        if (args.length == 1) {
            n = Integer.parseInt(args[0]);
        }
        else if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        }
        PercolationStats percolationStats = new PercolationStats(n, trials);
        System.out.println("mean\t= " + percolationStats.mean());
        System.out.println("stddev\t= " + percolationStats.stddev());
        System.out.println("95% confidence interval\t= " + percolationStats.confidenceLo() + ", "
                                   + percolationStats.confidenceHi());
    }
}
