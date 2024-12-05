package pc.tsp;

public class City {
	private final int index;
	private final double x;
	private final double y;

	public City(int index, double x, double y) {
		this.index = index;
		this.x = x;
		this.y = y;
	}

	public int getIndex() {
		return index;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public String toString() {
		return "City{" + "index=" + index + ", x=" + x + ", y=" + y + '}';
	}
}
