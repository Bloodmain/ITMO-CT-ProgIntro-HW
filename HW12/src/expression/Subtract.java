package expression;

public class Subtract extends BinaryOperation {
    private static final Priority PRIORITY = new Priority(1, 0, 2);
    public Subtract(PriorityExpression leftOperand, PriorityExpression rightOperand) {
        super(leftOperand, rightOperand, (a, b) -> a - b, (a, b) -> a - b);
    }

    @Override
    public String getOperatorSymbol() {
        return "-";
    }

    @Override
    public Priority getPriority() {
        return PRIORITY;
    }
}
