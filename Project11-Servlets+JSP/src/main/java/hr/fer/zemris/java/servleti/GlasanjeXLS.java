package hr.fer.zemris.java.servleti;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.servleti.GlasanjeRezultatiServlet.ResultData;
import hr.fer.zemris.java.servleti.util.FileUtil;

/**
 * Creates xls file with voting results.
 * 
 * @author Luka Grgić
 */
@WebServlet("/glasanje-xls")
public class GlasanjeXLS extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<ResultData> resultData = FileUtil.getResults(req);
		
		HSSFWorkbook hwb = new HSSFWorkbook();

		HSSFSheet sheet = hwb.createSheet("Rezultati glasanja");
		int currentRow = 0;

		HSSFRow rowhead = sheet.createRow((int) currentRow++);
		rowhead.createCell((int) 0).setCellValue("Bend");
		rowhead.createCell((int) 1).setCellValue("Broj glasova");

		for (int j = 0; j < resultData.size(); j++) {
			HSSFRow row = sheet.createRow((int) currentRow++);
			row.createCell((int) 0).setCellValue(resultData.get(j).name);
			row.createCell((int) 1).setCellValue(resultData.get(j).voteResult);
		}
		
		resp.setContentType("application/vnd.ms-excel"); 
		resp.setHeader("Content-Disposition", "attachment; filename=rezultati.xls");
		
		hwb.write(resp.getOutputStream());
		hwb.close();
	}

}
