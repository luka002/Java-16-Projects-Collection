package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class SmartScriptParserTest {

	@Test
	public void testNotNull() {
		SmartScriptParser parser = new SmartScriptParser("");
		
		assertNotNull("Token was expected but null was returned.", parser.getDocumentNode());
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testNullInput() {
		new SmartScriptParser(null);
	}
	
	@Test
	public void testParsingTextNode() {
		SmartScriptParser parser = new SmartScriptParser("Ovo je tekst.");
		DocumentNode node = parser.getDocumentNode();
		
		assertEquals(1, node.numberOfChildren());
		assertTrue(node.getChild(0) instanceof TextNode);
	}
	
	@Test
	public void testParsingEchoNodeStartingWithEquals() {
		SmartScriptParser parser = new SmartScriptParser("{$= 6 + 9$}");
		DocumentNode node = parser.getDocumentNode();
		
		assertEquals(1, node.numberOfChildren());
		assertTrue(node.getChild(0) instanceof EchoNode);
	}
	
	@Test
	public void testParsingEchoNodeStartingWithVariable() {
		SmartScriptParser parser = new SmartScriptParser("{$ var 6 + 9$}");
		DocumentNode node = parser.getDocumentNode();
		
		assertEquals(1, node.numberOfChildren());
		assertTrue(node.getChild(0) instanceof EchoNode);
	}
	
	@Test
	public void testParsingEmptyForNode() {
		SmartScriptParser parser = new SmartScriptParser("{$ FOR i 1 2 3 $}{$END$}") ;
		DocumentNode node = parser.getDocumentNode();
		
		assertEquals(1, node.numberOfChildren());
		assertTrue(node.getChild(0) instanceof ForLoopNode);
	}

	@Test(expected=SmartScriptParserException.class)
	public void testParsingForNodeWithoutEndTag() {
		SmartScriptParser parser = new SmartScriptParser("{$ FOR i 1 2 3 $}") ;
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testParsingOnlyEndTag() {
		SmartScriptParser parser = new SmartScriptParser("{$END$}") ;
	}
	
	@Test
	public void testParsingForNodeWithChildren() {
		SmartScriptParser parser = new SmartScriptParser("{$ FOR i 1 2 3 $}Ja sam dijete{$END$}") ;
		DocumentNode node = parser.getDocumentNode();
		ForLoopNode childNode = (ForLoopNode) node.getChild(0);
		
		assertEquals(1, node.numberOfChildren());
		assertTrue(node.getChild(0) instanceof ForLoopNode);
		assertEquals(1, childNode.numberOfChildren());
		assertTrue(childNode.getChild(0) instanceof TextNode);
	}
}
