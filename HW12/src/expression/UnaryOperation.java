package expression;

import java.util.function.UnaryOperator;

public class UnaryOperation implements PriorityExpression {
    private final PriorityExpression operand;
    private final UnaryOperator<Integer> operatorInt;
    private final UnaryOperator<Double> operatorDouble;
    private final char operatorSymbol;
    private final Priority priority;

    public UnaryOperation(PriorityExpression operand, Priority priority, UnaryOperator<Integer> operatorInt,
                          UnaryOperator<Double> operatorDouble, char operatorSymbol) {
        this.operand = operand;
        this.operatorInt = operatorInt;
        this.operatorDouble = operatorDouble;
        this.operatorSymbol = operatorSymbol;
        this.priority = priority;
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
    public Priority getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return operatorSymbol + "(" + operand.toString() + ")";
    }

    @Override
    public String toMiniString() {
        if (priority.compareRight(operand.getPriority())) {
            return operatorSymbol + "(" + operand.toMiniString() + ")";
        }
        return operatorSymbol + " " + operand.toMiniString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UnaryOperation that) {
            return this.operatorSymbol == that.operatorSymbol &&
                    this.operand.equals(that.operand);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
