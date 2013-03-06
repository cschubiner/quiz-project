<%@page import="quiz.QuizUtils"%>
<%@ include file="template/header.jsp"%>
<div class="contentTitle">
	<h1>
		<%if (request.getAttribute("welcomeMessage") != null)
			out.println(request.getAttribute("welcomeMessage"));
			else out.println("Welcome to Quiztopia!");
			
			%>
	</h1>
</div>
<div class="contentText">
	<%
		username = request.getSession().getAttribute("username");
		if (username != null){
			out.println(username + " is currently logged in.");
		}
		else { //user is not logged in
			out.println("Click login above to log in or to create a new account.");
		}	
		DBConnection db = (DBConnection) request.getServletContext().getAttribute("database");

		ArrayList<Quiz> quizzes = QuizUtils.getXMostPopularQuizzes(db, 3);
		
		out.println("<h3>Most popular quizzes</h3>");
		out.println("<ul>");
		for (int i = 0 ; i < quizzes.size(); i++)
			out.println("<li>"+QuizUtils.getQuizLinkString(quizzes.get(i).getName(), quizzes.get(i).getID())+" by "+UserUtils.getUserLinkString(quizzes.get(i).getAuthor())
					+" - <b>Taken "+quizzes.get(i).getNumTimesTaken()+" times</b></li>");
		out.println("</ul>");
	%>

	<table width="100%" cellpadding="0" cellspacing="10" border="0">
		<tr>
			<td width="50%" valign="top">
				<p>Columns in text are a very handy tool. Unfortunately, most
					designers don't use them because they're tedious to implement.</p>
			</td>
			<td width="50%" valign="top">
				<p>However, if you take the time and effort of putting them into
					use, you'll find that they really add to the aesthetics and
					functionality of your work.</p>
			</td>
		</tr>
	</table>
</div>


<%@ include file="template/footer.jsp"%>
