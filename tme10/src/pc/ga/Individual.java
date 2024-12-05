package pc.ga;
public interface Individual<T extends Individual<T>> {
    T mutate();
    T crossover(T other);
    T copy();
    double getFitness();
    void setFitness(double fitness);
}
