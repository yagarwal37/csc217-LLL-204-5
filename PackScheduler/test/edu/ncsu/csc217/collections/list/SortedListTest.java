package edu.ncsu.csc217.collections.list;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

/**
 * Tests the SortedList class through various methods like add, remove, get, and a few more.
 * 
 * 
 * @author yash
 * @author tanmay
 * @author tadeo
 *
 */
public class SortedListTest {

	/**
	 * Test the construction of the SortedList list by added an 11th element
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertFalse(list.contains("apple"));
		
		list.add("orange");
		list.add("apple");
		list.add("banana");
		list.add("plum");
		list.add("mango");
		list.add("starfruit");
		list.add("dragonfruit");
		list.add("apricot");
		list.add("cherry");
		list.add("blueberry");
		list.add("grape");
		
		assertEquals(11, list.size());	
	}

	/**
	 * Test the add method of the SortedList class
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();
		
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
		list.add("apple");
		assertEquals("apple", list.get(0));
		list.add("apricot");
		assertEquals("apricot", list.get(1));
		list.add("grape");
		assertEquals("grape", list.get(3));	
		
		Exception e1 = assertThrows(NullPointerException.class,
				() -> list.add(null));
		assertEquals(null, e1.getMessage(), "Incorrect exception thrown with adding null string");
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> list.add("banana"));
		assertEquals("Element already in list.", e2.getMessage(), "Incorrect exception thrown with adding duplicate string");
		
	}
	/**
	 * Tests the get method from the sorted list class. 
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();
		
		Exception e3 = assertThrows(IndexOutOfBoundsException.class,
				() -> list.get(0));
		assertEquals(null, e3.getMessage(), "Incorrect exception thrown with getting string"); 
		
		list.add("apple");
		assertEquals(list.get(0), "apple");
		
		Exception e1 = assertThrows(IndexOutOfBoundsException.class,
				() -> list.get(list.size()));
		assertEquals(null, e1.getMessage(), "Incorrect exception thrown with getting string"); 
		
		Exception e2 = assertThrows(IndexOutOfBoundsException.class,
				() -> list.get(-1));
		assertEquals(null, e2.getMessage(), "Incorrect exception thrown with getting string"); 
		
	}
	
	/**
	 * Tests the remove method in the SortedList class
	 */
	
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();
		
		Exception e1 = assertThrows(IndexOutOfBoundsException.class,
				() -> list.remove(0));
		assertEquals(null, e1.getMessage(), "Incorrect exception thrown with removing string"); 
		
		list.add("orange");
		list.add("grape");
		list.add("apple");
		list.add("apricot");
		
		Exception e2 = assertThrows(IndexOutOfBoundsException.class,
				() -> list.remove(-1));
		assertEquals(null, e2.getMessage(), "Incorrect exception thrown with removing string"); 
		
		Exception e3 = assertThrows(IndexOutOfBoundsException.class,
				() -> list.remove(-1));
		assertEquals(null, e3.getMessage(), "Incorrect exception thrown with removing string");
		
		assertEquals(list.remove(2), "grape");
		
		assertEquals(list.remove(2), "orange");
		
		assertEquals(list.remove(0), "apple");
		
		assertEquals(list.remove(0), "apricot");
	}
	
	/**
	 * Tests adding elements and then checking their index, and throwing exception for null index
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(-1, list.indexOf(""));
		list.add("iPhone");
		list.add("iMac");
		list.add("iWatch");
		list.add("iPod");
		assertEquals(1, list.indexOf("iPhone"));
		assertEquals(0, list.indexOf("iMac"));
		assertEquals(3, list.indexOf("iWatch"));
		assertEquals(2, list.indexOf("iPod"));
		Exception e1 = assertThrows(NullPointerException.class,
				() -> list.indexOf(null));
		assertEquals(null, e1.getMessage(), "Incorrect exception thrown with finding index of null element"); 
		assertEquals(-1, list.indexOf("iPad"));
		
	}
	
	/**
	 * Tests the clear method in the SortedList class
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();
		
		list.add("iPhone");
		list.add("iMac");
		list.add("iWatch");
		list.add("iPod");
		list.clear();
		assertEquals(0, list.size());
	}

	/**
	 * Tests the isEmpty method in the SortedList class
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();
		
		assertTrue(list.isEmpty());
		
		list.add("iPhone");
		list.add("iMac");
		list.add("iWatch");
		list.add("iPod");
		
		assertFalse(list.isEmpty());
	}

	/**
	 * Tests the contains method in the SortedList class
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();
		
		assertFalse(list.contains("iPhone"));
		
		list.add("iPhone");
		list.add("iMac");
		
		assertTrue(list.contains("iPhone"));
		assertTrue(list.contains("iMac"));
		assertFalse(list.contains("apple"));
	}
	
	/**
	 * Tests the equals method in the SortedList class
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		list1.add("iPhone");
		list1.add("iMac");
		
		list2.add("iPhone");
		list2.add("iMac");
		
		list3.add("iPad");
		list3.add("iPod");
		
		assertTrue(list1.equals(list2));
		assertTrue(list2.equals(list1));
		
		assertFalse(list3.equals(list1));
		assertFalse(list1.equals(list3));
	}
	
	/**
	 * Tests the HashCode method in the SortedList class
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		
		list1.add("iPhone");
		list1.add("iMac");
		
		list2.add("iPhone");
		list2.add("iMac");
		
		list3.add("iPad");
		list3.add("iPod");
		
		assertEquals(list1.hashCode(), list2.hashCode());
		assertEquals(list2.hashCode(), list1.hashCode());
		
		assertNotEquals(list1.hashCode(), list3.hashCode());
		assertNotEquals(list3.hashCode(), list1.hashCode());
	}

}
 