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
            <li><a href="<%=request.getContextPath()%>/user/inscription.htm">Inscription</a></li>
            <li><a href="<%=request.getContextPath()%>/login">Login</a></li>
            <li><a href="<%=request.getContextPath()%>/user/listeUser.htm">Les utilisateurs</a></li>
            <li><a href="<%=request.getContextPath()%>/livre/ajoutLivre.htm">Ajouter un livre</a></li>
            <li><a href="<%=request.getContextPath()%>/livre/lesLivres.htm">Les livres</a></li>
        </ul>
    </nav>
    </body>
</html>
