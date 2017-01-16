package src.interfaces;

class InterfaceFoo {
  public static void chamaMetodo(AreaCalculavel a) {
    System.out.println("Tipo: " + a.getTipo());
    System.out.println("Area: " + a.calculaArea());
    System.out.println("\n");
  }
}

public class TesteInterface {
  public static void main(String[] args) {
    AreaCalculavel q = new Quadrado(4);
    AreaCalculavel r = new Retangulo(3,4);
    AreaCalculavel c = new Circulo(5);

    InterfaceFoo.chamaMetodo(q);
    InterfaceFoo.chamaMetodo(r);
    InterfaceFoo.chamaMetodo(c);
  }
}
