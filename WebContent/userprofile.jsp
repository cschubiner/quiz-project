<%@ include file="template/header.jsp"%>
<%@ page import="user.*, java.util.*"%>
<div class="contentTitle">

	<h1><%=request.getParameter("username") %></h1>
	&nbsp; &nbsp; <hr />
</div>

<div class="contentText">
	Friends:</br>
	<%
		ArrayList<String> friends = (ArrayList<String>) request.getAttribute("friends");
		for(String f : friends){
			out.println(UserUtils.getUserLinkString(f));
		}
	%>

</div>
<%@ include file="template/footer.jsp"%>
