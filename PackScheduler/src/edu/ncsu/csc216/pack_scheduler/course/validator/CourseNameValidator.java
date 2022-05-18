/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * CourseNameValidator ensures a Course meets the following requirements:
 * 1 to 4 letters, followed by 3 numbers, optionally followed a suffix 1 letter
 * suffix. It implements this check using a FSM using private inner classes. If
 * the CourseName results in an invalid transition, the class will thrown an InvalidTransitionException
 * and if the name doesn't end in a valid state, it will return false. If the name is valid the isValid
 * method will return true.
 * 
 * @author John Nolan
 *
 */
public class CourseNameValidator {
	/**
	 *  Boolean is true if the name is on a valid end state with 3 numbers or in the sufffix state.
	 *  Otherwise, it will be false.
	 */
	private boolean validEndState;
	/**
	 * The number of letters at the start of the course name. Initializes to 0 each time isValid is
	 * ran and increments from each letter at the start. Should end between MIN_LETTERS and MAX_LETTERS
	 * to be a valid name.
	 */
	private int letterCount;
	/**
	 * The number of digits following the course prefix. Should be equal to EXPECTED_DIGITS to be a valid name.
	 */
	private int digitCount;

	
	
	/**
	 * The number of digits expected in the course name.
	 */
	private static final int EXPECTED_DIGITS = 3;
	/**
	 * The maximum number of letters that should be at the start of a course name.
	 */
	private static final int MAX_LETTERS = 4;
	
	/**
	 * The constant for the initial state of the CourseNameValidator, prior to receiving any characters.
	 */
	private final InitialState initial = new InitialState();
	/**
	 * The constant for the letter state of the CourseNameValidator, during the letter prefix of the CourseName
	 */
	private final LetterState letter = new LetterState();
	/**
	 * The constant for the number state of the CourseNameValidator, during the number section of the CourseName
	 */
	private final NumberState number = new NumberState();
	/**
	 * The constant for the suffix state of CourseNameValidator, at the end of the course name.
	 */
	private final SuffixState suffix = new SuffixState();
	
	/**
	 * The variable to store the current state of the finite state machine. Is altered to the initial,
	 * letter, number, and suffix states depending on the character inputs.
	 */
	private State state;
	
	/** The parameterless constructor for the CourseNameValidator*/
	public CourseNameValidator() {
		//No fields need to be set on construction
	}
	
	/**
	 * The method returns true if the course name is valid and meets all the requirements.
	 * The course name must contain 1 to 4 letters, followed by 3 numbers, 
	 * optionally followed by a suffix letter. The method utilizes a finite state machine
	 * to accomplish this going character by character. If the course name causes an invalid
	 * transition between states it will throw an InvalidTransitionException. If the finite
	 * state machine ends in at a state that is not an end state it will return false.
	 * @param name The course name whose validity should be tested.
	 * @return True if a valid name, false if it ends at a non-ending state.
	 * @throws InvalidTransitionException Thrown if the name causes an invalid transition between
	 * different states.
	 */
	public boolean isValid(String name) throws InvalidTransitionException {
		//Initializes variables for new check
		state = initial;
		letterCount = 0;
		digitCount = 0;
		
		//Runs appropriate method depending on next character in course name
		for(int i = 0; i < name.length(); i++) {
			char currentChar = name.charAt(i);
			if(Character.isDigit(currentChar)) {
				state.onDigit();
			}
			else if(Character.isLetter(currentChar)) {
				state.onLetter();
			}
			else {
				state.onOther();
			}
		}
		
		//Return if it ended on a valid end state
		return validEndState;
	}
	
	/**
	 * The state abstract class represents the different states that the finite state machine
	 * can go through. It contains methods for whether the next character is a letter, digit, or
	 * other to cause the appropriate transition or error.
	 * @author John Nolan
	 *
	 */
	private abstract class State {
		/**
		 * Constructs a new state and initializes it to the initial state
		 */
		public State() {
			state = initial;
		}
		
		/**
		 * The method ran when the next character is a letter. Throws an exception if
		 * the letter is not expected.
		 * @throws InvalidTransitionException Thrown when the state was not expecting a letter.
		 * This always occurs if the method is ran in the suffix state. The exception can
		 * be thrown in the letter or digit states if there becomes and invalid number of letters or digits
		 * respectively.
		 */
		public abstract void onLetter() throws InvalidTransitionException;
		
