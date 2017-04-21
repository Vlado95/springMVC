<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>
    </head>

    <body>
    <h2>Welcome ${user.nom} ${user.prenom} !</h2>
    <nav>
        <ul>
            <li><a href="<%=request.getContextPath()%>/login">Login</a></li>
            <li><a href="<%=request.getContextPath()%>/editeurs">Les Edtieurs</a></li>
        </ul>
    </nav>
    </body>
</html>
