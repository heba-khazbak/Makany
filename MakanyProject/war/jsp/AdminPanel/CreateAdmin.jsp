<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Signup</title>
</head>
<body>
<h1>Admin Signup</h1>

<form action="/makany/CreateAdmin" method="post">
	User name: <input name="username" type="text" required />
	Password: <input name="password" type="password" required />
    
	<input type="submit" value="Signup"/>
	
	</form>

</body>
</html>