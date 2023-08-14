/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author danil
 */
public class GAExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int geracao = 0;
        ArrayList<Double> menorValor = new ArrayList<Double>();
        Populacao populacao = new Populacao();
        while (geracao != Config.FATOR_REPETIBILIDADE) {
            
            for (Individuo individuo : populacao.getPopulacao()) {
                System.out.println("Funcao: " + individuo.realizarFuncao(individuo.calcularDecimal()));
                menorValor.add(individuo.realizarFuncao(individuo.calcularDecimal()));
            }
            System.out.println("A menor funcao e:" +Collections.min(menorValor));
            populacao.evolucaoPopulacao();
            
            geracao++;
        }
//         imprime o resultado final
        System.out.println("Melhor indivíduo: " + Arrays.toString(populacao.buscaMelhor().getCromossomo()));
        System.out.println("Fitness: " + populacao.buscaMelhor().calcularDecimal());
    }
}