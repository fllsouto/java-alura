package src.interfaces;

class Quadrado implements AreaCalculavel {
  private int lado;
  private String tipo = "Quadrado";

  public Quadrado(int lado) {
    this.lado = lado;
  }

  public double calculaArea() {
    return this.lado * this.lado;
  }

  public String getTipo() { return this.tipo; }
}
