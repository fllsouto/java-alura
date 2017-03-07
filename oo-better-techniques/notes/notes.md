# Orientação a Objetos - Melhores técnicas
## Indice

- [Lógica de Negócio e Modelos Anêmicos](#lógica-de-negócio-e-modelos anêmicos)
- [Classes Coesas e Single Responsibility Principle](#classes-coesas-e-single-responsibility-principle)
- [Herança e o problema do acoplamento](#herança-e-o-problema-do-acoplamento)
- [Classes acopladas e a Lei de Demeter](#classes-acopladas-e-a-lei-de-demeter)
- [Reduzindo acoplamento com interfaces, polimorfismo e injeção de dependências](#reduzindo-acoplamento-com-interfaces,-polimorfismo-e-injeção-de-dependências)

## Lógica de Negócio e Modelos Anêmicos

No nosso exemplo das dívidas queremos descontar 8 reais para qualquer pagamento que for superior a 100 reais. No nosso código atual teremos que alterar em duas classes diferentes, na `BalancoEmpresa` e `GerenciadorDeDividas`. Essa lógica deveria estar isolada dentro da classe `Dívida`.

A classe Dívida é burra, não faz nada além de guardar dados. Dizemos que ela é uma classe **Anêmica**.

Controlar o nível de encapsulamento de uma classe é uma das tarefas mais importantes em uma boa orientação a objetos. É preciso proteget os dados internos de uma classe e controlar a manipulação deles por outros objetos, além de controlar as formas como essa manipulação será feita.

As mudanças na classe `Dívida` fizeram com que ela passe a ter tanto dados quanto comportamentos. A interação das outras classes com ela está seguindo a ideia do princípio conhecido como **Tell, Don't Ask**, onde falamos para uma classe o que queremos dela. Diferente do comportamento anterior, onde usavamos os estados internos de outra classe para tomarmos decisões.

## Classes Coesas e Single Responsibility Principle

Na nossa alteração recente a classe `Dívida` tem as seguintes responsabilidades:

- Registrar pagamentos com diferentes lógicas de negócio
- Aplicar descontos
- Filtrar pagamentos
- Validar CNPJ

Existem muitas responsabilidades com pouca relação entre si, neste caso dizemos que não existe coesão dentro da classe.

> co·e·são
(francês cohésion, do latim medieval cohaesio, -onis, proximidade, .contato)
substantivo feminino
1. Aderência, força que une entre si as moléculas dos líquidos ou dos sólidos.
2. União.
3. Qualidade de uma coisa em que todas as partes estão ligadas umas às outras.
4. [Figurado]  Harmonia, associação íntima.

> "coesão", in Dicionário Priberam da Língua Portuguesa [em linha], 2008-2013, https://www.priberam.pt/dlpo/coes%C3%A3o [consultado em 22-02-2017].

A baixa coesão dificulta o aproveitamento de código, para validar um CNPJ em qualquer parte do código eu preciso instanciar a classe Dívida. Para solucionar esse problema devemos quebrar as classes para aumentar a coesão nelas, podemos tomar as seguinte ações:

1. Extrair o `Cnpj` para uma classe específica
2. Criar uma classe `Pagamentos`, que é responsável por gerenciar pagamentos
3. Associar `Pagamentos` a Dívida, e delegar para a primeira o pagamento de um valor
4. Refatorar o `BalancoEmpresa` e `GerenciadorDeDividas`

Partimos de uma classe com nenhuma responsabilidade, anêmica. Passamos a ter uma classe com mais responsabilidades do que deveria e com baixa coesão, e chegamos ao meio termo, uma classe que segue o **Single Responsibility Principle**. O reaproveitamento e manutenção das classes se tornam mais fácil, além de conseguirmos comport mais facilmente comportamentos.

Considere a seguinte classe:

```java
public class RelatorioDeVendas {
  private void conectaAoBanco() {...}
  private String geraRelatorio() {...}
  public void salvaParaArquivo() {...}
  public void imprime() {...}
}
```
Essa classe tem muitas responsabilidades:

- Conectar ao bando de dados
- Gerar o relatório
- Salvar o relatório
- Imprimir o relatório

Não existem coesão nela. Seguindo o **SRP** poderiamos dividir em mais classes:

```java
class BancoDeDados {
  void conecta() { ... }
}
class Impressora {
  void imprime() { ... }
}
class SistemaDeArquivos {
  void escreveNovoArquivo(String nomeDoArquivo, String texto) { ... }
}
class RelatórioDeVendaas {
  void geraRelatorio() { ... }
}
```

##  Herança e o problema do acoplamento

No nosso sistema os `Pagamentos` são uma classe filha de `ArrayList`. Por causa da herança a classe filha recebe muitos métodos, como o `add(int i, Object o)`, `add(Object o)` e o `addAll(List o)`. Todos esses métodos tem uma implementação já definida na classe mãe, que nada tem haver com a classe filha. Precisamos sobrescrever esses métodos de forma que usem o método `paga`. Fazemos isso da seguinte forma:

```java
@Override
public boolean add(Pagamento pagamento) {
  paga(pagamento);
  return super.add(pagamento);
}
```

E o `addAll`:

```java

@Override
public boolean addAll(Collection<? extends Pagamento> pagamentos) {
  for (Pagamento pagamento : pagamentos) {
    paga(pagamento);
  }
  return super.addAll(pagamentos);
}
```

Chamamos o método `paga` para cada elemento da coleção recebida no método `addAll`, fazemos isso por que a classe mãe não usa o método `add` dentro do `super.addAll(pagamaneto)`. Saber esse tipo de detalhe é muito caro, estamos amarrando muito a classe filha com a mãe, isso cria o problema chamado de **Acoplamento**. Classes muito acopladas possuem muita dependência entre si, e uma simples mudança pode ser muito trabalhosa. Um exemplo disso seria alterar a classe mãe para um `HashSet`, caso queiramos que os pagamentos sejam únicos. Essa alteração quebraria a sobrescrita do médoto `addAll`, a classe mãe faz uso do método `add` dentro da chamada `super.addAll(pagamaneto)`


## Classes acopladas e a Lei de Demeter

Considere o seguinte trecho de código:

```java
//BalancoEmpresa.java
divida.getPagamentos().registra(pagamento);
```

Para registrar um novo pagamento na classe `BalancoEmpresa` precisamos saber que a classe `Dívida` possui um atributo do tipo `Pagamentos` e que oferece um método que retorna esse valor. Lidando com a classe desse jeito estamos infligindo o princípio **Tell, don't ask** pois estamos pedindo por um atributo interno de uma classe e depois operando sobre ele, estamos **encadeando chamadas** para chegar no nosso objetivo, e diferente do encadeamento funcional onde transformamos os dados aqui simplesmente estamos navegando dentro da estrutura de composições até acessar um determinado atributo.

Essa situação deu origem a [Lei de Demeter](https://en.wikipedia.org/wiki/Law_of_Demeter) a qual diz que devemos avançar apenas um nível na chamada dos objetos, e que o nível chamado deve se encarregar de delegar a sequência das ações. Esse tipo de ação contribui para o encapsulamento, por que não precisamos conhecer a estrutura interna de um objeto para poder utiliza-lo.

```java
//BalancoEmpresa.java
divida.registra(pagamento);

//Divida.java
public void registra(Pagamento pagamento) {
  // a classe agora delega o registro de um pagamento para seu atributo pagamentos
  this.pagamentos.registra(pagamento);
}
```

Precisamos sempre refletir sobre como uma classe se apresenta para o mundo exterior, através da sua interface pública. Pensar bem sobre isso pode nos ajudar na tarefa de design e interação entre as classes, além de contribuir de forma positiva em futuras mudanças que se tornam muito mais simples.

Precisamos definir o que cada classe irá fazer e compor elas, essa composição cria um certo nível de acoplamento. Enquanto que ao tentar fazer com que as classes não saibam muito umas sobre as outras, elas passarão a realizar muitas tarefas, diminuindo a coesão. É tarefa do programador balancear a coesão com o acoplamento.

A composição de objetos aumenta a coesão, enquanto que a delegação de tarefas diminui o acoplamento. Mas a melhor solução é juntar abstração, interfaces e polimorfismo.

## Reduzindo acoplamento com interfaces, polimorfismo e injeção de dependências

O Java tem um mecanismo através do qual podemos lidar com classes que respondem a determinados métodos. Esse mecanismo são as **interfaces** e através delas as classes podem ser manipuladas sem que seja necessário conhecer o tipo do operando, ele precisa apenas implementar os métodos definidos na interface. Isso cria uma maneira uniforme de lidar com um conjunto de objetos de classes distintas, mas que são semelhantes em determinados comportamentos.

O polimorfismo permite que nosso código evolua de forma orgânica e natural. Imagine que temos as classes `Cnpj` e `Cpf`, teríamos que criar lógicas específicas para lidar com cada tipo de documento, o que poderia aumentar a replicação de código e a criação de diversos **Ifs** para identificar o tipo certo de dado e estratégia. Uma melhor abordagem é criar a interface `Documento` e apenas lidar com ela, isso flexibiliza muito mais nosso código e permite que se um novo documento for criado sua integração no sistema será feito de maneira muito mais simplificada.

Utilizar muitos ifs aumenta nossa [complexidade ciclomática](https://en.wikipedia.org/wiki/Cyclomatic_complexity).

Imagine que queremos gravar os dados do `BalancoEmpresa` em um banco de dados, e não mais em um HashMap. Criamos primeiro uma classe chamada `BancoDeDados`, que irá se conectar, manipular os dados e também se desconectar quando for necessário. Fazemos a criação dessa classe dentro da classe `BalancoEmpresa`, porém ela vai ter que manipular o banco de dados e cuidar da conexão dele, responsabilidades que fogem ao escopo da classe, além de que não sabemos ao certo quando devemos chamar o método `desconecta`, a classe `BalancoEmpresa` irá precisar da conexão aberta enquanto existir.

Nesta situação precisamos voltar um nível na pilha de execução e fazer com que o mesmo método que manipula o `BalancoEmpresa` cuide também do banco de dados. Com isso saberemos exatamente o momento de abrir a conexão e fechar, tirando essa responsabilidade da classe de deve realizar apenas o balanço. Passaremos como argumento do construtor da classe `BalancoEmpresa` uma instância do banco de dados, estaremos assim **injetando uma dependência**, essa a qual pode ser muito mais que um simples banco de dados.

Podemos criar a interface chamada `ArmazenadorDeDividas`, que irá criar um contrato sobre quais operações deverão ser feitas por uma classe que armazena dívidas. No nosso caso serão os métodos `salva` e `carrega`.

```java
public interface ArmazenadorDeDividas {
  public void salva(Divida divida);
  public Divida carrega(Documento documentoCredor);
}
```

Com isso, podemos passar uma instância de um objeto que implementa a interface acima, flexibilizando e desacoplando ainda mais as estruturas:

```java
public class BalancoEmpresa {
  private ArmazenadorDeDividas dividas;

  public BalancoEmpresa(ArmazenadorDeDividas dividas) {
    this.dividas = dividas;
  }
  ...
}
```

Unindo o conceito de polimorfismo e injeção de dependência conseguimos criar sistemas mais fáceis de manter e estender. Esses conceitos nos ajudam a diminuir os pontos de mudança e aumentam o nível de abstração do nosso código.

```java
// Complexidade ciclomática alta, Documento como classe concreta com diferentes tipos
public void fazAlgo(Documento documento) {
  if(documento.getTipo().equals("CNPJ")) { faz algo aqui... }
    else if(documento.getTipo().equals("CPF")) { faz algo aqui... }
    else if(documento.getTipo().equals("CHN")) { faz algo aqui... }
}

// Complexidade ciclomática baixa, Documento como interface abstrata e outras classe concretas implementam ele, podemos também delegar facilmente dessa forma
public void fazAlgo(Documento documento) {
  documento.fazAlgo();
}

```
