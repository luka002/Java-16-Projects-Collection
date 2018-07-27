package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ObjectMultistackTest {

	@Test
	public void testPush() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.push("year", new ValueWrapper("3"));
		multistack.push("year", new ValueWrapper("3.2"));
		multistack.push("age", new ValueWrapper(Double.valueOf(2.2)));
		
		assertFalse(multistack.isEmpty("year"));
		assertFalse(multistack.isEmpty("age"));
		assertTrue(multistack.isEmpty("month"));
	}
	
	@Test(expected=NullPointerException.class)
	public void testPushNull() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.push("year", null);
	}
	
	@Test
	public void testPop() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.push("year", new ValueWrapper("3"));
		multistack.push("year", new ValueWrapper("3.2"));
		multistack.push("year", new ValueWrapper(Double.valueOf(2.2)));
	
		assertEquals(Double.valueOf(2.2), multistack.pop("year").getValue());
		assertEquals("3.2", multistack.pop("year").getValue());
		assertEquals("3", multistack.pop("year").getValue());
		
		assertTrue(multistack.isEmpty("year"));
	}
	
	@Test(expected=EmptyStackException.class)
	public void testPopEmptyStack() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.push("year", new ValueWrapper("3"));
		
		multistack.pop("year");
		multistack.pop("year");
	}
	
	@Test
	public void testPeek() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.push("year", new ValueWrapper("3"));
		multistack.push("year", new ValueWrapper("3.2"));
	
		assertEquals("3.2", multistack.peek("year").getValue());
		
		assertFalse(multistack.isEmpty("year"));
	}
	
	@Test(expected=EmptyStackException.class)
	public void testPeekEmptyStack() {
		ObjectMultistack multistack = new ObjectMultistack();
	
		multistack.peek("year");	
	}
	
	@Test
	public void testIsEmpty() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.push("year", new ValueWrapper("3"));
		
		assertEquals(false, multistack.isEmpty("year"));
		assertEquals(true, multistack.isEmpty("age"));
	}	
	
}
