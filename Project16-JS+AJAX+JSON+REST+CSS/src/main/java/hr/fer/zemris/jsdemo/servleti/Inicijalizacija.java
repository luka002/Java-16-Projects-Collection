package hr.fer.zemris.jsdemo.servleti;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.jsdemo.rest.Gallery;

/**
 * Listener class that creates saves path to the
 * pictures descriptor file in a Gallery instance.
 * 
 * @author Luka Grgić
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String path = sce.getServletContext().getRealPath("/WEB-INF/opisnik.txt");
		Gallery.getInstance().setDecsriptorPath(path);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}