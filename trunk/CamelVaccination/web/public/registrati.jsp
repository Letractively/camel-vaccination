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
        <link rel="stylesheet" type ="text/css" href="../style.css" />
        <title>Registrazione</title>
    </head>
    <body>
        <fieldset>
            <legend>Registrati</legend>
            <form action="Registrati" method="POST">
                <p class="form"><label for="user">Username: </label>
                    <input type="text" id="user" name="user" value="" /></p>
                <p class="form"><label for="password">Password: </label>
                    <input type="password" id="password" name="password" value="" /></p>
                <p class="form"><label for="confirm_password">Re-enter Password:</label>
                    <input type="password" id="confirm_password" name="confirm_password" value="" /></p>
                <p class="form"><img id="captcha" src="jcaptcha.jpg" alt="captcha"/></p>
                <p class="form"><label for="confirmation_code">Confirmation Code:</label>
                    <input type="text" id="confirmation_code" name="jcaptcha" value="" /></p>               
                <p class="submit"><input type="submit" value="Invia" /></p>
            </form>
        </fieldset>
    </body>
</html>