package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Writes out voting results from PollOptions table
 * with poll id as pollID provided. Output is provided
 * in table format, pie chart format and gives you the option 
 * to download xls format.
 * 
 * @author Luka Grgić
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Auto gerenated.
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
		
		List<PollOption> topVoted = DAOProvider.getDao().getTopVoted(pollID);
		List<PollOption> options = DAOProvider.getDao().getPollOptions(pollID);
		
		req.setAttribute("pollID", pollID);
		req.setAttribute("options", options);
		req.setAttribute("topVoted", topVoted);
		
		if (topVoted.isEmpty() || options.isEmpty()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wrong pollID provided.");
		} else {
			req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
		}
	}
	
}
