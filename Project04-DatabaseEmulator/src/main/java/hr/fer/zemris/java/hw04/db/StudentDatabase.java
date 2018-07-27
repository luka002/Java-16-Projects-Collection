package hr.fer.zemris.java.hw04.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw04.collections.SimpleHashtable;

/**
 * Class that stores list of StudentRecord objects.
 * 
 * @author Luka Grgić
 * @version 1.0  
 */
public class StudentDatabase {
	/** List of StudentRecord objects. */
	private List<StudentRecord> studentRecords;
	/** Hash table that connects jmbag with integer */
	private SimpleHashtable<String, Integer> table;

	/**
	 * Constructor.
	 * 
	 * @param studentRecords List of records in string.
	 */
	public StudentDatabase(List<String> studentRecords) {
		this.studentRecords = new ArrayList<>(studentRecords.size());
		this.table = new SimpleHashtable<>(studentRecords.size());
		
		toList(studentRecords);
	}
	
	/**
	 * Transforms string list to StudentRecord list.
	 * 
	 * @param studentRecords List of records in string.
	 */
	private void toList(List<String> studentRecords) {
		int position = 0;
		
		for (String row : studentRecords) {
			String[] record = row.split("\t");
			
			this.table.put(record[0], position);
			this.studentRecords.add(new StudentRecord(record[0], record[1], record[2], record[3]));
			
			position++;
		}
		
	}

	/**
	 * Retrieves StudentRecord for provided jmbag.
	 * 
	 * @param jmbag Student's jmbag.
	 * @return StudentRecord od provided jmbag.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		if (table.get(jmbag) == null) return null;
		
		return studentRecords.get(table.get(jmbag));
	}
	
	/**
	 * Filters list for provided filter.
	 * 
	 * @param filter Provided filter.
	 * @return Filtered list.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> records = new ArrayList<>();
		
		for (StudentRecord record : studentRecords) {
			if (filter.accepts(record)) {
				records.add(record);
			}
		}
		
		return records;
	}
	
	
}
