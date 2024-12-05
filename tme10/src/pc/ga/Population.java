package pc.ga;

import java.util.ArrayList;
import java.util.List;

public class Population<T extends Individual<T>> {
  private final List<T> individuals;

  public Population() {
    individuals = new ArrayList<>();
  }

  public Population(List<T> individuals) {
    this.individuals = new ArrayList<>(individuals);
  }

  public void add(T individual) {
    individuals.add(individual);
  }

  public void evaluateAll(Problem<T> problem) {
    for (T individual : individuals) {
      double fitness = problem.evaluate(individual);
      individual.setFitness(fitness);
    }
  }

  public List<T> getIndividuals() {
    return individuals;
  }

  public int size() {
    return individuals.size();
  }

  public void sort() {
    // increasing value of fitness ; for TSP lower is better
    individuals.sort((a, b) -> Double.compare(a.getFitness(), b.getFitness()));
  }

}
