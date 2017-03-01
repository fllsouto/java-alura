import java.util.HashMap;
import java.util.Map;

public class BalancoEmpresa {
	Map<Cnpj, Divida> dividas = new HashMap<Cnpj, Divida>();
	public void registraDivida(String credor, Cnpj cnpjCredor, double valor) {
		Divida divida = new Divida();
		divida.setCredor(credor);
		divida.setTotal(valor);
		divida.setCnpjCredor(cnpjCredor);
		dividas.put(cnpjCredor, divida);
	}
	
	public void pagaDivida(String cnpjCredor, Pagamento pagamento) {
		Divida divida = dividas.get(cnpjCredor);
		if(dividas != null) {
			divida.getPagamentos().registra(pagamento);
		}
	}
}
