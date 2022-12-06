package expression;

public class Add extends BinaryOperation {
    private static final Priority PRIORITY = new Priority(0, 0,0);
    public Add(PriorityExpression leftOperand, PriorityExpression rightOperand) {
        super(leftOperand, rightOperand, (a, b) -> a + b, (a, b) -> a + b);
    }

    @Override
    public String getOperatorSymbol() {
        return "+";
    }

    @Override
    public Priority getPriority() {
        return PRIORITY;
    }
}
