public class Main {

    public static void main(String[] args) {
        String formula = "p^q->q";
        // Validacao v = new Validacao(formula);
        // v.mostraCaracteres();
        System.out.println(Tableaux.checkFormula(formula));
    }
}
