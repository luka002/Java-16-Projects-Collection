package hr.fer.zemris.java.servleti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that calculates sin and cos values
 * from all the numbers in between provided parameters
 * a and b. If a is not provided value used will be 0, if b is 
 * not provided value used will be 360, is b>a+720 b will be set 
 * to a+720 and if b is higher than a their values will
 * be swaped.
 * 
 * @author Luka Grgić
 */
@WebServlet("/trigonometric")
public class Trigonometric extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String getA = req.getParameter("a");
		String getB = req.getParameter("b");
		
		Integer a = null;
		Integer b = null;
		
		if (getA != null) {
			try {
				a = Integer.valueOf(getA);
			} catch (Exception e) {
				a = 0;
			}
		} else {
			a = 0;
		}
		
		if (getB != null) {
			try {
				b = Integer.valueOf(getB);
			} catch (Exception e) {
				b = 360;
			}
		} else {
			b = 360;
		}
		
		if (a > b) {
			Integer temp = a;
			a = b;
			b = temp;
		} else if (b > a + 720) {
			b = a + 720;
		}
		
		List<SinAndCosCalculated> list = new ArrayList<>();
		
		for (int i = a; i <= b; i++) {
			list.add(new SinAndCosCalculated(i, Math.sin((i*Math.PI)/180), Math.cos((i*Math.PI)/180)));
		}
	
		req.setAttribute("values", list);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
	
	public static class SinAndCosCalculated {
		private int x;
		private double sin;
		private double cos;
		
		public SinAndCosCalculated(int x, double sin, double cos) {
			this.x = x;
			this.sin = sin;
			this.cos = cos;
		}

		public int getX() {
			return x;
		}

		public double getSin() {
			return sin;
		}

		public double getCos() {
			return cos;
		}
		
	}
	
}
