package hr.fer.zemris.java.tecaj_13.model;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

/**
 * Class that simulates registration form. It has fields
 * that registration form has and it can validate input.
 * 
 * @author Luka Grgić
 */
public class RegistrationForm extends LoginForm {

	/**
	 * Provided first name
	 */
	private String firstName;
	/**
	 * Provided last name
	 */
	private String lastName;
	/**
	 * Provided email
	 */
	private String email;
	
	/**
	 * Email regex that checks if email provided is valid
	 */
	private final String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$"
			+ "%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\"
			+ "x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@"
			+ "(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])"
			+ "?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]"
			+ "|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\"
			+ "x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	
	@Override
	public void fillFromHttpRequest(HttpServletRequest req) {
		super.fillFromHttpRequest(req);
		this.firstName = prepare(req.getParameter("firstName"));
		this.lastName = prepare(req.getParameter("lastName"));
		this.email = prepare(req.getParameter("email"));
	}
	
	/**
	 * Fills this object from BlogUser object.
	 * 
	 * @param blogUser blog user
	 */
	public void fillFromBlogUser(BlogUser blogUser) {
		this.firstName = blogUser.getFirstName();
		this.lastName = blogUser.getLastName();
		this.nick = blogUser.getNick();
		this.email = blogUser.getEmail();
	}
	
	/**
	 * Fills BlogUser object from this object.
	 * 
	 * @param blogUser blog user
	 */
	public void fillInBlogUser(BlogUser blogUser) {
		blogUser.setFirstName(firstName);
		blogUser.setLastName(lastName);
		blogUser.setNick(nick);
		blogUser.setEmail(email);
		blogUser.setPasswordHash(calculateHash(password));
	}
	
	@Override
	public void validate() {
		errors.clear();
		
		if (firstName.isEmpty()) {
			errors.put("firstName", "First name required.");
		}
		
		if (lastName.isEmpty()) {
			errors.put("lastName", "Last name required.");
		}
		
		if (nick.isEmpty()) {
			errors.put("nick", "Nick required.");
		} else if (DAOProvider.getDAO().nickExists(nick)) {
			errors.put("nick", "Nick already exist.");
		}
		
		if (email.isEmpty() || !email.matches(emailRegex)) {
			errors.put("email", "Email is not valid.");
		}
		
		if (password.isEmpty()) {
			errors.put("password", "Password required.");
		}
	}

	/**
	 * Get first name.
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Set first name.
	 * 
	 * @param firstName first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Get last name.
	 * 
	 * @return lase name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set last name.
	 * 
	 * @param lastName last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Get email.
	 * 
	 * @return email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set email.
	 * 
	 * @param email email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
}
