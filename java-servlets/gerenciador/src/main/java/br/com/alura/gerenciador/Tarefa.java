package br.com.alura.gerenciador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Tarefa {

    String executa(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

}
