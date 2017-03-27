package br.com.alura.gerenciador;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns="/*")
public class FiltroDeAuditoria implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		String usuario = "<deslogado>";
		
		if(usuarioLogado != null) {
			usuario = usuarioLogado.getEmail();
		}
		System.out.println("Usuario " + usuario + " acessando a URI " + req.getRequestURI());
		chain.doFilter(req, res);
	}
}
