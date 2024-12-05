package pc.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Simulated Annealing (recuit simulé) Evolver implementation.
 *
 * @param <T> The type of individuals in the population.
 */
public class SimulatedAnnealing<T extends Individual<T>> implements Evolver<T> {
    private final Problem<T> problem;
    private final double initialTemperature;
    private final double coolingRate;
    private final int iterations;

    /**
     * Constructs a RecuitSimule evolver with specified parameters.
     *
     * @param problem            The problem to solve.
     * @param initialTemperature The starting temperature for annealing.
     * @param coolingRate        The rate at which the temperature decreases.
     * @param iterations         The number of iterations to perform.
     */
    public SimulatedAnnealing(Problem<T> problem, double initialTemperature, double coolingRate, int iterations) {
        if (initialTemperature <= 0) {
            throw new IllegalArgumentException("Initial temperature must be positive.");
        }
        if (coolingRate <= 0 || coolingRate >= 1) {
            throw new IllegalArgumentException("Cooling rate must be between 0 and 1.");
        }
        if (iterations <= 0) {
            throw new IllegalArgumentException("Number of iterations must be positive.");
        }
        this.problem = problem;
        this.initialTemperature = initialTemperature;
        this.coolingRate = coolingRate;
        this.iterations = iterations;
    }

    @Override
    public Population<T> evolve(Population<T> original) {
        if (original.size() != 1) {
            throw new IllegalArgumentException("RecuitSimule Evolver expects a population size of one.");
        }

        // Initialize current and best individuals
        T current = original.getIndividuals().get(0).copy();
        double currentFitness = problem.evaluate(current);
        T best = current.copy();
        double bestFitness = currentFitness;

        double temperature = initialTemperature;
        Random random = ThreadLocalRandom.current();

        for (int i = 0; i < iterations; i++) {
            // Generate a candidate solution via mutation
            T candidate = current.mutate(); 
            double candidateFitness = problem.evaluate(candidate);
            double delta = candidateFitness - currentFitness;

            // Decide whether to accept the candidate
            if (delta < 0 || random.nextDouble() < Math.exp(-delta / temperature)) {
                current = candidate.copy();
                currentFitness = candidateFitness;

                // Update the best solution found
                if (currentFitness < bestFitness) {
                    best = current.copy();
                    bestFitness = currentFitness;
                }
            }

            // Cool down the temperature
            temperature *= coolingRate;
            if (temperature < 1e-10) {
                break; // Temperature has cooled sufficiently
            }
        }

        // Create a new population with the best individual
        List<T> newIndividuals = new ArrayList<>();
        newIndividuals.add(best);
        return new Population<>(newIndividuals);
    }
}
