package hr.fer.zemris.java.hw04.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public class SimpleHashtableTest {

	@Test
	public void testConstructor() {
		SimpleHashtable<String, Integer> table1 = new SimpleHashtable<>();
		SimpleHashtable<String, Integer> table2 = new SimpleHashtable<>(4);

		assertTrue(table1.isEmpty());
		assertTrue(table2.isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProvidingWrongCapacityToConstructor() {
		new SimpleHashtable<String, Integer>(0);
	}

	@Test
	public void testPut() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();

		table.put("Ivana", 2);
		table.put("Ante", 2);
		table.put("Jasna", null);

		assertEquals(3, table.size());
	}
	
	@Test
	public void testPutSameKey() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();

		table.put("Ivana", 2);
		table.put("Ante", 2);
		table.put("Ante", 99);

		assertEquals(2, table.size());
		assertEquals(99, (int) table.get("Ante"));
	}

	@Test(expected = NullPointerException.class)
	public void testPutNullKey() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();

		table.put(null, 2);
	}
	
	@Test
	public void testGet() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);

		table.put("Ivana", 2);
		table.put("Ante", 2);
		table.put("Jasna", null);

		assertEquals(2, (int) table.get("Ivana"));
		assertEquals(2, (int) table.get("Ante"));
		assertEquals(null, table.get("Jasna"));
		assertEquals(null, table.get(null));
		assertEquals(null, table.get("Madagaskar"));
		assertEquals(null, table.get(5));
	}
	
	@Test
	public void testSize() {
		SimpleHashtable<String, Integer> table1 = new SimpleHashtable<>();
		SimpleHashtable<String, Integer> table2 = new SimpleHashtable<>(4);
		
		table1.put("Ivana", 2);
		table1.put("Ante", 2);
		table1.put("Jasna", null);

		assertEquals(3, table1.size());
		assertEquals(0, table2.size());
	}
	
	@Test
	public void testContainsKey() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();

		table.put("Ivana", 2);
		table.put("Ante", 2);
		table.put("Jasna", null);

		assertTrue(table.containsKey("Ivana"));
		assertTrue(table.containsKey("Ante"));
		assertTrue(table.containsKey("Jasna"));
		
		assertFalse(table.containsKey(null));
		assertFalse(table.containsKey("Marko"));
		assertFalse(table.containsKey(9));	
	}
	
	@Test
	public void testContainsValue() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();

		table.put("Ivana", 2);
		table.put("Ante", 2);
		table.put("Jasna", null);

		assertTrue(table.containsValue(2));
		assertTrue(table.containsValue(null));
		
		assertFalse(table.containsKey(8));
		assertFalse(table.containsKey("Marko"));	
	}
	
	@Test
	public void testRemove() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();

		table.put("Ivana", 2);
		table.put("Ante", 2);
		table.put("Jasna", null);

		table.remove("Jasna");
		table.remove("Ivana");
		table.remove(null);
		table.remove("Traktor");
		table.remove(5);
		
		assertEquals(1, table.size());
		assertFalse(table.containsKey("Ivana"));
		assertFalse(table.containsKey("Jasna"));
	}

	@Test
	public void testIsEmpty() {
		SimpleHashtable<String, Integer> table1 = new SimpleHashtable<>();
		SimpleHashtable<String, Integer> table2 = new SimpleHashtable<>();

		table1.put("Ivana", 2);
		table1.put("Ante", 2);
		table1.put("Jasna", null);

		assertFalse(table1.isEmpty());
		assertTrue(table2.isEmpty());
	}
	
	@Test
	public void testClear() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		
		table.put("Ivana", 2);
		table.put("Ante", 2);
		table.put("Jasna", null);

		table.clear();
		
		assertTrue(table.isEmpty());
		assertFalse(table.containsKey("Ivana"));
		assertFalse(table.containsKey("Jasna"));
		assertFalse(table.containsKey("Ante"));
	}
	
	@Test
	public void testHasNext() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		
		table.put("Ivana", 2);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iterator = table.iterator();
		
		assertTrue(iterator.hasNext());
		iterator.next();
		assertFalse(iterator.hasNext());
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void testHasNextAfterModification() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iterator = table.iterator();
		
		table.put("Ivana", 2);
		iterator.hasNext();
	}
	
	@Test
	public void testNext() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		
		table.put("Ivana", 2);
		table.put("Jasna", null);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iterator = table.iterator();
		
		assertEquals("Ivana", iterator.next().getKey());
		assertEquals("Jasna", iterator.next().getKey());
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void testNextAfterModification() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iterator = table.iterator();
		
		table.put("Ivana", 2);
		iterator.next();
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testNextWithNoElements() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iterator = table.iterator();
		
		iterator.next();
	}

	@Test
	public void testIteratorRemove() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		
		table.put("Ivana", 2);
		table.put("Jasna", null);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iterator = table.iterator();
		iterator.next();
		iterator.remove();
		
		assertEquals(1, table.size());
		assertTrue(table.containsKey("Jasna"));
		assertFalse(table.containsKey("Ivana"));
	}
	
	@Test(expected=IllegalStateException.class)
	public void testIteratorRemoveBeforeCallingNext() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		
		table.put("Ivana", 2);
		table.put("Jasna", null);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iterator = table.iterator();
		iterator.remove();
	}
	
	@Test(expected=IllegalStateException.class)
	public void testIteratorRemoveCallingTwoTimesInaRow() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		
		table.put("Ivana", 2);
		table.put("Jasna", null);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iterator = table.iterator();
		iterator.remove();
		iterator.remove();
	}

	@Test(expected=ConcurrentModificationException.class)
	public void testIteratorRemoveAfterModification() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		
		table.put("Ivana", 2);
		table.put("Jasna", null);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iterator = table.iterator();
		
		iterator.next();
		table.put("Jasna", 5);
		iterator.remove();
	}
	
}
