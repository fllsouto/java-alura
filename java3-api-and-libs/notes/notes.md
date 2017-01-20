# Java 3 - Principais APIs e bibliotecas

## Indice

- [Pacotes](#pacotes)
- [Arquivo Jar](#arquiv-jar)
- [O pacote java.lang](#o-pacote-java.lang)
  - [A classe System](#a-classe-system)
  - [A classe String](#A-classe-string)
- [O pacote java.io](#o-pacote-java.io)


## Pacotes

Pacotes são uma forma elegante de separar os arquivos, parecido com os diretórios que temos em um computador. Um pacote pode conter nenhum ou mais subpacotes/classes dentro dele. A Sun estabelece um padrão para nomeação de pacotes, de acordo com o nome da empresa/programador que desenvolveu a classe:

```
br.com.nomedaempresa.nomedoprojeto.subpacote1
<!-- br.com.nomedaempresa.nomedoprojeto.subpacote2 -->
br.com.nomedaempresa.nomedoprojeto.subpacote3.subpacote1

com.amazon.wakafoogenerator.package1
```

A convenção é só utilizar letras minúsculas sem separação, evitando ao máximo possiveis conflitos. Podemos utilizar classes de pacotes diferentes de duas formas:

```
// Com a palavra import
import br.com.foo.util.Banco;

// Ou usando o fully qualified name, em ambos os casos a classe Banco precisa ser pública ou estar no mesmo pacote da classe que a utiliza. Classes que dividem um mesmo pacote podem ser private ou protected, mas isso veremos em outro
// momento do curso
br.com.foo.util.Banco bb = new br.com.foo.util.Banco();
```

Podemos importar métodos estáticos com o comando `import static`, assim não precisamos ficar explicitando sempre que usamos métodos como o `Math.round`.

## Arquivo Jar

Um arquivo Jar é uma forma de disponibilizar um programa escrito em java, e significa **Java Archive**. Podemos gerar um arquivo jar utilizando uma IDE ou através do terminal usando os seguintes comandos:

```
// Gerando um arquivo Jar
$ jar -cvf <nome-do-arquivo-jar>.jar <path-para-os-arquivos-classe-um-ou-mais>

// Executando o jar
$ java -classpath <nome-do-arquivo-jar>.jar <nome-da-classe-main-em-fully-qualified-name>

// Ex:
$ jar -cvf banco.jar src/br/com/empresa/banco/conta/*.class src/br/com/empresa/banco/exception/*.class src/br/com/empresa/banco/sistema/*.class src/br/com/empresa/banco/util/*.class
$ java -classpath banco.jar src.br.com.empresa.banco.sistema.TesteConta
```

## O pacote java.lang

O pacote **java.lang** é um dos mais importantes da linguagem e é incluído por default em todos os programas.

### A classe System

A classe System tem funcionalidades muito úteis, algumas delas são:
- System.out.prinln("") : Imprime na saída padrão uma String
- System.in.read() : Lê bytes da entrada padrão (precisa estar dentro de um block try-catch)
- System.exit(0) : Finaliza a execução da JVM
- Runtime.getRuntime() : Permite uma interação com o sistema operacional, como rodar programas

Todas as classes criadas herdam da classe **Object**, definida no pacote `java.lang`. Por causa disso podemos manipular qualquer objeto como um **Object**, mesmo que tenhamos criado com outro tipo.

### A classe String

- String são imutaveis em java
- Sempre que criamos uma String ela fica armazeada em um pool de strings, ou seja, Strings criadas sem a palavra new não são duplicadas, esse é um dos motivos para as strings não serem imutaveis (ver `src/TesteString.java`)
- Podemos usar o `java.lang.StringBuffer` para manipular String. Ele é mais performática por que não cria dezenas de Strings intermediarias imutaveis, e não tem o pool de Strings citado

## O pacote java.io

O controle de entrada e saída do Java é realizado através do pacote `java.io`. Ele utiliza o conceito de fluxos de entrada (InputStream) e saída (OutputStream), de forma agnóstica quanto a fonte e o destino das manipulações, que podem ser **Arquivos**, **Blobs**, **Sockets**, ou mesmo a entrada e saída padrão. Os Stream manipulam bytes, em ambos os sentidos.

### FileInputStream e FileOutputStream

São classes úteis para a manipulação de bytes em arquivos. Para instanciar qualquer uma delas é preciso passar o nome do arquivo como parâmetro, um objeto do tipo **File** ou um **FileDescriptor**. Podemos instanciar um FileInputStream e atribuir a uma variável do tipo InputStream, que é a sua classe mãe abstrata. A classe **BufferedReader** irá ler diversos caracteres e junta-los para formar uma String, como o método `readLine()`.

O Reader que estamos usando irá pedaços (através do buffer), isso evita que muitas chamadas ao sistema operacional sejam feitas. Podemos controlar o tamanho do buffer criado pelo construtor. Essa composição de leitores segue o design pattern conhecido como **Decorator**.

```
|
| new FileInputStream('arquivo.txt')
|
V
---------------
|             |
| InputStream |
|             |
---------------
|
| Leitura em bytes
|
V
---------------------
|                   |
| InputStreamReader |
|                   |
---------------------
|
| Conversão para char
|
V
-------------------
|                 |
| BufferedReader  |
|                 |
-------------------
|
| Leitura de diversos caracteres para transformar em uma String
|
V
```

O método `readLine` lê uma linha e muda seu cursos de leitura para a próxima linha. Quando o cursor chegar ao final do arquivo a leitura irá devolver o valor `null`.

## System.in

Para ler do teclado podemos recorrer ao `System.in`. Essa chamada retorna um `InputStream` configurado para entrada padrão do usuário, que costuma ser o teclado. Através do polimorfismo mudamos apenas o tipo do InputStream e tudo continua funcionando como antes. Através dessa composição podemos criar nosso próprio InputStream se for preciso.

```
|
| System.in
|
V
---------------
|             |
| InputStream |
|             |
---------------
|
| Leitura em bytes
|
V
---------------------
|                   |
| InputStreamReader |
|                   |
---------------------
|
| Conversão para char
|
V
-------------------
|                 |
| BufferedReader  |
|                 |
-------------------
|
| Leitura de diversos caracteres para transformar em uma String
|
V
```

### Escrita de dados

A escrita de dados segue um processo inverso ao apresentado. Uma cadeia de caracteres se transforma em chars e depois em bytes. No final do processo a saída escolhida é definida, podendo ser qualquer uma que se desejar. Podemos recorrer as classes `Scanner` e `PrintStream` para lidar com entrada e saída em um nível de abstração mais algo.

Sempre que trabalhamos com recursos de entrada e saída precisamos prestar atenção na abertura e fechamento do canal. Chamando o método `close()` do `BufferedReader` iremos fechar todos os recursos abertos em cascata, sem ter que passar por cada um deles.

## Collection Framework

Arrays puros tem alguns problemas:
- Não podem ser redimensionados;
- Não é possível buscar um elemento, apenas indexar;
- Não conseguimos saber quantas posições foram ocupadas

Uma alernativa para a classe Arrays está localizado no pacote `java.util` e é conhecido [**Collection Framework**](https://docs.oracle.com/javase/tutorial/collections/TOC.html). Consiste em diversas estruturas de dados para as mais diversas finalidades.

![Collection Framework Hierarchy](/notes/imgs/Collection-Framework-hierarchy.png)
[source](http://javabydeveloper.com/collection-framework-key-interfaces/)

### Listas

Listas são estruturas de dados com tamanho redimensionavel, que permite elementos duplicados e que mantém a ordem de inserção. Temos a interface `List` que define um conjunto de comportamentos para outras classes, como a `ArrayList`.

```java
// Criando uma ArrayList
ArrayList lista = new ArrayList();

// Lidando apenas com a interface List
List lista = new ArrayList();

// A lista que criamos aceita qualquer tipo de objeto por que tanto as collections quanto as lists tentam ser o mais genéricos possíveis
List lista = new ArrayList();
lista.add("Waka");
lista.add("Foo");
lista.add("Bar");

// Aqui estamos pegando o elemento na posição 1. Como a lista aceita tipos genéricos receberemos um objeto da classe Object. Para manipular uma string precisamos realizar o typecasting para a classe desejada
lista.get(1); // => Object
String foo = (String) lista.get(1);

// Usamos Generics para restringir os tipos de dados aceitos pelo ArrayList, isso evita que o typecasting seja necessário na hora de pegar um elemento da lista e limita que apenas elementos do tipo de dado delimitado poderão ser inseridos na lista

List<String> lista = new ArrayList<String>();
lista.add("Waka");
lista.add("Foo");
lista.add("Bar");

// Aqui estamos pegando o elemento na posição 1. Como a lista aceita tipos genéricos receberemos um objeto da classe Object. Para manipular uma string precisamos realizar o typecasting para a classe desejada
lista.get(1);
String foo = lista.get(1);
```

Sempre é indicado trabalhar com interfaces em vez de classes, no caso de termos uma método que retorna um estrutura de dados é preferível que ele retorne a interface que ele implementa:

```java
// Não é genérico, estamos manipulando uma classe
public ArrayList<Conta> buscaTodasAsContas() { ... }

// Estamos manipulando uma interface, muito mais flexível e suscetivel a mudanças0
public List<Conta> buscaTodasAsContas() { ... }
```

## Referências

- [Collection Framework Key Interfaces](http://javabydeveloper.com/collection-framework-key-interfaces/)
