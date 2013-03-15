package login;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.UserUtils;

import database.DBConnection;
import database.DatabaseUtils;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String LOGIN_COOKIE_NAME = "loginCookie";

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
		DBConnection db = (DBConnection) getServletContext().getAttribute("database");

		String userName = request.getParameter("account");
		String password = request.getParameter("password");

		//System.out.println("username: " + userName + "\nnewAccount: "+request.getParameter("newAccount")
			//	+ "\ncreatingNewAccount: "+ request.getParameter("creatingNewAccount"));

		if(actManager.login(userName, password)){
			request.setAttribute("username",userName);
			request.getSession().setAttribute("username", userName);
			request.setAttribute("welcomeMessage", "Welcome "+userName);
			
			if (request.getParameter("rememberCheckbox")!=null){
				//save the user's login cookie
				String tokenID = Cracker.generateHash(userName+Math.random()+DateFormat.getDateInstance().format(new Date()));
				UserUtils.SetLoginCookie(db, userName, tokenID);
				Cookie c = new Cookie(LOGIN_COOKIE_NAME, tokenID);
				c.setMaxAge(24 * 60 * 60 * 30 * 2); // two months
				response.addCookie(c);
			}
			
			
			RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
			dispatch.forward(request, response);
		}else{
			request.setAttribute("welcomeMessage", "Your username and/or password is incorrect. Please try again.");
			RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
			dispatch.forward(request, response);
		}
	}

}
