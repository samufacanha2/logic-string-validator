import java.util.ArrayList;

class Node {
    private String leftSubstring;
    private String rightSubstring;
    private Node leftChild;
    private Node rightChild;
    private Node parent;
    private boolean isAtomic;
    private char connector;
    private boolean negated;

    // private String[] CONNECTORS = { "^", "v", "↔", "→" };

    public Node(String formula) {

        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;

        if (formula.charAt(0) == '~') {
            this.negated = true;
        } else {
            this.negated = false;
        }

        char connector = getConnectorFromFormula(formula);
        this.connector = connector;

        if (connector == ' ') {
            this.leftSubstring = formula;

            if (formula.length() == 3 && formula.charAt(0) == '('
                    && formula.charAt(2) == ')') {
                this.isAtomic = true;
            } else if (formula.length() == 4 && formula.charAt(1) == '('
                    && formula.charAt(3) == ')' && formula.charAt(0) == '~') {
                this.isAtomic = true;
            } else if (formula.length() == 4 && formula.charAt(0) == '('
                    && formula.charAt(3) == ')' && formula.charAt(1) == '~') {
                this.leftSubstring = "~(" + formula.charAt(2) + ")";
                this.isAtomic = true;

            } else {
                this.isAtomic = false;
            }
            return;
        } else {
            this.isAtomic = false;
            this.leftSubstring = getLeftSubstringFromFormula(formula);
            this.rightSubstring = getRightSubstringFromFormula(formula);
        }

    }

    private String getLeftSubstringFromFormula(String formula) {
        int leftSubstringEnd = 0;
        int leftSubstringStart = 0;
        int openParentheses = 0;

        for (int i = 0; i < formula.length(); i++) {
            if (formula.charAt(i) == '(') {
                openParentheses++;
            } else if (formula.charAt(i) == ')') {
                openParentheses--;
            } else if (formula.charAt(i) == '^' || formula.charAt(i) == 'v' || formula.charAt(i) == '→'
                    || formula.charAt(i) == '↔') {
                if (openParentheses == (this.negated ? 0 : 1)) {
                    leftSubstringEnd = i;
                    break;
                }
            }
        }
        return formula.substring(leftSubstringStart, leftSubstringEnd) + (this.negated ? "" : ")");
    }

    private String getRightSubstringFromFormula(String formula) {
        int rightSubstringStart = 0;
        int rightSubstringEnd = formula.length();
        int openParentheses = 0;
        for (int i = 0; i < formula.length(); i++) {
            if (formula.charAt(i) == '(') {
                openParentheses++;
            } else if (formula.charAt(i) == ')') {
                openParentheses--;
            } else if (formula.charAt(i) == '^' || formula.charAt(i) == 'v' || formula.charAt(i) == '→'
                    || formula.charAt(i) == '↔') {
                if (openParentheses == (this.negated ? 0 : 1)) {
                    rightSubstringStart = i + 1;
                    break;
                }
            }
        }
        return (this.negated ? "" : "(") + formula.substring(rightSubstringStart, rightSubstringEnd);
    }

    private char getConnectorFromFormula(String formula) {
        int openParentheses = 0;
        for (int i = 0; i < formula.length(); i++) {
            if (formula.charAt(i) == '(') {
                openParentheses++;
            } else if (formula.charAt(i) == ')') {
                openParentheses--;
            } else if (formula.charAt(i) == '^' || formula.charAt(i) == 'v' || formula.charAt(i) == '→'
                    || formula.charAt(i) == '↔') {
                if (openParentheses == (this.negated ? 0 : 1)) {
                    return formula.charAt(i);
                }
            }
        }
        return ' ';
    }

    public String getLeftSubstring() {
        return leftSubstring;
    }

    public String getRightSubstring() {
        return rightSubstring;
    }

    public char getConnector() {
        return connector;
    }

    public boolean isNegated() {
        return negated;
    }

    public boolean isAtomic() {
        return isAtomic;
    }

    public void addLeftChild(Node child) {
        leftChild = child;
        child.setParent(this);
    }

    public void addRightChild(Node child) {
        rightChild = child;
        child.setParent(this);
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public ArrayList<Node> getChildren() {
        ArrayList<Node> children = new ArrayList<>();
        if (leftChild != null) {
            children.add(leftChild);
        }
        if (rightChild != null) {
            children.add(rightChild);
        }
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

    public void print() {

        System.out.println("left substring: " + this.getLeftSubstring());
        System.out.println("right substring: " + this.getRightSubstring());
        System.out.println("connector: " + this.getConnector());
        System.out.println("is atomic: " + this.isAtomic());
        System.out.println("is negated: " + this.isNegated());

        return;
    }
}