<%@ include file="template/header.jsp"%>
<link rel="stylesheet" type="text/css" href="css/table.css">
<div class="contentTitle">
	<h1>
		Challenge Friends to Take Quiz <font color="FF0000">
		<%
			String id =request.getParameter("id");
			Quiz q = QuizUtils.getQuizByID(db, Integer.parseInt(id));
			request.setAttribute("name", q.getName());
			out.print(q.getName());
		%></font>
	</h1>
</div>
<div class="contentText">
	Select Friends to Challenge:
	<form action=<%="ChallengeServlet?id="+request.getParameter("id")+"&name="+request.getParameter("name") %> method="post">
		<table style="float: left;">
			<input type="submit" value="Challenge!">
			<%
				String currUser = (String) session.getAttribute("username");
				HashSet<String> friends = UserUtils.findFriends(currUser, db);

				for (String f : friends) {
					out.println("<tr>");
					out.println("<td><input type=\"checkbox\" name=\"challenge" + f
							+ "\" value=\"challenge\">" + "</td>");
					out.println("<td>" + f + "</td>");
					out.println("</tr>");
				}
			%>
			
		</table>
	</form>
</div>
<%@ include file="template/footer.jsp"%>
