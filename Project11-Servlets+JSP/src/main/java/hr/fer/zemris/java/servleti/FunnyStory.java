package hr.fer.zemris.java.servleti;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that randomly chooses one color
 * and sets page text to be that color.
 * 
 * @author Luka Grgić
 */
@WebServlet("/funnyStory")
public class FunnyStory extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Random random = new Random();
		int number = random.nextInt(5);
		String color = null;
		
		switch (number) {
			case 0:
				color = "black";
				break;
			case 1:
				color = "blueviolet";
				break;
			case 2:
				color = "darkorange";
				break;
			case 3:
				color = "gold";
				break;
			case 4:
				color = "maroon";
				break;
		}
		
		req.setAttribute("textColor", color);
		req.getRequestDispatcher("/WEB-INF/pages/stories/funny.jsp").forward(req, resp);
	}
	
}
