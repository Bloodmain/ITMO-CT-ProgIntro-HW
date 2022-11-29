package expression;

public class Divide extends BinaryOperation {
    public Divide(PriorityExpression leftOperand, PriorityExpression rightOperand) {
        super(leftOperand, rightOperand,
                new Priority(5, 2, 7));
    }

    @Override
    public int evaluate(int x) {
        return leftOperand.evaluate(x) / rightOperand.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return leftOperand.evaluate(x,y,z) / rightOperand.evaluate(x,y,z);
    }

    @Override
    public double evaluate(double x) {
        return leftOperand.evaluate(x) / rightOperand.evaluate(x);
    }

    @Override
    public char getOperationChar() {
        return '/';
    }
}
