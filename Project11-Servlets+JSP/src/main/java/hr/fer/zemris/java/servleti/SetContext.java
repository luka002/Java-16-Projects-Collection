package hr.fer.zemris.java.servleti;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Servlet context listener that stores
 * time when the server was started.
 * 
 * @author Luka Grgić
 */
@WebListener
public class SetContext implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent context) {
		context.getServletContext().removeAttribute("StartingTime");
	}

	@Override
	public void contextInitialized(ServletContextEvent context) {
		context.getServletContext().setAttribute("StartingTime", Long.toString(System.currentTimeMillis()));
	}

}
