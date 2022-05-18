package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;


/**
 * The LinkedList uses a Linked List of ListNodes to implement a list. 
 * It can use any type of data to be stored into the LinkedList.
 * Each ListNode stores the information at its index and then a reference
 * to the next ListNode in the LinkedList.
 * @author yash
 *
 * @param <E> The type of data that will be stored into the LinkedList
 */
public class LinkedList<E> extends AbstractSequentialList<E> {

	/** The first item in the LinkedList */
	private ListNode front;
	/** The last item in the LinkedList*/
	private ListNode back;
	/** The number of elements inside of the LinkedList */
	private int size;
	
	/**
	 * Constructor for the LinkedList
	 */
	public LinkedList() {
		back = new ListNode(null);
		
		front = new ListNode(null, null, back);
		back = new ListNode(null, front, null);
		size = 0;
	}
	
	/**
	 * Constructs a LikedListIterator to iterate through the LinkedList
	 * @param index int index that is used to construct the LinkedListIterator
	 * @return tempIterator LinkedListIterator iterator used for the LinkedList
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		ListIterator<E> tempIterator = new LinkedListIterator(index);
		return tempIterator;
	}
	
	/**
	 * Returns the size of the LinkedList class
	 * @return the size of the LinkedList
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Sets the node at index idx to a given element through the LinkedListIterator class
	 * 
	 * @param idx int index in the list
	 * @param element E data to be replaced with 
	 * @return return the orignal contents of the node
	 * @throws IllegalArgumentException if the element is a duplicate
	 */
	@Override
	public E set(int idx, E element) {
		if(super.contains(element)) {
			throw new IllegalArgumentException();
		}
		return super.set(idx, element);
	}
	
	/**
	 * Adds an element to a specified index in the LinkedList
	 * @param idx int index that the element is placed at
	 * @param element E data that is entered into the ListNode
	 * @throws IllegalArgumentException if the element is a duplicate
	 */
	@Override
	public void add(int idx, E element) {
		if(super.contains(element)) {
			throw new IllegalArgumentException();
		}
		super.add(idx, element);
	}

	/**
	 * A single node inside of the LinkedList class. Stores the information for the item at this
	 * index and the next ListNode
	 */
	private class ListNode {
		/** The data stored at the specific index in the LinkedList inside this ListNode */
		public E data;
		/** The previous ListNode inside the LinkedList.*/
		public ListNode prev;
		/** The next ListNode inside the LinkedList. Null if it is the last element in the list. */
		public ListNode next;
		
		/**
		 * Constructor of a ListNode only using the data stored at the index. The next defaults to
		 * null and so this will act as the final ListNode in the LinkedList
		 * @param data The data that will be stored in this index of the LinkedList.
		 */
		public ListNode(E data) {
			this(data, null, null);
		}
		
		/**
		 * The constructor of ListNode using parameters for both the information that will be stored at
		 * this location and the ListNode that comes next in the LinkedList.
		 * @param data The data that will be stored at this ListNode in the LinkedList.
		 * @param prev The previous ListNode in the LinkedList
		 * @param next The next ListNode in the LinkedList.
		 */
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
		
	}

	/**
	 * Iterator that traverses through a LinkedList
	 * Extends the ListIterator class
	 * Instance is positioned inbetween two ListNodes and points to both while tracking indicies 
	 * and the last ListNode that was worked on
	 * @author yash
	 *
	 */
	private class LinkedListIterator implements ListIterator<E> {
		
		/**Index that would be returned on a call to previousIndex()*/
		public int previousIndex;
		/**Index that would be returned on a call to nextIndex()*/
		public int nextIndex;
		/**ListNode that would be returned on a call to previous()*/
		public ListNode previous;
		/**ListNode that would be returned on a call to next()*/
		public ListNode next;
		/**ListNode that was returned on the last call to either previous() or next()*/
		private ListNode lastRetrieved; 
		
		/**
		 * Constructs a LinkedListIterator
		 * @param index int index for the nextIndex parameter
		 * @throws IndexOutOfBoundsException Exception thrown if index is less than 0 or greater than size
		 */
		public LinkedListIterator(int index) {
			if(index < 0 || index > size()) {
				throw new IndexOutOfBoundsException();
			}
			
			ListNode current = front;
			for(int i = 0; i < index; i++) {
				current = current.next;
			}
			
			previous = current;
			next = current.next;
			lastRetrieved = null;
			previousIndex = index - 1;
			nextIndex = index;
		}
		
		/**
		 * Checks if next is null or not
		 * @return true if next has data. False otherwise
		 */
		@Override
		public boolean hasNext() {
			return next.data != null;
		}

		@Override
		public E next() {
			if(next.data == null) {
				throw new NoSuchElementException();
			}
			
			next = next.next;
			previous = next.prev;
			
			nextIndex++;
			previousIndex++;
			
			lastRetrieved = next.prev;
			
			return lastRetrieved.data;
		}
		
		/**
		 * Checks if previous is null or not
		 * @return true if previous has data. False otherwise
		 */
		@Override
		public boolean hasPrevious() {
			return previous.data != null;
		}

		
		@Override
		public E previous() {
			if(previous.data == null) {
				throw new NoSuchElementException();
			}
			
			previous = previous.prev;
			next = previous.next;
			
			previousIndex--;
			nextIndex--;
			
			lastRetrieved = previous.next;
			
			return lastRetrieved.data;
		}

		/**
		 * Returns the field, next
		 * @return nextIndex int index of the ListNode right of the LinkedListIterator
		 */
		@Override
		public int nextIndex() {
			return nextIndex;
		}

		/**
		 * Returns the field, previous
		 * @return previousIndex int index of the ListNode left of the LinkedListIterator
		 */
		@Override
		public int previousIndex() {
			return previousIndex;
		}

		@Override
		public void remove() {
			//Ensure there is a last retrieved node
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			
			ListNode current = lastRetrieved;
			
			lastRetrieved.prev.next = lastRetrieved.next;
			 
			current.next.prev = current.prev;
			
			lastRetrieved = null;
			
			size--;

		}

		/**
		 * Sets the data of a current node to data passed as a parameter.
		 * @param e data to attach to the last retrieved node
		 * @throws IllegalStaeException if the last retrieved node is null
		 * @throws NullPointerException if the data of the last retrieved is null,
		 * indicating it is an end node
		 */
		@Override
		public void set(E e) {
			//Ensure there is a last retrieved node
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			if(e == null) {
				throw new NullPointerException();
			}
			
			//Check that last retrieved was not an end node (known if it's data is null)
			E prevE = lastRetrieved.data;
			if (prevE == null) {
				throw new NullPointerException();
			}
			
			lastRetrieved.data = e;
		}

		@Override
		public void add(E e) {
			if(e == null) {
				throw new NullPointerException();
			}
			ListNode tempNode = new ListNode(e, previous, next);
			
			next.prev = tempNode;
			previous.next = tempNode;
			
			previous = tempNode;
			previousIndex++;
			nextIndex++;
			
			size++;
			lastRetrieved = null;
			
		}
		
	}

}
