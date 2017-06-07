## Java Spring MVC I

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

#### Retomando os estudos

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
