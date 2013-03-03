<%@ include file="template/header.jsp"%>
<div class="contentTitle">
	<h1>
		<%if (request.getAttribute("welcomeMessage") != null)
			out.println(request.getAttribute("welcomeMessage"));
			else out.println("Welcome to Quiztopia!");
			
			%>
	</h1>
</div>
<div class="contentText">
	<%
		username = request.getSession().getAttribute("username");
		if (username != null){
			out.println(username + " is currently logged in.");
		}
		else { //user is not logged in
		
			out.println("Click login above to log in or to create a new account.");
		}	
	%>

</div>


<%@ include file="template/footer.jsp"%>
