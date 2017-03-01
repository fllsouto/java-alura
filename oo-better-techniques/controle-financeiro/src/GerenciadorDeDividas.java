
public class GerenciadorDeDividas {

	public void efetuaPagament(Divida divida, String nomePagador, String cnpjPagador, double valor) {
        Pagamento pagamento = new Pagamento();
        pagamento.getCnpjPagador().setValor(cnpjPagador);;
        pagamento.setPagador(nomePagador);
        pagamento.setValor(valor);
        divida.getPagamentos().registra(pagamento);
	}
}
