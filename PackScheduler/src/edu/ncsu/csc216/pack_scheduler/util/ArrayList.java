package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/** 
 * This is the ArraysList class which extends the AbstractList
 * 
 * @param <E> object of the AbstractList class
 */
public class ArrayList<E> extends AbstractList<E> {

	/** The default initial size of the ArrayList*/
	private static final int INIT_SIZE = 10;
	/** Array List of type E*/
	private E[] list;
	/** Size of the ArrayList*/
	private int size;
	
	
	
	/**
	 * Is the ArrayList constructor of the ArrayList Object
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		this.size = 0;
		list = (E[]) new Object[INIT_SIZE];
	}
	
	@SuppressWarnings("unchecked")
	private void growArray() {
		E[] oldList = list;
		E[] newList = (E[]) new Object[list.length * 2];
		
		for(int i = 0; i < oldList.length; i++) {
			newList[i] = oldList[i];
		}
		
		list = newList;
	}
	
	/**
	 * The add method is an override method used to add object at a given index
	 * 
	 * @throws NullPointerException if the object is null
	 * @throws IndexOutOfBoundsException if the index is less than zero(0), or more than the size of the ArrayList
	 * @throws IllegalArgumentException is thrown if an element of a list equals the object
	 */
	@Override
	public void add(int index, E object) {
		if(object == null) {
			throw new NullPointerException();
		} 
		
		if(index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		if(list.length == this.size){
			growArray();
		}
		
		for(int i = 0; i < size; i++) {
			if(list[i].equals(object)) {
				throw new IllegalArgumentException();
			}
		}
		
		for(int i = this.size - 1; i >= index; i-- ) {
			list[i + 1] = list[i];
		}
		this.size++;
		list[index] = object;
	}

	/**
	 * An override method for the method to remove an element from the ArrayList
	 * 
	 * @param index is the index of the element in the ArrayList
	 * @return removedItem
	 * @throws IndexOutOfBoundsException is thrown if index is less than zero(0) or greater than the size of the ArrayList
	 */
	@Override
	public E remove(int index) {
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		
		E removedItem = this.get(index);
		for(int i = index; i < size() - 1; i++) { //check if tests are failing
			list[i] = list[i + 1];
		}
		
		list[size() - 1] = null;
		
		size--;
		
		return removedItem;
	}

	/**
	 * An override method to get the index of the list
	 * 
	 * @param index is the index of the element in the ArrayList
	 * @return the content on that specific index of the list
	 * @throws IndexOutOfBoundsException is thrown if index is less than zero(0) or greater than the size of the ArrayList
	 */
	@Override
	public E get(int index) {
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		return list[index];
	}

	/**
	 * Returns the number of used spaces in the ArrayList/
	 * @return size of the list as an int
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * An override method that sets the E object
	 * 
	 * @param index is the index of the element in the ArrayList
	 * @return overridenItem item, which has been modified
	 * @throws NullPointerException thrown when item passed is null
	 * @throws IllegalArgumentException thrown when an element of the list at i index is equal to item 
	 * @throws IndexOutOfBoundsException is thrown if index is less than zero(0) or greater than the size of the ArrayList
	 */
	@Override
	public E set(int index, E item) {
		if(item == null) throw new NullPointerException();
		
		for(int i = 0; i < this.size; i++) {
			if(list[i].equals(item)) {
				throw new IllegalArgumentException();
			}
		}
		
		if(index < 0 || index >= this.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		E overriddenItem = this.get(index); 
		
		list[index] = item;
		
		return overriddenItem;
	}
}
