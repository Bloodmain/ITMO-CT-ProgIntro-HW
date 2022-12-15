package expression.exceptions;

public class NoEOFException extends UnexpectedToken {
    public NoEOFException(String message) {
        super(message);
    }

    public NoEOFException(String message, Throwable cause) {
        super(message, cause);
    }
}
