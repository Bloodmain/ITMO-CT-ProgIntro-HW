package expression;

public class Divide extends BinaryOperation {
    public Divide(PriorityExpression leftOperand, PriorityExpression rightOperand) {
        super(leftOperand, rightOperand, new Priority(5, 2, 7),
                (a, b) -> a / b, (a, b) -> a / b, '/');
    }
}
