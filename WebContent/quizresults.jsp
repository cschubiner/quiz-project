<%@ include file="template/header.jsp"%>
<%@ page import="quiz.*, java.util.*"	 %>

<div class="contentTitle">
	<h1>
		<%
		out.print("You scored " + ((Quiz)(session.getAttribute("quiz"))).getScoreString() );
		
		%>
	</h1>
</div>
<div class="contentText">
	<%
	
	%>

</div>


<%@ include file="template/footer.jsp"%>
