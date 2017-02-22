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
