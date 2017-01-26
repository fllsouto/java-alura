# Java 2 - Orientação a Objetos

## Indice

- [Introdução](#introdução)
- [Herança](#herança)
  - [Atributos privados e protegidos](#atributos-privados-e-protegidos)
  - [Polimorfismo](#polimorfismo)
- [Classes abstratas](#classes-abstratas)
- [Interface](#interface)
- [Exceções](#exceções)


## Herança

Quando queremos reaproveitar comportamento entre classes, ou mesmo variáveis a **Herança** é uma técnica muito utilizada. No exemplo a seguir temos as classes **Funcionario** e **Gerente**. Um gerente se diferencia de um funcionário apenas pelo seu valor de bônus calculado, portanto um gerente pode herdar os comportamentos do funcionário.

```java
class Funcionario {
  private String nome;
  private double salario;

  public void setSalario(double salario) {
    this.salario = salario;
  }

  public double getBonus() {
    return this.salario * 0.2;
  }
}

class Gerente extends Funcionario {
  public double getBonus() {
    return this.salario * 0.3;
  }
}
```

Leitura futura: [Como não aprender orientação a objetos: Herança](http://blog.caelum.com.br/como-nao-aprender-orientacao-a-objetos-heranca/)

### Atributos privados e protegidos

O código apresentado anteriormente possui um problema, a variavel salário está sendo utilizada pelo gerente, porém ela está definida como **private**, fazendo com que ela seja visivel **apenas dentro da classe Funcionário**. Para funcionar corretamente precisamos utilizar o alterar de acesso ```protected```.

Considere que temos dois packages diferentes, PackageA e PackageB, onde o primeiro tem as classes Alpha e Beta, e o segundo as classes AlphaSub e Gama. A tabela a seguir define os níveis de acesso entre as classes com a classe Alpha:

```
____________
| PackageA  |
|           |
| Alpha     |
| Beta      |
-------------

^
|
| Relação entre as classes e Alpha
|
V
____________
| PackageB  |
|           |
| AlphaSub  |
| Gama      |
-------------
```

Modificador       | Alpha | Beta  | AlphaSub  | Gamma
--                |---    |---    |---        |--     
public            |Y      |Y      |Y          |Y
protected         |Y      |Y      |Y          |N
sem modificador   |Y      |Y      |N          |N
private           |Y      |N      |N          |N

### Polimorfismo

A nossa classe gerente está extendendo outra classe, isso faz com que todo gerente seja um funcionário, porém essa relação não é reflexiva. Podemos utilizar uma variável do tipo Funcionario para guardar um gerente, por exemplo:

```java
Gerente joaquim = new Gerente();
Funcionario fun = joaquim;
fun.setSalario(2000.0);
```

A variável fun guarda uma referência para um objeto do tipo funcionário, nunca o objeto em si. Através dela podemos mandar mensagens que um funcionario entenderia, como `setSalario`. Essa pluraridade na hora de referenciar um objeto é chamado de **Polimorfismo**, o objeto não se transforma ao longo da execução, o que muda é como referenciamos ele. Um exemplo prático está descrito no programa `src/TestePolimorfismo.java`. Não importa como estamos referenciando um objeto, o método invocado sempre será o dele, caso o método não exista o java irá procurar em tempo de execução qual superclasse implementa ele.

O Polimorfismo diminui o acoplamento entre as classes, adicionar um novo tipo de funcionário torna-se fácil, basta que a nova classe implemente os métodos necessários. Uma futura mudança também se beneficia disso, a quantidade de lugares que precisarão ser alterados é reduzida.

## Classes abstratas

Muitas vezes precisamos de classes que servem apenas como um esquema, um prótipo. Essas classes definem uma estrutura que poderá ser herdada, uma coleção de métodos que deverão ser implementados pela classe filha e outra coleção de métodos ja implementados. Esse tipo de classe é chamado de **classe abstrata** e é útil em java quando precisamos criar um tipo de dado que pode ser manipulado indiretamente, mas nunca criado diretamente. A utilidade disso tudo é poder referenciar um objeto como uma classe abstrata e utilizar os seus métodos definidos, sem que precisemos saber qual o tipo de dado que está realmente sendo manipulado.

```java
abstract class Funcionario {
  // Essa é a definição de um método abstrato, isso cria um contrato entre a classe abstrata e a concreta. A classe filha precisa implementar o método abstrato, caso contrário isso irá gerar um erro
  // de compilação. É necessário que seja desse jeito senão não seria possível fazer uso do polimorfismo das classes que implementam Funcionario
  abstract double getBonificacao();
}

abstract class Secretaria extends Funcionario {
  // Uma classe abstata pode implementar outra classe abstrata, poderiamos implementar os métodos também caso seja necessário
}

class SecretariaAgencia extends Secretaria {
  // Essa classe precisa implementar o método getBonificacao
}
```

A anotação **@Override** informa para o compilador que um método em questão está sendo sobrescrito, isso é útil caso aconteça algum erro de digitação. Podemos chamar métodos abstratos de dentro de um método concreto em uma classe abstrata, neste caso o importante não é o tipo da referência e sim o tipo do objeto referenciado.

## Interface

As classes abstratas são mecanismos úteis para compartilhar comportamento e variáveis através de herança, porém não é um mecanismo muito fino. Imagine que queremos que um grupo de funcionários do sistema (Gerentes e Diretores) tenham a capacidade de autenticação em um sistema interno. Para isso poderiamos criar uma classe que fosse filha de funcionário e Pai do conjunto citado. Isso resolveria nosso problema mas não seria fléxivel ao ponto de permitir compartilhar essa capacidade com outros tipos de usuários do sistema que não fosse funcionários (Clientes por exemplo).

Para resolver esse problema de compartilhar comportamentos de uma forma mais fina podemos recorrer as **Interfaces**, elas funcionam como contratos, semelhante aos métodos abstratos, porém uma classe pode implementar diversas interfaces de uma vezes. Podemos manipular um objeto através das interfaces que ele implementa, e quem implementar a interface tem a responsabilidade de implementar o contrato (métodos).

```java
// Uma interface define apenas o que se deve fazer, não como fazer
// Nem o que ele deve ter. A implementação da interface que irá definir isso
interface Autenticavel {
  // Métodos de interface são sempre públicos e abstratos
  boolean autentica(int senha);
  // ...
}

class Gerente extends Funcionario implements Autenticavel {
  // ...
}

// Podemos referenciar um Gerente de uma nova forma

Autenticavel a = new Gerente();
```

Quando estamos manipulando uma interface em vez de um objeto podemos chamar apenas métodos que fazem parte da interface, para maiores exemplos ver o programa `src/TesteConta.java`. Interfaces são uma forma elegante de dimunir o acoplamento entre as classes, embora exista uma repetição adicional de código. A **Composição** pode ajudar a diminuir a redundância presente no código.

## Exceções

Quando nosso programa recebe algum tipo de entrada não esperada ou cai em algum caso não coberto o programa, pode acontecer uma falha. O Java dispôe de mecanismos para lidar com erros, que são conhecidas como exceções. Uma exceção é um tipo de erro que acontece durante a execução de programa, como por exemplo o acesso a uma posição de memória não definida, a chamada de um método que não existe, o timeout de espera de algum serviço, dentre outros. Para controlar uma exceção utilizamos um bloco `try ... catch`:

```java
try {
  // Código que será executado
} catch(<tipo da execução a ser capturada> <nome do objeto do tipo exceção>) {
  // Tratamento da exceção
}
```
Podemos ter o seguinte trecho de código:

```java

class Conta {
  // ...

  public Conta buscaConta(String CPF, String secretToken) throws IOException{
    //.. Tenta buscar uma conta, caso contrário lança uma exceção
  }

  public void realizaTransação(double valor) {
    try {
      saque(valor)
    } catch(RuntimeException e) {
      System.out.println("Transação não realizada, Erro: " + e)
    }
  }

  private void saque(double valor) {
    if(this.saldo > valor) {
      this.salde -= valor
    } else {
      throws new RuntimeException("Saldo indisponível")
    }
  }
}

```

Neste caso estamos lançando uma nova exceção e capturando em uma função acima, entretanto alguns tipos de exceções precisam ser capturadas localmente como por exemplo `IOException`. Podemos resolver isso incluindo na assinatura do método que ele pode lançar uma exceção e delegar sua captura para estrutura acima, ou capturando em um bloco local. Esse tipo de exceção recebe o nome de **checked**, ao chamar um método ou construtor o compilador irá exigir que a exception seja tratada. Temos como exemplo o seguinte trecho:

```java
// Primeiro exemplo, não funciona

class Foo {
    public static void waka() {
        new java.io.FileInputStream("foobar.txt");
    }
}

/* Foo.java:3 unreported exception java.io.FileNotFoundException; must be caught or declared to be thrown
*    new java.io.FileReader("foobar");
* 1 error
*/


class Foo2 {
  // Segundo exemplo, tratando localmente
    public static void waka1() {
        try {
          new java.io.FileInputStream("foobar.txt");
        } catch(java.io.FileNotFoundException e) {
          System.out.println("Não foi possível abrir o arquivo para leitura!")
        }
    }

    public static void waka1() throws java.io.FileNotFoundException{
          new java.io.FileInputStream("foobar.txt");
    }
}

```

Podemos ter que lidar com mais de uma exceção por vez:

```java
// Assim:

try {
  //  Faz algo
} catch(Exception1 e) {
  // Captura 1
} catch(Exception2 e) {
  // Captura 2
}

// Ou assim:

public void foo() throws Exception1,Exception2 {
  // Faz algo
}
```

Junto com o tratamento de exceções temos a palavra reservada `finally`. O bloco `finally` sempre será executado, falhando o código ou não. Ele é útil para lidar com a liberação de recursos, como conexões com banco de dados, arquivos abertos e etc.


```java
try {
  // Tente executar
} catch(Exception foo) {
  // Se falhar
} finally {
  // Finalização para ambos os casos
}
```

Mais referências: [Lidando com exceptions](http://blog.caelum.com.br/lidando-com-exceptions/)
