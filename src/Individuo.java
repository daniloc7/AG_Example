/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author danil
 */
public class Individuo {

    private int[] cromossomo = new int[Config.CROMOSSOMO];
    private double aptidao;

    public Individuo(int[] cromossomo, double aptidao) {
        this.cromossomo = cromossomo;
        this.aptidao = aptidao;
    }

    public Individuo(int[] cromossomo) {
        this.cromossomo = cromossomo;
    }

    public Individuo() {
        aptidao = 0;
    }

    public int[] getCromossomo() {
        return cromossomo;
    }

    public void setCromossomo(int[] cromossomo) {
        this.cromossomo = cromossomo;
    }

    public double getAptidao() {
        return aptidao;
    }

    public void setAptidao(double aptidao) {
        this.aptidao = aptidao;
    }

    public double calcularDecimal() {
        double decimal = 0;
        for (int i = cromossomo.length - 1; i >= 0; i--) {
            decimal += cromossomo[i] * Math.pow(2, cromossomo.length - 1 - i);
        }
        return decimal;
    }

    public void calcularAptidao() {
        int soma = 0;
        //calcula o valor dos 5 cromossomos
        for (int i = 0; i < cromossomo.length; i++) {
            soma += cromossomo[i];
        }
        aptidao = soma;
    }
//

    public double realizarFuncao(double valor) {

        double x = -10 + (10 - (-10)) * valor / (Math.pow(2, Config.CROMOSSOMO)) - 1;

        System.out.println("Valor X:" + x);
        return ((x * x) - (3 * x) + 4);
    }

    public double calcularAptidaoRInt() {
//        int soma = 0;
        //calcula o valor dos 5 cromossomos
        for (int i = 0; i < cromossomo.length; i++) {
            aptidao += cromossomo[i];
        }
//        aptidao = soma;
        return aptidao;
    }

    public void mutacao() {
        //pega algum dos 5 bits
        // verifica se ocorre a mutação com uma probabilidade de 1%
        for (int j = 0; j < cromossomo.length; j++) {
            if (Math.random() < Config.PROB_MUT) {
                // seleciona um índice aleatório para realizar a mutação

//                int i = (int) (Math.random() * cromossomo.length);
                // inverte o valor
                cromossomo[j] = 1 - cromossomo[j];
            }
        }
    }

    //retorna um individuo novo dado a separação dos bits no crossover
    public Individuo crossover(Individuo companheiro) {
        int[] filho = new int[cromossomo.length];
        double random = Math.random();
//        System.out.println("math.random"+random);
        double crossoverProb = Config.CROSSOVER_MIN + random * (Config.CROSSOVER_MAX - Config.CROSSOVER_MIN);
//        System.out.println("CROSSOVERPROB:"+crossoverProb);
        int crossoverCorte = (int) (crossoverProb * (cromossomo.length - 1)) + 1;

        // copia os genes do pai até o ponto de corte
        for (int i = 0; i < crossoverCorte; i++) {
            filho[i] = cromossomo[i];
        }

        // copia os genes da mãe após o ponto de corte
        for (int i = crossoverCorte; i < cromossomo.length; i++) {
            filho[i] = companheiro.cromossomo[i];
        }

        // cria e retorna um novo indivíduo com o cromossomo resultante do crossover
        return new Individuo(filho);
    }

}
