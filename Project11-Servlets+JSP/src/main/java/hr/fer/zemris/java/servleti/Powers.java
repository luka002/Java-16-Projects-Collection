package hr.fer.zemris.java.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet that creates xls file with n pages
 * and on each page there are two columns. First
 * column contains values from a to b and second
 * column contains that value raised on page number
 * power. Values a, b and n are provided as parameters.
 * 		-a (integer from [-100,100]) 
 * 		-b (integer from [-100,100])
 * 		-n (integer from [1,5])
 * 
 * @author Luka Grgić
 */
@WebServlet("/powers")
public class Powers extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String getA = req.getParameter("a");
		String getB = req.getParameter("b");
		String getN = req.getParameter("n");
		
		Integer a = getAsInt(getA, -100);
		Integer b = getAsInt(getB, 100);
		Integer n = getAsInt(getN, 5);
		
		boolean invalid = false;
		if (a < -100 || a > 100) {
			req.setAttribute("a", "invalid");
			invalid = true;
		}
		if (b > 100 || b < -100) {
			req.setAttribute("b", "invalid");
			invalid = true;
		}
		if (n < 1 || n > 5) {
			req.setAttribute("n", "invalid");
			invalid = true;
		}
		if (a > b) {
			req.setAttribute("a<=b", "invalid");
			invalid = true;
		}
		
		if (invalid) {
			req.getRequestDispatcher("/WEB-INF/pages/powersInvalid.jsp").forward(req, resp);
			return;	
		}
		
		HSSFWorkbook hwb = new HSSFWorkbook();

		for (int i = 1; i <= n; i++) {
			HSSFSheet sheet = hwb.createSheet("Sheet " + i);
			int currentRow = 0;
			
			HSSFRow rowhead = sheet.createRow((int)currentRow++);
			rowhead.createCell((int)0).setCellValue("Value");
			rowhead.createCell((int)1).setCellValue("Value^" + i);
			
			for (int j = a; j <= b; j++) {
				HSSFRow row = sheet.createRow((int)currentRow++);
				row.createCell((int)0).setCellValue(j);
				row.createCell((int)1).setCellValue(Math.pow(j, i));
			}
		}
		
		resp.setContentType("application/vnd.ms-excel"); 
		resp.setHeader("Content-Disposition", "attachment; filename=powers.xls");
		
		hwb.write(resp.getOutputStream());
		hwb.close();
	}

	/**
	 * Get getNum as integer. If it can not
	 * be cast to integer return default value.
	 * 
	 * @param getNum string that should be cast to int
	 * @param defaultValue default value
	 * @return getNum cast as integer or default value if unable 
	 * to do so
	 */
	private Integer getAsInt(String getNum, Integer defaultValue) {
		if (getNum!= null) {
			try {
				return Integer.valueOf(getNum);
			} catch (Exception e) {
				return defaultValue;
			}
		} else {
			return defaultValue;
		}
	}
		
}
