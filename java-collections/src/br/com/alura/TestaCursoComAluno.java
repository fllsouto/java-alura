package br.com.alura;

public class TestaCursoComAluno {

	public static void main(String[] args) {
		Curso javaColecoes = new Curso("Dominando as Coleções do Java", "Paulo Silveira");
		javaColecoes.adiciona(new Aula("Trabalhando com ArrayList", 50));
		javaColecoes.adiciona(new Aula("Revisitando as ArrayLists", 21));
		javaColecoes.adiciona(new Aula("Lista de objetos",15));
		javaColecoes.adiciona(new Aula("Relacionamento de listas e objetos", 10));
		
		Aluno a1 = new Aluno("Rodrigo Turini", 34762);
		Aluno a2 = new Aluno("Guilherme Silveira", 5617);
		Aluno a3 = new Aluno("Mauricio Aniche", 17645);
		
		javaColecoes.matricula(a1);
		javaColecoes.matricula(a2);
		javaColecoes.matricula(a3);
		
		javaColecoes.getAlunos().forEach( aluno -> {
			System.out.println(aluno);
		});
		
		System.out.println("O aluno" + a1 + " está matriculado?");
		System.out.println(javaColecoes.estaMatriculado(a1));
		
		Aluno turini = new Aluno("Rodrigo Turini", 34762);
		
		
		System.out.println("O aluno" + turini + " está matriculado [2]?");
		System.out.println(javaColecoes.estaMatriculado(turini));
		
//		Aluno alunoNull = new Aluno(null, 17711);
	}
}
