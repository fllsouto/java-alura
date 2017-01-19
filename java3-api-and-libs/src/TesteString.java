package src;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;

public class TesteString {
  public static void main(String[] args) {
    // charAtTeste("in a hole in the ground there lived a hobbit");
  }

  public static void charAtReverseTeste(String str) {
    System.out.println(str);
    for(int i = str.length() - 1; i >= 0; i--) {
      System.out.print(str.charAt(i));
    }
  }


  public static void charAtTeste(String str) {
    System.out.println(str);
    for(int i = 0; i < str.length(); i++) {
      System.out.println(str.charAt(i));
    }
  }

  public static void stringMethodsTeste() {
    String str = "in a hole in the ground there lived a hobbit";

    int substring = str.indexOf("hobbit");

    String withoutWhiteSpace = str.replaceAll("\\s", "");

    boolean isEmpty = str.isEmpty();

    int stringLength = str.length();

    System.out.println(str);
    System.out.println("hobbit index: " + substring);
    System.out.println(withoutWhiteSpace);
    System.out.println("Is empty? " + isEmpty);
    System.out.println("String length: " + stringLength);
  }

  public static void hashCodeTeste() {
    String str1 = "Waka";
    String str2 = "Waka";

    String str3 = new String("foo");
    String str4 = new String("foo");

    System.out.println(str1.hashCode());
    System.out.println(str2.hashCode());
    System.out.println("-----");
    System.out.println(str3.hashCode());
    System.out.println(str4.hashCode());

    if(str1 == str1) {
      System.out.println("Iguais!");
    } else{
      System.out.println("Diferentes!");
    }

    if(str3 == str4) {
      System.out.println("Iguais!");
    } else{
      System.out.println("Diferentes!");
    }

    System.out.println("str1 : " + System.identityHashCode(str1));
    System.out.println("str2 : " + System.identityHashCode(str2));
    System.out.println("str3 : " + System.identityHashCode(str3));
    System.out.println("str4 : " + System.identityHashCode(str4));

  }
}
