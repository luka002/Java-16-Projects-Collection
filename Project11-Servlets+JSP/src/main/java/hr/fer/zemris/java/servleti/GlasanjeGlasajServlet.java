package hr.fer.zemris.java.servleti;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.servleti.util.FileUtil;

/**
 * Servlet that updates voting results
 * based on id parameter provided.
 * 
 * @author Luka Grgić
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path results = Paths.get(fileName);
		
		FileUtil.createIfDoesntExists(results, req);
		
		List<String> lines = Files.readAllLines(results, StandardCharsets.UTF_8);
		String fileUpdated = "";
		String id = req.getParameter("id");
		
		for (String line : lines) {
			String[] values = line.split("\t");
			
			if (values[0].equals(id)) {
				fileUpdated += values[0] + "\t" + (Integer.parseInt(values[1]) + 1) + "\n";
			} else {
				fileUpdated += values[0] + "\t" + values[1] + "\n";
			}
		}
		
		fileUpdated = fileUpdated.substring(0, fileUpdated.length()-1);	
		
		synchronized (getServletContext()) {
			Files.write(results, fileUpdated.getBytes(StandardCharsets.UTF_8));	
		}
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

	
	
}
