<%@ include file="template/header.jsp"%>
<%@ page import="quiz.*, java.util.*"	 %>

<div class="contentTitle">
	<h1>
		<%
		Quiz q = ((Quiz)(session.getAttribute("quiz")));
		out.print("You scored " + q.getScoreString() );
		Quiz quiz = q;
		%>
	</h1>
</div>
<div class="contentText">
	<%
		out.print("Time taken: " +QuizUtils.getDurationString(q.duration_seconds));
		for (int i = 0; i < q.getQuestions().size(); i++) {
			out.println(q.getQuestions().get(i).getUserCompareHTML());
		}
	%>
	<table width="100%" cellpadding="0" cellspacing="10" border="0">
		<tr>
			<td width="40%" valign="top">
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
			<td width="60%" valign="top">
				<%
					//YOUR RECENT SCORES
						ArrayList<TQuiz> yourRecentScores = QuizUtils
								.getXRecenttQuizzesTakenByUser(db, quiz.getID(), 4,
										(String) userName);
						if (yourRecentScores.size() > 0) {
							out.println("<h3>Your Recent Scores</h3>");
							out.println("<ul>");
							for (int i = 0; i < yourRecentScores.size(); i++) {
								out.print("<li>Score: " + yourRecentScores.get(i).getScore() + " - "+yourRecentScores.get(i).getDuration_seconds()+ " seconds - Taken "+QuizUtils.getHowLongAgo(yourRecentScores.get(i).getTimeTaken())+"</li>");
							}
							out.println("</ul>");
						}

						//RECENT SCORES
						out.println("<h3>All Recent Scores</h3>");
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
