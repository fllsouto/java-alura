# Java 1 - Primeiros Passos

## Indice

- [Introdução](#introduction)
- [Um programa simples em Java](#a-simple-java-program)
- [Compilando um código em Java](#compiling-java-sourcode)

## Introdução

A **Sun** no começo produzia software embarcado, que rodava em diversos tipos de dispositivos, como geladeiras, carros e etc. Existia uma grande variadade de hardwares, isso dificultava a escrita dos softwares pelos desenvolvedores, cada plataforma precisava de um cuidado especial, fazendo com o que o código escrito fosse **portavel**. No começo o projeto se chamava **Oak**.

O Java resolve o problema de diferentes sistemas operacionais e hardwares executando o mesmo código. Isso é possível através do pseudo-código gerado pela compilação do arquivo **.java** chamado de **bytecode**. Esse **bytecode** é lido de diferentes formas, de acordo com o sistema operacional e suas implementações da **JVM (Java Virtual Machine)**.

A máquina virtual é um conceito um mais alto nível do que um simples **interpretador**. Uma máquina virtual é responsável pelo gerencimento de memória, pilha de execução, threads e etc... A aplicação é executada sem que aja envolvimento direto com o sistema operacional, apenas com a JVM. Existe um total **isolamento**

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
