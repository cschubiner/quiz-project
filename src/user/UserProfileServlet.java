package user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBConnection;

/**
 * Servlet implementation class UserProfileServlet
 */
@WebServlet("/UserProfileServlet")
public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("username");
		
		String currentUser = (String) request.getSession().getAttribute("username");
		
		if(currentUser!=null){
			DBConnection db = (DBConnection) getServletContext().getAttribute("database");
			request.setAttribute("friends", UserUtils.findFriends(userName, db));
			request.setAttribute("myFriends", UserUtils.findFriends((String)currentUser, db));
			request.setAttribute("friendRequest", UserUtils.checkFriendRequest(currentUser, userName, db));
			
			RequestDispatcher dispatch = request.getRequestDispatcher("userprofile.jsp?username="+userName);
			dispatch.forward(request, response);
		}
		else{
			RequestDispatcher dispatch = request.getRequestDispatcher("permissiondenied.jsp");
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
