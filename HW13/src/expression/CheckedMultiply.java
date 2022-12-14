package expression;

import java.util.function.BinaryOperator;

public class CheckedMultiply extends CheckedBinaryOperation {

    public CheckedMultiply(CheckedBinary leftOperand, CheckedBinary rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public BinaryOperator<Integer> getIntOperator() {
        return Multiply.OPERATION_INT;
    }

    @Override
    public BinaryOperator<Double> getDoubleOperator() {
        return null;
    }

    @Override
    public Priority getPriority() {
        return Multiply.PRIORITY;
    }

    @Override
    public String getOperatorSymbol() {
        return Multiply.SYMBOL;
    }

    @Override
    public boolean check(int a, int b) {
        if (a == 0 || b == 0) {
            return true;
        }
        if (a == -1) {
            return b != Integer.MIN_VALUE;
        }
        if (a > 0 && b > 0 || a < 0 && b < 0) {
            return a > 0 ? b <= Integer.MAX_VALUE / a : b >= Integer.MAX_VALUE / a;
        } else {
            return a > 0 ? b >= Integer.MIN_VALUE / a : b <= Integer.MIN_VALUE / a;
        }
    }
}
