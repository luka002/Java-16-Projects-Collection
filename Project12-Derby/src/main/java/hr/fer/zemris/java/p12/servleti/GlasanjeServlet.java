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
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Servlet that gets PollOptions from the table
 * with poll id provided as parameter and writes
 * it out so user can vote.
 * 
 * @author Luka Grgić
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * Auto generated.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pollID;
		
		try {
			pollID = Integer.parseInt(req.getParameter("pollID"));
		} catch (NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wrong pollID provided.");
			return;
		}
		
		List<PollOption> options = DAOProvider.getDao().getPollOptions(pollID);
		Poll poll = DAOProvider.getDao().getPoll(pollID);
		
		req.setAttribute("poll", poll);
		req.setAttribute("options", options);
		
		if (poll == null || options.isEmpty()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wrong pollID provided.");
		} else {
			req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);	
		}
		
	}
		
}
