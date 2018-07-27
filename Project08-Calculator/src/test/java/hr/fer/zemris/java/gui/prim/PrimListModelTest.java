package hr.fer.zemris.java.gui.prim;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hr.fer.zemris.java.gui.prim.PrimDemo.PrimListModel;

public class PrimListModelTest {

	@Test
	public void testDefaultState() {
		PrimDemo.PrimListModel prim = new PrimListModel();
		
		assertEquals(1, prim.getSize());
		assertEquals(1, prim.getElementAt(0).intValue());
	}
	
	@Test
	public void testNext() {
		PrimDemo.PrimListModel prim = new PrimListModel();
		prim.next();
		prim.next();
		
		assertEquals(3, prim.getSize());
		assertEquals(3, prim.getElementAt(2).intValue());
	}
	
}
