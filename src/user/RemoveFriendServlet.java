package user;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBConnection;

/**
 * Servlet implementation class RemoveFriendServlet
 */
@WebServlet("/RemoveFriendServlet")
public class RemoveFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveFriendServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//not used
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String currUser = (String) request.getSession().getAttribute("username");
		DBConnection db = (DBConnection) getServletContext().getAttribute("database");
		HashSet<String> friends = UserUtils.findFriends(currUser, db);
		
		for(String f: friends){
			if(request.getParameter("remove"+f)!=null){
				//if we wanted to prevent duplicates
				//	&& !UserUtils.checkChallenge(currUser, f, id, name, db)){
				UserUtils.removeFriend(currUser, f, db);
			}
		}
		response.sendRedirect("UserProfileServlet?username=" + currUser);
	}

}
