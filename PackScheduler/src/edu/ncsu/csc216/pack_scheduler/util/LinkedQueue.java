package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * LinkedAbstractList that uses the Queue interface
 * @author yash
 *
 * @param <E> The type of data that will be stored into the LinkedQueue
 */
public class LinkedQueue<E> implements Queue<E> {

	/**LinkedAbstractList of type E*/
	private LinkedAbstractList<E> list;
	
	/**
	 * Constructs a instance of LinkedQueue
	 * @param capacity int capacity of the LinkedQueue
	 */
	public LinkedQueue(int capacity){
		list = new LinkedAbstractList<E>(capacity);
	}


	/**
	 * Adds the element to the back of the Queue
	 * @param element E data that is to be added to the Queue
	 * @throws IllegalArgumentException Exception if the capacity has been reached
	 */
	@Override
	public void enqueue(E element) {
		list.add(size(), element);	
	}

	/**
	 * Removes the element at the front of the Queue
	 * @return oldData E the original data at the front of the Queue
	 */
	@Override
	public E dequeue() {
		if(!isEmpty()) {
			return list.remove(0);
		}
		else {
			throw new NoSuchElementException();
		}	}


	/**
	 * Determines if the Queue is empty
	 * @return true if the Queue is empty. False otherwise
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the number of elements in the Queue
	 * @return size int the size of the Queue 
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Sets the Queue’s capacity
	 * @param capacity int passed in parameter to set the capacity of the Queue
	 * @throws IllegalArgumentException Exception if the parameter is negative or less than the size of the Queue
	 */
	@Override
	public void setCapacity(int capacity) {
		list.setCapacity(capacity);
	}
}
