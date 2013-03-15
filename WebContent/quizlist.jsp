<%@page import="user.UserUtils"%>
<%@ include file="template/header.jsp"%>
<div class="contentTitle">
	<h1>
		Quizzes! &nbsp;
		<% session.setAttribute("createid", -1); %>
		<a href="EditQuizServlet"><input type="button" value="Create a Quiz!" /></a>
	</h1>
</div>
<div class="contentText">

	<table width="600">
		<th> Quiz Name </th>
		<th> Author </th>
		<th> Last Modified </th>
	<%
		ArrayList<Quiz> quizlist = (ArrayList<Quiz>)(request.getAttribute("quizlist"));
		for (Quiz q: quizlist) {

			out.println("<tr>");
			out.println("<td>"+QuizUtils.getQuizLinkString(q.getName(), q.getID())+"</td>");
			out.println("<td>"+ UserUtils.getUserLinkString(q.getAuthor()) + " </td>");
			out.println("<td>"+ QuizUtils.getHowLongAgo(q.getLastModified()) + " </td>");
			out.println("</tr>");
			
		}
	%>
	
	</table>
</div>


<%@ include file="template/footer.jsp"%>
