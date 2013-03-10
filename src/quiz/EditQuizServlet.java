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
		//create a quiz object based on the database
		DBConnection db = (DBConnection) request.getServletContext().getAttribute("database");
		Object qo = (request.getSession().getAttribute("createquizid"));
		int id = (qo == null) ? -1 : Integer.parseInt((String)qo);
		Quiz tq = null;
		if (id != -1) {
			tq = new Quiz(db, id);
		}
		else {
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
			Question q = QuestionFactory.CreateDefaultQuestion(tq.getNextQuestionID(),tq.getID(), tq.getQuestions().size(), Integer.parseInt(request.getParameter("questiontype")));
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
