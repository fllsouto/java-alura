## Java Spring MVC II

### Indice

### Cacheando métodos

Em uma aplicação Web muitas vezes temos que retornar um conjunto de dados requisitados, e pode ser que esses dados retornados sejam os mesmos, a cada requisição, ou seja, demore para que exista mudança nos dados. Podemos otimizar essa requisição dos dados, evitando que sejam feitas querys no banco de dado pelos mesmos dados repetidas vezes, uma vez que o banco de dados grava suas informações em disco, e o acesso é mais devagar do que a busca de uma informação em memória. Com o Spring é muito fácil fazer uso de cache, para isso utilizamos a anotação `@Cachable`:

```java

@RequestMapping("/")
@Cacheable(value="produtosHome") //Temos que atribuir uma chave para acessar os dados cacheados
public ModelAndView index(){
  List<Produto> produtos = produtoDao.listar();
  ModelAndView modelAndView = new ModelAndView("home");
  modelAndView.addObject("produtos", produtos);
  return modelAndView;
}
```

Apenas colocar essa anotação no método não irá resolver o problema, precisamos configurar o cache, através da nossa classe `AppWebConfiguration`:

```java

@Bean
public CacheManager cacheManager() {
  ...
}
```

Um pequeno problema de quando utilizamos cache é saber quando invalidar ele, para que um novo e mais atualizado seja criado. Para auxiliar isso utilizamos outra anotação do Spring, `@CacheEvict(value="produtosHome", allEntries=true)`, nela dizemos qual chave será invalidada e porça do conjunto.

Embora essa solução resolva nosso problema, o cache provido pelo Spring não é tão flexível quanto queremos, não sendo viável para ambientes de produção. Ele não possibilita o controle da vida útil do cache, muito menos o tamanho máximo do cache. A documentação do Spring nos recomenda o uso do **Guava**, um framework de cache criado pela Google, que acaba sendo mais flexível e fornecendo muitas outras funcionalidades. Sua dependência é:

```xml
// Dependência do Guava
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>18.0</version>
</dependency>

// Dependência que integra o Guava com o Spring
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context-support</artifactId>
    <version>4.1.0.RELEASE</version>
</dependency>


```
```java
// AppWebConfiguration
@Bean
public CacheManager cacheManager() {
    CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(5,
            TimeUnit.MINUTES);
    GuavaCacheManager manager = new GuavaCacheManager();
    manager.setCacheBuilder(builder);
    return manager;
}

// Limparemos o cache quando um novo produto for adicionado
@RequestMapping(method=RequestMethod.POST)
@CacheEvict(value="produtosHome", allEntries=true)
public ModelAndView gravar(MultipartFile sumario, @Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) { ... }

// Podemos adicionar o mesmo cacheamento a página de listagem
@RequestMapping(method=RequestMethod.GET)
@Cacheable(value="produtosHome")
public ModelAndView listar() { ... }

// Porém isso irá gerar um error, a primeira requisição que chegar em um dos dois métodos irá criar um cache. Esse cache manterá fixo a rota de redirecionamento do modelAndView, podendo levar a uma páginas erradas outras requisições.
```

### Retornando respostas no formato JSON

Podemos retornar objetos no formato JSON, isso é útil quando queremos integrar aplicações ou expor nossos endpoints para serem consumidos. O Jackson será a lib usada para fazer essa tarefa (mas poderiamos utilizar outros formatos, como o XML). Para fazer com que um método retorne JSON primeiro temos que anotar ele, para que uma view com o nome solicitado não seja procurado, fazemos isso da seguinte forma:

```java
@RequestMapping("/{id}")
@RespondeBody
public Produto detalheJSON(@PathVariable("id") Integer id) {
  return produtoDao.find(id);
}
```

A anotação `@RespondeBody` diz ao spring que o retorno do método será o corpo da resposta da requisição. O problema dessa abordagem é a duplicação de código, cada método que quisesse retornar dados como JSON teria que ser reimplementado, quebrando totalmente o conceito de DRY-code. Para resolver isso criou-se um padrão conhecido como **Content Negociation**, onde o formato esperado da resposta é definido no endereço da requisição, ou em um parâmetro do header.


