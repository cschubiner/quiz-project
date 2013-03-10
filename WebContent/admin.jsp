<%@ include file="template/header.jsp"%>
<%@ page import="quiz.*, java.util.*"%>

<div class="contentTitle">
	<h1>
		<%
			if (isAdmin) {
				out.print("Administrator Tools");
			} else
				out.print("Permission denied: You are not an administrator.");
		%>

	</h1>
</div>
<div class="contentText">
	<%
		if (!isAdmin)
			out.print("Contact an administrator if you wish to be promoted to an administrator");
	%>

	<table width="100%" cellpadding="0" cellspacing="10" border="0">
		<tr>
			<td width="50%" valign="top">
				<%
					if (isAdmin) {
						out.println("<h3>What would you like to do?</h3>");
						out.println("<ul>");
						out.println("<li><a href=\"createannouncement.jsp\">Create Announcement</a></li>");
						out.println("<li>Modify Users</li>");
						out.println("<li><a href=\"modifyquizzeslistadmin.jsp\">Modify Quizzes</a></li>");
						out.println("</ul>");

					}
				%>
			</td>
			<td width="50%" valign="top">
				<%
					if (isAdmin) {
						out.println("<h3>Site Statistics</h3>");
						out.println("<ul>");
						out.println("<li>Number of users: "+UserUtils.getNumberOfUsers(db)+"</li>");
						out.println("<li>Number of quizzes created: "+QuizUtils.getNumberOfQuizzesCreated(db)+"</li>");
						out.println("<li>Number of quizzes taken: "+QuizUtils.getNumberOfQuizzesTaken(db)+"</li>");
						out.println("</ul>");

					}
				%>
			</td>
		</tr>
	</table>

</div>


<%@ include file="template/footer.jsp"%>
