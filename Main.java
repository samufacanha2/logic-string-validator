public class Main {

    public static void main(String[] args) {
        // String formula = "(p→q";
        // Validacao v = new Validacao(formula);
        // System.out.println(v.isFbf());
        // System.out.println(Tableaux.checkFormula(formula));
        String formula = "((p^q)^~p)";

        System.out.println("formula: " + formula);

        System.out.println(Tableaux.checkFormula(formula));

        // Node node = new Node(formula);
        // node.print();

    }
}
