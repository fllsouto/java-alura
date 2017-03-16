# Java e JDBC

## Trabalhando com um banco de dados

O **JDBC** é uma interface comum para lidar com diferentes banco de dados. Cada vendor de banco de dados irá implementar essa interface que irá interagir com seu banco de dados em baixo nível.

Para utilizar o **JDBC** primeiro devemos criar uma **Statement** a partir de uma **Connection**:

```java
// Criando um statement
Statement stmt = connection.createStatement();

// Executando uma query, o valor de retorno será true se recebermos uma lista de resultados
Statement stmt = stmt.execute("sekect * from Produto");

// O resultado da query fica em uma estrutura chamada ResultSet
ResultSet resultSet = stmt.getResultSet();

// O result set é como uma lista ligada, cada element guarda informação de si e do próximo, e podemos utilizar o método next() para iterar

// Ao fim o ResultSet e o Statement devem ser fechados

stmt.close();
resultSet.close();
```

As interfaces **Connection, ResourceSet e Statement** herdam da interface **AutoCoseable**, isso indica que o recurso pode ser fechado automaticamente, através de um try-with-resources:

```java
try(Connection c = buscaConexao()) {
  // só usar a conexão, e ela será fechada ao fim do bloco
}
```

Por padrão o JDBC funciona no modo **autoCommit**, mas podemos controlar o momento que o commit transação será realizada.

Podemos criar um **pool de conexões** através da interface DataSource, que é implementado pelo vendor do banco de dados. Esse pool diminui o custo de abrir uma conexão nova com o banco de dados.

## Mapeado dados em objetos: O Data Access Object
Podemos querer transformar um objeto em um registro do banco de dados, e vice-versa. Teremos que lidar com o acesso dos dados, e esse acesso será feito da mesma forma para todas os registros. Utilizamos a técnica conhecida como **Data Access Object (DAO)**, que consiste em uma forma centralizada e unificada de lidar com os dados de um banco de dados. A maior vantagem do DAO é que ele cria uma forma uniformizada de acesso.

Isolamos todo o código de acessa o repositório de dados em apenas um lugar, isso facilita a manutenção. Uma possível falha de um DAO é se cada um deles utilizar sua própria conexão com o banco de dados, isso prejudica a gestão eficiente de recursos computacionais e impossibilita o compartilhamento de uma mesma transação por diferentes DAOS.

Com o JDBC temos o controle fino da manipula
## Referências

- [JDBC](http://tutorials.jenkov.com/jdbc/index.html)
- [Java Persistence](http://tutorials.jenkov.com/java-persistence/index.html)
