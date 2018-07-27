package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Model of complex number polynom with form
 * form zn*zn+zn-1*zn-1+...+z2*z2+z1*z+z0 where
 * z0 to zn represents coefficient of corresponding
 * exponent of z.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ComplexPolynomial {
	
	/** List of complex numbers */
	private List<Complex> list;

	/**
	 * Constructor.
	 * 
	 * @param factors complex numbers.
	 */
	public ComplexPolynomial(Complex... factors) {
		Objects.requireNonNull(factors, "Complex can not be null");
		list = new ArrayList<>();

		for (Complex c : factors) {
			list.add(c);
		}
	}

	/**
	 * @return returns list of complex numbers.
	 */
	public List<Complex> getList() {
		return list;
	}

	/**
	 * @return returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3.
	 */
	public short order() {
		return (short) (list.size()-1);
	}

	/**
	 * Computes a new polynomial this*p
	 * 
	 * @param p other polynomial
	 * @return this*p polynimial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		List<Complex> polynomial = p.getList();
		int size = list.size() + polynomial.size() -1;
		List<Complex> newPolynomial = new ArrayList<>(Collections.nCopies(size, null));
		
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < polynomial.size(); j++) {
				
				Complex pol = list.get(i).multiply(polynomial.get(j));
				
				if (newPolynomial.get(i+j) == null) {
					newPolynomial.set(i+j, pol);
				} else {
					newPolynomial.set(i+j, newPolynomial.get(i+j).add(pol));
				}
		
			}
		}
		
		Complex[] cp = new Complex[size];
		return new ComplexPolynomial(newPolynomial.toArray(cp));
	}

	/**
	 * Computes first derivative of this polynomial; 
	 * for example, for (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * 
	 * @return ComplexPolynomial that is first derivative
	 * of this polynomial.
	 */
	public ComplexPolynomial derive() {
		List<Complex> newPolynomial = new ArrayList<>();
		
		for (int i = 1; i < list.size(); i++) {
			newPolynomial.add(list.get(i).multiply(new Complex(i, 0)));
		}
		
		Complex[] cp = new Complex[newPolynomial.size()];
		return new ComplexPolynomial(newPolynomial.toArray(cp));
	}

	/**
	 * Computes polynomial value at given point z
	 * 
	 * @param z given complex number.
	 * @return calculated complex number.
	 */
	public Complex apply(Complex z) {
		Complex c = list.get(0);
		
		for (int i = 1; i < list.size(); i++) {
			c = c.add(list.get(i).multiply(z.power(i))); 
		}
		
		return c;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return list.toString();
	}
}