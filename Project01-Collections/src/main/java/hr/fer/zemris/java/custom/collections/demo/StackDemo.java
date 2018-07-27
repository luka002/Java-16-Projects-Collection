package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Program that calculates given mathematical expression
 * that uses postfix expression to calculate the result.
 * Mathematical expression is given as command line argument
 * and is defined in quotes.
 * Example 1: "8 2 /" means apply / on 8 and 2, so 8/2=4.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class StackDemo {

	/**
	 * Method that is called when program starts executing.
	 * 
	 * @param args Command line arguments. User is expected to 
	 * insert one argument written as postfix mathematical expression.
	 */
	public static void main(String[] args) {

		if (args.length == 1) {
			String[] values = args[0].split("\\s+");
			ObjectStack stack = new ObjectStack();

			for (int i = 0; i < values.length; i++) {
				if (values[i].matches("^[-+]?\\d+$")) {
					stack.push(Integer.parseInt(values[i]));
				} else if (values[i].matches("^[*+-/%]$")) {
					if (stack.size() < 2) {
						System.err.println("Wrong expression. there has to be at " + 
										"least 2 numbers before operand.");
						System.exit(1);
					}

					char operator = values[i].charAt(0);
					int first = (int) stack.pop();
					int second = (int) stack.pop();

					try {
						switch (operator) {
						case '+':
							stack.push(second + first);
							break;
						case '-':
							stack.push(second - first);
							break;
						case '*':
							stack.push(second * first);
							break;
						case '/':
							stack.push(second / first);
							break;
						case '%':
							stack.push(second % first);
							break;
						}
					} catch (ArithmeticException e) {
						System.err.println("Can not devide by zero.");
						System.exit(1);
					}
				} else {
					System.err.println("Wrong operands. Operands that can be used are: /, *, -, + and %.");
					System.exit(1);
				}
			}

			if (stack.size() != 1) {
				System.err.println("Calculation error. Expression not provided correctly.");
			} else {
				System.out.println(stack.pop());
			}

		} else {
			System.err.println("Calculation should be provided as one argument only.");
		}

	}

}
