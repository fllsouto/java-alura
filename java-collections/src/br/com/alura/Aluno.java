package br.com.alura;

public class Aluno implements Comparator<Aluno>{

	private String nome;
	private int NumeroMatricula;

	public Aluno(String nome, int numeroMatricula) {
		if(nome == null) {
			throw new NullPointerException("O nome não pode ser nulo");
		}
		this.nome = nome;
		NumeroMatricula = numeroMatricula;
	}

	public String getNome() {
		return nome;
	}

	public int getNumeroMatricula() {
		return NumeroMatricula;
	}
	
	@Override
	public String toString() {
		return String.format("[Aluno: %s -- Número de matrícula: %d]", this.nome, this.getNumeroMatricula());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + NumeroMatricula;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		if (NumeroMatricula != other.NumeroMatricula)
			return false;
		if (!nome.equals(other.nome))
			return false;
		return true;
	}
	

}
