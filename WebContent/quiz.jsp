<%@ include file="template/header.jsp"%>
<%@ page import="quiz.*,java.util.*"%>

<div class="contentTitle">

<h1>
<%
	Quiz quiz = ((Quiz) (session.getAttribute("quiz")));
	boolean practice = (request.getAttribute("practicemode") != null);
	Object o = request.getAttribute("page");
	int pageNum = (o != null) ? Integer.parseInt(o.toString()) : -1;
	if (practice)
		out.print("Practicing ");
	out.print(quiz.getName() + " ");
	
	if (pageNum != -1)
		out.println("Question #" + (pageNum + 1) + " of "
				+ quiz.getQuestions().size() + "<br>");
%>
</h1>

</div>
<div class="contentText">
<font size="4" color="red"><div id="time"></div></font>
<%
	String a = practice ? "PracticeQuizServlet" : "QuizServlet";
	if (practice && quiz.getQuestions().size() == 0) {
		out.println("You have mastered this quiz!");
	}
	out.println("<form name='quiz' action=\"" + a + "\" method=\"post\">");
	if (practice || quiz.getPaging() == Quiz.PAGING_MULTI_PAGE) {
		//DISPLAY CURRENT QUESTION
		if (quiz.getQuestions().size() > 0) {
			Question curr = quiz.getQuestions().get(pageNum);
			if (request.getAttribute("grade") != null) {//IMMEDIATE GRADING
				out.println(curr.getPromptHTML());
				String g = "Correct!";
				if (request.getAttribute("grade").toString()
						.equals("false")) {
					g = "Incorrect";
				}
				out.println(g);
				out.println("<br><button name='submit' type='submit'>Next</button> ");
				out.println("<input type='hidden' name='graded' value='graded'>");

			} else {//NORMAL QUESTION
				if (curr.isTimed ) {
					out.print("<input type='hidden' name='timelimit' value='" + curr.timelimit_seconds +"'>" );
				}
				
				if (practice)
					out.println("You have answered this question correctly "
							+ curr.times_answered_correctly
							+ " times</br>");
				out.println(curr.getQuestionHTML());
				out.println("<br><button name='submit' type='submit'>Next</button> ");

			}
			out.println("<input type='hidden' name='nextPage' value='"
					+ (pageNum + 1) + "'>");
			out.println("<input type='hidden' name='questionid' value='"
					+ (curr.getID()) + "'>");
		}
	} else {
		//DISPLAY ALL QUESTIONS
		for (int i = 0; i < quiz.getQuestions().size(); i++) {
			out.println((i + 1) + ". " + quiz.getQuestions().get(i).getQuestionHTML()
					+ "<br>");
		}
		out.println("<button name='submit' type='submit'>Submit Answers</button> ");
	}
	out.println("</form>");
	if (practice) {
		out.println("<form action='index.jsp'><button type='submit'>Quit Practice</button></form>");
	}
%>
</div>
<script type="text/javascript"> 
 
 	function doSubmit() {

		 document.quiz.submit.click();
 	}
 	function tick() {
 		time--;
 		if (time <= 0) {
 			doSubmit();
 		}
 		document.getElementById("time").innerHTML="Time left:" + time; 
 	}
 	var time = document.quiz.elements['timelimit'].value;
 	if (time != -1) {
 		setInterval(tick, 1000);
 		document.getElementById("time").innerHTML="Time left:" + time;
 	}
 
 </script>

<%@ include file="template/footer.jsp"%>
