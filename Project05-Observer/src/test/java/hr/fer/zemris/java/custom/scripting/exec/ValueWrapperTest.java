package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ValueWrapperTest {

	@Test
	public void testConstructor() {
		ValueWrapper value1 = new ValueWrapper(null);
		ValueWrapper value2 = new ValueWrapper(Integer.valueOf(2));
		ValueWrapper value3 = new ValueWrapper("Traktor");
		ValueWrapper value4 = new ValueWrapper(Double.valueOf(1.2));
		
		assertEquals(null, value1.getValue());
		assertEquals(Integer.valueOf(2), value2.getValue());
		assertEquals("Traktor", value3.getValue());
		assertEquals(Double.valueOf(1.2), value4.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void testWrongValueProvidedInConstructor() {
		new ValueWrapper(new StringBuilder());
	}
	
	@Test(expected=RuntimeException.class)
	public void testInvalidString() {
		ValueWrapper value1 = new ValueWrapper("Ankica");
		ValueWrapper value2 = new ValueWrapper(Integer.valueOf(1));
		
		value1.add(value2.getValue());
	}
	
	@Test
	public void testNullAddNull() {
		ValueWrapper value1 = new ValueWrapper(null);
		ValueWrapper value2 = new ValueWrapper(null);
		
		value1.add(value2.getValue());
		
		assertEquals(Integer.valueOf(0), value1.getValue());
		assertEquals(null, value2.getValue());
	}
	
	@Test
	public void testNullSubstractInteger() {
		ValueWrapper value1 = new ValueWrapper(null);
		ValueWrapper value2 = new ValueWrapper(Integer.valueOf(3));
		
		value1.subtract(value2.getValue());
		
		assertEquals(Integer.valueOf(-3), value1.getValue());
		assertEquals(Integer.valueOf(3), value2.getValue());
	}
	
	@Test
	public void testNullMultipyDouble() {
		ValueWrapper value1 = new ValueWrapper(null);
		ValueWrapper value2 = new ValueWrapper(Double.valueOf(3));
		
		value1.multiply(value2.getValue());
		
		assertEquals(Double.valueOf(0), value1.getValue());
		assertEquals(Double.valueOf(3), value2.getValue());
	}
	
	@Test
	public void testNullDivideStringInteger() {
		ValueWrapper value1 = new ValueWrapper(null);
		ValueWrapper value2 = new ValueWrapper("2");
		
		value1.divide(value2.getValue());
		
		assertEquals(Integer.valueOf(0), value1.getValue());
		assertEquals("2", value2.getValue());
	}
	
	@Test
	public void testNullAddStringDouble() {
		ValueWrapper value1 = new ValueWrapper(null);
		ValueWrapper value2 = new ValueWrapper("2.2");
		
		value1.add(value2.getValue());
		
		assertEquals(Double.valueOf(2.2), value1.getValue());
		assertEquals("2.2", value2.getValue());
	}
	
	@Test
	public void testIntegerSubtractNull() {
		ValueWrapper value1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper value2 = new ValueWrapper(null);
		
		value1.subtract(value2.getValue());
		
		assertEquals(Integer.valueOf(3), value1.getValue());
		assertEquals(null, value2.getValue());
	}
	
	@Test
	public void testIntegerMultiplyInteger() {
		ValueWrapper value1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper value2 = new ValueWrapper(Integer.valueOf(3));
		
		value1.multiply(value2.getValue());
		
		assertEquals(Integer.valueOf(9), value1.getValue());
		assertEquals(Integer.valueOf(3), value2.getValue());
	}
	
	@Test
	public void testIntegerDivideDouble() {
		ValueWrapper value1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper value2 = new ValueWrapper(Double.valueOf(3));
		
		value1.divide(value2.getValue());
		
		assertEquals(Double.valueOf(1), value1.getValue());
		assertEquals(Double.valueOf(3), value2.getValue());
	}
	
	@Test
	public void testIntegerAddStringInteger() {
		ValueWrapper value1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper value2 = new ValueWrapper("2");
		
		value1.add(value2.getValue());
		
		assertEquals(Integer.valueOf(5), value1.getValue());
		assertEquals("2", value2.getValue());
	}
	
	@Test
	public void testIntegerAddStringDouble() {
		ValueWrapper value1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper value2 = new ValueWrapper("2.2");
		
		value1.add(value2.getValue());
		
		assertEquals(Double.valueOf(5.2), value1.getValue());
		assertEquals("2.2", value2.getValue());
	}
	
	@Test
	public void testDoubleAddNull() {
		ValueWrapper value1 = new ValueWrapper(Double.valueOf(3.2));
		ValueWrapper value2 = new ValueWrapper(null);
		
		value1.add(value2.getValue());
		
		assertEquals(Double.valueOf(3.2), value1.getValue());
		assertEquals(null, value2.getValue());
	}
	
	@Test
	public void testDoubleAddInteger() {
		ValueWrapper value1 = new ValueWrapper(Double.valueOf(3.2));
		ValueWrapper value2 = new ValueWrapper(Integer.valueOf(3));
		
		value1.add(value2.getValue());
		
		assertEquals(Double.valueOf(6.2), value1.getValue());
		assertEquals(Integer.valueOf(3), value2.getValue());
	}
	
	@Test
	public void testDoubleAddDouble() {
		ValueWrapper value1 = new ValueWrapper(Double.valueOf(3.2));
		ValueWrapper value2 = new ValueWrapper(Double.valueOf(3));
		
		value1.add(value2.getValue());
		
		assertEquals(Double.valueOf(6.2), value1.getValue());
		assertEquals(Double.valueOf(3), value2.getValue());
	}
	
	@Test
	public void testDoubleAddStringInteger() {
		ValueWrapper value1 = new ValueWrapper(Double.valueOf(3.2));
		ValueWrapper value2 = new ValueWrapper("2");
		
		value1.add(value2.getValue());
		
		assertEquals(Double.valueOf(5.2), value1.getValue());
		assertEquals("2", value2.getValue());
	}
	
	@Test
	public void testDoubleAddStringDouble() {
		ValueWrapper value1 = new ValueWrapper(Double.valueOf(3.2));
		ValueWrapper value2 = new ValueWrapper("2.2");
		
		value1.add(value2.getValue());
		
		assertEquals(Double.valueOf(5.4), value1.getValue());
		assertEquals("2.2", value2.getValue());
	}
	
	@Test
	public void testStringIntegerAddNull() {
		ValueWrapper value1 = new ValueWrapper("2");
		ValueWrapper value2 = new ValueWrapper(null);
		
		value1.add(value2.getValue());
		
		assertEquals(Integer.valueOf(2), value1.getValue());
		assertEquals(null, value2.getValue());
	}
	
	@Test
	public void testStringIntegerAddInteger() {
		ValueWrapper value1 = new ValueWrapper("2");
		ValueWrapper value2 = new ValueWrapper(Integer.valueOf(3));
		
		value1.add(value2.getValue());
		
		assertEquals(Integer.valueOf(5), value1.getValue());
		assertEquals(Integer.valueOf(3), value2.getValue());
	}
	
	@Test
	public void testStringIntegerAddDouble() {
		ValueWrapper value1 = new ValueWrapper("2");
		ValueWrapper value2 = new ValueWrapper(Double.valueOf(3));
		
		value1.add(value2.getValue());
		
		assertEquals(Double.valueOf(5), value1.getValue());
		assertEquals(Double.valueOf(3), value2.getValue());
	}
	
	@Test
	public void testStringIntegerAddStringInteger() {
		ValueWrapper value1 = new ValueWrapper("2");
		ValueWrapper value2 = new ValueWrapper("2");
		
		value1.add(value2.getValue());
		
		assertEquals(Integer.valueOf(4), value1.getValue());
		assertEquals("2", value2.getValue());
	}
	
	@Test
	public void testStringIntegerAddStringDouble() {
		ValueWrapper value1 = new ValueWrapper("2");
		ValueWrapper value2 = new ValueWrapper("2.2");
		
		value1.add(value2.getValue());
		
		assertEquals(Double.valueOf(4.2), value1.getValue());
		assertEquals("2.2", value2.getValue());
	}
	
	@Test
	public void testStringDoubleAddNull() {
		ValueWrapper value1 = new ValueWrapper("2.2");
		ValueWrapper value2 = new ValueWrapper(null);
		
		value1.add(value2.getValue());
		
		assertEquals(Double.valueOf(2.2), value1.getValue());
		assertEquals(null, value2.getValue());
	}
	
	@Test
	public void testStringDoubleAddInteger() {
		ValueWrapper value1 = new ValueWrapper("1.2E1");
		ValueWrapper value2 = new ValueWrapper(Integer.valueOf(3));
		
		value1.add(value2.getValue());
		
		assertEquals(Double.valueOf(15), value1.getValue());
		assertEquals(Integer.valueOf(3), value2.getValue());
	}
	
	@Test
	public void testStringDoubleAddDouble() {
		ValueWrapper value1 = new ValueWrapper("2.2");
		ValueWrapper value2 = new ValueWrapper(Double.valueOf(3));
		
		value1.add(value2.getValue());
		
		assertEquals(Double.valueOf(5.2), value1.getValue());
		assertEquals(Double.valueOf(3), value2.getValue());
	}
	
	@Test
	public void testStringDoubleAddStringInteger() {
		ValueWrapper value1 = new ValueWrapper("2.2");
		ValueWrapper value2 = new ValueWrapper("2");
		
		value1.add(value2.getValue());
		
		assertEquals(Double.valueOf(4.2), value1.getValue());
		assertEquals("2", value2.getValue());
	}
	
	@Test
	public void testStringDoubleAddStringDouble() {
		ValueWrapper value1 = new ValueWrapper("2.2");
		ValueWrapper value2 = new ValueWrapper("2.2");
		
		value1.add(value2.getValue());
		
		assertEquals(Double.valueOf(4.4), value1.getValue());
		assertEquals("2.2", value2.getValue());
	}

	@Test
	public void testNumCompare() {
		ValueWrapper nullValue = new ValueWrapper(null);
		ValueWrapper integerValue = new ValueWrapper(Integer.valueOf(2));
		ValueWrapper doubleValue = new ValueWrapper(Double.valueOf(2.2));
		ValueWrapper stringIntegerValue = new ValueWrapper("3");
		ValueWrapper stringDoubleValue = new ValueWrapper("2.2");
		
		assertEquals(0, nullValue.numCompare(nullValue.getValue()));
		assertEquals(0, doubleValue.numCompare(stringDoubleValue.getValue()));
		assertEquals(0, stringDoubleValue.numCompare(stringDoubleValue.getValue()));
		
		assertEquals(1, stringIntegerValue.numCompare(stringDoubleValue.getValue()));
		assertEquals(1, stringDoubleValue.numCompare(integerValue.getValue()));
		assertEquals(1, doubleValue.numCompare(nullValue.getValue()));
		
		assertEquals(-1, doubleValue.numCompare(stringIntegerValue.getValue()));
		assertEquals(-1, nullValue.numCompare(stringDoubleValue.getValue()));
		assertEquals(-1, integerValue.numCompare(stringIntegerValue.getValue()));
		
		assertEquals(null, nullValue.getValue());
		assertEquals(Integer.valueOf(2), integerValue.getValue());
		assertEquals(Double.valueOf(2.2), doubleValue.getValue());
		assertEquals("3", stringIntegerValue.getValue());
		assertEquals("2.2", stringDoubleValue.getValue());
	}
	
}
