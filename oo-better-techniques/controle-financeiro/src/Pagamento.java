import java.util.Calendar;

public class Pagamento {

	private String pagador;
	private Documento documentoPagador;
	private double valor;
	private Calendar data;

	public Pagamento(String nomePagador, Documento documentoPagador, double valor) {
		this.pagador = nomePagador;
		this.documentoPagador = documentoPagador;
		this.valor = valor;
	}
	public String getPagador() {
		return pagador;
	}
	public Documento getCnpjPagador() {
		return this.documentoPagador;
	}
	public double getValor() {
		return valor;
	}
	public Calendar getData() {
		return this.data;
	}
	public Documento getDocumentoPagador() {
		return this.documentoPagador;
	}
	public void setData(Calendar data) {
		this.data = data;
	}
	
	
}
