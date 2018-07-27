package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Program that fetches chart data from a file
 * and then draws the chart.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class BarChartDemo extends JFrame {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor sets GUI.
	 * 
	 * @param model model
	 * @param file file
	 */
	public BarChartDemo(BarChart model, String file) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Demo");
		setLocation(100, 100);
		setSize(500, 500);
		initGUI(model, file);
	}

	/**
	 * Adds component to content pane.
	 * 
	 * @param model model.
	 * @param file file.
	 */
	private void initGUI(BarChart model, String file) {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new BarChartComponent(model, file), BorderLayout.CENTER);
	}

	/**
	 * Method that is first executed when program starts.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		String file = "chartData.txt";
		List<String> data = null;

		try {
			data = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Could not read file data.");
		}

		if (data.size() != 6) {
			System.err.println("Invalid data in file.");
		}

		final BarChart chart;
		try {
			String xDescription = data.get(0);
			String yDescription = data.get(1);
			String[] values = data.get(2).split("[\\s,]");
			int yMin = Integer.parseInt(data.get(3));
			int yMax = Integer.parseInt(data.get(4));
			int yStep = Integer.parseInt(data.get(5));

			List<XYValue> xyValues = new ArrayList<>();
			for (int i = 0; i < values.length; i += 2) {
				int x = Integer.parseInt(values[i]);
				int y = Integer.parseInt(values[i + 1]);
				xyValues.add(new XYValue(x, y));
			}

			chart = new BarChart(xyValues, xDescription, yDescription, yMin, yMax, yStep);
			
			SwingUtilities.invokeLater(() -> {
				new BarChartDemo(chart, file).setVisible(true);
			});
		} catch (NumberFormatException e) {
			System.err.println("Wrong data provided.");
		}
	}

}
