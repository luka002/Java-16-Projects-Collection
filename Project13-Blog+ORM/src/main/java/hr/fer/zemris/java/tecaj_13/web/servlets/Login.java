package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.LoginForm;

/**
 * Servlet that handles login process.
 * 
 * @author Luka Grgić
 */
@WebServlet("/servleti/login")
public class Login extends HttpServlet {

	/** Auto generated */
	private static final long serialVersionUID = -2457272305751879100L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		LoginForm form = new LoginForm();
		form.fillFromHttpRequest(req);
		form.validate();
		
		if(form.hasErrors()) {
			req.setAttribute("login", form);
			req.getRequestDispatcher("/servleti/main").forward(req, resp);
		} else {
			BlogUser user = DAOProvider.getDAO().getUser(form.getNick());
			req.getSession().setAttribute("userId", user.getId());
			req.getSession().setAttribute("userFirstName", user.getFirstName());
			req.getSession().setAttribute("userLastName", user.getLastName());
			req.getSession().setAttribute("userNick", user.getNick());
			
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
		}
	}
	
}
