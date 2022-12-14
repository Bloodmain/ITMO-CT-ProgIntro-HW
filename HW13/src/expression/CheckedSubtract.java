package expression;

import java.util.function.BinaryOperator;

public class CheckedSubtract extends CheckedBinaryOperation {
    public CheckedSubtract(CheckedBinary leftOperand, CheckedBinary rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public BinaryOperator<Integer> getIntOperator() {
        return Subtract.OPERATION_INT;
    }

    @Override
    public BinaryOperator<Double> getDoubleOperator() {
        return null;
    }

    @Override
    public Priority getPriority() {
        return Subtract.PRIORITY;
    }

    @Override
    public String getOperatorSymbol() {
        return Subtract.SYMBOL;
    }

    @Override
    public boolean check(int a, int b) {
        if (b >= 0) {
            return (a >= 0 ? Integer.MAX_VALUE : a - Integer.MIN_VALUE) >= b;
        } else {
            return (a < 0 ? Integer.MIN_VALUE : a - Integer.MAX_VALUE) <= b;
        }
    }
}
