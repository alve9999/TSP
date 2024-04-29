import java.util.Random;
import java.util.stream.IntStream;

import static common.CalculateDistance.calculateTourLength;
import static java.lang.Math.exp;

public class SimulatedAnneling extends Solver{

    @Override
    public int[] solveTSP(double[][] distances) {
        int n = distances.length;
        int[] tour = IntStream.range(0, n).toArray();

        int[] best = tour;
        Random rand = new Random();
        float shortest = calculateTourLength(distances,best);
        float temp = 10000.0f;

        while(timer){
            int[] new_tour = tour.clone();
            int swap1 = rand.nextInt(1,n);
            int swap2 = rand.nextInt(1,n);
            int temporary = new_tour[swap1];
            new_tour[swap1] = new_tour[swap2];
            new_tour[swap2] = temporary;

            float current_energy = calculateTourLength(distances,tour);
            float new_energy = calculateTourLength(distances,new_tour);

            if(new_energy<current_energy){
                tour = new_tour;
                if(new_energy<shortest){
                    shortest = new_energy;
                    best = tour;
                }
            }
            else{
                double prob = exp(-(new_energy-current_energy)/temp);
                if(rand.nextFloat()<prob){
                    tour=new_tour;
                }
            }
            temp = 0.999f*temp;
        }
        return best;
    }
}
