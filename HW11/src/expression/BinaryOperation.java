package expression;

public abstract class BinaryOperation implements PriorityExpression {
    protected final PriorityExpression leftOperand;
    protected final PriorityExpression rightOperand;
    private final Priority priority;

    public BinaryOperation(final PriorityExpression leftOperand, final PriorityExpression rightOperand,
                           Priority priority) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.priority = priority;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "(" + leftOperand.toString() + ' ' + getOperationChar() + ' ' + rightOperand.toString() + ")";
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
                ' ' + getOperationChar() + ' ' +
                putInBracketsIfNeeded(rightOperand.toMiniString(),
                        getPriority().compareRight(rightOperand.getPriority()));
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BinaryOperation that) {
            return getOperationChar() == that.getOperationChar()
                    && leftOperand.equals(that.leftOperand)
                    && rightOperand.equals(that.rightOperand);
        }
        return false;
    }
}
