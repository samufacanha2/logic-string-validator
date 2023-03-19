import java.util.ArrayList;

public class Evaluator {

    // Helper method to get the variables used in a logical expression
    public static ArrayList<Character> getVariables(String expression) {
        ArrayList<Character> variables = new ArrayList<>();
        for (char c : expression.toCharArray()) {
            if (Character.isLetter(c) && !variables.contains(c) && c != 'T' && c != 'F' && c != 'v') {
                variables.add(c);
            }
        }
        return variables;
    }

    // Helper method to evaluate a logical expression
    public static boolean evaluateExpression(String expression) {

        if (expression.contains("(") && expression.contains(")")) {
            int startIndex = expression.lastIndexOf("(");
            int endIndex = expression.indexOf(")", startIndex);
            String subexpression = expression.substring(startIndex + 1, endIndex);
            boolean subexpressionResult = evaluateExpression(subexpression);
            expression = expression.substring(0, startIndex) + (subexpressionResult ? "T" : "F")
                    + expression.substring(endIndex + 1);

            return evaluateExpression(expression);

        } else if (expression.contains("<->")) {

            String[] terms = expression.split("<->");
            if (terms.length != 2) {
                System.err.println("Error: invalid expression");
                return false;
            }
            boolean left = evaluateExpression(terms[0]);
            boolean right = evaluateExpression(terms[1]);

            return (left && right) || (!left && !right);

        } else if (expression.contains("->")) {

            String[] terms = expression.split("->");
            if (terms.length != 2) {
                System.err.println("Error: invalid expression");
                return false;
            }
            boolean left = evaluateExpression(terms[0]);
            boolean right = evaluateExpression(terms[1]);

            return !left || right;

        } else if (expression.contains("v")) {

            String[] terms = expression.split("v");
            if (terms.length != 2) {
                System.err.println("Error: invalid expression");
                return false;
            }
            boolean left = evaluateExpression(terms[0]);
            boolean right = evaluateExpression(terms[1]);

            return left || right;

        } else if (expression.contains("^")) {

            String[] terms = expression.split("\\^");
            boolean result = true;
            for (String term : terms) {
                boolean value = true;
                for (char c : term.toCharArray()) {
                    if (c == 'T') {
                        value = value && true;
                    } else if (c == 'F') {
                        value = value && false;
                    } else {
                        System.err.println("Error: unknown variable " + c);
                        return false;
                    }
                }
                result = result && value;
            }

            return result;

        } else if (expression.equals("T")) {

            return true;
        } else if (expression.equals("F")) {

            return false;
        } else {

            System.err.println("Error: invalid expression");
            return false;
        }
    }
}