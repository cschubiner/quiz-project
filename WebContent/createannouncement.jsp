<%@page import="user.UserUtils"%>
<%@ include file="template/header.jsp"%>
<div class="contentTitle">
	<h1><%
			if (isAdmin) {
				out.print("Create Announcement");
			} else
				out.print("Permission denied: You are not an administrator.");
		%></h1>
</div>
<div class="contentText">
	<%
	if (!isAdmin)
		out.print("Contact an administrator if you wish to be promoted to an administrator");

	if (isAdmin){
		out.println("<form action=\"AnnouncementServlet\" method=\"post\">");
		out.println("Title: <input type=\"text\" maxLength=64 placeholder = \"Enter title here\" name=\"announcementTitle\"></br>");
		out.println("<textarea NAME=\"announcement\" maxLength=256 placeholder=\"Enter your announcement here!\" rows=\"10\" cols=\"30\"></textarea>");
		out.println("<br><input type=\"submit\" value=\"Post Announcement\"></form>");
	}
	%>
</div>


<%@ include file="template/footer.jsp"%>
