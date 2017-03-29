## Java JSTL

JSP são muito bagunçadas e difíceis para dar manutenção. Uma alternativa é a **Java JSTL**, uma biblioteca de tags que facilitam a escrita de código HTML através do embedding de código Java.

Para fazer um for-loop temos o seguinte exemplo:

```java
<c:forEach var="p" items="${produtoList}" varStatus="st">
  <tr id="produtos${p.id}">
    <td>${st.count}</td>
    <td>${p.nome}</td>
    <td>${p.preco}</td>
    <td>${p.descricao}</td>
    <td>${p.dataInicioVenda.time}</td>
    <c:if test="${p.usado}">
      <td>Sim</td>
    </c:if>
    <c:if test="#${not p.usado}">
      <td>Não</td>
    </c:if>
  </tr>
</c:forEach>
```
