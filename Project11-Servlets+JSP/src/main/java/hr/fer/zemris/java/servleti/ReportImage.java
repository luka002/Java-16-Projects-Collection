package hr.fer.zemris.java.servleti;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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

/**
 * Servlet that creates pie chart as an
 * png image and writes it to output.
 * 
 * @author Luka Grgić
 */
@WebServlet("/reportImage")
public class ReportImage extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		PieDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset);
		
		BufferedImage image = chart.createBufferedImage(600, 400);
		
		resp.setContentType("image/png");
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		try {
			ImageIO.write(image, "png", output);
		} catch (IOException ignorable) {
		}
		
		resp.getOutputStream().write(output.toByteArray());
	}

	/**
	 * Creates data for pie chart.
	 * 
	 * @return data for pie chart.
	 */
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Linux", 29);
        result.setValue("Mac", 20);
        result.setValue("Windows", 51);
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
				"Operating system usage", 
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
