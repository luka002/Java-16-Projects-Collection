package hr.fer.zemris.java.servleti;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.servleti.util.FileUtil;

/**
 * Writes out voting results in table format,
 * pie chart format and gives you the option 
 * to download xls format.
 * 
 * @author Luka Grgić
 *
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<ResultData> resultData = FileUtil.getResults(req);
		List<ResultData> topBands = resultData.stream()
												.filter(s -> {
													return Integer.parseInt(s.voteResult) ==
															Integer.parseInt(resultData.get(0).voteResult);
												})
												.collect(Collectors.toList());
		
		req.setAttribute("results", resultData);
		req.setAttribute("topBands", topBands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	/**
	 * Class that stores band name, number of votes
	 * and link to song.
	 * 
	 * @author Luka Grgić
	 *
	 */
	public static class ResultData {
		/** Band name */
		public String name;
		/** Number of votes */
		public String voteResult;
		/** Link to song from band */
		public String link;
		
		/**
		 * Constructor.
		 * 
		 * @param name name
		 * @param voteResult number of votes
		 * @param link link to song from band
		 */
		public ResultData(String name, String voteResult, String link) {
			this.name = name;
			this.voteResult = voteResult;
			this.link = link;
		}

		/**
		 * Get name.
		 * 
		 * @return name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Get number of votes.
		 * 
		 * @return number of votes
		 */
		public String getVoteResult() {
			return voteResult;
		}

		/**
		 * Get link to song from band.
		 * 
		 * @return link to song from band
		 */
		public String getLink() {
			return link;
		}
		
	}
	
	
}
