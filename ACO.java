import common.CalculateDistance;

import java.util.Random;

import static java.lang.Math.pow;
import static common.CalculateDistance.calculateTourLength;
public class ACO extends Solver{

    @Override
    public int[] solveTSP(double[][] distances) {
        int num_ants = 20;
        double alpha = 1.0f;
        double beta = 4.0f;
        double initial_pheromone = 1.0f;
        double pheromone_strength = 10.0f;
        double evaporation_rate = 0.2f;
        int n = distances.length;
        Random rand = new Random();

        double[][] pheromone = new double[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                pheromone[i][j]=initial_pheromone;
            }
        }

        int[] best = new int[n + 1];
        double shortest = 1000000000000.0f;
        double[] prob = new double[n];
        while (timer){
            for(int ants = 0; ants<num_ants;ants++){
                int[] tour = new int[n + 1];
                boolean[] visited = new boolean[n];
                int current = rand.nextInt(n);
                visited[current] = true;
                tour[0] = current;

                for(int i = 0; i<n-1;i++){
                    double sum = 0.0f;
                    for(int next = 0; next<n;next++){
                        if(!visited[next]){
                            double desirability = pow(pheromone[current][next],alpha)*pow(distances[current][next],-beta);
                            prob[next]=desirability;
                            sum+=desirability;
                        }
                        else{
                            prob[next] = 0.0f;
                        }
                    }
                    double rand_val = rand.nextFloat();
                    double prob_cumulative = 0.0f;
                    for(int k = 0; k < n; k++){
                        if(!visited[k]){
                            prob_cumulative+=prob[k]/sum;
                            if(rand_val<=prob_cumulative){
                                current = k;
                                visited[current] = true;
                                tour[i+1] = current;
                                break;
                            }
                        }
                        if(k==n-1){
                            for(int j = 0; j<n ; j++){
                                if(!visited[j]){
                                    current = j;
                                    visited[current] = true;
                                    tour[i+1] = current;
                                    break;
                                }
                            }
                        }
                    }
                }
                tour[n] = tour[0];
                double current_distance = calculateTourLength(distances,tour);
                if(current_distance<shortest){
                    shortest = current_distance;
                    best = tour;
                }

                double delta_pheromone = pheromone_strength / current_distance;
                for (int i = 0; i < n ; i++){
                    int from = tour[i];
                    int to = tour[(i + 1)%n];
                    pheromone[from][to] += delta_pheromone;
                }
            }
            for(int i = 0; i<n; i++){
                for(int j = 0; j<n; j++){
                    pheromone[i][j]*=(1-evaporation_rate);
                }
            }
        }
        return best;
    }
}
