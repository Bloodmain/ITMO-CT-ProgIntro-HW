package expression;

import java.util.Objects;

public class Const implements PriorityExpression {
    private final static Priority priority = new Priority(10, 0,0);
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
        return Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Const that) {
            return value.equals(that.value);
        }
        return false;
    }
}
