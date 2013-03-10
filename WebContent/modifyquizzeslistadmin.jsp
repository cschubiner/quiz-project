<%@page import="user.UserUtils"%>
<%@ include file="template/header.jsp"%>
<div class="contentTitle">
	<h1><%
			if (isAdmin) {
				out.print("Modify Quiz Database");
			} else
				out.print("Permission denied: You are not an administrator.");
		%></h1>
</div>
<div class="contentText">
	<%
	if (!isAdmin)
		out.print("Contact an administrator if you wish to be promoted to an administrator");

	if (isAdmin){
		out.println("<h3>All Quizzes</h3>");
		ArrayList<Quiz> quizzes = QuizUtils.getAllQuizzes(db);
		out.println("<ul>");
		for (int i = 0; i < quizzes.size(); i++)
			out.println("<li>"
					+ QuizUtils.getQuizLinkString(quizzes.get(i).getName(),
							quizzes.get(i).getID())
					+ " by "
					+ UserUtils.getUserLinkString(quizzes.get(i)
							.getAuthor()) + " - <b><a href=\"AdminDatabaseModificationServlet?removeQuizID="+quizzes.get(i).getID()+"\">Remove</a></b>"+
					" - <b><a href=\"AdminDatabaseModificationServlet?clearQuizIDHistory="+quizzes.get(i).getID()+"\">Clear History</a></b></li>");
		out.println("</ul>");

	}
	%>
</div>


<%@ include file="template/footer.jsp"%>
