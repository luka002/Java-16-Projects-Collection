package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class that executes document whose parse tree it obtains.
 * 
 * @author Luka Grgić
 */
public class SmartScriptEngine {

	/** Document node from parsed tree */
	private DocumentNode documentNode;
	/** RequestContext */
	private RequestContext requestContext;
	/** Holds for loop variables */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/**
	 * Constructor.
	 * 
	 * @param documentNode documentNode
	 * @param requestContext requestContext
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		Objects.requireNonNull(documentNode, "Document node can not be null.");
		Objects.requireNonNull(requestContext, "Request context can not be null.");
		
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}
	
	/**
	 * Calls accept on document node.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
	
	private INodeVisitor visitor = new INodeVisitor() {
		
		/**
		 * Calls accept for all direct children.
		 * 
		 * @param node node
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int index = 0; index < node.numberOfChildren(); index++) {
				node.getChild(index).accept(this);
			}			
		}
		
		/**
		 * Writes text that it contains using requestContext's write method.
		 * 
		 * @param node node
		 */
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.err.println("Error writing.");
				e.printStackTrace();
			}
		}
		
		/**
		 * Calls accept on all direct children as many times
		 * as defined in the node.
		 * 
		 * @param node node
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variable = node.getVariable().asText();
			double start = getFromNode(node.getStartExpression());
			double end = getFromNode(node.getEndExpression());
			double step = getFromNode(node.getStepExpression());
			
			multistack.push(variable, new ValueWrapper(start));
			
			while ((double)multistack.peek(variable).getValue() <= end) {
				for(int index = 0; index < node.numberOfChildren(); index++) {
					node.getChild(index).accept(this);
				}
				
				multistack.peek(variable).add(step);
			}
			
			multistack.pop(variable);
		}
		
		/**
		 * Gets element as double value.
		 * 
		 * @param element element
		 * @return element as double value.
		 */
		private double getFromNode(Element element) {
			if (element instanceof ElementConstantInteger || 
					element instanceof ElementConstantDouble ||
					element instanceof ElementString) {
				
				return Double.parseDouble(element.asText());
			}
			
			if (element instanceof ElementVariable) {
				return (double)multistack.peek(element.asText()).getValue();
			}
			
			return 0;
		}

		/**
		 * Goes trough all tokens and based on which
		 * token it is takes appropriate action and writes
		 * result in the ouputStream.
		 * 
		 * @param node node
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> stack = new Stack<>();
			Element[] elements = node.getElements();
			
			for (int i = 1; i < elements.length; i++) {
				
				if (elements[i] instanceof ElementConstantInteger || 
						elements[i] instanceof ElementConstantDouble ||
						elements[i] instanceof ElementString) {
					stack.push(elements[i].asText());
				} 
				else if (elements[i] instanceof ElementVariable) {
					stack.push(multistack.peek(elements[i].asText()).getValue());
				}
				else if (elements[i] instanceof ElementOperator) {
					double second = getAsDouble(stack.pop());
					double first = getAsDouble(stack.pop());
					
					switch (elements[i].asText()) {
						case "+":
							stack.push(first + second);
							break;
						case "-":
							stack.push(first - second);
							break;
						case "*":
							stack.push(first * second);
							break;
						case "/":
							stack.push(first / second);
							break;
					}
				} 
				else if (elements[i] instanceof ElementFunction) {
					checkFuction(elements[i].asText(), stack);
				}
			}
			
			Object[] objects = stack.toArray();
			
			for (int i = 0; i < objects.length; i++) {
				try {
					requestContext.write(getAsString(objects[i]));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		/**
		 * Returns object as string.
		 * 
		 * @param object object
		 * @return object as string
		 * @throws IllegalArgumentException if object can not be casted
		 * as a string
		 */
		private String getAsString(Object object) {
			if (object instanceof Integer) {
				return Integer.toString((Integer)object);
			}
				
			if (object instanceof Double) {
				if ((Double)object%1 == 0) {
					return Integer.toString(((Double)object).intValue());	
				}
				return Double.toString((Double)object);
			}
			
			if (object instanceof String) {
				return (String)object;
			}
			
			throw new IllegalArgumentException("Can not get poped value as string.");
		}

		/**
		 * Returns poped as double
		 * 
		 * @param poped object
		 * @return poped as double
		 * @throws IllegalArgumentException if object can not be casted
		 * as a double
		 */
		private double getAsDouble(Object poped) {
			if (poped instanceof Integer || poped instanceof Double) {
				return (double)poped;
			}
			
			if (poped instanceof String) {
				return Double.parseDouble((String)poped);
			}
			
			throw new IllegalArgumentException("Can not get poped value as double.");
		}
		
		/**
		 * Checks what function should be called.
		 * 
		 * @param function function
		 * @param stack stack
		 */
		private void checkFuction(String function, Stack<Object> stack) {
			switch (function) {
				case "sin":
					calculateSin(stack);
					break;
				case "decfmt":
					calculateDecfmt(stack);
					break;
				case "dup":
					calculateDup(stack);
					break;
				case "swap":
					calculateSwap(stack);
					break;
				case "setMimeType":
					calculateSetMimeType(stack);
					break;
				case "paramGet":
					calculateParamGet(stack);
					break;
				case "pparamGet":
					calculatePparamGet(stack);
					break;
				case "pparamSet":
					calculatePparamSet(stack);
					break;
				case "pparamDel":
					calculatePparamDel(stack);
					break;
				case "tparamGet":
					calculateTparamGet(stack);
					break;
				case "tparamSet":
					calculateTparamSet(stack);
					break;
				case "tparamDel":
					calculateTparamDel(stack);
					break;
			}
		}

		/**
		 * Calculates sinus.
		 * 
		 * @param stack stack
		 */
		private void calculateSin(Stack<Object> stack) {
			double value = getAsDouble(stack.pop());
			stack.push(Math.sin(value*(Math.PI/180)));
		}
		
		/**
		 * Formats output.
		 * 
		 * @param stack stack
		 */
		private void calculateDecfmt(Stack<Object> stack) {
			DecimalFormat format = new DecimalFormat((String)stack.pop());
			double value = getAsDouble(stack.pop());
			
			String formatedValue = format.format(value);
			stack.push(formatedValue);
		}

		/**
		 * Copies value from top of the stack and pushes it
		 * on top of the stack.
		 * 
		 * @param stack stack
		 */
		private void calculateDup(Stack<Object> stack) {
			stack.add(stack.peek());
		}

		/**
		 * Swaps two values on top of the stack.
		 * 
		 * @param stack stack
		 */
		private void calculateSwap(Stack<Object> stack) {
			Object value1 = stack.pop();
			Object value2 = stack.pop();
			
			stack.push(value1);
			stack.push(value2);
		}

		/**
		 * Sets mime type.
		 * 
		 * @param stack stack.
		 */
		private void calculateSetMimeType(Stack<Object> stack) {
			requestContext.setMimeType((String)stack.pop());
		}

		/**
		 * Gets parameter from requestContext
		 * 
		 * @param stack stack
		 */
		private void calculateParamGet(Stack<Object> stack) {
			Object defaultValut = stack.pop();
			Object name = stack.pop();
			
			String value = requestContext.getParameter(getAsString(name));
			stack.push(value == null ? defaultValut : value);
		}

		
		/**
		 * Gets permanent parameter from requestContext
		 * 
		 * @param stack stack
		 */
		private void calculatePparamGet(Stack<Object> stack) {
			Object defaultValut = stack.pop();
			Object name = stack.pop();
			
			String value = requestContext.getPersistentParameter(getAsString(name));
			stack.push(value == null ? defaultValut : value);
		}

		/**
		 * Gets permanent parameter and puts it in requestContext
		 * 
		 * @param stack stack
		 */
		private void calculatePparamSet(Stack<Object> stack) {
			String name = getAsString(stack.pop());
			String value = getAsString(stack.pop());
			
			requestContext.setPersistentParameter(name, value);
		}
		
		/**
		 * Deletes permanent parameter.
		 * @param stack
		 */
		private void calculatePparamDel(Stack<Object> stack) {
			String name = getAsString(stack.pop());
			
			requestContext.removePersistentParameter(name);
		}

		/**
		 * Gets temporary parameter from requestContext
		 * 
		 * @param stack stack
		 */
		private void calculateTparamGet(Stack<Object> stack) {
			Object defaultValut = stack.pop();
			Object name = stack.pop();
			
			String value = requestContext.getTemporaryParameter(getAsString(name));
			stack.push(value == null ? defaultValut : value);
		}
		
		/**
		 * Gets temporary parameter and puts it in requestContext
		 * 
		 * @param stack stack
		 */
		private void calculateTparamSet(Stack<Object> stack) {
			String name = getAsString(stack.pop());
			String value = getAsString(stack.pop());
			
			requestContext.setTemporaryParameter(name, value);
		}
		
		/**
		 * Deletes temporary parameter.
		 * @param stack
		 */
		private void calculateTparamDel(Stack<Object> stack) {
			String name = getAsString(stack.pop());
			
			requestContext.removeTemporaryParameter(name);
		}		
		
	};
		
}
