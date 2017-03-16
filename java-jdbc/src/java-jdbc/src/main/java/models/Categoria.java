package models;

public class Categoria {

	private final Integer id;
	private final String nome;
	
	public Categoria(int id, String nome) {
		this.id = id;
		this.nome = nome;
		
	}

	public String getNome() {
		return this.nome;
	}
}
