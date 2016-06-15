<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
<title>Admin Login</title>
</head>
<body>

<form class="navbar-form navbar-left" action="/makany/login" method="post">
<div class="form-group">

	User name: <input class="form-control" name="username" type="text" required /> 
	Password: <input  class="form-control"name="password" type="password" required /> 
    
	<input class="btn btn-default" type="submit" value="Login"/>
	</div>
	</form>
	<br> <br>
<a  href="/makany/CreateAdminPage/">Create Admin</a> <br>
</body>
</html>