package expression;

public class BitCount extends UnaryOperation {
    private static final Priority PRIORITY = new Priority(100, 0, 10);

    public BitCount(PriorityExpression operand) {
        super(operand, Integer::bitCount, null);
    }

    @Override
    public Priority getPriority() {
        return PRIORITY;
    }

    @Override
    public String getOperatorSymbol() {
        return "count";
    }
}
