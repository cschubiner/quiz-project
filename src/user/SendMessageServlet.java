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
 * Servlet implementation class SendMessageServlet
 */
@WebServlet("/SendMessageServlet")
public class SendMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//do nothing
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String target = request.getParameter("target");
		String currUser = (String) request.getSession().getAttribute("username");

		if(currUser == null){
			RequestDispatcher dispatch = request.getRequestDispatcher("permissiondenied.jsp");
			dispatch.forward(request, response);
		}else{
			DBConnection db= (DBConnection) getServletContext().getAttribute("database");
			String message = request.getParameter("message");
			
			UserUtils.sendMessage(currUser, target, UserUtils.NORMAL_MESSAGE, message, db);
			response.sendRedirect("UserProfileServlet?username="+target);
		}
//		RequestDispatcher dispatch = request.getRequestDispatcher("permissiondenied.jsp");
//		dispatch.forward(request, response);
	}

}
