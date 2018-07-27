package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Servlet that updates voting results
 * based on pollID and optionID parameters provided.
 * 
 * @author Luka Grgić
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Auto generated.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pollID;
		int optionID;
		
		try {
			pollID = Integer.parseInt(req.getParameter("pollID"));
			optionID = Integer.parseInt(req.getParameter("optionID"));
		} catch (NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wrong pollID provided.");
			return;
		}
		
		if (DAOProvider.getDao().vote(pollID, optionID)) {
			resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollID);
		} else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wrong pollID provided.");
		}
		
	}

}
