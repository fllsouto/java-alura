package br.com.alura;

public class TestaCurso {

	public static void main(String[] args) {
		Curso javaColecoes = new Curso("Dominando as Coleções do Java", "Paulo Silveira");
		javaColecoes.adiciona(new Aula("Trabalhando com ArrayList", 50));
		javaColecoes.adiciona(new Aula("Revisitando as ArrayLists", 21));
		javaColecoes.adiciona(new Aula("Lista de objetos",15));
		javaColecoes.adiciona(new Aula("Relacionamento de listas e objetos", 10));
		
		System.out.println(javaColecoes.getAulas());
		System.out.println(javaColecoes);
	}
}
