package login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DatabaseUtils;

import user.UserUtils;

/**
 * Servlet implementation class CreateServlet
 */
@WebServlet("/CreateServlet")
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// can be ignored
		RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = this.getServletContext();
		AccountManager actManager = (AccountManager) context.getAttribute("accountManager");

		String userName = request.getParameter("account");
		String password = request.getParameter("password");

		request.setAttribute("username",userName);
		
		if (request.getParameter("newAccount") != null){
			request.setAttribute("newAccount","true");
			request.setAttribute("welcomeMessage", "Enter desired username and password");
			return;
		}
		
		if(userName.length()==0 || userName.length()>15){
			RequestDispatcher dispatch = request.getRequestDispatcher("createaccount.jsp");
			request.setAttribute("Incorrect length for User Name","invalid User Name, Please nonempty username with less than 16 characters");

			dispatch.forward(request, response);
		}
		else if(actManager.createAccount(userName, password)){//if the account name is available
			RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
			request.getSession().setAttribute("username", userName);
			dispatch.forward(request, response);
			UserUtils.sendMessage(AccountManager.ADMIN, userName, UserUtils.NORMAL_MESSAGE, "Welcome to QuizTopia!", DatabaseUtils.getDatabaseConnectionFromHttpServlet(this));
		}else{
			RequestDispatcher dispatch = request.getRequestDispatcher("createaccount.jsp");
			request.setAttribute("usernameAlreadyTaken","usernameAlreadyTaken");

			dispatch.forward(request, response);
		}
	}

}
