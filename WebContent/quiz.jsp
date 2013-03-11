<%@ include file="template/header.jsp"%>
<%@ page import="quiz.*, java.util.*"	 %>

<div class="contentTitle">
	<h1>
		<%
		Quiz quiz = ((Quiz)(session.getAttribute("quiz")));
		quiz.getAllQuestions(db);
		out.print("You are taking quiz: "+quiz.getName() +" #" + quiz.getQuestions().size() +"<br>");
		
			%>
	</h1>
</div>
<div class="contentText">
	<%
	out.println("<form action=\"QuizServlet\" method=\"post\">");
	for (int i = 0; i < quiz.getQuestions().size(); i++) {
		out.println(quiz.getQuestions().get(i).getQuestionHTML() + "<br>");
	}
	out.println("<button name='submit' type='submit'>Submit Answers</button> ");
	out.println("</form>");
	%>

</div>


<%@ include file="template/footer.jsp"%>
