package hr.fer.zemris.java.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Program that accepts user input and based on
 * that input filters database.
 * Query examples:
 * query jmbag="0000000003"
 * query lastName = "Blažić"
 * query firstName>"A" and lastName LIKE "B*ć"
 * query firstName>"A" and firstName<"C" and lastName LIKE "B*ć" and jmbag>"0000000002"
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class StudentDB {

	/**
	 * Program starts executing here.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		List<String> lines = null;

		try {
			lines = Files.readAllLines(Paths.get("examples/database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		StudentDatabase database = new StudentDatabase(lines);
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("> ");
			String input = scanner.nextLine();

			if (input.equals("exit")) {
				System.out.println("Goodbye!");
				scanner.close();
				System.exit(0);
			} else if (!input.substring(0, 6).matches("query\\s")) {
				System.err.println("Wrong query syntax.");
				continue;
			}

			List<StudentRecord> records = new ArrayList<>();
			int[] longestLength = { 0, 0 };

			try {
				QueryParser parser = new QueryParser(input.substring(6));

				if (parser.isDirectQuery()) {
					StudentRecord record = database.forJMBAG(parser.getQueriedJMBAG());
					records.add(record);

					System.out.println("Using index for record retrieval.");

					longestLength[0] = record.getLastName().length();
					longestLength[1] = record.getFirstName().length();
				} else {
					for (StudentRecord record : database.filter(new QueryFilter(parser.getQuery()))) {
						records.add(record);

						if (record.getLastName().length() > longestLength[0]) {
							longestLength[0] = record.getLastName().length();
						}

						if (record.getFirstName().length() > longestLength[1]) {
							longestLength[1] = record.getFirstName().length();
						}
					}
				}
			} catch (Exception e) {
				System.err.println("Wrong query syntax.");
			}

			print(records, longestLength);
		}

	}

	/**
	 * Method that prints filtered database.
	 * 
	 * @param records List of record to print.
	 * @param longestLength For resizing printing table.
	 */
	private static void print(List<StudentRecord> records, int[] longestLength) {
		if (records.size() > 0) {
			printBorder(longestLength);

			for (StudentRecord record : records) {
				System.out.print("| " + record.getJmbag() + " | ");

				System.out.print(record.getLastName());

				for (int i = record.getLastName().length(); i < longestLength[0]; i++) {
					System.out.print(" ");
				}

				System.out.print(" | ");

				System.out.print(record.getFirstName());

				for (int i = record.getFirstName().length(); i < longestLength[1]; i++) {
					System.out.print(" ");
				}

				System.out.println(" | " + record.getFinalGrade() + " |");
			}
			
			printBorder(longestLength);
		}

		System.out.println("Records selected: " + records.size());
	}

	/**
	 * Prints top and bottom border.
	 * 
	 * @param longestLength For resizing.
	 */
	private static void printBorder(int[] longestLength) {
		System.out.print("+============+");

		for (int i = 0; i < longestLength[0] + 2; i++) {
			System.out.print("=");
		}

		System.out.print("+");

		for (int i = 0; i < longestLength[1] + 2; i++) {
			System.out.print("=");
		}

		System.out.println("+===+");
	}

}
