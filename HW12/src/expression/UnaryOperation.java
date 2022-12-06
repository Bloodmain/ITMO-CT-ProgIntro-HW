package expression;

import java.util.Objects;
import java.util.function.UnaryOperator;

public abstract class UnaryOperation implements PriorityExpression {
    private final PriorityExpression operand;
    private final UnaryOperator<Integer> operatorInt;
    private final UnaryOperator<Double> operatorDouble;

    public UnaryOperation(PriorityExpression operand, UnaryOperator<Integer> operatorInt,
                          UnaryOperator<Double> operatorDouble) {
        this.operand = operand;
        this.operatorInt = operatorInt;
        this.operatorDouble = operatorDouble;
    }

    @Override
    public double evaluate(double x) {
        return operatorDouble.apply(operand.evaluate(x));
    }

    @Override
    public int evaluate(int x) {
        return operatorInt.apply(operand.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return operatorInt.apply(operand.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return getOperatorSymbol() + "(" + operand.toString() + ")";
    }

    @Override
    public String toMiniString() {
        if (getPriority().compareRight(operand.getPriority())) {
            return getOperatorSymbol() + "(" + operand.toMiniString() + ")";
        }
        return getOperatorSymbol() + " " + operand.toMiniString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UnaryOperation that) {
            return this.getClass() == that.getClass() &&
                    this.operand.equals(that.operand);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operand, getOperatorSymbol(), operatorInt, operatorDouble);
    }
}
