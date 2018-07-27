package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * Servlet that shows list of selected user blogs from which any of
 * them can be selected and shown. It is also in charge
 * of creating blogs, updating blogs and adding comments.
 * 
 * @author Luka Grgić
 */
@WebServlet("/servleti/author/*")
public class Author extends HttpServlet {

	/**	Auto generated. */
	private static final long serialVersionUID = -3690400366367102678L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getPathInfo() == null) {
			req.getRequestDispatcher("/WEB-INF/pages/invalidPath.jsp").forward(req, resp);
			return;
		}
		
		String pathInfo = req.getPathInfo().substring(1);
		
		if (!pathInfo.contains("/")) {
			hasOnlyNick(pathInfo, req, resp);
			return;
		}
		
		String[] path = pathInfo.split("/");
		if (path.length != 2) {
			req.getRequestDispatcher("/WEB-INF/pages/invalidPath.jsp").forward(req, resp);
			return;
		}
		
		if (!DAOProvider.getDAO().nickExists(path[0])) {
			req.getRequestDispatcher("/WEB-INF/pages/invalidPath.jsp").forward(req, resp);
			return;
		}
		
		req.setAttribute("nick", path[0]);
		
		if (path[1].equals("new")) {
			req.setAttribute("action", "Create");
			req.getRequestDispatcher("/WEB-INF/pages/createEditBlog.jsp").forward(req, resp);
			return;
		} 
		
		if (path[1].equals("edit") && isInt(req.getParameter("id"))) {
			long id = Long.parseLong(req.getParameter("id"));
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
			
			if (entry == null) {
				req.getRequestDispatcher("/WEB-INF/pages/invalidPath.jsp").forward(req, resp);
				return;
			}
			
			req.setAttribute("action", "Edit");
			req.setAttribute("entry", entry);
			req.getRequestDispatcher("/WEB-INF/pages/createEditBlog.jsp").forward(req, resp);
			return;
		}
		
		if (isInt(path[1])) {
			long id = Long.parseLong(path[1]);
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
			
			if (entry == null) {
				req.getRequestDispatcher("/WEB-INF/pages/invalidPath.jsp").forward(req, resp);
				return;
			}
			
			req.setAttribute("entry", entry);
			req.getRequestDispatcher("/WEB-INF/pages/showBlog.jsp").forward(req, resp);
			return;
		}
		
		req.getRequestDispatcher("/WEB-INF/pages/invalidPath.jsp").forward(req, resp);
	}
	
	/**
	 * Fetches users and renders page with that user blogs shown
	 * as list.
	 * 
	 * @param nick nick
	 * @param req req
	 * @param resp resp
	 * @throws ServletException exception
	 * @throws IOException exception
	 */
	private void hasOnlyNick(String nick, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!DAOProvider.getDAO().nickExists(nick)) {
			req.getRequestDispatcher("/WEB-INF/pages/invalidPath.jsp").forward(req, resp);
			return;
		}
		
		List<BlogEntry> entries = DAOProvider.getDAO().getUserBlogs(nick);
		
		if (!entries.isEmpty()) {
			req.setAttribute("entries", entries);
		}
		
		req.setAttribute("nick", nick);
		req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
		return;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String nick = (String) req.getSession().getAttribute("userNick");
		
		String method = req.getParameter("method");
		if("Cancel".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + nick);
			return;
		}
		
		if("Create".equals(method)) {
			BlogEntry entry = new BlogEntry();	
			entry.setCreatedAt(new Date());
			entry.setLastModifiedAt(entry.getCreatedAt());
			entry.setTitle(req.getParameter("title"));
			entry.setText(req.getParameter("text"));
			entry.setCreator(DAOProvider.getDAO().getUser(nick));
			
			if (entry.getTitle().trim().isEmpty() || entry.getText().trim().isEmpty()) {
				sendError(entry, nick, "Create", req, resp);
				return;
			}
			
			DAOProvider.getDAO().saveBlogEntry(entry);
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + nick);
			return;
		}
		
		if("Edit".equals(method)) {
			Long id = Long.parseLong(req.getParameter("id"));
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
			entry.setLastModifiedAt(new Date());
			entry.setTitle(req.getParameter("title"));
			entry.setText(req.getParameter("text"));
			
			if (entry.getTitle().trim().isEmpty() || entry.getText().trim().isEmpty()) {
				sendError(entry, nick, "Edit", req, resp);
				return;
			}
			
			DAOProvider.getDAO().updateBlogEntry(entry);
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + nick + "/" + entry.getId());
			return;
		}
		
		if("Comment".equals(method)) {
			nick = req.getPathInfo().substring(1).replaceFirst("/.*", "");
			int index = req.getPathInfo().lastIndexOf('/');
			Long entryId = Long.parseLong(req.getPathInfo().substring(index+1));
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryId);
			
			BlogComment comment = new BlogComment();
			comment.setBlogEntry(entry);
			comment.setMessage(req.getParameter("comment"));
			comment.setPostedOn(new Date());
			comment.setUsersEMail(req.getParameter("email"));
			
			if (comment.getMessage().trim().isEmpty() || comment.getUsersEMail().trim().isEmpty()) {
				req.setAttribute("error", "No empty fields allowed.");
				req.setAttribute("comment", comment);
				req.setAttribute("entry", entry);
				req.getRequestDispatcher("/WEB-INF/pages/showBlog.jsp").forward(req, resp);
				return;
			}

			DAOProvider.getDAO().saveComment(comment);
			
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + nick + "/" + entry.getId());
			return;
		}
	}

	/**
	 * Fills request attributes with given values.
	 * 
	 * @param entry entry
	 * @param nick nick
	 * @param action action
	 * @param req req
	 * @param resp resp
	 * @throws ServletException exception
	 * @throws IOException exception
	 */
	private void sendError(BlogEntry entry, String nick, String action, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("nick", nick);
		req.setAttribute("error", "No empty fields allowed.");
		req.setAttribute("action", action);
		req.setAttribute("entry", entry);
		req.getRequestDispatcher("/WEB-INF/pages/createEditBlog.jsp").forward(req, resp);
	}

	/**
	 * Checks if provided string can be parsed as int.
	 * 
	 * @param string string checked if it can be parsed as int
	 * @return true it string can be parsed as int
	 */
	private boolean isInt(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
 
}
