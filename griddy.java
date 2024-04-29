
public class griddy extends Solver {
    
    public int[] solveTSP(double[][] distances) {
        int numPoints = distances.length;
        int[] tour = new int[numPoints+1];
        boolean[] visited = new boolean[numPoints];
        
        // Start from the first point
        tour[0] = 0;
        visited[0] = true;
        
        for (int i = 1; i < numPoints; i++) {
            int currentCity = tour[i - 1];
            int nearestCity = -1;
            double minDistance = Double.MAX_VALUE;
            
            // Find the nearest unvisited city
            for (int j = 0; j < numPoints; j++) {
                if (!visited[j] && distances[currentCity][j] < minDistance) {
                    nearestCity = j;
                    minDistance = distances[currentCity][j];
                }
            }
            
            // Mark the nearest city as visited and add it to the tour
            tour[i] = nearestCity;
            visited[nearestCity] = true;
        }
        tour[numPoints]=0;

        // Return to the starting point to complete the tour
        return tour;
    }
}
