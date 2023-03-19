import java.util.ArrayList;

class Node {
    private String formula;
    private ArrayList<Node> children;
    private Node parent;

    // private String[] CONNECTORS = { "^", "v", "↔", "→" };

    public Node(String formula) {
        // check if formula has parentheses then remove them
        if (formula.startsWith("(") && formula.endsWith(")")) {
            formula = formula.substring(1, formula.length() - 1);
        }
        if (formula.length() == 2) {
            if (formula.startsWith("(")) {
                formula = formula.substring(1);
            } else if (formula.endsWith(")")) {
                formula = formula.substring(0, formula.length() - 1);
            }
        }
        if (formula.length() == 3 && formula.contains("~")) {
            if (formula.startsWith("(")) {
                formula = formula.substring(1);
            } else if (formula.endsWith(")")) {
                formula = formula.substring(0, formula.length() - 1);
            }
        }

        this.formula = formula.trim();
        this.children = new ArrayList<>();
        this.parent = null;
    }

    public String getFormula() {
        return formula;
    }

    public void addChild(Node child) {
        children.add(child);
        child.setParent(this);
    }

    public void removeChild(Node child) {
        children.remove(child);
        child.setParent(null);
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public ArrayList<Node> getSiblings() {
        if (parent == null) {
            return new ArrayList<>();
        }
        ArrayList<Node> siblings = new ArrayList<>();
        for (Node sibling : parent.getChildren()) {
            if (!sibling.equals(this)) {
                siblings.add(sibling);
            }
        }
        return siblings;
    }
}