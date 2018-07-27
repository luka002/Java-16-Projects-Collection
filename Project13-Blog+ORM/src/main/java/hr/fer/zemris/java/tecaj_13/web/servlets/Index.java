package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * When accessed redirects user to /servleti/main.
 * 
 * @author Luka Grgić
 */
@WebServlet("/index.jsp")
public class Index extends HttpServlet {

	/** Auto generated.*/
	private static final long serialVersionUID = 8170180513814741436L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}

}
