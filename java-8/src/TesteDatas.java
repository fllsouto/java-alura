import java.text.DateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class TesteDatas {

	public static void main(String[] args) {
		LocalDate hoje = LocalDate.now();
		System.out.println(hoje);
		
		LocalDate olimpiadasJapao = LocalDate.of(2020, Month.JUNE, 5);
		
		int anos = olimpiadasJapao.getYear() - hoje.getYear();
		
		System.out.println(anos);
		
		Period periodo = Period.between(hoje, olimpiadasJapao);
		
		
		LocalDate proximasOlimpiadas = olimpiadasJapao.plusYears(4);
		
		System.out.println(proximasOlimpiadas);
		
		LocalDate futuro = LocalDate.of(2099, Month.JANUARY, 25);
		
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/YYYY");
		String diaDeHoje = hoje.format(pattern);
		System.out.println(diaDeHoje);
	}
}
