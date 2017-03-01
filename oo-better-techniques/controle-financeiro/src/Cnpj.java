
public class Cnpj implements Documento {
	private String valor;

	public Cnpj() {
		this("");
	}

	public Cnpj(String valor) {
		this.valor = valor;
	}
	@Override
	public boolean ehValido() {
		return primeiroDigitoVerificador() == primeiroDigitoCorreto()
				&& segundoDigitoVerificador() == segundoDigitoCorreto();
	}
	private int primeiroDigitoCorreto() {
		// Extrai o primeiro dígito verificador do CNPJ armazenado
		// no atributo cnpj
		return 1;
	}

	private int primeiroDigitoVerificador() {
		// Extrai o segundo dígito verificador do CNPJ armazenado
		// no atributo cnpj
		return 2;
	}


	private int segundoDigitoCorreto() {
		// Calcula o primeiro dígito verificador correto para
		// o CNPJ armazenado no atributo cnpj
		return 3;
	}

	private int segundoDigitoVerificador() {
		// Calcula o primeiro dígito verificador correto para
		// o CNPJ armazenado no atributo cnpj
		return 4;
	}
	
	public String getValor() {
		return valor;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}
	@Override
	public int hashCode() {
		return this.valor.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cnpj other = (Cnpj) obj;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.valor;
	}
}
