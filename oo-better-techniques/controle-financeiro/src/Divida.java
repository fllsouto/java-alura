import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Divida {

	private double total;
	private String credor;
	private Cnpj cnpjCredor = new Cnpj();
	private Pagamentos pagamentos = new Pagamentos();



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

	public Pagamentos getPagamentos() {
		return this.pagamentos;
	}
	public Cnpj getCnpjCredor() {
		return this.cnpjCredor;
	}
	public void setCnpjCredor(Cnpj cnpjCredor) {
		this.cnpjCredor = cnpjCredor;
	}



}
