package hr.fer.zemris.java.gui.calc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Program that emulates simple calculator.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class Calculator extends JFrame{

	private static final long serialVersionUID = 1;
	
	/**
	 * Constructor sets GUI.
	 */
	public Calculator() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Calculator");
		setLocation(200, 200);
		setSize(608, 311);
		initGUI();
	}
	
	/**
	 * Creates components and adds them to content pane.
	 */
	private void initGUI() {
		JPanel panel = new JPanel(new CalcLayout(3));
		
		MyLabel label = new MyLabel("0");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
		label.setOpaque(true);
		label.setBackground(Color.YELLOW);
		label.setFont(new Font("Serif", Font.BOLD, 24));
		
		JCheckBox checkBox = new JCheckBox("Inv");
		checkBox.setBackground(Color.decode("#24aae2"));
		checkBox.setFont(new Font("Serif", Font.BOLD, 24));
		JPanel checkBoxPanel = new JPanel(new BorderLayout());
		checkBoxPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
		checkBoxPanel.add(checkBox, BorderLayout.CENTER);
		
		Map<String, JButton> buttons = createButtons();
		
		int i = 1, j = 6;
		for (JButton button : buttons.values()) {
			button.setBackground(Color.decode("#24aae2"));
			button.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
			button.setFont(new Font("Serif", Font.BOLD, 24));
			panel.add(button, new RCPosition(i, j));
			j++;
			if (j == 8) {
				i++; 
				j = 1;
			}
			if (i == 5 && j == 7) break;
		}
		
		panel.add(label, "1,1");
		panel.add(checkBoxPanel, "5,7");
		
		addListeners(buttons, label, checkBox);
		getContentPane().add(panel);
	}

	/**
	 * Adds listeners to the components.
	 * 
	 * @param buttons buttons
	 * @param label label
	 * @param checkBox checBbox
	 */
	private void addListeners(Map<String, JButton> buttons, MyLabel label, JCheckBox checkBox) {
		Stack<Double> stack = new Stack<>();
		CalcModelImpl model = new CalcModelImpl();
		model.addCalcValueListener(label);
		addCheckBoxListener(buttons, checkBox, model);
		
		ActionListener insertDigitListener = a -> model.insertDigit(Integer.parseInt(a.getActionCommand()));
		addInsertDigitListeners(buttons, insertDigitListener);
	
		ActionListener operandListener = getOperandListener(model);
		addOperandListeners(buttons, operandListener);
		
		buttons.get("sin").addActionListener(
				functionListener(model, checkBox, x -> Math.sin(x), x -> Math.asin(x)));
		buttons.get("cos").addActionListener(
				functionListener(model, checkBox, x -> Math.cos(x), x -> Math.acos(x)));
		buttons.get("tan").addActionListener(
				functionListener(model, checkBox, x -> Math.tan(x), x -> Math.atan(x)));
		buttons.get("ctg").addActionListener(
				functionListener(model, checkBox, x -> 1/Math.tan(x), x -> Math.atan(1/x)));
		buttons.get("log").addActionListener(
				functionListener(model, checkBox, x -> Math.log(x), x -> Math.pow(10, x)));
		buttons.get("ln").addActionListener(
				functionListener(model, checkBox, x -> Math.log(x)/Math.log(Math.E), 
												  x -> Math.pow(Math.E, x)));
		
		buttons.get("rec").addActionListener(a -> model.setValue(1/model.getValue()));	
		buttons.get("equals").addActionListener(a -> {
			if (model.isActiveOperandSet()) {
				DoubleBinaryOperator operator = model.getPendingBinaryOperation();
				double value1 = model.getActiveOperand();
				double value2 = model.getValue();
				model.setValue(operator.applyAsDouble(value1, value2));
				model.clearAll();
			}
		});
		buttons.get("dot").addActionListener(a -> model.insertDecimalPoint());
		buttons.get("swap").addActionListener(a -> model.swapSign());
		buttons.get("clear").addActionListener(a -> model.clear());
		buttons.get("res").addActionListener(a -> {
			model.clearAll();
			model.valueChanged();
		});
		buttons.get("push").addActionListener(a -> stack.push(model.getValue()));
		buttons.get("pop").addActionListener(a -> {
			if (stack.empty()) {
				JOptionPane.showMessageDialog(
						this, 
						"Stack is empty, can not pop!",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				model.setValue(stack.pop());
			}
		});
		buttons.get("pow").addActionListener(a -> {
			if (!checkBox.isSelected()) {
				DoubleBinaryOperator binaryOperator = (x, y) -> Math.pow(x, y);
				
				if (!model.isActiveOperandSet()) {
					model.setActiveOperand(model.getValue());
					model.clear();
					model.setPendingBinaryOperation(binaryOperator);
				} else {
					DoubleBinaryOperator operator = model.getPendingBinaryOperation();
					double value1 = model.getActiveOperand();
					double value2 = model.getValue();
					model.setActiveOperand(operator.applyAsDouble(value1, value2));
					model.clear();
					model.setPendingBinaryOperation(binaryOperator);
				}
			} else {
				double number = Math.sqrt(model.getValue());
				if (Double.isFinite(number)) {
					model.setValue(number);
				} else {
					JOptionPane.showMessageDialog(
							this, 
							"Can not caluculate root from given number.",
							"Error",
							JOptionPane.ERROR_MESSAGE);
					model.clearAll();
					model.valueChanged();
				}
			}
		});
		
	}
	
	/**
	 * Creates ActionListener for unary operations.
	 * 
	 * @param model model
	 * @param checkBox checkBox
	 * @param oper1 operation1
	 * @param oper2 operation2
	 * @return ActionListener
	 */
	private ActionListener functionListener(CalcModel model, JCheckBox checkBox, 
							DoubleUnaryOperator oper1, DoubleUnaryOperator oper2) {
		ActionListener listener = a -> {
			if (!checkBox.isSelected()) {
				model.setValue(oper1.applyAsDouble(model.getValue()));
			} else {
				model.setValue(oper2.applyAsDouble(model.getValue()));
			}
		};
		return listener;
	}

	/**
	 * Creates listener for checkBox.
	 * 
	 * @param buttons buttons
	 * @param checkBox checkBox
	 * @param model model
	 */
	private void addCheckBoxListener(Map<String, JButton> buttons, JCheckBox checkBox, CalcModelImpl model) {
		checkBox.addActionListener(a -> {
			if (checkBox.isSelected()) {
				buttons.get("sin").setText("arcsin");
				buttons.get("cos").setText("arccos");
				buttons.get("tan").setText("arctan");
				buttons.get("ctg").setText("arcctg");
				buttons.get("log").setText("10^x");
				buttons.get("ln").setText("e^x");
				buttons.get("pow").setText("sqrt(x)");
			} else {
				buttons.get("sin").setText("sin");
				buttons.get("cos").setText("cos");
				buttons.get("tan").setText("tan");
				buttons.get("ctg").setText("ctg");
				buttons.get("log").setText("log");
				buttons.get("ln").setText("ln");
				buttons.get("pow").setText("x^n");
			}
		});
	}

	/**
	 * Adds listeners to binary operator buttons.
	 * 
	 * @param buttons buttons
	 * @param operandListener listener
	 */
	private void addOperandListeners(Map<String, JButton> buttons, ActionListener operandListener) {
		buttons.get("add").addActionListener(operandListener);
		buttons.get("sub").addActionListener(operandListener);
		buttons.get("mul").addActionListener(operandListener);
		buttons.get("div").addActionListener(operandListener);
	}

	/**
	 * Creates listener for binary operations.
	 * 
	 * @param model model
	 * @return listener
	 */
	private ActionListener getOperandListener(CalcModelImpl model) {
		ActionListener operandListener = a -> {
			DoubleBinaryOperator binaryOperator = getBinaryOperator(a);
			
			if (!model.isActiveOperandSet()) {
				model.setActiveOperand(model.getValue());
				model.clear();
				model.setPendingBinaryOperation(binaryOperator);
			} else {
				DoubleBinaryOperator operator = model.getPendingBinaryOperation();
				double value1 = model.getActiveOperand();
				double value2 = model.getValue();
				model.setActiveOperand(operator.applyAsDouble(value1, value2));
				model.clear();
				model.setPendingBinaryOperation(binaryOperator);
			}
		};
		return operandListener;
	}

	/**
	 * Creates DoubleBinaryOperator for ActionEvent.
	 * 
	 * @param a action event
	 * @return operation to perform.
	 */
	private DoubleBinaryOperator getBinaryOperator(ActionEvent a) {
		switch (a.getActionCommand()) {
			case "+":
				return (x, y) -> x + y;
			case "-":
				return (x, y) -> x - y;
			case "*":
				return (x, y) -> x * y;
			case "/":
				return (x, y) -> x / y;
		}
		return null;
	}

	/**
	 * Adds listeners to digit buttons.
	 * 
	 * @param buttons buttons
	 * @param insertDigitListener listener
	 */
	private void addInsertDigitListeners(Map<String, JButton> buttons, ActionListener insertDigitListener) {
		buttons.get("1").addActionListener(insertDigitListener);
		buttons.get("2").addActionListener(insertDigitListener);
		buttons.get("3").addActionListener(insertDigitListener);
		buttons.get("4").addActionListener(insertDigitListener);
		buttons.get("5").addActionListener(insertDigitListener);
		buttons.get("6").addActionListener(insertDigitListener);
		buttons.get("7").addActionListener(insertDigitListener);
		buttons.get("8").addActionListener(insertDigitListener);
		buttons.get("9").addActionListener(insertDigitListener);
		buttons.get("0").addActionListener(insertDigitListener);
	}

	/**
	 * Creates buttons.
	 * 
	 * @return map with buttons.
	 */
	private Map<String, JButton> createButtons() {
		Map<String, JButton> buttons = new LinkedHashMap<>();
		buttons.put("equals", new JButton("="));
		buttons.put("clear", new JButton("clr"));
		buttons.put("rec", new JButton("1/x"));
		buttons.put("sin", new JButton("sin"));
		buttons.put("7", new JButton("7"));
		buttons.put("8", new JButton("8"));
		buttons.put("9", new JButton("9"));
		buttons.put("div", new JButton("/"));
		buttons.put("res", new JButton("res"));
		buttons.put("log", new JButton("log"));
		buttons.put("cos", new JButton("cos"));
		buttons.put("4", new JButton("4"));
		buttons.put("5", new JButton("5"));
		buttons.put("6", new JButton("6"));
		buttons.put("mul", new JButton("*"));
		buttons.put("push", new JButton("push"));
		buttons.put("ln", new JButton("ln"));
		buttons.put("tan", new JButton("tan"));
		buttons.put("1", new JButton("1"));
		buttons.put("2", new JButton("2"));
		buttons.put("3", new JButton("3"));
		buttons.put("sub", new JButton("-"));
		buttons.put("pop", new JButton("pop"));
		buttons.put("pow", new JButton("x^n"));
		buttons.put("ctg", new JButton("ctg"));
		buttons.put("0", new JButton("0"));
		buttons.put("swap", new JButton("+/-"));
		buttons.put("dot", new JButton("."));
		buttons.put("add", new JButton("+"));
		return buttons;
	}

	/**
	 * Method that is first executed when program starts.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Calculator().setVisible(true);
			}
		});
	}

}
