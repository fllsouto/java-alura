package src.br.com.empresa.banco.conta;

import src.br.com.empresa.banco.exception.ValorInvalidoException;

public abstract class Conta {
  protected double saldo;
  private int numero;
  private String nomeCliente;

  public Conta(double saldo) {
    this.saldo = saldo;
  }

  public void setNumero(int numero) {
    this.numero = numero;
  }

  public int getNumero() {
    return this.numero;
  }

  public void setNomeCliente(String nome) {
    this.nomeCliente = nome;
  }

  public String getNomeCliente() {
    return this.nomeCliente;
  }


  @Override
  public boolean equals(Object obj) {
    Conta outraConta = (Conta) obj;

    return (this.numero == outraConta.getNumero() && this.nomeCliente.equals(outraConta.getNomeCliente()));
  }

  public double getSaldo() {
    return saldo;
  }

  public void deposita(double deposito) throws ValorInvalidoException{
    if(deposito > 0) {
      saldo += deposito;
    } else {
      throw new ValorInvalidoException("Valor inválido para depósito R$: ", deposito);
    }
  }

  public void saca(double saque) throws ValorInvalidoException{
    if(saque > 0 && saque <= saldo) {
      saldo -= saque;
    } else {
      throw new ValorInvalidoException("Valor inválido para saque R$: ", saque);
    }
  }
  public void bar() {
    System.out.println("Conta#bar");
    this.foo();
  }
  public abstract void foo();

  public abstract void atualiza(double taxa);

  @Override
  public String toString() {
    return "esse objeto é uma conta com saldo R$: " + this.saldo;
  }
  // public void atualiza(double taxa) {
  //   if(taxa > 0) {
  //     saldo += saldo * taxa;
  //   }
  // }
}
