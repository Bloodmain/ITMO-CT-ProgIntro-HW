package expression;

public interface PriorityExpression extends Expression, TripleExpression, DoubleExpression {
    Priority getPriority();
}
