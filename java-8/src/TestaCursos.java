import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

class Curso {
	private String nome;
	private int alunos;

	public Curso(String nome, int alunos) {
		super();
		this.nome = nome;
		this.alunos = alunos;
	}

	public String getNome() {
		return nome;
	}

	public int getAlunos() {
		return alunos;
	}

	public static boolean moreThan100Students(Curso c) {
		return c.getAlunos() > 100;
	}

	public static boolean moreThan50Students(Curso c) {
		return c.getAlunos() > 50;
	}

}

public class TestaCursos {

	public static void main(String[] args) {
		List<Curso> cursos = new ArrayList<Curso>();
		cursos.add(new Curso("Python", 45));
		cursos.add(new Curso("JavaScript", 150));
		cursos.add(new Curso("Java 8", 113));
		cursos.add(new Curso("C", 55));

		cursos.stream()
				.filter(Curso::moreThan50Students)
				.map(Curso::getNome)
				.forEach(c -> System.out.println("Curso: " + c));
		System.out.println("----");
		cursos.stream()
				.filter(c -> c.getAlunos() >= 100)
				.findAny()
				.ifPresent(c -> System.out.println(c.getNome()));

//		Optional<Curso> optional = cursos.stream()
//				.filter(c -> c.getAlunos() >= 200)
//				.findAny();
		
		OptionalDouble average = cursos.stream().mapToInt(c -> c.getAlunos()).average();
		System.out.println("Média: " + average.getAsDouble());
		
		List<Curso> maisDeCinquenta = cursos.stream().filter(c -> c.getAlunos() > 50).collect(Collectors.toList());
		maisDeCinquenta.forEach(c -> System.out.println(c.getNome()));
		// cursos.sort(Comparator.comparing(Curso::getAlunos));

		// cursos.forEach(System.out::println);

		// cursos.stream()
		// .filter(Curso::moreThan50Students) // Precisa ser static
		// .map(Curso::getAlunos) // Pode ser método de instancia
		// .forEach(a -> System.out.println("Alunos: " + a));
	}
}
