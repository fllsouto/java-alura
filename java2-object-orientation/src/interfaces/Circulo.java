package src.interfaces;

class Circulo implements AreaCalculavel {
  private int raio;
  private String tipo = "Circulo";

  public Circulo(int raio) {
    this.raio = raio;
  }

  public double calculaArea() {
    return Math.PI * (this.raio * this.raio);
  }

  public String getTipo() { return this.tipo; }
}
