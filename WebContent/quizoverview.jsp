<%@ include file="template/header.jsp"%>
<%@ page import="quiz.*, java.util.*"	 %>

<div class="contentTitle">
	<h1>
		<%
		Quiz quiz = (Quiz)request.getAttribute("quiz");
		out.print(quiz.getName());
			%>
	</h1>
</div>
<div class="contentText">
	<%
		out.print("Modifed at <i>"+quiz.getLastModified() + 
				"</i> by <a href=\"UserProfileServlet?username="
		+quiz.getAuthor()+"\">"+quiz.getAuthor()+"</a>");
	
	
	out.println("<form action=\"QuizServlet\" method=\"get\">");
	out.println("<input type=\"hidden\" value = \""+quiz.getID()+"\" name=\"id\">");
	out.println("<input type=\"submit\" value=\"Start the quiz!\"></form>");
	%>

</div>


<%@ include file="template/footer.jsp"%>
