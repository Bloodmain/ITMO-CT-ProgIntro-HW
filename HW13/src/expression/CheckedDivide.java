package expression;

import java.util.function.BinaryOperator;

public class CheckedDivide extends CheckedBinaryOperation {

    public CheckedDivide(CheckedBinary leftOperand, CheckedBinary rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public BinaryOperator<Integer> getIntOperator() {
        return Divide.OPERATION_INT;
    }

    @Override
    public BinaryOperator<Double> getDoubleOperator() {
        return null;
    }

    @Override
    public Priority getPriority() {
        return Divide.PRIORITY;
    }

    @Override
    public String getOperatorSymbol() {
        return Divide.SYMBOL;
    }

    @Override
    public boolean check(int a, int b) {
        if (b == -1) {
            return a != Integer.MIN_VALUE;
        }
        return b != 0;
    }
}
