package expression;

public abstract class CheckedBinaryOperation extends BinaryOperation implements CheckedBinary {
    public CheckedBinaryOperation(CheckedBinary leftOperand, CheckedBinary rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public int evaluate(int x) {
        int a1 = leftOperand.evaluate(x);
        int a2 = rightOperand.evaluate(x);
        if (!check(a1, a2)) {
            throw new ArithmeticException("Overflow for " + a1 + " " + getOperatorSymbol() + " " + a2);
        }
        return getIntOperator().apply(a1, a2);
    }

    @Override
    public int evaluate(int x, int y, int z)  {
        int a1 = leftOperand.evaluate(x, y, z);
        int a2 = rightOperand.evaluate(x, y, z);
        if (!check(a1, a2)) {
            throw new ArithmeticException("Overflow for " + a1 + " " + getOperatorSymbol() + " " + a2);
        }
        return getIntOperator().apply(a1, a2);
    }
}
