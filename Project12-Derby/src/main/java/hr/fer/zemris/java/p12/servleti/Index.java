package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;

/**
 * Servlet that gets polls from the database and
 * lists them to the user to choose one for voting.
 * 
 * @author Luka Grgić
 */
@WebServlet("/servleti/index.html")
public class Index extends HttpServlet {

	/**
	 * Auto generated
	 */
	private static final long serialVersionUID = -8923432536446083905L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Poll> polls = DAOProvider.getDao().getPolls(); 
		req.setAttribute("polls", polls);
		
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}

}
