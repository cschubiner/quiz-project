<%@ include file="template/header.jsp"%>
<%@ page import="user.*, java.util.*"%>
<link rel="stylesheet" type="text/css" href="css/buttons.css">
<div class="contentTitle">
	<header>
		<font size="20" color="blue"><%=request.getParameter("username")%></font>
		&nbsp;&nbsp;
		<%
			String pageUser = request.getParameter("username");
			String sessionUser = (String) session.getAttribute("username");
			HashSet<String> myFriends = (HashSet<String>) request
					.getAttribute("myFriends");
			if (!pageUser.equals(sessionUser) && !myFriends.contains(pageUser)) {
				out.println("<a href=\"AddFriendServlet?&user1="+pageUser+"&user2="+sessionUser
						+"\"><input type=\"button\" class = \"new-aqua\" value=\"Add as a Friend!\" /></a>");
			}
		%>
	</header>
	&nbsp; &nbsp;
	<hr />
</div>

<div class="contentText">
	</br>
	Friends:</br>
	<%
		HashSet<String> friends = (HashSet<String>) request
				.getAttribute("friends");
		for (String f : friends) {
			out.println(UserUtils.getUserLinkString(f));
		}
	%>

</div>
<%@ include file="template/footer.jsp"%>
