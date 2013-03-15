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
 * Servlet implementation class PracticeQuizServlet
 */
@WebServlet("/PracticeQuizServlet")
public class PracticeQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PracticeQuizServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DBConnection db = (DBConnection) getServletContext().getAttribute("database");
		Quiz quiz = QuizUtils.getQuizByID(db, Integer.parseInt(request.getParameter("id")));
		quiz.getAllQuestions(db);
		quiz.setStartTime();
		AchievementUtils.checkPracticeAchievement(db, (String) request.getSession().getAttribute("username"));
		if (quiz.getOrdering() == Quiz.ORDER_RANDOM_ORDER) {
			quiz.randomizeQuestions();
		}
		request.setAttribute("page", 0);
		request.setAttribute("practicemode", true);
		RequestDispatcher dispatch = request.getRequestDispatcher("quiz.jsp");
		request.getSession().setAttribute("quiz", quiz);
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher("QuizResultsServlet");
		DBConnection db = (DBConnection) getServletContext().getAttribute("database");
		Quiz q = (Quiz)(request.getSession().getAttribute("quiz"));
		Object np = request.getParameter("nextPage");
		int page = (np != null) ? Integer.parseInt(np.toString()) : -1;
		if (request.getParameter("graded") == null) {//repeat page
			request.setAttribute("page", page - 1);
			boolean g = q.evaluateAnswer(request, page - 1);
			request.setAttribute("grade", g);
			if (g) q.getQuestions().get(page - 1).times_answered_correctly++;
			dispatch = request.getRequestDispatcher("quiz.jsp");
		}
		else if (page < q.getQuestions().size()) {//move to next
			if (q.getQuestions().get(page  - 1).times_answered_correctly >= 3) {
				q.getQuestions().remove(page - 1);
				request.setAttribute("page", page - 1);
			}
			else {
				request.setAttribute("page", page);
			}
			dispatch = request.getRequestDispatcher("quiz.jsp");
		}
		else {//finish quiz
			if (q.getQuestions().get(page  - 1).times_answered_correctly >= 3) {
				q.getQuestions().remove(page - 1);
				request.setAttribute("page", page - 1);
			}
			request.setAttribute("page", 0);
			dispatch = request.getRequestDispatcher("quiz.jsp");
		}
		request.setAttribute("practicemode", true);
		dispatch.forward(request, response);



	}

}
