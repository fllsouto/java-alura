<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:pageTemplate
	titulo="Livros de Java, Android, IOs, Mobile e muito mais...">
	<h1>Lista de Produtos</h1>
	<p>${sucesso}</p>
	<p>${falha}</p>
	<div class="container">
		<h1>Produto cadastrado com sucesso!</h1>

	</div>
</tags:pageTemplate>