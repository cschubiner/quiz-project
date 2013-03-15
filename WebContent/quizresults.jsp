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
		out.print("<br>You took " +q.duration_seconds + " minutes" );
		for (int i = 0; i < q.getQuestions().size(); i++) {
			out.println("<br>" + q.getQuestions().get(i).getUserCompareHTML());
		}
	
		out.print("<br>Top Scores for this Quiz:" );
		ArrayList<TQuiz> topScores = (ArrayList<TQuiz>)request.getAttribute("topscores");
		for (int i = 0; i < topScores.size(); i++) {
			out.print("<br>" + topScores.get(i));
		}
	%>

</div>


<%@ include file="template/footer.jsp"%>
