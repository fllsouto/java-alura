package src.mensagens;

import java.util.*;

public class ProduzMensagens implements Runnable {

    private int comeco;
    private int fim;
    private Collection<String> mensagens;

    public ProduzMensagens(int comeco, int fim, Collection<String> mensagens) {
      this.comeco = comeco;
      this.fim = fim;
      this.mensagens = mensagens;
    }

    // Problema para inserir os registros, algumas posições serão puladas e isso vai causar um index out of bounds
    // public void run() {
    //   for(int i = comeco; i < fim; i++) {
    //     mensagens.add("Mensagem " + i);
    //   }
    // }

    // Corrigindo o problema com a diretiva synchronized
    public void run() {
      for(int i = comeco; i < fim; i++) {
        // synchronized(mensagens) {
          mensagens.add("Mensagem " + i);
        // }
      }
    }
}
