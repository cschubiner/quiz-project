<%@ include file="template/header.jsp"%>
<div class="contentTitle">
	<%
		String user = request.getParameter("username");
		if(user.equals("")){
			out.print("<h1>Sorry, please login to view your account, or click on a valid account.</h1>");
		}else{
			out.println("<h1>"+ user+"</h1>");	
		}
	%>


</div>
<%@ include file="template/footer.jsp"%>
