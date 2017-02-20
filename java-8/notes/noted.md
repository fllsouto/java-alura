# Java 8
## Indice

- [Relacionamento entre coleções](#relacionamento-entre-coleções)
  - [Referências adicionais](#referências-adicionais)
- [Interfaces como classes anônimas](#interfaces-como-classes-anônimas)
- [Usando streams](#usando-streams)

## Interfaces e default methods

A partir do Java 8 interfaces passaram a poder ter métodos, não apenas métodos abstratos que precisam ser implementados. Esses métodos foram nomeados como `default methods`, um exemplo deles é o método sort na interface `List`:

```java
default void sort(Comparator<? suṕer E> c) {
  Collections.sort(this, c);
}
```

O método está chamando a classe `Collections` e não estão sendo manipulados atributos de instância (não é permitido esse tipo de manipulação), apenas métodos. Um outro exemplo é o método `forEach` da interface `Iterable`, que recebe como argumento um `Consumer`:

```java
class ConsumidorDeString implements Consumer<String> {
  public void accept(String s) {
    System.out.println("Word: " + s);
  }
}

.
.
.

Consumer<String> consumidor = new ConsumidorDeString();
palavras.forEach(consumidor);
```

Podemos sobrescrever default methods, semelhante a ideia de herança. Só que a implementação de default methods pode criar um problema de herança multipla e colisão de nome, mais para frente aprenderemos como tratar isso.

### Refernecias adicionais

- [Using Interfaces](https://docs.oracle.com/javase/tutorial/java/IandI/usinginterface.html)
- [Default Methods](https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html)
- [Abstract](https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html)
- [Interface as Type](https://docs.oracle.com/javase/tutorial/java/IandI/interfaceAsType.html)


## Interfaces como classes anônimas

Como vimos anteriormente, as interfaces no Java 8 podem ter métodos default. Podemos usar elas como fizemos acima, ou ainda podemos instanciar uma interface. Fazemos isso através da criação de uma **classe anônima** e da implementação de seus métodos, direto no bloco de instanciação. Isso é útil quando não precisamos reaproveitar o código escrito ou quando o corpo da interface é bem pequeno.

```java
// Primeira forma

Consumer<String> consumidor = new Consumer<String>() {
  @Override
  public void accept(String s) {
    System.out.println("Word: " + s);
  }
};

palavras.forEach(consumidor);

// Forma mais enxuta ainda

palavras.forEach(new Consumer<String>() {
  @Override
  public void accept(String s) {
    System.out.println("Word: " + s);
  }
});

// Mais enxuta ainda [2], isso não é necessariamente uma classe anônima

palavras.forEach((String s) -> {
    System.out.println("Word: " + s);
});

// Sem declaração de tipo

palavras.forEach((s) -> {
    System.out.println("Word: " + s);
});

// One line shot, Lambda

palavras.forEach(s -> System.out.println("Word: " + s));
```

Exemplo com o sort:

```java
// Primeira tentativa

Comparator<String> comparator = new ComparadorPorTamanho();

palavras.sort(comparator);

class ComparadorPorTamanho implements Comparator<String> {

	@Override
	public int compare(String s1, String s2) {
		if(s1.length() < s2.length())
			return -1;
		if(s1.length() < s2.length())
			return 1;
		return 0;
	}

}

// Usando classes anônimas

palavras.sort(new Comparator<String> {

	@Override
	public int compare(String s1, String s2) {
		if(s1.length() < s2.length())
			return -1;
		if(s1.length() < s2.length())
			return 1;
		return 0;
	}

});

// Usando Lambda

palavras.sort((String s1, String s2) -> {
		if(s1.length() < s2.length())
			return -1;
		if(s1.length() < s2.length())
			return 1;
		return 0;
	});

// Delegando comparação, não preciso nem do statement de return

palavras.sort((s1, s2) -> Integer.compare(s1.length(), s2.length()));

```

Uma interface que possui apenas um método abstrato é conhecida como **interface funcional** e elas tem a anotação `@FunctionalInterface`. A interface Runnable é um outro exemplo:

```java
// Sintaxe normal

new Thread(new Runnable() {

    @Override
    public void run() {
        System.out.println("Executando um Runnable");
    }

}).start();

// Sintaxe funcional

new Thread(() -> {
  System.out.println("Executando um Runnable");
  }).start();
```

Podemos utilizar métodos estáticos da interface `Compatador`, como o `comparing`. Ele é um método de primeira classe que recebe uma função como parâmetro:

```java

// Esse método está recebendo um lambda e criando um comparator para mim com ele, parecido com uma Factory
palavras.sort(Comparator.comparing(s -> s.length()));

// Poderiamos ser mais verbosos e criar uma parte de cada vez
Function<String, Integer> funcao = new Function<String, Integer>() {
  @Override
  public Integer apply(String s) {
    return s.length;
  }
}

Comparator<String> comparator = Comparator.comparing(funcao);
palavras.sort(comparator);
```

É comum que lambdas façam uso de apenas um método, como no caso anterior que temos a chamada do método `length()` da classe `String`. Existe uma forma mais reduzida de escrever lambdas, utilizando **::**. Essa sintaxe recebe o nome de **method reference**, aplicando essa ideia no exemplo anterior temos:

```java
Function<String, Integer> funcao = String::length;
palavras.sort(Comparator.comparing(funcao));

// Podemos imprimir com
palavras.forEach(System.out::println);
```

Nem sempre podemos utilizar method reference, principalmente quando o lambda precisa receber parâmetros ou quando o lambda é complexo.

## Usando streams

Utilizamos streams para criar fluxos de objetos. Trabalhar com esse stream não tem **side-effects** nas coleções anteriores, isso evita problemas relacionados com concorrência e etc. Muitas manipulações de números do Stream fazem autoboxing and unboxing nos números, precisamos ter cuidado com isso para não criar centena de objetos.

Com esse fluxo de dados podemos aplicar diversas operações, como filtar determinado tipos de dados ou realizar transformações.

```java
  cursos.stream()
    .filter(c -> c.getAlunos() > 100);
```

No examplo acima estamos filtrando apenas os cursos que tem mais de 100 alunos. Podemos encadear mais chamadas desse tipo, e estaremos sempre manipulando o Stream. Caso quisessemos o primeiro elemento poderiamos utilizar o método `findFirst`. Ele irá retornar um `Optional`, um container de objetos com o qual podemos lidar com a presença ou não de elementos de uma forma mais alto nível, como por exemplo os métodos:

```java
// Verifica se existe um elemento no container
isPresent()

// Executa um Consumer em um containter se existir um elemento dentro dele
ifPresent()

// Tenta manipular um valor, caso não seja possível retornará um valor default
orElse()

// ou executa um lambda/método
orElseGet()
```
Além desses métodos o Optional tem diversos outros que nos permitem lidar com esse container de forma funcional. Quando terminamos de manipular um stream podemos empacotar os dados em uma nova collection, através do métod `collect`, ele irá aceitar como parâmetro um Collector, uma interface funcional que fornece factory methods para criação de novas collections.
