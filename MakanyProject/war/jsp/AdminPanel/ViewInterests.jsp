<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Districts</title>
</head>
<body>
<%@ page import="com.eg.Makany.Models.Interest" %>
<%@ page import="java.util.*" %>

<%
	String json =(String) session.getAttribute("myInterests");
	Vector<Interest> myInterests = Interest.parseFromJson(json);
	
	for (Interest I : myInterests)
	{
		out.println(I.getInterestValue()+ "<br>");
%>

		<form action="/makany/EditInterest" method="post">
		<input name="interestValue" type="hidden" value=<%=I.getInterestValue() %> />
		<input name="newName" type="text"  />
		
		<input type="submit" value="Edit"/>
		</form>
		
		<form action="/makany/DeleteInterest" method="post">
		<input name="interestValue" type="hidden" value=<%=I.getInterestValue() %> />
		<input type="submit" value="Delete"/>
		</form>
		<br>
<% 
	}

%>

</body>
</html>