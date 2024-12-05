package pc.ga;

public class GASolver {
  public static 
  <T extends Individual<T>> 
  T solveWithGA(Problem<T> problem, Population<T> population, Evolver<T> evolver, int maxGen) {
    long start = System.currentTimeMillis();
    for (int i=0; i < maxGen ; i++) {
      // Evaluate the population
      population.evaluateAll(problem);

      // Sort the population
      population.sort();

      if (i % 10 == 0) {
        T bestIndividual = population.getIndividuals().get(0);
        // Print the best solution
        System.out.println("Generation " + i + ": " + bestIndividual.getFitness());
      }

      // Evolve the population
      population = evolver.evolve(population);
    }
    long end = System.currentTimeMillis();
    // la dernière génération n'est pas évaluée
    population.evaluateAll(problem);
    population.sort();

    System.out.println("Execution time: "+(end-start)+"ms");
    T bestIndividual = population.getIndividuals().get(0);
    System.out.println("Best tour length: " + bestIndividual.getFitness());

    return bestIndividual;
  }

}
