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

		System.out.println("username: " + userName + "\nnewAccount: "+request.getParameter("newAccount")
				+ "\ncreatingNewAccount: "+ request.getParameter("creatingNewAccount"));
		if (request.getParameter("newAccount") != null){
			request.setAttribute("newAccount","true");
			request.setAttribute("welcomeMessage", "Enter desired username and password");
			return;
		}
		
		if (request.getParameter("creatingNewAccount") != null){
			if(actManager.createAccount(userName, password)){//if the account name is available
				RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
				dispatch.forward(request, response);
			}else{
				RequestDispatcher dispatch = request.getRequestDispatcher("alreadyexists.jsp");
				dispatch.forward(request, response);
			}
			return;
		}

		if(actManager.login(userName, password)){
			request.setAttribute("username",userName);
			request.setAttribute("welcomeMessage", "Welcome "+userName);
			RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
			dispatch.forward(request, response);
		}else{
			request.setAttribute("welcomeMessage", "Either your pw or username is incorrect. Plz try again");
			RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
			dispatch.forward(request, response);
		}
	}

}