```
localhost:8080/casadocodigo/produtos/5      // Retornaria HTML
localhost:8080/casadocodigo/produtos/5.json // Retornaria JSON
```

Para fazer isso temos que configurar na classe `AppWebConfiguration` quem será responsável por fazer essa negociação.

```java
// AppWebConfiguration
@Bean
ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
    List<ViewResolver> viewResolvers = new ArrayList<>();
    viewResolvers.add(interalResourceViewResolver());
    viewResolvers.add(new JsonViewResolver());

    ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
    resolver.setContentNegotiationManager(manager);
    resolver.setViewResolvers(viewResolvers);
    return resolver;
}

// Precisamos implementar também a classe JsonViewResolver

package br.com.casadocodigo.loja.conf;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class JsonViewResolver implements ViewResolver {

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        jsonView.setPrettyPrint(true);
        return jsonView;
    }

}
```

### Integrando o Spring com o Bootstrap

O Bootstrap é um framework para frontend muito popular nos dias de hoje. Sua integração com o Spring é bem fácil, para isso seguiremos os seguintes passos:

1. Copiar os assets do Bootstrap para a pasta `src/main/webapp/WEB-INF/resources`
2. Precisamos configurar qual servlet que irá atentender as requisições do tipo script, estilo, fonte, imagens. Para isso faremos nossa `AppWebConfiguration` extender a classe `WebMvcConfigurationAdapter`, através da qual herdaremos uma coleção de métodos muito úteis.

### Controle de acesso usando o Spring Security

Em nosso sistema temos dois formulários, um para cadastro de novos livros e outro para compara. Seria interessante limitar o acesso dos usuários ao primeiro formulário, assim evitamos que pessoas não autorizadas possam adicionar novos livros em nosso sistema. Para isso faremos uso do Spring Security, um conjunto de classes que cuidam da filtragem de requsições e controle de acesso. Primeiro, adicionamos as dependências ao nosso `pom.xml`. Em seguida adicionamos nossa `SecurityConfiguration.class` ao método `getRootConfigClasses`, no arquivo `ServletSpringMVC.java`. Adicionar ao método root faz com que nossa classe de configuração seja lida logo que o sistema subir.

Fazemos em seguida, que a classe `SecurityConfiguration` estenda `WebSecurityConfigurerAdapter`, através da qual sobrescreveremos o método `configure`. Através desse método é possível configurar o nível de acesso que uma determinada Role terá no sistema. Após isso temos que criar a estrutura de autenticação de usuários. Começamos criando a classe `UsuarioDAO` e `Usuario`, e em seguida a classe `Role`.

Sobrescreveremos o método config de novo na classe `SecurityConfiguration`, mas agora com uma assinatura diferente:

```java

@Autowired // Injeção do spring
private UsuarioDAO usuarioDao;

@Override
protected void configure(HttpSecurity http) throws Exception {
    [...]
}

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(usuarioDao); // Precisamos que nosso Dao implemente a interface UserDetailsService, que é esperada por esse método
}

//UsuarioDAO.java

// Simplesmente substituímos o método find por esse
public  loadUserByUsername(String email) throws UsernameNotFoundException {
    List<Usuario> usuarios = manager.createQuery("SELECT u FROM USER AS u WHERE u.email == :email").setParameter("email", email).getResultList();

    if(usuarios.isEmpty()) {
        throw new UsernameNotFoundException("O usuário " + email + "não foi encontrado");
    }

    return usuarios.get(0);
}

// Além disso a classe Usuario precisa reimplementar alguns métodos
public class Usuario implements UserDetails { ... }
```

Porém, teremos um problema, o método `getRootConfigClasses` carregará inicialmente apenas as configurações da classe `SecurityConfiguration`, mas ela por sua vez faz uso de um DAO, que será carregado por outras classes de configuração em um momento posterior. Para resolver isso teremos que mover todo carregamento para o nível root. Ao tentar subir o sistema teremos um problema, por que a role `ADMIN` não existe. Para criar vamos executar diretamente no console do mysql:


