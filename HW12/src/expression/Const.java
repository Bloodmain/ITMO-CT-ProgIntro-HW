package expression;

public class Const implements PriorityExpression {
    private final static Priority priority = new Priority(10, 0,0);
    private final double value;
    private final int type;

    public Const(final int value) {
        this.value = value;
        this.type = 0;
    }

    public Const(final double value) {
        this.value = value;
        this.type = 1;
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        return (int)value;
    }
    @Override
    public int evaluate(int x) {
        return (int)value;
    }

    @Override
    public double evaluate(double x) {
        return value;
    }

    @Override
    public String toString() {
        if (type==0) {
            return String.valueOf((int)value);
        } else {
            return String.valueOf(value);
        }
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Const that) {
            return value == that.value;
        }
        return false;
    }
}