		/**
		 * The method ran when the next character is a digit. Can throw an exception if a digit
		 * was unexpected.
		 * @throws InvalidTransitionException Thrown when the state was not expecting a digit.
		 * Always occurs in the suffix or number state. Can occur in the number or letter state
		 * if it causes an incorrect number of digits or letters respectively.
		 */
		public abstract void onDigit() throws InvalidTransitionException;
		
		/**
		 * Ran if the next character is not a letter or digit. Will always throw an exception
		 * because course name should not contain these characters.
		 * @throws InvalidTransitionException Always thrown because the character is invalid.
		 */
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}
	}
	
	/**
	 * The state for when the finite state machine is at the prefix of the course name.
	 * @author John Nolan
	 *
	 */
	private class LetterState extends State {
		/**
		 * When a letter is received in the letter state, the letter count is incremented and
		 * it is checked that this letter did not cause the letters to exceed the maximum letter
		 * count.
		 * @throw InvalidTransitionException Thrown when the letter count exceed the MAX_LETTERS
		 */
		public void onLetter() throws InvalidTransitionException {
			letterCount++;
			if(letterCount > MAX_LETTERS)
			{
				throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			}
		}
		
		/**
		 * When a digit is received in the letter state, it transitions to the number state and
		 * increments the digitCount for the first number.
		 */
		public void onDigit() {
			state = number;
			digitCount++;
		}
	}
	
	/**
	 * The state for while at the suffix character. If this state receives any more characters,
	 * it will thrown an InvalidTransitionException since no more characters should exist.
	 * @author John Nolan
	 *
	 */
	private class SuffixState extends State {
		/**
		 * If another letter is added to the course name, an exception is thrown.
		 * @throw InvalidTransitionException Always thrown in suffix state since no more letters
		 * should exist.
		 */
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
		}

		/**
		 * If another digit is added to the course name, an exception is thrown.
		 * @throw InvalidTransitionException Always thrown in suffix state since no more digits
		 * should exist.
		 */
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
		}
	}
	
	/**
	 * Beginning state of FSM before any letters are received.
	 * @author John Nolan
	 *
	 */
	private class InitialState extends State {
		/**
		 * When a letter is received in the intial state, the letter count is incremented for the
		 * first letter then the state is transition into the letter state.
		 */
		public void onLetter() {
			letterCount++;
			state = letter;
		}
		
		/**
		 * If a digit is received in the initial state, an exception is thrown since the first
		 * character should not be a number.
		 * @throw InvalidTransitionException Always thrown since course name should not begin with
		 * a number.
		 */
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must start with a letter.");
		}
	}
	
	/**
	 * The number state of the FSM during the period of the course name that only contains EXPECTED_DIGITS numbers.
	 * Will set valid end state if exactly three numbers are added. Will throw an InvalidTransitionException
	 * if too many digits are added or a letter is added while not at three digits.
	 * @author John Nolan
	 *
	 */
	private class NumberState extends State {
		/**
		 * When a letter is received in the number state, it transitions to the suffix state
		 * if and only if there are exactly 3 numbers in the course name. Otherwise, 
		 * an InvalidTransitionException will be thrown.
		 * @throws InvalidTransitionException If the digitCount is not 3 when the suffix is added.
		 */
		public void onLetter() throws InvalidTransitionException {
			if(digitCount != EXPECTED_DIGITS)
			{
				throw new InvalidTransitionException("Course name must have 3 digits.");
			}
			state = suffix;
		}
		
		/**
		 * When a digit is added in the number state the digit count is incremented and it remains in
		 * the number state. If the digit count exceeds the EXPECTED_DIGITS then an InvalidTransitionException
		 * is thrown. The the digits becomes EXPECTED_DIGITS it will set validEndState to true, which will
		 * also remain true if a suffix is added.
		 * @throws InvalidTransitionException Thrown if a digit is added that causes the digit count to
		 * exceed the EXPECTED_DIGITS.
		 */
		public void onDigit() throws InvalidTransitionException {
			digitCount++;
			if(digitCount > EXPECTED_DIGITS)
			{
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}
			if(digitCount == EXPECTED_DIGITS)
			{
				validEndState = true;
			}
		}
	}
}
