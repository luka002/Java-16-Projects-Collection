package hr.fer.zemris.java.custom.collections;

import org.junit.Test;
import org.junit.Assert;

public class ArrayIndexedCollectionTest {

	@Test
	public void usingDefaultConstructor() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		
		Assert.assertEquals(0, array.size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void giveConstructorWrongInitialCapacity() {
		new ArrayIndexedCollection(0);
	}
	
	@Test
	public void giveConstructorRightInitialCapacity() {
		new ArrayIndexedCollection(1);
	}
	
	@Test(expected=NullPointerException.class)
	public void giveConstructorEmptyCollection() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		new ArrayIndexedCollection(list);
	}
	
	@Test
	public void giveConstructorFilledCollection() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		ArrayIndexedCollection newArray = new ArrayIndexedCollection(array);
		Assert.assertEquals(3, newArray.size());
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getObjectFromIndexAboveUpperThreshold() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		array.get(array.size());
	}
	
	@Test
	public void getObjectFromIndexOnUpperThreshold() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		Assert.assertEquals(new String("Vjetrenjača"), array.get(array.size()-1));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getObjectFromIndexBellowLowerThreshold() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		array.get(-1);
	}
	
	@Test
	public void getObjectFromIndexOnLowerThreshold() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		Assert.assertEquals(new String("Kamion"), array.get(0));
	}
	
	
	@Test
	public void getIndexOfNonExistingObject() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		Assert.assertEquals(-1, array.indexOf(new String("Buzdovan")));
	}
	
	@Test
	public void getIndexOfExistingObject() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		Assert.assertEquals(2, array.indexOf(new String("Vjetrenjača")));
	}
	
	@Test
	public void getIndexOfNull() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		Assert.assertEquals(-1, array.indexOf(null));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void insertObjectAboveUpperThreshold() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		array.insert(new String("Rambo"), array.size()+1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void insertObjectBellowLowerThreshold() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		array.insert(new String("Rambo"), -1);
	}
	
	@Test
	public void insertObjectOnUpperThresholdWithCapacityNotFull() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		array.insert(new String("Rambo"), array.size());
		
		Assert.assertEquals(new String("Rambo"), array.get(3));
		Assert.assertEquals(4, array.size());
	}
	
	@Test
	public void insertObjectOnLowerThresholdWithCapacityNotFull() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		array.insert(new String("Rambo"), 0);
		
		Assert.assertEquals(new String("Rambo"), array.get(0));
		Assert.assertEquals(4, array.size());
	}
	
	@Test
	public void insertObjectOnUpperThresholdWithCapacityFull() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(3);
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		array.insert(new String("Rambo"), array.size());
		
		Assert.assertEquals(new String("Rambo"), array.get(3));
		Assert.assertEquals(4, array.size());
	}
	
	@Test
	public void insertObjectOnLowerThresholdWithCapacityFull() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(3);
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		array.insert(new String("Rambo"), 0);
		
		Assert.assertEquals(new String("Rambo"), array.get(0));
		Assert.assertEquals(4, array.size());
	}
	
	@Test
	public void calculatesCorrectSize() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		Assert.assertEquals(3, array.size());
	}
	
	@Test(expected=NullPointerException.class)
	public void addingNull() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(3);
		array.add(null);
	}
	
	@Test
	public void addingValidValues() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(3);
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		Assert.assertEquals(new String("Kamion"), array.get(0));
		Assert.assertEquals(new String("Traktor"), array.get(1));
		Assert.assertEquals(new String("Vjetrenjača"), array.get(2));
	}
	
	@Test
	public void isContainingNonExistingObject() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(3);
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		Assert.assertFalse(array.contains(new String("Jackie Chan")));
	}
	
	@Test
	public void isContainingExistingObject() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(3);
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		Assert.assertTrue(array.contains(new String("Traktor")));
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void removeObjectFromWrongIndex() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(3);
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		array.remove(7);
	}
	
	@Test
	public void removeObjectFromCorrectIndex() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(3);
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		array.remove(1);
		
		Assert.assertEquals(new String("Kamion"), array.get(0));
		Assert.assertEquals(new String("Vjetrenjača"), array.get(1));
		Assert.assertEquals(2, array.size());
	}
	
	@Test
	public void removeNull() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(3);
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		Assert.assertFalse(array.remove(null));
	}
	
	@Test
	public void removeNonExistingObject() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(3);
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		Assert.assertFalse(array.remove(new String("Orangutan")));
	}
	
	@Test
	public void removeExistingObject() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(3);
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		Assert.assertTrue(array.remove(new String("Kamion")));
	}
	
	@Test
	public void transformToArray() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(3);
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		Object[] objects = array.toArray();
		
		Assert.assertEquals(new String("Kamion"), objects[0]);
		Assert.assertEquals(new String("Traktor"), objects[1]);
		Assert.assertEquals(new String("Vjetrenjača"), objects[2]);
		Assert.assertEquals(3, objects.length);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void transformToArrayEmptyArray() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
	
		array.toArray();
	}
	
	@Test
	public void clearArray() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(3);
		array.add(new String("Kamion"));
		array.add(new String("Traktor"));
		array.add(new String("Vjetrenjača"));
		
		array.clear();
		
		Assert.assertTrue(array.isEmpty());
	}
	
	@Test
	public void checkIfEmptyArrayIsEmpty() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(3);
		
		Assert.assertTrue(array.isEmpty());
	}
	
	@Test
	public void checkIfNotEmptyArrayIsEmpty() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(3);
		array.add(new String("Kamion"));
		
		Assert.assertFalse(array.isEmpty());
	}
	
	@Test
	public void areAllElementsAddedFromAnotherCollection() {
		ArrayIndexedCollection array1 = new ArrayIndexedCollection(3);
		array1.add(new String("Kamion"));
		array1.add(new String("Traktor"));
		array1.add(new String("Vjetrenjača"));
		
		ArrayIndexedCollection array2 = new ArrayIndexedCollection(3);
		array2.add(new String("Volan"));
		array2.add(new String("Samuraj"));
		array2.add(new String("Vadičep"));
		
		array1.addAll(array2);
		
		Assert.assertEquals(6, array1.size());
		Assert.assertEquals(3, array2.size());
	}
	
	
}
