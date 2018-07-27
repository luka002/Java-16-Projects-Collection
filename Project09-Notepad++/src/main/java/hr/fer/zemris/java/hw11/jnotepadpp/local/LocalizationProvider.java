package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton class that creates translations with
 * ResourceBundle.
 * 
 * @author Luka Grgić
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/** Language */
	private String language;
	/** Resource bundle */
	private ResourceBundle bundle;
	
	/** Localization provider instance */
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	/**
	 * Constructor.
	 */
	private LocalizationProvider() {
		setLanguage("en");
	}
	
	/**
	 * @return instance
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	/**
	 * Sets Language.
	 * 
	 * @param lan language.
	 */
	public void setLanguage(String lan) {
		language = lan;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
		fire();
	}
	
	@Override
	public String getString(String s) {
		return bundle.getString(s);
	}

	/**
	 * @return language.
	 */
	public String getLanguage() {
		return language;
	}

}
