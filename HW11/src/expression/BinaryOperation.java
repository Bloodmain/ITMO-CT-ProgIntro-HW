package expression;

import java.util.function.BinaryOperator;

public abstract class BinaryOperation implements PriorityExpression {
    private final PriorityExpression leftOperand;
    private final PriorityExpression rightOperand;
    private final BinaryOperator<Integer> operator;
    private final char operationSymbol;
    private final Priority priority;

    public BinaryOperation(final PriorityExpression leftOperand, final PriorityExpression rightOperand,
                           final BinaryOperator<Integer> operator, final char operationSymbol, Priority priority) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operator = operator;
        this.operationSymbol = operationSymbol;
        this.priority = priority;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public int evaluate(int x) {
        return operator.apply(leftOperand.evaluate(x), rightOperand.evaluate(x));
    }

    @Override
    public String toString() {
        return "(" + leftOperand.toString() + ' ' + operationSymbol + ' ' + rightOperand.toString() + ")";
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
                ' ' + operationSymbol + ' ' +
                putInBracketsIfNeeded(rightOperand.toMiniString(),
                        getPriority().compareRight(rightOperand.getPriority()));
    }

    @Override
    public int hashCode() {
        return (leftOperand.hashCode() * 31 + operationSymbol) * 31 + rightOperand.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BinaryOperation that) {
            return operationSymbol == that.operationSymbol
                    && leftOperand.equals(that.leftOperand)
                    && rightOperand.equals(that.rightOperand);
        }
        return false;
    }
}
