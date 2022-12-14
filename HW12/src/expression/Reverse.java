package expression;

public class Reverse extends UnaryOperation {
    private static final Priority PRIORITY = new Priority(1,
            OperationsGroups.UNARY,
            OperationsProperties.REFLEXIVE | OperationsProperties.ASSOCIATIVE);

    public Reverse(PriorityExpression operand) {
        super(operand, Reverse::reverse, null);
    }

    public static int reverse(long a) {
        boolean negative = false;
        int res = 0;
        if (a < 0) {
            a = -a;
            negative = true;
        }
        while (a > 0) {
            int digit = (int) (a % 10);
            a /= 10;
            res = res * 10 + digit;
        }
        return negative ? -res : res;
    }

    @Override
    public Priority getPriority() {
        return PRIORITY;
    }

    @Override
    public String getOperatorSymbol() {
        return "reverse";
    }
}
