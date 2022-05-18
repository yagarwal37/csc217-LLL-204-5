package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * ArrayList that uses the Queue interface
 * @author yash
 *
 * @param <E> The type of data that will be stored into the ArrayQueue
 */
public class ArrayQueue<E> implements Queue<E> {

	/**ArrayList of type E*/
	private ArrayList<E> list;
	/**Set capacity of the ArrayQueue*/
	private int capacity;
	
	/**
	 * Constructs a instance of ArrayQueue
	 * @param capacity int capacity of the ArrayQueue
	 */
	public ArrayQueue(int capacity){
		list = new ArrayList<E>();
		setCapacity(capacity);
	}
	
	/**
	 * Adds the element to the back of the ArrayQueue
	 * @param element E data that is to be added to the ArrayQueue
	 * @throws IllegalArgumentException Exception if the capacity has been reached
	 */
	@Override
	public void enqueue(E element) {
		if(size() < capacity) {
			list.add(element);
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Removes the element at the front of the ArrayQueue
	 * @return oldData E the original data at the front of the ArrayQueue
	 */
	@Override
	public E dequeue() {
		if(!isEmpty()) {
			return list.remove(0);
		}
		else {
			throw new NoSuchElementException();
		}
	}
	
	/**
	 * Determines if the ArrayQueue is empty
	 * @return true if the ArrayQueue is empty. False otherwise
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Returns the number of elements in the ArrayQueue
	 * @return size int the size of the ArrayQueue 
	 */
	@Override
	public int size() {
		return list.size();
	}
	
	/**
	 * Sets the ArrayQueue’s capacity
	 * @param capacity int passed in parameter to set the capacity of the ArrayQueue
	 * @throws IllegalArgumentException Exception if the parameter is negative or less than the size of the ArrayQueue
	 */
	@Override
	public void setCapacity(int capacity) {
		if(capacity < 0 || capacity < size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}
	
}
