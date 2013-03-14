<%@ include file="template/header.jsp"%>
<link rel="stylesheet" type="text/css" href="css/buttons.css">
<div class="contentTitle">
	<% Message m = (Message) request.getAttribute("currentMessage"); %>
	<h1> From <%="  " +m.getSender() + ": " %></h1>
</div>
<div class="contentText">
	<%
		out.println(m.getMessage());
	%>
	<br><br>
	<%
		String messageType = m.getMessageType();
		if(messageType.equals(Message.NORMAL_MESSAGE)){
			out.println("<a href=\"sendmessage.jsp?target="
					+ m.getSender() + "\"><input type=\"button\" class = \"new-aqua\" value=\"Reply\" /></a>");
		}else if (messageType.equals(Message.FRIEND_REQUEST)){
			out.println("<a href=\"AcceptFriendServlet?&user1="
					+ m.getSender()
					+ "&user2="
					+ m.getRecipient()
					+ "\"><input type=\"button\" class = \"new-aqua\" value=\"Accept\" /></a>");
			out.println("<a href=\"UserProfileServlet?username="
					+ m.getRecipient()+"\"><input type=\"button\" class = \"new-aqua\" value=\"Ignore\" /></a>");
		}else{
			String msg = m.getMessage();
			int beg = msg.indexOf("#")+1;
			int end = msg.indexOf(" " , beg);
					
			out.println("Quiz Link: "
					+ QuizUtils.getQuizLinkString(msg.substring(end), Integer.parseInt(msg.substring(beg, end))));
			
		}
	%>
</div>


<%@ include file="template/footer.jsp"%>
