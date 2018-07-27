package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class that wraps value and is stored in ObjectMulstistack.
 * Value can be of types String, Integer, Double or it can be null.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ValueWrapper {
	
	/** Value Wrapped */
	private Object value;
	
	/**
	 * Constructor that initializes object.
	 * 
	 * @param value Value that is set.
	 */
	public ValueWrapper(Object value) {
		setValue(value);
	}
	
	/**
	 * @return Returns value.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Method that sets value. It can be of types String, 
	 * Integer, Double or it can be null.
	 * 
	 * @param value Provided value.
	 * @throws RuntimeException if value invalid.
	 */
	public void setValue(Object value) {
		if (value != null && (value.getClass() != String.class) && 
				(value.getClass() != Integer.class) && (value.getClass() != Double.class)) {
			throw new RuntimeException("Value can only be of types 'Integer', 'String', 'Double' or it can be null.");
		}
		
		this.value = value;
	}

	/**
	 * Method that increases this value for provided value.
	 * 
	 * @param incValue Provided value.
	 */
	public void add(Object incValue) {
		compute(Operation.ADDITION, incValue);
	}
	
	/**
	 * Method that decreases this value for provided value.
	 * 
	 * @param decValue Provided value.
	 */
	public void subtract(Object decValue) {
		compute(Operation.SUBTRACTION, decValue);
	}
	
	/**
	 * Method that divides this value with provided value.
	 * 
	 * @param divValue Provided value.
	 */
	public void divide(Object divValue) {
		compute(Operation.DIVISION, divValue);
	}
	
	/**
	 * Method that multiplies this value with provided value.
	 * 
	 * @param mulValue Provided value.
	 */
	public void multiply(Object mulValue) {
		compute(Operation.MULTIPLICATION, mulValue);
	}

	/**
	 * Method that determines which calculation should be made
	 * based on operator provided. After determination calculation is
	 * made between this value and provided value.
	 * 
	 * @param operation Operation between values.
	 * @param computeValue Value provided.
	 */
	private void compute(Operation operation, Object computeValue) {
		value = checkCompatibility(value);
		Object newValue = checkCompatibility(computeValue);
		
		if ((value.getClass() == Double.class) || (newValue.getClass() == Double.class)) {
			if (value.getClass() == Integer.class) {
				value = Double.valueOf((Integer)value);
			}
			
			if (newValue.getClass() == Integer.class) {
				newValue = Double.valueOf((Integer)newValue);
			}
			
			switch (operation) {
				case ADDITION:
					value = Double.valueOf((double)value+(double)newValue);
					break;
				case SUBTRACTION:
					value = Double.valueOf((double)value-(double)newValue);
					break;
				case DIVISION:
					value = Double.valueOf((double)value/(double)newValue);
					break;
				case MULTIPLICATION:
					value = Double.valueOf((double)value*(double)newValue);
					break;
			}
		} else {
			switch (operation) {
				case ADDITION:
					value = Integer.valueOf((int)value+(int)newValue);
					break;
				case SUBTRACTION:
					value = Integer.valueOf((int)value-(int)newValue);
					break;
				case DIVISION:
					value = Integer.valueOf((int)value/(int)newValue);
					break;
				case MULTIPLICATION:
					value = Integer.valueOf((int)value*(int)newValue);
					break;
			}
		}
	}
	
	/**
	 * Method that checks if value is compatible for calculation.
	 * If it is not, it is changes so it is.
	 * 
	 * @param checkValue Checked value.
	 * @return Compatible value.
	 */
	private Object checkCompatibility(Object checkValue) {
		if (checkValue == null) {
			return Integer.valueOf(0);
		}
		
		if (checkValue.getClass() == String.class) {
			try {
				return Integer.valueOf(Integer.parseInt((String)checkValue));
			} catch (NumberFormatException e1) {
				try {
					return Double.valueOf(Double.parseDouble((String)checkValue));
				} catch (NumberFormatException e2) {
					throw new RuntimeException("String can not be converter to 'Integer' or 'Double'.");
				}
			}
		}
		
		return checkValue;
	}
	
	/**
	 * Method that determines which value is greater.
	 * 
	 * @param withValue Value that will be compared to this value.
	 * @return 1 if this value is greater, -1 if provided value is greater,
	 * 0 if they are the same.
	 */
	public int numCompare(Object withValue) {
		Object thisValue = checkCompatibility(value);
		Object otherValue = checkCompatibility(withValue);
		
		Double newThisValue = getAsDouble(thisValue);
		Double newOtherValue = getAsDouble(otherValue);
		
		return newThisValue.compareTo(newOtherValue);
	}

	/**
	 * Method that transforms value to Double.
	 * 
	 * @param value Provided value.
	 * @return Value as Double.
	 */
	private Double getAsDouble(Object value) {
		if (value.getClass() == Double.class) {
			return ((Double)value);
		}
		
		return Double.valueOf(((Integer)value).intValue());
	}
	
}
