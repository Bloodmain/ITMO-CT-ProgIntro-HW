package expression.exceptions;

import java.util.Scanner;

public class Main {
    public static String getExceptionsChain(Throwable e) {
        return e.getMessage() + (e.getCause() != null ? (" (" + getExceptionsChain(e.getCause()) + ")") : "");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter expression: ");
        // 1000000*x*x*x*x*x/(x-1)
        String expr = sc.nextLine();
        sc.close();

        ExpressionParser parser = new ExpressionParser();

        try {
            CheckedExpression parsedExpr = parser.parse(expr);
            System.out.println("x\tf");
            for (int x = 0; x <= 10; ++x) {
                System.out.print(x + "\t");
                try {
                    System.out.println(parsedExpr.evaluate(x));
                } catch (DivisionByZeroException e) {
                    System.out.println("division by zero");
                } catch (OverflowException e) {
                    System.out.println("overflow");
                }
            }
        } catch (ParseException e) {
            System.err.println(getExceptionsChain(e));
        }
    }
}
