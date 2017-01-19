package src;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.io.PrintStream;
import java.io.FileNotFoundException;

class TesteEntrada {
  public static void main(String[] args) {
    saida2();
  }
  // Abertura de um arquivo simples
  public static void entrada1() {
    try {

      // Procura o arquivo a partir do local em que a JVM foi chamada
      InputStream in = new FileInputStream("entradaFoo.txt");
      int b = in.read();
      System.out.println(b);
    } catch(IOException io) {
      System.out.println(io);
    }
  }

  // Abertura de um arquivo simples e leitura com um decodificador
  // Está lendo apenas 1 byte, ou seja, o caracter W
  public static void entrada2() {
    try {
      InputStream in = new FileInputStream("entradaFoo.txt");
      InputStreamReader isr = new InputStreamReader(in);
      int b = isr.read();
      char c = (char) isr.read();
      System.out.println(b);
      System.out.println(c);
    } catch(IOException io) {
      System.out.println(io);
    }
  }

  public static void entrada3() {
    try {
      InputStream in = new FileInputStream("entradaFoo.txt");
      InputStreamReader isr = new InputStreamReader(in);
      BufferedReader br = new BufferedReader(isr);
      String s;

      while((s = br.readLine()) != null) {
        System.out.println("str: " + s);
      }
      br.close();
    } catch(IOException io) {
      System.out.println(io);
    }
  }

  public static void entrada4() {
    try {

      InputStream in = System.in;
      InputStreamReader isr = new InputStreamReader(in);
      BufferedReader br = new BufferedReader(isr);
      String s;

      while((s = br.readLine()) != null) {
        System.out.println("str: " + s);
      }
      br.close();
    } catch(IOException io) {
      System.out.println(io);
    }
  }

  public static void saida1() {
    try {
      OutputStream os = new FileOutputStream("saidaFoo.txt");
      OutputStreamWriter osw = new OutputStreamWriter(os);
      BufferedWriter bw = new BufferedWriter(osw);

      bw.write("Waka foo bar 42");
      bw.close();
    } catch(IOException e) {
      System.out.println(e);
    }
  }

  public static void saida2() {
    try {
      Scanner s = new Scanner(System.in);
      PrintStream ps = new PrintStream("saidaFoo2.txt");
      while(s.hasNextLine()) {
        ps.println(s.nextLine());
      }
    } catch(FileNotFoundException fnfe) {
      System.out.println(fnfe);
    }
  }

}


/*
* entradaFoo.txt
*
* Waka foo
* bar
*
*/
