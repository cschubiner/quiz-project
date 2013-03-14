<%@ include file="template/header.jsp"%>
<div class="contentTitle">
	<h1>
		Challenge Friends to Take Quiz <font color="FF0000"><%=request.getParameter("name")%></font>
	</h1>
</div>
<div class="contentText">
	Select Friends to Challenge: 
	<form action="ChallengeServlet" method="post">
		<table>
			<%
				String currUser = (String) session.getAttribute("username");
				HashSet<String> friends = UserUtils.findFriends(currUser, db);

				for (String f : friends) {
					out.println("<tr>");
					out.println("<td><input type=\"checkbox\" name=\"challenge"+f+ "\" value=\"challenge\">"
							+ "</td>");
					out.println("<td>" + f + "</td>");
					out.println("</tr>");
				}
			%>
		</table>
		<p><input type="submit" value="Challenge!"></p>
	</form>
</div>
<%@ include file="template/footer.jsp"%>
