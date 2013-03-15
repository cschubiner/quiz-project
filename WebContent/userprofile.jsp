<%@ include file="template/header.jsp"%>
<%@ page import="user.*, java.util.*"%>
<link rel="stylesheet" type="text/css" href="css/buttons.css">
<link rel="stylesheet" type="text/css" href="css/table.css">
<div class="contentTitle">
	<header>
		<font size="20" color="blue"><%=request.getParameter("username")%></font>
		<%
			String pageUser = request.getParameter("username");
			String sessionUser = (String) session.getAttribute("username");

			if (sessionUser == null) {
				response.sendRedirect("permissiondenied.jsp");
			}

			int friendRequest = (Integer) request.getAttribute("friendRequest");
			HashSet<String> myFriends = (HashSet<String>) request
					.getAttribute("myFriends");
			if (!pageUser.equals(sessionUser)) {
				if (myFriends.contains(pageUser)) {
					out.println("<font class=\"rightside\" size=\"5\" color=\"black\">Already Friends</font>");
					out.println("&nbsp;&nbsp;");
					out.println("<a href=\"sendmessage.jsp?&target="
							+ pageUser
							+ "\" class=\"rightside\"><input type=\"button\" class = \"new-aqua\" value=\"Message\" /></a>");
				} else if (friendRequest == 1) {
					out.println("<font class=\"rightside\" size=\"5\" color=\"black\">Request Sent</font>");
				} else if (friendRequest == 2) {
					out.println("<a href=\"AcceptFriendServlet?&user1="
							+ pageUser
							+ "&user2="
							+ sessionUser
							+ "\" class=\"rightside\"><input type=\"button\" class = \"new-aqua\" value=\"Accept\" /></a>");
				} else {
					out.println("<a href=\"AddFriendServlet?&user1="
							+ pageUser
							+ "&user2="
							+ sessionUser
							+ "\" class=\"rightside\"><input type=\"button\" class = \"new-aqua\" value=\"Add as a Friend!\" /></a>");
				}
			}
		%>

	</header>
	&nbsp; &nbsp;
	<hr />
</div>

<div class="contentText">
	<br>
	<%

		if (pageUser.equals(sessionUser) && messages.size() > 0) {
			out.println("<div style=\"text-align: center\"><font size=\"5\">Messages: </font></div>");
			out.println("<div>");
			out.println("<table class=\"message\">");
			out.println("<th> From </th>");
			out.println("<th> Message </th>");
			out.println("<th> Date </th>");
			out.println("<th> Type </th>");

			for (Message m : messages) {
				String seenEffect = " ";
				String highlight = " class=\"highlight\"";
				if (!m.isSeen()) {
					seenEffect = " color=\"red\" ";
				}

				out.println("<tr" + highlight + ">");
				out.println("<td><font " + seenEffect + ">"
						+ UserUtils.getUserLinkString(m.getSender())
						+ "</font></td>");
				out.println("<td><a href=\"ReadMessageServlet?sender="
						+ m.getSender() + "&date=" + m.getTimeSent()
						+ "\"><font " + seenEffect + ">" + m.getMessage()
						+ "</font></a></td>");
				out.println("<td><font " + seenEffect + ">"
						+ m.getTimeSent() + "</font></td>");
				out.println("<td><font " + seenEffect + ">"
						+ m.getMessageType() + "</font></td>");
				out.println("</tr>");
			}
			out.println("</table>");
			out.println("</div>");
		}
	%>
	<div> <br>
		<h2 class="tableheader">Friends:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Achievements:</h2>
		<table align="left" class="alternate">
			<%
				HashSet<String> friends = (HashSet<String>) request
						.getAttribute("friends");
				int counter = 0;
				if (friends.size() == 0) {
					out.println("<tr><td>I have no friends :(</td></tr>");
				} else {
					for (String f : friends) {
						if (counter % 2 == 1) {
							out.println("<tr><td>" + UserUtils.getUserLinkString(f)
									+ "</td></tr>");
						} else {
							out.println("<tr><td class=\"odd\">"
									+ UserUtils.getUserLinkString(f) + "</td></tr>");
						}
						counter++;
					}
				}
			%>
		</table>

		<table align="right" class="alternate">
			<%
				ArrayList<MAchievement> mAchievements = AchievementUtils
						.getAllAchievementsForUser(db, (String) pageUser);
				if (mAchievements.size() == 0) {
					out.println("<tr><td>I have no achievements :(</td></tr>");
				} else {
					for (MAchievement ma : mAchievements) {
						if (counter % 2 == 1) {
							out.println("<tr><td>" + "<b>" + ma.getName()
									+ "</b> - " + ma.getDescription()
									+ "</td></tr>");
						} else {
							out.println("<tr><td class=\"odd\">" + "<b>"
									+ ma.getName() + "</b> - "
									+ ma.getDescription() + "</td></tr>");
						}
						counter++;
					}
				}
			%>
		</table>
	</div>
</div>
<%@ include file="template/footer.jsp"%>
