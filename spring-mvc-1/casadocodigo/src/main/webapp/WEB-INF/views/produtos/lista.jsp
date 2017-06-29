<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Livros de Java, Android, iPhone, Ruby, PHP e muito mais -
	Casa do Código</title>
</head>
<body>
	<h1>Lista de Produtos</h1>
	<p>${sucesso}</p>
	<p>${falha}</p>
	<table>
		<tr>
			<td>Id</td>
			<td>Título</td>
			<td>Descrição</td>
			<td>Páginas</td>
			<td>Sumario</td>
		</tr>
		<c:forEach items="${produtos}" var="produto">
			<tr>
				<td><a
					href="${s:mvcUrl('PC#detalheId').arg(0, produto.id).build()}">${produto.id}</a></td>
				<td><a
					href="${s:mvcUrl('PC#detalheTitulo').arg(0, produto.titulo).build()}">${produto.titulo}</a></td>
				<td>${produto.descricao}</td>
				<td>${produto.paginas}</td>
				<td>${produto.sumarioPath}</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>