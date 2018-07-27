package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hr.fer.zemris.java.hw04.db.FieldValueGetters;
import hr.fer.zemris.java.hw04.db.StudentRecord;

public class FieldValueGettersTest {

	@Test
	public void testFieldValueGetters() {
		StudentRecord record = new StudentRecord("0000001234", "Marković", "Štef", "5");

		assertEquals("Štef", FieldValueGetters.FIRST_NAME.get(record));
		assertEquals("Marković", FieldValueGetters.LAST_NAME.get(record));
		assertEquals("0000001234", FieldValueGetters.JMBAG.get(record));
	}
	
}
