package expression.parser;

import expression.*;

import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public final class ExpressionParser implements TripleParser {
    private static final Map<String, BinaryOperator<PriorityExpression>> GCD_LCM = Map.of(
            "gcd", Gcd::new,
            "lcm", Lcm::new
    );

    private static final Map<String, BinaryOperator<PriorityExpression>> ADDICTIVE = Map.of(
            "+", Add::new,
            "-", Subtract::new
    );

    private static final Map<String, BinaryOperator<PriorityExpression>> MULTIPLICATIVE = Map.of(
            "*", Multiply::new,
            "/", Divide::new
    );

    private static final Map<String, UnaryOperator<PriorityExpression>> UNARY = Map.of(
            "-", Negate::new,
            "reverse", Reverse::new
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
            PriorityExpression result = parseGcdLcm();
            assertEOF();
            return result;
        }

        private PriorityExpression parseExpression(final Map<String, BinaryOperator<PriorityExpression>> operationsToParse,
                                                   final Supplier<PriorityExpression> next) {
            PriorityExpression expr = next.get();
            skipWhitespaces();
            String op = readOperation();
            while (operationsToParse.containsKey(op)) {
                consume(op);
                PriorityExpression right = next.get();
                skipWhitespaces();
                expr = operationsToParse.get(op).apply(expr, right);
                op = readOperation();
            }
            return expr;
        }

        private PriorityExpression parseGcdLcm() {
            return parseExpression(GCD_LCM, this::parseAddictive);
        }

        private PriorityExpression parseAddictive() {
            return parseExpression(ADDICTIVE, this::parseMultiplicative);
        }

        private PriorityExpression parseMultiplicative() {
            return parseExpression(MULTIPLICATIVE, this::parseBrackets);
        }

        private PriorityExpression parseBrackets() {
            skipWhitespaces();
            if (test('(')) {
                consume();
                PriorityExpression expr = parseGcdLcm();
                skipWhitespaces();
                assertNextEquals(')');
                consume();
                return expr;
            } else {
                return parseUnary();
            }
        }

        private PriorityExpression parseUnary() {
            skipWhitespaces();
            if (checkBounds('0', '9')) {
                return parseConst(false);
            } else {
                String name = readOperation();
                consume(name);
                if (name.equals("-") && checkBounds('0', '9')) {
                    return parseConst(true);
                } else if (UNARY.containsKey(name)) {
                    return UNARY.get(name).apply(parseBrackets());
                } else if (AVAILABLE_VARIABLES.contains(name)) {
                    return new Variable(name);
                }
                throw source.error("Unexpected token: '" + name + "'");
            }
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

        private String readOperation() {
            String op = readSymbolicName();
            if (op.isEmpty()) {
                op = readAlphabeticName();
            }
            return op;
        }

        private String readSymbolicName() {
            if (test('+')) {
                return "+";
            } else if (test('-')) {
                return "-";
            } else if (test('*')) {
                return "*";
            } else if (test('/')) {
                return "/";
            }
            return "";
        }

        private String readAlphabeticName() {
            return getToken(c -> Character.isLetter(c) || Character.isDigit(c));
        }
    }
}
