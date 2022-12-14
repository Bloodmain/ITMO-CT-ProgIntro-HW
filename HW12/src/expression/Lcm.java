package expression;

public class Lcm extends BinaryOperation {
    private static final Priority PRIORITY = new Priority(1,
            OperationsGroups.LOWEST_PRIORITY,
            OperationsProperties.HIGH_PRIORITY | OperationsProperties.REFLEXIVE);
    public Lcm(PriorityExpression leftOperand, PriorityExpression rightOperand) {
        super(leftOperand, rightOperand, Lcm::lcm, null);
    }

    public static int lcm(long a, long b) {
        int g = Gcd.gcd(a, b);
        return g == 0 ? 0 : (int) (a * b / g);
    }

    @Override
    public String getOperatorSymbol() {
        return "lcm";
    }

    @Override
    public Priority getPriority() {
        return PRIORITY;
    }
}
