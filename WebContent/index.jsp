<%@page import="achievement.AchievementUtils"%>
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
		
	%>

	<table width="100%" cellpadding="0" cellspacing="10" border="0">
		<tr>
			<td width="50%" valign="top">
				<%
					ArrayList<Announcement> adminAnnouncements = AdminUtils
							.getXMostRecentAnnouncements(db, 4);
					out.println("<h3>Administrator Announcements</h2>");
					out.println("<ul>");
					for (int i = 0; i < adminAnnouncements.size(); i++)
						out.println("<h3>" + adminAnnouncements.get(i).title
								+ "</h3>\t" + adminAnnouncements.get(i).message + "");
					out.println("</ul>");

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
								+ QuizUtils.getHowLongAgo(quizzes.get(i).getLastModified()) + "</b></li>");
					out.println("</ul>");

					if (userName != null) {
						quizzes = QuizUtils.getXMostRecentQuizzesTakenByUser(db,
								(String) userName, 3);
						out.println("<h3>Your Recently Taken Quizzes</h3>");
						if (quizzes.size() == 0)
							out.print("You have not taken any quizzes!");
						out.println("<ul>");
						for (int i = 0; i < quizzes.size(); i++) {
							if (quizzes.get(i) == null)
								continue;
							out.println("<li>"
									+ QuizUtils.getQuizLinkString(quizzes.get(i)
											.getName(), quizzes.get(i).getID())
									+ " by "
									+ UserUtils.getUserLinkString(quizzes.get(i)
											.getAuthor()) + "</li>");
						}
						out.println("</ul>");
					}

					//Display a list of friend's recent activities including quizzes taken or 
					//created and achievements earned. This summary should include links to the friend's 
					//user page and the quiz pages.
					HashSet<String> friends = UserUtils.findFriends((String) userName,
							db);
					if (friends != null && friends.size() > 0) {
						ArrayList<FriendActivityString> friendActions = new ArrayList<FriendActivityString>();
						for (String friend : friends) {
							ArrayList<MAchievement> achievements = AchievementUtils
									.getXRecentlyAchievedAchievementsForUser(db,
											friend, 4);
							for (MAchievement achievement : achievements)
								friendActions
										.add(new FriendActivityString(
												UserUtils.getUserLinkString(friend)
														+ " achieved: "
														+ achievement.getName(),
												achievement.dateIssued));

							ArrayList<Quiz> quizzesTaken = QuizUtils
									.getXMostRecentQuizzesTakenByUser(db, friend, 4);
							for (Quiz quiz : quizzesTaken)
								friendActions.add(new FriendActivityString(UserUtils
										.getUserLinkString(friend)
										+ " took the quiz: "
										+ QuizUtils.getQuizLinkString(quiz.getName(),
												quiz.getID()), quiz.dateIssued));

							ArrayList<Quiz> quizzesCreated = QuizUtils
									.getXMostRecentlyCreatedQuizzesByUser(db, 4, friend);
							for (Quiz quiz : quizzesCreated)
								friendActions.add(new FriendActivityString(UserUtils
										.getUserLinkString(friend)
										+ " created the quiz: "
										+ QuizUtils.getQuizLinkString(quiz.getName(),
												quiz.getID()), quiz.getLastModified()));
						}
						out.println("<h3>Recent Friend Activity</h3>");
						out.println("<ul>");
						Collections.sort(friendActions);

						for (int i = 0; i < Math.min(friendActions.size(), 6); i++)
							out.println("<li>"
									+ friendActions.get(i).getString()
									+ " - "
									+ QuizUtils.getHowLongAgo(friendActions.get(i)
											.getDateTime()) + "</li>");
						out.println("</ul>");

					}
				%>
			</td>
			<td width="50%" valign="top">
				<%
					int unreadMessageCount = 0, unreadChallengeCount = 0, unreadFriendRequest = 0;
					if (messages != null) {
						for (Message message : messages) {
							if (message.isSeen() == false) {
								if (message.getMessageType().equals(
										Message.FRIEND_REQUEST))
									unreadFriendRequest++;
								if (message.getMessageType().equals(
										Message.NORMAL_MESSAGE))
									unreadMessageCount++;
								if (message.getMessageType().equals(
										Message.QUIZ_CHALLENGE))
									unreadChallengeCount++;
							}
						}
					}

					if (userName != null) {
						out.println("<h3>Notifications</h3>");
						out.println("<ul>");
						if ((unreadMessageCount > 0 || unreadChallengeCount > 0 || unreadFriendRequest > 0) == false)
							out.println("<li>You have no new notifications</li>");
						if (unreadMessageCount > 0) {
							out.println("<li>You have <b>" + unreadMessageCount
									+ "</b> unread message"
									+ (unreadMessageCount == 1 ? "" : "s") + "</li>");
						}
						if (unreadFriendRequest > 0) {
							out.println("<li>You have <b>" + unreadFriendRequest
									+ "</b> unseen friend request"
									+ (unreadFriendRequest == 1 ? "" : "s") + "</li>");
						}
						if (unreadChallengeCount > 0) {
							out.println("<li>You have <b>" + unreadChallengeCount
									+ "</b> unseen quiz challenge"
									+ (unreadChallengeCount == 1 ? "" : "s") + "</li>");
						}
						out.println("</ul>");
					}

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

						ArrayList<MAchievement> mAchievements = AchievementUtils
								.getXRecentlyAchievedAchievementsForUser(db,
										(String) userName, 3);
						out.println("<h3>Your Recent Achievements</h3>");
						if (mAchievements.size() == 0)
							out.print("You have not gotten any achievements!");
						out.println("<ul>");
						for (int i = 0; i < mAchievements.size(); i++)
							out.println("<li>" + "<b>" + mAchievements.get(i).getName()
									+ "</b> - " + mAchievements.get(i).getDescription()
									+ "</li>");
						out.println("</ul>");
					}
				%>

			</td>
		</tr>
	</table>
</div>


<%@ include file="template/footer.jsp"%>
