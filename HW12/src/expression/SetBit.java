package expression;

public class SetBit extends BinaryOperation {
    private static final Priority PRIORITY = new Priority(0, 0,1);
    public SetBit(PriorityExpression leftOperand, PriorityExpression rightOperand) {
        super(leftOperand, rightOperand, (a, b) -> a | (1 << b), null);
    }

    @Override
    public String getOperatorSymbol() {
        return "set";
    }

    @Override
    public Priority getPriority() {
        return PRIORITY;
    }
}
