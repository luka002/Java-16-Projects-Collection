package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Program that prints out various lists of
 * filtered student list.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class StudentDemo {

	/**
	 * Method where program start executing.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		List<String> lines = null;

		try {
			lines = Files.readAllLines(Paths.get("studenti.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<StudentRecord> records = convert(lines);

		long broj = vratiBodovaViseOd25(records);
		System.out.println("=========1.vratiBodovaViseOd25()=========");
		System.out.println(broj);

		long broj5 = vratiBrojOdlikasa(records);
		System.out.println("\n==========2.vratiBrojOdlikasa()===========");
		System.out.println(broj5);

		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		System.out.println("\n==========3.vratiListuOdlikasa()==========");
		odlikasi.forEach(record -> System.out.println(record.toString()));
		
		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		System.out.println("\n=====4.vratiSortiranuListuOdlikasa()======");
		odlikasiSortirano.forEach(record -> System.out.println(record.toString() + " => " + 
				(record.getLabPoints() + record.getLastTermPoints() + record.getMidTermPoints())));
		
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		System.out.println("\n=========5.vratiPopisNepolozenih()=========");
		nepolozeniJMBAGovi.forEach(record -> System.out.println(record.toString()));
		
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		System.out.println("\n======6.razvrstajStudentePoOcjenama()======");
		for (int i = 1; i <= 5; i++) {
			List<StudentRecord> list = mapaPoOcjenama.get(i);
			list.forEach(record -> System.out.println(record.toString()));
			System.out.println("==============================================================");
		}
		
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		System.out.println("\n====7.vratiBrojStudenataPoOcjenama()======");
		mapaPoOcjenama2.forEach((k, v) -> System.out.format("%d => %d%n", k, v));
		
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);		
		System.out.println("\n=========8.razvrstajProlazPad()===========");
		List<StudentRecord> list = prolazNeprolaz.get(true);
		list.forEach(record -> System.out.println(record.toString()));
		
		System.out.println("============================================");
		
		list = prolazNeprolaz.get(false);
		list.forEach(record -> System.out.println(record.toString()));
	}

	/**
	 * Method that calculates map which key is true or false for students
	 * that have passed the subject or not and value is list
	 * of students that have passed it or not.
	 * 
	 * @param records List of student records.
	 * @return map which key is true or false for students
	 * that have passed the subject or not and value is list
	 * of students that have passed it or not.
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream()
						.collect(Collectors.partitioningBy(s -> s.getGrade() > 1));
	}

	/**
	 * Method that calculates map which key is grade and value is number
	 * of students with that grade.
	 * 
	 * @param records List of student records.
	 * @return Map that key is grade and value is number
	 * of students with that grade.
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream()
						.collect(Collectors.toMap(s -> s.getGrade(), s -> 1 , (s1, s2) -> s1+s2 ));
	}

	/**
	 * Method that calculates map which key is grade and value is list
	 * of students with that grade.
	 * 
	 * @param records List of student records.
	 * @return Map that key is grade and value is list
	 * of students with that grade.
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream()
						.collect(Collectors.groupingBy(s -> s.getGrade()));
	}

	/**
	 * Method that calculates list of jmbags of students that 
	 * didn't pass the subject sorted by jmbag.
	 * 
	 * @param records List of student records.
	 * @return Sorted list of students of jmbag from students that didn't pass
	 * the subject.
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream()
						.filter(s -> s.getGrade() == 1)
						.map(s -> s.getJmbag())
						.sorted((s1, s2) -> s1.compareTo(s2))
						.collect(Collectors.toList());
	}

	/**
	 * Method that calculates sorted list of students that have final
	 * grade 5. 
	 * 
	 * @param records List of student records.
	 * @return Sorted list of students that have final grade 5.
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
						.filter(student -> student.getGrade() == 5)
						.sorted((s1, s2) -> {
							if (s1.getMidTermPoints() + s1.getLastTermPoints() + s1.getLabPoints() <
								s2.getMidTermPoints() + s2.getLastTermPoints() + s2.getLabPoints()) return 1;
							if (s1.getMidTermPoints() + s1.getLastTermPoints() + s1.getLabPoints() >
								s2.getMidTermPoints() + s2.getLastTermPoints() + s2.getLabPoints()) return -1;
							return 0;
						})
						.collect(Collectors.toList());
	}

	/**
	 * Method that calculates list of students that have final
	 * grade 5. 
	 * 
	 * @param records List of student records.
	 * @return List of students that have final grade 5.
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
						.filter(student -> student.getGrade() == 5)
						.collect(Collectors.toList());
	}

	/**
	 * Method that calculates number of students that 
	 * have final grade 5.
	 * 
	 * @param records List of student records.
	 * @return Number of students that have final grade 5.
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream()
						.filter(student -> student.getGrade() == 5)
						.count();
	}

	/**
	 * Method that calculates how many students have more
	 * than 25 points.
	 * 
	 * @param records List of student records.
	 * @return Number of students with more than 25 points.
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream()
				.filter(studet -> studet.getLabPoints() + studet.getMidTermPoints() + studet.getLastTermPoints() > 25)
				.count();
	}

	/**
	 * Method that converts list of strings into list
	 * of student records.
	 * 
	 * @param lines List of strings.
	 * @return List of student records.
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<>();

		for (String row : lines) {
			String[] record = row.split("\t");

			records.add(new StudentRecord(record[0], record[1], record[2], Double.parseDouble(record[3]),
					Double.parseDouble(record[4]), Double.parseDouble(record[5]), Integer.parseInt(record[6])));
		}

		return records;
	}

}
