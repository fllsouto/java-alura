# Introdução ao MySQL I

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
