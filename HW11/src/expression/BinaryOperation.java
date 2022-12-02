package expression;

import java.util.Objects;
import java.util.function.BinaryOperator;

public abstract class BinaryOperation implements PriorityExpression {
    protected final PriorityExpression leftOperand;
    protected final PriorityExpression rightOperand;
    private final Priority priority;
    private final BinaryOperator<Integer> operatorInt;
    private final BinaryOperator<Double> operatorDouble;
    private final char operatorSymbol;

    public BinaryOperation(final PriorityExpression leftOperand, final PriorityExpression rightOperand,
                           Priority priority, BinaryOperator<Integer> operatorInt, BinaryOperator<Double> operatorDouble,
                           char operatorSymbol) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.priority = priority;
        this.operatorInt = operatorInt;
        this.operatorDouble = operatorDouble;
        this.operatorSymbol = operatorSymbol;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public int evaluate(int x) {
        return operatorInt.apply(leftOperand.evaluate(x), rightOperand.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return operatorInt.apply(leftOperand.evaluate(x,y,z), rightOperand.evaluate(x,y,z));
    }

    @Override
    public double evaluate(double x) {
        return operatorDouble.apply(leftOperand.evaluate(x), rightOperand.evaluate(x));
    }

    @Override
    public String toString() {
        return "(" + leftOperand.toString() + ' ' + operatorSymbol + ' ' + rightOperand.toString() + ")";
    }

    private String putInBracketsIfNeeded(String expr, boolean need) {
        if (need) {
            return "(" + expr + ")";
        } else {
            return expr;
        }
    }

    @Override
    public String toMiniString() {
        return putInBracketsIfNeeded(leftOperand.toMiniString(),
                        getPriority().compareLeft(leftOperand.getPriority())) +
                ' ' + operatorSymbol + ' ' +
                putInBracketsIfNeeded(rightOperand.toMiniString(),
                        getPriority().compareRight(rightOperand.getPriority()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftOperand, rightOperand, operatorSymbol, operatorInt, operatorDouble);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BinaryOperation that) {
            return operatorSymbol == that.operatorSymbol
                    && leftOperand.equals(that.leftOperand)
                    && rightOperand.equals(that.rightOperand);
        }
        return false;
    }
}
