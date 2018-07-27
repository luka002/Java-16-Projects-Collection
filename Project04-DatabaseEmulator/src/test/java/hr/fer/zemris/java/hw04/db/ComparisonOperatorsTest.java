package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hr.fer.zemris.java.hw04.db.ComparisonOperators;
import hr.fer.zemris.java.hw04.db.IComparisonOperator;

public class ComparisonOperatorsTest {

	@Test
	public void testLESS() {
		IComparisonOperator operator = ComparisonOperators.LESS;

		assertTrue(operator.satisfied("Ana", "Zrinka"));
		assertFalse(operator.satisfied("Ana", "Ana"));
		assertFalse(operator.satisfied("Zrinka", "Ana"));
	}
	
	@Test
	public void testLESS_OR_EQUALS() {
		IComparisonOperator operator = ComparisonOperators.LESS_OR_EQUALS;

		assertTrue(operator.satisfied("Ana", "Zrinka"));
		assertTrue(operator.satisfied("Ana", "Ana"));
		assertFalse(operator.satisfied("Zrinka", "Ana"));
	}
	
	@Test
	public void testGREATER() {
		IComparisonOperator operator = ComparisonOperators.GREATER;

		assertFalse(operator.satisfied("Ana", "Zrinka"));
		assertFalse(operator.satisfied("Ana", "Ana"));
		assertTrue(operator.satisfied("Zrinka", "Ana"));
	}
	
	@Test
	public void testGREATER_OR_EQUALS() {
		IComparisonOperator operator = ComparisonOperators.GREATER_OR_EQUALS;

		assertFalse(operator.satisfied("Ana", "Zrinka"));
		assertTrue(operator.satisfied("Ana", "Ana"));
		assertTrue(operator.satisfied("Ivana", "Ana"));
	}
	
	@Test
	public void testEQUALS() {
		IComparisonOperator operator = ComparisonOperators.EQUALS;

		assertFalse(operator.satisfied("Ana", "Zrinka"));
		assertTrue(operator.satisfied("Ana", "Ana"));
		assertFalse(operator.satisfied("Zrinka", "Ana"));
	}
	
	@Test
	public void testNOT_EQUALS() {
		IComparisonOperator operator = ComparisonOperators.NOT_EQUALS;

		assertTrue(operator.satisfied("Ana", "Zrinka"));
		assertFalse(operator.satisfied("Ana", "Ana"));
		assertTrue(operator.satisfied("Zrinka", "ana"));
	}
	
	@Test
	public void testLIKE() {
		IComparisonOperator operator = ComparisonOperators.LIKE;

		assertTrue(operator.satisfied("Zrinka", "Zrinka"));
		assertTrue(operator.satisfied("Zrinka", "*inka"));
		assertTrue(operator.satisfied("Zrinka", "Zrin*"));
		assertTrue(operator.satisfied("Zrinka", "Zr*inka"));
		assertTrue(operator.satisfied("Zrinka", "*Zrinka"));
		assertTrue(operator.satisfied("Zrinka", "Zrinka*"));
		assertTrue(operator.satisfied("Zrinka", "Zr*ka"));
		assertTrue(operator.satisfied("Zrinka", "*"));
		
		assertFalse(operator.satisfied("Zrinka", "aZrinka"));
		assertFalse(operator.satisfied("Zrinka", "Zrinkaaaaa"));
		assertFalse(operator.satisfied("Zrinka", "Zri*fnka"));
		assertFalse(operator.satisfied("Zrinka", "*Zrccccinka"));
		assertFalse(operator.satisfied("Zrinka", "Zrisssssnka*"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testLIKEWithTwoWildcards() {
		IComparisonOperator operator = ComparisonOperators.LIKE;

		assertTrue(operator.satisfied("Zrinka", "Z*i*a"));
	}

}
