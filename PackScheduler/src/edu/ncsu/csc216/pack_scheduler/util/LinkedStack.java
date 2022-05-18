package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Stack type class created by imposing limitations on an LinkedAbstractList field so
 * that values can only be accessed from the most recent additions.
 * In this implementation, the top of the stack is the final node.
 * @author Hunter Pruitt
 *
 * @param <E> Type of data to be stored in the Stack
 */
public class LinkedStack<E> implements Stack<E> {
	
	/** Linked List storing the Stack elements */
	private LinkedAbstractList<E> list;
	
	/**
	 * Creates the LinkedStack object using the passed capacity.
	 * @param capacity max number of elements allowed in the stack
	 */
	public LinkedStack(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
	}
	
	/**
	 * Adds an element to the top of the stack at the end of the Linked container.
	 * 
	 * @param element element to add to top of stack
	 * @throws NullPointerException if the element is null
	 * @throws IndexOutOfBoundsException if the index is less than zero(0), or more than the size of the ArrayList
	 * @throws IllegalArgumentException if capacity has been reached
	 */
	@Override
	public void push(E element) {
		list.add(element);
	}

	/**
	 * Returns the top element of the stack, located at the end of the Linked List container.
	 * @return top element
	 * @throws EmptyStackException if stack is empty
	 */
	@Override
	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		} else {
			return list.remove(size() - 1);
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
		list.setCapacity(capacity);
	}

}
