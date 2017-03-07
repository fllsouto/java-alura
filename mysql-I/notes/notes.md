# Introdução ao MySQL I

## Indice

- [Como instalar?](#como-instalar?)
- [Comandos básicos](#comandos-básicos)
- [Fazendo querys](#fazendo-querys)
- [Atualizando e apagando registros](#atualizando-e-apagando-registros)
- [Colocando valores defaults](#colocando-valores-defaults)
- [Funções de agregação](#funções-de-agregação)

## Como instalar?

- Tutorial de instalação para o Fedora, Centos OS ou RedHat: [link](https://www.if-not-true-then-false.com/2010/install-mysql-on-fedora-centos-red-hat-rhel/)
- Artigo sobre password plugin: [link](https://dev.mysql.com/doc/refman/5.7/en/validate-password-plugin.html)

## Comandos básicos

```sql
# Criar novo banco de dados
CREATE DATABASE controle_compras;

# Usar banco de dados
USE controle_compras;

# Criar nova tabela
CREATE TABLE COMPRAS (id int auto_increment primare key, valor double, data date, observacoes varchar(255), recebido boolean);

# Descrever a tablea criada
desc compras;

# Inserindo novos dados
INSERT INTO COMPRAS (VALOR, DATA, OBSERVACOES, RECEBIDO) VALUES (100.0, '2017-02-01', 'COMPRAS DE FEVEREIRO', 1);

```

## Fazendo querys

```sql
# Query 1
SELECT valor, observacoes, data from COMPRAS where data > '2008-12-15';

# Query 2
SELECT valor, observacoes, data from COMPRAS where data > '2008-12-15' AND data < '2010-12-15';

# Query 3
SELECT valor, observacoes, data from COMPRAS where valor >= 15.0 AND valor <= 35.0;

# Query 4
SELECT valor, observacoes, data from COMPRAS where valor >= 15.0 AND valor <= 35.0 AND observacoes like "LANCHONETE%";


# Query 5.1
SELECT * from COMPRAS where recebido = 1;

# Query 5.2
SELECT * from COMPRAS where recebido = TRUE;

# Query 6
SELECT * from COMPRAS where valor > 5000.0 OR recebido = FALSE;

# Query 7
SELECT * FROM COMPRAS WHERE VALOR = 108.0;
```

## Atualizando e apagando registros

```sql
# Update 1
UPDATE COMPRAS SET OBSERVACOES = 'preparando o natal' where data = '2010-12-20';


# Update 2
UPDATE COMPRAS SET valor = valor + 10 where data < '2009-06-01';

# Update 3
UPDATE COMPRAS SET observacoes='entregues antes de 2011', recebido = TRUE where (data between '2009-07-01' AND '2010-07-01');

# Delete 1
DELETE FROM COMPRAS WHERE data between '2009-03-05' AND '2009-03-20';
```

## Colocando valores defaults

```sql
# Aqui estamos colocando um valor null no campo observação
INSERT INTO COMPRAS (VALOR, DATA, OBSERVACOES, RECEBIDO) VALUES (100.0, '2010-10-10', NULL, 1);

# Estamos alterando a configuração das tabelas para estabelecer que um valor não será nulo
ALTER TABLE COMPRAS MODIFY COLUMN OBSERVACOES TEXT NOT NULL

# Alteramos novamente para definir um valor default
ALTER TABLE COMPRAS MODIFY COLUMN RECEBIDO TINYINT(1) DEFAULT '0';

# Mudando nome da coluna
ALTER TABLE COMPRAS CHANGE RECEBIDO recebido TINYINT(1);

# Com essa alteração não precisamos colocar o status recebido
INSERT INTO COMPRAS (VALOR, DATA, OBSERVACOES) VALUES (189.76, '2009-02-09', 'UMA COMPRA QUALQUER');

# Update de todos os campos
update COMPRAS SET forma_pagt = 'dinheiro' where forma_pagt is null;

# Update todas as observacoes
UPDATE COMPRAS SET observacoes = '(VAZIO)' where observacoes is null;

# Alterando o tipo de dado que uma coluna pode ter
ALTER TABLE COMPRAS modify column observacoes varchar(255) not null;

# Criando nova coluna com valores pré defenidos
ALTER TABLE COMPRAS ADD COLUMN forma_pagt ENUM('cartao', 'boleto', 'dinheiro');

# Create table com campos customizados
CREATE TABLE COMPRAS (
  id int not null auto_increment,
  valor double,
  data date,
  observacoes varchar(255) not null,
  recebido tinyint(1) defaults '1',
  forma_pagt enum('dinheiro', 'cartao', 'boleto'),
  PRIMARY KEY(id)
);
```

## Funções de agregação


```sql
# Média com agrupamento
select sum(valor) from COMPRAS where data < '2009-05-12' group by forma_pagt;

# Contagem com agrupamento
select count(valor) from COMPRAS where data < '2009-05-12' group by forma_pagt;

# Contagem
select count(id) from COMPRAS where data < '2009-05-12' AND recebido is TRUE;

```
