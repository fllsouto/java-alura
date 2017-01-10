class Empresa {
	private String nome;
	private String cnpj;
	private String endereco;
	private Funcionario[] funcionarios;
	private int fIndx;
	public Empresa(String nome, String cnpj, String endereco, int qntFuncionarios) {
		this.nome = nome;
		this.cnpj = cnpj;
		this.endereco = endereco;
		funcionarios = new Funcionario[qntFuncionarios];
		this.fIndx = 0;
	}

	public void insereFuncionario(Funcionario funcionario) {
		this.funcionarios[this.fIndx++] = funcionario;
	}

	public void mostraFuncionarios() {
		for(Funcionario f: this.funcionarios) {
			f.mostra();
		}
	}

	public boolean contem(Funcionario f) {
		for(Funcionario ff: this.funcionarios) {
			if(f.equals(ff)) {
				return true;
			}
		}
		return false;
	}


}


class Data {
	private int dia;
	private int mes;
	private int ano;

	public Data(int dia, int mes, int ano) {
		this.dia = dia;
		this.mes = mes;
		this.ano = ano;
	}

	public String toString() {
		return String.format("%s--%s--%s", this.dia, this.mes, this.ano);
	}

	public String getFormatada() {
		return String.format("%s-|-%s-|-%s", this.dia, this.mes, this.ano);
	}
}

class Funcionario {
	private String nome;
	private String departamento;
	private double salario;
	private Data dataEntrada;
	private String rg;
	private int identificador;

	private static int contaIdentificador = 1;

	public Funcionario(String nome, String departamento, double salario, Data dataEntrada, String rg) {
		this.nome = nome;
		this.departamento = departamento;
		this.salario = salario;
		this.dataEntrada = dataEntrada;
		this.rg = rg;
		this.identificador = Funcionario.novoIdentificador();
	}

	private static int novoIdentificador() {
		// Pré-incremento
		return Funcionario.contaIdentificador++;
	}

	private static int pegaIdentificador() {
		return Funcionario.contaIdentificador;
	}

	public Funcionario(String departamento, double salario, Data dataEntrada, String rg) {
		this("", departamento, salario, dataEntrada, rg);
	}

	public Funcionario() {

	}

	public String getNome() { return this.nome; }
	public String getDepartamento() { return this.departamento; }
	public double getSalario() { return this.salario; }
	public void setSalario(double salario) { this.salario = salario; }
	public Data 	getDataEntrada() { return this.dataEntrada; }
	public String getRg() { return this.rg; }

	public void recebeAumento(double aumento) {
		this.salario += aumento;
	}

	public double calculaGanhoAnual() {
		return (this.salario * 12);
	}

	public void mostra() {
		System.out.println("Nome: " + this.nome);
		System.out.println("Departamento: " + this.departamento);
		System.out.println("Salario: " + this.salario);
		System.out.println("Data de entrada: " + this.dataEntrada.getFormatada());
		System.out.println("RG: " + this.rg);
		System.out.println("ID: " + this.identificador);
		System.out.print("\n");
	}
	// Sobrescrevendo um método
	@Override
	public boolean equals(Object f) {
		if(f == this) return true;
		if(!(f instanceof Funcionario)) return false;

		Funcionario ff = (Funcionario) f;
		if(ff.getNome() != this.getNome()) return false;
		if(ff.getDepartamento() != this.getDepartamento()) return false;
		if(ff.getSalario() != this.getSalario()) return false;
		if(ff.getDataEntrada().getFormatada() != this.getDataEntrada().getFormatada()) return false;
		if(ff.getRg() != this.getRg()) return false;
		return true;
	}


}

public class TesteFuncionario {
	public static void main(String[] args) {
		Data data = new Data(1,1,2017);
		Empresa blackMesa = new Empresa("Black Mesa", "111111.24", "Fort Lauderlade Avenue, Austin, Texas", 2);
		Funcionario fellipe = new Funcionario("Fellipe Sampaio", "Backend", 6000.0, data, "123456789");
		Funcionario joao = new Funcionario("Joao Santos", "PO", 8000.0, data, "1234567844");
		fellipe.mostra();
		joao.mostra();
		// fellipe.recebeAumento(500.0);
		// System.out.println("Ganho anual R$: " + fellipe.calculaGanhoAnual());
		// fellipe.mostra();

		// Funcionario fellipe2 = new Funcionario("Fellipe Sampaio", "Backend", 6000.0, "01/01/2017", "123456789");
		// Funcionario fellipe2 = fellipe;
		// if(fellipe == fellipe2) {
		// 	System.out.println("Iguais");
		// } else {
		// 	System.out.println("Diferentes");
		// }
		// blackMesa.insereFuncionario(fellipe);
		// blackMesa.insereFuncionario(joao);
		// blackMesa.mostraFuncionarios();
		//
		// if(blackMesa.contem(fellipe)) {
		// 	System.out.println("Contem o funcionario");
		// } else {
		// 	System.out.println("Não contem o funcionario");
		// }
	}
}
