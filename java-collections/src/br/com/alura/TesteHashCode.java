package br.com.alura;

public class TesteHashCode {

	public static void main(String[] args) {
		System.out.println("casa has code: " + "casa".hashCode());
		System.out.println(String.format("c: %d", (int)'c'));
		System.out.println(String.format("a: %d", (int)'a'));
		System.out.println(String.format("s: %d", (int)'s'));
		System.out.println(String.format("a: %d", (int)'a'));
		
		System.out.println(String.format("Max integer value: %d", Integer.MAX_VALUE + 2));
		System.out.println(String.format("Min integer value: %d", Integer.MIN_VALUE));
		
		printHashCode("hello world");
		printHashCode("           ");
		printHashCode("            ");
		printHashCode("             ");
		printHashCode("~~~~~~~~~~~");
		printHashCode("123456");
		printHashCode("José da Silva");
	}
	
	public static void printHashCode(String str) {
		System.out.println(String.format("String: %s -- HC: %d", str, str.hashCode()));
	}
}
