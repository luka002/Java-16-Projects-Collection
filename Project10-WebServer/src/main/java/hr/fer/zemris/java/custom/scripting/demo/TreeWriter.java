package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Program that parses file and reassembles it with the
 * help of WriteVisitor class that behaves like visitor pattern.
 * 
 * @author Luka Grgić
 */
public class TreeWriter {

	/**
	 * Method where program starts executing.
	 * 
	 * @param args command line arguments
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
		WriterVisitor visitor = new WriterVisitor();
		document.accept(visitor);
		
		String originalDocumentBody = visitor.getText();
		System.out.println(originalDocumentBody);
	}
	
	/**
	 * Class designed to reassemble parsed document.
	 * 
	 * @author Luka Grgić
	 */
	private static class WriterVisitor implements INodeVisitor {

		/**
		 * Reassembled text.
		 */
		String text = "";
		
		@Override
		public void visitTextNode(TextNode node) {
			text += node.getText();
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			text += node.getText();
			
			for(int index = 0; index < node.numberOfChildren(); index++) {
				node.getChild(index).accept(this);
			}
			
			text += "{$ END $}";
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			text += node.getText();
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int index = 0; index < node.numberOfChildren(); index++) {
				node.getChild(index).accept(this);
			}
		}
		
		public String getText() {
			return text;
		}
		
	}

}
