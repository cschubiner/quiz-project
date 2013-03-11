package login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBConnection;
import database.DatabaseUtils;

import user.UserUtils;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Cookie[] cookies = request.getCookies();                
	     for (int i = 0; i < cookies.length; i++) {
	          Cookie c = cookies[i];            
	          if (c.getName().equals("loginCookie")) {        
	              c.setMaxAge(0);
	              c.setValue(null);
	              System.out.println("deleted login cookie");
	              response.addCookie(c);    
	          } 
	     }
	     
		request.setAttribute("username", null);
		request.getSession().setAttribute("username", null);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
		dispatch.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
