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
		Object username = request.getAttribute("username");
		if (username != null){
			out.println("Thanks for logging in, " + username);
		}
		else { //user is not logged in
		
			out.println("<form action=\"LoginServlet\" method=\"post\">");
			out.println("<br>User Name: <input type=\"text\" name=\"account\"></br>");
			out.println("Password: <input type=\"password\" name=\"password\"></input>");
			out.println("<input type=\"submit\" value=\"Login\"></form>");
			
			out.println("<a href=\"createaccount.jsp\">Create New Account</a>");
		}	
	%>

</div>


<%@ include file="template/footer.jsp"%>
