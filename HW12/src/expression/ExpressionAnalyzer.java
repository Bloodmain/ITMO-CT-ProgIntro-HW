package expression;

import java.util.Map;

public final class ExpressionAnalyzer {
    private ExpressionAnalyzer() {}
    private static final Map<String, Integer> OPERATION_PRIORITIES = Map.of(
            "+", 5,
            "-", 5,
            "*", 10,
            "/", 10
    );
    private static final String AFTER_VARIABLES = " \t\n+-*/)" + BaseParser.END;

    public static PriorityExpression parse(CharSource source) {
        return new ExpressionParser(source).parse();
    }

    public static PriorityExpression parse(String source) {
        return parse(new StringSource(source));
    }

    private static class ExpressionParser extends BaseParser {

        public ExpressionParser(CharSource source) {
            super(source);
        }

        private void skipWhitespaces() {
            while (testAndConsume(' ') || testAndConsume('\t') || testAndConsume('\n')) {}
        }

        private PriorityExpression parse() {
            PriorityExpression res = parseExpression(-1, null);
            asserEOF();
            return res;
        }

        private PriorityExpression parseExpression(int returnOnPriority, PriorityExpression left) {
            skipWhitespaces();
            if (left == null) {
                if (testAndConsume('(')) {
                    left = parseExpression(-1, null);
                    assertNextEquals(')');
                    consume();
                } else if (test('x') || test('y') || test('z')) {
                    left = parseVariable();
                } else {
                    left = parseConst();
                }
            }

            skipWhitespaces();
            String op;
            if (test('+')) {
                op = "+";
            } else if (test('-')) {
                op = "-";
            } else if (test("*")) {
                op = "*";
            } else if (test('/')) {
                op = "/";
            } else {
                return left;
            }

            if (ExpressionAnalyzer.OPERATION_PRIORITIES.get(op) <= returnOnPriority) {
                return left;
            }
            consume();
            skipWhitespaces();
            PriorityExpression right = parseExpression(ExpressionAnalyzer.OPERATION_PRIORITIES.get(op), null);
            PriorityExpression res;

            switch (op) {
                case "+" -> {
                   res = new Add(left, right);
                }
                case "-" -> {
                    res = new Subtract(left, right);
                }
                case "*" -> {
                    res = new Multiply(left, right);
                }
                default -> {
                    res = new Divide(left, right);
                }
            }

            if (checkEOF()) {
                return res;
            } else {
                return parseExpression(returnOnPriority, res);
            }
        }

        private PriorityExpression parseConst() {
            StringBuilder res = new StringBuilder();
            if (testAndConsume('-')) {
                res.append('-');
            }
            while (checkBounds('0', '9')) {
                res.append(consume());
            }
            try {
                assertNextOneOfThe(ExpressionAnalyzer.AFTER_VARIABLES);
                return new Const(Integer.parseInt(res.toString()));
            } catch (NumberFormatException e) {
                throw source.error("Expected const, but didn't find it");
            }
        }

        private PriorityExpression parseVariable() {
            char variable = consume();
            assertNextOneOfThe(ExpressionAnalyzer.AFTER_VARIABLES);
            return new Variable(String.valueOf(variable));
        }

    }
}
