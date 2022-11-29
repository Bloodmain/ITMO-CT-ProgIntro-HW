package expression;

public class Subtract extends BinaryOperation {
    public Subtract(PriorityExpression leftOperand, PriorityExpression rightOperand) {
        super(leftOperand, rightOperand,
                new Priority(1, 0, 2));
    }

    @Override
    public int evaluate(int x) {
        return leftOperand.evaluate(x) - rightOperand.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return leftOperand.evaluate(x,y,z) - rightOperand.evaluate(x,y,z);
    }

    @Override
    public double evaluate(double x) {
        return leftOperand.evaluate(x) - rightOperand.evaluate(x);
    }

    @Override
    public char getOperationChar() {
        return '-';
    }
}
