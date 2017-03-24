## Java Servlets

Um **Servlet** é uma classe java, desenvolvida seguindo uma estrutura bem definida e que **extende as funcionalidades** de um servidor. Quando essa classe é instalada e configurada em um servidor que suporta Servlets, como o Tomcat (**Servlet Container**) ele passa a poder receber requisições de clientes Web, como por exemplo Browsers. Ao receber uma requisição um servlet pode capturar a entrada, aplicar qualquer tipo de processamento ou regra de negócio e devolver uma página html como resposta.

referência: [Stack Overflow BR](http://pt.stackoverflow.com/questions/91620/o-que-%C3%A9-um-servlet-e-para-que-serve)

Um applet era um tipo de programa, feito em Java, que era executado no Browser. Isso era feito em uma época que as tecnologias da internet estavam começando.

O Tomcat surgiu como um software derivado do Apache, e serve como um container de execução de aplicações Web. Um [**Web container**](https://en.wikipedia.org/wiki/Web_container) é um componente de um servidor web, responsável por **gerenciar o ciclo de vida de um servlet**, mapear uma URL para um servlet particular e assegurar que as requisições para esse recurso tem os direitos corretos de acesso.

Uma classe java para ser um Servlet precisa extender a classe pai **HttpServlet**, como no exemplo a seguir:

```java
// Criamos um mapeamento de rotas usando anotações
@WebServlet(urlPatterns="/busca")
public class BuscaEmpresa extends HttpServlet {

	/**
	 *
	 */
  // Esse valor nos ajuda a serializar a classe, é mandatório fazer isso. O eclipse pode gerar ele para nós
	private static final long serialVersionUID = 1L;

  // A classe pai implementa esse método, que na verdade não fazer nada de mais. O eclipse na sobrescrita desse método irá adicionar a chamada para o pai com um 'super',
  // podemos descartar essa chamada e ficar apenas com a definição do método escrito por nós
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { ... }
  // req é a variável que guarda as informações da requisição feita pelo usuário
  // resp é a variável que guarda as informações da resposta que será enviada para o usuário
```

Através da URL podemos passar parâmetros:

```java
http://localhost:8080/gerenciador/busca?filtro=Ca

// Nosso parâmetro está em req.getParameters(chave)
// no nosso caso: req.getParameters("filtro")
```

Os diferentes métodos do HTTP pode ser utilizados:

- doGet
- doPost
- doHead

Caso queiramos utilizar o método POST precisamos explicitar na requisição, através do parâmetro **method**:

```
<html>
	<body>
		<form action="novaEmpresa" method="post">
			Nome: <input type="text" name="nome" /><br/>
			<input type="submit" value="Enviar">
		</form>
	</body>
</html>
```

A ideia de usar o GET é retornar recursos que não irão impactar/ter side-effects o/no estado do servidor.

GET:
- Podemos cachear resultados, tanto no cliente, proxies e servidor
- Tamanho limitado de URI pelo servidor e pelos proxies intermediadores
- O cache pode previnir que o método seja executado, levando a resultados inesperados
- O cabeçalho da requisição não aceita conteúdo binário, então não poderiamos usar um GET no upload de uma imagem
- Não deve criar side-effects no servidor

Devemos usar o POST quando queremos submeter dados para o servidor, como formulários.  Diferente do GET, uma requisição POST não envia seus dados na URI, isso possibilita que a requisição trafegue muito mais dados. O POST é o método correto para alterar o estado do servidor. Por padrão o POST não é cacheado pelo servidor.

### Filtos em Servlets

Podemos criar uma camada anterior ao servlet que irá processar uma requisição. Essa camada é um filtro, e a criamos extendendo a classe **Filter** e fazendo o mapeamento para uma URL:

```java
// Aqui definimos qual URL será mapeada para esse filtro
// FiltroDeAuditoria.java
@WebFilter(urlPatterns="/*")
public class FiltroDeAuditoria implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		System.out.println("Usuario acessando a URI " + req.getRequestURI());
		// Continua a cadeia de execução
		chain.doFilter(request, response);
	}
}
// Login.java
@WebServlet(urlPatterns="/login")
public class Login extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter writer = resp.getWriter();

		String email = req.getParameter("email");
		String senha = req.getParameter("senha");

		Usuario usuario = new UsuarioDAO().buscaPorEmailESenha(email, senha);
		if(usuario == null) {
			writer.println("<html><body>Usuário ou senha inválido</body></html>");

		} else {
			// Criação do Cookie para salvar dados
			Cookie cookie = new Cookie("usuario.logado", email);
			resp.addCookie(cookie);
			writer.println("<html><body>Usuário logado como: " + email +"</body></html>");
		}
	}
}
```

Podemos controlar o tempo de vida de cookie com o método `cookie.setMaxAge(seconds)`. Normalmente o cookei só expira quando o navegador é fechado, mas setando um tempo de vida podemos fechar o browser e o cookie continuará a existir.

O cookie tem dois problemas:
- O cliente pode facilmente alterar dados do cookie, levando a um injection de informações no servidor
- A cada requisição o cookie é enviado e recebido pelo servidor, isso pode se tornar oneroso caso o cookie seja muito grande

Uma alternativa mais segura para os Cookies são as Sessões, informações que são guardadas no próprio servidor através da estrutura `HttpSerssion`. A sessão de um usuário poderá armazenar várias chaves e valores. Porém podemos capturar o cookie de um usuário e autenticar com sua sessão.

Referências adicionais:
- [Session hijacking](https://en.wikipedia.org/wiki/Session_hijacking)
- [Prevent session from being replicated when JSESSIONID cookie copied](http://stackoverflow.com/questions/35579283/prevent-session-from-being-replicated-when-jsessionid-cookie-copied)

### Anatomia de um Servlet

Uma Servlet será instanciada na primeira vez que interagirem com seu endpoint. Esse servlet possui três métodos que atuam no seu ciclo de vida:
- O construtor
- O inicializador `init`
- O destruidor `destroy`

Um mesmo servlet será reutilizado entre diversos clientes ao mesmo tempo. Isso pode ser algo perigoso pensando nas condições de corrida entre as requisições. Podemos o exemplo desse problema no código a seguir:

```java
// Duas Threads irão executar o mesmo objeto ao mesmo tempo, ambas interagirão com a variáveil filtro e existirá uma disputa entre elas
// http://stackoverflow.com/questions/12388012/how-can-two-threads-accessing-two-methods-of-the-same-object-one-after-another-i
@WebServlet(urlPatterns = "/busca")
public class BuscaEmpresa extends HttpServlet {

    public BuscaEmpresa() {
        System.out.println("Instanciando uma Servlet do tipo BuscaEmpresa "
                + this);
    }

    String filtro;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();
        writer.println("<html>");
        writer.println("<body>");
        writer.println("Resultado da busca:<br/>");
        writer.println("</body>");
        writer.println("</html>");

        filtro = req.getParameter("filtro");
```

Por causa da possível concorrência por recursos devemos evitar variáveis de instância. Diferentes threads estarão em execução ao mesmo tempo e utilizarão o mesmo objeto concorrentemente.
