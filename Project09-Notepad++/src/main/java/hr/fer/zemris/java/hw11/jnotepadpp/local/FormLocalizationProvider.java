package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Derived from LocalizationProviderBridge.
 * 
 * @author Luka Grgić
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/** frame */
	private JFrame frame;
	
	/**
	 * Constuctor.
	 * 
	 * @param provider provider
	 * @param frame frame
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		this.frame = frame;
		
		this.frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent event) {
				disconnect();
			}

			@Override
			public void windowOpened(WindowEvent event) {
				connect();
			}
			
		});
	}
	
}
