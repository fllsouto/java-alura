package src;

class Funcionario {
  private String nome;
  /*
  * O atributo precisa ser protected para ser acessível pela subclasse, ou precisamos criar um getter
  * private double salario;
  * src/TesteFuncionario.java:16: error: salario has private access in Funcionario
  *   return this.salario * 0.3;
  *              ^
  * 1 error
  */
  protected double salario;


  public void setSalario(double salario) {
    this.salario = salario;
  }

  public double getBonus() {
    return this.salario * 0.2;
  }
}

class Gerente extends Funcionario {
  public double getBonus() {
    return this.salario * 0.3;
  }
}




public class TesteFuncionario {
  public static void main(String[] args) {
    Funcionario joao = new Funcionario();
    joao.setSalario(1000.0);

    Gerente joaquim = new Gerente();
    joaquim.setSalario(2000.0);

    System.out.println("Bonus Joao R$: " + joao.getBonus());
    System.out.println("Bonus Joaquim R$: " + joaquim.getBonus());
  }
}
