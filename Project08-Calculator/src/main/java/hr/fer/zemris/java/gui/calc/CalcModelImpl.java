package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * Concrete implementation of CalcModel.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class CalcModelImpl implements CalcModel {
	
	/** Currently typed number */
	private String typedNumber;
	/** Number typed before binary operator */
	private String activeOperand;
	/** List of CalcValueListener-s*/
	private List<CalcValueListener> listeners;
	/** Operation to be performed */
	private DoubleBinaryOperator pendingOperation;
	
	/**
	 * Constructor initializes all to null.
	 */
	public CalcModelImpl() {
		super();
		this.typedNumber = null;
		this.activeOperand = null;
		this.pendingOperation = null;
		listeners = new ArrayList<>();
	}

	/**
	 * Notifies listeners when value has changed.
	 */
	public void valueChanged() {
		for (CalcValueListener listener : listeners) {
			listener.valueChanged(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (typedNumber == null) {
			return "0";
		}
		return typedNumber;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if (listeners.contains(l)) {
			listeners.remove(l);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getValue() {
		if (typedNumber == null) {
			return 0.0f;
		}
		return Double.parseDouble(typedNumber);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(double value) {
		if (Double.isFinite(value)) {
			typedNumber = Double.toString(value);
		}
		valueChanged();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		typedNumber = null;
		valueChanged();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearAll() {
		typedNumber = null;
		pendingOperation = null;
		activeOperand = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void swapSign() {
		if (typedNumber == null) return;
		
		if (typedNumber.charAt(0) == '-') {
			typedNumber = typedNumber.substring(1);
		} else {
			typedNumber = "-" + typedNumber;
		}
		valueChanged();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertDecimalPoint() {
		if (typedNumber != null && typedNumber.contains(".")) return;
		
		if (typedNumber == null) {
			typedNumber = "0.";
		} else {
			typedNumber += ".";
		}
		valueChanged();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertDigit(int digit) {
		boolean leadingZeros = false;
		try {
			if (Integer.parseInt(typedNumber) == 0) {
				leadingZeros = true;
			}
		} catch (NumberFormatException ignorable) {
		}
		
		if (typedNumber == null) {
			typedNumber = Integer.toString(digit);
		} else if (Double.parseDouble(typedNumber + Integer.toString(digit)) < Double.MAX_VALUE){
			if (digit == 0 && leadingZeros) {
				return;
			} else if (digit != 0 && leadingZeros) {
				typedNumber = Integer.toString(digit);
			} else {
				typedNumber += Integer.toString(digit);
			}	
		} 
		valueChanged();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getActiveOperand() {
		if (activeOperand == null) throw new IllegalStateException("Operand not set.");
		
		return Double.parseDouble(activeOperand);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = Double.toString(activeOperand);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}

}
