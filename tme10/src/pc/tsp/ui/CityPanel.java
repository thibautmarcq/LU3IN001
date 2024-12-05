package pc.tsp.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import pc.tsp.City;
import pc.tsp.CityMap;
import pc.tsp.Tour;

/**
 * A JPanel that graphically displays the cities and the tour.
 */
public class CityPanel extends JPanel {
    private final CityMap cityMap;
    private Tour tour;

    // Scaling factors and margins
    private double minX, minY, maxX, maxY;
    private double marginX, marginY;

    /**
     * Constructs a CityPanel with the given CityMap and Tour.
     *
     * @param cityMap The map containing city coordinates.
     * @param tour    The tour to display.
     */
    public CityPanel(CityMap cityMap, Tour tour) {
        this.cityMap = cityMap;
        this.tour = tour;
        computeBounds();
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 600));
    }

    /**
     * Updates the tour and repaints the panel.
     *
     * @param tour The new tour to display.
     */
    public void setTour(Tour tour) {
        this.tour = tour;
        repaint();
    }

    /**
     * Computes the minimum and maximum X and Y coordinates with 5% margins.
     */
    private void computeBounds() {
        minX = Double.MAX_VALUE;
        minY = Double.MAX_VALUE;
        maxX = Double.MIN_VALUE;
        maxY = Double.MIN_VALUE;

        for (int i = 0; i < cityMap.getNumberOfCities(); i++) {
            City city = cityMap.getCity(i);
            double x = city.getX();
            double y = city.getY();
            if (x < minX) {
              minX = x;
            }
            if (x > maxX) {
              maxX = x;
            }
            if (y < minY) {
              minY = y;
            }
            if (y > maxY) {
              maxY = y;
            }
        }

        double rangeX = maxX - minX;
        double rangeY = maxY - minY;
        marginX = rangeX * 0.05;
        marginY = rangeY * 0.05;

        minX -= marginX;
        maxX += marginX;
        minY -= marginY;
        maxY += marginY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (cityMap == null) {
          return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(2));

        // Draw cities
        for (int i = 0; i < cityMap.getNumberOfCities(); i++) {
            City city = cityMap.getCity(i);
            int x = scaleX(city.getX());
            int y = scaleY(city.getY());
            g2.fillOval(x - 5, y - 5, 10, 10);
        }

        // Draw tour
        if (tour != null) {
            g2.setColor(Color.BLUE);
            int[] indices = tour.getCityIndices();
            for (int i = 0; i < indices.length - 1; i++) {
                City from = cityMap.getCity(indices[i]);
                City to = cityMap.getCity(indices[i + 1]);
                g2.drawLine(scaleX(from.getX()), scaleY(from.getY()), scaleX(to.getX()), scaleY(to.getY()));
            }
            // Connect last city back to the first
            City last = cityMap.getCity(indices[indices.length - 1]);
            City first = cityMap.getCity(indices[0]);
            g2.drawLine(scaleX(last.getX()), scaleY(last.getY()), scaleX(first.getX()), scaleY(first.getY()));
        }
    }

    /**
     * Scales the X coordinate to fit the panel width.
     *
     * @param x The original X coordinate.
     * @return The scaled X coordinate.
     */
    private int scaleX(double x) {
        double panelWidth = getWidth();
        return (int) ((x - minX) / (maxX - minX) * panelWidth);
    }

    /**
     * Scales the Y coordinate to fit the panel height.
     *
     * @param y The original Y coordinate.
     * @return The scaled Y coordinate.
     */
    private int scaleY(double y) {
        double panelHeight = getHeight();
        // Invert Y-axis to match typical graphical coordinates
        return (int) ((maxY - y) / (maxY - minY) * panelHeight);
    }
}
