package hr.fer.zemris.math;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ComplexTest {

	private final static double DELTA = 1E-2;
	
	@Test
	public void addComplexNumbers() {
		Complex number = new Complex(2, 3);
		Complex newNumber = number.add(new Complex(-4, 3));
				
		Assert.assertEquals(-2, newNumber.getRe(), DELTA);
		Assert.assertEquals(6, newNumber.getIm(), DELTA);
	}
	
	@Test
	public void subtractComplexNumbers() {
		Complex number = new Complex(2, 3);
		Complex newNumber = number.sub(new Complex(-4, 3));
				
		Assert.assertEquals(6, newNumber.getRe(), DELTA);
		Assert.assertEquals(0, newNumber.getIm(), DELTA);
	}
	
	@Test
	public void multipyComplexNumbers() {
		Complex number = new Complex(2, 3);
		Complex newNumber = number.multiply(new Complex(-4, 3));
				
		Assert.assertEquals(-17, newNumber.getRe(), DELTA);
		Assert.assertEquals(-6, newNumber.getIm(), DELTA);
	}
	
	@Test
	public void divideComplexNumbers() {
		Complex number = new Complex(2, 3);
		Complex newNumber = number.divide(new Complex(-4, 3));
				
		Assert.assertEquals(0.04, newNumber.getRe(), DELTA);
		Assert.assertEquals(-0.72, newNumber.getIm(), DELTA);
	}
	
	@Test
	public void powerOfComplexNumber() {
		Complex number = new Complex(2, 3);
		Complex newNumber = number.power(3);
				
		Assert.assertEquals(-46, newNumber.getRe(), DELTA);
		Assert.assertEquals(9, newNumber.getIm(), DELTA);
	}
	
	@Test
	public void rootOfComplexNumber() {
		Complex number = new Complex(2, 3);
		List<Complex> newNumbers = number.root(3);
				
		Assert.assertEquals(1.45, newNumbers.get(0).getRe(), DELTA);
		Assert.assertEquals(0.49, newNumbers.get(0).getIm(), DELTA);
		Assert.assertEquals(-1.15, newNumbers.get(1).getRe(), DELTA);
		Assert.assertEquals(1.01, newNumbers.get(1).getIm(), DELTA);
		Assert.assertEquals(-0.29, newNumbers.get(2).getRe(), DELTA);
		Assert.assertEquals(-1.50, newNumbers.get(2).getIm(), DELTA);
	}
	
	@Test
	public void negateComplexNumbers() {
		Complex number = new Complex(2, 3).negate();
				
		Assert.assertEquals(-2, number.getRe(), DELTA);
		Assert.assertEquals(-3, number.getIm(), DELTA);
	}
	
	@Test
	public void testModule() {
		double number = new Complex(2, 3).module();
				
		Assert.assertEquals(Math.sqrt(13), number, DELTA);
	}
	
	@Test
	public void parsePositiveReal() {
		Complex number = Complex.parse("  26 ");
		Complex number2 = Complex.parse(" +26 ");

		Assert.assertEquals(26, number.getRe(), 1e-2);
		Assert.assertEquals(0, number.getIm(), 1e-2);
		Assert.assertEquals(26, number2.getRe(), 1e-2);
		Assert.assertEquals(0, number2.getIm(), 1e-2);
	}

	@Test
	public void parseNegativeReal() {
		Complex number = Complex.parse(" -26 ");

		Assert.assertEquals(-26, number.getRe(), 1e-2);
		Assert.assertEquals(0, number.getIm(), 1e-2);
	}

	@Test
	public void parsePositiveImaginaryWithNumber() {
		Complex number = Complex.parse(" i26 ");
		Complex number2 = Complex.parse(" +i26 ");

		Assert.assertEquals(0, number.getRe(), 1e-2);
		Assert.assertEquals(26, number.getIm(), 1e-2);
		Assert.assertEquals(0, number2.getRe(), 1e-2);
		Assert.assertEquals(26, number2.getIm(), 1e-2);
	}

	@Test
	public void parseNegativeImaginaryWithNumber() {
		Complex number = Complex.parse(" -i26 ");

		Assert.assertEquals(0, number.getRe(), 1e-2);
		Assert.assertEquals(-26, number.getIm(), 1e-2);
	}

	@Test
	public void parsePositiveImaginaryWithoutANumber() {
		Complex number = Complex.parse(" i ");
		Complex number2 = Complex.parse(" +i ");

		Assert.assertEquals(0, number.getRe(), 1e-2);
		Assert.assertEquals(1, number.getIm(), 1e-2);
		Assert.assertEquals(0, number.getRe(), 1e-2);
		Assert.assertEquals(1, number2.getIm(), 1e-2);
	}

	@Test
	public void parseNegativeImaginaryWithoutaNumber() {
		Complex number = Complex.parse(" -i ");

		Assert.assertEquals(0, number.getRe(), 1e-2);
		Assert.assertEquals(-1, number.getIm(), 1e-2);
	}

	@Test
	public void parsePositiveRealAndPositiveImaginaryWithNumber() {
		Complex number = Complex.parse("  9  +  i6  ");
		Complex number2 = Complex.parse(" +9 + i6 ");

		Assert.assertEquals(9, number.getRe(), 1e-2);
		Assert.assertEquals(6, number.getIm(), 1e-2);
		Assert.assertEquals(9, number2.getRe(), 1e-2);
		Assert.assertEquals(6, number2.getIm(), 1e-2);
	}

	@Test
	public void parsePositiveRealAndPositiveImaginaryWithOutANumber() {
		Complex number = Complex.parse(" 9 + i ");
		Complex number2 = Complex.parse("+9+i");

		Assert.assertEquals(9, number.getRe(), 1e-2);
		Assert.assertEquals(1, number.getIm(), 1e-2);
		Assert.assertEquals(9, number2.getRe(), 1e-2);
		Assert.assertEquals(1, number2.getIm(), 1e-2);
	}

	@Test
	public void parsePositiveRealAndNegativeImaginaryWithNumber() {
		Complex number = Complex.parse(" 9 -i6");
		Complex number2 = Complex.parse("+9-i6");

		Assert.assertEquals(9, number.getRe(), 1e-2);
		Assert.assertEquals(-6, number.getIm(), 1e-2);
		Assert.assertEquals(9, number2.getRe(), 1e-2);
		Assert.assertEquals(-6, number2.getIm(), 1e-2);
	}

	@Test
	public void parsePositiveRealAndNegativeImaginaryWithoutANumber() {
		Complex number = Complex.parse(" 9- i ");
		Complex number2 = Complex.parse("+9-i");

		Assert.assertEquals(9, number.getRe(), 1e-2);
		Assert.assertEquals(-1, number.getIm(), 1e-2);
		Assert.assertEquals(9, number2.getRe(), 1e-2);
		Assert.assertEquals(-1, number2.getIm(), 1e-2);
	}

	@Test
	public void parseNegativeRealAndPositiveImaginaryWithNumber() {
		Complex number = Complex.parse("-9+i6");

		Assert.assertEquals(-9, number.getRe(), 1e-2);
		Assert.assertEquals(6, number.getIm(), 1e-2);
	}

	@Test
	public void parseNegativeRealAndPositiveImaginaryWithOutANumber() {
		Complex number = Complex.parse("-9+i");

		Assert.assertEquals(-9, number.getRe(), 1e-2);
		Assert.assertEquals(1, number.getIm(), 1e-2);
	}

	@Test
	public void parseNegativeRealAndNegativeImaginaryWithNumber() {
		Complex number = Complex.parse("-9-i6");

		Assert.assertEquals(-9, number.getRe(), 1e-2);
		Assert.assertEquals(-6, number.getIm(), 1e-2);
	}

	@Test
	public void parseNegativeRealAndNegativeImaginaryWithoutANumber() {
		Complex number = Complex.parse("-9-i");

		Assert.assertEquals(-9, number.getRe(), 1e-2);
		Assert.assertEquals(-1, number.getIm(), 1e-2);
	}
}
