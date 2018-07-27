package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node representing an entire document.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class DocumentNode extends Node implements INodeVisitor {
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}

	@Override
	public void visitTextNode(TextNode node) {
	}

	@Override
	public void visitForLoopNode(ForLoopNode node) {
	}

	@Override
	public void visitEchoNode(EchoNode node) {
	}

	@Override
	public void visitDocumentNode(DocumentNode node) {
	}
	
	public void setText(String text) {
	}
	
}
