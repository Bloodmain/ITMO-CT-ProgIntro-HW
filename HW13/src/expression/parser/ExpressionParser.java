package expression.parser;
import expression.*;

import java.util.Map;

public final class ExpressionParser implements TripleParser {
    private static final String NEGATE = "--";
    private static final Map<String, Integer> OPERATION_PRIORITIES = Map.of(
            "+", 5,
            "-", 5,
            "*", 10,
            "/", 10,
            NEGATE, 15,
            "reverse", 15,
            "gcd", 0,
            "lcm", 0
    );

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
            assertEOF();
            return res;
        }

        private PriorityExpression parseExpression(final int returnOnPriority, PriorityExpression left) {
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
                } else if (testAndConsume('r')) {
                    assertAndConsume("everse");
                    left = parseReverse();
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
            } else if (test('*')) {
                op = "*";
            } else if (test('/')) {
                op = "/";
            } else if (test('l')) {
                op = "lcm";
            } else if (test('g')) {
                op = "gcd";
            } else {
                return left;
            }

            if (OPERATION_PRIORITIES.get(op) <= returnOnPriority) {
                return left;
            }
            assertAndConsume(op);
            skipWhitespaces();
            PriorityExpression right = parseExpression(OPERATION_PRIORITIES.get(op), null);
            PriorityExpression res = switch (op) {
                case "+" -> new Add(left, right);
                case "-" -> new Subtract(left, right);
                case "*" -> new Multiply(left, right);
                case "lcm" -> new Lcm(left, right);
                case "gcd" -> new Gcd(left, right);
                default -> new Divide(left, right);
            };

            return checkEOF() ? res : parseExpression(returnOnPriority, res);
        }

        private PriorityExpression parseReverse() {
            return new Reverse(parseExpression(OPERATION_PRIORITIES.get("reverse"), null));
        }

        private PriorityExpression parseNegate() {
            return new Negate(parseExpression(OPERATION_PRIORITIES.get(NEGATE), null));
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
                return new Const(Integer.parseInt(res.toString()));
            } catch (NumberFormatException e) {
                throw source.error("Expected const, but didn't find it");
            }
        }

        private PriorityExpression parseVariable() {
            char variable = consume();
            skipWhitespaces();
            return new Variable(String.valueOf(variable));
        }
    }
}
