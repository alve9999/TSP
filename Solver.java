import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public abstract class Solver {
    /**
     * Solves the Traveling Salesman Problem (TSP) given a matrix of distances
     * between points and an array of points.
     * 
     * @param distances The matrix of distances between points.
     * @param timer A timer that expires after the allocated time.
     * @return An array representing the optimal order of visiting the points.
     */
    public volatile boolean timer = true;
    public Lock lock = new ReentrantLock();
    public abstract int[] solveTSP(double[][] distances);
}
