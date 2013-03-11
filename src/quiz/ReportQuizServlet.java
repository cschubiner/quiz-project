package quiz;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.AdminUtils;
import admin.Announcement;
import database.DBConnection;

/**
 * Servlet implementation class ReportQuizServlet
 */
@WebServlet("/ReportQuizServlet")
public class ReportQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReportQuizServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch; 
		DBConnection db = (DBConnection) getServletContext().getAttribute("database");
		String reportQuizID = request.getParameter("reportQuizID");
		if (reportQuizID != null){
			AdminUtils.MarkQuizAsInappropriate(db, Integer.parseInt(reportQuizID));
			dispatch = request.getRequestDispatcher("QuizOverviewServlet?id="+Integer.parseInt(reportQuizID));
		}
		else dispatch = request.getRequestDispatcher("QuizListServlet");
		dispatch.forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
