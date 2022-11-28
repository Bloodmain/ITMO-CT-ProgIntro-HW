package expression;

public class Multiply extends BinaryOperation {
    public Multiply(PriorityExpression leftOperand, PriorityExpression rightOperand) {
        super(leftOperand, rightOperand, (a, b) -> a * b, '*',
                new Priority(6, 2, 6));
    }
}
