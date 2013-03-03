<%@ include file="template/header.jsp"%>
<div class="contentTitle">
	<%
		String userName = request.getParameter("username");
		if(userName.equals("null")){
			out.print("<h1>Sorry, please login to view your account, or click on a valid account.</h1>");
		}else{
			out.println("<h1>"+ userName+"</h1>");	
		}
	%>


</div>
<%@ include file="template/footer.jsp"%>
