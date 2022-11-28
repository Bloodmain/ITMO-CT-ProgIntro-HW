package expression;

public class Subtract extends BinaryOperation {
    public Subtract(PriorityExpression leftOperand, PriorityExpression rightOperand) {
        super(leftOperand, rightOperand, (a, b) -> a - b, '-',
                new Priority(1, 0, 2));
    }
}
