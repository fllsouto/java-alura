package br.com.alura.gerenciador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout implements Tarefa {

	@Override
    public String executa(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.removeAttribute("usuarioLogado");
//		resp.sendRedirect("logout.html");
		return "/WEB-INF/paginas/logout.html";
	}
	

}
