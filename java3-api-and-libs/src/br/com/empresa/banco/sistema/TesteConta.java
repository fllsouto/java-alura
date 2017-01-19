package src.br.com.empresa.banco.sistema;

import src.br.com.empresa.banco.conta.*;
import src.br.com.empresa.banco.exception.*;
import src.br.com.empresa.banco.util.*;

public class TesteConta {
  public static void main(String[] args) {
    testaContaEquals();
  }

  public static void testaContaEquals() {
    ContaCorrente c1 = new ContaCorrente(1000.0);
    ContaCorrente c2 = new ContaCorrente(1000.0);
    ContaCorrente c3 = new ContaCorrente(1000.0);
    ContaCorrente c4 = new ContaCorrente(1000.0);
    c1.setNumero(1234);
    c2.setNumero(1234);
    c3.setNumero(2345);
    c4.setNumero(1234);

    c1.setNomeCliente("João");
    c2.setNomeCliente("José");
    c3.setNomeCliente("Maria");
    c4.setNomeCliente("José");

    System.out.println("C1 == C2 :" + c1.equals(c2));
    System.out.println("C1 == C3 :" + c1.equals(c3));
    System.out.println("C2 == C4 :" + c2.equals(c4));

  }

  public static void testaContaToString() {
    ContaCorrente c = new ContaCorrente(1000.0);
    System.out.println(c);
  }

  public static void testaContaException() {
    try {
      ContaCorrente c = new ContaCorrente(1000.0);
      c.deposita(400.0);
      c.saca(-500.0);
    } catch(ValorInvalidoException e) {
      System.out.println("Erro: " + e.getMessage());
    }
  }

  public static void testaConta() {
    ContaCorrente c = new ContaCorrente(1000.0);
    System.out.println("Saldo R$: " + c.getSaldo());
    System.out.println("Deposito de R$: 400.0");
    c.deposita(400.0);
    System.out.println("Saldo R$: " + c.getSaldo());
    System.out.println("Saque de R$: 800.0");
    c.saca(800.0);
    System.out.println("Saldo R$: " + c.getSaldo());

    System.out.println("Taxa de 0.3%");
    c.atualiza(0.3);
    System.out.println("Saldo R$: " + c.getSaldo());

    System.out.println("\n");
    ContaCorrente cc = new ContaCorrente(1000.0);
    System.out.println("Saldo CC R$: " + cc.getSaldo());
    System.out.println("Taxa CC de 0.25%");
    cc.atualiza(0.25);
    System.out.println("Saldo R$: " + cc.getSaldo());

    System.out.println("\n");
    ContaPoupanca cp = new ContaPoupanca(1000.0);
    System.out.println("Saldo CP R$: " + cp.getSaldo());
    System.out.println("Taxa CP de 0.25%");
    cp.atualiza(0.25);
    System.out.println("Saldo CPR$: " + cp.getSaldo());
    System.out.println("Deposito CP de R$: 250,00");
    cp.deposita(250.00);
    System.out.println("Saldo CPR$: " + cp.getSaldo());

    System.out.println("\n");
    ContaCorrente c1 = new ContaCorrente(1000);
    ContaCorrente cc1 = new ContaCorrente(1000);
    ContaPoupanca cp1 = new ContaPoupanca(1000);

    // c1.atualiza(0.01);
    // cc1.atualiza(0.01);
    // cp1.atualiza(0.01);

    System.out.println("\n");
    System.out.println("Saldo C1 R$: " + c1.getSaldo());
    System.out.println("Saldo CC1 R$: " + cc1.getSaldo());
    System.out.println("Saldo CP1 R$: " + cp1.getSaldo());

    AtualizadorDeContas att = new AtualizadorDeContas(0.01);
    // att.roda(c);
    att.roda(c1);
    // att.roda(cp);
    att.roda(cp1);
    // att.roda(cc);
    att.roda(cc1);

    System.out.println("Saldo total R$: " + att.saldoTotal());

    c1.bar();
  }

  public static void testaTributavel() {
    ContaCorrente cc = new ContaCorrente(1000);
    System.out.println("Saldo total R$: " + cc.getSaldo());
    System.out.println(cc.calculaTributos());
    System.out.println("Saldo total R$: " + cc.getSaldo());

    Tributavel t = cc;
    System.out.println(t.calculaTributos());
    // System.out.println("Saldo total R$: " + t.getSaldo());

  }
}
