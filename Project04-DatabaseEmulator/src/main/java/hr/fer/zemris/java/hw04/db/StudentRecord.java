package hr.fer.zemris.java.hw04.db;

/**
 * Class that represents one student record.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class StudentRecord {
	/** Student's jmbag */
	private String jmbag;
	/** Student's last name */
	private String lastName;
	/** Student's first name */
	private String firstName;
	/** Student's final grade */
	private String finalGrade;
	
	/**
	 * Constructor that initializes one object.
	 * 
	 * @param jmbag Student's jmbag.
	 * @param lastName Student's last name.
	 * @param firstName Student's first name.
	 * @param finalGrade Student's final grade.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * @return Student's jmbag.
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Sets Student's jmbag.
	 * 
	 * @param jmbag Student's jmbag.
	 */
	public void setJmbag(String jmbag) {
		this.jmbag = jmbag;
	}
	
	/**
	 * @return Student's last name.
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Student's last name.
	 * 
	 * @param jmbag Student's last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return Student's first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Student's first name.
	 * 
	 * @param jmbag Student's first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return Student's final grade.
	 */
	public String getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Student's final grade.
	 * 
	 * @param jmbag Student's final grade.
	 */
	public void setFinalGrade(String finalGrade) {
		this.finalGrade = finalGrade;
	}

	/**
	 * Calculates student hash code.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * Calculates if provided StudentRecord object is
	 * equal to this one. Objects are compared based on their
	 * jmbag.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	/**
	 * Transforms student to string.
	 */
	@Override
	public String toString() {
		return "[" + jmbag + ", " + lastName  + ", " + firstName + ", " + finalGrade + "]";
	}
	
}
