package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

/**
 * Servlet that displays main page. If the user is not
 * logged he can log in or register. There are also
 * displayed names of users that have posted blogs.
 * 
 * @author Kim Jong Un
 */
@WebServlet("/servleti/main")
public class MainPage extends HttpServlet {

	/** Auto generated.*/
	private static final long serialVersionUID = -2388749744575411398L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		proccess(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		proccess(req, resp);
	}
	
	/**
	 * Sets users nicks as request attribute and renders main page.
	 * 
	 * @param req http request
	 * @param resp http response
	 * @throws ServletException if the request for the POST could not be handled
	 * @throws IOException if an input or output error is detected when the servlet handles the request
	 */
	private void proccess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<String> users = DAOProvider.getDAO().getUsersNicks();
		
		if (!users.isEmpty()) {
			req.setAttribute("users", users);
		}
		
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}
	
}
