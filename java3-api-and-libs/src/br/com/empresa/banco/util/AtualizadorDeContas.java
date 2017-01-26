package src.br.com.empresa.banco.util;

import src.br.com.empresa.banco.conta.*;


public class AtualizadorDeContas {
  private double saldoTotal = 0;
  private double selic;

  public AtualizadorDeContas(double selic) {
    this.selic = selic;
  }

  public void roda(Conta c) {
    System.out.println("Saldo anterior R$: " + c.getSaldo());
    c.atualiza(selic);
    System.out.println("Saldo final R$: " + c.getSaldo());
    saldoTotal += c.getSaldo();
  }

  public double saldoTotal() {
    return this.saldoTotal;
  }
}
