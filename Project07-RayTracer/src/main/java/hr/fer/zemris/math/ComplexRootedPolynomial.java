package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Model of complex number polynom where every
 * complex number represents nullpoint. Polynom is of
 * form  (z-z1)*(z-z2)*...*(z-zn).
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ComplexRootedPolynomial {
	
	/** List of complex numbers */
	List<Complex> list;
	
	/**
	 * Constructor.
	 * 
	 * @param roots colmplex numbers.
	 */
	public ComplexRootedPolynomial(Complex ...roots) {
		Objects.requireNonNull(roots, "Complex can not be null");
		list = new ArrayList<>();
		
		for (Complex c : roots) {
			list.add(c);
		}
	}
	
	
	/** 
	 * Computes polynomial value at given point z
	 * 
	 * @param z point
	 * @return polynomial value
	 */
	public Complex apply(Complex z) {
		List<Complex> newList = new ArrayList<>();
		
		for (Complex c : list) {
			newList.add(z.sub(c));
		}
		Complex complex = newList.get(0);
		
		for (int i = 1; i < newList.size(); i++) {
			complex = complex.multiply(newList.get(i));
		}
		
		return complex;
	}
	
	/** 
	 * Converts this representation to ComplexPolynomial type.
	 * 
	 * @return Converted representation to ComplexPolynomial type.
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial c = new ComplexPolynomial(list.get(0).negate(), Complex.ONE);
		
		for (int i = 1; i < list.size(); i++) {
			c = c.multiply(new ComplexPolynomial(list.get(i).negate(), Complex.ONE));
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
	
	/** 
	 * finds index of closest root for given complex number z that is within
	 * threshold; if there is no such root, returns -1.
	 * 
	 * @param z complex number.
	 * @param treshold threshold
	 * @return index of closest root or -1 if not found
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		double smallestDistance = treshold;
		
		for (int i = 0; i < list.size(); i++) {
			if (Math.abs(list.get(i).sub(z).module()) <= smallestDistance) {
				index = i;
			}
		}
		
		return index;
	}
	
}
