<%@ include file="template/header.jsp"%>
<%@ page import="quiz.*, java.util.*"%>

<div class="contentTitle">
<h1>
<%
		Quiz quiz = ((Quiz)(session.getAttribute("quiz")));
		Object o = request.getAttribute("page");
		int pageNum = (o != null) ? Integer.parseInt(o.toString()) : -1;
		//quiz.getAllQuestions(db);
		out.print(quiz.getName() + "  ");
		
		if (pageNum != -1) 
			out.println("Question #" + (pageNum + 1) +" of " + quiz.getQuestions().size() +"<br>");
		
			%>
</h1>
</div>
<div class="contentText">
<%
	out.println("<form action=\"QuizServlet\" method=\"post\">");
	if (quiz.getPaging() == Quiz.PAGING_MULTI_PAGE) {
		//DISPLAY CURRENT QUESTION
		Question curr = quiz.getQuestions().get(pageNum);
		out.println(curr.getQuestionHTML());
		out.println("<br><button name='submit' type='submit'>Next</button> ");
		out.println("<input type='hidden' name='nextPage' value='" + (pageNum+ 1) +"'>");
		out.println("<input type='hidden' name='questionid' value='" + (curr.getID()) +"'>");
	}
	else {
		//DISPLAY ALL QUESTIONS
		for (int i = 0; i < quiz.getQuestions().size(); i++) {
			out.println(quiz.getQuestions().get(i).getQuestionHTML() + "<br>");
		}
		out.println("<button name='submit' type='submit'>Submit Answers</button> ");
	}
	
	out.println("</form>");
	%>
</div>


<%@ include file="template/footer.jsp"%>
