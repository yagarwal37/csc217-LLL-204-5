package edu.ncsu.csc216.pack_scheduler.user;
import java.util.Objects;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * This is the Student Class. It incorporates each student's essential identity details,
 * being a subclass of the abstract User Class. 
 * Details include their first name, last name, id, email, password, and maximum credits
 * 
 * @author tanmaysoni 
 * @author tadeotheis
 * @author yashagarwal
 * 
 */
public class Student extends User implements Comparable<Student> {
	
	/** Personalized schedule for this student */
	private Schedule schedule;

	/** The maximum credits this student can enroll in */
	private int maxCredits;
	
	/** maximum possible credits any student can have */
	public static final int MAX_CREDITS = 18;
	
	/**
	 * Constructor for student class, calls the main constructor
	 * @param firstName student's first name
	 * @param lastName student's last name
	 * @param id student's id
	 * @param email student's email
	 * @param password student's password
	 * @param maxCredits are the Max Credits
	 */
	public Student(String firstName, String lastName, String id, String email, String password, int maxCredits) {
		super(firstName, lastName, id, email, password);
		setMaxCredits(maxCredits);
		schedule = new Schedule();
	}

	/**
	 * Constructor for student class, calls the main constructor and sets max credits to the default 18. 
	 * @param firstName student's first name
	 * @param lastName student's last name
	 * @param id student's id
	 * @param email student's email
	 * @param password student's password
	 */
	public Student(String firstName, String lastName, String id, String email, String password) {
		this(firstName, lastName, id, email, password, MAX_CREDITS);
	}

	/**
	 * Returns a student's max credits
	 * @return the maxCredits
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Checks id max credits is below 3 or above 18 and throws an IAE if it is, if not it sets the value to max credits.
	 * @param maxCredits the maxCredits to set
	 * @throws IllegalArgumentException if maxCredits is below 3 or above 18. 
	 */
	public void setMaxCredits(int maxCredits) {
		if(maxCredits < 3 || maxCredits > 18) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}
	
	/**
	 * Returns the user's class schedule for interacting with the GUI
	 * @return schedule the user's class schedule
	 */
	public Schedule getSchedule() {
		return this.schedule;
	}

	
	/**
	 * Overriding the hashCode method of the Student class 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(maxCredits);
		return result;
	}

	/**
	 * Overriding the equals method of the Student class 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return maxCredits == other.maxCredits;
	}

	/**
	 * Create a String of all the fields in the Student class separated by commas
	 * 
	 * @return finalOutput returns a String of all the fields in the Student class separated by commas
	 */
	@Override
	public String toString() {
		String finalOutput = getFirstName() + "," + getLastName() + "," + getId() + ","
							+ getEmail() + "," + getPassword() + "," + maxCredits;
		return finalOutput;
	}
	
	/**
	 * Compares Student Object s with  
	 * 
	 * @return an integer value
	 */
	@Override
	public int compareTo(Student s) {
		if(s == null) {
			throw new NullPointerException();
		}
		
		//Last Name
		
		if(s.getLastName().length() == this.getLastName().length()) {
			for(int i = 0; i < s.getLastName().length(); i++) {
				
				int ascii1 = (int) s.getLastName().charAt(i);
				int ascii2 = (int) this.getLastName().charAt(i);
				
				if (ascii1 > ascii2) {
					return -1;
				} else if (ascii2 > ascii1) {
					return 1;
				}
			}
		} else if (s.getLastName().length() > this.getLastName().length()) {
			for(int i = 0; i < this.getLastName().length(); i++) {
				
				int ascii1 = (int) s.getLastName().charAt(i);
				int ascii2 = (int) this.getLastName().charAt(i);
				
				if (ascii1 > ascii2) {
					return -1;
				} else if (ascii2 > ascii1) {
					return 1;
				}
			}
			return -1;
		} else if (s.getLastName().length() < this.getLastName().length()) {
			for(int i = 0; i < s.getLastName().length(); i++) {
				
				int ascii1 = (int) s.getLastName().charAt(i);
				int ascii2 = (int) this.getLastName().charAt(i);
				
				if (ascii1 > ascii2) {
					return -1;
				} else if (ascii2 > ascii1) {
					return 1;
				}
			
			}
			return 1;
		}
		
		//First Name
		
				if(s.getFirstName().length() == this.getFirstName().length()) {
					for(int i = 0; i < s.getFirstName().length(); i++) {
						
						int ascii1 = (int) s.getFirstName().charAt(i);
						int ascii2 = (int) this.getFirstName().charAt(i);
						
						if (ascii1 > ascii2) {
							return -1;
						} else if (ascii2 > ascii1) {
							return 1;
						}
					}
				} else if (s.getFirstName().length() > this.getFirstName().length()) {
					for(int i = 0; i < this.getFirstName().length(); i++) {
						
						int ascii1 = (int) s.getFirstName().charAt(i);
						int ascii2 = (int) this.getFirstName().charAt(i);
						
						if (ascii1 > ascii2) {
							return -1;
						} else if (ascii2 > ascii1) {
							return 1;
						}
					}
					return -1;
					
				} else if (s.getFirstName().length() < this.getFirstName().length()) {
					for(int i = 0; i < s.getFirstName().length(); i++) {
						
						int ascii1 = (int) s.getFirstName().charAt(i);
						int ascii2 = (int) this.getFirstName().charAt(i);
						
						if (ascii1 > ascii2) {
							return -1;
						} else if (ascii2 > ascii1) {
							return 1;
						}
					
					}
					return 1;
				}
				
				//Student ID
				
				if(s.getId().length() == this.getId().length()) {
					for(int i = 0; i < s.getId().length(); i++) {
						
						int ascii1 = (int) s.getId().charAt(i);
						int ascii2 = (int) this.getId().charAt(i);
						
						if (ascii1 > ascii2) {
							return -1;
						} else if (ascii2 > ascii1) {
							return 1;
						}
					}
				} else if (s.getId().length() > this.getId().length()) {
					for(int i = 0; i < this.getId().length(); i++) {
						
						int ascii1 = (int) s.getId().charAt(i);
						int ascii2 = (int) this.getId().charAt(i);
						
						if (ascii1 > ascii2) {
							return -1;
						} else if (ascii2 > ascii1) {
							return 1;
						}
					}
					return -1;
					
				} else if (s.getId().length() < this.getId().length()) {
					for(int i = 0; i < s.getId().length(); i++) {
						
						int ascii1 = (int) s.getId().charAt(i);
						int ascii2 = (int) this.getId().charAt(i);
						
						if (ascii1 > ascii2) {
							return -1;
						} else if (ascii2 > ascii1) {
							return 1;
						}
					
					}
					return 1;
				}
				return 0;
		
		
	}
	
	/**
	 * Checks if a course can be added to the Student's schedule. Returns false if the course is a duplicate,
	 * conflicts, or if it causes the student to exceed their maximum allowed credits.
	 * @param course The course that will be checked if it can be added to the Student's schedule.
	 * @return True if it can be added to the student's schedule. False if a duplicate, conflict, or exceed maximum
	 * allowed credits.
	 */
	public boolean canAdd (Course course) {
		
		if(schedule.getScheduleCredits() + course.getCredits() > maxCredits) {
			return false;
		}
		
		return schedule.canAdd(course);
		
	}
}