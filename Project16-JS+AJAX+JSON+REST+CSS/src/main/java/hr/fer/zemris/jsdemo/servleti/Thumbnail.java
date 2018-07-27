package hr.fer.zemris.jsdemo.servleti;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that fetches thumbnail with dimension of
 * 150x150 from /WEB-INF/thumbnails folder. If folder
 * doesn't exist it will be created. If thumbnail doesn't
 * exist it will be created. Described thumbnails are thumbnail
 * from pictures in /WEB-INF/slike folder.
 * 
 * @author Luka Grgić
 */
@WebServlet("/servleti/thumb")
public class Thumbnail extends HttpServlet{

	/**
	 * Auto generated.
	 */
	private static final long serialVersionUID = 3487013402840583491L;
	/**
	 * Path to folder where pictures are stored.
	 */
	private final String PICTURES_PATH = "/WEB-INF/slike/";
	/**
	 * Path to folder where thumbnails are stored.
	 */
	private final String THUMBNAILS_PATH = "/WEB-INF/thumbnails/";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String picName = req.getParameter("thumb");
		String path = req.getServletContext().getRealPath(PICTURES_PATH) + picName;
		String thumbPath = req.getServletContext().getRealPath(THUMBNAILS_PATH) + picName;
		
		createThumbnailFolderIfDoesntExist(thumbPath.substring(0, thumbPath.length()-1));
		createThumbnailIfDoesntExist(thumbPath, path);
		
		BufferedImage image = ImageIO.read(new File(thumbPath));
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		resp.setContentType("image/jpg");
		ImageIO.write(image, "jpg", output);
		
		resp.getOutputStream().write(output.toByteArray());
	}
	
	/**
	 * Creates thumbnail if it doesn't exist.
	 * 
	 * @param thumbPath path where thumbnail will be stored
	 * @param path path from original picture which thumbnail will be created
	 * @throws IOException if error reading or writing image
	 */
	private void createThumbnailIfDoesntExist(String thumbPath, String path) throws IOException {
		if (!Files.exists(Paths.get(thumbPath))) {
			BufferedImage image = ImageIO.read(new File(path));
			BufferedImage resizedImage = resizeImage(image);
			ImageIO.write(resizedImage, "jpg", new File(thumbPath));
		}
	}

	/**
	 * Creates thumbnail folder if it doesn't exist.
	 * 
	 * @throws IOException if error creating folder
	 */
	private void createThumbnailFolderIfDoesntExist(String path) throws IOException {
		Path thumbPath = Paths.get(path);
		
		if (!Files.exists(thumbPath)) {
			Files.createDirectories(thumbPath);
		}
	}

	/**
	 * Resizes image into image with dimension 150x150 and returns
	 * is as BufferedImage.
	 * 
	 * @param originalImage original image
	 * @return resizes image
	 */
	private BufferedImage resizeImage(BufferedImage originalImage){
		BufferedImage resizedImage = new BufferedImage(150, 150, Image.SCALE_SMOOTH);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, 150, 150, null);
		g.dispose();

		return resizedImage;
	}

	
	
}
