package hr.fer.zemris.java.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that sets background color for every page
 * based on parameter provided. Available colors are:
 * cyan, red, green and white. White is also default color.
 * 
 * @author Luka Grgić
 */
@WebServlet("/setColor")
public class SetColor extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = req.getParameter("pickedBgColor");
		
		if (color != null) {
			
			switch (color) {
				case "cyan":
					req.getSession().setAttribute("bgColor", "cyan");
					break;
				case "red":
					req.getSession().setAttribute("bgColor", "red");
					break;
				case "green":
					req.getSession().setAttribute("bgColor", "green");
					break;
				default:
					req.getSession().setAttribute("bgColor", "white");
				}
		}
		
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
	
}
