package pc.tsp;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Tour {
  private final int[] cityIndices;

  public Tour(int nbCities) {
    this.cityIndices = new int[nbCities];
    for (int i = 0; i < nbCities; i++) {
      cityIndices[i] = i;
    }
  }

  public void randomize () {
    int size = cityIndices.length;
    Random rnd = ThreadLocalRandom.current();
    for (int i=size; i>1; i--) {
      // Pick a random index to swap with the current index
      int randomIndex = rnd.nextInt(i);
      // Swap (non generic/int, swap in List or Arrays are generic)
      int tmp = cityIndices[i-1];
      cityIndices[i-1] = cityIndices[randomIndex];
      cityIndices[randomIndex] = tmp;
    }
  }

  public Tour(int[] cityIndices) {
    this.cityIndices = Arrays.copyOf(cityIndices, cityIndices.length);
  }

  public int[] getCityIndices() {
    return cityIndices;
  }

  public Tour copy() {
    return new Tour(cityIndices.clone());
  }

  public double computeLength(CityMap map) {
    double totalLength = 0.0;
    int numCities = cityIndices.length-1;
    for (int i = 0; i < numCities ; i++) {
      totalLength += map.getDistance(cityIndices[i], cityIndices[i + 1]);
    }
    // Return to starting city
    totalLength += map.getDistance(cityIndices[numCities], cityIndices[0]);
    return totalLength;
  }

  @Override
  public String toString() {
    return Arrays.toString(cityIndices);
  }
}
