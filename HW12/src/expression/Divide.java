package expression;

public class Divide extends BinaryOperation {
    private static final Priority PRIORITY = new Priority(6, 3, 8);

    public Divide(PriorityExpression leftOperand, PriorityExpression rightOperand) {
        super(leftOperand, rightOperand, (a, b) -> a / b, (a, b) -> a / b);
    }

    @Override
    public String getOperatorSymbol() {
        return "/";
    }

    @Override
    public Priority getPriority() {
        return PRIORITY;
    }
}
