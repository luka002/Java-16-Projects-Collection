package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Decorator for ILocalizationProvider.
 * 
 * @author Luka Grgić	
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/** Is connected */
	private boolean connected;
	/** provider */
	private ILocalizationProvider provider;
	/** Listener */
	private ILocalizationListener listener = new ILocalizationListener() {
		@Override
		public void localizationChanged() {
			fire();
		}
	};
	
	/**
	 * Constructor.
	 * 
	 * @param provider provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
		connect();
	}

	/**
	 * adds listener.
	 */
	public void connect() {
		if (connected) return;
		connected = true;
		provider.addLocalizationListener(listener);
	}

	/**
	 * Removes listener.
	 */
	public void disconnect() {
		if (!connected) return;
		connected = false;
		
		provider.removeLocalizationListener(listener);
	}
	
	@Override
	public String getString(String s) {
		return provider.getString(s);
	}

}
