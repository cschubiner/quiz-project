<%@page import="user.UserUtils"%>
<%@ include file="template/header.jsp"%>
<%@ page import="quiz.*, java.util.*"%>

<div class="contentTitle">
	<h1>
		To
		<%=request.getParameter("target")%>:
	</h1>
</div>
<div class="contentText">
		<form action="SendMessageServlet?target=<%=request.getParameter("target")%>" method="post">
		<textarea name="message" placeholder="Enter your message here!" rows="15" cols="50"></textarea>
		<br><input type="submit" value="Send Message"></form>
</div>


<%@ include file="template/footer.jsp"%>
