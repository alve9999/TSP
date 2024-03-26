import java.util.Arrays;
import java.util.Random;

public class genetic implements Solver {
    private static final int POPULATION_SIZE = 100;
    private static final double MUTATION_RATE = 0.1;
    private static final int MAX_GENERATIONS = 1000;
    
    private Random random = new Random();
    
    public int[] solveTSP(double[][] distances) {
        int numPoints = distances.length;
        int[][] population = initializePopulation(numPoints);
        
        for (int generation = 0; generation < MAX_GENERATIONS; generation++) {
            int[][] newPopulation = new int[POPULATION_SIZE][numPoints];
            double[] fitness = new double[POPULATION_SIZE];
            
            // Evaluate fitness of each individual
            for (int i = 0; i < POPULATION_SIZE; i++) {
                fitness[i] = evaluateFitness(population[i], distances);
            }
            
            // Selection, crossover, and mutation
            for (int i = 0; i < POPULATION_SIZE; i++) {
                int[] parent1 = selectParent(population, fitness);
                int[] parent2 = selectParent(population, fitness);
                int[] child = crossover(parent1, parent2);
                mutate(child);
                newPopulation[i] = child;
            }
            
            // Replace old population with new population
            population = newPopulation;
        }
        
        // Find the best individual in the final population
        double bestFitness = Double.MAX_VALUE;
        int[] bestIndividual = null;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            double currentFitness = evaluateFitness(population[i], distances);
            if (currentFitness < bestFitness) {
                bestFitness = currentFitness;
                bestIndividual = population[i];
            }
        }
        
        return bestIndividual;
    }
    
    private int[][] initializePopulation(int numPoints) {
        int[][] population = new int[POPULATION_SIZE][numPoints];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population[i] = generateRandomTour(numPoints);
        }
        return population;
    }
    
    private int[] generateRandomTour(int numPoints) {
        int[] tour = new int[numPoints];
        for (int i = 0; i < numPoints; i++) {
            tour[i] = i;
        }
        for (int i = numPoints - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = tour[i];
            tour[i] = tour[j];
            tour[j] = temp;
        }
        return tour;
    }
    
    private double evaluateFitness(int[] tour, double[][] distances) {
        double length = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            int from = tour[i];
            int to = tour[i + 1];
            length += distances[from][to];
        }
        length += distances[tour[tour.length - 1]][tour[0]]; // Return to the starting point
        return length;
    }
    
    private int[] selectParent(int[][] population, double[] fitness) {
        // Roulette wheel selection
        double totalFitness = Arrays.stream(fitness).sum();
        double randomFitness = random.nextDouble() * totalFitness;
        double sum = 0;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            sum += fitness[i];
            if (sum >= randomFitness) {
                return population[i];
            }
        }
        return population[POPULATION_SIZE - 1]; // fallback
    }
    
    private int[] crossover(int[] parent1, int[] parent2) {
        // Partially Mapped Crossover (PMX)
        int[] child = new int[parent1.length];
        int startPos = random.nextInt(parent1.length);
        int endPos = random.nextInt(parent1.length);
        if (startPos > endPos) {
            int temp = startPos;
            startPos = endPos;
            endPos = temp;
        }
        
        // Copy genes from parent1
        for (int i = startPos; i <= endPos; i++) {
            child[i] = parent1[i];
        }
        
        // Fill in remaining genes from parent2
        for (int i = 0; i < parent2.length; i++) {
            if (i < startPos || i > endPos) {
                int gene = parent2[i];
                int index = Arrays.asList(child).indexOf(gene);
                while (index != -1 && index >= startPos && index <= endPos) {
                    gene = parent2[index];
                    index = Arrays.asList(child).indexOf(gene);
                }
                child[i] = gene;
            }
        }
        
        return child;
    }
    
    private void mutate(int[] tour) {
        // Swap mutation
        if (random.nextDouble() < MUTATION_RATE) {
            int index1 = random.nextInt(tour.length);
            int index2 = random.nextInt(tour.length);
            int temp = tour[index1];
            tour[index1] = tour[index2];
            tour[index2] = temp;
        }
    }
}
