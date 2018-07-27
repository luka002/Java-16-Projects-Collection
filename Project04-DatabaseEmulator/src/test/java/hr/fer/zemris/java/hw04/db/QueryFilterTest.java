package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import hr.fer.zemris.java.hw04.db.QueryFilter;
import hr.fer.zemris.java.hw04.db.QueryParser;
import hr.fer.zemris.java.hw04.db.StudentDatabase;
import hr.fer.zemris.java.hw04.db.StudentRecord;

public class QueryFilterTest {

	@Test
	public void testfilter() {
		List<String> lines = null;

		try {
			lines = Files.readAllLines(Paths.get("examples/database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		StudentDatabase database = new StudentDatabase(lines);
		QueryParser parser = new QueryParser("lastName LIKE \"B*\"");

		List<StudentRecord> records = database.filter(new QueryFilter(parser.getQuery()));
		
		StudentRecord record1 = new StudentRecord("0000000002", "Bakamović", "Petra", "3");
		StudentRecord record2 = new StudentRecord("0000000003", "Bosnić", "Andrea", "4");
		StudentRecord record3 = new StudentRecord("0000000004", "Božić", "Marin", "5");
		StudentRecord record4 = new StudentRecord("0000000005", "Brezović", "Jusufadis", "2");
		
		assertEquals(record1, records.get(0));
		assertEquals(record2, records.get(1));
		assertEquals(record3, records.get(2));
		assertEquals(record4, records.get(3));
		
		assertTrue(records.size() == 4);
	}
	
}
