package admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quiz.QuizUtils;

import database.DBConnection;

/**
 * Servlet implementation class AdminDatabaseModificationServlet
 */
@WebServlet("/AdminDatabaseModificationServlet")
public class AdminDatabaseModificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminDatabaseModificationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher("admin.jsp");
		DBConnection db = (DBConnection) getServletContext().getAttribute("database");
		String quizIDToRemove = request.getParameter("removeQuizID");
		if (quizIDToRemove != null){
			QuizUtils.removeMQuizFromDatabase(db, Integer.parseInt(quizIDToRemove));
		}
		
		
		dispatch.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher("admin.jsp");
		DBConnection db = (DBConnection) getServletContext().getAttribute("database");
		String quizIDToRemove = request.getParameter("removeQuizID");
		if (quizIDToRemove != null){
			QuizUtils.removeMQuizFromDatabase(db, Integer.parseInt(quizIDToRemove));
		}
		
		
		dispatch.forward(request, response);
	}

}
