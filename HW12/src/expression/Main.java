package expression;

import expression.parser.ExpressionParser;
import expression.parser.TripleParser;

public class Main {
    public static void main(String[] args) {
        TripleParser parser = new ExpressionParser();
        TripleExpression expr = parser.parse("x * (x - 2)*x + 1");
        System.out.println(expr);
        System.out.println(expr.toMiniString());
        if (args.length < 3) {
            System.err.println("Variables' values are not provided.");
        } else {
            try {
                System.out.println(expr.evaluate(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
                        Integer.parseInt(args[2])));
            } catch (NumberFormatException e) {
                System.err.println("Provided variable is not an integer: " + e.getMessage());
            }
        }
    }
}
