package pc;

public class TaillesNonConcordantesException extends Exception {
    public TaillesNonConcordantesException() {
        super("Les tailles des matrices ne concordent pas.");
    }

    public TaillesNonConcordantesException(String message) {
        super(message);
    }
}