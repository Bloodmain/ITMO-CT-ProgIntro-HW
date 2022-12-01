package expression;

public class Add extends BinaryOperation {
    public Add(PriorityExpression leftOperand, PriorityExpression rightOperand) {
        super(leftOperand, rightOperand, new Priority(0, 0,0),
                (a, b) -> a + b, (a, b) -> a + b, '+');
    }
}
