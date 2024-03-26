public class CalculateDistance {
    public int calculateTourLength(double[][] connections,int[] tour) {
        int length = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            int from = tour.get(i);
            int to = tour.get(i + 1);
            length += connections[from][to];
        }
        // Add length from the last city back to the starting city
        length += connections[tour.get(tour.size() - 1)][tour.get(0)];
        return length;
    }
}
