import java.util.ArrayList;
import java.util.List;

class Node {
    private String formula;
    private List<Node> children;
    private Node parent;

    public Node(String formula) {
        // check if formula has parentheses then remove them
        if (formula.startsWith("(") && formula.endsWith(")")) {
            formula = formula.substring(1, formula.length() - 1);
        }
        this.formula = formula;
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

    public List<Node> getChildren() {
        return children;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getSiblings() {
        if (parent == null) {
            return new ArrayList<>();
        }
        List<Node> siblings = new ArrayList<>();
        for (Node sibling : parent.getChildren()) {
            if (!sibling.equals(this)) {
                siblings.add(sibling);
            }
        }
        return siblings;
    }
}