# Java 1 - Primeiros Passos

## Indice

- [Introdução](#introdução)
- [Um programa simples em Java](#um-programa-simples-em-java)
- [Compilando um código em Java](#compilando-um-código-em-java)
- [Tipos primitivos de dados](#tipos-primitivos-de-dados)
- [Laços](#laços)
  - [Pré e Pós incremento](#pré-e-pós-incremento)
- [Introdução a Orientação à Objetos](#introdução-a-orientação-à-objetos)
  - [Comparação de objetos](#comparação-de-objetos)
  - [Alteradores de acesso](#alteradores-de-acesso)
  - [Construtores](#construtores)
  - [Atributos e métodos estáticos](#atributos-e-métodos-estáticos)

## Introdução

A **Sun** no começo produzia software embarcado, que rodava em diversos tipos de dispositivos, como geladeiras, carros e etc. Existia uma grande variadade de hardwares, isso dificultava a escrita dos softwares pelos desenvolvedores, cada plataforma precisava de um cuidado especial, fazendo com o que o código escrito fosse **portavel**. No começo o projeto se chamava **Oak**.

O Java resolve o problema de diferentes sistemas operacionais e hardwares executando o mesmo código. Isso é possível através do pseudo-código gerado pela compilação do arquivo **.java** chamado de **bytecode**. Esse **bytecode** é lido de diferentes formas, de acordo com o sistema operacional e suas implementações da **JVM (Java Virtual Machine)**.

A máquina virtual é um conceito mais alto nível do que um simples **interpretador**. Uma máquina virtual é responsável pelo gerencimento de memória, pilha de execução, threads e etc... A aplicação é executada sem que aja envolvimento direto com o sistema operacional, apenas com a JVM. Existe um total **isolamento**.

## Um programa simples em Java

```java
// OlaMundo.java
public class OlaMundo {
  // Todo método principal em java tem essa assinatura
  public static void main(String[] args) {
    System.out.println("Olá mundo!");
  }
}

```
## Compilando um código em Java

```bash
# Compilador Java, código independente de plataforma
$ java OlaMundo.java

$ ls
# OlaMundo.java OlaMundo.class

# Execuntando o bytecode usando minha JVM
$ java OlaMundo
# Olá Mundo

# É possível ver o bytecode gerado pelo compilador, utilizando o javap

$ javap -c OlaMundo.class

#public class OlaMundo {
#  public OlaMundo();
#    Code:
#       0: aload_0
#       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
#       4: return
#
#  public static void main(java.lang.String[]);
#    Code:
#       0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
#       3: ldc           #3                  // String Olá mundo!
#       5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
#       8: return
#}

```

## Tipos primitivos de dados

O Java possui tipos primitivos de dados. Esses tipos não precisam ser instanciados como outras classes. Alguns exemplos são:

- int: 10
- double: 23.0
- boolean: true
- char: 'M'

Uma característica importante é que tipos primitivos **não começam com letra maiúscula**. Além disso é possível converter um tipo primitivo para outra, através do **type casting**, que podem ser implícitos ou explícitos. No primeiro caso não precisamos colocar o tipo antes do dado, já que o destino irá comportar o tamanho original do dado, enquanto que no casting explícito temos que informar qual será o tipo de destino, essa conversão pode levar a uma perda de informação, como é o caso da conversão de um float para um inteiro. Alguns casting podem levar a erro de compilação.

```java
// Gera um erro de compilação
long x = 100; // Um long tem 8 bytes
int i = x; // Um int tem 4 bytes

// Conversão ok!
int i = (int) x;

// Casting muito comum
double d = 5;
float f = 3;

float x = f + (float) d;

short s1 = 42; // Um short tem 2 bytes
int i1 = s1; // Um int tem 4 bytes, essa conversão funciona de forma implícita

int i2 = 42;
short s2 = (int) i2; // Aqui eu preciso jogar fora 2 bytes de informação, usando o casting explícito
```

## Laços

```java
while(<expressão booleana>) {
  corpo do laço
}

for(<inicializador>; <expressão booleana>; <incremento>) {
  corpo do laço
}
```

### Pré e Pós incremento

```java

// Pós incremento
int i = 5;
int x = i++;

// i => 6
// x => 5
// Retorna o valor antigo e depois incrementa (pós incremento)

// Pré incremento
int i = 5;
int x = ++i;

// i => 6
// x => 6
// Incrementa o valor antigo e retorna ele (pré incremento)
```

## Introdução a Orientação à Objetos

### Comparação de objetos

```java
  MyClass object1 = new MyClass("test");
  MyClass object2 = new MyClass("test");
  MyClass object3 = object1;

  // Comparação de referências, neste caso é falso
  object1 == object2

  // Verdadeiro
  object1 == object3

  // Equals é utilizado para comparar Strings inicialmente, para objetos complexos o médoto precisa ser ser sobrescrito
  "waka".equals("foo")

  // Sobrescevendo o método equals na classe Funcionário
  @Override
  public boolean equals(Object f) {
    if(f == this) return true;
    if(!(f instanceof Funcionario)) return false;

    Funcionario ff = (Funcionario) f;
    if(ff.getNome() != this.getNome()) return false;
    if(ff.getDepartamento() != this.getDepartamento()) return false;
    if(ff.getSalario() != this.getSalario()) return false;
    if(ff.getDataEntrada().getFormatada() != this.getDataEntrada().getFormatada()) return false;
    if(ff.getRg() != this.getRg()) return false;
    return true;
  }
```

### Alteradores de acesso

```java
class Conta {
  // Quando eu restrinjo o acesso a variável saldo para leitura isso também vale para a escrita
  // Para manipular os dados com maior controle precisamos implementar métodos para "ler" e "escrever"
  private double saldo;

  // Definir algo como private faz com que a troca de mensagensseja reduzida e melhor controlada
  public double pegaSaldo(){ ... }
  public double defineSaldo(){ ... }

  // Padrão do java
  public <tipo-de-dado> set<Nome-da-variavel> {}
  public <tipo-de-dado> get<Nome-da-variavel> {}
}
```

Isso se chama **encapsulamento**, que consiste no controle do acesso/visibilidade de algum tipo de recurso. Caso tentemos acessar uma variável privada diretamente acontecerá um erro de compilação:

```bash
TesteFuncionario.java:117: error: nome has private access in Funcionario
		joao.nome = "Foo";
		    ^
1 error
```

O **private** faz com que métodos e atributos sejam visíveis/acessáveis somente pela própria classe, enquanto que o **public** faz com que métodos e atributos sejam visíveis pela própria classe e por qualquer outra.

O encapsulamento facilita a propagação de mudanças, não precisamos saber como é a implementação de um objeto, apenas confiar que seu comportamento será o que esperamos.

### Construtores

Usamos os contrutores para construir os objetos com os atributos que queremos, sem ter que usar uma setter para cada campo. Quando não definimos um construtor para uma classe o java atraibui um default para ela, onde nenhuma manipulação é feita. Uma classe pode ter diversos construtores, o que irá variar é apenas a assinaturado método e os tipos de dados, não pode haver redundância.

**Um construtor não tem valor de retorno** e a partir do momento que eu criar meu primeiro construtor **o default deixará de existir**. Um construtor pode chamar o outro, isso evita a duplicação de código, por exemplo:

```java
// ...

public Conta(int numero, double limite) {
    this(numero, limite, 0);
}

public Conta(int numero, double limite, double saldoInicial) {
    this.numero = numero;
    this.limite = limite;
    this.saldo = saldoInicial;
}

// ...
```

### Atributos e métodos estáticos

Podemos compartilhar dados entre diversos objetos, como por exemplo um **contador**. Para fazer isso utilizamos o alterador **static**, como por exemplo ```private static int contador```. Essa variável irá pertencer apenas a classe, sendo global para todos os objetos. Um detalhe importante é que **métodos estáticos só enxergam métodos e recursos estáticos**.

E pelo fato do atributo ser estático e privado teremos que criar um getter para ele:

```java
class Conta {
  private static contador;

  public Conta(){
    Conta.contador = Conta.contador + 1;
  }

  public int getContador(){
    return Conta.contador;
  }

  public static int getContador(){
    return Conta.contador;
  }
}
Conta c = new Conta();

// Duas formas de obter uma conta
c.getContador();
Conta.getContador();

// TesteFoo.java
class Teste {
    // Uma atributo sem alterador é de objeto por padrão
    int x = 37;

    public static void main(String[] args) {
        System.out.println(x);
    }
}

// Gera um problema de compilação nas referências
TesteFoo.java:5: error: non-static variable x cannot be referenced from a static context
        System.out.println(x);
                           ^
1 error
```

Um exemplo de método estático é o **main**, ele deve ser público(```public```), estático(```static```), não ter valor de retorno(```void```) e receber como parâmetros um array de strings (```String[]```).
