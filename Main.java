public class Main {

    public static void main(String[] args) {
        String formula = "(p→q";
        Validacao v = new Validacao(formula);
        System.out.println(v.isFbf());
        System.out.println(Tableaux.checkFormula(formula));
    }
}
