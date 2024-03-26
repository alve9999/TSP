

public interface Solver {
    /**
     * Solves the Traveling Salesman Problem (TSP) given a matrix of distances
     * between points and an array of points.
     * 
     * @param distances The matrix of distances between points.
     * @param points    The array of points.
     * @return An array representing the optimal order of visiting the points.
     */
    int[] solveTSP(double[][] distances);
}
