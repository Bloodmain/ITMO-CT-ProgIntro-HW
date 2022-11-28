package expression;

public class Const implements PriorityExpression {
    private final int value;
    private final static Priority priority = new Priority(10, 0,0);

    public Const(final int value) {
        this.value = value;
    }

    @Override
    public int evaluate(int x) {
        return value;
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
        return Integer.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Const that) {
            return value == that.value;
        }
        return false;
    }
}
