package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * Model of calculator with appropriate methods.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public interface CalcModel {
	
	/**
	 * Adds CalcValueListener to the model.
	 * 
	 * @param l CalcValueListener
	 */
	void addCalcValueListener(CalcValueListener l);
	
	/**
	 * Removes CalcValueListener from the model.
	 * 
	 * @param l CalcValueListener
	 */
	void removeCalcValueListener(CalcValueListener l);
	
	/**
	 * Returns model value as a string.
	 * 
	 * @return model value as a string.
	 */
	String toString();
	
	/**
	 * Gets value.
	 * @return value.
	 */
	double getValue();
	
	/**
	 * Sets value.
	 * 
	 * @param value value
	 */
	void setValue(double value);
	
	/**
	 * Sets value to null.
	 */
	void clear();
	
	/**
	 * Sets all to null.
	 */
	void clearAll();
	
	/**
	 * Swaps values sign.
	 */
	void swapSign();
	
	/**
	 * appends decimal point to value.
	 */
	void insertDecimalPoint();
	
	/**
	 * Appends digit to value.
	 * 
	 * @param digit digit
	 */
	void insertDigit(int digit);
	
	/**
	 * Checks is active operand is set.
	 * 
	 * @return true if set else false.
	 */
	boolean isActiveOperandSet();
	
	/**
	 * Gets active operand.
	 * @return active operand.
	 */
	double getActiveOperand();
	
	/**
	 * Sets active operand.
	 * 
	 * @param activeOperand active operand.
	 */
	void setActiveOperand(double activeOperand);
	
	/**
	 * Sets active operand to null.
	 */
	void clearActiveOperand();
	
	/**
	 * Gets pendingBinaryOperation.
	 * 
	 * @return pendingBinaryOperation
	 */
	DoubleBinaryOperator getPendingBinaryOperation();
	
	/**
	 * Sets pendingBinaryOperation.
	 * @param op operation.
	 */
	void setPendingBinaryOperation(DoubleBinaryOperator op);
}