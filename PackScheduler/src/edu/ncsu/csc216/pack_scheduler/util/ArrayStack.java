package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Stack type class created by imposing limitations on an ArrayList field so
 * that values can only be accessed from the most recent additions.
 * @author Hunter Pruitt
 *
 * @param <E> Type of data to be stored in the Stack
 */
public class ArrayStack<E> implements Stack<E> {

	/** Max number of elements that can be added */
	private int capacity;
	
	/** ArrayList storing the Stack elements */
	private ArrayList<E> list;
	
	/**
	 * Creates the ArrayStack object using the passed capacity.
	 * @param capacity max number of elements allowed in the stack
	 */
	public ArrayStack(int capacity) {
		setCapacity(capacity);
		list = new ArrayList<E>();
		
	}

	/**
	 * Adds an element to the top of the stack.
	 * 
	 * @param element element to add to top of stack
	 * @throws IllegalArgumentException if capacity has been reached
	 */
	@Override
	public void push(E element) {
		if (list.size() == capacity) {
			throw new IllegalArgumentException();
		} else {
			list.add(0, element);
		}
	}
	
	
	/**
	 * Returns the top element of the stack.
	 * 
	 * @return top element
	 * @throws EmptyStackException if stack is empty
	 */
	@Override
	public E pop() {
		if (list.isEmpty()) {
			throw new EmptyStackException();
		} else {
			return list.remove(0);
		}
	}

	
	/**
	 * Indicates if the Stack is empty
	 * @return boolean indicator
	 */
	@Override
	public boolean isEmpty() {
		return list.size() == 0 || list == null;
	}
	
	/**
	 * Indicates the number of elements in the stack.
	 * @return number of elements in the stack
	 */
	@Override
	public int size() {
		if (list != null) {
			return list.size();	
		} else {
			return 0;
		}
	}
	
	/**
	 * Sets the max number of elements to be contained.
	 * @param capacity value indicating maximum elements
	 * @throws IllegalArgumentException if capacity is negative or less than size
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < size()) {
			throw new IllegalArgumentException();
		} else {
			this.capacity = capacity;
		}
	}
	
}
