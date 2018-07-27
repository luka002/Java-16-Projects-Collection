package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that logs out user.
 * 
 * @author Luka Grgić
 */
@WebServlet("/servleti/logout")
public class Logout extends HttpServlet {

	/** Auto generated */
	private static final long serialVersionUID = 5196637307694804231L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}

}
