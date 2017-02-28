# Orientação a Objetos - Melhores técnicas
## Indice

- [Relacionamento entre coleções](#)

## Lógica de Negócio e Modelos Anêmicos

No nosso exemplo das dívidas queremos descontar 8 reais para qualquer pagamento que for superior a 100 reais. No nosso código atual teremos que alterar em duas classes diferentes, na `BalancoEmpresa` e `GerenciadorDeDividas`. Essa lógica deveria estar isolada dentro da classe `Dívida`.

A classe Dívida é burra, não faz nada além de guardar dados. Dizemos que ela é uma classe **Anêmica**.

Controlar o nível de encapsulamento de uma classe é uma das tarefas mais importantes em uma boa orientação a objetos. É preciso proteget os dados internos de uma classe e controlar a manipulação deles por outros objetos, além de controlar as formas como essa manipulação será feita.

As mudanças na classe `Dívida` fizeram com que ela passe a ter tanto dados quanto comportamentos. A interação das outras classes com ela está seguindo a ideia do princípio conhecido como **Tell, Don't Ask**, onde falamos para uma classe o que queremos dela. Diferente do comportamento anterior, onde usavamos os estados internos de outra classe para tomarmos decisões.

## Classes Coesas e Single Responsability Principle

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

A baixa coesão dificulta o aproveitamento de código, para validar um CNPJ em qualquer parte do código eu preciso instanciar a classe Dívida.

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

## Reduzindo acomplamento com interfaces e polimorfismo

O Java tem um mecanismo através do qual podemos lidar com classes que respondem a determinados métodos. Esse mecanismo são as **interfaces** e através delas as classes podem ser manipuladas sem que seja necessário conhecer o tipo do operando, ele precisa apenas implementar os métodos definidos na interface. Isso cria uma maneira uniforme de lidar com um conjunto de objetos de classes distintas, mas que são semelhantes em determinados comportamentos.

O polimorfismo permite que nosso código evolua de forma orgânica e natural. Imagine que temos as classes `Cnpj` e `Cpf`, teríamos que criar lógicas específicas para lidar com cada tipo de documento, o que poderia aumentar a replicação de código e a criação de diversos **Ifs** para identificar o tipo certo de dado e estratégia. Uma melhor abordagem é criar a interface `Documento` e apenas lidar com ela, isso flexibiliza muito mais nosso código e permite que se um novo documento for criado sua integração no sistema será feito de maneira muito mais simplificada.
