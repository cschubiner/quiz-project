package login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//can ignore since we are using post
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = this.getServletContext();
		AccountManager actManager = (AccountManager) context.getAttribute("accountManager");

		String userName = request.getParameter("account");
		String password = request.getParameter("password");

		if(actManager.login(userName, password)){
			request.setAttribute("username",userName);
			
			RequestDispatcher dispatch = request.getRequestDispatcher("welcome.jsp");
			dispatch.forward(request, response);
		}else{
			RequestDispatcher dispatch = request.getRequestDispatcher("tryagain.html");
			dispatch.forward(request, response);
		}
	}

}