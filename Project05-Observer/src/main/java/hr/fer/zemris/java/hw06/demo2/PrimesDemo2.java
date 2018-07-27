package hr.fer.zemris.java.hw06.demo2;

/**
 * Program that iterates trough PrimeCollection and
 * prints them out cartesian product of it.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class PrimesDemo2 {

	/**
	 * Method that first executes when program is started.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {

		PrimesCollection primesCollection = new PrimesCollection(2);

		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}

	}

}
