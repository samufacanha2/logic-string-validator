public class Tableaux {

    public static boolean checkFormula(String formula) {
        Node root = new Node(formula);
        buildTree(root);
        return checkTreeOpen(root);
    }

    private static void buildTree(Node node) {
        if (node.isAtomic()) {
            return;
        }
        R1(node);
        R2(node);
        R3(node);
        R4(node);
        R5(node);
        R6(node);
        R7(node);
        R8(node);
        R9(node);
    }

    private static boolean checkTreeOpen(Node node) {
        if (node.isAtomic()) {
            return true;
        }
        if (node.isNegated()) {
            return checkTreeClosed(node);
        }
        return checkTreeOpen(node.getLeftChild()) && checkTreeOpen(node.getRightChild());
    }

    private static boolean checkTreeClosed(Node node) {
        if (node.isAtomic()) {
            return false;
        }
        if (node.isNegated()) {
            return checkTreeOpen(node);
        }
        return checkTreeClosed(node.getLeftChild()) || checkTreeClosed(node.getRightChild());
    }

    private static void R1(Node node) {
        if (node.getConnector() != '^')
            return;
        Node grandChild = new Node(node.getRightSubstring());
        Node child = new Node(node.getLeftSubstring());
        child.addLeftChild(grandChild);
        node.addLeftChild(child);
    }

    private static void R2(Node node) {
        if (node.getConnector() != 'v')
            return;

        if (node.isNegated()) {
            return;
        }
        Node leftChild = new Node(node.getLeftSubstring());
        Node rightChild = new Node(node.getRightSubstring());
        node.addLeftChild(leftChild);
        node.addRightChild(rightChild);
    }

    private static void R3(Node node) {
        if (node.getConnector() != '→')
            return;

        if (node.isNegated()) {
            return;
        }
        Node leftChild = new Node("~" + node.getLeftSubstring());
        Node rightChild = new Node(node.getRightSubstring());
        node.addLeftChild(leftChild);
        node.addRightChild(rightChild);
    }

    private static void R4(Node node) {
        if (node.getConnector() != '↔')
            return;

        if (node.isNegated()) {
            return;
        }
        Node leftChild = new Node(node.getLeftSubstring() + '^' + node.getRightSubstring());
        Node rightChild = new Node("~" + node.getLeftSubstring() + '^' + "~" + node.getRightSubstring());
        node.addLeftChild(leftChild);
        node.addRightChild(rightChild);
    }

    private static void R5(Node node) {
        if (!node.isAtomic()) {
            return;
        }

        if (node.isNegated() && node.getLeftSubstring().startsWith("~~")) {
            Node child = new Node(node.getLeftSubstring().substring(2));
            node.addLeftChild(child);
        }
    }

    private static void R6(Node node) {
        if (node.getConnector() != '^')
            return;

        if (!node.isNegated()) {
            return;
        }
        Node leftChild = new Node("~" + node.getLeftSubstring());
        Node rightChild = new Node("~" + node.getRightSubstring());
        node.addLeftChild(leftChild);
        node.addRightChild(rightChild);
    }

    private static void R7(Node node) {
        if (node.getConnector() != 'v')
            return;

        if (!node.isNegated()) {
            return;
        }
        Node grandChild = new Node("~" + node.getLeftSubstring());
        Node child = new Node("~" + node.getRightSubstring());
        child.addLeftChild(grandChild);
        node.addLeftChild(child);
    }

    private static void R8(Node node) {
        if (node.getConnector() != '→')
            return;

        if (!node.isNegated()) {
            return;
        }
        Node grandChild = new Node(node.getLeftSubstring());
        Node child = new Node("~" + node.getRightSubstring());
        child.addLeftChild(grandChild);
        node.addLeftChild(child);
    }

    private static void R9(Node node) {
        if (node.getConnector() != '↔')
            return;

        if (!node.isNegated()) {
            return;
        }
        Node leftChild = new Node("~" + node.getLeftSubstring() + "^" + node.getRightSubstring());
        Node rightChild = new Node(node.getLeftSubstring() + "^~" + node.getRightSubstring());
        node.addLeftChild(leftChild);
        node.addRightChild(rightChild);
    }

}
