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
 * Servlet implementation class QuizOverviewServlet
 */
@WebServlet("/QuizOverviewServlet")
public class QuizOverviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuizOverviewServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBConnection db = (DBConnection) getServletContext().getAttribute("database");
		Quiz quiz = QuizUtils.getQuizByID(db, Integer.parseInt(request.getParameter("id")));
		request.setAttribute("quiz", quiz);
		
		if(quiz==null){
			RequestDispatcher dispatch = request.getRequestDispatcher("nonexistent.jsp");
			dispatch.forward(request, response);
		}else{
			ArrayList<TQuiz> topScores = QuizUtils.getXHighestScoringtQuizzes(db,quiz.getID() , 5);
			request.setAttribute("topscores", topScores);
			ArrayList<TQuiz> recentScores = QuizUtils.getXRecenttQuizzes(db, quiz.getID(), 5);
			request.setAttribute("recentscores",recentScores);
			RequestDispatcher dispatch = request.getRequestDispatcher("quizoverview.jsp");
			dispatch.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
