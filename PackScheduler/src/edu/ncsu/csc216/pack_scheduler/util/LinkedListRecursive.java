package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Represents a recursive collection of data using the linked list paradigm.
 * @author Hunter Pruitt
 * @param <E> The type of data that will be stored into the LinkedList
 */
public class LinkedListRecursive<E> {

	/** The first item in the LinkedListRecursive */
	private ListNode front;

	/** The number of elements inside of the LinkedListRecursive */
	private int size;
	
	/**
	 * Constructor for LinkedListRecursive object, a collection of ListNodes
	 * that does not allow duplicate values.
	 * Sets the front to null and size to zero.
	 */
	public LinkedListRecursive() {
		front = null;
		size = 0;
	}
	
	/**
	 * Indicates if the list is empty by checking if size is zero
	 * @return boolean indication as to if the list is empty
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Indicates how many elements or ListNodes with data are in the linked list
	 * @return number of elements in the list
	 */
	public int size() {
		return size;
	}

	/**
	 * Indicates if the element is contained by a ListNode in the list.
	 * Recursive through each list node, returning if it is contained by the private method in ListNode
	 * @param element to search for
	 * @return boolean indicator as to if the list contains the passed element
	 */
	public boolean contains(E element) {
		if (isEmpty()) {
			return false;
		} else {
			return front.contains(element);
		}
	}
	

	/**
	 * Adds an element to the end of the list
	 * @param element data to tack onto the list
	 * @return boolean indicator as to the success of the add
	 * @throws NullPointerException if the new data is null
	 * @throws IllegalArgumentException if the new data matches data already contained by the list
	 */
	public boolean add(E element) {
		//Ensure data to add is not null
		if (element == null) {
			throw new NullPointerException();
		}
		//Check if the list is empty
		if (isEmpty()) {
			front = new ListNode(element, null);
			size++;
			return true;
		}
		//Check to ensure element isn't a duplicate
		else if (contains(element)) {
			throw new IllegalArgumentException();
		} else {
			//Add recursively
			return front.add(element);
		}
	}
	
	/**
	 * Inserts a ListNode into the given index, shifting what was currently
	 * at said index to the right.
	 * @param idx insertion point of the data
	 * @param element data to add to the list
	 * @throws IndexOutOfBoundsException if the index is out of bounds of the list
	 * @throws NullPointerException if the new data is null
	 * @throws IllegalArgumentException if the new data is a duplicate of already contained data
	 */
	public void add(int idx, E element) {
		//Check bounds, if element is null, and if duplicate in that order
		if (idx > size || idx < 0) {
			throw new IndexOutOfBoundsException();
		} else if (element == null) {
			throw new NullPointerException();
		} else if (contains(element)) {
			throw new IllegalArgumentException();
		}
		
		//Add the element
		if (idx == 0) {
			front = new ListNode(element, front);
			size++;
		} else {
			front.add(idx - 1, element);
		}
	}
	
