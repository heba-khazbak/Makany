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
 <br>  <br>  <br> 
<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h1 class="text-center login-title">Sign in to Makany Admin</h1>
            <div class="account-wall">
                
                <form class="form-signin" action="/makany/login" method="post">
                <input type="text" class="form-control"  name="username" placeholder="UserName" required autofocus>
                <input type="password" class="form-control" name="password" placeholder="Password" required>
                <button class="btn btn-lg btn-primary btn-block" type="submit">
                    Sign in</button>
                
                
                </form>
            </div>
            <br> 
            <a href="/makany/CreateAdminPage/" class="text-center new-account">Create an account </a>
        </div>
    </div>
</div>


	
 <br>
</body>
</html>