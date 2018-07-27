package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * Program that creates node tree from given document
 * and than return is back as it was at the beginning.
 * That process is done two times and documents are compared
 * to test if they were parsed correctly both times.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class SmartScriptTester {

	/**
	 * Program starts executing from this method.
	 * 
	 * @param args Comand line arguments. Not used here.
	 */
	public static void main(String[] args) {

		String docBody = null;
		
		try {
			docBody = new String(Files.readAllBytes(Paths.get("examples/doc1.txt")), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			System.exit(1);
		}

		SmartScriptParser parser = null;
		
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); 
		
		System.out.println("==============");
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = createOriginalDocumentBody(document2);
		System.out.println(originalDocumentBody2);
		
		System.out.println("==============");
		
		System.out.println("Documents are equal: " + originalDocumentBody.equals(originalDocumentBody2));

	}

	/**
	 * Method that creates original document from node tree.
	 * 
	 * @param document Main node from tree.
	 * @return original document.
	 */
	private static String createOriginalDocumentBody(DocumentNode document) {
		StringBuilder text = new StringBuilder();
		Node childNode;
		int size;
		
		try {
			size = document.numberOfChildren();
		} catch (NullPointerException e) {
			size = 0;
		}
		
		for (int i = 0; i < size; i++) {
			childNode = document.getChild(i);

			if ((childNode instanceof ForLoopNode)) {
				text.append(getText(childNode));
			} else {
				text.append(childNode.getText());
			}
			
		}
		
		return text.toString();
	}

	/**
	 * Method that is called if node has children.
	 * Here it will only be called for "ForLoopNode".
	 * 
	 * @param node Node parent.
	 * @return Text generated from node and it's children.
	 */
	private static String getText(Node node) {
		StringBuilder text = new StringBuilder();
		
		text.append(node.getText());
		Node childNode;
		int size;
		
		try {
			size = node.numberOfChildren();
		} catch (NullPointerException e) {
			size = 0;
		}
		
		for (int i = 0; i < size; i++ ) {
			childNode = node.getChild(i);

			if (childNode instanceof ForLoopNode) {
				text.append(getText(childNode));
			} else {
				text.append(childNode.getText());
			}
		}
		
		text.append("{$ END $}");
		
		return text.toString();
	}

}
