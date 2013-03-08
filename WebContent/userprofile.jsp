<%@ include file="template/header.jsp"%>
<%@ page import="user.*, java.util.*"%>
<link rel="stylesheet" type="text/css" href="css/buttons.css">
<div class="contentTitle">
	<header>
		<font size="20" color="blue"><%=request.getParameter("username")%></font>
		<%
			String pageUser = request.getParameter("username");
			String sessionUser = (String) session.getAttribute("username");
			int friendRequest = (Integer) request.getAttribute("friendRequest");
			HashSet<String> myFriends = (HashSet<String>) request
					.getAttribute("myFriends");
			if (!pageUser.equals(sessionUser) ){
				if(myFriends.contains(pageUser)) {
					out.println("<font class=\"rightside\" size=\"5\" color=\"black\">Already Friends</font>");
				}
				else if(friendRequest==1){
					out.println("<font class=\"rightside\" size=\"5\" color=\"black\">Request Send</font>");
				}
				else if(friendRequest==2){
					out.println("<a href=\"AcceptFriendServlet?&user1="+pageUser+"&user2="+sessionUser
							+"\" class=\"rightside\"><input type=\"button\" class = \"new-aqua\" value=\"Accept\" /></a>");
				}
				else{
					out.println("<a href=\"AddFriendServlet?&user1="+pageUser+"&user2="+sessionUser
							+"\" class=\"rightside\"><input type=\"button\" class = \"new-aqua\" value=\"Add as a Friend!\" /></a>");
				}
				out.println("&nbsp;&nbsp;");
				out.println("<a href=\"SendMessageServlet?&user1="+pageUser+"&user2="+sessionUser
						+"\" class=\"rightside\"><input type=\"button\" class = \"new-aqua\" value=\"Message\" /></a>");
			
			}
		%>

	</header>
	&nbsp; &nbsp;
	<hr />
</div>

<div class="contentText">
	</br> Friends:</br>
	<%
		HashSet<String> friends = (HashSet<String>) request
				.getAttribute("friends");
		for (String f : friends) {
			out.println(UserUtils.getUserLinkString(f));
		}
	%>

</div>
<%@ include file="template/footer.jsp"%>
