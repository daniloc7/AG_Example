/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author danil
 */
public class Populacao {

    private Individuo[] populacao;

    public Populacao(Individuo[] populacao) {
        this.populacao = populacao;
    }

    public Populacao() {
        populacao = new Individuo[Config.POP];
        for (int i = 0; i < Config.POP; i++) {
            populacao[i] = new Individuo(gerarCromossomo());
//            populacao[i].new Individuo());
//            gerarCromossomo
        }
    }

    public Individuo[] getPopulacao() {
        return populacao;
    }

    public void setPopulacao(Individuo[] populacao) {
        this.populacao = populacao;
    }

//    public Map<Integer, Individuo> buscaMelhor(int index) {
//        Map<Integer, Individuo> melhor = new HashMap<>();
//        melhor.put(0, populacao[0]);
////        Individuo melhor = populacao[0];
//        for (int i = 1; i < Config.POP; i++) {
//            //iterando para buscar o melhor, inicialmente o chute é no mais velho
//            if (populacao[i].getAptidao() > melhor.getAptidao()) {
//                melhor = populacao[i];
//            }
//        }
//        return melhor;
//    }
    public void calcularAptidaoPopulacao() {
        int soma = 0;
        int soma1 = 0;
        for (int i = 0; i < Config.POP; i++) {
//            soma1 += populacao[i].calcularDecimal();
            soma += populacao[i].calcularAptidaoRInt();
            soma1 += soma;
//            System.out.println("SOMA:"+Arrays.toString(populacao[i].getCromossomo()));
        }
//        System.out.println("Aptidao da populacao:" + soma1);
    }

    public List<Individuo> buscarMelhores() {
        List<Individuo> melhores = new ArrayList<>();
        melhores.add(populacao[0]);
        for (int i = 1; i < Config.POP; i++) {
            Individuo individuoAtual = populacao[i];
            int j = 0;
            while (j < melhores.size() && individuoAtual.getAptidao() < melhores.get(j).getAptidao()) {
                j++;
            }
            melhores.add(j, individuoAtual);
        }
        return melhores;
    }

    public Individuo buscaMelhor() {
        Individuo melhor = populacao[0];
        double melhorAptidao = Double.NEGATIVE_INFINITY;
        for (int i = 1; i < Config.POP; i++) {
            //iterando para buscar o melhor, inicialmente o chute é no mais velho
            if (populacao[i].getAptidao() > melhor.getAptidao()) {
                melhor = populacao[i];
                melhor.setAptidao(populacao[i].getAptidao());
            }
        }
        return melhor;
    }

    public Individuo getIndividuo(int index) {
        return populacao[index];
    }

    public int size() {
        return Config.POP;
    }

    private int[] gerarCromossomo() {
        int[] cromossomo = new int[Config.CROMOSSOMO];
        for (int i = 0; i < cromossomo.length; i++) {
            cromossomo[i] = (int) Math.round(Math.random());
        }
        return cromossomo;
    }

    public void evolucaoPopulacao() {
        Individuo[] novaPopulacao = new Individuo[Config.POP];
        // cria os indivíduos da nova população
        List<Individuo> melhores = buscarMelhores();
        Individuo melhor = buscaMelhor();

//        for (int i = 0; i < 1; i++) {
//            novaPopulacao[i] = melhores.get(3);
////            System.out.println("MELHORES:" + melhores.get(i).calcularAptidaoRInt() + "I:" + i);
//        }
//        novaPopulacao[0] = melhor;
//        System.out.println("MELHORES " + melhores.get(0).calcularAptidaoRInt());
//        System.out.println("MELHOR " + melhor.calcularAptidaoRInt());
        for (int i = 0; i < Config.POP; i++) {
            // seleciona dois indivíduos para o cruzamento
            Individuo pai = select();
            Individuo mae = select();

            // realiza o crossover
            Individuo filho = pai.crossover(mae);

            // realiza a mutação
            filho.mutacao();

            // adiciona o filho à nova população
            novaPopulacao[i] = filho;
        }

        // atualiza a população atual com a nova população
        populacao = novaPopulacao;
        // recalcula a aptidão da população atual
        calcularAptidaoPopulacao();

    }

    private Individuo select() {
        // calcula a soma ottal de fitness da população
        double totalFitness = 0;
        for (Individuo individual : populacao) {
            totalFitness += individual.getAptidao();
        }

        // vai distribuir a porcentagem de cada indivíduo na roleta
        double[] roulette = new double[Config.POP];
        double accumulatedFitness = 0;
        for (int i = 0; i < Config.POP; i++) {
            accumulatedFitness += populacao[i].getAptidao() / totalFitness;
            roulette[i] = accumulatedFitness;
        }

        // gira a roleta para selecionar um indivíduo aleatoriamente
        double spin = Math.random();
        for (int i = 0; i < Config.POP; i++) {
            if (spin <= roulette[i]) {
                return populacao[i];
            }
        }

        // caso a roleta não selecione nenhum indivíduo, retorna um indivíduo aleatório
        return populacao[(int) (Math.random() * Config.POP)];
    }

}
