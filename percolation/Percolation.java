import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// import java.io.File;
// import java.io.FileNotFoundException;
// import java.util.Scanner;


public class Percolation {
    private int size;
    private boolean[] openedSites;
    private WeightedQuickUnionUF quickFindUF;
    private int top, bottom, openedSitesCount;

    // creates n-by-n grid, with all sites initially blocked
    // time O(n^2)
    public Percolation(int size) {
        this.size = size;
        if (this.size <= 0)
            throw new IllegalArgumentException();

        this.openedSites = new boolean[this.size * this.size];
        for (int i = 0; i < this.size * this.size; i++) {
            this.openedSites[i] = false;
        }
        this.openedSitesCount = 0;

        this.quickFindUF = new WeightedQuickUnionUF(this.size * this.size + 2);
        this.top = 0;
        this.bottom = this.size * this.size + 1;
    }

    // opens the site (row, col) if it is not open already
    // time constant + constant colls of union-find
    public void open(int row, int col) {
        if (isOpen(row, col))
            return;

        int unionFindIndex = getUnionFindIndex(row, col);

        openedSites[getOpenedSitesIndex(row, col)] = true;
        openedSitesCount += 1;
        if (row - 1 == 0) // top
            quickFindUF.union(unionFindIndex, top);
        else if (isOpen(row - 1, col))
            quickFindUF.union(unionFindIndex, getUnionFindIndex(row - 1, col));
        if (row == this.size) // bottom
            quickFindUF.union(unionFindIndex, bottom);
        else if (openedSites[getOpenedSitesIndex(row + 1, col)])
            quickFindUF.union(unionFindIndex, getUnionFindIndex(row + 1, col));
        if (col + 1 <= this.size && isOpen(row, col + 1)) // right
            quickFindUF.union(unionFindIndex, getUnionFindIndex(row, col + 1));
        if (col - 1 >= 1 && isOpen(row, col - 1)) // left
            quickFindUF.union(unionFindIndex, getUnionFindIndex(row, col - 1));
    }

    private int getOpenedSitesIndex(int row, int col) {
        int nonHumanRow = row - 1;
        int nonHumanCol = col - 1;
        return nonHumanRow * this.size + nonHumanCol;
    }

    // is the site (row, col) open?
    // time constant + constant colls of union-find
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > this.size || col < 1 || col > this.size)
            throw new IllegalArgumentException();

        return openedSites[getOpenedSitesIndex(row, col)];
    }

    // is the site (row, col) full?
    // time constant + constant colls of union-find
    public boolean isFull(int row, int col) {
        if (row < 1 || row > this.size || col < 1 || col > this.size)
            throw new IllegalArgumentException();

        return quickFindUF.connected(top, getUnionFindIndex(row, col));
    }

    private int getUnionFindIndex(int row, int col) {
        int nonHumanRow = row - 1;
        int nonHumanCol = col - 1;
        int unionFindOffset = 1;
        return nonHumanRow * this.size + nonHumanCol + unionFindOffset;
    }

    // returns the number of open sites
    // time constant + constant colls of union-find
    public int numberOfOpenSites() {
        return this.openedSitesCount;
    }

    // does the system percolate?
    // time constant + constant colls of union-find
    public boolean percolates() {
        return quickFindUF.connected(top, bottom);
    }

    // test client (optional)
    // time constant + constant calls of union-find
    public static void main(String[] args) {

        // String filename = "wayne98.txt";
        // boolean answer = true;
        // int openSitesCount = 5079;
        // try {
        //     File myObj = new File(filename);
        //     Scanner myReader = new Scanner(myObj);
        //
        //     int size = Integer.parseInt(myReader.nextLine());
        //     Percolation percolation = new Percolation(size);
        //
        //     while (myReader.hasNextLine()) {
        //         String s = myReader.nextLine();
        //         if (s.equals(""))
        //             continue;
        //
        //         String[] union = s.trim().split("\\s+");
        //         int row = Integer.parseInt(union[0]);
        //         int col = Integer.parseInt(union[1]);
        //         percolation.open(row, col);
        //     }
        //     myReader.close();
        //
        //     System.out.println("Percolates?");
        //     System.out.println("Should be: " + String.valueOf(answer));
        //     System.out.println("Got:       " + String.valueOf(percolation.percolates()));
        //
        //     System.out.println();
        //     System.out.println("Open sites count");
        //     System.out.println("Should be: " + String.valueOf(openSitesCount));
        //     System.out.println("Got:       " + String.valueOf(percolation.numberOfOpenSites()));
        //
        // }
        // catch (FileNotFoundException e) {
        //     System.out.println("An error occurred.");
        //     e.printStackTrace();
        // }
    }
}
