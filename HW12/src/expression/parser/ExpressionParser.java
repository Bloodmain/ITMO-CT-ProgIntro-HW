package expression.parser;

import expression.*;

import java.util.Map;

public final class ExpressionParser implements TripleParser {
    private static final Map<String, Integer> OPERATION_PRIORITIES = Map.of(
            "+", 5,
            "-", 5,
            "*", 10,
            "/", 10,
            "--", 10
    );
    private static final String AFTER_VARIABLES = "+-*/)" + BaseParser.END;

    public PriorityExpression parse(CharSource source) {
        return new ExpressionAnalyzer(source).parse();
    }

    @Override
    public PriorityExpression parse(String source) {
        return parse(new StringSource(source));
    }

    private static class ExpressionAnalyzer extends BaseParser {

        public ExpressionAnalyzer(CharSource source) {
            super(source);
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
                } else if (testAndConsume('-')) {
                    if (checkBounds('0', '9')) {
                        left = parseConst(true);
                    } else {
                        skipWhitespaces();
                        left = parseNegate();
                    }
                } else {
                    left = parseConst(false);
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

            if (ExpressionParser.OPERATION_PRIORITIES.get(op) <= returnOnPriority) {
                return left;
            }
            consume();
            skipWhitespaces();
            PriorityExpression right = parseExpression(ExpressionParser.OPERATION_PRIORITIES.get(op), null);
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

        private PriorityExpression parseNegate() {
            return new Negate(parseExpression(ExpressionParser.OPERATION_PRIORITIES.get("--"), null));
        }

        private PriorityExpression parseConst(boolean negative) {
            StringBuilder res = new StringBuilder();
            if (negative) {
                res.append('-');
            }
            while (checkBounds('0', '9')) {
                res.append(consume());
            }
            try {
                skipWhitespaces();
                assertNextOneOfThe(ExpressionParser.AFTER_VARIABLES);
                return new Const(Integer.parseInt(res.toString()));
            } catch (NumberFormatException e) {
                throw source.error("Expected const, but didn't find it");
            }
        }

        private PriorityExpression parseVariable() {
            char variable = consume();
            skipWhitespaces();
            assertNextOneOfThe(ExpressionParser.AFTER_VARIABLES);
            return new Variable(String.valueOf(variable));
        }

    }
}
