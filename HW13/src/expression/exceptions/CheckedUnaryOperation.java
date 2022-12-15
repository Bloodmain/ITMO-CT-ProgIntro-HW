package expression.exceptions;

import expression.UnaryOperation;

public abstract class CheckedUnaryOperation extends UnaryOperation implements CheckedExpression {
    public CheckedUnaryOperation(CheckedExpression operand) {
        super(operand);
    }

    @Override
    public int evaluate(int x) {
        int a = operand.evaluate(x);
        if (check(a) == CheckResult.OVERFLOW) {
            throw new OverflowException("Overflow for " + getOperatorSymbol() + " " + a);
        }
        return getIntOperator().apply(a);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int a = operand.evaluate(x, y, z);
        if (check(a) == CheckResult.OVERFLOW) {
            throw new OverflowException("Overflow for " + getOperatorSymbol() + " " + a);
        }
        return getIntOperator().apply(a);
    }
}
