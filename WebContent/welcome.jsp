<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="style.css" />
<title>Home - Quiztopia</title>
</head>

<body>
	<div id="page">

		<div id="header">
			<h1>Quiztopia</h1>
			<h2>Your number 1 place to take quizzes ;(</h2>

		</div>
		<div id="bar">
			<div class="link">
				<a href="#">home</a>
			</div>
			<div class="link">
				<a href="#">quizzes</a>
			</div>
			<div class="link">
				<a href="#">friends</a>
			</div>
			<div class="link">
				<a href="#">about</a>
			</div>
			<div class="link">
				<a href="#">contact</a>
			</div>
		</div>
		<div class="contentTitle">
			<h1>
				Welcome
				<%=request.getAttribute("username")%></h1>
		</div>
		<div class="contentText">
			<p>You may use this template on any site, anywhere, for free just
				please leave the link back to me in the footer. This template
				validates XHTML Strict 1.0, CSS Validates as well; enjoy :)</p>
			<p>&nbsp;</p>
		</div>
	</div>
</body>
</html>
