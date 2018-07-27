package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Generates document with lint to Home worker and
 * displays is color has been updated.
 * 
 * @author Luka Grgić
 */
public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color = context.getParameter("bgcolor");
		context.write("<html><body>");
		
		if (color != null && color.matches("^(?:[0-9a-fA-F]{6})$")) {
			context.setPersistentParameter("bgcolor", color);
			context.write("Color updated, <a href=\"/index2.html\">go back</a>.");
		} else {
			context.write("Color not updated, <a href=\"/index2.html\">go back</a>.");
		}
		
		context.write("</html></body>");
	}

}
