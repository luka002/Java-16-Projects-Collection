package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import hr.fer.zemris.java.hw04.db.StudentDatabase;
import hr.fer.zemris.java.hw04.db.StudentRecord;

public class StudentDatabaseTest {
	static List<String> lines = null;
	static StudentDatabase database;
	
	@BeforeClass
	public static void initialize() {
		try {
			lines = Files.readAllLines(Paths.get("examples/database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		database = new StudentDatabase(lines);
	}
	
	@Test
	public void testforJMBAG() {
		StudentRecord record1 = new StudentRecord("0000000012", "Franković", "Hrvoje", "5");
		StudentRecord record2 = new StudentRecord("0000000033", "Mirković", "Vlado", "8");
		
		assertEquals(record1, database.forJMBAG("0000000012"));
		assertNotEquals(record2, database.forJMBAG("0000000012"));
	}

	@Test
	public void testFilterForTrue() {
		List<StudentRecord> records = database.filter(record -> true);
		
		assertEquals(lines.size(), records.size());
	}
	
	@Test
	public void testFilterForFalse() {
		List<StudentRecord> records = database.filter(record -> false);
		
		assertEquals(0, records.size());
	}
	
}
