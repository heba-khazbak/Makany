<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add District</title>
</head>
<body>
<h1>Add District</h1>

<form action="/makany/AddDistrict" method="post">
	District name: <input name="districtName" type="text" required />
    
	<input type="submit" value="Add District"/>
	
	</form>

</body>
</html>