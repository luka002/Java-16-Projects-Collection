package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements ILocalizationProvider and adds fire method.
 * 
 * @author Luka Grgić
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/** List of listeners */
	private List<ILocalizationListener> list;
	
	/**
	 * Constructor.
	 */
	public AbstractLocalizationProvider() {
		this.list = new ArrayList<>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		list.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		list.remove(l);
	}

	/**
	 * Notifies listeners.
	 */
	public void fire() {
		list.forEach(l -> l.localizationChanged());
	}
	
	public abstract String getString(String s);

	
	

}
