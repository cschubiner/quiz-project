<%@page import="quiz.QuizUtils"%>
<%@ include file="template/header.jsp"%>
<div class="contentTitle">
	<h1>
		<%
			if (request.getAttribute("welcomeMessage") != null)
				out.println(request.getAttribute("welcomeMessage"));
			else
				out.println("Welcome to Quiztopia!");
		%>
	</h1>
</div>
<div class="contentText">
	<%
		username = request.getSession().getAttribute("username");
		if (username != null) {
			out.println(username + " is currently logged in.");
		} else { //user is not logged in
			out.println("Click login above to log in or to create a new account.");
		}
		DBConnection db = (DBConnection) request.getServletContext()
				.getAttribute("database");
	%>

	<table width="100%" cellpadding="0" cellspacing="10" border="0">
		<tr>
			<td width="50%" valign="top">
				<%
					ArrayList<Quiz> quizzes = QuizUtils.getXMostPopularQuizzes(db, 3);
					out.println("<h3>Recently Added Quizzes</h3>");
					out.println("<ul>");
					for (int i = 0; i < quizzes.size(); i++)
						out.println("<li>"
								+ QuizUtils.getQuizLinkString(quizzes.get(i).getName(),
										quizzes.get(i).getID())
								+ " by "
								+ UserUtils.getUserLinkString(quizzes.get(i)
										.getAuthor()) + " - <b>Uploaded at: "
								+ quizzes.get(i).getLastModified() + "</b></li>");
					out.println("</ul>");

					if (username != null) {
						quizzes = QuizUtils.getXMostRecentQuizzesTakenByUser(db,
								(String) username, 3);
						out.println("<h3>Your Recently Taken Quizzes</h3>");
						out.println("<ul>");
						for (int i = 0; i < quizzes.size(); i++)
							out.println("<li>"
									+ QuizUtils.getQuizLinkString(quizzes.get(i)
											.getName(), quizzes.get(i).getID())
									+ " by "
									+ UserUtils.getUserLinkString(quizzes.get(i)
											.getAuthor()) + "</li>");
						out.println("</ul>");
					}
				%>
			</td>
			<td width="50%" valign="top">
				<%
					quizzes = QuizUtils.getXMostPopularQuizzes(db, 3);
					out.println("<h3>Most Popular Quizzes</h3>");
					out.println("<ul>");
					for (int i = 0; i < quizzes.size(); i++)
						out.println("<li>"
								+ QuizUtils.getQuizLinkString(quizzes.get(i).getName(),
										quizzes.get(i).getID())
								+ " by "
								+ UserUtils.getUserLinkString(quizzes.get(i)
										.getAuthor()) + " - <b>Taken "
								+ quizzes.get(i).getNumTimesTaken() + " times</b></li>");
					out.println("</ul>");

					if (username != null) {
						quizzes = QuizUtils.getXMostRecentlyCreatedQuizzesByUser(db, 3,
								(String) username);
						out.println("<h3>Your Recently Created Quizzes</h3>");
						out.println("<ul>");
						for (int i = 0; i < quizzes.size(); i++)
							out.println("<li>"
									+ QuizUtils.getQuizLinkString(quizzes.get(i)
											.getName(), quizzes.get(i).getID())
									+ " by "
									+ UserUtils.getUserLinkString(quizzes.get(i)
											.getAuthor()) + " - <b>Taken "
									+ quizzes.get(i).getNumTimesTaken()
									+ " times</b></li>");
						out.println("</ul>");
					}
				%>

			</td>
		</tr>
	</table>
</div>


<%@ include file="template/footer.jsp"%>
