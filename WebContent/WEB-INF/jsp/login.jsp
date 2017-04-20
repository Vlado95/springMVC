<%-- 
    Document   : login
    Created on : 10 sept. 2014, 16:32:02
    Author     : Fitec
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login!</h1>
        <form:form action="" method="post" modelAttribute="user">
            <form:label path="email">Email : </form:label><br>
            <form:input path="email"/><br>
            <form:errors path="email" cssStyle="color:red;"/><br>
            <form:label path="password">Password : </form:label><br>
            <form:password path="password"/><br>
            <form:errors path="password" cssStyle="color:red;"/><br>
            <form:button>Login</form:button>
        </form:form>
    </body>
</html>
