<%@ include file="template/header.jsp"%>
<%@ page import="user.*, java.util.*"%>
<div class="contentTitle">
	<header>
		<font size="20" color="blue"><%=request.getParameter("username")%></font>
		&nbsp;&nbsp;
		<%
			String pageUser = request.getParameter("username");
			HashSet<String> myFriends = (HashSet<String>) request
					.getAttribute("myFriends");
			if (!pageUser.equals(session.getAttribute("username"))
					&& !myFriends.contains(pageUser)) {
				out.println("<a href=\"createquiz.jsp\"><input type=\"button\" value=\"Add as a Friend!\" /></a>");
			}
		%>
	</header>
	&nbsp; &nbsp;
	<hr />
</div>

<div class="contentText">
	</br></br>
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
