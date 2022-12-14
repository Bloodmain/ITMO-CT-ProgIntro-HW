package expression;

import expression.PriorityExpression;

public interface CheckedBinary extends PriorityExpression {
    boolean check(int a, int b);
}
