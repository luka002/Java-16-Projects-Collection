package hr.fer.zemris.java.servleti;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Loads glasanje-definicija.txt file with
 * information about bands and writes it out
 * so user can vote for one band.
 * 
 * @author Luka Grgić
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String fileName = req.getServletContext().getRealPath(
				 "/WEB-INF/glasanje-definicija.txt"
		);
		
		List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
		List<Bands> bands = new ArrayList<>();
		
		for (String line : lines) {
			String[] values = line.split("\t");
			
			bands.add(new Bands(values[0], values[1]));
		}
		
		req.setAttribute("bands", bands);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
	
	/**
	 * Class that stores band id and name.
	 * 
	 * @author Luka Grgić
	 */
	public static class Bands {
		/** Band id */
		private String number;
		/** Band name */
		private String name;
		
		/**
		 * Constructor.
		 * 
		 * @param number id
		 * @param name name
		 */
		public Bands(String number, String name) {
			this.number = number;
			this.name = name;
		}

		/**
		 * Get id.
		 * 
		 * @return id
		 */
		public String getNumber() {
			return number;
		}

		/**
		 * Get name.
		 * 
		 * @return name
		 */
		public String getName() {
			return name;
		}
		
	}

	
	
}
