import java.util.stream.IntStream;

public class BruteForce implements Solver {

    public int[] solveTSP(double[][] distances) {
        int n = distances.length;
        int[] permutation = IntStream.range(0, n).toArray();
        int[] bestTour = permutation.clone();
        int shortestLength = CalculateDistance.calculateTourLength(distances, bestTour);

        while (nextPermutation(permutation)) {
            int currentLength = CalculateDistance.calculateTourLength(distances, permutation);
            if (currentLength < shortestLength) {
                shortestLength = currentLength;
                bestTour = permutation.clone();
            }
        }

        return bestTour;
    }

    private boolean nextPermutation(int[] permutation) {
        int n = permutation.length;

        // Find the largest index k such that a[k] < a[k + 1]
        int k = -1;
        for (int i = 0; i < n - 1; i++) {
            if (permutation[i] < permutation[i + 1]) {
                k = i;
            }
        }

        // If no such index exists, the permutation is the last permutation
        if (k == -1) {
            return false;
        }

        // Find the largest index l greater than k such that a[k] < a[l]
        int l = -1;
        for (int i = k + 1; i < n; i++) {
            if (permutation[k] < permutation[i]) {
                l = i;
            }
        }

        // Swap the value of a[k] with that of a[l]
        int temp = permutation[k];
        permutation[k] = permutation[l];
        permutation[l] = temp;

        // Reverse the sequence from a[k + 1] up to and including the final element a[n]
        for (int i = k + 1, j = n - 1; i < j; i++, j--) {
            temp = permutation[i];
            permutation[i] = permutation[j];
            permutation[j] = temp;
        }

        return true;
    }
}
