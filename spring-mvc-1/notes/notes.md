## Java Spring MVC I

### Indice

- [Primeiro exemplo](#primeiro-exemplo)
- [Cadastrando produtos - Lidando com persistência de objetos](#cadastrando-produtos---lidando-com-persistencia-de-objetos)
    * [1. Adicionar as dependências no pom.xml](#1-adicionar-as-dependencias-no-pomxml)
    * [2. Anotar a classe que será mapeada no banco](#2-anotar-a-classe-que-sera-mapeada-no-banco)
    * [3. Delegamos a manipulação do objeto](#3-delegamos-a-manipulacao-do-objeto)
    * [4. Fazemos o uso do DAO no controller](#4-fazemos-o-uso-do-dao-no-controller)
    * [5. Habilitando transações](#5-habilitando-transacoes)
- [Retomando os estudos](#retomando-os-estudos)
- [Relacionando Produtos com Preços](#relacionando-produtos-com-precos)
- [Adicionando dados a View](#adicionando-dados-a-view)
- [Configurando o path dos controller e o HTTP method](#configurando-o-path-dos-controller-e-o-http-method)
- [Redirect e apresentação de mensagens com Flash](#redirect-e-apresentacao-de-mensagens-com-flash)
- [Validação de dados](#validacao-de-dados)
- [Apresentando erros e externalizando mensagens](#apresentando-erros-e-externalizando-mensagens)
- [Formatando datas](#formatando-datas)
- [Upload de arquivos](#upload-de-arquivos)
- [Servindo arquivos estáticos](#servindo-arquivos-estaticos)
- [Buscando dados com o DAO](#buscando-dados-com-o-dao)
- [Urls amigaveis](#urls-amigaveis)
- [Lidando com escopo de sessão](#lidando-com-escopo-de-sessao)
- [Transformando uma classe em um Json](#transformando-uma-classe-em-um-json)
- [Fazendo request para serviços externos](#fazendo-request-para-servicos-externos)%


### Primeiro exemplo

- Estamos usando o [Jboss Forge](https://forge.jboss.org/download), um framework para fazer a criação e gestão do projeto Spring MVC. Esse framework utiliza o Maven para gerenciar as dependências do projeto. A vantegem de utilizar o Jboss Forge é a velocidade que podemos conseguir na criação e gerenciamento do projeto. Ele faz a geração de toda estrutura do projeto e sua integração com o Maven, ele configura as dependências, pastas e gera toda configuração envolvendo os XMLs
- Sempre temos que adicionar o projeto ao servidor, isso equivale a fazer o deploy do código no servlet. Fazemos isso clicando sobre o servidor e adicionando a ele o projeto que será deployado. Caso aconteça erro precisamos criar o servidor desde o começo
- Simplesmente acessar uma página index.html não faz uso do spring, precisamos mudar isso, atender a requisição através de um controller
- A conversa com o SpringMVC pode acontecer de duas formas possíveis: arquivo de configuração XML ou anotações. Utilizar anotações deixa o código mais flexível
- Precisamos fazer com que o servidor tomcat repasse as requsições para o SpringMVC, podemos fazer isso através das classes de configuração `AppWebConfiguration` e `ServletSpringMVC`
- Nâo queremos que o usuário tenha acesso direto as páginas web no nosso servidor, apenas digitando a rota delas. Para contornar isso precisamos colocar as páginas dentro da pasta `src/main/webapp/WEB-INF/`, essa é uma pasta protegida que impede o acesso direto dos usuários aos recursos, com isso teremos maior controle de acesso a elas. O acesso é primeiro feito através do controle, e depois acessamos a página
- Podemos configurar o prefixo e o sufixo das pastas que farão parte das views, através de um `InternalResourceViewResolver`
- O Spring faz um _binding_ entre os campos definidos no JSP e os parâmetros criados na view. Para isso precisamos definir o mesmo nome no JSP e no controller, ex:

```html
// produto.jsp
// No formulario temos um campo título
<form>
  <label>Titulo</label>
  </input type="text" name="titulo">
  ...
</form>

// ProdutoController.java

// No controller temos um parâmetro título
// Mesmo nome que foi definido na view, isso cria um binding pelo Spring
public String gravar(..., String titulo, ...) {
  ....
}
```

### Cadastrando produtos - Lidando com persistência de objetos

Como dito anteriormente, podemos criar um binding entre os campos de uma view e os parâmetros recebidos em um controller. O grande problema disso é quantidade de atributos jogados que temos, imagine receber uma requisição com 40 parâmetros. Para resolver isso trafegaremos uma representação do produto, que será definido na camada de model.

```java
// models.Produto;

public class Produto {
    private String titulo;
    private String descricao;
    private int paginas;

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
...    
}

// controllers.ProdutosController

@Controller
public class ProdutosController {

    @RequestMapping("/produtos/form")
    public String form() {
        return "produtos/form";
    }

    @RequestMapping("/produtos")
    public String gravar(Produto produto) {
        System.out.println(produto);
        return "produtos/ok";
    }
}
```

O spring vai tomar conta de gerenciar a criação do objeto e o set dos atributos. Para salvar no banco de dados usaremos o **JPA (Java Persistence API)**, uma especificação que define:
- Uma API de comunicação
- A linguagem de comunicação e persistência JPQL
- Metainformação relacional e de objetos

A implementação do JPA que usaremos é o **Hibernate Framework**. Para começar a utilizar ele integrado com nosso aplicação seguiremos os seguintes passos:

##### 1. Adicionar as dependências no pom.xml

```XML
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-entitymanager</artifactId>
    <version>4.3.0.Final</version>
</dependency>
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core</artifactId>
    <version>4.3.0.Final</version>
</dependency>
<dependency>
    <groupId>org.hibernate.javax.persistence</groupId>
    <artifactId>hibernate-jpa-2.1-api</artifactId>
    <version>1.0.0.Final</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-orm</artifactId>
    <version>4.1.0.RELEASE</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.15</version>
</dependency>
```

##### 2. Anotar a classe que será mapeada no banco

```java
// br.com.casadocodigo.loja.models.Produto

import javax.persistence.Entity;

@Entity
public class Produto {
    [...]
}
```

##### 3. Delegamos a manipulação do objeto

O acesso de dados e manipulação do objeto será realizado por um **DAO (Data Access Object)**. Para que o ProdutoDAO realize a persistencia do objeto no banco de dados seu DAO precisa ter acesso a um gerenciador de entidades. O Spring nos fornece o **EntityManager** para persistir produtos no banco de dados:

```java
// br.com.casadocodigo.loja.daos.ProdutoDAO

@PersistenceContext
private EntityManager manager;

public void gravar(Produto produto) {
    manager.persist(produto);
}
```

##### 4. Fazemos o uso do DAO no controller

O Spring criará para nós o ProdutoDAO através da anotação `@Autowired`. Precisamos também criar uma classe que irá cuidar da configuração do JPA. Precisamos também configurar o factoryBean que será utilizado pelo Spring.

```java
// br.com.casadocodigo.loja.conf;

public class JPAConfiguration {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        factoryBean.setJpaVendorAdapter(vendorAdapter);

        Properties props = new Properties();
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.hbm2ddl.auto", "update");

        factoryBean.setJpaProperties(props);

        factoryBean.setPackagesToScan("br.com.casadocodigo.loja.models");

        return factoryBean;
    }
}

// Adicionamos também a lista de confiuration do Servlet
// br.com.casadocodigo.loja.conf;
return new Class[] { AppWebConfiguration.class , JPAConfiguration.class };


// Por fim, precisamos adicionar o atraibuto id a classe Produto. Podemos também definir a estratégia usada pelo spring para gerar os ids
// br.com.casadocodigo.loja.models;
@Entity
public class Produto {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
...
}
```

##### 5. Habilitando transações

Precisamos criar um **TransactionManager** para auxiliar o **EntityManager**, para isso seguimos os seguintes passos:

1. Adicionar a annotation `@EnableTransactionManagement` na classe JPAConfiguration
2. Implementar o método `public JpaTransactionManager transactionManager(EntityManagerFactory emf)`

### Retomando os estudos

- As classes que são controladas pelo Spring se chamam Bean
- O Spring cuida da criação de muitos elementos no nosso sistema em tempo de execução. Isso é feito principalmente com o uso de **anotações** e **injeção de dependências**
- Temos que habilitar, anotar e criar o nosso transactionManager. O ultimo passo é feito relacionando o transactionManager com o entityManager, fazemos essa configuração na **JPAConfiguration**, através do método:

```java
// Existem diversos transactionManager, um exemplo é o JpaTransactionManager
// A associação é feita através de uma injeção na criação do transactionManager
public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
}
```

### Relacionando Produtos com Preços

Podemos relacionar um produto com um uma lista de preços, usando duas tabelas diferentes. Teriamos então um produto para vários preços. Entretanto não precisamos que o preço tenha Id, pois não iremos reutilizar ele. Usamos primeiro a anaotação `@ElementCollection`, que indica que esse atributo é uma coleção de elementos. Em seguida marcamos a classe que fará parte da estrutura interna, neste caso   **Preco** com a assinatura `@Embeddable`

### Adicionando dados a View

Podemos utilizar o objeto do tipo ModelAndView para disponibilizar dados na View. Primeiro instanciamos o objeto passando qual view que iremos usar, em seguida adicionamos os dados dentro dela:

```java
public ModelAndView form(){

    ModelAndView modelAndView = new ModelAndView("produtos/form");
    modelAndView.addObject("tipos", TipoPreco.values());

    return modelAndView;
}
```

### Configurando o path dos controller e o HTTP method

Podemos configurar um escopo geral para um controller através da anotação `RequestMapping("path")`, acima da definição do controller. Para definir qual método HTTP um determinado método irá atender também usamos a anotação `RequestMapping(method=RequestMethod.GET)`.

### Redirect e apresentação de mensagens com Flash

Após realizar um POST devemos redirecionar o usuário, isso por que o browser salva o ultimo request realizado, e caso o F5 seja apertado esse request será repetido. Para evitar isso redirecionamos o usuário ao término do post, ou melhor, respondemos o post com uma ordem de redirect. O browser irá interpretar essa ordem e disparará uma requisição de redirecionamento. Para mostrar mensagens durante este processo podemos usar o objeto **FlashScopped**, os objetos adicionados nele ficam vivos de um request para outro, enquanto o browser estiver fazendo um redirecionamento.

### Validação de dados

Validar os dados recebidos de um formulário é uma importante etapa durante o processamento de uma requisição. Podemos fazer isso do lado do usuário, com Java script, mas seria passíel de ser alterado. Outra alternativa é validar do lado do servidor essas informações, e essa será nossa abordagem.

A primeira validação que temos que fazer é das informações que mandamos para o servidor, para que o binding entre os parâmetros seja feito sem problemas. Por causa dessa falta de binding valores primitivos podem ficar sem ser inicializados, gerando um erro.

Para fazer a validação dos dados usaremos uma especificação chamada **Bean Validation**, e sua implementação **Hibernate Validator**. Em seguida precisamos amarrar o controller com seu validador. Para isso utilizaremos um Binder:

```java
// ProdutoController.java
// O problema aqui é que nosso ProdutoValidation precisa implementar a interface Validation
@InitBinder
public void InitBinder(WebDataBinder binder) {
    binder.addValidators(new ProdutoValidation());
}

// ProdutoValidation.java
// Neste método estamos verificando se o objeto recebido tem uma assinatura da classe Produto
@Override
public boolean supports(Class<?> clazz) {
    return Produto.class.isAssignableFrom(clazz);
}

@Override
public void validate(Object target, Errors errors) {
    ValidationUtils.rejectIfEmpty(errors, "titulo", "field.required");
    ValidationUtils.rejectIfEmpty(errors, "descricao", "field.required");

    Produto produto = (Produto) target;
    if(produto.getPaginas() <= 0) {
        errors.rejectValue("paginas", "field.required");
    }
}

// ProdutoController.java
// No nosso controller receberemos o resultado da validação, no objeto BindingResult. Podemos verificar a existência de erros através do método
// hasErrors. Precisamos colocar o BindingResult logo após a anotação @Valid para que o Spring consiga fazer a validação corretamente.
@RequestMapping(method=RequestMethod.POST)
public ModelAndView gravar(@Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {
    if(result.hasErrors()) {
        System.out.println(result.getAllErrors());
        return form();
    }
    produtoDAO.gravar(produto);
    redirectAttributes.addFlashAttribute("sucesso","Produto cadastrado com sucesso!");
    return new ModelAndView("redirect:produtos");
}
```

### Apresentando erros e externalizando mensagens

Podemos apresentar erros utilizando a taglib `<form:errors path="titulo" />`. O arquivo externo é um `.property` e precisa ser configurado para que o Spring manipule ele como um Bean:

```java
// AppWebConfiguration.java
@Bean
public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("/WEB-INF/messages");
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setCacheSeconds(1);
    return messageSource;
}

// WEB-INF/message.property

// field.required = Campo é obrigatório
// field.required.produto.titulo = O Campo título é obrigatório
// .
// .
// .
// typeMismatch = O tipo de dado foi inválido

```

### Formatando datas

Podemos acrescentar um campo data facilmente utilizando o Spring. Basta criar uma varáveil no modelo do tipo **Calendar** e adicionar a anotação `@DateTimeFormat`. Na classe de configuração podemos fazer uma config global que irá functionar para todas as classes que precisam usar formatação de datas:

```java
// AppWebConfiguration.java

@Bean
public FormattingConversionService mvcConversionService() {
    DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
    DateFormatterRegistrar formatterRegistrar = new DateFormatterRegistrar();
    formatterRegistrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
    formatterRegistrar.registerFormatters(conversionService);

    return conversionService;
}
```
### Upload de arquivos

Podemos construir um formulário que faz o upload de um arquivo. O primeiro passo é adicionar ao formulário um campo de seleção de arquivos. Nosso controller precisa muda a assinatura para `public ModelAndView gravar(MultipartFile sumario, @Valid Produto produto, ...` e o formulário precisa ser do tipo multipart.  

Além disso precisamos configurar um resolver para o multipart:

```java
// AppWebConfiguration.java

public MultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
}

// ServletSpringMVC
@Override
protected void customizeRegistration(Dynamic registration) {
    registration.setMultipartConfig(new MultipartConfigElement(""));
}
```

O Spring vai se encarregar de criar os objetos que estarão anotados com `@Autowired` para que possamos utiliza-los dentro dos nossos métodos

```java
// FileSaver.java

@Component // Diz para o Spring que o FileSaver é um componente, conceito parecido com o de Bean
public class FileSaver {

    @Autowired
    private HttpServletRequest request;

    public String write(String baseFolder, MultipartFile file) {
        try {
            String realPath = request.getServletContext().getRealPath("/" + baseFolder);
            System.out.println(realPath);
            String path = realPath + "/" + file.getOriginalFilename();
            System.out.println(path);
            file.transferTo(new File(path));
            return baseFolder + "/" + file.getOriginalFilename();

        } catch (IllegalStateException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

// ProdutoController.java
@RequestMapping("/produtos")
public class ProdutosController {

    @Autowired
    private ProdutoDAO produtoDAO;

    @Autowired
    private FileSaver fileSaver;
...
    @RequestMapping(method=RequestMethod.POST)
    public ModelAndView gravar(MultipartFile sumario, @Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return form(produto);
        }

        String path = fileSaver.write("arquivos-sumario", sumario);
        produto.setSumarioPath(path);
        System.out.println(path);

        produtoDAO.gravar(produto);
        redirectAttributes.addFlashAttribute("sucesso","Produto cadastrado com sucesso!");
        return new ModelAndView("redirect:produtos");
    }
...
}
```

Durante a execução do servidor, que está rodando com o auxílio do eclipse, a pasta que estamos usando como workspace é `~/opt/jee-neon/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/casadocodigo/arquivos-sumario`, que não necessariamente é a pasta que está o projeto. O curioso é que temos a pasta de workspace do eclipse e a pasta targe. Qual a diferença das duas?

### Servindo arquivos estáticos

Para que o spring sirva arquivos estáticos, como css, js e imagens precisamos configurar na classe `AppWebConfiguration`, através do método:

```java
// Porém a classe AppWebConfiguration precisa extender a classe pai WebMvcConfigurerAdapter
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
}

```
Com isso os arquivos estáticos na pasta `/resources/` serão servidos pelo Spring, essa solução se baseou neste [tutorial](http://www.baeldung.com/spring-mvc-static-resources). Usar uma estratégia de log é uma boa opção para debuggar, no meu caso utilizei o log4j para isso, baseado neste [tutorial](https://www.mkyong.com/spring-mvc/spring-mvc-log4j-integration-example/)


### Buscando dados com o DAO

Para exibir o nosso produto e seus detalhes precisamos fazer uma, e utilizar seu id. Nossa primeira abordagem é o método:

```java
public Produto find(Integer id) {
    return manager.find(Produto.class, id);
}
```

O problema desse método é que apenas o Produto será carrega, não seus preços. Nossa query precisa ser customizada, de forma que as tabelas associadas com o Produto sejam carregadas também. Para isso usamos a query a seguir:

```java
public Produto find(Integer id) {
    return manager.createQuery("select distinct(p) from Produto p join fetch p.precos precos where p.id = :id",
            Produto.class).setParameter("id", id).getSingleResult();
}
```

Essa query irá fazer um join entre as tabelas Produto e Preço, e a diretiva `distinct` fará com que o Hibernate não retorne valores duplicados. Por fim, pegaremos apenas um resultado, como o método `getSingleResult`.

### Urls amigaveis

Podemos usar o seguinte comando `<a href="${s:mvcUrl('PC#detalhe').arg(0, produto.id).build()}">${produto.titulo}</a>` para criar um link que nos levará para a página de detalhes. No controller podemos melhorar o path e deixar com mais cara de RESTful, da seguinte forma:

```java
@RequestMapping("/detalhe/{id}")
public ModelAndView detalhe(@PathVariable Integer id) {
  .
  .
  .
}
```

A anotação `@PathVariable` serve para informar que esse parâmetro será recebido através da Url utilizada na requisição.

### Lidando com escopo de sessão

Criamos a classe **CarrinhoCompras** e definimos ela como sendo um Bean, ou seja, uma classe que será gerenciada pelo Spring. O escopo dessa anotação é de um singleton, por default. Podemos configurar isso de maneira mais fina através da anotação `@Scope` e dos valores disponíveis na interface `WebApplicationContext`. No nosso caso o escopo terá sua duração atrelada a sessão do usuário.

O devmedia tem um artigo que se aprofunda um pouco mais nesse assunto: [link](http://www.devmedia.com.br/introducao-pratica-ao-spring-framework-com-uso-de-anotacoes/27859). A maior diferença entre cada um dos escopos é:

- Aplicação: Os objetos criados desde o começo da execução são singletons
- Sessão: Cada usuário que se contecta à aplicação recebe um conjunto de objetos diferentes
- Requisição: Os objetos tem tempo de vida igual ao tempo de vida dos requests.


### Transformando uma classe em um Json

Para validar nossa transação iremos enviar um requisição do tipo POST para uma aplicação fictícia, com o valor da compra seguindo o modelo `{ value: 500 }`. Importante resaltar que o limite de compra é até R$: 500,00 reais, caso esse valor seja ultrapassado a aplicação fictícia gerará um erro. Para converter um objeto em JSON precisamos definir um conversor, no caso um `HttpMessageConverter`. Para isso utilizaremos uma lib chamada **Jackson**. Primeiro adicionamos a sua dependência no nosso `pom.xml`:

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.5.1</version>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.5.1</version>
</dependency>
```

### Fazendo request para serviços externos

Muitas vezes nossas aplicações precisam interar com outras, para isso podemos utilizar o padrão de comunicação HTTP, fazendo com que o nosso servidor assuma o papel do usuário na comunicação. Para isso utilizamos  a classe `RestTemplate` junto com o métoto `postForObject`:


```java
@Autowired
RestTemplate restTemplate; // Intejando a dependência

public Callable<ModelAndView> finalizar(RedirectAttributes model) {
    return () -> {
        try {
            String url = "http://...";
            String response = restTemplate.postForObject(url, new DadosPagamento(carrinho.getTotal()),
                    String.class);
            model.addFlashAttribute("sucesso", response);
            System.out.println(response);
            return new ModelAndView("redirect:/produtos");
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            model.addFlashAttribute("falha", "Valor maior do o permitido: V <= 500");
            return new ModelAndView("redirect:/produtos");

        }
    };
}

// Configuramos também o Bean que irá criar o RestTemplate para nós, na classe AppWebConfiguration

@Bean
public RestTemplate restTemplate() {
    return new RestTemplate();
}
```
