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
