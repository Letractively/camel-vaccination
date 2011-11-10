<%-- 
    Document   : index
    Created on : 9-nov-2011, 16.19.49
    Author     : Lorenzo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="servlet" method="POST">
            Username: <input type="text" name="user" value="" /><BR>
            Password: <input type="password" name="password" value="" /><BR>
            <input type="radio" name="type" value="medico" />medico 
            <input type="radio" name="type" value="paziente" />paziente
            <input type="submit" name="ok" value="Login" />
        </form>
        Hai dimenticato la password? <a href="recupera.jsp" title="Registrati">Recupero password</a><BR>
        Sei un nuovo utente? <a href="registrati.jsp" title="Registrati">Registrati</a><BR>
        
    </body>
</html>
