package expression.parser;

import expression.*;

public class Main {
    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser();
        PriorityExpression expr = parser.parse("x * (x - 2)*x + 1");
        System.out.println(expr);
        System.out.println(expr.toMiniString());
        if (args.length == 0) {
            System.err.println("Variable's value is not provided.");
        } else {
            try {
                System.out.println(expr.evaluate(Integer.parseInt(args[0])));
            } catch (NumberFormatException e) {
                System.err.println("Provided variable is not an integer: " + e.getMessage());
            }
        }
    }
}
