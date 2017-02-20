import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class OrdenaStrings {

	public static void main(String[] args) {
		List<String> palavras = new ArrayList<String>();
		palavras.add("Alura online");
		palavras.add("Editora casa do código");
		palavras.add("Caelum");
		Comparator<String> comparator = new ComparadorPorTamanho();
		
//		Collections.sort(palavras, comparator);
		palavras.sort(comparator);
		System.out.println(palavras);
		palavras.forEach(new ConsumidorDeStrings());
		
//		Object o = s -> System.out.println(s);
		
//		new Thread(() -> {
//			System.out.println("Executando um Runnable");
//			}).start();
		
//		Function<String, Integer> funcao = String::hashCode;
		Function<String, Integer> funcao = s -> s.hashCode();
		palavras.sort(Comparator.comparing(funcao));

		// Podemos imprimir com
		palavras.forEach(System.out::println);
	}
}

class ComparadorPorTamanho implements Comparator<String> {

	@Override
	public int compare(String s1, String s2) {
		if(s1.length() < s2.length())
			return -1;
		if(s1.length() < s2.length())
			return 1;
		return 0;
	}
	
}

class ConsumidorDeStrings implements Consumer<String> {
	public void accept(String s) {
		System.out.println("Word: " + s);
	}
}