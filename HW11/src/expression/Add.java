package expression;

public class Add extends BinaryOperation {
    public Add(PriorityExpression leftOperand, PriorityExpression rightOperand) {
        super(leftOperand, rightOperand, (a, b) -> a + b, '+',
                new Priority(0, 0,0));
    }
}
