package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;

/**
 * Class that calculates number n of first primes.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class PrimesCollection implements Iterable<Integer> {
	/** Number of primes to calculate */
	private int primes;
	/** Number one less than first prime number */
	private final static int START = 1;

	/**
	 * Constructor that initializes object.
	 * 
	 * @param primes Number of primes to calculate .
	 */
	public PrimesCollection(int primes) {
		if (primes < 1)
			throw new IndexOutOfBoundsException("Number of primes has to be at least 1");

		this.primes = primes;
	}

	/**
	 * Method that creates class that implements Iterator.
	 * 
	 * @return PrimesCollectionIterator that implement Iterator.
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new PrimesCollectionIterator();
	}

	/**
	 * Class that implements Iterator and provides
	 * iterating trough number of first primes.
	 * 
	 * @author Luka Grgić
	 * @version 1.0
	 */
	private class PrimesCollectionIterator implements Iterator<Integer> {
		/** Last calculated prime */
		private int currentPrime;
		/** Primes computes */
		private int primesComputed;

		/**
		 * Constructor that initializes object.
		 */
		public PrimesCollectionIterator() {
			this.currentPrime = START;
			this.primesComputed = 0;
		}

		/**
		 * Method that checks if there are more primes to
		 * calculate.
		 * 
		 * @return true if there is more primes to calculate, 
		 * else false.
		 */
		@Override
		public boolean hasNext() {
			return primesComputed < primes;
		}

		/**
		 * Method that calculates next prime number.
		 * 
		 * @return Next calculated prime.
		 */
		@Override
		public Integer next() {
			boolean isPrime = true;

			while (true) {
				currentPrime++;
				
				for (int i = 2, n = (int) Math.sqrt(currentPrime); i <= n; i++) {
					if ((currentPrime % i) == 0) {
						isPrime = false;
						break;
					}
				}
				
				if (isPrime) break;
				isPrime = true;
			}
			
			primesComputed++;
			return currentPrime;
		}

	}

}
