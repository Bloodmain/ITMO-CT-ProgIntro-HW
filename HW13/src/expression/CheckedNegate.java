package expression;

import java.util.function.UnaryOperator;

public class CheckedNegate extends CheckedUnaryOperation {
    public CheckedNegate(PriorityExpression operand) {
        super(operand);
    }

    @Override
    public Priority getPriority() {
        return Negate.PRIORITY;
    }

    @Override
    public String getOperatorSymbol() {
        return Negate.SYMBOL;
    }

    @Override
    public UnaryOperator<Integer> getIntOperator() {
        return Negate.OPERATION_INT;
    }

    @Override
    public UnaryOperator<Double> getDoubleOperator() {
        return null;
    }

    @Override
    public boolean check(int a) {
        return a != Integer.MIN_VALUE;
    }
}
