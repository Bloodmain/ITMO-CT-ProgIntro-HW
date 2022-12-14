package expression;

public class Gcd extends BinaryOperation {
    private static final Priority PRIORITY = new Priority(0,
            OperationsGroups.LOWEST_PRIORITY,
            OperationsProperties.HIGH_PRIORITY | OperationsProperties.REFLEXIVE);

    public Gcd(PriorityExpression leftOperand, PriorityExpression rightOperand) {
        super(leftOperand, rightOperand, Gcd::gcd, null);
    }

    public static int gcd(long a, long b) {
        return (int) Math.abs(b == 0 ? a : gcd(b, a % b));
    }

    @Override
    public String getOperatorSymbol() {
        return "gcd";
    }

    @Override
    public Priority getPriority() {
        return PRIORITY;
    }
}
