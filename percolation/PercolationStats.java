import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    public double mean, stddev;
    public int trials, n;
    public double[] probs;
    public double time;
    public Percolation percolation;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        this.n = n;
        this.trials = trials;
        this.probs = new double[trials];

        int row, col;

        Stopwatch stopwatch = new Stopwatch();

        for (int i = 0; i < this.trials; i++) {
            percolation = new Percolation(this.n);
            while (!percolation.percolates()) {
                row = StdRandom.uniform(this.n) + 1;
                col = StdRandom.uniform(this.n) + 1;
                percolation.open(row, col);
            }
            probs[i] = percolation.numberOfOpenSites() / (double) (this.n * this.n);
        }
        time = stopwatch.elapsedTime();
    }

    // sample mean of percolation threshold
    public double mean() {
        mean = StdStats.mean(probs);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stddev = StdStats.stddev(probs);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double confidenceLevel = 1.96;
        double temp = confidenceLevel * stddev / Math.sqrt(trials);
        return mean - temp;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double confidenceLevel = 1.96;
        double temp = confidenceLevel * stddev / Math.sqrt(trials);
        return mean + temp;
    }

    // test client (see below)
    public static void main(String[] args) {
        // n-by-n grid
        int n = Integer.parseInt(args[0]), row, col;
        // T independent computational experiments
        int T = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, T);

        // print the original array
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() +
                                   ", " + stats.confidenceHi() + "]");
        System.out.println("elapsed time            = " + stats.time);
    }
}
