package src;

interface Tributavel {
  double calculaTributos();
}

abstract class Conta {
  protected double saldo;

  public Conta(double saldo) {
    this.saldo = saldo;
  }

  public double getSaldo() {
    return saldo;
  }

  public void deposita(double deposito) {
    if(deposito > 0) {
      saldo += deposito;
    }
  }

  public void saca(double valor) {
    if(saldo >= valor) {
      saldo -= valor;
    }
  }
  public void bar() {
    System.out.println("Conta#bar");
    this.foo();
  }
  public abstract void foo();

  public abstract void atualiza(double taxa);
  // public void atualiza(double taxa) {
  //   if(taxa > 0) {
  //     saldo += saldo * taxa;
  //   }
  // }
}

class SeguroDeVida implements Tributavel {
  public double calculaTributos() {
    return 42;
  }
}

class ContaCorrente extends Conta implements Tributavel {

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
}

class ContaPoupanca extends Conta {

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

class GerenciadorDeImportoDeRenda {
  private double total;

  void adiciona(Tributavel t) {
    System.out.println("Adicionando tributavel: " + t);

    this.total += t.calculaTributos();
  }

  public double getTotal() {
    return this.total;
  }
}

class AtualizadorDeContas {
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
public class TesteConta {
  public static void main(String[] args) {
    testaTributavel();
  }

  public void testaConta() {
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
    System.out.println("Saldo total R$: " + t.getSaldo());

  }
}
