package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Graphics;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

/**
 * Represents status bar on the bottom of the frame.
 * 
 * @author Luka Grgić
 */
public class StatusBar extends JLabel {

	private static final long serialVersionUID = 1L;

	/** Date formatter */
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	/** Document length */
	private int length;
	/** Caret current line */
	private int currentLine;
	/** Caret current column */
	private int currentColumn;
	/** Selection */
	private int selection;
	/** Model */
	private MultipleDocumentModel mdModel;
	
	/**
	 * Constructor.
	 * 
	 * @param text text
	 * @param mdModel model
	 */
	public StatusBar(String text, MultipleDocumentModel mdModel) {
		super(text);	
		this.mdModel = mdModel;
		this.mdModel.addMultipleDocumentListener(mdListener);
		
		if (mdModel.getCurrentDocument() != null) {
			mdModel.getCurrentDocument().getTextComponent().addCaretListener(cListener);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawString("length : " + length, 5, 15);
		g.drawLine(180, 0, 180, 20);
		
		String stats = "Ln : " + currentLine + "  Col : " + currentColumn + 
				"  Sel : " + selection;
		g.drawString(stats, 185, 15);
		
		String time = sdf.format(new Timestamp(System.currentTimeMillis()));
		int timeLength = g.getFontMetrics().stringWidth(time);
		int minLength = 190 + g.getFontMetrics().stringWidth(stats);
		g.drawLine(minLength, 0, minLength, 20);
		
		if (getWidth()-timeLength-10 < minLength) {
			g.drawString(time, minLength+5, 15);
		} else {
			g.drawString(time, getWidth()-timeLength-5, 15);
		}
	}

	/**
	 * @return length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length Sets length
	 */
	public void setLength(int length) {
		this.length = length;
		repaint();
	}

	/**
	 * @return returns currentLine
	 */
	public int getCurrentLine() {
		return currentLine;
	}

	/**
	 * @param currentLine Sets currentLine
	 */
	public void setCurrentLine(int currentLine) {
		this.currentLine = currentLine;
		repaint();
	}

	/**
	 * @return currentColumn
	 */
	public int getCurrentColumn() {
		return currentColumn;
	}

	/**
	 * @param currentColumn Sets currentColumn
	 */
	public void setCurrentColumn(int currentColumn) {
		this.currentColumn = currentColumn;
		repaint();
	}

	/**
	 * @return selection.
	 */
	public int getSelection() {
		return selection;
	}

	/**
	 * @param selection Sets selection.
	 */
	public void setSelection(int selection) {
		this.selection = selection;
		repaint();
	}
	
	/**
	 * MultipleDocumentListener that updated caret listener 
	 * when model changed.
	 */
	private final MultipleDocumentListener mdListener = new MultipleDocumentListener() {
		
		@Override
		public void documentRemoved(SingleDocumentModel model) {
		}
		
		@Override
		public void documentAdded(SingleDocumentModel model) {
			
		}
		
		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			if (previousModel != null) {
				previousModel.getTextComponent().removeCaretListener(cListener);
			}
			if (currentModel != null) {
				currentModel.getTextComponent().addCaretListener(cListener);
			}
			updateStatusBar();
		}
	};
	
	/**
	 * CaretListener updates status.
	 */
	private final CaretListener cListener = new CaretListener() {
	
		@Override
		public void caretUpdate(CaretEvent arg0) {
			updateStatusBar();
		}
	};
	
	/**
	 * Updates status bar.
	 */
	private void updateStatusBar() {
		if (mdModel.getCurrentDocument() == null) {
			setSelection(0);
			setLength(0);
			setCurrentLine(0);
			setCurrentColumn(0);
			return;
		}
		JTextArea textArea = mdModel.getCurrentDocument().getTextComponent();
		
		int length = Math.abs(
				textArea.getCaret().getDot()-textArea.getCaret().getMark()
		);
		setSelection(length);
		setLength(textArea.getDocument().getLength());
		
		try {
			int caretPosition = textArea.getCaretPosition();
			int lineNumber = textArea.getLineOfOffset(caretPosition) + 1;
			int columnNumber = caretPosition - textArea.getLineStartOffset(lineNumber-1) + 1;
			
			setCurrentLine(lineNumber);
			setCurrentColumn(columnNumber);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}
