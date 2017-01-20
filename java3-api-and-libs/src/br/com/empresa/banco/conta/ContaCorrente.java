package src.br.com.empresa.banco.conta;

public class ContaCorrente extends Conta implements Tributavel, Comparable<ContaCorrente> {

  public double calculaTributos() {
    return this.getSaldo() * 0.01;
  }

  public ContaCorrente(double saldo) {
    super(saldo);
  }

  @Override
  public void atualiza(double taxa) {
    this.saldo += this.saldo * taxa * 2;
  }

  @Override
  public void foo() {
    System.out.println("ContCorrente#foo");
  }

  @Override
  public int compareTo(ContaCorrente outra) {
    if(this.saldo < outra.saldo) { return -1; }
    if(this.saldo > outra.saldo) { return 1; }
    return 0;
  }
}
