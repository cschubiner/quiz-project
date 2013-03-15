package user;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quiz.Quiz;
import quiz.QuizUtils;
import quiz.TQuiz;
import database.DBConnection;

/**
 * Servlet implementation class ChallengeServlet
 */
@WebServlet("/ChallengeServlet")
public class ChallengeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChallengeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//do Nothing
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String currUser = (String) request.getSession().getAttribute("username");
		DBConnection db = (DBConnection) getServletContext().getAttribute("database");
		Quiz q = QuizUtils.getQuizByID(db, Integer.parseInt(id));
		String name = q.getName();
		HashSet<String> friends = UserUtils.findFriends(currUser, db);
		
		TQuiz highestScore = QuizUtils.getTopScore(db, Integer.parseInt(id), currUser);
		String score="N/A";
		if(highestScore!=null){
			score = highestScore.getScore() + " in " + highestScore.getDuration_seconds() + "s";
		}
		
		for(String f: friends){
			if(request.getParameter("challenge"+f)!=null){
				//if we wanted to prevent duplicates
				//	&& !UserUtils.checkChallenge(currUser, f, id, name, db)){
				UserUtils.challengeFriend(currUser, f, id, name, score,db);
			}
		}
		
		response.sendRedirect("QuizOverviewServlet?id="+id);
	}

}
