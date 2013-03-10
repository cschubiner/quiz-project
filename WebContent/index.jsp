<%@page import="admin.AdminUtils"%>
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
		ArrayList<Announcement> adminAnnouncements = AdminUtils
				.getXMostRecentAnnouncements(db, 4);
		out.println("<h3>Administrator Announcements</h2>");
		out.println("<ul>");
		for (int i = 0; i < adminAnnouncements.size(); i++)
			out.println("<h3>" + adminAnnouncements.get(i).title
					+ "</h3>\t" + adminAnnouncements.get(i).message
					+ "");
		out.println("</ul>");
		
		System.out.println("here1");
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
										.getAuthor()) + " - <b>Uploaded: "
								+ quizzes.get(i).getLastModified() + "</b></li>");
					out.println("</ul>");

					System.out.println("here2");

					
					if (userName != null) {
						quizzes = QuizUtils.getXMostRecentQuizzesTakenByUser(db,
								(String) userName, 3);
						out.println("<h3>Your Recently Taken Quizzes</h3>");
						if (quizzes.size() == 0)
							out.print("You have not taken any quizzes!");
						out.println("<ul>");
						for (int i = 0; i < quizzes.size(); i++) {
							if (quizzes.get(i) == null)continue; 
							out.println("<li>"
									+ QuizUtils.getQuizLinkString(quizzes.get(i)
											.getName(), quizzes.get(i).getID())
									+ " by "
									+ UserUtils.getUserLinkString(quizzes.get(i)
											.getAuthor()) + "</li>");
						}
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

					if (userName != null) {
						quizzes = QuizUtils.getXMostRecentlyCreatedQuizzesByUser(db, 3,
								(String) userName);
						out.println("<h3>Your Recently Created Quizzes</h3>");
						if (quizzes.size() == 0)
							out.print("You have not created any quizzes!");
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
