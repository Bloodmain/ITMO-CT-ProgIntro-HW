package expression.exceptions;

public class UnclosedParentheses extends ParseException {
    public UnclosedParentheses(String message) {
        super(message);
    }

    public UnclosedParentheses(String message, Throwable cause) {
        super(message, cause);
    }
}
