package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents an unmodifiable complex number.
 * 
 * @author Luka Grgić
 * @version 1.0
 *
 */
public class Complex {
	
	/** Real part of complex number */
	private double re;
	/** Imaginary part of complex number */
	private double im;

	/** Complex number with re=0 and im=0 */
	public static final Complex ZERO = new Complex(0, 0);
	/** Complex number with re=1 and im=0 */
	public static final Complex ONE = new Complex(1, 0);
	/** Complex number with re=-1 and im=0 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/** Complex number with re=0 and im=1 */
	public static final Complex IM = new Complex(0, 1);
	/** Complex number with re=0 and im=-1 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Default constructor that sets re and im to zero;
	 */
	public Complex() {
		this.re = 0;
		this.im = 0;
	}

	/**
	 * Constructor that accepts real and imaginary parts of
	 * complex number.
	 * 
	 * @param real Real part of complex number.
	 * @param imaginary Imaginary part of complex number.
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * @return Returns real part of complex number.
	 */
	public double getRe() {
		return re;
	}

	/**
	 * @return Returns imaginary part of complex number.
	 */
	public double getIm() {
		return im;
	}

	/**
	 * Calculates module of complex number.
	 * 
	 * @return Returns module of complex number.
	 */
	public double module() {
		return Math.sqrt(re * re + im * im);
	}

	/**
	 * Multiplies this complex number with provided one.
	 * 
	 * @param c Provided complex number.
	 * @return Complex number that is result of multiplication from 
	 * this and provided complex numbers.
	 */
	public Complex multiply(Complex c) {
		return new Complex(re * c.getRe() - im * c.getIm(), im * c.getRe() + re * c.getIm());
	}

	/**
	 * Divides this complex number with provided one.
	 * 
	 * @param c Provided complex number.
	 * @return Complex number that is result of division from 
	 * this and provided complex numbers.
	 */
	public Complex divide(Complex c) {
		return new Complex((re * c.getRe() + im * c.getIm()) / (c.module() * c.module()),
				(im * c.getRe() - re * c.getIm()) / (c.module() * c.module()));
	}

	/**
	 * Adds this complex number with provided one.
	 * 
	 * @param c Provided complex number.
	 * @return Complex number that is result of addition from 
	 * this and provided complex numbers.
	 */
	public Complex add(Complex c) {
		return new Complex(re + c.getRe(), im + c.getIm());
	}

	/**
	 * Subtracts this complex number with provided one.
	 * 
	 * @param c Provided complex number.
	 * @return Complex number that is result of subtraction from 
	 * this and provided complex numbers.
	 */
	public Complex sub(Complex c) {
		return new Complex(re - c.getRe(), im - c.getIm());
	}

	/**
	 * Negates this complex number.
	 * 
	 * @return negated complex number.
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}

	/**
	 * Method that calculates power of this complex number.
	 * 
	 * @param n Power that will be calculated.
	 * @return This number raised on provided power.
	 * @throws IndexOutOfBoundsException if provided power is negative.
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IndexOutOfBoundsException("Power can not be negative.");
		}

		double r = Math.pow(module(), n);

		return new Complex(r * (Math.cos(n * getAngle())), r * (Math.sin(n * getAngle())));
	}

	/**
	 * Method that calculate root of this complex number.
	 * 
	 * @param n Root that will be calculated.
	 * @return List of complex number roots calculated.
	 * @throws IndexOutOfBoundsException if provided root is 
	 * 0 or negative
	 */
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IndexOutOfBoundsException("Root has to be 0 or greater.");
		}
		
		List<Complex> numbers = new ArrayList<>();
		double r = Math.pow(module(), (double)1/n);
		
		for (int i = 0; i < n; i++) {
			numbers.add(new Complex(r*Math.cos((getAngle() + 2*i*Math.PI)/n),
										r*Math.sin((getAngle() + 2*i*Math.PI)/n)));
		}
		
		return numbers;
	}

	/**
	 * @return Returns angle of complex number.
	 */
	private double getAngle() {
		double angle = Math.atan2(im, re);

		return angle > 0 ? angle : angle + 2 * Math.PI;
	}

	/**
	 * Method that returns the string representation of complex number.
	 * 
	 * @return string representation of complex number.
	 */
	@Override
	public String toString() {
		return re + (im>=0 ? "+" : "") + im + "i"; 
	}
	
	/**
	 * Method that parses given string into complex number.
	 * 
	 * @param s String to be parsed into complex number.
	 * @return Complex number from parsed string.
	 * @throws NumberFormatException is string that does not correspond
	 * to complex number is provided.
	 */
	public static Complex parse(String s) throws NumberFormatException {
		s = s.replace(" ", "");

		if (s.matches("^([-+]?\\d*\\.?\\d+)?(([-+]?i)(\\d*\\.?\\d+)?)?$")) {
			String[] numbers = s.split("[+i-]");
			String[] operatorsArray = s.split("[^+-]*");
			
			numbers = removeEmptyFields(numbers);
			operatorsArray = removeEmptyFields(operatorsArray);
			
			char[] operators = operatorsToCharrArray(operatorsArray);
			
			if (s.contains("i")) {
				if (numbers.length == 2) {
					return hasRealAndImaginary(numbers, operators);
				} else {
					return hasImaginaryAndMaybeReal(numbers, operators, s);
				}
			} else if (numbers.length == 1) {
				return hasOnlyReal(numbers, operators);
			}
			
			throw new NumberFormatException();
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
	private static Complex hasOnlyReal(String[] numbers, char[] operators) {
		double real = Double.parseDouble(numbers[0]);

		if (operators.length == 1) {
			return new Complex(operators[0] == '-' ? -real : real, 0.0);
		} else {
			return new Complex(real, 0.0);
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
	private static Complex hasImaginaryAndMaybeReal(String[] numbers, char[] operators, String s) {
		if (numbers.length == 1) {
			double imaginaryOrReal = Double.parseDouble(numbers[0]);

			if (s.matches("([-+]?\\d*\\.?\\d+)([+-]i)")) {
				if (operators.length == 2) {
					return new Complex(operators[0] == '-' ? -imaginaryOrReal : imaginaryOrReal,
											operators[1] == '-' ? -1.0 : 1.0);
				} else {
					return new Complex(imaginaryOrReal, operators[0] == '-' ? -1.0 : 1.0);	
				}
			} else {
				if (operators.length == 1) {
					return new Complex(0.0, operators[0] == '-' ? -imaginaryOrReal : imaginaryOrReal);
				} else {
					return new Complex(0.0, imaginaryOrReal);
				}	
			}
		} else {
			if (operators.length == 1) {
				return new Complex(0.0, operators[0] == '-' ? -1.0 : 1.0);
			} else {
				return new Complex(0.0, 1.0);
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
	private static Complex hasRealAndImaginary(String[] numbers, char[] operators) {
		double real = Double.parseDouble(numbers[0]);
		double imaginary = Double.parseDouble(numbers[1]);

		if (operators.length == 2) {
			return new Complex(operators[0] == '-' ? -real : real, 
									operators[1] == '-' ? -imaginary : imaginary);
		} else {
			return new Complex(real, operators[0] == '-' ? -imaginary : imaginary);	
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

}
