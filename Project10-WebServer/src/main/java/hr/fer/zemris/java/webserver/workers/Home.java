package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Displays links to scripts and workers and has
 * has fields for adding two numbers and changing 
 * background color.
 *  
 * @author Luka Grgić
 *
 */
public class Home implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {

		if (context.getPersistentParameter("bgcolor") != null) {
			context.setTemporaryParameter("background", context.getPersistentParameter("bgcolor"));
		} else {
			context.setTemporaryParameter("background", "7F7F7F");
		}
		
		context.getDispatcher().dispatchRequest("/private/home.smscr");
		
	}

}
