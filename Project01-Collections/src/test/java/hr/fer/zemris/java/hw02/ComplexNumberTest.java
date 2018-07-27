package hr.fer.zemris.java.hw02;

import org.junit.Test;
import org.junit.Assert;

public class ComplexNumberTest {

	@Test
	public void testGetters() {
		ComplexNumber number = new ComplexNumber(4, 6);

		Assert.assertEquals(4, number.getReal(), 1e-2);
		Assert.assertEquals(6, number.getImaginary(), 1e-2);
		Assert.assertEquals(7.21, number.getMagnitude(), 1e-2);
		Assert.assertEquals(0.98, number.getAngle(), 1e-2);
	}

	@Test
	public void testFactoryFromReal() {
		ComplexNumber number = ComplexNumber.fromReal(2);

		Assert.assertEquals(2, number.getReal(), 1e-2);
		Assert.assertEquals(0, number.getImaginary(), 1e-2);
	}

	@Test
	public void testFactoryFromImaginary() {
		ComplexNumber number = ComplexNumber.fromImaginary(2);

		Assert.assertEquals(0, number.getReal(), 1e-2);
		Assert.assertEquals(2, number.getImaginary(), 1e-2);
	}

	@Test
	public void testFactoryFromMagnitudeAndAngle() {
		ComplexNumber number = ComplexNumber.fromMagnitudeAndAngle(7.21, 0.98);

		Assert.assertEquals(4, number.getReal(), 1e-1);
		Assert.assertEquals(6, number.getImaginary(), 1e-1);
	}

	@Test
	public void parsePositiveReal() {
		ComplexNumber number = ComplexNumber.parse("26");
		ComplexNumber number2 = ComplexNumber.parse("+26");

		Assert.assertEquals(26, number.getReal(), 1e-2);
		Assert.assertEquals(0, number.getImaginary(), 1e-2);
		Assert.assertEquals(26, number2.getReal(), 1e-2);
		Assert.assertEquals(0, number2.getImaginary(), 1e-2);
	}

	@Test
	public void parseNegativeReal() {
		ComplexNumber number = ComplexNumber.parse("-26");

		Assert.assertEquals(-26, number.getReal(), 1e-2);
		Assert.assertEquals(0, number.getImaginary(), 1e-2);
	}

	@Test
	public void parsePositiveImaginaryWithNumber() {
		ComplexNumber number = ComplexNumber.parse("26i");
		ComplexNumber number2 = ComplexNumber.parse("+26i");

		Assert.assertEquals(0, number.getReal(), 1e-2);
		Assert.assertEquals(26, number.getImaginary(), 1e-2);
		Assert.assertEquals(0, number2.getReal(), 1e-2);
		Assert.assertEquals(26, number2.getImaginary(), 1e-2);
	}

	@Test
	public void parseNegativeImaginaryWithNumber() {
		ComplexNumber number = ComplexNumber.parse("-26i");

		Assert.assertEquals(0, number.getReal(), 1e-2);
		Assert.assertEquals(-26, number.getImaginary(), 1e-2);
	}

	@Test
	public void parsePositiveImaginaryWithoutANumber() {
		ComplexNumber number = ComplexNumber.parse("i");
		ComplexNumber number2 = ComplexNumber.parse("+i");

		Assert.assertEquals(0, number.getReal(), 1e-2);
		Assert.assertEquals(1, number.getImaginary(), 1e-2);
		Assert.assertEquals(0, number.getReal(), 1e-2);
		Assert.assertEquals(1, number2.getImaginary(), 1e-2);
	}

	@Test
	public void parseNegativeImaginaryWithoutaNumber() {
		ComplexNumber number = ComplexNumber.parse("-i");

		Assert.assertEquals(0, number.getReal(), 1e-2);
		Assert.assertEquals(-1, number.getImaginary(), 1e-2);
	}

	@Test
	public void parsePositiveRealAndPositiveImaginaryWithNumber() {
		ComplexNumber number = ComplexNumber.parse("9+6i");
		ComplexNumber number2 = ComplexNumber.parse("+9+6i");

		Assert.assertEquals(9, number.getReal(), 1e-2);
		Assert.assertEquals(6, number.getImaginary(), 1e-2);
		Assert.assertEquals(9, number2.getReal(), 1e-2);
		Assert.assertEquals(6, number2.getImaginary(), 1e-2);
	}

	@Test
	public void parsePositiveRealAndPositiveImaginaryWithOutANumber() {
		ComplexNumber number = ComplexNumber.parse("9+i");
		ComplexNumber number2 = ComplexNumber.parse("+9+i");

		Assert.assertEquals(9, number.getReal(), 1e-2);
		Assert.assertEquals(1, number.getImaginary(), 1e-2);
		Assert.assertEquals(9, number2.getReal(), 1e-2);
		Assert.assertEquals(1, number2.getImaginary(), 1e-2);
	}

