package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface used as visitor design pattern 
 * when traversing trough the nodes.
 * 
 * @author Luka Grgić
 */
public interface INodeVisitor {
	
	/**
	 * Called when text node is visited.
	 * 
	 * @param node node
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Called when for loop node is visited.
	 * 
	 * @param node node
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Called when echo node is visited.
	 * 
	 * @param node node
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Called when document node is visited.
	 * 
	 * @param node node
	 */
	public void visitDocumentNode(DocumentNode node);

}