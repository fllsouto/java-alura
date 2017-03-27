package br.com.alura.gerenciador;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.alura.gerenciador.dao.EmpresaDAO;

public class BuscaEmpresa implements Tarefa {

	@Override
	public String executa(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String filtro = req.getParameter("filtro");
		Collection<Empresa> empresas = new EmpresaDAO().buscaPorSimilaridade(filtro);
		req.setAttribute("empresas", empresas);
		return "/WEB-INF/paginas/buscaEmpresa.jsp";
	}

}
