<%@page import="user.UserUtils"%>
<%@ include file="template/header.jsp"%>
<%@ page import="quiz.*, java.util.*"%>

<div class="contentTitle">
	<h1>
		<%
			Quiz quiz = (Quiz) request.getAttribute("quiz");
			out.print(quiz.getName());

			if (userName != null) {
				out.print("&nbsp;<a href="
						+ "challenge.jsp?id="
						+ request.getParameter("id")
						+ "&name="
						+ quiz.getName()
						+ ""
						+ "><input type=\"button\" value=\"Challenge a Friend!\" /></a>");

			}
		%>
	</h1>
</div>
<div class="contentText">
	<%
		out.print("Modifed at <i>" + quiz.getLastModified() + "</i> by "
				+ UserUtils.getUserLinkString(quiz.getAuthor()));
		boolean quizIsInappropriate = AdminUtils.QuizIsReported(db,
				quiz.getID());
		if (quizIsInappropriate)
			out.print(" - <FONT COLOR=\"FF0000\">Quiz has been marked as inappropriate</FONT>");
		out.println("<form action=\"QuizServlet\" method=\"get\">");
		out.println("<input type=\"hidden\" value = \"" + quiz.getID()
				+ "\" name=\"id\">");
		out.println("<input type=\"submit\" value=\"Start the Quiz!\"></form>");

		if (quiz.getAuthor().equals(userName)) {

			session.setAttribute("createid", quiz.getID());
			out.println("<a href='EditQuizServlet'><button name = 'edit' value='"
					+ quiz.getID() + "'>Edit</button></a> ");
		}

		if (userName != null && quizIsInappropriate == false)
			out.println("<a href='ReportQuizServlet?reportQuizID="
					+ quiz.getID()
					+ "'><button name = 'reportQuizID'>Mark Quiz as Inappropriate</button></a>");
		//SUMMARY
		out.print("<br>" + request.getAttribute("summary").toString());
		//TOP SCORES
		out.print("<br>Top Scores for this Quiz All Time:");
		ArrayList<TQuiz> topScores = (ArrayList<TQuiz>) request
				.getAttribute("topscores");
		for (int i = 0; i < topScores.size(); i++) {
			out.print("<br>" + topScores.get(i));
		}
		//RECENT TOP SCORES
		out.print("<br><br>Recent Top Scores for this Quiz:");
		ArrayList<TQuiz> recentTopScores = (ArrayList<TQuiz>) request
				.getAttribute("recenttopscores");
		for (int i = 0; i < recentTopScores.size(); i++) {
			out.print("<br>" + recentTopScores.get(i));
		}
		//RECENT SCORES
		out.print("<br><br>Recent Scores for this Quiz:");
		ArrayList<TQuiz> recentScores = (ArrayList<TQuiz>) request
				.getAttribute("recentscores");
		for (int i = 0; i < topScores.size(); i++) {
			out.print("<br>" + recentScores.get(i));
		}
	%>

</div>


<%@ include file="template/footer.jsp"%>
