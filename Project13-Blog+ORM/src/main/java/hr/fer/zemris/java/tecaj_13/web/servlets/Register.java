package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.RegistrationForm;

/**
 * Servlet that enables user to register.
 * 
 * @author Luka Grgić
 */
@WebServlet("/servleti/register")
public class Register extends HttpServlet {

	/** Auto generated */
	private static final long serialVersionUID = 367494131885032643L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String method = req.getParameter("method");
		if(!"Save".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}

		RegistrationForm form = new RegistrationForm();
		form.fillFromHttpRequest(req);
		form.validate();
		
		if(form.hasErrors()) {
			req.setAttribute("entry", form);
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
			return;
		}
		
		BlogUser user = new BlogUser();
		form.fillInBlogUser(user);
		DAOProvider.getDAO().saveUser(user);
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
	
}
