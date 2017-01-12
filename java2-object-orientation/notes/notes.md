# Java 2 - Orientação a Objetos

## Indice

- [Introdução](#introdução)


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
