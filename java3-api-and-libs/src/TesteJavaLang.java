package src;

public class TesteJavaLang {
  public static void main(String[] args) {
    Walter ww = new Walter("Walter White");
    Pinkman jp = new Pinkman("Jesse Pinkman");

    MethLab ml = new MethLab();

    ml.adiciona(ww);
    ml.adiciona(jp);

    // Preciso fazer o type casting
    // Object wwo = ml.pega(0);
    Walter wwo = (Walter) ml.pega(0);
    wwo.talk();

    // Estou fazendo um downcasting, saindo da classe Object e indo para a classe filha
    // Object jpo = ml.pega(1);

    // Esse trecho gera uma ClassCastException
    // Exception in thread "main" java.lang.ClassCastException:
    // Walter jpo = (Walter) ml.pega(1);

    Pinkman jpo = (Pinkman) ml.pega(1);
    jpo.talk();
  }
}
class MethLab {
    private Object[] arrayDeObjetos = new Object[20];
    private int posicao = 0;

    public void adiciona(Object obj) {
      this.arrayDeObjetos[this.posicao++] = obj;
    }

    public Object pega(int indx) {
      return this.arrayDeObjetos[indx];
    }
}


class Walter {
  private String name;

  public Walter(String name) {
    this.name = name;
  }

  public void talk() {
    System.out.println("Say my name: " + name);
  }
}

class Pinkman {
  private String name;

  public Pinkman(String name) {
    this.name = name;
  }

  public void talk() {
    System.out.println("Yo, Bitch! I ma : " + name);
  }
}
