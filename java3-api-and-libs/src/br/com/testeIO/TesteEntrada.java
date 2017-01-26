package src.br.com.testeIO;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class TesteEntrada {
  public static void main(String[] args) {
    testeEntradaScanner();
  }

  public static void testeEntradaArquivo() {
    try {
      InputStream is = new FileInputStream("entradaFoo.txt");
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);

      String s;

      while((s = br.readLine()) != null) {
        System.out.println(s);
      }
      br.close();
    } catch(IOException ioe) {
      System.out.println(ioe);
    }
  }

  public static void testeEntradaPadrao() {
    try {
      InputStream is = System.in;
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);

      String s;

      while((s = br.readLine()) != null) {
        System.out.println(s);
      }
      br.close();
    } catch(IOException ioe) {
      System.out.println(ioe);
    }
  }

  public static void testeEntradaPadraoSort() {
    try {
      // Versão encurtada, sem salvar a referência das estruturas intermediarias
      BufferedReader br = new BufferedReader(
                            new InputStreamReader(
                              System.in));
      String s;

      while((s = br.readLine()) != null) {
        System.out.println(s);
      }
      br.close();
    } catch(IOException ioe) {
      System.out.println(ioe);
    }
  }

  public static void testeEntradaScanner() {
    try {
      InputStream is = new FileInputStream("entradaFoo.txt");
      Scanner s = new Scanner(is);

      while(s.hasNextLine()) {
        System.out.println(s.nextLine());
      }
      s.close();
    } catch(IOException ioe) {
      System.out.println(ioe);
    }
  }
}
