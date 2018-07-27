package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Calculates sum of parameters a and b. If they
 * are not set value used will be 1.
 * 
 * @author Luka Grgić
 */
public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String a = context.getParameter("a");
		String b = context.getParameter("b");
		
		if (a != null && !a.isEmpty()) {
			context.setTemporaryParameter("a", a);
		} else {
			a = "1";
			context.setTemporaryParameter("a", a);
		}
		
		if (b != null && !b.isEmpty()) {
			context.setTemporaryParameter("b", b);
		} else {
			b = "1";
			context.setTemporaryParameter("b", b);
		}
		
		int zbroj = Integer.parseInt(a) + Integer.parseInt(b);
		context.setTemporaryParameter("zbroj", Integer.toString(zbroj));
		
		context.getDispatcher().dispatchRequest("/private/calc.smscr");
	}

}
