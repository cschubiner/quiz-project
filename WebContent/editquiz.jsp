<%@ include file="template/header.jsp"%>
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
		out.println("<form action=\"EditQuizServlet\" method=\"post\">");
		out.println("<br>Quiz Name: <input type=\"text\" name=\"qname\" value='" +tempquiz.getName() + "'><br>" );
		ArrayList<Question> questions = tempquiz.getQuestions();
		int c = 0;
		for (Question q : questions) {
			q.setOrder(c++);
			out.print(q.getCreateHTML());
			out.print("<br><div class='aligncenter' style='width:425px;height:0;border-top:2px dashed #ff7500;font-size:0;'><hr /></div><br>");
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
