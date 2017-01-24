package src;

import java.util.*;

public class TestaPerformance {
  public static void main(String[] args) {
    System.out.println("Iniciando...");
    // Collection<Integer> teste = new ArrayList<Integer>();
    Collection<Integer> teste = new HashSet<Integer>();
    long inicio = System.currentTimeMillis();

    int total = 30000;

    for (int i = 0; i < total; i++) {
      teste.add(i);
    }
    long inter = System.currentTimeMillis();

    for(int i = 0; i < total; i++) {
      teste.contains(i);
    }

    long fim = System.currentTimeMillis();
    long tempo1 = inter - inicio;
    long tempo2 = fim - inter;
    long tempo3 = fim - inicio;
    System.out.println("Tempo inserção: " + tempo1);
    System.out.println("Tempo busca: " + tempo2);
    System.out.println("Tempo total: " + tempo3);
  }
}

/*
* ArrayList
*
* Tempo inserção: 17
* Tempo busca: 2666
* Tempo total: 2683
*
* HashSet
*
* Tempo inserção: 48
* Tempo busca: 23
* Tempo total: 71
*/
