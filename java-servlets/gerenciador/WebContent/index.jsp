<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<body>
		Bem vindo ao nosso gerenciador de empresas!<br/>
		<c:if test="${not empty usuarioLogado}">
			Você está logado como ${usuarioLogado.email}<br/>
		</c:if>
		<form action="fazTud?tarefa=novaEmpresa" method="post">
			Nome: <input type="text" name="nome" /><br/>
			<input type="submit" value="Enviar">
		</form>
		<br/>
		<form action="login" method="post">
			Email: <input type="text" name="email"/><br/>
			Senha: <input type="password" name="senha"/><br/>
			<input type="submit" value="Login"/>
		</form>
		<br/>
		<form action="fazTudo?tarefa-Logout" method="post">
			<input type="submit" value="Logout" />
		</form>
	</body>
</html>