```sql
//  Inserimos uma nova role
insert into Role values ('ROLE_ADMIN');

// E um novo usuário. Pelo fato de estarmos usando o Bcrypt como algoritmo de criptografia podemos usar a calculadora de hash disponível no site :
// https://www.dailycred.com/article/bcrypt-calculator para a nossa senha '123456'
insert into Usuario (email, nome, senha) values ('admin@casadocodigo.com.br', 'Administrador', '$2a$04$qP517gz1KNVEJUTCkUQCY.JzEoXzHFjLAhPQjrg5iP6Z/UmWjvUhq');

// Em seguida relacionamos cada um dos registros adicionados, e estamos pronto para testar o código
insert into Usuario_Role(Usuario_email, roles_nome) values ('admin@casadocodigo.com.br', 'ROLE_ADMIN');
```

Vamos analisar o controle de acesso das rotas, feito na classe `SecurityConfiguration`:

```java
protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests() //Vai definir que o acesso é restrito e definido através das regras do HttpServletRequest
    .antMatchers("/produtos/form").hasRole("ADMIN") // Restringe  para que apenas administradores acessem o formulário de produtos através de qualquer método HTTP
    .antMatchers("/carrinho/**").permitAll() // Permite que qualquer usuário acesse o carrinho e as páginas abaixo dela
    .antMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN") // Restringe  para que apenas administradores acessem a lista de produtos através do método HTTP#POST
    .antMatchers(HttpMethod.GET, "/produtos").hasRole("ADMIN") // Restringe  para que apenas administradores acessem a lista de produtos através do método HTTP#GET
    .antMatchers("/produtos/**").permitAll() // Permite que os usuários acessem acessem a lista de produtos através do de qualquer método HTTP, excluindo os que estão bloqueados
    .anyRequest().authenticated() // Diz que qualquer request, excluindo os definidos anteriormente, devem ser autenticados
    .and().formLogin(); // Diz que a autenticação será através de um formulário
}

// Um outro exemplo está presente no método HttpSecurity#authorizeRequests

http.authorizeRequests()
  .antMatchers("/admin/**").hasRole("ADMIN") // Os matchers são definidos em ordem, aqui primeiro definimos o path /admin/**
  .antMatchers("/**").hasRole("USER") // Em seguida definimos todos os paths restantes que não foram definidos
  .and().formLogin();

http.authorizeRequests()
  .antMatchers("/**").hasRole("USER") // Neste caso estamos mapeando todos os paths de uma vez
  .antMatchers("/admin/**").hasRole("ADMIN") // Por conta disso este segundo mapeamento nunca irá acontecer
```

Fazer o spring security funcionar é passível de falhas, para evitar isso faça com baby steps e sempre teste a cada nova adição.

Para adicionar uma camada de segurança para proteger de ataques de **CSRF** podemos usar a tag `<form:form>..</form:form>` que irá incluir um csrf token para nós.

### Usando arquivos externos de Template

Podemos quebrar nossa view em diversos arquivos externos, isso facilita a reutilização e manutenção do código. Esse processo pode ser feito seguindo esses passos:

1. Copiamos o trecho de código que será reutilizado para um arquivo externo
2. utilizamos a tag `<%@ inculde file="..." %>` para incluir nosso arquivo **.JSP**

O grande problema dessa solução é ter que lembrar de fazer esses inumeros includes. Podemos resolver isso criando um template, que nada mais é do que uma estrutura básica que já está pronto e está só esperando a inclusão do seu miolo.

A criação de templates, bem como de tags, segue uma específicação que deve ser respeitada. A seguir temos um exemplo de template:

```html
<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="titulo" required="true" %> // Aqui estamos definindo que cada view deve definir essa variável, caso isso não aconteça um erro será lançado
<%@ attribute name="bodyClass" required="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <title>${titulo} - Casa do Código</title>
    .
    .
    .
</head>
<body>

    <%@ include file="/WEB-INF/views/cabecalho.jsp" %>

    <jsp:doBody />

    <%@ include file="/WEB-INF/views/rodape.jsp" %>

</body>
</html>
```

