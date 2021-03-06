<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="quiz.*, java.util.*,database.*, user.*, question2.*, admin.*, achievement.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="template/style.css" />
<title>Quiztopia</title>
</head>

<body>
	<div id="page">
		<%
			DBConnection db = (DBConnection) request.getServletContext()
					.getAttribute("database");
		%>
		<div id="header">
			<h1>QuizTopia</h1>
			<h2>The Ultimate Quiz-Taking Destination</h2>
			<form action="SearchServlet" method="get" class="searchbar">
				<input type="text" name="search"> <input type="submit"
					value="Search">
			</form>
		</div>
		<div id="bar">
			<div class="link">
				<a href="index.jsp">Home</a>
			</div>
			<div class="link">
				<a href="QuizListServlet">Quizzes</a>
			</div>
			<%
				Object userName = session.getAttribute("username");
				Object username = userName;
				if (userName == null) {
					//userName = "";
					Cookie[] cookies = request.getCookies();
					if (cookies != null) {
						for (int i = 0; i < cookies.length; i++) {
							Cookie c = cookies[i];
							if (c == null)
								continue;
							if (c.getName().equals("loginCookie")) {
								userName = UserUtils.getUserNameGivenCookie(db,
										c.getValue());

								if (userName != null) {
									System.out.println("logged in user with cookie! username: "
													+ userName);
									session.setAttribute("username", userName);
								}
							}
						}
					}
				}

				ArrayList<Message> messages = UserUtils.getMessages(
						(String) userName, db);

				if (userName != null) {
					int messageCount = 0;
					if (messages != null) {
						for (Message message : messages) {
							if (message.isSeen() == false) {
					messageCount++;
							}
						}
					}
					out.println("<div class=\"link\">");
					out.println("<a href=\"UserProfileServlet?username=" + userName
							+ "\">My Profile"+(messageCount > 0 ? " <FONT COLOR=\"FF0000\">("+messageCount+")</FONT>" : "")+"</a>");

					out.println("</div>");
				}

				boolean isAdmin = false;
				if (userName == null) {
					out.println("<div class=\"link\">");
					out.println("<a href=\"login.jsp\">Login</a>");
					out.println("</div>");
				} else {

					isAdmin = UserUtils
							.isUserAnAdministrator((String) userName, db);
					if (isAdmin) {
						out.println("<div class=\"link\">");
						out.println("<a href=\"admin.jsp\">Admin Tools</a>");
						out.println("</div>");
					}

					out.println("<div class=\"link\">");
					out.println("<a href=\"LogoutServlet\">Logout</a>");
					out.println("</div>");
				}
			%>

		</div>