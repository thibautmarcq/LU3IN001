package pc.tsp.ui;


import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

import pc.ga.Population;
import pc.tsp.CityMap;
import pc.tsp.Tour;
import pc.tspga.TSPIndividual;

/**
 * A simple JFrame that contains the CityPanel.
 */
public class CityFrame extends JFrame {
  private CityPanel cityPanel;
  private PopulationPanel populationPanel;

  /**
   * Constructs a CityFrame with the given CityMap and Tour.
   *
   * @param cityMap The map containing city coordinates.
   * @param tour    The tour to display.
   */
  public CityFrame(CityMap cityMap, Tour tour) {
    setSize(800, 600);
    setTitle("TSP Visualization");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    JSplitPane pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    cityPanel = new CityPanel(cityMap, tour);
    pane.add(cityPanel);
    populationPanel = new PopulationPanel();
    pane.add(populationPanel);
    pane.setDividerLocation(400);
    setContentPane(pane);

    setLocationRelativeTo(null); // Centers the frame on the screen
    setVisible(true);
  }

  /**
   * Updates the tour displayed in the CityPanel.
   *
   * @param tour The new tour to display.
   */
  public void setTour(Tour tour) {
    cityPanel.setTour(tour);
  }

  public void setPopulation(Population<TSPIndividual> population) {
    populationPanel.setPopulation(population);
  }
}
