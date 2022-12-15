package expression.exceptions;

public class IncorrectConstantException extends ParseException {

    public IncorrectConstantException(String message) {
        super(message);
    }

    public IncorrectConstantException(String message, Throwable cause) {
        super(message, cause);
    }
}
