package src;

import src.br.com.empresa.banco.conta.*;
import src.br.com.empresa.banco.exception.*;
import src.br.com.empresa.banco.sistema.*;
import src.br.com.empresa.banco.util.*;

public class TesteThreads {
  public static void main(String[] args) {
    ContaCorrente c1 = new ContaCorrente(500.0);
    c1.setNumero(003);

    FazDeposito fd1 = new FazDeposito(c1, 100.0);
    // FazDeposito fd2 = new FazDeposito(c1, 200.0);

    Thread t1 = new Thread(fd1);
    Thread t2 = new Thread(fd1);

    System.out.println("Saldo antes: " + c1.getSaldo());

    t1.start();
    t2.start();

    // try {
    //   Thread.sleep(2000);
    // } catch(InterruptedException ie) {
    //   System.out.println(ie);
    // }
    System.out.println("Saldo depois: " + c1.getSaldo());
  }
}


class FazDeposito implements Runnable {

  private Conta conta;
  private double valor;

  public FazDeposito(Conta conta, double valor) {
    this.conta = conta;
    this.valor = valor;
  }

  public void run() {
    System.out.println("Id: " + Thread.currentThread().getId());
    this.conta.deposita(this.valor);
  }
}
