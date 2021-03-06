package quiz;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import achievement.AchievementUtils;
import database.DBConnection;

/**
 * Servlet implementation class QuizServlet
 */
@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuizServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = (String) request.getSession().getAttribute("username");
		if(username==null){
			RequestDispatcher dispatch = request.getRequestDispatcher("permissiondenied.jsp");
			dispatch.forward(request, response);
		}
		else{
			DBConnection db = (DBConnection) getServletContext().getAttribute("database");
			Quiz quiz = QuizUtils.getQuizByID(db, Integer.parseInt(request.getParameter("id")));
			quiz.getAllQuestions(db);
			quiz.setStartTime();
			
			if(request.getParameter("challenge").equals("yes")){
				AchievementUtils.checkChallengeAchievement(db, username);
			}
			
			if (quiz.getOrdering() == Quiz.ORDER_RANDOM_ORDER) {
				quiz.randomizeQuestions();
			}
			if (quiz.getPaging() == Quiz.PAGING_MULTI_PAGE) {
				request.setAttribute("page", 0);
			}

			RequestDispatcher dispatch = request.getRequestDispatcher("quiz.jsp");
			request.getSession().setAttribute("quiz", quiz);
			dispatch.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher("QuizResultsServlet");
		DBConnection db = (DBConnection) getServletContext().getAttribute("database");
		Quiz q = (Quiz)(request.getSession().getAttribute("quiz"));

		if (q.getPaging() == Quiz.PAGING_SINGLE_PAGE) {
			q.evaluateAllAnswers(request);
			q.recordTQuiz(db, request.getSession().getAttribute("username").toString());
			AchievementUtils.checkTakeQuizAchievement(db, request.getSession().getAttribute("username").toString());
			AchievementUtils.checkGreatestAchievement(db, request.getSession().getAttribute("username").toString(), q.getID());
		}
		else {
			int id = Integer.parseInt(request.getParameter("questionid"));
			Object np = request.getParameter("nextPage");
			int page = (np != null) ? Integer.parseInt(np.toString()) : -1;
			if (q.getGrading() == Quiz.GRADING_IMMEDIATE && request.getParameter("graded") == null) {//repeat page
				page = Integer.parseInt(np.toString());
				request.setAttribute("page", page - 1);
				request.setAttribute("grade", q.evaluateAnswer(request, page - 1));
				dispatch = request.getRequestDispatcher("quiz.jsp");
			}
			else if (page < q.getQuestions().size()) {//move to next
				request.setAttribute("page", page);
				if (q.getGrading() == Quiz.GRADING_ALL_AT_END) {
					q.evaluateAnswer(request, page - 1);
				}
				dispatch = request.getRequestDispatcher("quiz.jsp");
			}
			else {
				if (q.getGrading() == Quiz.GRADING_ALL_AT_END) {
					q.evaluateAnswer(request, page - 1);
				}
				q.recordTQuiz(db, request.getSession().getAttribute("username").toString());
				AchievementUtils.checkTakeQuizAchievement(db, request.getSession().getAttribute("username").toString());
				AchievementUtils.checkGreatestAchievement(db, request.getSession().getAttribute("username").toString(), q.getID());
			}

		}

		dispatch.forward(request, response);
	}

}
