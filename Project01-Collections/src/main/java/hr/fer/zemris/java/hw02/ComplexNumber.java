package hr.fer.zemris.java.hw02;

/**
 * Class that represents an unmodifiable complex number.
 * 
 * @author Luka Grgić
 * @version 1.0
 *
 */
public class ComplexNumber {

	private final double real;
	private final double imaginary;

	/**
	 * Constructor that accepts real and imaginary parts of
	 * complex number.
	 * 
	 * @param real Real part of complex number.
	 * @param imaginary Imaginary part of complex number.
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * @return Returns real part of complex number.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * @return Returns imaginary part of complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * @return Returns magnitude of complex number.
	 */
	public double getMagnitude() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * @return Returns angle of complex number.
	 */
	public double getAngle() {
		double angle = Math.atan2(imaginary, real);
		
		return angle > 0 ? angle : angle+2*Math.PI;
	}

	/**
	 * Factory method for creating complex number with real
	 * part only.
	 * 
	 * @param real Real part of complex number.
	 * @return Complex number with real part defined.
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0.0);
	}

	/**
	 * Factory method for creating complex number with imaginary
	 * part only.
	 * 
	 * @param real Imaginary part of complex number.
	 * @return Complex number with imaginary part defined.
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0.0, imaginary);
	}

	/**
	 * Factory method for creating complex number with provided 
	 * magnitude and angle.
	 * 
	 * @param magnitude Magnitude of complex number to be created.
	 * @param angle Angle of complex number to be created.
	 * @return Complex number with real and imaginary parts 
	 * derived from magnitude and angle.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * Method that parses given string into complex number.
	 * 
	 * @param s String to be parsed into complex number.
	 * @return Complex number from parsed string.
	 * @throws NumberFormatException is string that does not correspond
	 * to complex number is provided.
	 */
	public static ComplexNumber parse(String s) throws NumberFormatException {

		if (s.matches("^(([-+]?\\d*\\.?\\d+)?((([-+]\\d*\\.?\\d+)|[-+])?i)?)?$")) {
			String[] numbers = s.split("[+i-]");
			String[] operatorsArray = s.split("[^+-]*");
			
			numbers = removeEmptyFields(numbers);
			operatorsArray = removeEmptyFields(operatorsArray);
			
			char[] operators = operatorsToCharrArray(operatorsArray);
			
			if (s.charAt(s.length() - 1) == 'i') {
				if (numbers.length == 2) {
					return hasRealAndImaginary(numbers, operators);
				} else {
					return hasImaginaryAndMaybeReal(numbers, operators, s);
				}
			} else if (numbers.length == 1) {
				return hasOnlyReal(numbers, operators);
			}
			
			return new ComplexNumber(0.0, 0.0);
		} else {
			throw new NumberFormatException();
		}

	}

	/**
	 * Helper method that creates complex number when only real
	 * part is provided in parse method.
	 * 
	 * @param numbers Contains real part of complex number.
	 * @param operators Contains operator of number to determine 
	 * if it is positive or negative.
	 * @return created complex number
	 */
	private static ComplexNumber hasOnlyReal(String[] numbers, char[] operators) {
		double real = Double.parseDouble(numbers[0]);

		if (operators.length == 1) {
			return new ComplexNumber(operators[0] == '-' ? -real : real, 0.0);
		} else {
			return new ComplexNumber(real, 0.0);
		}
	}

	/**
	 * Helper method that creates complex number when imaginary
	 * part does not contain number but only letter "i".
	 * Cases are imaginary part is 1("i") or -1("-i").  
	 * 
	 * @param numbers Contains numbers of complex number.
	 * @param operators Contains operators of number to determine 
	 * if it is positive or negative.
	 * @return created complex number.
	 */
	private static ComplexNumber hasImaginaryAndMaybeReal(String[] numbers, char[] operators, String s) {
		if (numbers.length == 1) {
			double imaginaryOrReal = Double.parseDouble(numbers[0]);

			if (s.matches("([-+]?\\d*\\.?\\d+)([+-]i)")) {
				if (operators.length == 2) {
					return new ComplexNumber(operators[0] == '-' ? -imaginaryOrReal : imaginaryOrReal,
											operators[1] == '-' ? -1.0 : 1.0);
				} else {
					return new ComplexNumber(imaginaryOrReal, operators[0] == '-' ? -1.0 : 1.0);	
				}
			} else {
				if (operators.length == 1) {
					return new ComplexNumber(0.0, operators[0] == '-' ? -imaginaryOrReal : imaginaryOrReal);
				} else {
					return new ComplexNumber(0.0, imaginaryOrReal);
				}	
			}
		} else {
			if (operators.length == 1) {
				return new ComplexNumber(0.0, operators[0] == '-' ? -1.0 : 1.0);
			} else {
				return new ComplexNumber(0.0, 1.0);
			}
		}
	}

