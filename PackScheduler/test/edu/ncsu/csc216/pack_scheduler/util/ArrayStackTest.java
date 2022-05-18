package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class tests the methods of the ArrayStack class, initializing an
 * instance of said class before each test run to ensure test integrity
 * 
 * @author Hunter Pruitt
 */
public class ArrayStackTest {

	/**
	 * Stack reference to be tested
	 */
	private ArrayStack<String> stack;
	
	/**
	 * Setup method, initializing and resetting the stack field for every test
	 */
	@BeforeEach
	void setUp() {
		stack = new ArrayStack<String>(3);
	}
	
	/**
	 * Tests the push and pop functions for ArrayStack, 
	 * including that they throw the proper exceptions
	 */
	@Test
	void testPushPop() {
		assertThrows(EmptyStackException.class, () -> stack.pop());
		
		//Generic push and pop
		stack.push("A");
		assertEquals("A", stack.pop());

		//Several pushes and pops
		stack.push("A");
		stack.push("B");
		stack.push("C");
		assertThrows(IllegalArgumentException.class, () -> stack.push("F"));

		assertEquals("C", stack.pop());
		assertEquals("B", stack.pop());
		assertEquals("A", stack.pop());
		
	}

	/**
	 * Ensures the isEmpty method works, with new stack and after pushing/popping
	 */
	@Test
	void testIsEmpty() {
		assertTrue(stack.isEmpty());
		stack.push("A");
		assertFalse(stack.isEmpty());
		stack.pop();
		assertTrue(stack.isEmpty());
	}

	/**
	 * Ensures the size method works, with new stack and after pushing/popping
	 */
	@Test
	void testSize() {
		assertEquals(0, stack.size());
		
		stack.push("A");
		assertEquals(1, stack.size());
		stack.pop();
		assertEquals(0, stack.size());
	}

	/**
	 * Tests setting the capacity with values to either throw exceptions or behave as expected
	 */
	@Test
	void testSetCapacity() {
		//Test with negative number
		assertThrows(IllegalArgumentException.class, () -> stack.setCapacity(-1));
		
		//Test with value less than size
		stack.push("A");
		stack.push("B");
		assertThrows(IllegalArgumentException.class, () -> stack.setCapacity(1));
		
		//Try setting capacity to size
		try {
			stack.setCapacity(stack.size());
		} catch (Exception e) {
			fail("Exception thrown when setting capcity to size");
		}
	}

}
