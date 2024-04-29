package common;
public class CalculateDistance {
    public static int calculateTourLength(double[][] connections,int[] tour) {
        int length = 0;
        for (int i = 0; i < tour.length-1; i++) {
            int from = tour[i];
            int to = tour[i + 1];
            length += connections[from][to];
        }
        return length;
    }
}
