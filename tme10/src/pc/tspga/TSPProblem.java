package pc.tspga;

import pc.ga.Problem;
import pc.tsp.CityMap;

public class TSPProblem implements Problem<TSPIndividual> {
    private final CityMap cityMap;

    public TSPProblem(CityMap cityMap) {
        this.cityMap = cityMap;
    }

    @Override
    public double evaluate(TSPIndividual individual) {
        // The fitness is the total length of the tour
        double length = individual.getTour().computeLength(cityMap);
        individual.setFitness(length); // Store the fitness in the individual
        return length;
    }

    public CityMap getCityMap() {
        return cityMap;
    }
}
