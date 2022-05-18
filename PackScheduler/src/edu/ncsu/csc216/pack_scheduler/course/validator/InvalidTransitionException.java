package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * This class checks for the InvalidTransitionEception
 * 
 * @author tanmaysoni
 *
 */
public class InvalidTransitionException extends Exception {
	
	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;

	
	/** A default message thrown whenever there is an invalid state transition*/
	public static final String DEFAULT_MESSAGE = "Invalid FSM Transition.";
	
	/**
	 * This is a parameterized constructor for the Invalid Transition Exception carrying a custom message
	 * 
	 * @param message is the custom message
	 */
	public InvalidTransitionException (String message) {
		super(message);
	}
	
	/**
	 * This is a parameterless constructor for the Invalid Transition Exception having the DEFAULT_MESSAGE
	 */
	public InvalidTransitionException() {
		this(DEFAULT_MESSAGE);
	}
	
}
