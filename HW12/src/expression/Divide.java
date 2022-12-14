package expression;

public class Divide extends BinaryOperation {
    private static final Priority PRIORITY = new Priority(1,
            OperationsGroups.MULTIPLICATIVE,
            OperationsProperties.HIGH_PRIORITY);

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
