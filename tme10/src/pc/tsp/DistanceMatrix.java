package pc.tsp;
public class DistanceMatrix {
    private final double[][] distances;
    private final int size;

    public DistanceMatrix(CityMap townMap) {
        this.size = townMap.getNumberOfCities();
        this.distances = new double[size][];
        for (int i = 0; i < size; i++) {
            distances[i] = new double[i + 1];
            for (int j = 0; j <= i; j++) {
                double dx = townMap.getCity(i).getX() - townMap.getCity(j).getX();
                double dy = townMap.getCity(i).getY() - townMap.getCity(j).getY();
                distances[i][j] = Math.hypot(dx, dy);
            }
        }
    }

    public double getDistance(int index1, int index2) {
        int i = Math.max(index1, index2);
        int j = Math.min(index1, index2);
        return distances[i][j];
    }

    public double getDistance(City t1, City t2) {
        return getDistance(t1.getIndex(), t2.getIndex());
    }
}
