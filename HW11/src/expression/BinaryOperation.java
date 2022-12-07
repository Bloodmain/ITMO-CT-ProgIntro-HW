package expression;

import java.util.Objects;
import java.util.function.BinaryOperator;

public abstract class BinaryOperation implements PriorityExpression {
    protected final PriorityExpression leftOperand;
    protected final PriorityExpression rightOperand;
    private final BinaryOperator<Integer> operatorInt;
    private final BinaryOperator<Double> operatorDouble;

    public BinaryOperation(final PriorityExpression leftOperand, final PriorityExpression rightOperand,
                           BinaryOperator<Integer> operatorInt, BinaryOperator<Double> operatorDouble) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operatorInt = operatorInt;
        this.operatorDouble = operatorDouble;
    }

    @Override
    public int evaluate(int x) {
        return operatorInt.apply(leftOperand.evaluate(x), rightOperand.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return operatorInt.apply(leftOperand.evaluate(x, y, z), rightOperand.evaluate(x, y, z));
    }

    @Override
    public double evaluate(double x) {
        return operatorDouble.apply(leftOperand.evaluate(x), rightOperand.evaluate(x));
    }

    @Override
    public String toString() {
        return "(" + leftOperand.toString() + ' ' + getOperatorSymbol() + ' ' + rightOperand.toString() + ")";
    }

    private String putInBracketsIfNeeded(String expr, boolean need) {
        if (need) {
            return "(" + expr + ")";
        }
        return expr;
    }

    @Override
    public String toMiniString() {
        return putInBracketsIfNeeded(leftOperand.toMiniString(),
                        getPriority().compareLeft(leftOperand.getPriority())) +
                ' ' + getOperatorSymbol() + ' ' +
                putInBracketsIfNeeded(rightOperand.toMiniString(),
                        getPriority().compareRight(rightOperand.getPriority()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftOperand, rightOperand, this.getClass(), operatorInt, operatorDouble);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BinaryOperation that) {
            return that.getClass() == this.getClass()
                    && leftOperand.equals(that.leftOperand)
                    && rightOperand.equals(that.rightOperand);
        }
        return false;
    }
}
