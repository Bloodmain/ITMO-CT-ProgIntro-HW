package expression;

public class Negate extends UnaryOperation {
    private static final Priority PRIORITY = new Priority(100, 0, 10);

    public Negate(PriorityExpression operand) {
       super(operand, a -> -a, a -> -a);
    }

    @Override
    public Priority getPriority() {
        return PRIORITY;
    }

    @Override
    public String getOperatorSymbol() {
        return "-";
    }
}
