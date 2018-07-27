package hr.fer.zemris.java.custom.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hr.fer.zemris.java.custom.collections.Dictionary;

public class DictionaryTest {

	@Test
	public void dictionaryIsEmpty() {
		Dictionary dictionary = new Dictionary();
		
		assertTrue(dictionary.isEmpty());
		assertEquals(0, dictionary.size());
	}
	
	@Test
	public void putDifferentKeys() {
		Dictionary dictionary = new Dictionary();
		
		dictionary.put("Auto", "Porsche");
		dictionary.put("Kamion", "Iveco");
		dictionary.put("Formula", "F1");
		dictionary.put("Motor", null);
		
		assertTrue(!dictionary.isEmpty());
		assertEquals(4, dictionary.size());
		
		assertEquals("Porsche", dictionary.get(new String("Auto")));
		assertEquals("Iveco", dictionary.get(new String("Kamion")));
		assertEquals("F1", dictionary.get(new String("Formula")));
		assertEquals(null, dictionary.get(new String("Motor")));
	}
	
	@Test
	public void putExistingKeys() {
		Dictionary dictionary = new Dictionary();
		
		dictionary.put("Auto", "Porsche");
		dictionary.put("Kamion", "Iveco");
		dictionary.put("Auto", "Audi");
		
		assertTrue(!dictionary.isEmpty());
		assertEquals(2, dictionary.size());
		
		assertEquals("Audi", dictionary.get(new String("Auto")));
		assertEquals("Iveco", dictionary.get(new String("Kamion")));
	}
	
	@Test(expected=NullPointerException.class)
	public void putNullKeys() {
		Dictionary dictionary = new Dictionary();
		
		dictionary.put(null, "Porsche");
	}
	
	@Test
	public void getValueForExistingKey() {
		Dictionary dictionary = new Dictionary();
		
		dictionary.put("Auto", "Porsche");
		dictionary.put("Kamion", "Iveco");

		assertEquals("Porsche", dictionary.get(new String("Auto")));
		assertEquals("Iveco", dictionary.get(new String("Kamion")));
	}
	
	@Test
	public void getValueForNonExistingKey() {
		Dictionary dictionary = new Dictionary();
		
		dictionary.put("Auto", "Porsche");
		dictionary.put("Kamion", "Iveco");

		assertEquals(null, dictionary.get(new String("Samobor")));
	}
	
	@Test
	public void testClear() {
		Dictionary dictionary = new Dictionary();
		
		dictionary.put("Auto", "Porsche");
		dictionary.put("Kamion", "Iveco");

		dictionary.clear();
		
		assertEquals(0, dictionary.size());
		assertTrue(dictionary.isEmpty());
	}
	
}
