package hr.fer.zemris.jsdemo.servleti;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that provides picture from /WEB-INF/slike folder.
 * Name of the picture is provided as "segment" parameter.
 * 
 * @author Luka Grgić
 */
@WebServlet("/servleti/segment")
public class Segment extends HttpServlet {

	/**
	 * Auto generated.
	 */
	private static final long serialVersionUID = -5587309250252249366L;
	/**
	 * Path to folder where pictures are stored.
	 */
	private final String PICTURES_PATH = "/WEB-INF/slike/";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String picName = req.getParameter("segment");
		String path = req.getServletContext().getRealPath(PICTURES_PATH) + picName;
		
		BufferedImage image = ImageIO.read(new File(path));
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		resp.setContentType("image/jpg");
		ImageIO.write(image, "jpg", output);
		
		resp.getOutputStream().write(output.toByteArray());
	}
	
}
