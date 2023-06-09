import java.util.ArrayList;

public class Validacao {
    private String frase; //Frase lógica
    private ArrayList<Character> caracteres; //Separar cada caractere

    public Validacao(String frase) { //Construtor, que vai receber a frase lógica
        this.frase = frase;
        this.caracteres = new ArrayList<>();
        quebra();
    }

    //1 etapa

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    private void quebra() {
        for(int i = 0; i < frase.length(); i++) { //Percorre cada caractere da frase logica, OBS : Frase.length é o tamanho da frase
            if(frase.charAt(i) != ' ' ) { //Verifica se o caractere não é um espaço
                this.caracteres.add(frase.charAt(i)); //Adiciona o caractere ao atributo caracteres
            }
        }
    }

    public void mostraCaracteres() {
        for(int i = 0; i < caracteres.size(); i++) { //Percorre cada caractere da lista caractere
            System.out.println(caracteres.get(i)); //Imprime o caractere em cada posicao (i)
        }
    }

    private boolean isParentese(Character c) {
        ArrayList<Character> parentese = new ArrayList<>();
        parentese.add('(');
        parentese.add(')');
        for(Character i : parentese) {
            if(i.equals(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean isConective(Character c) {
        ArrayList<Character> operacoes = new ArrayList<>();
        operacoes.add('∧');
        operacoes.add('∨');
        operacoes.add('~');
        operacoes.add('→');
        operacoes.add('↔');
        for(Character i : operacoes) {
            if(i.equals(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNegador(Character c) {
        Character negador = '~';
        return c.equals(negador);
    }

    private boolean isOperador(Character c) { //Verifica se o caractere passado é um operador ou não
        ArrayList<Character> operacoes = new ArrayList<>(); //Cria a relação de operadores
        operacoes.add('∧');
        operacoes.add('∨');
        operacoes.add('→');
        operacoes.add('↔');
        for(Character i : operacoes) { //Para cada caractere em opereções, faça isso
            if(i.equals(c)) { //Verifica se o caractere passado é igual a algum dos operadores da relação
                return true;
            }
        }
        return false; //Caso não tenha retornado true em nenhum dos casos, retorna false
    }
    public boolean verificaConectivos() {
        for(int i = 0; i < caracteres.size(); i++) { //Percorre cada caractere dos caracteres
            if(!Character.isLetter(caracteres.get(i)) && !Character.isDigit(caracteres.get(i))) { //Se não for uma letra, entao, executa
                if(!isConective(caracteres.get(i)) && !isParentese(caracteres.get(i))) { //Verifica se o caractere não é um operador
                    return false;
                }
            }
        }
        return true; //Se nenhum dos caracteres não for um operador, retorna true
    }

    private boolean isAberto(Character c) {
        Character abertoParentese = '(';
        return c.equals(abertoParentese);
    }

    public ArrayList<Character> getCaracteres() {
        return caracteres;
    }

    private boolean isFechado(Character c) {
        Character fechadoParentese = ')';
        return c.equals(fechadoParentese);
    }

    //2 etapa
    public boolean isFbf() {
        int countParentese = 0; //Contar os parenteses
        for(int i = 0; i < caracteres.size(); i++) { //Percorre cada caractere dos caracteres
            if(countParentese >= 0) { //Verifica se o parentese não negativo
                if (isOperador(caracteres.get(i))) { //Se for um operador
                    try {
                        if (isOperador(caracteres.get(i + 1))) { //E se o proximo também for um operador
                            return false;
                        }
                    } catch (IndexOutOfBoundsException e) { //Se o último for um operador
                        return false;
                    }
                }
                if (Character.isLetter(caracteres.get(i))) { //Verifica se é uma letra(proposição)
                    try {
                        if (Character.isLetter(caracteres.get(i + 1)) || isNegador(caracteres.get(i + 1))) { //verifica se o próximo não é um operador
                            return false;
                        }
                    } catch (IndexOutOfBoundsException e) { //Se não tiver nada depois
                        return countParentese == 0; //Se todos os parenteses que foram abertos, forem fechados, retorna true
                    }
                }
                if (isNegador(caracteres.get(i))) { //Verifica se é um negador
                    try {
                        if (isOperador(caracteres.get(i + 1))) { //Verifica também se o próximo é um operador
                            return false;
                        }
                    } catch (IndexOutOfBoundsException e) { //Se o negador estiver no final
                        return false;
                    }
                }
                if(isParentese(caracteres.get(i))) { //Verifica se é um parentese
                    if(isAberto(caracteres.get(i))) { //Verifica também se esta abrindo(parentese)
                        countParentese++; //Aumenta o contador dos parenteses
                        try {
                            if (isOperador(caracteres.get(i + 1))) { //Verifica se o próximo é um operador
                                return false;
                            }
                        } catch (IndexOutOfBoundsException e) { //Se ele abrir parentese no final
                            return false;
                        }
                    }
                    if(isFechado(caracteres.get(i))) { //Verifica se o parentese é fechado
                        countParentese--; //Decrementa o contador de parentese
                        try {
                            if(!isConective(caracteres.get(i + 1))) { //Verifica se o próximo não é um conectivo
                                return false;
                            }
                        } catch (IndexOutOfBoundsException e) { //Se o último não for um parentese fechado
                            return countParentese == 0; //Se todos os parenteses que foram abertos, forem fechados, retorna true
                        }
                    }
                }
            } else { //Se o contador for negativo
                return false;
            }
        }
        return countParentese == 0; //Se todos os parenteses que foram abertos, forem fechados, retorna true
    }
}

