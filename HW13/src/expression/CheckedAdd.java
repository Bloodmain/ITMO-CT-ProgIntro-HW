package expression;

import java.util.function.BinaryOperator;

public class CheckedAdd extends CheckedBinaryOperation {

    public CheckedAdd(CheckedBinary leftOperand, CheckedBinary rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    public Priority getPriority() {
        return Add.PRIORITY;
    }

    @Override
    public String getOperatorSymbol() {
        return Add.SYMBOL;
    }

    @Override
    public boolean check(int a, int b) {
        if (b >= 0) {
            return (a < 0 ? Integer.MAX_VALUE : Integer.MAX_VALUE - a) >= b;
        } else {
            return (a > 0 ? Integer.MIN_VALUE : Integer.MIN_VALUE - a) <= b;
        }
    }

    @Override
    public BinaryOperator<Integer> getIntOperator() {
        return Add.OPERATION_INT;
    }

    @Override
    public BinaryOperator<Double> getDoubleOperator() {
        return null;
    }
}
