package pc.ga;
public interface Problem<T extends Individual<T>> {
    double evaluate(T individual);
}
