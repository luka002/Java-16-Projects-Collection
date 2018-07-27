package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Program that creates two lists and adds list 
 * model to both of them and adds a button. When 
 * button is pressed next prim number is calculated
 * and added to the model.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class PrimDemo extends JFrame {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor sets GUI;
	 */
	public PrimDemo() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Model");
		setLocation(100, 100);
		setSize(500, 500);
		initGUI();
	}
	
	/**
	 * Model that calculates prim numbers.
	 * 
	 * @author Luka Grgić
	 * @version 1.0
	 */
	static class PrimListModel implements ListModel<Integer> {
		
		/** Prim numbers */
		private List<Integer> elementi;
		/** Listeners */
		private List<ListDataListener> promatraci;
		/** Last prim */
		private int lastPrim;
		
		/**
		 * Constructor.
		 */
		public PrimListModel() {
			super();
			this.elementi = new ArrayList<>();
			elementi.add(1);
			this.promatraci = new ArrayList<>();
			this.lastPrim = 1;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void addListDataListener(ListDataListener l) {
			promatraci.add(l);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void removeListDataListener(ListDataListener l) {
			promatraci.remove(l);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public int getSize() {
			return elementi.size();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Integer getElementAt(int index) {
			return elementi.get(index);
		}
		
		/**
		 * Adds next prim number to the model and notifies
		 * listeners.
		 */
		public void next() {
			int pos = elementi.size();
			elementi.add(nextPrim());
			
			ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
			for(ListDataListener l : promatraci) {
				l.intervalAdded(event);
			}
		}
		
		/**
		 * Calculates next prim numbers.
		 * @return next prim number.
		 */
		private Integer nextPrim() {
			boolean isPrime = true;

			while (true) {
				lastPrim++;
				
				for (int i = 2, n = (int) Math.sqrt(lastPrim); i <= n; i++) {
					if ((lastPrim % i) == 0) {
						isPrime = false;
						break;
					}
				}
				if (isPrime) break;
				isPrime = true;
			}

			return lastPrim;
		}
		
	}
	
	/**
	 * Adds lists and button to content pane.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel model = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		
		JButton next = new JButton("Next");
		next.addActionListener(a -> model.next());
		
		JPanel central = new JPanel(new GridLayout(1, 0));
		central.add(new JScrollPane(list1));
		central.add(new JScrollPane(list2));
		
		cp.add(central, BorderLayout.CENTER);
		cp.add(next, BorderLayout.PAGE_END);
	}

	/**
	 * Method that is first executed when program starts.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}

}
