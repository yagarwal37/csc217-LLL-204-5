package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tester class to test the LinkedList class
 * @author yash
 *
 */
public class LinkedListTest {

	/**
	 * Tests the ArrayList Method
	 */
	@Test
	public void testLinkedList() {
		//Construct a LinkedList and test size is 0
		LinkedList<String> c = new LinkedList<String>();
		assertEquals(0, c.size());
		
		//Construct a LinkedListIterator for c and see if the parameters are empty 
		ListIterator<String> iterator = c.listIterator(0);
		assertFalse(iterator.hasPrevious());
		assertFalse(iterator.hasNext());
		assertEquals(-1, iterator.previousIndex());
		assertEquals(0, iterator.nextIndex());
				
		//Check if the previous and next ListNodes hold null data
		assertThrows(NoSuchElementException.class, () -> iterator.previous());
		assertThrows(NoSuchElementException.class, () -> iterator.next());
				
		//Attempt to add a null element			
		assertThrows(NullPointerException.class, () -> iterator.add(null));		
		//Add A to the list and see if size is incremented
		iterator.add("A");
		assertEquals(1, c.size());
		
		//Add an element to list through the LinkedList
		c.add(1, "B");
		assertEquals(2, c.size());
		assertEquals("A", c.get(0));
		assertEquals("B", c.get(1));
		
		//Test set
		c.set(0, "C");
		assertEquals("C", c.get(0));
		assertEquals(2, c.size());
		assertThrows(IllegalArgumentException.class, () -> c.set(0, "B"));
		
		//Test remove
		assertEquals("C", c.remove(0));
		assertEquals(1, c.size());
		

	}
}
