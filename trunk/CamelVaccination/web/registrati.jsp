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
        <title>JSP Page</title>
    </head>
    <body>
<form action="registrati" method="POST">
	Username:<input type="text" name="user" value="" /><BR>
	Password<input type="password" name="password1" value="" /><BR>
	Re-enter Password<input type="password" name="password2" value="" /><BR>
	<!-- Captcha -->
	<input type="submit" name="registrami" value="Registrami" /><BR>
</form>
    </body>
</html>