Podemos também usar fragments no nosso template, que nada mais são do que pedaços de script que executarão alguma função específica. Para usar fragments temos que seguir a seguinte estrutura:

```html
...
<%@ attribute name="extraScripts" fragment="true"%>
<!DOCTYPE html>
<html>
  <head>
...  
  <body>

      <%@ include file="/WEB-INF/views/cabecalho.jsp" %>

      <jsp:doBody />
      <jsp:invoke fragment="extraScripts"></jsp:invoke>

      <%@ include file="/WEB-INF/views/rodape.jsp" %>

  </body>
</html>


// Temos que envolver o conteúdo e o script entre tags específicas
<tags:pageTemplate titulo="xxx">

	<jsp:attribute name="extraScripts">...</jsp:attribute>
	<jsp:body> ...</jsp:body>
</tags:pageTemplate >
```

### Testando a aplicação

Podemos escrever testes automatizados para nossas classes. Através deles podemos assegurar que nossa aplicação continuará funcionando a medida que adicionemos novas funcionalidades. Nosso objetivo será escrever um teste de unidade para um método implementado na classe ProdutoDAO:

```java
// ProdutoDAO

public BigDecimal somaPrecosPorTupe(TipoPreco tipoPreco) {
    TypedQuery<BigDecimal> query = manager.createQuery("select sum(preco.valor) from Produto p join p.precos preco where preco.tipo = :tipoPreco", BigDecimal.class);
    query.setParameter("tipoPreco", tipoPreco);
    return query.getSingleResult();
}

// ProdutoBuilder
...
public class ProdutoBuilder {

    private List<Produto> produtos = new ArrayList<>();

    private ProdutoBuilder(Produto produto) {
        produtos.add(produto);
    }

    public static ProdutoBuilder newProduto(TipoPreco tipoPreco, BigDecimal valor) {
        Produto livro = create("livro 1", tipoPreco, valor);
        return new ProdutoBuilder(livro);
    }

    public static ProdutoBuilder newProduto() {
        Produto livro = create("livro 1", TipoPreco.COMBO, BigDecimal.TEN);
        return new ProdutoBuilder(livro);
    }

    private static Produto create(String nomeLivro, TipoPreco tipoPreco, BigDecimal valor) {
        Produto livro = new Produto();
        livro.setTitulo(nomeLivro);
        livro.setDataLancamento(Calendar.getInstance());
        livro.setPaginas(150);
        livro.setDescricao("Livro top sobre testes");
        Preco preco = new Preco();
        preco.setTipo(tipoPreco);
        preco.setValor(valor);
        livro.getPrecos().add(preco);
        return livro;
    }

    public ProdutoBuilder more(int number) {
        Produto base = produtos.get(0);
        Preco preco = base.getPrecos().get(0);
        for (int i = 0; i < number; i++) {
            produtos.add(create("Book " + i, preco.getTipo(), preco.getValor()));
        }
        return this;
    }

    public Produto buildOne() {
        return produtos.get(0);
    }

    public List<Produto> buildAll() {
        return produtos;
    }
}
```

Também usamos um **Builder** para construir uma série de objetos para nos ajudar a testar. Um problema que teremos ao testar a classe ProdutoDAO é que o `EntityManager` utilizado por ela é criado **apenas dentro do contexto do Spring**, por causa dessa dependência teriamos que "simular" esse contexto apenas para testar nossa classe, algo que envolve muita complexidade desnecessária. Para resolver isso faremos uso do SpringTest, aliado a duas anotações `@RunWith` e `@ContextConfiguration`. Então, declaramos o nosso `produtoDAO` como uma dependência que será injetada pela Spring através do Autowired.

Se executarmos o teste receberemos um erro. Isso acontece por que estamos usando o mesmo banco de desenvolvimento, precisamos ter um banco específico para testes, de forma que ele seja limpo a cada nova execução, isso evita que dados desconhecidos influenciem nossos testes.

Teremos que separar as execuções em profiles diferentes, isso garante que o chaveamento entre bancos ocorra corretamente.
