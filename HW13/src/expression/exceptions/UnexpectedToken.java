package expression.exceptions;

public class UnexpectedToken extends ParseException {
    public UnexpectedToken(String message) {
        super(message);
    }

    public UnexpectedToken(String message, Throwable cause) {
        super(message, cause);
    }
}
