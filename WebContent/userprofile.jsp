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
			int friendRequest = (Integer) request.getAttribute("friendRequest");
			HashSet<String> myFriends = (HashSet<String>) request
					.getAttribute("myFriends");
			if (!pageUser.equals(sessionUser)) {
				if (myFriends.contains(pageUser)) {
					out.println("<font class=\"rightside\" size=\"5\" color=\"black\">Already Friends</font>");
					out.println("&nbsp;&nbsp;");
					out.println("<a href=\"sendmessage.jsp?&target="+ pageUser
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
	<br />
	<div>
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
			%>
		</table>
		
		<table align="right" class="alternate">
			<%
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
			%>
		</table>
	</div>
	
	<h2 align="center">Messages: </h2>
</div>
<%@ include file="template/footer.jsp"%>
