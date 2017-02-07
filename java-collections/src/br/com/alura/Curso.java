package br.com.alura;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Curso {

	private String nome;
	private String instrutor;
	private List<Aula> aulas = new LinkedList<Aula>();

	public Curso(String nome, String instrutor) {
		super();
		this.nome = nome;
		this.instrutor = instrutor;
	}

	public String getNome() {
		return nome;
	}

	public String getInstrutor() {
		return instrutor;
	}

	public List<Aula> getAulas() {
		return Collections.unmodifiableList(aulas);
	}

	public void adiciona(Aula a) {
		this.aulas.add(a);
	}
	
	public int getTempoTotal() {
		return this.aulas.stream().mapToInt(Aula::getTempo).sum();
	}
	
	@Override
	public String toString() {
		return String.format("[Curso: <%s>. tempo total: <%d>, aulas: [\n%s] ]", this.getNome(), this.getTempoTotal(), this.getAulasDescription());
	}

	private String getAulasDescription() {
		String aulas = "";
		for (Aula aula : this.aulas) {
			aulas = aulas + aula + "\n";
		}
		return aulas;
	}

}
