package expression;

public class Priority {
    private final int priority;
    private final int leftBound;
    private final int rightBound;

    public Priority(int priority, int leftBound, int rightBound) {
        this.priority = priority;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    public boolean compareLeft(Priority that) {
        return that.priority < this.leftBound;
    }

    public boolean compareRight(Priority that) {
        return that.priority < this.rightBound;
    }
}
