package pc;

public class TaillesNonCondordantesException extends Exception {
    public TaillesNonCondordantesException() {
        super("Les tailles des matrices ne concordent pas.");
    }

    public TaillesNonCondordantesException(String message) {
        super(message);
    }
}