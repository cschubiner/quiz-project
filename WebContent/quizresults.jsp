<%@ include file="template/header.jsp"%>
<%@ page import="quiz.*, java.util.*"	 %>

<div class="contentTitle">
	<h1>
		<%
		Quiz q = ((Quiz)(session.getAttribute("quiz")));
		out.print("You scored " + q.getScoreString() );
		
		%>
	</h1>
</div>
<div class="contentText">
	<%
		out.print("Time taken: " +QuizUtils.getDurationString(q.duration_seconds));
		for (int i = 0; i < q.getQuestions().size(); i++) {
			out.println(q.getQuestions().get(i).getUserCompareHTML());
		}
	
		out.print("<br>");
		out.println("<h3>All Time Top Scores</h3>");
		ArrayList<TQuiz> topScores = (ArrayList<TQuiz>)request.getAttribute("topscores");
		out.println("<ul>");
		for (int i = 0; i < topScores.size(); i++) {
			out.print("<li>" + topScores.get(i)+"</li>");
		}
		out.println("</ul>");

	%>

</div>


<%@ include file="template/footer.jsp"%>
