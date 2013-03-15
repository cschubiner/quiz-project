package quiz;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBConnection;

/**
 * Servlet implementation class QuizResultsServlet
 */
@WebServlet("/QuizResultsServlet")
public class QuizResultsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizResultsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher("quizresults.jsp");
		DBConnection db = (DBConnection) getServletContext().getAttribute("database");
		Quiz q = ((Quiz)(request.getSession().getAttribute("quiz")));
		Quiz quiz = q;
		ArrayList<TQuiz> topScores = QuizUtils.getXHighestScoringtQuizzes(db,quiz.getID() , 5);
		request.setAttribute("topscores", topScores);
		ArrayList<TQuiz> recentScores = QuizUtils.getXRecenttQuizzes(db, quiz.getID(), 5);
		request.setAttribute("recentscores",recentScores);
		ArrayList<TQuiz> recentTopScores = QuizUtils.getXHighestScoringtQuizzesLastDay(db, quiz.getID(), 5);
		request.setAttribute("recenttopscores",recentTopScores);
		
		dispatch.forward(request, response);
	}

}
