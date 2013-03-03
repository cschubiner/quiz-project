<%@ include file="template/header.jsp"%>
<%@ page import="quiz.*, java.util.*"	 %>
<div class="contentTitle">
	<h1>
		Quizzes! &nbsp;
		<a href="createquiz.jsp"><input type="button" value="Create a Quiz!" /></a>
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
			out.println("<td> <a href=\"QuizOverviewServlet?id="+q.getID()+"\">" + q.getName() + "</a></td>");
			out.println("<td> " + q.getAuthor() + " </td>");
			out.println("<td> " + q.getLastModified() + " </td>");
			out.println("</tr>");
			
		}
	%>
	
	</table>
</div>


<%@ include file="template/footer.jsp"%>
