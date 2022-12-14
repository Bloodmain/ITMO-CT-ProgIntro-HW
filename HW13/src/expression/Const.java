package expression;

public class Const implements PriorityExpression, CheckedUnary, CheckedBinary {
    private final static Priority priority = new Priority(0,
            OperationsGroups.VARIABLES,
            0);
    private final Number value;

    public Const(final int value) {
        this.value = value;
    }

    public Const(final double value) {
        this.value = value;
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        return value.intValue();
    }
    @Override
    public int evaluate(int x) {
        return value.intValue();
    }

    @Override
    public double evaluate(double x) {
        return value.doubleValue();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String getOperatorSymbol() {
        return "";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Const that) {
            return value.equals(that.value);
        }
        return false;
    }

    @Override
    public boolean check(int a, int b) {
        return true;
    }

    @Override
    public boolean check(int a) {
        return true;
    }
}
