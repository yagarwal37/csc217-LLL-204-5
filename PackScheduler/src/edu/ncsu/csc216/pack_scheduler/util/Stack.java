package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Abstract interface for Stack type collections, in which elements can only 
 * be accessed if they are "on top."
 * Also includes field related methods as well as accessors.
 * @author Hunter Pruitt
 *
 * @param <E> Type of data to be stored in the Stack
 */
public interface Stack<E> {

	/**
	 * Adds an element to the top of the stack.
	 * 
	 * @param element element to add to top of stack
	 * @throws IllegalArgumentException if capacity has been reached
	 */
	void push(E element);
	
	/**
	 * Returns the top element of the stack.
	 * 
	 * @return top element
	 */
	E pop();
	
	/**
	 * Indicates if the Stack is empty
	 * @return boolean indicator
	 */
	boolean isEmpty();
	
	/**
	 * Indicates the number of elements in the stack.
	 * @return number of elements in the stack
	 */
	int size();
	
	/**
	 * Sets the max number of elements to be contained.
	 * @param capacity value indicating maximum elements
	 * @throws IllegalArgumentException if capacity is negative or less than size
	 */
	void setCapacity(int capacity);
}
