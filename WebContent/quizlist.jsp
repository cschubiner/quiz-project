<%@ include file="template/header.jsp"%>
<%@ page import="quiz.*, java.util.*"	 %>
<div class="contentTitle">
	<h1>
		Quizzes!
	</h1>
</div>
<div class="contentText">
	<%
		ArrayList<Quiz> quizlist = (ArrayList<Quiz>)(request.getAttribute("quizlist"));
		for (Quiz q: quizlist) {
			
			out.print(q.getName());
		}
	%>

</div>


<%@ include file="template/footer.jsp"%>
