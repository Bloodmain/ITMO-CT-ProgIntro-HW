package expression.parser;

import expression.*;

import java.util.Map;
import java.util.Set;

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

    private static final Set<String> AVAILABLE_VARIABLES = Set.of(
            "x", "y", "z"
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
                } else if (testAndConsume('-')) {
                    if (checkBounds('0', '9')) {
                        left = parseConst(true);
                    } else {
                        skipWhitespaces();
                        left = parseUnary(NEGATE);
                    }
                } else if (test(Character::isAlphabetic)) {
                    String name = parseAlphabeticName();
                    if (AVAILABLE_VARIABLES.contains(name)) {
                        left = new Variable(name);
                    } else {
                        left = parseUnary(name);
                    }
                } else {
                    left = parseConst(false);
                }
            }

            skipWhitespaces();
            String op = parseSymbolicName();
            if (op.isEmpty()) {
                op = parseAlphabeticName();
            }
            if (op.isEmpty()) {
                return left;
            }

            if (!OPERATION_PRIORITIES.containsKey(op)) {
                throw source.error("Unavailable binary operation's name: '" + op + "'");
            }

            if (OPERATION_PRIORITIES.get(op) <= returnOnPriority) {
                seekBackwards(op.length());
                return left;
            }

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

        private PriorityExpression parseUnary(String name) {
            if (name.equals("reverse")) {
                return new Reverse(parseExpression(OPERATION_PRIORITIES.get(name), null));
            } else if (name.equals(NEGATE)) {
                return new Negate(parseExpression(OPERATION_PRIORITIES.get(name), null));
            }
            throw source.error("Unavailable unary operator/variable 's name: '" + name + "'");
        }

        private String parseSymbolicName() {
            if (testAndConsume('+')) {
                return "+";
            } else if (testAndConsume('-')) {
                return "-";
            } else if (testAndConsume('*')) {
                return "*";
            } else if (testAndConsume('/')) {
                return "/";
            }
            return "";
        }

        private String parseAlphabeticName() {
            return getSatisfied(c -> Character.isAlphabetic(c) || Character.isDigit(c));
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
                return new Const(Integer.parseInt(res.toString()));
            } catch (NumberFormatException e) {
                throw source.error("Unavailable const: '" + res + "'");
            }
        }
    }
}
