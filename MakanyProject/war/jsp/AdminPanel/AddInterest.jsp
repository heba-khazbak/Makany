<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Interest</title>
</head>
<body>
<h1>Add Interest</h1>

<form action="/makany/AddInterest" method="post">
	Interest value: <input name="interestValue" type="text" required />
    
	<input type="submit" value="Add Interest"/>
	
	</form>

</body>
</html>