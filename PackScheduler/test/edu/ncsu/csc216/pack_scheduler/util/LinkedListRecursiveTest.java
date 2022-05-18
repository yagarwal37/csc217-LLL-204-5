package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Tests the LinkedListRecursive Class, 
 * ensuring that recursion works and methods behave as expected.
 * @author Hunter Pruitt
 */
class LinkedListRecursiveTest {

	/** Reference LinkedListRecursive for tests */
	private LinkedListRecursive<String> list;
	
	/**
	 * Explicitly tests variants of add(), remove(), and set().
	 * get(), size(), isEmpty(), and contains() tested in the process.
	 */
	@Test
	void testAddRemoveSet() {
		
		////////////
		//Test Add
		////////////
		
		//Construct list
		list = new LinkedListRecursive<String>();
		//Ensure size is zero and it doesn't contain a random String, and is empty
		assertEquals(0, list.size());
		assertFalse(list.contains("Something"));
		assertTrue(list.isEmpty());
		
		//Test add(E element)
		assertTrue(list.add("A"));
		assertEquals("A", list.get(0));
		assertEquals(1, list.size());
	
		assertTrue(list.add("B"));
	
		assertThrows(IllegalArgumentException.class, () -> list.add("A"));
		assertThrows(NullPointerException.class, () -> list.add(null));
		
		//Test add(int idx, E element)
		//Add at front, middle, and end
		list.add(0, "0");
		assertEquals("0", list.get(0));
		assertEquals("A", list.get(1));
	
		list.add(1, "1");
		assertEquals("1", list.get(1));
		assertEquals("A", list.get(2));
		
		list.add(3, "3");
		assertEquals("3", list.get(3));
		assertEquals("B", list.get(4));
		assertEquals(5, list.size());

		//Now list looks like front -> [0] -> [1] -> [A] -> [3] -> [B]
		//Check exceptions
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(6, "Exception"));
		assertThrows(NullPointerException.class, () -> list.add(3, null));
		assertThrows(IllegalArgumentException.class, () -> list.add(0, "B"));

		
		
		////////////
		//Test Set
		////////////

		//Check exceptions
		assertThrows(NullPointerException.class, () -> list.set(0, null));
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(6, "Exception"));
		assertThrows(IllegalArgumentException.class, () -> list.set(0, "B"));

		//Check setting at front, middle, and end
		assertEquals("0", list.set(0, "Z"));
		assertEquals("Z", list.get(0));
		assertEquals("1", list.get(1));

		assertEquals("1", list.set(1, "Y"));
		assertEquals("Y", list.get(1));
	
		assertEquals("B", list.set(4, "X"));
		assertEquals("X", list.get(4));
		
		assertEquals(5, list.size());
		
		//Now list looks like front -> [Z] -> [Y] -> [A] -> [3] -> [X]
	
		
		////////////
		//Test Remove
		////////////
		
		//Test remove(int idx)
		//Check exceptions
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(5));
		
		//Test removing from front, middle, and end
		list.remove(0);
		assertEquals("Y", list.get(0));
		assertEquals(4, list.size());
		
		list.remove(1);
		assertEquals("Y", list.get(0));
		assertEquals("3", list.get(1));
		assertEquals(3, list.size());
		
		list.remove(2);
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
		assertEquals(2, list.size());
		
		//Restore list to front -> [Z] -> [Y] -> [A] -> [3] -> [X]
		list = new LinkedListRecursive<String>();
		list.add("Z");
		list.add("Y");
		list.add("A");
		list.add("3");
		list.add("X");
		
		//Test remove(E element)
		//Test removing element that does not exist
		assertFalse(list.remove("DNE"));
		
		//Test removing from front, middle, and end
		
		assertTrue(list.remove("Z"));
		assertEquals("Y", list.get(0));
		
		assertTrue(list.remove("A"));
		assertEquals("3", list.get(1));
		
		assertTrue(list.remove("X"));
		assertEquals(2, list.size());
		
	}

}
