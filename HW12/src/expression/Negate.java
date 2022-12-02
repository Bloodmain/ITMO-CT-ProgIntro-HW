package expression;

public class Negate extends UnaryOperation {

    public Negate(PriorityExpression operand) {
       super(operand, new Priority(100, 0, 10), a -> -a, a -> -a, '-');
    }
}
