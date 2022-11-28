package expression;

public class Divide extends BinaryOperation {
    public Divide(PriorityExpression leftOperand, PriorityExpression rightOperand) {
        super(leftOperand, rightOperand, (a, b) -> a / b, '/',
                new Priority(5, 2, 7));
    }
}
