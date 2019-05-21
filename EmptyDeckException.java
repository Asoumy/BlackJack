public class EmptyDeckException extends Exception {
    public EmptyDeckException(String message) {
        super(message);
    }

    public EmptyDeckException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
