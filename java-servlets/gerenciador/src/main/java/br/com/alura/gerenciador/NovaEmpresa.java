package br.com.alura.gerenciador;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.alura.gerenciador.dao.EmpresaDAO;

public class NovaEmpresa implements Tarefa{

	@Override
	public String executa(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String nomeEmpresa = req.getParameter("nome");

		if(nomeEmpresa != null && !nomeEmpresa.isEmpty()) {
			Empresa novaEmpresa  = new Empresa(nomeEmpresa);
			EmpresaDAO empresaDAO = new EmpresaDAO();
			empresaDAO.adiciona(novaEmpresa);
			req.setAttribute("empresa", novaEmpresa);
			return "/WEB-INF/paginas/novaEmpresa.jsp";			
		} else {
		    throw new IllegalArgumentException("Parâmetros passados errado");
		}

	}

	
}
