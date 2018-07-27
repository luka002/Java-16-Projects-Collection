package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Redirects to /servleti/index.html
 * 
 * @author Luka Grgić
 */
@WebServlet("/index.html")
public class IndexRedirect extends HttpServlet {

	/**
	 * Auto generated.
	 */
	private static final long serialVersionUID = 4638758360058659318L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
	}
	
}
