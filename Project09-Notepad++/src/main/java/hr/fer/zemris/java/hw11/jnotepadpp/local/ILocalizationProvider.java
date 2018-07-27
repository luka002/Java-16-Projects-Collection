package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Object which are instances of classes that implement 
 * this interface will be able to return translations 
 * for given keys
 * 
 * @author Luka Grgić
 */
public interface ILocalizationProvider {

	/**
	 * adds ILocalizationListener
	 * 
	 * @param l ILocalizationListener
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * removes ILocalizationListener
	 * 
	 * @param l ILocalizationListener
	 */
	void removeLocalizationListener(ILocalizationListener l);
	
	/**
	 * takes a key and gives back the localization.
	 * 
	 * @param s key
	 * @return localaization
	 */
	String getString(String s);
	
}
