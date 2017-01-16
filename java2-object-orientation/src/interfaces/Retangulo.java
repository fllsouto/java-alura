package src.interfaces;

class Retangulo implements AreaCalculavel {
  private int largura;
  private int altura;
  private String tipo = "Retangulo";

  public Retangulo(int largura, int algura) {
    this.largura = largura;
    this.altura = algura;
  }

  public double calculaArea() {
    return this.largura * this.altura;
  }

  public String getTipo() { return this.tipo; }
}
