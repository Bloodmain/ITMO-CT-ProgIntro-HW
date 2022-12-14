package expression;

import expression.*;

import java.io.IOException;

public interface CheckedExpression extends Expression, TripleExpression {
    Priority getPriority();
    String getOperatorSymbol();
}
