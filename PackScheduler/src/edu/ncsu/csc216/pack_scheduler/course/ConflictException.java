/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Exception for when there are conflicting activities
 * @author tadeotheis
 *
 */
public class ConflictException extends Exception {

	/**
	 * D used for serialization.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Parameterized constructor for ConflictException passes a custom message up for the exception object
	 * @param message message for a schedule conflict
	 */
	public ConflictException(String message) {
		super(message);
	}

	/**
	 * Constructor for ConflictException passes a default string to the parameterized constructor
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}
}
