package expression;

public class Multiply extends BinaryOperation {
    private static final Priority PRIORITY = new Priority(
            0,
            OperationsGroups.MULTIPLICATIVE,
            OperationsProperties.REFLEXIVE | OperationsProperties.ASSOCIATIVE);
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
