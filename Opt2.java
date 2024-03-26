
import com.CalculateDistance;

public class Opt2 implements Solver{

    public int[] solveTSP(double[][] distances){
        int n = distances.length;
        boolean improvement = true;
        int[] tour = new int[n+1];
        for (int i = 0; i < n; i++) {
            tour[i] = i;
        }

        while (improvement) {
            improvement = false;
            for (int i = 1; i < n - 1; ++i) {
                for (int j = i + 1; j < n; ++j) {
                    int[] newOrder = tour.clone();
                    reverse(newOrder, i, j);
                    double newDistance = CalculateDistance.calculateTourLength(distances, newOrder);
                    if (newDistance < CalculateDistance.calculateTourLength(distances, tour)) {
                        tour = newOrder;
                        improvement = true;
                    }
                }
            }
        }
	tour[n]=tour[0];
	return tour;
    }
    
    private void reverse(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
    
}
