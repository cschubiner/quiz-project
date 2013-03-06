<%@ include file="template/header.jsp"%>
<%@ page import="quiz.*, java.util.*, question.*"	 %>
<div class="contentTitle">
	<h1>
		Create a Quiz!
	</h1>
</div>
<div class="contentText">
	<%
		Object tq = (session.getAttribute("tempquiz"));
		Quiz tempquiz;
		if (tq == null) {//create a new quiz
			tempquiz = new Quiz(username.toString());
			session.setAttribute("tempquiz", tempquiz);
		}
		else {
			tempquiz = (Quiz)tq;
		}
		out.println("<form action=\"CreateQuizServlet\" method=\"post\">");
		out.println("<br>Quiz Name: <input type=\"text\" name=\"qname\" value='" +tempquiz.getName() + "'><br>" );
		ArrayList<Question> questions = tempquiz.getQuestions();
		for (Question q : questions) {
			out.print(q.getCreateHTML());
		}
		
		out.println("<select name='questiontype'> " +
        	"<option selected='selected' value='0'>Response Question</option>" +
        	"<option value='1'>Fill Question</option>" +
      		"</select>");
		out.println("<input type=\"submit\" name='action' value=\"Add Question\" ><br>");
		out.println("<input type=\"submit\" name='action' value=\"Save Quiz\">");
		out.println("</form>");
	%>
	
</div>


<%@ include file="template/footer.jsp"%>
