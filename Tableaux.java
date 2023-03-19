import java.util.ArrayList;

public class Tableaux {

    public static boolean checkFormula(String formula) {
        Node root = new Node(formula);
        return checkSubtree(root);
    }

    private static boolean checkSubtree(Node node) {
        String formula = node.getFormula();

        // Check for a contradiction
        if (nodeHasContradiction(node)) {
            System.out.println("Contradiction found");
            return false;
        }

        // Check for a closed branch
        if (nodeIsClosed(node)) {
            System.out.println("Closed branch found");
            return false;
        }

        // Check for a complete branch
        if (nodeIsComplete(node)) {
            System.out.println("Complete branch found");
            return true;
        }

        // Apply tableau rules based on the formula's main connective
        if (formula.startsWith("~")) {
            // Negation rule
            String subformula = formula.substring(1);
            Node child = new Node(subformula);
            node.addChild(child);
            return checkSubtree(child);

        }

        else if (formula.contains("^"))

        {
            // Conjunction rule
            String[] subformulas = formula.split("\\^");
            Node child1 = new Node(subformulas[0]);
            Node child2 = new Node(subformulas[1]);
            node.addChild(child1);
            node.addChild(child2);
            return checkSubtree(child1) && checkSubtree(child2);
        } else if (formula.contains("v")) {
            // Disjunction rule
            String[] subformulas = formula.split("v");
            Node child1 = new Node(subformulas[0]);
            Node child2 = new Node(subformulas[1]);
            node.addChild(child1);
            node.addChild(child2);
            return checkSubtree(child1) || checkSubtree(child2);
        } else if (formula.contains("<->")) {
            // Equivalence rule
            String[] subformulas = formula.split("<->");
            Node child1 = new Node("(" + subformulas[0] + "^" + subformulas[1] + ")");
            Node child2 = new Node("(~" + subformulas[0] + "^~" + subformulas[1] + ")");
            node.addChild(child1);
            node.addChild(child2);
            return checkSubtree(child1) && checkSubtree(child2);
        } else if (formula.contains("->")) {
            // Implication rule
            String[] subformulas = formula.split("->");
            Node child1 = new Node("~" + subformulas[0]);
            Node child2 = new Node(subformulas[1]);
            node.addChild(child1);
            node.addChild(child2);
            return checkSubtree(child1) || checkSubtree(child2);
        } else if (formula.length() == 1) {
            // Atomic formula
            return true;
        }

        // If we get here, something went wrong
        throw new IllegalArgumentException("Invalid formula: " + formula);
    }

    private static boolean nodeHasContradiction(Node node) {
        ArrayList<Node> children = node.getChildren();
        for (Node child : children) {
            if (child.getFormula().equals("~" + node.getFormula()) ||
                    node.getFormula().equals("~" + child.getFormula())) {
                return true;
            }
        }
        if (node.getParent() != null) {
            return nodeHasContradiction(node.getParent());
        }
        return false;
    }

    private static boolean nodeIsClosed(Node node) {
        ArrayList<Node> siblings = node.getSiblings();
        for (Node sibling : siblings) {
            if (sibling.getFormula().equals(node.getFormula())) {
                return true;
            }
        }
        if (node.getParent() != null) {
            return nodeIsClosed(node.getParent());
        }
        return false;
    }

    private static boolean nodeIsComplete(Node node) {
        ArrayList<Node> children = node.getChildren();
        if (children.isEmpty()) {
            return false;
        }
        for (Node child : children) {
            if (!checkSubtree(child)) {
                return false;
            }
        }
        return true;
    }

}
