package pc.tspga;

import pc.ga.BasicEvolver;
import pc.ga.Evolver;
import pc.ga.Individual;
import pc.ga.Population;
import pc.ga.Problem;
import pc.tsp.CityMap;
import pc.tsp.TSPParser;
import pc.tsp.Tour;
import pc.tsp.ui.CityFrame;

public class TSPGAMain {
  private static final int MAXGEN = 1000;

  public static void main(String[] args) {
    try {
      if (args.length < 1) {
        System.err.println("Usage: java pc.tspga.TSPGAMain <tsp_file>");
        System.exit(1);
      }

      // Parse the .tsp file to create a CityMap
      String tspFilePath = args[0];
      CityMap cityMap = TSPParser.parse(tspFilePath);

      // Create a TSPProblem instance
      TSPProblem problem = new TSPProblem(cityMap);

      Tour optimal = TSPParser.findAndParseOptimal(tspFilePath);
      if (optimal != null) {
        System.out.println("Optimal tour length: " + optimal.computeLength(cityMap));
        CityFrame frame2 = new CityFrame(cityMap, optimal);
      }


      // Create a Population and add the initial individual
      int populationSize = 1000;
      Population<TSPIndividual> population = new Population<>();

      int nbCities = cityMap.getNumberOfCities();
      // Fill the population with random individuals
      for (int i = 0; i < populationSize; i++) {
        Tour tour = new Tour(nbCities);
        /*if (i>0)*/ tour.randomize();
        TSPIndividual individual = new TSPIndividual(tour);
        population.add(individual);
      }

      
      CityFrame frame = new CityFrame(cityMap, population.getIndividuals().get(0).getTour());

      // Create an Evolver : 10% selection, 5% mutation
      Evolver<TSPIndividual> evolver = new BasicEvolver<>(.25, .05);
      solveWithGA(problem, population, frame, evolver);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static <T extends Individual<T>> void solveWithGA(Problem<T> problem, Population<T> population, CityFrame frame,
      Evolver<T> evolver) throws InterruptedException {
    long start = System.currentTimeMillis();
    for (int i=0; i < MAXGEN ; i++) {
      // Evaluate the population
      population.evaluateAll(problem);
      
      // Evaluate the population in parallel
//   public void evaluateAllParallel(Problem<T> problem) {
//    individuals.parallelStream().forEach(individual -> {
//      double fitness = problem.evaluate(individual);
//      individual.setFitness(fitness);
//    });
//  }


      // Sort the population
      population.sort();

      Individual<T> bestIndividual = population.getIndividuals().get(0);
      // Print the best solution

      if (i%10 == 0) {
        frame.setTour(((TSPIndividual) bestIndividual).getTour());
        System.out.println("Iteration "+i+"Best tour length: " + bestIndividual.getFitness());
        // System.out.println("Iteration "+i+"Best tour: " + bestIndividual.getTour());
        frame.setPopulation((Population<TSPIndividual>) population);
      }
      
      Thread.sleep(20);

      // Evolve the population
      population = evolver.evolve(population);
    }
    long end = System.currentTimeMillis();
    System.out.println("Execution time: "+(end-start)+"ms");
    System.out.println("Best tour length: " + population.getIndividuals().get(0).getFitness());
  }
}
