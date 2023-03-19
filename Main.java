import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a logical expression: ");
        String expression = input.nextLine();

        Validacao v = new Validacao(expression);

        while (!v.isFbf()) { // validate the expression
            System.out.println("Invalid expression, please enter a valid one.");
            expression = input.nextLine();
            v = new Validacao(expression);
        }

        // Prompt the user to enter values for the variables
        for (char variable : Evaluator.getVariables(expression)) {
            System.out.print("Enter a value for " + variable + " (true or false): ");
            boolean value = input.nextBoolean();
            expression = expression.replace(variable, value ? 'T' : 'F');
        }

        while (expression.contains("~T") || expression.contains("~F")) { // replace all ~T with F and ~F with T
            expression = expression.replace("~T", "F");
            expression = expression.replace("~F", "T");
        }

        // Evaluate the logical expression
        boolean result = Evaluator.evaluateExpression(expression);

        // Print the result
        System.out.println("The result of the expression " + expression + " is: " + result);

        input.close();
    }

}
