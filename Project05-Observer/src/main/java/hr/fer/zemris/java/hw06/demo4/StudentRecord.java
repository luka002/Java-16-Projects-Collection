package hr.fer.zemris.java.hw06.demo4;

/**
 * Class that represents data from a student.
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
	/** Student's mid term points */
	private double midTermPoints;
	/** Student's last term points */
	private double lastTermPoints;
	/** Student's lab points */
	private double labPoints;
	/** Student's grade */
	private int grade;
	
	/**
	 * Constructor that initializes object.
	 * 
	 * @param jmbag Student's jmbag.
	 * @param lastName Student's last name.
	 * @param firstName Student's first name.
	 * @param midTermPoints Student's mid term points.
	 * @param lastTermPoints Student's last term points.
	 * @param labPoints Student's lab points.
	 * @param grade Student's grade.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, double midTermPoints, double lastTermPoints,
			double labPoints, int grade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.midTermPoints = midTermPoints;
		this.lastTermPoints = lastTermPoints;
		this.labPoints = labPoints;
		this.grade = grade;
	}

	/**
	 * @return Returns student's jmbag.
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * @return Returns student's last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return Retruns student's first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return Returns student's mid term points.
	 */
	public double getMidTermPoints() {
		return midTermPoints;
	}

	/**
	 * @return Returns student's last term points.
	 */
	public double getLastTermPoints() {
		return lastTermPoints;
	}

	/**
	 * @return Returns student's lab points.
	 */
	public double getLabPoints() {
		return labPoints;
	}

	/**
	 * @return Returns student's grade.
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * Transforms object to string.
	 * 
	 * @return Returns object in string.
	 */
	@Override
	public String toString() {
		return "[" + jmbag + ",  " + lastName + ",  " + firstName + ",  "
				+ midTermPoints + ",  " + lastTermPoints + ",  " + 
				labPoints + ",  " + grade + "]"; 
	}

}