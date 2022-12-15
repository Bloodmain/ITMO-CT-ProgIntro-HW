package expression.exceptions;

import expression.Const;
import expression.Variable;

import java.util.Map;

public final class ExpressionParser implements TripleParser {
    private static final String NEGATE = "--";
    private static final Map<String, Integer> OPERATION_PRIORITIES = Map.of(
            "+", 5,
            "-", 5,
            "*", 10,
            "/", 10,
            NEGATE, 15
    );

    public CheckedExpression parse(CharSource source) throws ParseException {
        return new ExpressionAnalyzer(source).parse();
    }

    @Override
    public CheckedExpression parse(String source) throws ParseException {
        return parse(new StringSource(source));
    }

    private static class ExpressionAnalyzer extends BaseParser {

        public ExpressionAnalyzer(CharSource source) {
            super(source);
        }

        private CheckedExpression parse() throws ParseException {
            try {
                CheckedExpression res = parseExpression(-1, null);
                assertEOF();
                return res;
            } catch (ParseException e) {
                System.out.println(Main.getExceptionsChain(e));
                throw new ParseException("Exception during expression parsing", e);
            }
        }

        private CheckedExpression parseExpression(final int returnOnPriority,
                                                  CheckedExpression left) throws ParseException {
            skipWhitespaces();
            if (left == null) {
                if (testAndConsume('(')) {
                    left = parseExpression(-1, null);
                    if (!test(')')) {
                        if (checkEOF()) {
                            throw new UnclosedParentheses("Pos " + source.getPos() + ": Unclosed parentheses",
                                    new UnexpectedEOF("Unexpected EOF"));
                        }
                        throw new UnclosedParentheses("Pos " + source.getPos() + ": Unclosed parentheses");
                    }
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
            } else if (test('*')) {
                op = "*";
            } else if (test('/')) {
                op = "/";
            }  else {
                return left;
            }

            if (OPERATION_PRIORITIES.get(op) <= returnOnPriority) {
                return left;
            }
            assertAndConsume(op);
            skipWhitespaces();
            CheckedExpression right = parseExpression(OPERATION_PRIORITIES.get(op), null);
            CheckedExpression res = switch (op) {
                case "+" -> new CheckedAdd(left, right);
                case "-" -> new CheckedSubtract(left, right);
                case "*" -> new CheckedMultiply(left, right);
                default -> new CheckedDivide(left, right);
            };

            return checkEOF() ? res : parseExpression(returnOnPriority, res);
        }

        private CheckedExpression parseNegate() throws ParseException {
            return new CheckedNegate(parseExpression(OPERATION_PRIORITIES.get(NEGATE), null));
        }

        private CheckedExpression parseConst(boolean negative) throws ParseException {
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
                throw new IncorrectConstantException("Pos " + source.getPos() + ": Expected const, but didn't find it");
            }
        }

        private CheckedExpression parseVariable() {
            char variable = consume();
            skipWhitespaces();
            return new Variable(String.valueOf(variable));
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(new ExpressionParser().parse("reverse x"));
        } catch (ParseException e) {
            Throwable x = e;
            while (x != null) {
                System.out.println(x.getMessage());
                x = x.getCause();
            }
        }
    }
}
