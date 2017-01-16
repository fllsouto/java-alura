package src;

class GrandMother {
  public void sayHello1() {
    System.out.println("Classe GrandMother");
  }

  public void sayHello2() {
    System.out.println("Classe GrandMother");
  }

  public void sayHello3() {
    System.out.println("Classe GrandMother");
  }

  public void sayHello4() {
    System.out.println("Classe GrandMother");
  }

}

class Mother extends GrandMother {
  public void sayHello1() {
    System.out.println("Classe Mother");
  }

  // public void sayHello2() {
  //   // System.out.println("Classe Mother");
  // }

  public void sayHello4() {
    System.out.println("Classe Mother");
  }
}

class Daughter extends Mother{
  public void sayHello1() {
    System.out.println("Classe Daughter");
  }

  public void sayHello2() {
    super.sayHello2();
    // System.out.println("Classe Daughter");
  }

  public void sayHello3() {
    System.out.println("Classe Daughter");
  }

  // public void sayHello4() {
  //   System.out.println("Classe Daughter");
  // }

}

public class TestePolimorfismo {
  public static void main(String[] args) {
    GrandMother gm = new GrandMother();
    Mother m = new Mother();
    Daughter d = new Daughter();

    System.out.println("\nSayHello1");
    gm.sayHello1();
    m.sayHello1();
    d.sayHello1();

    System.out.println("\nSayHello2");
    gm.sayHello2();
    m.sayHello2();
    d.sayHello2();

    System.out.println("\nSayHello3");
    GrandMother gm2 = d;
    d.sayHello3();
    // Erro de compilação se eu não definir o método na superclasse
    gm2.sayHello3();

    System.out.println("\nSayHello4");
    GrandMother gm3 = d;
    // A classe Daughter não possui o método implementado, então irá subir até a classe mãe
    d.sayHello4();
    // Mesmo eu utilizando uma variável do tipo GrandMother o objeto ainda é uma Daughter, ambos
    // entendem a mensagem sayHello4, porém a implementação usada será a da classe Mother
    gm3.sayHello4();
  }
}

/*
* SayHello1
* Classe GrandMother
* Classe Mother
* Classe Daughter
*
* SayHello2
* Classe GrandMother
* Classe GrandMother
* Classe GrandMother
*
* SayHello3
* Classe Daughter
* Classe Daughter
*
* SayHello4
* Classe Mother
* Classe Mother
*/
