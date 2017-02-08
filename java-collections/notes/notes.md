# Java - Dominando as Collections

## Indice

- [Relacionamento entre coleções](#relacionamento-entre-coleções)
- [Referencias sobre complexidade](#referências-sobre-complexidade)
- [Conjuntos e Sets](#conjuntos-e-sets)
- [Buscas e comparações com HashSets](#buscas-e-comparações-com-hashsets)

## Relacionamento entre coleções

Um curso tem uma coleção de aulas. Para modelar esse relacionamento eu poderia utilizar estruturas como ArrayList, LinkedList dentre outras. Porém eu estaria manipulando implementações concretas, algo que não é uma boa prática. O código ficaria amarrado a apenas um tipo de comportamento.

```java
private ArrayList<Aulas> aulas = ArrayList<>();
```

A boa prática no mundo Java é lidar apenas com interfaces, isso diminui o acoplamento entre as classes.

```java
private List<Aula> aulas = LinkedList<Aula>();

.
.
.

```

O método a seguir retorna a lista de aulas. Esse retorno pode ser manipulado diretamente por qualquer parte do programa, e irá alterar a referência original, uma péssima prática.

```java
public List<Aula> getAulas() {
  return aulas;
}
```

O ideal seria criar um método para realizar alterações na lista e outro método que só possibilita a leitura dos dados, não a escrita. Conseguimos isso através do método `unmodifiableList` da classe `Collections`. Será retornado uma referência imutável, exatamente o que precisamos.

```java
public List<Aula> getAulas() {
  return Collections.unmodifiableList(aulas);
}

public void adiciona(Aula a) {
  this.aulas.add(a);
}
```

## Referências sobre complexidade

- [Big-O Cheat Sheet](http://bigocheatsheet.com/)
- [Java Collections Framework summary table](http://www.codejava.net/java-core/collections/java-collections-framework-summary-table)
- [Java Collections Cheat Sheet](https://zeroturnaround.com/rebellabs/Java-Collections-cheat-sheet/)
- [Java Collections – Performance (Time Complexity)](http://infotechgems.blogspot.com.br/2011/11/java-collections-performance-time.html)
- [Exception handling in java](http://www.s4techno.com/blog/2016/07/12/exception-handling/)

## Conjuntos e Sets

Um conjunto é uma "sacola" onde eu posso colocar elementos de qualquer tipo. Diferente de uma lista, um conjunto não possui uma ordem, eles podem ser inseridos em qualquer ordem. A indexação através do objeto com o auxílio de uma tabela de hash e possui métodos como contains e remove.

Esse conjunto não aceita elementos duplicados, e isso pode ser verificado através do retorno do método `add`:

```java
Set<String> alunos = new HashSet<>();
boolean adicionado = alunos.add("Paulo");
System.out.println("Paulo foi adicionado [1]?: " + adicionado);

alunos.add("Alberto");
alunos.add("Pedro");    
alunos.add("Nico");

System.out.println("Tamanho antes: " + alunos.size());

adicionado = alunos.add("Paulo");
System.out.println("Paulo foi adicionado [2]?: " + adicionado);
alunos.add("Alberto");
alunos.add("Pedro");    
alunos.add("Nico");

System.out.println("Tamanho depois: " + alunos.size());

// Paulo foi adicionado [1]?: true
// Tamanho antes: 4
// Paulo foi adicionado [2]?: false
// Tamanho depois: 4
```

### Buscas e comparações com HashSets

O HashSet é uma estrutura muito utilizada quando estamos desenvolvendo. Ela utiliza uma tabela de espalhamento para indexar os elementos. Para procurar um elemento dentro de um HashSet precisamos sobrescrever o método `equals` e o `hashCode`.

Importante mencionar que dois elementos que **são iguais possuem necessariamente o mesmo hashCode**, mas dois elementos que tem o mesmo hashCode não são necessariamente iguais:

```java
"José da Silva", RG: 332211
  |
  |--hashcode--> -459750397 -
                            |
  |--hashcode--> -459750397 ---> Objetos diferentes
  |
"José da Silva", RG: 332200


.
.
.
|===========|
| 459750397 |
|===========|
|
|-----> [
  {"José da Silva", RG: 332211},
  {"José da Silva", RG: 332200}]
```

Existe um contrato na sobrescrita do método `equals`, é boa prática reescrever também o método `hashCode` para termos um maior controle onde cada colisão irá acontecer. Podemos também utilizar a implementação do método `hashcode` da classe `String`.

Referência: https://muhammadkhojaye.blogspot.com.br/2010/02/java-hashing.html
