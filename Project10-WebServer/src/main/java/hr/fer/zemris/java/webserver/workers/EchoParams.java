package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Outputs back to the user the parameters it obtained 
 * formatted as an HTML table.
 * 
 * @author Luka Grgić
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		StringBuilder table = new StringBuilder(
				"<style>\r\n" + 
				"table, th, td {\r\n" + 
				"    border: 1px solid black;\r\n" + 
				"}\r\n" + 
				"</style><table>"
		);
		
		context.getParameters().forEach((k, v) -> {
			table.append("<tr><td>" + k + "</td>" + "<td>" + v + "</td></tr>");
		});
		
		table.append("</table>");
		
		context.write(table.toString());
	}

}
