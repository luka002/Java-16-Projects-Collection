package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw16.jvdraw.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;

/**
 * Component that displays which colors are currently
 * chosen for drawing. It is observer in observer design
 * pattern and it listens to JColorArea for color changes.
 *  
 * @author Luka Grgić
 *
 */
public class SelectedColors extends JLabel implements ColorChangeListener {
	
	/**
	 * Auto generated.
	 */
	private static final long serialVersionUID = -6847353863934854140L;
	/**
	 * Subject id observer design patter for foreground color
	 */
	private IColorProvider fgColorProvider;
	/**
	 * Subject id observer design patter for background color
	 */
	@SuppressWarnings("unused")
	private IColorProvider bgColorProvider;
	/**
	 * foreground color displayed as string
	 */
	private String fgColor;
	/**
	 * background color displayed as string
	 */
	private String bgColor;

	/**
	 * Constructor.
	 * 
	 * @param fgColorProvider subject id observer design patter for foreground color
	 * @param bgColorProvider subject id observer design patter for background color
	 */
	public SelectedColors(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		
		this.fgColor = getAsRGB(fgColorProvider.getCurrentColor(), true);
		this.bgColor = getAsRGB(bgColorProvider.getCurrentColor(), false);
		setText();
		
		fgColorProvider.addColorChangeListener(this);
		bgColorProvider.addColorChangeListener(this);
	}

	/**
	 * Transforms color into following string:
	 * "Fore/Back-ground color: (r, g, b)"
	 * 
	 * @param currentColor color to transform
	 * @param isForeground flag that shows if string should start with
	 * "Foreground" or "Background". If true "Foreground" is chosen.
	 * @return string notation of color 
	 */
	private String getAsRGB(Color currentColor, boolean isForeground) {
		StringBuilder rgb = null;
		
		if (isForeground) {
			rgb = new StringBuilder("Foreground color: (");
		} else {
			rgb = new StringBuilder("Background color: (");
		}
		
		rgb.append(currentColor.getRed() + ", ");
		rgb.append(currentColor.getGreen() + ", ");
		rgb.append(currentColor.getBlue() + ")");
		
		return rgb.toString();
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		if (source == fgColorProvider) {
			fgColor = getAsRGB(newColor, true);
		} else {
			bgColor = getAsRGB(newColor, false);
		}
		
		setText();
	}

	/**
	 * Sets label text to show current foreground and background color.
	 */
	private void setText() {
		setText(fgColor + ", " + bgColor + ".");
	}
	
}
