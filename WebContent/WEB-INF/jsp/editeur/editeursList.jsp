<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Editeurs</title>
</head>
<body>
<h1>Les Editeurs</h1>
<a href="<%=request.getContextPath()%>/ajoutEditeur">Ajouter Edtieur</a>
        <ul>
            <c:forEach var="editeur" items="${listEditeur}">
                <li>${editeur.nom}</li>
            </c:forEach>
        </ul>
</body>
</html>