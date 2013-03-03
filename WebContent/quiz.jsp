<%@ include file="template/header.jsp"%>
<%@ page import="quiz.*, java.util.*"	 %>

<div class="contentTitle">
	<h1>
		<%
		Quiz quiz = (Quiz)request.getAttribute("quiz");
		out.print("You are taking quiz: "+quiz.getName());
			%>
	</h1>
</div>
<div class="contentText">
	<%
		
	%>

</div>


<%@ include file="template/footer.jsp"%>
