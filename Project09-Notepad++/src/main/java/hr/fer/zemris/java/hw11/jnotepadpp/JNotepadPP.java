package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Program that represents simple text file editor called JNotepad++. 
 * JNotepad++ allows user to work with multiple documents at the same time. 
 * For this is used JTabbedPane component. Application provides the 
 * following functionality to the user:
 * 		• creating a new blank document,
 * 		• opening existing document,
 * 		• saving document,
 * 		• saving-as document
 * 		• close document shown it a tab (and remove that tab)
 * 		• cut/copy/paste text,
 * 		• showing statistical info,
 * 		• exiting application
 * 		• changing selected text letter case
 * 		• sorting selected text lines
 *  	• changing application language
 *  
 * @author Luka Grgić
 */
public class JNotepadPP extends JFrame{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Status bar
	 */
	private static StatusBar statusBar;
	private MultipleDocumentModel model;
	/**
	 * Contains all actions.
	 */
	private Actions actions;
	/**
	 * Localization provider
	 */
	private FormLocalizationProvider flp;
	
	/**
	 * Sets frame.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);
		setTitle("JNotepadPP");
		
		initGUI();
	}

	/**
	 * Adds component to frame.
	 */
	private void initGUI() {
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		model = new DefaultMultipleDocumentModel(this);
		
		statusBar = new StatusBar(" ", model);
		statusBar.setBorder(new MatteBorder(2, 0, 0, 0, Color.GRAY));
		
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				statusBar.repaint();
			}
		}, 0, 1000);
		
		
		actions = new Actions((DefaultMultipleDocumentModel)model, this, flp);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				actions.closeAllTabs();
			}
		});
		
		JPanel child = new JPanel(new BorderLayout());
		child.add((DefaultMultipleDocumentModel)model, BorderLayout.CENTER);
		child.add(statusBar, BorderLayout.PAGE_END);
		
		JPanel parent = new JPanel(new BorderLayout());
		parent.add(child, BorderLayout.CENTER);
	
		createMenus();
		createToolbars();
		
		getContentPane().add(parent, BorderLayout.CENTER);
	}

	/**
	 * Creates all menus.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu(actions.fileMenu("File", "FileDes"));
		menuBar.add(fileMenu);
		
		fileMenu.add(new JMenuItem(actions.createNew("NewFile", "NewFileDes")));
		fileMenu.add(new JMenuItem(actions.open("Open", "OpenDes")));
		fileMenu.add(new JMenuItem(actions.save("Save", "SaveDes")));
		fileMenu.add(new JMenuItem(actions.saveAs("SaveAs", "SaveAsDes")));
		fileMenu.add(new JMenuItem(actions.CloseTab("CloseTab", "CloseTabDes")));
		fileMenu.add(new JMenuItem(actions.exit("Exit", "ExitDes")));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(actions.statistics("Info", "InfoDes")));
		
		JMenu editMenu = new JMenu(actions.editMenu("Edit", "EditDes"));
		menuBar.add(editMenu);
		
		editMenu.add(new JMenuItem(actions.cut("Cut", "CutDes")));
		editMenu.add(new JMenuItem(actions.copy("Copy", "CopyDes")));
		editMenu.add(new JMenuItem(actions.paste("Paste", "PasteDes")));
		
		JMenu languegesMenu = new JMenu(actions.languagesMenu("Language", "LanguageDes"));
		menuBar.add(languegesMenu);
		
		JMenuItem hr = new JMenuItem("hr");
		JMenuItem en = new JMenuItem("en");
		JMenuItem de = new JMenuItem("de");
		
		hr.addActionListener(l -> {
			LocalizationProvider.getInstance().setLanguage("hr");
		});
		en.addActionListener(l -> {
			LocalizationProvider.getInstance().setLanguage("en");
		});
		de.addActionListener(l -> {
			LocalizationProvider.getInstance().setLanguage("de");
		});
		
		languegesMenu.add(hr);
		languegesMenu.add(en);
		languegesMenu.add(de);
		
		JMenu toolsMenu = new JMenu(actions.toolsMenu("Tools", "ToolsDes"));
		JMenu changeCaseMenu = new JMenu(actions.changeCaseMenu("ChangeCase", "ChangeCaseDes"));
		toolsMenu.add(changeCaseMenu);
		
		Action toggleAction = actions.toggle("Toggle", "ToggleDes");
		toggleAction.setEnabled(false);
		Action toUpperAction = actions.toUpper("Upper", "UpperDes");
		toUpperAction.setEnabled(false);
		Action toLowerAction = actions.toLower("Lower", "LowerDes");
		toLowerAction.setEnabled(false);
		
		addListener(toggleAction, toUpperAction, toLowerAction);
		
		JMenuItem upper = new JMenuItem(toUpperAction);
		JMenuItem lower = new JMenuItem(toLowerAction);
		JMenuItem change = new JMenuItem(toggleAction);
		changeCaseMenu.add(upper);
		changeCaseMenu.add(lower);
		changeCaseMenu.add(change);
		
		menuBar.add(toolsMenu);
		
		JMenu sortMenu = new JMenu(actions.sortMenu("Sort", "SortDes"));
		toolsMenu.add(sortMenu);
		
		JMenuItem ascending = new JMenuItem(actions.ascending("Ascending", "AscendingDes"));
		JMenuItem descending = new JMenuItem(actions.descending("Descending", "DescendingDes"));
		JMenuItem unique = new JMenuItem(actions.unique("Unique", "UniqueDes"));
		sortMenu.add(ascending);
		sortMenu.add(descending);
		sortMenu.add(unique);
		
		this.setJMenuBar(menuBar);
	}
	
	/**
	 * Adds listener to model to notify toggleAction, toUpperAction
	 * and toLowerAction when model has changed.
	 * 
	 * @param toggleAction toggleAction
	 * @param toUpperAction toUpperAction
	 * @param toLowerAction toLowerAction
	 */
	private void addListener(Action toggleAction, Action toUpperAction, Action toLowerAction) {
		
		/**
		 * Listens for caret changes and if there is no
		 * selected text disables actions.
		 */
		final CaretListener cListener = new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent event) {
				int len = model.getCurrentDocument().getTextComponent().getCaret().getDot()-
						model.getCurrentDocument().getTextComponent().getCaret().getMark();
				
				toggleAction.setEnabled(len!=0);
				toUpperAction.setEnabled(len!=0);
				toLowerAction.setEnabled(len!=0);
			}
		};
		
		model.addMultipleDocumentListener(new MultipleDocumentListener() {
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
					
					int len = currentModel.getTextComponent().getCaret().getDot()-
							currentModel.getTextComponent().getCaret().getMark();
					
					toggleAction.setEnabled(len!=0);
					toUpperAction.setEnabled(len!=0);
					toLowerAction.setEnabled(len!=0);
				} else {
					toggleAction.setEnabled(false);
					toUpperAction.setEnabled(false);
					toLowerAction.setEnabled(false);
				}
			}
		});
	}
	
	/**
	 * Creates toolbar.
	 */
	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Tools");
		toolBar.setFloatable(true);
		
		toolBar.add(new JButton(actions.createNew("NewFile", "NewFileDes")));
		toolBar.add(new JButton(actions.open("Open", "OpenDes")));
		toolBar.add(new JButton(actions.save("Save", "SaveDes")));
		toolBar.add(new JButton(actions.saveAs("SaveAs", "SaveAsDes")));
		toolBar.add(new JButton(actions.CloseTab("CloseTab", "CloseTabDes")));
		toolBar.add(new JButton(actions.exit("Exit", "ExitDes")));
		toolBar.addSeparator();
		toolBar.add(new JButton(actions.statistics("Info", "InfoDes")));
		toolBar.addSeparator();
		toolBar.add(new JButton(actions.cut("Cut", "CutDes")));
		toolBar.add(new JButton(actions.copy("Copy", "CopyDes")));
		toolBar.add(new JButton(actions.paste("Paste", "PasteDes")));
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	
	/**
	 * First method executed when program starts.
	 * 
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}

}
