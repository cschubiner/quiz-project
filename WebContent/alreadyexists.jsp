<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Account</title>
</head>
<body>
<h1>The Name <%=request.getAttribute("username") %> is Already in Use</h1>

	<form action="CreateServlet" method="post">
		Please Enter Proposed User Name and Password <br>
		<br> User Name: <input type="text" name="account"> <br>
		<br> Password: <input type="text" name="password"> 
		<input type="submit" value="Login">
	</form>
</body>
</html>