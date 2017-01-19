package src.br.com.empresa.banco.conta;

public class ContaPoupanca extends Conta {

  public ContaPoupanca(double saldo) {
    super(saldo);
  }

  @Override
  public void atualiza(double taxa) {
    this.saldo += this.saldo * taxa * 3;
  }

  @Override
  public void foo() {
    System.out.println("ContCorrentea#foo");
  }

  public void deposita(double valor) {
    this.saldo += valor - 0.1;
  }
}
