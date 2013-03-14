<%@ include file="template/header.jsp"%>
<div class="contentTitle">
	<h1>Search Results</h1>
</div>
<div class="contentText">
	<table width="600">
		<th>Quizzes</th>
		<th>Users</th>
		<%
			ArrayList<String> quizzes = (ArrayList<String>) (request
					.getAttribute("quizzes"));
			ArrayList<String> users = (ArrayList<String>) (request
					.getAttribute("users"));
			int num = Math.max(quizzes.size(), users.size());
			for (int i = 0; i < num; i++) {
				out.println("<tr>");
				if (i < quizzes.size()) {
					out.println("<td>" + quizzes.get(i) + "</td>");
				} else {
					out.println("<td></td>");
				}
				if (i < users.size()) {
					out.println("<td>" + users.get(i) + "</td>");
				} else {
					out.println("<td></td>");
				}
				out.println("</tr>");
			}
		%>
	</table>

</div>
<%@ include file="template/footer.jsp"%>
