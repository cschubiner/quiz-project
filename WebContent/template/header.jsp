<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="template/style.css" />
<title>Home - Quiztopia</title>
</head>

<body>


	<div id="page">

		<div id="header">
			<h1>Quiztopia</h1>
			<h2>Your number 1 place to take quizzes ;)</h2>

		</div>
		<div id="bar">
			<div class="link">
				<a href="index.jsp">Home</a>
			</div>
			<div class="link">
				<a href="QuizListServlet">Quizzes</a>
			</div>
			<div class="link">
				<a href="#">User Profile</a>
			</div>
			<div class="link">
				<a href="#">About</a>
			</div>
			<%
				Object username = request.getSession().getAttribute("username");
				if (username == null) {
					out.println("<div class=\"link\">");
					out.println("<a href=\"login.jsp\">Login</a>");
					out.println("</div>");
				}
			%>
		</div>