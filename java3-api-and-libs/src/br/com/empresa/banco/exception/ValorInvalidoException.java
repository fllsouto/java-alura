package src.br.com.empresa.banco.exception;

public class ValorInvalidoException extends RuntimeException {
  public ValorInvalidoException(String msg, double valor) {
    super(msg + valor);
  }
}
