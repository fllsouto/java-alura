
public class GerenciadorDeDividas {

	public void efetuaPagament(Divida divida, String nomePagador, Documento documentoPagador, double valor) {
        Pagamento pagamento = new Pagamento(nomePagador, documentoPagador, valor);
        divida.registra(pagamento);
	}
}
