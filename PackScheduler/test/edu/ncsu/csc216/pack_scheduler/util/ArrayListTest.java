package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * This class tests the methods of the ArrayList class
 * 
 * @author tanmaysoni
 * @author johnnolan
 * @author hunterpruitt
 */
class ArrayListTest {
	

	/**
	 * Tests the ArrayList Method
	 */
	@Test
	void testArrayList() {

		ArrayList<String> c = new ArrayList<String>();
		assertEquals(0, c.size());

		//Test add method
		assertThrows(IndexOutOfBoundsException.class, () -> c.add(-1, "A"));
		assertThrows(IndexOutOfBoundsException.class, () -> c.add(1, "A"));
		assertEquals(0, c.size());
		
		c.add(0, "A");
		assertEquals(1, c.size());
		assertEquals("A", c.get(0));
		
		assertThrows(NullPointerException.class, () -> c.add(1, null));
		assertThrows(IllegalArgumentException.class, () -> c.add(1, "A"));
		
		c.add(1, "B");
		assertEquals(2, c.size());

		//Test set method
		assertThrows(NullPointerException.class, () -> c.set(1, null));
		assertThrows(IllegalArgumentException.class, () -> c.set(0, "B"));
		assertThrows(IndexOutOfBoundsException.class, () -> c.set(-1, "C"));
		assertThrows(IndexOutOfBoundsException.class, () -> c.set(2, "C"));

		c.set(0, "Z");
		assertEquals("Z", c.get(0));
		assertEquals(2, c.size());
		
		
		//Test get method, partially tested above
		assertThrows(IndexOutOfBoundsException.class, () -> c.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> c.get(2));
		assertThrows(IndexOutOfBoundsException.class, () -> c.get(3));	
		
		
		//Test remove method
		assertThrows(IndexOutOfBoundsException.class, () -> c.remove(2));
		assertThrows(IndexOutOfBoundsException.class, () -> c.remove(-1));

		assertEquals("Z", c.remove(0));
		assertEquals("B", c.get(0));
		assertEquals("B", c.remove(0));
		assertEquals(0, c.size());
		
		//Test growArray by adding elements to beyond INIT_SIZE
		try {
			c.add(0, "A");
			c.add(1, "B");
			c.add(2, "C");
			c.add(3, "D");
			c.add(4, "E");
			c.add(5, "F");
			c.add(6, "G");
			c.add(7, "H");
			c.add(8, "I");
			c.add(9, "J");
			c.add(10, "K");
			c.add(11, "L");
			assertEquals(12, c.size());
		} catch (Exception e) {
			fail("Shouldn't have thrown any exceptions");
		}

		
		//		test grow array by adding 10+ objects
	
	
	}

}
