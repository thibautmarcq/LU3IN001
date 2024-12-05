package pc.tsp;
import java.util.Arrays;

public class CityMap {
  private final City[] cities;
  private final DistanceMatrix distanceMatrix;

  public CityMap(City[] cities) {
    // Copy the array to ensure immutability
    this.cities = Arrays.copyOf(cities, cities.length);

    // Initialize the distance matrix
    this.distanceMatrix = new DistanceMatrix(this);
  }

  public City[] getCities() {
    return cities;
  }

  public City getCity(int index) {
    return cities[index];
  }

  public int getNumberOfCities() {
    return cities.length;
  }

  public double getDistance(int city1, int city2) {
    return distanceMatrix.getDistance(city1, city2);
  }
}
