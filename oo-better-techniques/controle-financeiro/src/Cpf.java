
public class Cpf implements Documento {
	private String valor;
	
	public Cpf(String valor) {
		this.valor = valor;
	}

	@Override
	public boolean ehValido() {
		return false;
	}

	@Override
	public String getValor() {
		return this.valor;
	}

	@Override
	public String toString() {
		return this.valor;
	}
}
