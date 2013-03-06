package quiz;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import question.Question;
import question.QuestionFactory;
import question.ResponseQuestion;
import database.DBConnection;

/**
 * Servlet implementation class CreateQuizServlet
 */
@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuizServlet() {
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
		
		Quiz tq = (Quiz)(request.getSession().getAttribute("tempquiz"));
		DBConnection db = (DBConnection) request.getServletContext().getAttribute("database");
		String action = request.getParameter("action");
		String delete = request.getParameter("delete");
		tq.updateFromHTML(request);
		if (action != null && "Add Question".equals(action)) {
			Question q = QuestionFactory.CreateQuestion(db, tq.getNextQuestionID(),tq.getQuestions().size(), Integer.parseInt(request.getParameter("questiontype")));
			tq.addQuestion(q);
		}
		else if (delete != null) {
			tq.removeQuestion(Integer.parseInt(delete));
		}
		else if (action != null && "Save Quiz".equals(action)) {
			//save stuff
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("createquiz.jsp");
		dispatch.forward(request, response);
	}

}
