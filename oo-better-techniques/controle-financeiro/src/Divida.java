public class Divida {

	private double total;
	private String credor;
	private Documento documentoCredor;
	private Pagamentos pagamentos = new Pagamentos();

	public Divida(String credor, Documento documentoCredor, double total) {
		super();
		this.total = total;
		this.credor = credor;
		this.documentoCredor = documentoCredor;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getCredor() {
		return credor;
	}

	public void setCredor(String credor) {
		this.credor = credor;
	}
	public Documento getDocumentoCredor() {
		return this.documentoCredor;
	}

	public void registra(Pagamento pagamento) {
		this.pagamentos.registra(pagamento);
	}



}
