<%@page import="user.UserUtils"%>
<%@ include file="template/header.jsp"%>
<%@ page import="quiz.*, java.util.*"%>

<div class="contentTitle">
	<h1>
		<%
			Quiz quiz = (Quiz) request.getAttribute("quiz");
			out.print(quiz.getName());
			boolean quizIsInappropriate = AdminUtils.QuizIsReported(db,
					quiz.getID());
		%>
	</h1>
	<%
		out.print("<FONT COLOR=\"000000\">Modifed at <i>"
				+ quiz.getLastModified() + "</i> by "
				+ UserUtils.getUserLinkString(quiz.getAuthor()) + "</FONT>");
		if (quizIsInappropriate)
			out.print("<FONT COLOR=\"000000\"> - </FONT><FONT COLOR=\"FF0000\">Quiz has been marked as inappropriate</FONT>");
	%>
</div>
<div class="contentText">
	<%
		if (userName != null) {
			out.print("&nbsp;<a href="
					+ "challenge.jsp?id="
					+ request.getParameter("id")
					+ "&name="
					+ quiz.getName()
					+ ""
					+ "><input type=\"button\" value=\"Challenge a Friend!\" /></a>");

			if (quiz.getAuthor().equals(userName)) {

				session.setAttribute("createid", quiz.getID());
				out.println("</br><a href='EditQuizServlet'><button name = 'edit' value='"
						+ quiz.getID() + "'>Edit Quiz</button></a> ");
			}
		}
		out.println("<form action=\"QuizServlet\" method=\"get\">");
		out.println("<input type=\"hidden\" value = \"" + quiz.getID()
				+ "\" name=\"id\">");
		out.println("<input type=\"submit\" value=\"Start the Quiz!\"></form>");

		out.println("<form action=\"PracticeQuizServlet\" method=\"get\">");
		out.println("<input type=\"hidden\" value = \"" + quiz.getID()
				+ "\" name=\"id\">");
		out.println("<input type=\"submit\" value=\"Take in Practice Mode!\"></form>");

		if (userName != null && quizIsInappropriate == false)
			out.println("<a href='ReportQuizServlet?reportQuizID="
					+ quiz.getID()
					+ "'><button name = 'reportQuizID'>Mark Quiz as Inappropriate</button></a>");

		//SUMMARY
		String description = quiz.getDescription();
		if (description != null && description.length() > 0) {
			out.print("<br><FONT COLOR=\"000000\">" + description
					+ "</FONT></br>");
		}
		out.print("" + request.getAttribute("summary").toString());
	%>
	<table width="100%" cellpadding="0" cellspacing="10" border="0">
		<tr>
			<td width="50%" valign="top">
				<%
					if (quiz.getNumTimesTaken() > 0) {
						//TOP SCORES
						out.println("<h3>All Time Top Scores</h3>");
						ArrayList<TQuiz> topScores = (ArrayList<TQuiz>) request
								.getAttribute("topscores");
						out.println("<ul>");
						for (int i = 0; i < topScores.size(); i++) {
							out.print("<li>" + topScores.get(i) + "</li>");
						}
						out.println("</ul>");

						//RECENT TOP SCORES
						out.println("<h3>Recent Top Scores</h3>");
						ArrayList<TQuiz> recentTopScores = (ArrayList<TQuiz>) request
								.getAttribute("recenttopscores");
						out.println("<ul>");
						for (int i = 0; i < recentTopScores.size(); i++) {
							out.print("<li>" + recentTopScores.get(i) + "</li>");
						}
						out.println("</ul>");
					
				%>
			</td>
			<td width="50%" valign="top">
				<%

						//RECENT SCORES
						out.println("<h3>Recent Scores</h3>");
						ArrayList<TQuiz> recentScores = (ArrayList<TQuiz>) request
								.getAttribute("recentscores");
						out.println("<ul>");
						for (int i = 0; i < topScores.size(); i++) {
							out.print("<li>" + recentScores.get(i) + "</li>");
						}
						out.println("</ul>");
					}
				%>
			</td>
		</tr>
	</table>

</div>


<%@ include file="template/footer.jsp"%>
