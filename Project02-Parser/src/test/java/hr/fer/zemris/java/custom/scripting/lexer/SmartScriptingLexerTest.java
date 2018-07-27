package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class SmartScriptingLexerTest {

	@Test
	public void testNotNull() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("");
		
		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}
	
	@Test(expected=SmartScriptingLexerException.class)
	public void testNullInput() {
		new SmartScriptingLexer(null);
	}
	
	@Test
	public void testEmpty() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("");
		
		assertEquals("Empty input must generate only EOF token.", TokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		SmartScriptingLexer lexer = new SmartScriptingLexer("");
		
		Token token = lexer.nextToken();
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
	}
	
	@Test(expected=SmartScriptingLexerException.class)
	public void testRadAfterEOF() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		lexer.nextToken();
	}
	
	@Test
	public void testTextToken() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("Ovo \\{$je$} \\\\test. +*8.9");
		
		Token correctData[] = {
				new Token(TokenType.TEXT, new TextNode("Ovo {$je$} \\test. +*8.9"))
		};
		
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testForToken() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("{$ FOR i -1 2.3 1 $}"
				+ "{$   FoR    sco_re1 \"-1\"10    $}");
		
		Token correctData[] = {
				new Token(TokenType.FOR, new ForLoopNode(
						new ElementVariable("i"), new ElementConstantInteger(-1),
						new ElementConstantDouble(2.3), new ElementConstantInteger(1))),
				new Token(TokenType.FOR, new ForLoopNode(
						new ElementVariable("sco_re1"), new ElementString("-1"),
						new ElementConstantInteger(10)))
		};
		
		checkTokenStream(lexer, correctData);
	}
	
	@Test(expected=SmartScriptingLexerException.class)
	public void testForTokenNotStartingWithVariable() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("{$ FOR 3 -1 10 1 $}");
		
		lexer.nextToken();
	}
	
	@Test(expected=SmartScriptingLexerException.class)
	public void testForTokenContainingOparator() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("{$ FOR 3 -1 + 1 $}");
		
		lexer.nextToken();
	}
	
	@Test(expected=SmartScriptingLexerException.class)
	public void testForTokenWithToFewArguments() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("{$ FOR i 1 $}");
		
		lexer.nextToken();
	}
	
	@Test(expected=SmartScriptingLexerException.class)
	public void testForTokenContainingTooManyArguments() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("{$ FOR i 1 1 1 1 $}");
		
		lexer.nextToken();
	}
	
	@Test
	public void testEndToken() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("{$   enD   $}");
		
		assertEquals(TokenType.END, lexer.nextToken().getType());
	}
	
	@Test
	public void testEchoTokenStartingWithEquals() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("{$= + -* / ^ as_98 @sin -2 9.8 \"Joe \\\"Long\\\" Smith\" $}");
		
		Element[] elements = {
				new ElementOperator("="),
				new ElementOperator("+"),
				new ElementOperator("-"),
				new ElementOperator("*"),
				new ElementOperator("/"),
				new ElementOperator("^"),
				new ElementVariable("as_98"),
				new ElementFunction("sin"),
				new ElementConstantInteger(-2),
				new ElementConstantDouble(9.8),
				new ElementString("Joe \"Long\" Smith")
		};
		
		Token correctData[] = {
				new Token(TokenType.ECHOEQUALS, new EchoNode(elements))
		};
		
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testEchoTokenStartingWithVariable() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("{$ var66 7 + 5 $}");
		
		Element[] elements = {
				new ElementVariable(" var66"),
				new ElementConstantInteger(7),
				new ElementOperator("+"),
				new ElementConstantInteger(5),
		};
		
		Token correctData[] = {
				new Token(TokenType.ECHOVARIABLE, new EchoNode(elements))
		};
		
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testTokensCombined() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("This{$FOR i 2 3$}is a{$= 2$}test");
		
		Element[] elements = {
				new ElementOperator("="),
				new ElementConstantInteger(2),
		};
		
		Token correctData[] = {
				new Token(TokenType.TEXT, new TextNode("This")),
				new Token(TokenType.FOR, new ForLoopNode(
						new ElementVariable("i"), new ElementConstantInteger(2),
						new ElementConstantInteger(3))),
				new Token(TokenType.TEXT, new TextNode("is a")),
				new Token(TokenType.ECHOEQUALS, new EchoNode(elements)),
				new Token(TokenType.TEXT, new TextNode("test")),
		};
		
		checkTokenStream(lexer, correctData);
	}
	
	private void checkTokenStream(SmartScriptingLexer lexer, Token[] correctData) {
		int counter = 0;
		for(Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token "+counter + ":";
			assertEquals(msg, expected.getType(), actual.getType());
			assertEquals(msg, expected.getValue().getText(), actual.getValue().getText());
			counter++;
		}
	}
}
