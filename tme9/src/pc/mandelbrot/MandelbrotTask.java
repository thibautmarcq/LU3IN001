package pc.mandelbrot;

import java.util.concurrent.RecursiveAction;



public class MandelbrotTask extends RecursiveAction {
	private static final int THRESHOLD = 5000;
	private final BoundingBox boundingBox;
	private int maxIterations;
	private int[] imageBuffer;
	private int lo;
	private int hi;

	public MandelbrotTask(BoundingBox boundingBox, int maxIterations, int[] imageBuffer, int lo, int hi) {
		this.boundingBox = boundingBox;
		this.maxIterations = maxIterations;
		this.imageBuffer = imageBuffer;
		this.lo = lo;
		this.hi = hi;
	}

	@Override
	protected void compute() {
		int height = hi - lo;
		int width = boundingBox.width;
		int numPixels = height * width;

		if (numPixels <= THRESHOLD) {
			for (int py = lo; py < hi; py++) {
				for (int px = 0; px < width; px++) {
					int color = MandelbrotCalculator.computePixelColor(boundingBox, maxIterations, px, py);
					imageBuffer[py * width + px] = color;
				}
			}
		} else {
			int midY = lo + height / 2;
			MandelbrotTask task1 = new MandelbrotTask(boundingBox, maxIterations, imageBuffer, lo, midY);
			MandelbrotTask task2 = new MandelbrotTask(boundingBox, maxIterations, imageBuffer, midY, hi);
			invokeAll(task1, task2);
		}
	}
}