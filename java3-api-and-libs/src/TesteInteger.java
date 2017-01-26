package src;

import java.io.PrintStream;

public class TesteInteger {
  public static void main(String[] args) {
    syso();
  }
  public static void syso() {
    PrintStream saida = System.out;
    saida.println("Hello saida");
  }
  public static void conversion() {
    String valid = "10";
    String invalid = "abc";

    System.out.println("valid : " + Integer.parseInt(valid));

    // Erro!
    // Exception in thread "main" java.lang.NumberFormatException: For input string: "abc"
    // System.out.println("invalid : " + Integer.parseInt(invalid));
  }

  public static void doubleEqualAndEquals() {
    Integer x1 = new Integer(10);
    Integer x2 = new Integer(10);

    System.out.println("com ==");
    if(x1 == x2) {
      System.out.println("iguais");
    } else {
      System.out.println("diferentes");
    }

    System.out.println("com equals");
    if(x1.equals(x2)) {
      System.out.println("iguais");
    } else {
      System.out.println("diferentes");
    }

    System.out.println("x1 hc: " + x1.hashCode());
    System.out.println("x1 indentity hc: " + System.identityHashCode(x1));
    System.out.println("x2 hc : " + x2.hashCode());
    System.out.println("x2 indentity hc : " + System.identityHashCode(x2));
  }
}
