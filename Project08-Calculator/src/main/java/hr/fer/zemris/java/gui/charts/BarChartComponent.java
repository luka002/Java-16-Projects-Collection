package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComponent;

/**
 * Component that represents a chart. Component needs
 * description for x-axis, description for y-axis, min-y
 * to be seen of graph, max-y, step-y and file name.
 * With these, chart can be created.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	/** Chart data */ 
	private BarChart chart;
	/** File name from where data has been feched. */
	private String file;

	/**
	 * Constructor.
	 *  
	 * @param chart data
	 * @param file file name
	 */
	public BarChartComponent(BarChart chart, String file) {
		this.chart = chart;
		this.file = Paths.get(file).toAbsolutePath().toString();
	}

	/**
	 * Paints the component.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 12));
 
        Graphics2D g2d = (Graphics2D) g;  
        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#e8cbc0"),
        						getWidth(), getHeight(), Color.decode("#636fa4"));
        g2d.setPaint(gradient);        	
    	g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK); 
        int filePositionX = (getWidth()-g.getFontMetrics().stringWidth(file))/2;
        g.drawString(file, filePositionX, 20);
       
        AffineTransform defaultAt = g2d.getTransform();
        AffineTransform at = new AffineTransform();
        at.rotate(- Math.PI / 2);
        g2d.setTransform(at);
        
        int yDescriptionLength = g.getFontMetrics().stringWidth(chart.getyDescription());
        int y = (-getHeight())/2 - yDescriptionLength/2;
        g2d.drawString(chart.getyDescription(), y, 20);
        
        g2d.setTransform(defaultAt);
        int xDescriptionLength = g.getFontMetrics().stringWidth(chart.getxDescription());
        int x = (getWidth()-xDescriptionLength)/2;
        g2d.drawString(chart.getxDescription(), x, getHeight()-20);
  
        String lowestY = Integer.toString(chart.getyMin());
        String highestY = Integer.toString(chart.getyMax());
        String longest = (lowestY.length() < highestY.length()) ? highestY : lowestY;
        int longestNum = g.getFontMetrics().stringWidth(longest);
        
        int xStart = 50 + longestNum;
        int yStart = getHeight()-60;
        int maxX = getWidth()-25;
        int maxY = 50;
        double xLines = chart.getValues().size()+1;
        double ylines = ((chart.getyMax()-chart.getyMin())/chart.getyDistance())+1;
        int min = chart.getyMin();
       
        //Draw horizontal lines
        for (double i = 0; i < ylines; i++) {
        	int x1 = xStart - (int)(xStart*0.08);
        	int y1 = (int)(yStart-(((yStart-maxY)/(ylines-1))*i));
        	int x2 = maxX+5;
        	int y2 =  y1;
        	int numLength = g.getFontMetrics().stringWidth(Integer.toString(min));
        	g2d.drawString(Integer.toString(min), (int)(x1*0.95-numLength), y1+5);
        	g2d.drawLine(x1, y1, x2, y2);
        	min += chart.getyDistance();
        	if (i == 0) { 
        		g2d.fillPolygon(new int[]{x2+5, x2-1, x2-1}, new int[]{y2, y2+3, y2-3}, 3);
        	}
        }
        
        //Draw vertical lines
        for (int i = 0; i < xLines; i++) {
        	int x1 = (int)(xStart+(((getWidth()-xStart-25)/(xLines-1))*i));
        	int y1 = (int)(yStart + (yStart*0.01));
        	int x2 = x1;
        	int y2 = maxY-5;
        	g.drawLine(x1, y1, x2, y2);
        	if (i == 0) { 
        		g2d.fillPolygon(new int[]{x2, x2+3, x2-3}, new int[]{y2-5, y2+1, y2+1}, 3);
        	}
        }
        
        //Sort values based on x
        List<XYValue> values = chart.getValues()
        							.stream()	
        							.sorted((a, b) -> Integer.compare(a.getX(), b.getX()))
        							.collect(Collectors.toList());
        
        int x2 = (int)(xStart+(((getWidth()-xStart)/(xLines-1))*2)*0.95);
        int x1 = (int)(xStart+(((getWidth()-xStart)/(xLines-1))*1)*0.95);
        int cellWidth = x2 - x1;
        
        //Draw rectangles
        for (int i = 0; i < values.size(); i++) {
        	int px1 = (int)(xStart+(((getWidth()-xStart-25)/(xLines-1))*i))+1;
        	int py1 = (int)(yStart-(((yStart-maxY)/(ylines-1))*(values.get(i).getY()-chart.getyMin())/
        													chart.getyDistance()))+1;
        	Point p1 = new Point(px1, py1);
        	int px2 = (int)(xStart+(((getWidth()-xStart-25)/(xLines-1))*(i+1)));
        	int py2 = yStart;
        	Point p2 = new Point(px2, py2);
        	
        	if (py1 <= py2 ) {
				Rectangle r = new Rectangle(p1);
				r.add(p2);
				gradient = new GradientPaint(p1.x, p1.y, Color.decode("#1d2671"), 
											p2.x, p2.y, Color.decode("#c33764"));
				g2d.setPaint(gradient);
				g.fillRect(r.x, r.y, r.width, r.height);
			}
        	
            g.setColor(Color.BLACK); 
        	String xString = Integer.toString(values.get(i).getX());
        	int xStringLength = g.getFontMetrics().stringWidth(xString);
        	int xStringX = px2 - xStringLength/2 - cellWidth/2;
        	int xStringY = py2 + 16;
        	g2d.drawString(xString, xStringX, xStringY);
        }
	}
	
	
}
