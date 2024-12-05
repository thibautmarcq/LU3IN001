package pc.ga;

/**
 * Interface representing an Evolver that can evolve a population.
 *
 * @param <T> The type of individuals in the population.
 */
public interface Evolver<T extends Individual<T>> {
    /**
     * Evolves the given population and returns a new evolved population.
     *
     * @param original The original population to evolve.
     * @return A new population after evolution.
     */
    Population<T> evolve(Population<T> original);
}
