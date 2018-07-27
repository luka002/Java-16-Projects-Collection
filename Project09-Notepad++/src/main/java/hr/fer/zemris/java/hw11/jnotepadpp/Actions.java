package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Class containing all actions.
 * 
 * @author Luka Grgić
 */
public class Actions {

	/** Model */
	private DefaultMultipleDocumentModel model;
	/** Frame */
	private JFrame frame;
	/** Localization provider */
	private ILocalizationProvider flp;
	
	/**
	 * Constructor.
	 * 
	 * @param model model
	 * @param frame frame
	 * @param flp localization provider
	 */
	public Actions(DefaultMultipleDocumentModel model, 
			JFrame frame, ILocalizationProvider flp) {
		this.model = model;
		this.frame = frame;
		this.flp = flp;
	}

	/**
	 * Method that does internationalization for action provided.
	 * 
	 * @param action action
	 * @param keyName key for name
	 * @param keyDescription key for description
	 */
	private void doI18N(AbstractAction action, String keyName, String keyDescription) {
		action.putValue(Action.NAME, flp.getString(keyName));
		action.putValue(Action.SHORT_DESCRIPTION, flp.getString(keyDescription));
		
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				action.putValue(Action.NAME, flp.getString(keyName));
				action.putValue(Action.SHORT_DESCRIPTION, flp.getString(keyDescription));
			}
		});
	}
	
	/**
	 * Creates AscendingAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return AscendingAction
	 */
	public AbstractAction ascending(String key, String keyDescription) {
		return new AscendingAction(key, keyDescription);
	}
	
	/**
	 * Action that sorts selected lines in text
	 * in ascending order.
	 * 
	 * @author Luka Grgić
	 */
	public class AscendingAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription desctiption key
		 */
		public AscendingAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F11"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			if (model.getCurrentDocument() == null) return;
			
			Locale hrLocale = new Locale(LocalizationProvider.getInstance().getLanguage());
			Collator collator = Collator.getInstance(hrLocale);
			
			sort(s -> Collections.sort(s, (s1, s2) -> collator.compare(s1, s2)));
		}
	};
	
	/**
	 * Creates DescendingAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return DescendingAction
	 */
	public AbstractAction descending(String key, String keyDescription) {
		return new DescendingAction(key, keyDescription);
	}
	
	/**
	 * Action that sorts selected lines in text
	 * in descending order.
	 * 
	 * @author Luka Grgić
	 */
	public class DescendingAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription desctiption key
		 */
		public DescendingAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F10"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			if (model.getCurrentDocument() == null) return;
			
			Locale hrLocale = new Locale(LocalizationProvider.getInstance().getLanguage());
			Collator collator = Collator.getInstance(hrLocale);
			
			sort(s -> Collections.sort(s, (s1, s2) -> collator.compare(s2, s1)));	
		}
	};
	
	/**
	 * Creates UniqueAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return UniqueAction
	 */
	public AbstractAction unique(String key, String keyDescription) {
		return new UniqueAction(key, keyDescription);
	}
	
	/**
	 * Action that deletes duplicated lines from text.
	 * 
	 * @author Luka Grgić
	 */
	public class UniqueAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription desctiption key
		 */
		public UniqueAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F9"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			if (model.getCurrentDocument() == null) return;
			
			Locale hrLocale = new Locale(LocalizationProvider.getInstance().getLanguage());
			Collator collator = Collator.getInstance(hrLocale);
			
			sort(s -> {
				for (int i = 0; i < s.size()-1; i++) {
					for (int j = i+1; j < s.size(); j++) {
						
						if (collator.compare(s.get(i), s.get(j)) == 0) {
							s.remove(j);
							j--;
						}
						
					}	
				}
			});
		}
	};
	
	/**
	 * Sorts list based on provided consumer.
	 * 
	 * @param sort consumer
	 */
	private void sort(Consumer<List<String>> sort) {
		JTextArea area = model.getCurrentDocument().getTextComponent();
		Document doc = area.getDocument();
		int min, max;
		if (area.getCaret().getDot()>area.getCaret().getMark()) {
			min = area.getCaret().getMark();
			max = area.getCaret().getDot();
		} else {
			min = area.getCaret().getDot();
			max = area.getCaret().getMark();
		}
		
		int minLine, minLineStart = 0, maxLine, maxLineEnd;
		
		try {
			minLine = area.getLineOfOffset(min);
			minLineStart = area.getLineStartOffset(minLine);
			maxLine = area.getLineOfOffset(max);
			maxLineEnd = area.getLineEndOffset(maxLine);
		
			String text = area.getText(minLineStart, maxLineEnd-minLineStart);
			if (!text.contains("\n")) return;
			
			List<String> lines = new ArrayList<>(Arrays.asList(text.split("\n")));
			
			sort.accept(lines);
			
			text = "";
			for (int i = 0; i < lines.size(); i++) {
				text += lines.get(i) + "\n";
			}
			
			doc.remove(minLineStart, maxLineEnd-minLineStart);
			doc.insertString(minLineStart, text, null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates ToLowerAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return ToLowerAction
	 */
	public AbstractAction toLower(String key, String keyDescription) {
		return new ToLowerAction(key, keyDescription);
	}
	/**
	 * Action that changes marked text to lower case.
	 * 
	 * @author Luka Grgić
	 */
	private class ToLowerAction extends AbstractAction {
	
		private static final long serialVersionUID = 1L;
	
		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription description key
		 */
		public ToLowerAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F8"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			manipulateCase(s -> s.toLowerCase());
		}
		
	};
	
	/**
	 * Creates ToUpperAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return ToUpperAction
	 */
	public AbstractAction toUpper(String key, String keyDescription) {
		return new ToUpperAction(key, keyDescription);
	}
	
	/**
	 * Action that changes marked text to upper case.
	 * 
	 * @author Luka Grgić
	 */
	private class ToUpperAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription description key
		 */
		public ToUpperAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F7"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			manipulateCase(s -> s.toUpperCase());
		}
		
	};
	
	/**
	 * Creates ToUpperAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return ToUpperAction
	 */
	public AbstractAction toggle(String key, String keyDescription) {
		return new ToggleAction(key, keyDescription);
	}
	
	/**
	 * Action that changes marked text to upper case.
	 * 
	 * @author Luka Grgić
	 */
	private class ToggleAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
		
			/**
			 * Constructor.
			 * 
			 * @param key name key
			 * @param keyDescription description key
			 */	
		public ToggleAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F6"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			manipulateCase(s -> changeCase(s));
		}
		
	};
	
	/**
	 * Changes text case based on function provided.
	 * 
	 * @param manipulate function
	 */
	private void manipulateCase(Function<String, String> manipulate) {
		JTextArea area = model.getCurrentDocument().getTextComponent();
		Document doc = area.getDocument();
		
		int len = Math.abs(
				area.getCaret().getDot()-area.getCaret().getMark()
		);
		
		int offset = 0;
		if (len != 0) {
			offset = Math.min(
				area.getCaret().getDot(), 
				area.getCaret().getMark()
			);
		} else {
			len = doc.getLength();
		}
		
		try {
			String text = doc.getText(offset, len);
			text = manipulate.apply(text);
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Changes Lower cast to upper and
	 * upper to lower.
	 * 
	 * @param text text
	 * @return changed string
	 */
	private String changeCase(String text) {
		char[] characters = text.toCharArray();
		
		for (int i = 0; i < characters.length; i++) {
			char c = characters[i];
			
			if (Character.isLowerCase(c)) {
				characters[i] = Character.toUpperCase(c);
			} else if (Character.isUpperCase(c)) {
				characters[i] = Character.toLowerCase(c);
			}
		}
		
		return new String(characters);
	}
	
	/**
	 * Creates FileMenuAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return FileMenuAction
	 */
	public AbstractAction fileMenu(String key, String keyDescription) {
		return new FileMenuAction(key, keyDescription);
	}
	
	/**
	 * @author Luka Grgić
	 */
	private class FileMenuAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription description key
		 */
		public FileMenuAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
		}
		
	};
	
	/**
	 * Creates EditMenuAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return EditMenuAction
	 */
	
	public AbstractAction editMenu(String key, String keyDescription) {
		return new EditMenuAction(key, keyDescription);
	}
	
	/**
	 * @author Luka Grgić
	 */
	private class EditMenuAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription description key
		 */
		public EditMenuAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
		}
		
	};
	
	/**
	 * Creates LanguagesMenuAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return LanguagesMenuAction
	 */
	public AbstractAction languagesMenu(String key, String keyDescription) {
		return new LanguagesMenuAction(key, keyDescription);
	}
	
	/**
	 * @author Luka Grgić
	 */
	private class LanguagesMenuAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription description key
		 */
		public LanguagesMenuAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
		}
		
	};
	
	/**
	 * Creates ToolsMenuAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return ToolsMenuAction
	 */
	public AbstractAction toolsMenu(String key, String keyDescription) {
		return new ToolsMenuAction(key, keyDescription);
	}
	
	/**
	 * @author Luka Grgić
	 */
	private class ToolsMenuAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription description key
		 */
		public ToolsMenuAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
		}
		
	};
	
	
	/**
	 * Creates ChangeCaseMenuobject.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return ChangeCaseMenu
	 */
	public AbstractAction changeCaseMenu(String key, String keyDescription) {
		return new ChangeCaseMenuAction(key, keyDescription);
	}
	
	/**
	 * @author Luka Grgić
	 */
	private class ChangeCaseMenuAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
			
		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription description key
		 */
		public ChangeCaseMenuAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_H);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
		}
		
	};
	
	/**
	 * Creates SortMenu object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return SortMenu
	 */
	public AbstractAction sortMenu(String key, String keyDescription) {
		return new SortMenuAction(key, keyDescription);
	}
	
	/**
	 * @author Luka Grgić
	 */
	private class SortMenuAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
			
		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription description key
		 */
		public SortMenuAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
		}
		
	};
	
	/**
	 * Creates CreateNewDocumentAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return CreateNewDocumentAction
	 */
	public AbstractAction createNew(String key, String keyDescription) {
		return new CreateNewDocumentAction(key, keyDescription);
	}
	
	/**
	 * Creates new model.
	 * 
	 * @author Luka Grgić
	 */
	private class CreateNewDocumentAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
			
		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription description key
		 */
		public CreateNewDocumentAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			model.createNewDocument();
		}
		
	};
	
	/**
	 * Creates OpenDocumentAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return OpenDocumentAction
	 */
	public AbstractAction open(String key, String keyDescription) {
		return new OpenDocumentAction(key, keyDescription);
	}
	
	/**
	 * Loads document from file system.
	 * 
	 * @author Luka Grgić
	 */
	private class OpenDocumentAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription description key
		 */
		public OpenDocumentAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			
			if (fc.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(
						frame,
						"File " + fileName.getAbsolutePath() + " does not exist!",
						"Pogreška",
						JOptionPane.ERROR_MESSAGE
				);
				return;
			}
			
			model.loadDocument(filePath);
		}
		
	};
	
	/**
	 * Creates SaveDocumentAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return SaveDocumentAction
	 */
	public AbstractAction save(String key, String keyDescription) {
		return new SaveDocumentAction(key, keyDescription);
	}
	
	/**
	 * Saves document.
	 * 
	 * @author Luka Grgić
	 */
	private class SaveDocumentAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription description key
		 */
		public SaveDocumentAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			if (model.getCurrentDocument() == null) {
				return;
			} else if (model.getCurrentDocument().getFilePath() == null) {
				saveFile();
				return;
			}
			
			model.saveDocument(model.getCurrentDocument(), model.getCurrentDocument().getFilePath());
		}
	};
	
	/**
	 * Creates SaveAsDocumentAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return SaveAsDocumentAction
	 */
	public AbstractAction saveAs(String key, String keyDescription) {
		return new SaveAsDocumentAction(key, keyDescription);
	}
	
	/**
	 * Saves document to specified path.
	 * 
	 * @author Luka Grgić
	 */
	private class SaveAsDocumentAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription description key
		 */
		public SaveAsDocumentAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F2"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			if (model.getCurrentDocument() == null) {
				return;
			}
			saveFile();
		}
	};

	/**
	 * Saves file.
	 */
	private void saveFile() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save document");

		if (jfc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(
					frame, 
					"Nothing is saved", 
					"Warning",
					JOptionPane.WARNING_MESSAGE
			);
			return;
		}

		for (SingleDocumentModel sModel : model) {
			if (sModel.getFilePath() != null && 
					sModel.getFilePath().equals(jfc.getSelectedFile().toPath())) {
				JOptionPane.showMessageDialog(
						frame, 
						"Selected file is already opened in another tab.", 
						"Warning",
						JOptionPane.WARNING_MESSAGE
				);
				return;
			}
		}
		
		if (Files.exists(jfc.getSelectedFile().toPath())) {
			int dialogResult = JOptionPane.showConfirmDialog(
					frame,
					"File already exists, do you wish to overwrite it?", 
					"Overwrite exsisting file",
					JOptionPane.YES_NO_OPTION
			);
			
			if (dialogResult == JOptionPane.NO_OPTION) {
				return;
			}
		}
		else {
			try {
				Files.createFile(jfc.getSelectedFile().toPath());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(
						frame, 
						"Can not create file: " + jfc.getSelectedFile().toPath().toString(), 
						"Warning",
						JOptionPane.WARNING_MESSAGE
					);
			}
		}
		
		model.getCurrentDocument().setFilePath(jfc.getSelectedFile().toPath());
		model.saveDocument(model.getCurrentDocument(), model.getCurrentDocument().getFilePath());
	}
	
	/**
	 * Creates CloseTabAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return CloseTabAction
	 */
	public AbstractAction CloseTab(String key,  String keyDescription) {
		return new CloseTabAction(key, keyDescription);
	}
	
	/**
	 * Closes opened tab.
	 * 
	 * @author Luka Grgić
	 */
	private class CloseTabAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;		
		
		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription description key
		 */
		public CloseTabAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			SingleDocumentModel sModel = model.getCurrentDocument();
			
			if (model.getCurrentDocument() != null) {
				if (sModel.isModified() && sModel.getFilePath() != null) {
					saveAndClose(sModel, sModel.getFilePath().getFileName().toString());
				} 
				else if (sModel.isModified() && sModel.getFilePath() == null) {
					saveAndClose(sModel, "Untitled");
				} 
				else {
					model.closeDocument(sModel);
				}
			}
		}

	};

	/**
	 * If closing tab is modified checks if user wants to
	 * save or not.
	 * 
	 * @param sModel model
	 * @param name name
	 * @return false if canceled, else true
	 */
	private boolean saveAndClose(SingleDocumentModel sModel, String name) {
		int dialogResult = JOptionPane.showConfirmDialog(
				frame, 
				"\"" + name + "\" not saved. Save before closing?",
				"Save", 
				JOptionPane.YES_NO_CANCEL_OPTION
		);

		if (dialogResult == JOptionPane.CANCEL_OPTION) {
			return false;
		} else if (dialogResult == JOptionPane.YES_OPTION) {
			saveFile();
		}	
		
		model.closeDocument(sModel);
		return true;
	}

	/**
	 * Creates ExitAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return ExitAction
	 */
	public AbstractAction exit(String key, String keyDescription) {
		return new ExitAction(key, keyDescription);
	}
	
	/**
	 * Exit application.
	 * 
	 * @author Luka Grgić
	 */
	private class ExitAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;		
		
		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription description key
		 */
		public ExitAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			closeAllTabs();
		}
		
	};
	
	/**
	 * Close all tab and exit application.
	 */
	public void closeAllTabs() {
		model.setSelectedIndex(model.getComponentCount()-1);
		
		for (int i = model.getNumberOfDocuments()-1; i >= 0; i--) {
			SingleDocumentModel sModel = model.getDocument(i);
			
			if (sModel.isModified() && sModel.getFilePath() != null) {
				if (!saveAndClose(sModel, sModel.getFilePath().getFileName().toString())) return;
			} 
			else if (sModel.isModified() && sModel.getFilePath() == null) {
				if (!saveAndClose(sModel, "Untitled")) return;
			} 
			
		}
		
		frame.dispose();
	}
	
	/**
	 * Creates StatisticalInfoAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return StatisticalInfoAction
	 */
	public AbstractAction statistics(String key, String keyDescription) {
		return new StatisticalInfoAction(key, keyDescription);
	}
	
	/**
	 * Shows model information.
	 * 
	 * @author Luka Grgić
	 */
	private class StatisticalInfoAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription description key
		 */
		public StatisticalInfoAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			if (model.getCurrentDocument() == null) return;
			
			Document doc = model.getCurrentDocument().getTextComponent().getDocument();
			String text = null;
			
			try {
				text = doc.getText(0, doc.getLength());
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			
			int characters = text.length();
			int nonBlankChars = text.replaceAll("\\s", "").length();
			int lines = text.split("\r\n|\r|\n").length;
			
			StringBuilder message = new StringBuilder()
					.append("Your document has ")
					.append(characters)
					.append(" characters, ")
					.append(nonBlankChars)
					.append(" non blank characters and ")
					.append(lines)
					.append(" lines.");
			
			JOptionPane.showMessageDialog(
					frame, 
					message.toString(), 
					"Document info", 
					JOptionPane.INFORMATION_MESSAGE
			);
		}
	};
	
	/**
	 * Creates CutSelectedPartAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return CutSelectedPartAction
	 */
	
	public AbstractAction cut(String key, String keyDescription) {
		return new CutSelectedPartAction(key, keyDescription);
	}
	
	/**
	 * Cuts selected text.
	 * 
	 * @author Luka Grgić
	 */
	private class CutSelectedPartAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription description key
		 */
		public CutSelectedPartAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F3"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			Selection selection = getSelection();
			if (selection == null) return;
			
			try {
				if (selection.length == 0) return;
				String text = selection.document.getText(selection.offset, selection.length);
				
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(new StringSelection(text), null);
				
				selection.document.remove(selection.offset, selection.length);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	/**
	 * Creates CopySelectedPartAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return CopySelectedPartAction
	 */
	public AbstractAction copy(String key, String keyDescription) {
		return new CopySelectedPartAction(key, keyDescription);
	}
	
	/**
	 * Copies selected text.
	 * 
	 * @author Luka Grgić
	 */
	private class CopySelectedPartAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription description key
		 */
		public CopySelectedPartAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F4"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Y);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			Selection selection = getSelection();
			if (selection == null) return;
			
			try {
				if (selection.length == 0) return;
				String text = selection.document.getText(selection.offset, selection.length);
				
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(new StringSelection(text), null);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	/**
	 * Creates PasteSelectedPartAction object.
	 * 
	 * @param key name key
	 * @param keyDescription description key
	 * @return PasteSelectedPartAction
	 */
	public AbstractAction paste(String key, String keyDescription) {
		return new PasteSelectedPartAction(key, keyDescription);
	}
	
	/**
	 * Pastes selected text.
	 * 
	 * @author Luka Grgić
	 */
	private class PasteSelectedPartAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructor.
		 * 
		 * @param key name key
		 * @param keyDescription description key
		 */
		public PasteSelectedPartAction(String key, String keyDescription) {
			doI18N(this, key, keyDescription);
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F5"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			Selection selection = getSelection();
			if (selection == null) return;
			
			try {
				if (selection.length > 0) {
					selection.document.remove(selection.offset, selection.length);
				}
				
				String data = (String) Toolkit.getDefaultToolkit()
		                .getSystemClipboard().getData(DataFlavor.stringFlavor);
				
				selection.document.insertString(selection.offset, data, null);
			} catch (BadLocationException | IOException | UnsupportedFlavorException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	
	/**
	 * Gets selection of current model.
	 * 
	 * @return selection of current model.
	 */
	private Selection getSelection() {
		if (model.getCurrentDocument() == null) return null;
		
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		Document document = editor.getDocument();
	
		int length = Math.abs(
				editor.getCaret().getDot()-editor.getCaret().getMark()
		);
		
		int offset = Math.min(
				editor.getCaret().getDot(), 
				editor.getCaret().getMark()
		);
	
		return new Selection(document, length, offset);
	}
	
	/**
	 * Class that holds selection info.
	 * 
	 * @author Luka Grgić
	 */
	private static class Selection {
		public Document document;
		public int length;
		public int offset;
		
		public Selection(Document document, int length, int offset) {
			this.document = document;
			this.length = length;
			this.offset = offset;
		}
	}
	
}
