package expression;

public class Multiply extends BinaryOperation {
    public Multiply(PriorityExpression leftOperand, PriorityExpression rightOperand) {
        super(leftOperand, rightOperand, new Priority(6, 2, 6),
                (a, b) -> a * b,(a, b) -> a * b, '*');
    }
}