	@Test
	public void parsePositiveRealAndNegativeImaginaryWithNumber() {
		ComplexNumber number = ComplexNumber.parse("9-6i");
		ComplexNumber number2 = ComplexNumber.parse("+9-6i");

		Assert.assertEquals(9, number.getReal(), 1e-2);
		Assert.assertEquals(-6, number.getImaginary(), 1e-2);
		Assert.assertEquals(9, number2.getReal(), 1e-2);
		Assert.assertEquals(-6, number2.getImaginary(), 1e-2);
	}

	@Test
	public void parsePositiveRealAndNegativeImaginaryWithoutANumber() {
		ComplexNumber number = ComplexNumber.parse("9-i");
		ComplexNumber number2 = ComplexNumber.parse("+9-i");

		Assert.assertEquals(9, number.getReal(), 1e-2);
		Assert.assertEquals(-1, number.getImaginary(), 1e-2);
		Assert.assertEquals(9, number2.getReal(), 1e-2);
		Assert.assertEquals(-1, number2.getImaginary(), 1e-2);
	}

	@Test
	public void parseNegativeRealAndPositiveImaginaryWithNumber() {
		ComplexNumber number = ComplexNumber.parse("-9+6i");

		Assert.assertEquals(-9, number.getReal(), 1e-2);
		Assert.assertEquals(6, number.getImaginary(), 1e-2);
	}

	@Test
	public void parseNegativeRealAndPositiveImaginaryWithOutANumber() {
		ComplexNumber number = ComplexNumber.parse("-9+i");

		Assert.assertEquals(-9, number.getReal(), 1e-2);
		Assert.assertEquals(1, number.getImaginary(), 1e-2);
	}

	@Test
	public void parseNegativeRealAndNegativeImaginaryWithNumber() {
		ComplexNumber number = ComplexNumber.parse("-9-6i");

		Assert.assertEquals(-9, number.getReal(), 1e-2);
		Assert.assertEquals(-6, number.getImaginary(), 1e-2);
	}

	@Test
	public void parseNegativeRealAndNegativeImaginaryWithoutANumber() {
		ComplexNumber number = ComplexNumber.parse("-9-i");

		Assert.assertEquals(-9, number.getReal(), 1e-2);
		Assert.assertEquals(-1, number.getImaginary(), 1e-2);
	}

	@Test
	public void addComplexNumbers() {
		ComplexNumber number = new ComplexNumber(2, 3);
		ComplexNumber newNumber = number.add(new ComplexNumber(-4, 3));
				
		Assert.assertEquals(-2, newNumber.getReal(), 1e-2);
		Assert.assertEquals(6, newNumber.getImaginary(), 1e-2);
	}
	
	@Test
	public void subtractComplexNumbers() {
		ComplexNumber number = new ComplexNumber(2, 3);
		ComplexNumber newNumber = number.sub(new ComplexNumber(-4, 3));
				
		Assert.assertEquals(6, newNumber.getReal(), 1e-2);
		Assert.assertEquals(0, newNumber.getImaginary(), 1e-2);
	}
	
	@Test
	public void multipyComplexNumbers() {
		ComplexNumber number = new ComplexNumber(2, 3);
		ComplexNumber newNumber = number.mul(new ComplexNumber(-4, 3));
				
		Assert.assertEquals(-17, newNumber.getReal(), 1e-2);
		Assert.assertEquals(-6, newNumber.getImaginary(), 1e-2);
	}
	
	@Test
	public void divideComplexNumbers() {
		ComplexNumber number = new ComplexNumber(2, 3);
		ComplexNumber newNumber = number.div(new ComplexNumber(-4, 3));
				
		Assert.assertEquals(0.04, newNumber.getReal(), 1e-2);
		Assert.assertEquals(-0.72, newNumber.getImaginary(), 1e-2);
	}
	
	@Test
	public void powerOfComplexNumber() {
		ComplexNumber number = new ComplexNumber(2, 3);
		ComplexNumber newNumber = number.power(3);
				
		Assert.assertEquals(-46, newNumber.getReal(), 1e-2);
		Assert.assertEquals(9, newNumber.getImaginary(), 1e-2);
	}
	
	@Test
	public void rootOfComplexNumber() {
		ComplexNumber number = new ComplexNumber(2, 3);
		ComplexNumber[] newNumbers = number.root(3);
				
		Assert.assertEquals(1.45, newNumbers[0].getReal(), 1e-2);
		Assert.assertEquals(0.49, newNumbers[0].getImaginary(), 1e-2);
		Assert.assertEquals(-1.15, newNumbers[1].getReal(), 1e-2);
		Assert.assertEquals(1.01, newNumbers[1].getImaginary(), 1e-2);
		Assert.assertEquals(-0.29, newNumbers[2].getReal(), 1e-2);
		Assert.assertEquals(-1.50, newNumbers[2].getImaginary(), 1e-2);
	}

}
