package expression;

public class Multiply extends BinaryOperation {
    private static final Priority PRIORITY = new Priority(6, 2, 6);
    public Multiply(PriorityExpression leftOperand, PriorityExpression rightOperand) {
        super(leftOperand, rightOperand, (a, b) -> a * b,(a, b) -> a * b);
    }

    @Override
    public String getOperatorSymbol() {
        return "*";
    }

    @Override
    public Priority getPriority() {
        return PRIORITY;
    }
}
