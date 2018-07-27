package hr.fer.zemris.java.gui.layouts;

import static org.junit.Assert.assertEquals;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Test;

public class CalcLayoutTest {

	@Test(expected=CalcLayoutException.class)
	public void testRowBelowBounds() {
		new CalcLayout().addLayoutComponent(new JButton(), new RCPosition(0, 1));
	}
	
	@Test(expected=CalcLayoutException.class)
	public void testRowAbowBounds() {
		new CalcLayout().addLayoutComponent(new JButton(), new RCPosition(6, 1));
	}
	
	@Test(expected=CalcLayoutException.class)
	public void testColumnBelowBounds() {
		new CalcLayout().addLayoutComponent(new JButton(), new RCPosition(5, 0));
	}
	
	@Test(expected=CalcLayoutException.class)
	public void testColumnAbowBounds() {
		new CalcLayout().addLayoutComponent(new JButton(), new RCPosition(2, 8));
	}
	
	@Test(expected=CalcLayoutException.class)
	public void testFirstRowAndColumn2() {
		new CalcLayout().addLayoutComponent(new JButton(), new RCPosition(1, 2));
	}
	
	@Test(expected=CalcLayoutException.class)
	public void testFirstRowAndColumn5() {
		new CalcLayout().addLayoutComponent(new JButton(), new RCPosition(1, 5));
	}
	
	@Test(expected=CalcLayoutException.class)
	public void testAddingTwiceDifferentComponentOnSamePosition() {
		CalcLayout l = new CalcLayout(15);
		
		l.addLayoutComponent(new JButton(), new RCPosition(3,1));
		l.addLayoutComponent(new JButton(), new RCPosition(3,1));
	}
	
	@Test
	public void testAddingTwiceSameComponentOnSamePosition() {
		CalcLayout l = new CalcLayout(15);
		JButton b = new JButton();
		
		l.addLayoutComponent(b, new RCPosition(3,1));
		l.addLayoutComponent(b, new RCPosition(3,1));
		
		assertEquals(1, l.getComponents().size());
	}
	
	@Test
	public void testCorrectRowAndColumn() {
		CalcLayout l = new CalcLayout(15);
		
		l.addLayoutComponent(new JButton(), new RCPosition(1,1));
		l.addLayoutComponent(new JButton(), new RCPosition(1,6));
		l.addLayoutComponent(new JButton(), new RCPosition(1,7));
		
		for (int i = 2; i <= 5; i++) {
			for (int j = 1; j <= 7; j++) {
				l.addLayoutComponent(new JButton(), new RCPosition(i, j));
			}
		}
		
		assertEquals(31, l.getComponents().size());
	}
	
	@Test
	public void testAddingConstraitsAsString() {
		CalcLayout l = new CalcLayout(15);
		l.addLayoutComponent(new JButton(), "2,2");
		
		assertEquals(1, l.getComponents().size());
	}
	
	@Test(expected=CalcLayoutException.class)
	public void testAddingConstraitsAsIncorrectString() {
		CalcLayout l = new CalcLayout(15);
		l.addLayoutComponent(new JButton(), "2, 2");
	}
	
	@Test
	public void testPreferredSize1() {
		JPanel p = new JPanel(new CalcLayout(2));
		
		JLabel l1 = new JLabel(""); 
		l1.setPreferredSize(new Dimension(10,30));
		
		JLabel l2 = new JLabel(""); 
		l2.setPreferredSize(new Dimension(20,15));
		
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		
		Dimension dim = p.getPreferredSize();
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
	
	@Test
	public void testPreferredSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
		
		JLabel l1 = new JLabel(""); 
		l1.setPreferredSize(new Dimension(108, 15));
		
		JLabel l2 = new JLabel(""); 
		l2.setPreferredSize(new Dimension(16 ,30));
		
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		
		Dimension dim = p.getPreferredSize();
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
	
}
