<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<title>AjoutEditeur</title>
</head>
<body>
	<h1>Ajouter Editeur</h1>
	<form:form action="" method="post" modelAttribute="editeur">
		<form:label path="nom">nom : </form:label>
		<br>
		<form:input path="nom" />
		<br>
		<form:errors path="nom" cssStyle="color:red;" />
		<br>
		<form:button>Ajouter</form:button>
	</form:form>
</body>
</html>