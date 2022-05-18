package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * The LinkedAbstractList uses a Linked List of ListNodes to implement
 * a list. It can use any type of data to be stored into the LinkedList.
 * Each ListNode stores the information at its index and then a reference
 * to the next ListNode in the LinkedList.
 *
 * @param <E> The type of data that will be stored into the LinkedList
 */
public class LinkedAbstractList<E> extends AbstractList<E> {
	/** The first item in the LinkedList */
	private ListNode front;
	/** The last item in the LinkedList*/
	private ListNode back;
	/** The number of elements inside of the LinkedList */
	private int size;
	/** The number of spots that exist inside the LinkedList */
	private int capacity;
	
	/**
	 * The constructor of an empty LinkedAbstractList that uses an initial capacity
	 * @param capacity the number of spots in the list
	 * @throws IllegalArgumentException if passed a bad capacity
	 */
	public LinkedAbstractList(int capacity){
		this.size = 0;
		this.front = null;
		this.back = null;
		
		if(capacity < 0 || capacity < size) {
			throw new IllegalArgumentException();
		}
		
		this.capacity = capacity;
		
		
	}
	
	/**
	 * Setter method that sets the capacity of the LinkedAbstractList
	 * @param capacity int passed in capacity of the LinkedAbstractList
	 */
	public void setCapacity(int capacity) {
		if(capacity < 0 || capacity < size()) {
			throw new IllegalArgumentException();
		}
		
		this.capacity = capacity;
	}
	
	/**
	 * The add method is an override method used to add element at a given index
	 * 
	 * @throws NullPointerException if the element is null
	 * @throws IndexOutOfBoundsException if the index is less than zero(0), or more than the size of the LinkedList
	 * @throws IllegalArgumentException is thrown if an element of a list equals the object
	 */
	@Override
	public void add(int index, E element) {
		if(this.size >= this.capacity) {
			throw new IllegalArgumentException();
		}
		
		if(element == null) {
			throw new NullPointerException();
		}
		
		if(isDuplicate(element)) {
			throw new IllegalArgumentException();
		}
		
		if(index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		
		//Add to the index
		//If it's the first element
		if (index == 0) {
			ListNode temp = front;
			front = new ListNode(element, temp);
			back = front;
		}
		//If adding in the middle
		else if (index < size()) {
			ListNode temp = front;
			for (int i = 1; i < index; i++) {
				temp = temp.next;
			}
			temp.next = new ListNode(element, temp.next);
			back = temp.next;
		}
		//If adding at end
		else if (index == size) {
			ListNode temp = front;
			while(temp.next != null) {
				temp = temp.next;
			}
			temp.next = new ListNode(element);
			back = temp.next;
		}
		size++;
	}
	
	/**
	 * Indicates if two elements are the same
	 * @param element to be compared against
	 * @return boolean indicator
	 */
	private boolean isDuplicate(E element) {
		boolean found = false;
		ListNode temp = front;
		if (temp != null) {
			while(temp.next != null) {
				if(temp.data.equals(element)) {
					found = true;
					break;
				}
				temp = temp.next;
			}
			if(temp.data.equals(element)) {
				found = true;
			}
		}
		return found;
	}
	
	/**
	 * The method that will return the item stored at a specific index in the LinkedList
	 * @return E The item at the specified index
	 */
	@Override
	public E get(int index) {
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		ListNode temp = front;
		for (int i = 0; i < index; i++) {
			temp = temp.next;
		}
		return temp.data;
	}
	
	/**
	 * Sets the specified index with the given element
	 * @return E The element that is replaced by the set function.
	 */
	@Override
	public E set(int index, E element) {
		if(element == null) {
			throw new NullPointerException();
		}
		
		if(isDuplicate(element)) {
			throw new IllegalArgumentException();
		}
		
		if(index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		E replacedItem = null;
		
		if(index == 0) {
			ListNode next;
			if(front == null) {
				next = null;
			}
			else {
				replacedItem = front.data;
				next = front.next;
			}
			front = new ListNode(element, next);
		}
		else {
			ListNode temp = front;
			for (int i = 1; i < index; i++) {
				temp = temp.next;
			}
			replacedItem = temp.next.data;
			temp.next = new ListNode(element, temp.next.next);
		}
		
		return replacedItem;
		
	}
	
	/**
	 * Removes the item at the specified index. All items after this index will shift one left
	 * to fill the removal of this item.
	 * @return E The item that was removed from the LinkedList
	 */
	@Override
	public E remove(int index) {
		if(index < 0 || index >= size) {
				throw new IndexOutOfBoundsException();
		}
		
		E removedElement;
		
		if(index == 0) {
			removedElement = front.data;
			front = front.next;
			
			ListNode temp = front;
			for(int i = 0; i < size() - 1; i++) {
				temp = temp.next;
			}
			
			back = temp;
		}
		else if (index != size - 1) {
			ListNode temp = front;
			for (int i = 1; i < index; i++) {
				temp = temp.next;
			}
			removedElement = temp.next.data;
			temp.next = temp.next.next;
			
			ListNode temp2 = front;
			for(int i = 0; i < size() - 1; i++) {
				temp2 = temp2.next;
			}
			
			back = temp2;
		}
		else {
			ListNode temp = front;
			for (int i = 1; i < index; i++) {
				temp = temp.next;
			}
			back = temp;
			removedElement = back.next.data;
			temp.next = null;
		}
		
		size--;
		
		return removedElement;
	}

	/**
	 * Returns the size of the LinkedList class
	 * @return the size of the LinkedList
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * A single node inside of the LinkedList class. Stores the information for the item at this
	 * index and the next ListNode
	 */
	private class ListNode {
		/** The data stored at the specific index in the LinkedList inside this ListNode */
		E data;
		/** The next ListNode inside the LinkedList. Null if it is the last element in the list. */
		private ListNode next;
		
		/**
		 * Constructor of a ListNode only using the data stored at the index. The next defaults to
		 * null and so this will act as the final ListNode in the LinkedList
		 * @param data The data that will be stored in this index of the LinkedList.
		 */
		public ListNode(E data) {
			this.data = data;
		}
		
		/**
		 * The constructor of ListNode using parameters for both the information that will be stored at
		 * this location and the ListNode that comes next in the LinkedList.
		 * @param data The data that will be stored at this ListNode in the LinkedList.
		 * @param next The next ListNode in the LinkedList.
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
		
	}

}
