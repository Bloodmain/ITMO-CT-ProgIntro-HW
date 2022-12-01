package expression;

public class Variable implements PriorityExpression {
    private final String name;
    private final static Priority priority = new Priority(10, 0, 0);
    public Variable(final String name) {
        this.name = name;
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        return (name.equals("x") ? x : (name.equals("y") ? y : z));
    }

    @Override
    public int evaluate(final int x) {
        return x;
    }

    @Override
    public double evaluate(final double x) {
        return x;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variable that) {
            return name.equals(that.name);
        }
        return false;
    }
}
