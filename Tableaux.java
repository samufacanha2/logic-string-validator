import java.util.List;

public class Tableaux {

    public static boolean checkFormula(String formula) {
        Node root = new Node(formula);
        return checkSubtree(root);
    }

    private static boolean checkSubtree(Node node) {
        String formula = node.getFormula();

        // Check for a contradiction
        if (nodeHasContradiction(node)) {
            return false;
        }

        // Check for a closed branch
        if (nodeIsClosed(node)) {
            return false;
        }

        // Check for a complete branch
        if (nodeIsComplete(node)) {
            return true;
        }

        // Apply tableau rules based on the formula's main connective
        if (formula.startsWith("~")) {
            // Negation rule
            String subformula = formula.substring(1);
            Node child = new Node(subformula);
            node.addChild(child);
            return checkSubtree(child);
        } else if (formula.contains("^")) {
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
        List<Node> siblings = node.getSiblings();
        for (Node sibling : siblings) {
            if (sibling.getFormula().equals("~" + node.getFormula()) ||
                    node.getFormula().equals("~" + sibling.getFormula())) {
                return true;
            }
        }
        if (node.getParent() != null) {
            return nodeHasContradiction(node.getParent());
        }
        return false;
    }

    private static boolean nodeIsClosed(Node node) {
        List<Node> siblings = node.getSiblings();
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
        List<Node> children = node.getChildren();
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