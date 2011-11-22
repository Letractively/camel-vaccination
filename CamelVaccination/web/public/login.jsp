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
        <link rel="stylesheet" type ="text/css" href="/style.css" />
        <title>JSP Page</title>
    </head>
    <body>
        <fieldset>
            <legend>Login</legend>
            <form action="Login" method="POST">
                <p class="form"><label for="user">Username: </label>
                    <input type="text" id="user" name="user" value="" /></p>
                <p class="form"><label for="password">Password: </label>
                    <input type="password" id="password" name="password" value="" /></p>
                <p class="form"><label for="medico">Medico</label>
                    <input type="radio" class="radios" name="type" id="medico" value="medico" checked="checked"/>
                <label for="paziente" class="radios">Paziente</label>
                    <input type="radio" class="radios" name="type" id="paziente" value="paziente" /></p>
                <p class="submit"><input type="submit" name="ok" value="Login" /></p>
            </form>
        </fieldset>
        <div id="login">
        <!--<p>Hai dimenticato la password? <a href="recupera.jsp" title="Registrati">Recupero password</a></p>-->
        <p>Sei un nuovo utente? <a href="registrati.jsp" title="Registrati">Registrati</a></p>
        </div>
    </body>
</html>
