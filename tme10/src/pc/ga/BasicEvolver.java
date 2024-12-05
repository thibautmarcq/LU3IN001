package pc.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Basic implementation of the Evolver interface.
 * Configured with selection rate and mutation rate.
 *
 * @param <T> The type of individuals in the population.
 */
public class BasicEvolver<T extends Individual<T>> implements Evolver<T> {
  private final double selectionRate;
  private final double mutationRate;

  /**
   * Constructs a BasicEvolver with specified selection and mutation rates.
   *
   * @param selectionRate The fraction of the population to retain (0 < selectionRate <= 1).
   * @param mutationRate  The probability of mutation for each child (0 <= mutationRate <= 1).
   * @throws IllegalArgumentException If rates are out of bounds.
   */
  public BasicEvolver(double selectionRate, double mutationRate) {
    if (selectionRate <= 0 || selectionRate > 1) {
      throw new IllegalArgumentException("Selection rate must be in (0, 1].");
    }
    if (mutationRate < 0 || mutationRate > 1) {
      throw new IllegalArgumentException("Mutation rate must be in [0, 1].");
    }
    this.selectionRate = selectionRate;
    this.mutationRate = mutationRate;
  }

  /**
   * Evolves the given population by selecting the top individuals,
   * generating new offspring through crossover, and applying mutations.
   *
   * @param original The original population to evolve.
   * @return A new population after evolution.
   */
  @Override
  public Population<T> evolve(Population<T> original) {
    // Step 1: Sort the population in ascending order of fitness
    original.sort();

    // Step 2: Select the top fraction of the population based on selectionRate
    int selectedSize = (int) Math.ceil(original.size() * selectionRate);
    selectedSize = Math.max(selectedSize, 1); // Ensure at least one individual is selected
    List<T> selected = new ArrayList<>(original.getIndividuals().subList(0, selectedSize));

    // Step 3: Generate offspring to replenish the population
    int populationSize = original.size();
    List<T> newIndividuals = new ArrayList<>(original.size()); // Retain the selected individuals
    for (T individual : selected) {
      newIndividuals.add(individual.copy());
    }
    Random random = ThreadLocalRandom.current();
    while (newIndividuals.size() < populationSize) {
      // Randomly select two distinct parents
      int parent1Index = random.nextInt(selected.size());
      // only n-1 choices for parent2Index
      int parent2Index = random.nextInt(selected.size()-1);
      // shift top of range up by one, the whole interval
      if (parent2Index >= parent1Index) {
        parent2Index++;
      }

      T parent1 = selected.get(parent1Index);
      T parent2 = selected.get(parent2Index);

      // Perform crossover to produce a child
      T child = parent1.crossover(parent2);

      // Mutate the child based on mutationRate
      if (random.nextDouble() < mutationRate)
        child = child.mutate();

      // Add the child to the new population
      newIndividuals.add(child);
    }

    // Create and return the new evolved population
    return new Population<>(newIndividuals);
  }
}