	/**
	 * Helper method that creates complex number when imaginary
	 * and real parts are provided and imaginary part contains a
	 * number meaning it is not just "i" or "-i".  
	 * 
	 * @param numbers Contains numbers of complex number.
	 * @param operators Contains operators of number to determine 
	 * if it is positive or negative.
	 * @return created complex number.
	 */
	private static ComplexNumber hasRealAndImaginary(String[] numbers, char[] operators) {
		double real = Double.parseDouble(numbers[0]);
		double imaginary = Double.parseDouble(numbers[1]);

		if (operators.length == 2) {
			return new ComplexNumber(operators[0] == '-' ? -real : real, 
									operators[1] == '-' ? -imaginary : imaginary);
		} else {
			return new ComplexNumber(real, operators[0] == '-' ? -imaginary : imaginary);	
		}
	}

	/**
	 * Helper method that transforms string array to char array.
	 * 
	 * @param operators String array.
	 * @return Char array.
	 */
	private static char[] operatorsToCharrArray(String[] operators) {
		char[] operator = new char[operators.length];
		
		for (int i = 0; i < operators.length; i++) {
			operator[i] = operators[i].charAt(0);
		}
		
		return operator;
	}

	/**
	 * Helper method that removes empty fields from given
	 * string array.
	 * 
	 * @param array Array from which empty fields will be removed.
	 * @return Array without empty fields.
	 */
	private static String[] removeEmptyFields(String[] array) {
		int size = 0;
		
		for (int i = 0; i < array.length; i++) {
			if (!array[i].trim().isEmpty()) {
				size++;
			}
		}
		
		String[] newArray = new String[size];
		int currentPosition = 0;
		
		for (int i = 0; i < array.length; i++) {
			if (!array[i].trim().isEmpty()) {
				newArray[currentPosition] = array[i];
				currentPosition++;
			}
		}
		
		return newArray;		
	}
	
	/**
	 * Adds this complex number with provided one.
	 * 
	 * @param c Provided complex number.
	 * @return Complex number that is result of addition from 
	 * this and provided complex numbers.
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real + c.getReal(), imaginary + c.getImaginary());
	}
	
	/**
	 * Subtracts this complex number with provided one.
	 * 
	 * @param c Provided complex number.
	 * @return Complex number that is result of subtraction from 
	 * this and provided complex numbers.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real - c.getReal(), imaginary - c.getImaginary());
	}
	
	/**
	 * Multiplies this complex number with provided one.
	 * 
	 * @param c Provided complex number.
	 * @return Complex number that is result of multiplication from 
	 * this and provided complex numbers.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(real*c.getReal() - imaginary*c.getImaginary(),
								imaginary*c.getReal() + real*c.getImaginary());
	}
	
	/**
	 * Divides this complex number with provided one.
	 * 
	 * @param c Provided complex number.
	 * @return Complex number that is result of division from 
	 * this and provided complex numbers.
	 */
	public ComplexNumber div(ComplexNumber c) throws ArithmeticException {
	    if(Math.abs(c.getMagnitude()) < 1e-6) {
	    	throw new ArithmeticException();
	    }
	        
	    return new ComplexNumber((real*c.getReal() + imaginary*c.getImaginary())/(c.getMagnitude()*c.getMagnitude()),
	                       (imaginary*c.getReal() - real*c.getImaginary())/(c.getMagnitude()*c.getMagnitude()));
	}
	
	/**
	 * Method that calculate power of this complex number.
	 * 
	 * @param n Power that will be calculated.
	 * @return This number raised on provided power.
	 * @throws IndexOutOfBoundsException if provided power is negative.
	 */
	public ComplexNumber power(int n) throws IndexOutOfBoundsException {
		if (n < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		double r = Math.pow(getMagnitude(), n);
		
		return new ComplexNumber(r*(Math.cos(n*getAngle())),
								r*(Math.sin(n*getAngle())));
	}
	
	/**
	 * Method that calculate root of this complex number.
	 * 
	 * @param n Root that will be calculated.
	 * @return Array of complex number roots calculated.
	 * @throws IndexOutOfBoundsException if provided root is 
	 * 0 or negative
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IndexOutOfBoundsException();
		}
		
		ComplexNumber[] numbers = new ComplexNumber[n];
		double r = Math.pow(getMagnitude(), (double)1/n);
		
		for (int i = 0; i < n; i++) {
			numbers[i] = new ComplexNumber(r*Math.cos((getAngle() + 2*i*Math.PI)/n),
										r*Math.sin((getAngle() + 2*i*Math.PI)/n));
		}
		
		return numbers;
	}

	/**
	 * Method that returns the string representation of complex number.
	 */
	@Override
	public String toString() {
		if (real == 0 && imaginary == 0) return "0";
		if (real == 0) return checkIfOneOrMinusOne();
		if (imaginary == 0) return "" + real;
		if (imaginary > 0) return real + "+" + checkIfOneOrMinusOne();
		
		return real + "" + checkIfOneOrMinusOne();
	}
	
	private String checkIfOneOrMinusOne() {
		if (Math.abs(imaginary - 1) < 1e-6 && Math.abs(imaginary - 1) > -1e-6) {
			return "i";
		} else if (Math.abs(imaginary + 1) < 1e-6 && Math.abs(imaginary + 1) > -1e-6) {
			return "-i";
		}
		return imaginary + "i";
	}
}
