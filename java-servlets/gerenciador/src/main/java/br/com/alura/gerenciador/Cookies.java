package br.com.alura.gerenciador;

import javax.servlet.http.Cookie;

public class Cookies {

	private final Cookie[] cookies;

	public Cookies(Cookie[] cookies) {
		this.cookies = cookies;
	}
	
	public Cookie getUsuarioLogado() {
		for (Cookie cookie : this.cookies) {
			String cookieValue = cookie.getName();
			if(cookieValue != null && cookieValue.equals("usuario.logado")) {
				return cookie;
			}
		}
		return null;
	}
	
	
}
