package hr.fer.zemris.java.gui.calc;

import javax.swing.JLabel;

/**
 * Label that implements CalcValueListener so it can
 * listen to calculator.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class MyLabel extends JLabel implements CalcValueListener {

	/**
	 * Constructor.
	 * @param text text
	 */
	public MyLabel(String text) {
		super(text);
	}

	private static final long serialVersionUID = 1L;

	/**
	 * Sets label text to value from the calculator.
	 */
	@Override
	public void valueChanged(CalcModel model) {
		String string = model.toString();
		setText(string == null ? "0" : string);
	}
	
}
