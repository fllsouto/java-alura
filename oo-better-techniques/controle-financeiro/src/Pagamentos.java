import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Pagamentos{
	private double valorPago;
	private List<Pagamento> pagamentos = new ArrayList<Pagamento>();
	public ArrayList<Pagamento> pagamentosAntesDe(Calendar data) {
		ArrayList<Pagamento> pagamentosFiltrados = new ArrayList<Pagamento>();
		for (Pagamento pagamento : this.pagamentos) {
			if (pagamento.getData().before(data)) {
				pagamentosFiltrados.add(pagamento);
			}
		}
		return pagamentosFiltrados;
	}

	public ArrayList<Pagamento> pagamentosComValorMaiorQue(double valorMinimo) {
		ArrayList<Pagamento> pagamentosFiltrados = new ArrayList<Pagamento>();
		for (Pagamento pagamento : this.pagamentos) {
			if (pagamento.getValor() > valorMinimo) {
				pagamentosFiltrados.add(pagamento);
			}
		}
		return pagamentosFiltrados;
	}

	public ArrayList<Pagamento> pagamentosDo(String cnpjPagador) {
		ArrayList<Pagamento> pagamentosFiltrados = new ArrayList<Pagamento>();
		for (Pagamento pagamento : this.pagamentos) {
			if (pagamento.getCnpjPagador().equals(cnpjPagador)) {
				pagamentosFiltrados.add(pagamento);
			}
		}
		return pagamentosFiltrados;
	}


	public void registra(Pagamento pagamento) {
		paga(pagamento);
		this.pagamentos.add(pagamento);
	}

	private void paga(Pagamento pagamento) {
		double valor = pagamento.getValor();
		if (valor < 0) {
			throw new IllegalArgumentException("Valor invalido para pagamento");
		}
		if (valor > 100) {
			valor = valor - 8;
		}
		this.valorPago += valor;
	}

	public double getValorPago() {
		return this.valorPago;
	}
}
