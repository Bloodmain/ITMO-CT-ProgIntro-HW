package expression;

public abstract class CheckedUnaryOperation extends UnaryOperation implements CheckedUnary {
    public CheckedUnaryOperation(PriorityExpression operand) {
        super(operand);
    }

    @Override
    public int evaluate(int x) {
        int a = operand.evaluate(x);
        if (!check(a)) {
            throw new ArithmeticException("Overflow for " + getOperatorSymbol() + " " + a);
        }
        return getIntOperator().apply(a);
    }

    @Override
    public int evaluate(int x, int y, int z) throws ArithmeticException {
        int a = operand.evaluate(x, y, z);
        if (!check(a)) {
            throw new ArithmeticException("Overflow for " + getOperatorSymbol() + " " + a);
        }
        return getIntOperator().apply(a);
    }
}
