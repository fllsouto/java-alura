# HTTP - Entendendo a Web...
## Indice

- [O que é o HTTP?](#o-que-é-o-http?)
- [A web segura - HTTPS](#a-web-segura-https)

## O que é o HTTP?

**Hipertext transfer protocol**, ou HTTP, é um dos protocolos de comunicação mais utilizados na internet. Podemos entender um protocolo de comunicação como:

> In telecommunications, a communication protocol is a system of rules that allow two or more entities of a communications system to transmit information via any kind of variation of a physical quantity. These are the rules or standard that defines the syntax, semantics and synchronization of communication and possible error recovery methods. Protocols may be implemented by hardware, software, or a combination of both.

> Fonte: [Comunication Protocol](https://en.wikipedia.org/wiki/Communications_protocol#cite_ref-1)


Como podemos ver, um protocolo de comunicação é um **conjunto de regras** que entidades devem seguir para poderem se comunicar através de algum mecanismo físico. Esse protocolo estabelece como a informação será trocada, qual a semântica será utilizada e como a comunicação será sincronizada.

Esse protocolo funciona através do modelo **Client-Server**. O browser é o cliente na comunicação e requisita informações. O servidor é uma aplicação rodando em alguma máquina que irá receber requisições, processar e devolver uma resposta para o cliente.

O HTTP irá estabelecer as regras de comunicação entre as duas entidades. Na comunicação humana usamos a fala e a escrita para trocar informação com as pessoas. No Brasil usamos o a lingua Portuguêsa como protocolo de comunicação, e ela possui suas regras, sintaxe, vocabulário e etc, exatamente como um protocolo.

Outro exemplo de protocolo de comunicação é **P2P (Peer-to-Peer)** utilizado nos programas de Torrent. A arquitetura de comunicação é diferente do utilizado no HTTP, aqui cada cliente também faz o papel de servidor, constituindo uma configuração totalmente distribuída.

## A web segura - HTTPS

O protocolo HTTP se comunica utilizando **plain-text**, ou seja, texto puro. Não existe nenhuma proteção dos dados trocados. Durante a comunicação entre o cliente e o servidor existe diversos intermediários, como roteadores, switchs, e etc. Os dados trafegados entre esses intermediários podem ser interceptados, precisamos de uma abordagem mais segura.

Existem empresas que certificam que um site é seguro e confiavel. Isso é feito através de um certificado digital, que atesta a identidade de um site. Esse certificado também guarda a chave pública que será utilizada na criptografia dos dados.

Existem dois tipos de criptografia usando chaves:
- Assimétrica: As duas chaves são diferentes, uma mensagem cifrada pela chave C_1 só poderá ser decifrada pela chave C_2, e vice-versa. Esse tipo de criptografia costuma ser mais lenta
- Simétrica: As duas chaves são iguais, ou seja, qualquer pessoa que tiver uma das chaves pode saber o conteudo da mensagem. Esse tipo de criptografia é muito mais rápida

Como uma das criptografias é mais lenta e outra mais rápida o HTTPS faz uma combinação das duas abordagens:

1. O cliente inicia a comunicação com o servidor
2. O servidor gera uma chave simétrica exclusiva para o cliente que está se comunicado com ele
3. A chave é criptograda com outra chave assimétrica e mandada para o cliente
4. O cliente descriptografa a chave simétrica e utiliza ela na comunicação


## Entendendo a Estrutura da URL

Uma **URL (Uniform Resource Locator** é o endereço de um _Web Resource_ em uma rede de computadores. Esse _resource_ pode ser uma aplicação qualquer, como um site por exemplo.

Uma URL é comumente utilizada junto com uma página web (http), mas também pode ser empregado em outros tipos:
- FTP - Transferência de dados
- mailto - Troca de emails
- JDBC - Acesso a banco de dados

Uma URL é um caso especial de uma **[URI (Uniform Resource Identifier)](https://en.wikipedia.org/wiki/Uniform_Resource_Identifier)**, que é uma string utilizada para identificar qualquer tipo de recurso computacional. Esse recurso pode ser de qualquer tipo, como pastas, computadores na rede, arquivos em um sistema de arquivos e etc.

Nós interagimos com sites através de uma URL, mas o navegador faz isso utilizando o endereço **IP**. Podemos perguntar qual o endereço de uma página através do comando **nslookup**:

```
$ nslookup www.google.com.br
Server:		192.168.100.1
Address:	192.168.100.1#53

Non-authoritative answer:
Name:	www.google.com.br
Address: 172.217.29.195
```

O navegador irá identificar um computador na rede através do endereço acima. Porém, precisamos saber como entrar no computador, através de uma **Porta**. Podem existir milhares de portas, mas só algumas estão disponíveis. A porta padrão do HTTP e do HTTPS são **80** respecticamente **443**.

O padrão de uma URL é o seguinte:
```
URL:

protocolo://dominio:porta/recurso

Exemplo:
https://www.alura.com.br/forum/all/1
```

## O cliente pede e o servidor responde

O modelo de comunicação utilizado pelo HTTP é o **Request-Response**. Cada requisição é independente, dizemos então que o protocolo HTTP é _stateless_, ou seja, não guarda os estados anteriores, precisamos providenciar todas as informações a cada requisição. Porém podemos armazenar estado do lado do cliente utilizando **[Cookies](https://en.wikipedia.org/wiki/HTTP_cookie)**, eles são úteis para guardar informações e minizar a troca de dados entre o cliente e o servidor.

## Depurando a requisição HTTP
Podemos depurar requisições **HTTP** utilizando browsers como o firefox ou chrome. Na tab **network** do chrome podemos verificar a resposta de uma requisição. Os diferentes estados dizem se uma requisição deu certo ou não (código 200 e 404 respecticamente) ou se o recurso foi transferido para outro lugar (código 404).

Cada [código de erro](https://www.w3schools.com/tags/ref_httpmessages.asp) significa uma coisa específica. Podemos consultar a documentação do chrome para entender cada informação, como [o tempo de carregamento de recursos](https://developers.google.com/web/tools/chrome-devtools/network-performance/resource-loading). Referência adicionar de erros: [http statuses](https://httpstatuses.com/500).

## Métodos HTTP e parâmetros de requisição

Podemos passar parâmetros em uma requisição GET através de **query-parameters**, a sintaxe é a seguinte:

```
http://www.google.com.br/?nome1=valo1&nome2=valor2...
```

O protocolo HTTP nos orienta a usar o GET apenas para acessar dados, mas isso é totalmente arbitrário, depende unicamente da boa prática do programador. Podemos usar outros métodos como o GET no lugar que caberia melhor o REMOVE: `/vendas/remove?id=53`. 
