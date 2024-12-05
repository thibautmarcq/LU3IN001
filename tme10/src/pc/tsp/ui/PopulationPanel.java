package pc.tsp.ui;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

import pc.ga.Population;
import pc.tspga.TSPIndividual;

public class PopulationPanel extends JPanel {
    private List<Double> fitnessValues = new ArrayList<>();
    private int numBins = 20;
    private int[] bins;
    private double minFitness;
    private double maxFitness;

    public PopulationPanel() {
        setPreferredSize(new java.awt.Dimension(800, 200));
    }

    public void setPopulation(Population<TSPIndividual> population) {
        fitnessValues.clear();
        for (TSPIndividual individual : population.getIndividuals()) {
            fitnessValues.add(individual.getFitness());
        }
        computeHistogram();
        repaint();
    }

    private void computeHistogram() {
        if (fitnessValues.isEmpty()) {
            bins = new int[numBins];
            return;
        }
        minFitness = Collections.min(fitnessValues);
        maxFitness = Collections.max(fitnessValues);
        double binSize = (maxFitness - minFitness) / numBins;
        bins = new int[numBins];
        for (double fitness : fitnessValues) {
            int bin = (int) ((fitness - minFitness) / binSize);
            if (bin >= numBins) bin = numBins -1;
            bins[bin]++;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bins == null || bins.length ==0) return;
        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        int padding = 40;
        g2.setColor(Color.WHITE);
        g2.fillRect(padding, padding, width - 2 * padding, height - 2 * padding);
        g2.setColor(Color.BLACK);
        g2.drawLine(padding, height - padding, padding, padding);
        g2.drawLine(padding, height - padding, width - padding, height - padding);
        int barWidth = (width - 2 * padding) / numBins;
        int maxBin = Arrays.stream(bins).max().orElse(1);
        for (int i =0;i<numBins;i++) {
            int barHeight = (int)(((double)bins[i]/maxBin)*(height - 2 * padding));
            g2.setColor(Color.BLUE);
            g2.fillRect(padding + i * barWidth, height - padding - barHeight, barWidth - 2, barHeight);
            g2.setColor(Color.BLACK);
            g2.drawRect(padding + i * barWidth, height - padding - barHeight, barWidth - 2, barHeight);
        }
        g2.drawString(String.format("Min: %.2f", minFitness), padding, height - padding + 15);
        g2.drawString(String.format("Max: %.2f", maxFitness), width - padding - 60, height - padding + 15);
    }
}
