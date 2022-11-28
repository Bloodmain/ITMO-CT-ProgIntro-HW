package expression;

public class Priority {
    private final int leftPriority;
    private final int rightPriority;
    private final int rightBound;

    public Priority(int leftPriority, int rightPriority, int rightBound) {
        this.leftPriority = leftPriority;
        this.rightPriority = rightPriority;
        this.rightBound = rightBound;
    }

    public boolean compareLeft(Priority that) {
        return that.leftPriority < this.leftPriority;
    }

    public boolean compareRight(Priority that) {
        return that.rightPriority < this.rightBound;
    }
}
