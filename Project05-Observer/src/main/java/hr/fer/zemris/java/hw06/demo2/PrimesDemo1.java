package hr.fer.zemris.java.hw06.demo2;

/**
 * Program that iterates trough PrimeCollection and
 * prints them out.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class PrimesDemo1 {

	/**
	 * Method that first executes when program is started.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {

		PrimesCollection primesCollection = new PrimesCollection(5);

		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}

	}

}
