package quiz;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import question2.Question;
import question2.QuestionFactory;
import database.DBConnection;

/**
 * Servlet implementation class EditQuizServlet
 */
@WebServlet("/EditQuizServlet")
public class EditQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditQuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//create tempquiz based on the id
		DBConnection db = (DBConnection) request.getServletContext().getAttribute("database");
		Object o = request.getSession().getAttribute("createid");
		int id = Integer.parseInt(o.toString());
		Quiz tq;
		if (id != -1) {
			tq = new Quiz(db, id);
		}
		else {
			System.out.println("creating new quiz");
			tq = new Quiz(request.getSession().getAttribute("username").toString());
		}
		request.getSession().setAttribute("tempquiz", tq);
		RequestDispatcher dispatch = request.getRequestDispatcher("editquiz.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Quiz tq = (Quiz)(request.getSession().getAttribute("tempquiz"));
		DBConnection db = (DBConnection) request.getServletContext().getAttribute("database");
		String action = request.getParameter("action");
		String delete = request.getParameter("delete");
		tq.updateFromHTML(request);
		if (action != null && "Add Question".equals(action)) {
			int type = Integer.parseInt(request.getParameter("questiontype")); 
			Question q = QuestionFactory.CreateDefaultQuestion(QuizUtils.getNextQuestionID(db, type),tq.getID(),0, type);
			tq.addQuestion(q);
		}
		else if (delete != null) {
			tq.removeQuestion(Integer.parseInt(delete));
		}
		else if (action != null && "Save Quiz".equals(action)) {
			tq.storeToDatabase(db);
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("editquiz.jsp");
		dispatch.forward(request, response);
	}

}
