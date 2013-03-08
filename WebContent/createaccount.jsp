<%@ include file="template/header.jsp" %>
<div class="contentTitle">
			<h1><%
			if (request.getAttribute("usernameAlreadyTaken")!=null){
				out.print(request.getAttribute("usernameAlreadyTaken"));
		}
			else out.print("Please enter your desired username and password");
		%></h1>
		</div>
		<div class="contentText">
		<%
		
				out.println("<form action=\"CreateServlet\" method=\"post\">");
				out.println("<br>User Name: <input type=\"text\" name=\"account\"></br>");
				out.println("Password: <input type=\"password\" name=\"password\"></input>");
				out.println("<input type=\"submit\" value=\"Create Account\"></form>");
		%>

		</div>
<%@ include file="template/footer.jsp" %>
