<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Categories</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
	crossorigin="anonymous">

<style>
body {
	padding-top: 50px;
}

.starter-template {
	padding: 40px 15px;
	text-align: center;
}
</style>
</head>
<body>

	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Makany</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li><a href="/makany/HomePage">Home</a></li>
				<li><a href="/makany/ShowAllDistricts">District</a></li>
				<li class="active"><a href="/makany/ShowAllCategories">Categories</a></li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
	</nav>

	<div class="container">

		<div class="starter-template">

			<h1>Makany Admin Panel</h1>

			<div class="panel panel-default">
				<div class="panel-heading">Add District</div>
				<div class="panel-body"></div>
				<form class="form-inline" role="form" action="/makany/AddCategory"
					method="post">
					<div class="form-group">
						<label>Category name:</label> <input type="text"
							name="categoryValue" class="form-control" required>
					</div>
					

					<button type="submit" class="btn btn-default">Add Category</button>
				</form>
				<br>
			</div>




			<%@ page import="com.eg.Makany.Models.Category"%>
			<%@ page import="java.util.*"%>

			
			

			<%
			String json =(String) session.getAttribute("myCategories");
			Vector<Category> myCategories = Category.parseFromJson(json);

			for (Category C : myCategories) {%>
					
			
			<div class="panel panel-default">
				<div class="panel-body"><%=C.getCategoryValue()%></div>

			<form class="form-inline" role="form" action="/makany/EditCategory" method="post">
				<input name="categoryValue" type="hidden"
					value=<%=C.getCategoryValue()%> /> 
					<div class="form-group">
						<input name="newName" class="form-control" type="text" 
						value =<%=C.getCategoryValue()%>/>
					</div>

					 <input class="btn btn-default" type="submit" value="Edit" />
			</form>
			<br>
			<form action="/makany/DeleteCategory" method="post">
				<input name="categoryValue" type="hidden"
					value=<%=C.getCategoryValue()%> /> <input type="submit" class="btn btn-default"
					value="Delete" />
			</form>
			<br>
			</div>
			<%
				}
			%>
			
		</div>

	</div>
	<!-- /.container -->



</body>
</html>