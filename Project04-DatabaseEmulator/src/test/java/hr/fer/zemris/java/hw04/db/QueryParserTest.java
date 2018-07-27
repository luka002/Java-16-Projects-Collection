package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hr.fer.zemris.java.hw04.db.ComparisonOperators;
import hr.fer.zemris.java.hw04.db.FieldValueGetters;
import hr.fer.zemris.java.hw04.db.QueryParser;

public class QueryParserTest {
	
	@Test
	public void testJMBAGFieldValue() {
		QueryParser parser = new QueryParser("jmbag=\"0123456789\"");
		
		assertEquals(FieldValueGetters.JMBAG, parser.getQuery().get(0).getFieldValue());
	}
	
	@Test
	public void testLastNameFieldValue() {
		QueryParser parser = new QueryParser("lastName=\"Lovrić\"");
		
		assertEquals(FieldValueGetters.LAST_NAME, parser.getQuery().get(0).getFieldValue());
	}
	
	@Test
	public void testFirstNameFieldValue() {
		QueryParser parser = new QueryParser("firstName=\"Luka\"");
		
		assertEquals(FieldValueGetters.FIRST_NAME, parser.getQuery().get(0).getFieldValue());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testWrongFieldValue() {
		new QueryParser("jmbaggg=\"0123456789\"");
	}
	
	@Test
	public void testLikeOperator() {
		QueryParser parser = new QueryParser("firstName LIKE \"Ivan\"");
		
		assertEquals(ComparisonOperators.LIKE, parser.getQuery().get(0).getComparisonOperator());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidLikeOperator() {
		new QueryParser("firstName LiKE \"Ivan\"");
	}
	
	@Test
	public void testGreaterOperator() {
		QueryParser parser = new QueryParser("firstName>\"Ivan\"");
		
		assertEquals(ComparisonOperators.GREATER, parser.getQuery().get(0).getComparisonOperator());
	}
	
	@Test
	public void testLessOperator() {
		QueryParser parser = new QueryParser("firstName<\"Ivan\"");
		
		assertEquals(ComparisonOperators.LESS, parser.getQuery().get(0).getComparisonOperator());
	}
	
	@Test
	public void testGreaterOrEqualsOperator() {
		QueryParser parser = new QueryParser("firstName>=\"Ivan\"");
		
		assertEquals(ComparisonOperators.GREATER_OR_EQUALS, parser.getQuery().get(0).getComparisonOperator());
	}
	
	@Test
	public void testLessOrEqualsOperator() {
		QueryParser parser = new QueryParser("firstName<=\"Ivan\"");
		
		assertEquals(ComparisonOperators.LESS_OR_EQUALS, parser.getQuery().get(0).getComparisonOperator());
	}
	
	@Test
	public void testEqualsOperator() {
		QueryParser parser = new QueryParser("firstName=\"Ivan\"");
		
		assertEquals(ComparisonOperators.EQUALS, parser.getQuery().get(0).getComparisonOperator());
	}
	
	@Test
	public void testNotEqualsOperator() {
		QueryParser parser = new QueryParser("firstName!=\"Ivan\"");
		
		assertEquals(ComparisonOperators.NOT_EQUALS, parser.getQuery().get(0).getComparisonOperator());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testWrongOperator() {
		new QueryParser("firstName <> \"Ivan\"");
	}
	
	@Test
	public void testStringLiteral() {
		QueryParser parser = new QueryParser("firstName!=\"Ivan\"");
		
		assertEquals("Ivan", parser.getQuery().get(0).getStringLiteral());
	}
	
	@Test
	public void testSingleDirectQuerieWithSpaces() {
		QueryParser parser = new QueryParser(" jmbag   =   \"0123456789\" ");
		
		assertTrue(parser.isDirectQuery());
		assertEquals("0123456789", parser.getQueriedJMBAG());
		assertEquals(1, parser.getQuery().size());
	}
	
	@Test
	public void testSingleDirectQuerieWithoutSpaces() {
		QueryParser parser = new QueryParser("jmbag=\"0123456789\"");
		
		assertTrue(parser.isDirectQuery());
		assertEquals("0123456789", parser.getQueriedJMBAG());
		assertEquals(1, parser.getQuery().size());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testSingleNonDirectQuerieWithSpaces() {
		QueryParser parser = new QueryParser(" jmbag   >   \"0123456789\" ");
		
		assertFalse(parser.isDirectQuery());
		parser.getQueriedJMBAG();
	}
	
	@Test
	public void testMultipleQueries() {
		QueryParser parser = new QueryParser("jmbag=\"0123456789\" AND firstName!=\"Ivan\" and lastName>\"K\"");
		
		assertFalse(parser.isDirectQuery());
		assertEquals(3, parser.getQuery().size());
		
		assertEquals(FieldValueGetters.JMBAG, parser.getQuery().get(0).getFieldValue());
		assertEquals(ComparisonOperators.EQUALS, parser.getQuery().get(0).getComparisonOperator());
		assertEquals("0123456789", parser.getQuery().get(0).getStringLiteral());
		
		assertEquals(FieldValueGetters.FIRST_NAME, parser.getQuery().get(1).getFieldValue());
		assertEquals(ComparisonOperators.NOT_EQUALS, parser.getQuery().get(1).getComparisonOperator());
		assertEquals("Ivan", parser.getQuery().get(1).getStringLiteral());
		
		assertEquals(FieldValueGetters.LAST_NAME, parser.getQuery().get(2).getFieldValue());
		assertEquals(ComparisonOperators.GREATER, parser.getQuery().get(2).getComparisonOperator());
		assertEquals("K", parser.getQuery().get(2).getStringLiteral());		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNoSpaceBeforeAnd() {
		new QueryParser("jmbag=\"0123456789\"AND firstName!=\"Ivan\"");		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNoSpaceAfterAnd() {
		new QueryParser("jmbag=\"0123456789\" ANDfirstName!=\"Ivan\"");		
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testNoQueriesAfterAnd() {
		new QueryParser("jmbag=\"0123456789\" AND");		
	}
	
}
