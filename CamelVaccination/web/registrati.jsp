<%-- 
    Document   : registrati
    Created on : Nov 9, 2011, 5:03:17 PM
    Author     : administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrazione</title>
    </head>
    <body>
        <form action="Registrati" method="POST">
            Username:<input type="text" name="user" value="" /><BR>
            Password<input type="password" name="password1" value="" /><BR>
            Re-enter Password<input type="password" name="password2" value="" /><BR>
        
            <img src="/core/captcha.jpg" />
            <input type="text" name="captcha" value="" />
                
            <input type="submit" value="Registrami" /><BR>
        </form>
    </body>
</html>
