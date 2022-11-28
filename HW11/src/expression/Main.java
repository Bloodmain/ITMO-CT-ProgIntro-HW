package expression;

public class Main {
    public static void main(String[] args) {
        PriorityExpression expr = new Subtract(
                new Multiply(
                        new Const(2),
                        new Variable("x")
                ),
                new Const(3)
        );
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
