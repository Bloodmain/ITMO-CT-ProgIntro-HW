package expression;

public class Main {
    public static void main(String[] args) {
//        PriorityExpression expr = new Subtract(
//                new Multiply(
//                        new Variable("x"),
//                        new Variable("x")
//                ),
//                new Add(
//                        new Multiply(
//                                new Const(2),
//                                new Variable("x")
//                        ),
//                        new Const(1)
//                )
//        );
//        System.out.println(expr);
//        System.out.println(expr.toMiniString());
//        if (args.length == 0) {
//            System.err.println("Variable's value is not provided.");
//        } else {
//            try {
//                System.out.println(expr.evaluate(Integer.parseInt(args[0])));
//            } catch (NumberFormatException e) {
//                System.err.println("Provided variable is not an integer: " + e.getMessage());
//            }
//        }

        PriorityExpression expr = new Subtract(
                new Const(1),
                new Subtract(
                        new Const(2),
                        new Const(3)
                )
        );

        //(1 * 31 + 45) * 31 + (2 * 31 + 45) * 31 + 3
        //(1 * 31 + 43) * 31 + (2 * 31 + 47) * 31 + 3
        PriorityExpression expr2 = new Add(
                new Const(1),
                new Divide(
                        new Const(2),
                        new Const(3)
                )
        );
        System.out.println(expr.equals(expr2));
        System.out.println(expr.hashCode());
        System.out.println(expr2.hashCode());
    }
}
