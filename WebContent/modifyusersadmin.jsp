<%@page import="user.UserUtils"%>
<%@ include file="template/header.jsp"%>
<div class="contentTitle">
	<h1>
		<%
			if (isAdmin) {
				out.print("Modify User Database");
			} else
				out.print("Permission denied: You are not an administrator.");
		%>
	</h1>
</div>
<div class="contentText">
	<%
		if (!isAdmin)
			out.print("Contact an administrator if you wish to be promoted to an administrator");

		if (isAdmin) {
			out.println("<h3>All Users</h3>");
			ArrayList<String> userNames = UserUtils
					.getAllUsernamesFromDatabase(db);
			out.println("<ul>");
			for (int i = 0; i < userNames.size(); i++) {
				out.println("<li>"
						+ UserUtils.getUserLinkString(userNames.get(i)));
				if (userName.equals(userNames.get(i)) == false) {
					out.println(" - <b><a href=\"AdminDatabaseModificationServlet?removeUserName="
							+ userNames.get(i) + "\">Remove User</a></b>");
				}
				if (UserUtils.isUserAnAdministrator(userNames.get(i), db) == false) {
					out.println(" - <b><a href=\"AdminDatabaseModificationServlet?promoteUserName="
							+ userNames.get(i) + "\">Make Admin</a></b>");
				}

				out.println("</li>");
			}
			out.println("</ul>");

		}
	%>
</div>


<%@ include file="template/footer.jsp"%>
