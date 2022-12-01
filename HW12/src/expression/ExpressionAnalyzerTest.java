package expression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionAnalyzerTest {
    public void assertInvalid(String exprToParse) {
        try {
            PriorityExpression res = ExpressionAnalyzer.parse(exprToParse);
            fail("Expected to fail on '" + exprToParse + "' but found '" + res + "'");
        } catch (RuntimeException ignored) {
        }
    }

    @Test
    public void testOneOperator() {
        assertEquals(new Add(new Const(1), new Const(2)),
                ExpressionAnalyzer.parse("1+2"));
        assertEquals(new Multiply(new Const(1), new Const(2)),
                ExpressionAnalyzer.parse("1 *         2"));
        assertEquals(new Divide(new Const(1), new Const(2)),
                ExpressionAnalyzer.parse("        1 / 2"));
        assertEquals(new Subtract(new Const(1), new Const(2)),
                ExpressionAnalyzer.parse("      1  -  2     "));
    }

    @Test
    public void testTwoOperators() {
        assertEquals(new Multiply(new Add(new Const(1), new Const(2)), new Const(10)),
                ExpressionAnalyzer.parse("(1 + 2) * 10"));
        assertEquals(new Divide(new Const(2), new Add(new Const(3), new Const(5))),
                ExpressionAnalyzer.parse("2 / (3 + 5)"));
    }

    @Test
    public void testNestedBrackets() {
        assertEquals(new Divide(new Multiply(new Add(new Const(1), new Divide(new Const(2),
                        new Multiply(new Const(3), new Const(3)))),
                        new Const(3)), new Subtract(new Const(7), new Const(5))),
                ExpressionAnalyzer.parse("((1+(2 / (3*3)))*3) / (7-5)"));
    }

    @Test
    public void testManyWithSamePriorities() {
        assertEquals(new Add(new Add(new Const(1), new Const(2)), new Const(3)),
                ExpressionAnalyzer.parse("1 + 2 + 3"));
        assertEquals(new Divide(new Divide(new Const(1), new Const(2)), new Const(3)),
                ExpressionAnalyzer.parse("1 / 2 / 3"));
    }

    @Test
    public void testVariables() {
        assertEquals(new Variable("z"),
                ExpressionAnalyzer.parse("z"));
        assertEquals(new Add(new Variable("x"), new Variable("x")),
                ExpressionAnalyzer.parse("x + x"));
        assertEquals(new Divide(new Multiply(new Add(new Variable("y"), new Divide(new Const(2),
                        new Multiply(new Const(3), new Const(3)))),
                        new Variable("z")), new Subtract(new Variable("x"), new Const(5))),
                ExpressionAnalyzer.parse("((y+(2 / (3*3)))*z) / (x-5)"));
    }

    @Test
    public void testPriorities() {
        assertEquals(new Add(new Multiply(new Const(1), new Const(2)), new Const(3)),
                ExpressionAnalyzer.parse("1 * 2 + 3"));
        assertEquals(new Add(new Const(1), new Multiply(new Const(2), new Const(3))),
                ExpressionAnalyzer.parse("1 + 2 * 3"));
        assertEquals(new Subtract(new Add(new Const(1), new Divide(new Multiply(new Const(2), new Const(3)),
                        new Const(5))), new Const(3)),
                ExpressionAnalyzer.parse("1 + 2 * 3 / 5 - 3"));
    }

    @Test
    public void testNestedPriorities() {
        assertEquals(new Divide(new Multiply(new Add(new Const(1), new Divide(new Const(2),
                        new Add(new Add(new Const(1), new Multiply(new Const(3), new Const(3))),
                                new Const(1)))),
                        new Const(3)), new Add(new Subtract(new Const(7),
                        new Divide(new Multiply(new Const(5), new Const(10)),
                                new Const(5))), new Const(3))),
                ExpressionAnalyzer.parse("((1+(2/(1+3*3+1)))*3)/(7-5*10/5+3)"));
    }

    @Test
    public void testNegativeNumbers() {
        assertEquals(new Add(new Const(-1), new Const(2)),
                ExpressionAnalyzer.parse("-1+2"));

        assertEquals(new Divide(new Multiply(new Add(new Const(1), new Divide(new Const(2),
                        new Add(new Add(new Const(-1), new Multiply(new Const(3), new Const(3))),
                                new Const(1)))),
                        new Const(-3)), new Add(new Subtract(new Const(7),
                        new Divide(new Multiply(new Const(5), new Const(10)),
                                new Const(5))), new Const(3))),
                ExpressionAnalyzer.parse("((1+(2/(-1+3*3+1)))*-3)/(7-5*10/5+3)"));
    }

    @Test
    public void testDubininNumbers() {
        assertEquals(new Add(new Subtract(new Add(new Subtract(new  Add(new Const(-1), new Const(2)), new Const(3)),
                new Const(1)),
                new Divide(new Const(3), new Const(10))),
                new Const(15)), ExpressionAnalyzer.parse("-1+2-3+1-3/10 + 15"));
        assertInvalid("rzg jkaejk");
        assertInvalid("12 + 7*xy");
    }

    @Test
    public void testInvalid() {
        assertInvalid("3x");
        assertInvalid("3 2");
        assertInvalid("(3) (2)");
        assertInvalid("xy");
        assertInvalid("-");
        assertInvalid("+");
        assertInvalid("--3");
        assertInvalid("-3 3");
        assertInvalid("+3");
        assertInvalid("+3+1");
        assertInvalid("*2");
    }
}
