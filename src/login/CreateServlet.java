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
		
		if(actManager.createAccount(userName, password)){//if the account name is available
			RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
			request.getSession().setAttribute("username", userName);
			dispatch.forward(request, response);
		}else{
			RequestDispatcher dispatch = request.getRequestDispatcher("alreadyexists.jsp");
			dispatch.forward(request, response);
		}
	}

}
