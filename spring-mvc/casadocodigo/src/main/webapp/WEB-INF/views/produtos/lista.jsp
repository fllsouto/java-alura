<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:pageTemplate
	titulo="Livros de Java, Android, IOs, Mobile e muito mais...">
	<h1>Lista de Produtos</h1>
	<p>${sucesso}</p>
	<p>${falha}</p>
	<div class="container">
		<table class="table table-bordered table-striped table-hover">
			<tr>
				<th>Título</th>
				<th>Descrição</th>
				<th>Páginas</th>
				<th>Sumario</th>
			</tr>
			<c:forEach items="${produtos}" var="produto">
				<tr>
					<td><a
						href="${s:mvcUrl('PC#detalheTitulo').arg(0, produto.titulo).build()}">${produto.titulo}</a></td>
					<td>${produto.descricao}</td>
					<td>${produto.paginas}</td>
					<td>${produto.sumarioPath}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</tags:pageTemplate>