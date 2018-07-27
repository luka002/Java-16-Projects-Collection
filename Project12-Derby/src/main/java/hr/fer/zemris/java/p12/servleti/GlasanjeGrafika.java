package hr.fer.zemris.java.p12.servleti;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Creates pie chart with voting results.
 * 
 * @author Luka Grgić
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafika extends HttpServlet {

	/**
	 * Auto generated.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		PieDataset dataset = createDataset(req);
		JFreeChart chart = createChart(dataset);
		
		BufferedImage image = chart.createBufferedImage(600, 400);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		resp.setContentType("image/png");
		ImageIO.write(image, "png", output);
		
		resp.getOutputStream().write(output.toByteArray());
	}

	/**
	 * Creates data for pie chart.
	 * 
	 * @param req request
	 * @return data for pie chart.
	 * @throws IOException ioexception
	 */
	private PieDataset createDataset(HttpServletRequest req) throws IOException {
		DefaultPieDataset result = new DefaultPieDataset();
		int pollID = Integer.parseInt(req.getParameter("pollID"));
		List<PollOption> resultData = DAOProvider.getDao().getPollOptions(pollID);
		
		for (PollOption data : resultData) {
			result.setValue(data.getOptionTitle(), data.getVotesCount());
		}
		
        return result;
	}

	/**
	 * Creates pie chart with provided data.
	 * 
	 * @param dataset provided data
	 * @return pie chart
	 */
	private JFreeChart createChart(PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart3D(
				"Rezultati glasanja", 
				dataset, 
				true, 
				true, 
				false
		);
		
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        
        return chart;
	}
	
}
