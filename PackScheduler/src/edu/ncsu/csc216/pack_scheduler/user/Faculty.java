package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * Class defining Faculty subclass of User superclass, modeled after the Student class, 
 * with the key difference being that Faculty has a maxCourses field rather than a maxCredits field.
 * @author Hunter Pruitt
 */
public class Faculty extends User {

	/** Max number of courses for this faculty member */
	private int maxCourses;
	
	/** Maximum possible number of courses that maxCourses can be set to */
	public static final int MAX_COURSES = 3;
	
	/** Minimum possible number of courses that maxCourses can be set to */
	public static final int MIN_COURSES = 1;
	/** Schedule for each instance of Faculty*/
	private FacultySchedule schedule;
	
	/**
	 * Constructor for faculty class, calls the main 
	 * constructor and sets the child class field maxCourses
	 * @param firstName faculty member's first name
	 * @param lastName faculty member's last name
	 * @param id faculty member's id
	 * @param email faculty member's email
	 * @param password faculty member's password
	 * @param maxCourses max number of courses this faculty member can teach
	 * @throws IllegalArgumentException if passed a maxCourses field outside the bounds
	 */
	public Faculty(String firstName, String lastName, String id, String email, 
			String password, int maxCourses) {
		super(firstName, lastName, id, email, password);
		setMaxCourses(maxCourses);
		schedule = new FacultySchedule(id);
	}

	/**
	 * Constructor for faculty class, calls the main constructor and sets maxCourses to the default 3. 
	 * @param firstName faculty member's first name
	 * @param lastName faculty member's last name
	 * @param id faculty member's id
	 * @param email faculty member's email
	 * @param password faculty member's password
	 */
	public Faculty(String firstName, String lastName, String id, String email, String password) {
		this(firstName, lastName, id, email, password, MAX_COURSES);
	}
	
	
	/**
	 * Returns the max number of courses this faculty member can teach
	 * @return the maxCourses
	 */
	public int getMaxCourses() {
		return maxCourses;
	}

	/**
	 * Sets the maxCourses field.
	 * @param max value to set maxCourses field to
	 * @throws IllegalArgumentException if maxCredits is below 1 or above 3. 
	 */
	public void setMaxCourses(int max) {
		if (max > 3 || max < 1) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.maxCourses = max;
	}

	/**
	 * Overriding the hashCode method of the Faculty class 
	 * Alters functionality of the hashCode method in the Object class by simplifying the Hash Code of
	 * the Faculty object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}

	/**
	 * Overriding the equals method of the Faculty class 
	 * Compares reference, value, and class of the "this" and obj parameter
	 * @param obj Object Faculty parameter passed in to be compared to the "this" object
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		return maxCourses == other.maxCourses;
	}


	/**
	 * Create a String of all the fields in the Faculty class separated by commas
	 * 
	 * @return finalOutput returns a String of all the fields in the Faculty class separated by commas
	 */
	@Override
	public String toString() {
		String finalOutput = getFirstName() + "," + getLastName() + "," + getId() + ","
							+ getEmail() + "," + getPassword() + "," + getMaxCourses();
		return finalOutput;
	}
	
	/**
	 * Returns the current instance of schedule 
	 * @return schedule returns the schedule field 
	 */
	public FacultySchedule getSchedule() {
		return schedule;
	}
	
	/**
	 * Checks if the number of schedule courses is greater than MAX_COURSES 
	 * @return true if the number of schedule courses is greater than MAX_COURSES. False otherwise.
	 */
	public boolean isOverloaded() {
		return schedule.getNumScheduledCourses() > maxCourses;
		
	}
}
