
public class PagadorDeFuncionario {

	public void pagaFuncionario(Assalariado funcionario) {
		funcionario.depositaNaConta(funcionario.getSalario() + funcionario.getBonificacao());
	}
}
