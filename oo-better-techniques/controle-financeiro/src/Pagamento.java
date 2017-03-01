import java.util.Calendar;

public class Pagamento {

	private String pagador;
	private Cnpj cnpjPagador = new Cnpj();
	private double valor;
	private Calendar data;
	public void setData(Calendar data) {
		this.data = data;
	}
	public String getPagador() {
		return pagador;
	}
	public void setPagador(String pagador) {
		this.pagador = pagador;
	}
	public Cnpj getCnpjPagador() {
		return this.cnpjPagador;
	}

	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public Calendar getData() {
		return this.data;
	}
	
	
}
