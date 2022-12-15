package expression.exceptions;

public class UnexpectedEOF extends UnexpectedToken {
    public UnexpectedEOF(String message) {
        super(message);
    }

    public UnexpectedEOF(String message, Throwable cause) {
        super(message, cause);
    }
}
