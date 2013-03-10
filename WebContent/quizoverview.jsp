<%@page import="user.UserUtils"%>
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
				"</i> by "+UserUtils.getUserLinkString(quiz.getAuthor()));
	
	out.println("<form action=\"QuizServlet\" method=\"get\">");
	out.println("<input type=\"hidden\" value = \""+quiz.getID()+"\" name=\"id\">");
	out.println("<input type=\"submit\" value=\"Start the quiz!\"></form>");
	
	if (quiz.getAuthor().equals(userName)){
		
		session.setAttribute("createid", quiz.getID());
		System.out.println("id:" +session.getAttribute("createid"));
		out.println("<a href='EditQuizServlet'><button name = 'edit' value='" + quiz.getID() + "'>Edit</button></a> ");
	}
	%>

</div>


<%@ include file="template/footer.jsp"%>
