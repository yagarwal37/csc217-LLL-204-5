package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Interface class which is to be used by LinkedQueue or ArrayQueue.
 * Contains 5 methods to be override
 * @author yash
 *
 * @param <E>  The type of data that will be stored into the Queue
 */
public interface Queue<E> {

	/**
	 * Adds the element to the back of the Queue
	 * @param element E data that is to be added to the Queue
	 * @throws IllegalArgumentException Exception if the capacity has been reached
	 */
	void enqueue(E element);
	
	/**
	 * Removes the element at the front of the Queue
	 * @return oldData E the original data at the front of the Queue
	 */
	E dequeue();
	
	/**
	 * Determines if the Queue is empty
	 * @return true if the Queue is empty. False otherwise
	 */
	boolean isEmpty();
	
	/**
	 * Returns the number of elements in the Queue
	 * @return size int the size of the Queue 
	 */
	int size();
	
	/**
	 * Sets the Queue’s capacity
	 * @param capacity int passed in parameter to set the capacity of the Queue
	 * @throws IllegalArgumentException Exception if the parameter is negative or less than the size of the Queue
	 */
	void setCapacity(int capacity);
	
}

