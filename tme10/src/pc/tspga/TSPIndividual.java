package pc.tspga;

import java.util.Random;

import pc.ga.Individual;
import pc.tsp.Tour;

public class TSPIndividual implements Individual<TSPIndividual> {
  private final Tour tour;
  private double fitness;

  public TSPIndividual(Tour tour) {
    this.tour = tour;
  }

  public Tour getTour() {
    return tour;
  }

  @Override
  public TSPIndividual mutate() {
    Random random = new Random();
    Tour newTour = tour.copy();
    int[] cityIndices = newTour.getCityIndices();
    // Apply inversion mutation (reverse a subsequence)
    int i = random.nextInt(cityIndices.length);
    int j = random.nextInt(cityIndices.length);
    int start = Math.min(i, j);
    int end = Math.max(i, j);
    while (start < end) {
      int temp = cityIndices[start];
      cityIndices[start] = cityIndices[end];
      cityIndices[end] = temp;
      start++;
      end--;
    }
    return new TSPIndividual(newTour);
  }

  @Override
  public TSPIndividual crossover(TSPIndividual other) {
    // Implement Order Crossover (OX) with efficient city inclusion check
    Random random = new Random();
    int size = tour.getCityIndices().length;
    int[] parent1Tour = this.tour.getCityIndices();
    int[] parent2Tour = other.tour.getCityIndices();
    int[] childIndices = new int[size];

    int startPos = random.nextInt(size);
    int endPos = random.nextInt(size);

    int start = Math.min(startPos, endPos);
    int end = Math.max(startPos, endPos);

    // Initialize seen array to track included cities
    boolean[] seen = new boolean[size];

    // Copy a subset from parent1 to child
    for (int i = start; i <= end; i++) {
      int cityIndex = parent1Tour[i];
      childIndices[i] = cityIndex;
      seen[cityIndex] = true;
    }

    // Fill in the remaining positions with cities from parent2
    int currentPos = (end + 1) % size;
    for (int i = 0; i < size; i++) {
      int parent2Pos = (end + 1 + i) % size;
      int cityIndex = parent2Tour[parent2Pos];
      if (!seen[cityIndex]) {
        childIndices[currentPos] = cityIndex;
        seen[cityIndex] = true;
        currentPos = (currentPos + 1) % size;
        if (currentPos == start) {
          break; // Completed filling the child
        }
      }
    }

    // Create a new Tour and TSPIndividual
    Tour childTour = new Tour(childIndices);
    return new TSPIndividual(childTour);
  }

  @Override
  public double getFitness() {
    return fitness;
  }

  @Override
  public void setFitness(double fitness) {
    this.fitness = fitness;
  }

  @Override
  public TSPIndividual copy() {
    TSPIndividual copy = new TSPIndividual(tour.copy());
    copy.setFitness(fitness);
    return copy;
  }

}
