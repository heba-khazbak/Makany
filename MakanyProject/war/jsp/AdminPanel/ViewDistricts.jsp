<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Districts</title>
</head>
<body>
<%@ page import="com.eg.Makany.Models.District" %>
<%@ page import="java.util.*" %>

<%
	String json =(String) session.getAttribute("myDistricts");
	Vector<District> myDistricts = District.parseFromJson(json);
	
	for (District D : myDistricts)
	{
		out.println(D.getDistrictName()+ "<br>");
%>

		<form action="/makany/EditDistrict" method="post">
		<input name="districtName" type="hidden" value=<%=D.getDistrictName() %> />
		<input name="newName" type="text"  />
		
		<input type="submit" value="Edit"/>
		</form>
		
		<form action="/makany/DeleteDistrict" method="post">
		<input name="districtName" type="hidden" value=<%=D.getDistrictName() %> />
		<input type="submit" value="Delete"/>
		</form>
		<br>
<% 
	}

%>

</body>
</html>