	/**
	 * Returns the data in a node at the given index.
	 * @param idx index to retrieve the data from
	 * @return E data from the list
	 * @throws IndexOutOfBoundsException if the index is out of bounds of the list
	 */
	public E get(int idx) {
		if (idx >= size || idx < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		//Get recursively
		if (idx == 0) {
			return front.data;
		} else {
			return front.get(idx - 1);
		}
	}

	/**
	 * Removes the data from a specific index by iterating recursively through the list. 
	 * Returns the data that was removed.
	 * @param idx index to remove from
	 * @return E data contained at the removal point
	 * @throws IndexOutOfBoundsException if the index is out of bounds of the list
	 */
	public E remove(int idx) {
		
		if (idx >= size || idx < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		if (idx == 0) {
			E oldData = front.data;
			front = front.next;
			size--;
			return oldData;
		} else {
			return front.remove(idx - 1);
		}
	}
	
	/**
	 * Removes an element from the list after locating it through recursion.
	 * Returns boolean indicator if it is found + removed.
	 * @param element data to be found and removed
	 * @return boolean indicator as to if the element was removed
	 * 
	 */
	public boolean remove(E element) {
		//Cannot remove from empty list
		if (isEmpty()) {
			return false;
		}
		
		if (front.data.equals(element)) {
			front = front.next;
			size--;
			return true;
		} else {
			return front.remove(element);
		}
	}
	
	/**
	 * Sets the data contained in a node at a given index to something new.
	 * @param idx index to change node data
	 * @param element data to replace old data with	 
	 * @throws NullPointerException if the new data is null
	 * @throws IndexOutOfBoundsException if the index is out of bounds of the list
	 * @throws IllegalArgumentException if the new data is a duplicate of already contained data
	 * @return data that was overwritten by the new, parameter data
	 */
	public E set(int idx, E element) {
		//Ensure data to add is not null, and that index is in bounds
		if (element == null) {
			throw new NullPointerException();
		} else if (idx >= size || idx < 0) {
			throw new IndexOutOfBoundsException();
		} else if (contains(element)) {
			throw new IllegalArgumentException();
		}
		
		if (idx == 0) {
			E prevData = front.data;
			front.data = element;
			return prevData;
		} else {
			return front.set(idx - 1, element);
		}
	}
	
	
	/**
	 * Represents an individual step in the collection of data.
	 * @author Hunter Pruitt
	 *
	 */
	private class ListNode {
		/** The data stored inside this ListNode */
		public E data;

		/** The following ListNode inside the list */
		public ListNode next;

		/**
		 * Constructs ListNode, setting fields for use in LinkedListRecursive
		 * @param data information for this node to carry
		 * @param next the next node in the sequence
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}

		/**
		 * Sets the data contained in a node at a given index to something new.
		 * Private recursive component
		 * @param idx distance from setting point 
		 * @param element data to replace old data with
		 * @return data that is overwritten
		 */
		public E set(int idx, E element) {
			if (idx == 0) {
				E prevData = next.data;
				next.data = element;
				return prevData;
			} else {
				return next.set(idx - 1, element);
			}
			
		}

		/**
		 * Removes an element from the list after locating it through recursion.
		 * Returns boolean indicator if it is found + removed.
		 * Private recursive component
		 * @param element data to be found and removed
		 * @return boolean indicator as to if the element was removed
		 */
		public boolean remove(E element) {
			if (next.data.equals(element)) {
				next = next.next;
				size--;
				return true;
			} else if (next.next == null){
				return false;
			} else {
				return next.remove(element);
			}

		
		}

		/**
		 * Removes an element from the list after locating it through recursion.
		 * Returns boolean indicator if it is found + removed.
		 * Private recursive component
		 * @param idx distance from removal point
		 * @return data that was previously in the removed node
		 */
		public E remove(int idx) {
			if (idx == 0) {
				E oldData = next.data;
				next = next.next;
				size--;
				return oldData;
			} else {
				return next.remove(idx - 1);
			}
		}

		/**
		 * Returns data contained by a list node, handles cases where data is not stored in front.
		 * Recursive component
		 * @param i distance from retrieval point, get from next when i = 0
		 * @return element stored at the wanted index
		 */
		public E get(int i) {
			if (i == 0) {
				return next.data;
			} else {
				return next.get(i - 1);
			}
		}

		/**
		 * Inserts a ListNode into the given index, shifting what was currently
		 * at said index to the right.
		 * Recursive component
		 * @param idx distance from insertion point, add when idx = 0
		 * @param element data to add
		 */
		public void add(int idx, E element) {
			if (idx == 0) {
				next = new ListNode(element, next);
				size++;
			} else {
				next.add(idx - 1, element);
			}
			
		}

		/**
		 * Adds an element to the end of the list.
		 * This private method is the recursive component
		 * @param element to add to the end of the list
		 * @return boolean indicator as to if the element was added
		 */
		public boolean add(E element) {
			if (next == null) {
				next = new ListNode(element, null);
				size++;
				return true;
			} else {
				return next.add(element);
			}
		}

		/**
		 * Recursive method to determine if an element is contained by a ListNode.
		 * Iterates until it reaches the end or finds a copy.
		 * @param element to search for
	 	 * @return boolean indicator as to if the list contains the passed element
	 	 */
		public boolean contains(E element) {
			if(data.equals(element)) {
				return true;
			} else if (next != null) {
				return next.contains(element);
			} else {
				return false;
			}
		}
	
	} //ListNode class

} //LinkedListRecursive class