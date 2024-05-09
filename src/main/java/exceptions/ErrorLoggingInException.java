package exceptions;

public class ErrorLoggingInException extends RuntimeException {
    public ErrorLoggingInException(String message) {
        super(message);
    }
}
