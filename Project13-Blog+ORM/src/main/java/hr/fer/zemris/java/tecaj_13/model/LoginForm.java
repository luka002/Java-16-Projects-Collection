package hr.fer.zemris.java.tecaj_13.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

/**
 * Class that simulates login form. It has fields
 * that login form has and it can validate input.
 * 
 * @author Luka Grgić
 */
public class LoginForm {

	/**
	 * Provided nick
	 */
	protected String nick;
	/**
	 * Provided password
	 */
	protected String password;
	/**
	 * Map containing input error
	 */
	protected Map<String, String> errors = new HashMap<>();

	/**
	 * Get error with provided name.
	 * 
	 * @param name name of the error
	 * @return error
	 */
	public String getError(String name) {
		return errors.get(name);
	}
	
	/**
	 * Check if there were any errors.
	 * 
	 * @return <code>true</code> if there is
	 * at least one error otherwise <code>false</code>.
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	/**
	 * Check if there is an error under provided name.
	 * 
	 * @param name name of the error
	 * @return <code>true</code> it there is an error
	 * under provided name else <code>false</code>.
	 */
	public boolean hasError(String name) {
		return errors.containsKey(name);
	}
	
	/**
	 * Fills the object from http request.
	 * 
	 * @param req http request
	 */
	public void fillFromHttpRequest(HttpServletRequest req) {
		this.nick = prepare(req.getParameter("nick"));
		this.password = prepare(req.getParameter("password"));
	}
	
	/**
	 * If parameter is <code>null</code> returns
	 * empty string else returns trimmed string.
	 * 
	 * @param parameter parameter
	 * @return if parameter is <code>null</code> returns
	 * empty string else returns trimmed string
	 */
	protected String prepare(String parameter) {
		if(parameter == null) return "";
		return parameter.trim();
	}
	
	/**
	 * Checks if there are any errors. If there are,
	 * <code>errors</code> map is filled with them.
	 */
	public void validate() {
		errors.clear();
		
		if (nick.isEmpty()) {
			errors.put("nick", "Nick required.");
			return;
		} else if (!DAOProvider.getDAO().nickExists(nick)) {
			errors.put("nick", "Nick doesn't exist.");
			return;
		}
		
		BlogUser user = DAOProvider.getDAO().getUser(nick);
		
		if (!user.getPasswordHash().equals(calculateHash(password))) {
			errors.put("password", "Incorect password.");
			return;
		}
	}
	
	/**
	 * Get nick.
	 * 
	 * @return nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Set nick.
	 * 
	 * @param nick nick.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * Get password as hash.
	 * 
	 * @return password as hash.
	 */
	public String getPassword() {
		return calculateHash(password);
	}

	/**
	 * Set password.
	 * 
	 * @param password password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Calculate hash from provided password.
	 * 
	 * @param password password
	 * @return calculated hash
	 */
	protected String calculateHash(String password) {
		MessageDigest sha = null;

		try {
			sha = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("No such algorithm.");
		}

		byte[] hash = sha.digest(password.getBytes());
		
		return byteToHex(hash);
	}

	/**
	 * Converts byte array as hex string.
	 * 
	 * @param hash byte array
	 * @return byte array as hex string
	 */
	protected String byteToHex(byte[] hash) {
		if (hash.length == 0)
			return "";

		char[] hex = new char[hash.length * 2];

		for (int i = 0; i < hash.length; i++) {
			hex[i * 2] = (char) ((hash[i] & 0xF0) >>> 4);
			hex[i * 2 + 1] = (char) (hash[i] & 0x0F);
		}
		
		for (int i = 0; i < hex.length; i++) {
			if (hex[i] >= 0 && hex[i] < 10) {
				hex[i] += '0';
			} else {
				hex[i] += 'a' - 10;
			}
		}
		
		return new String(hex);
	}
	
}
