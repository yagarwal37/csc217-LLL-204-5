package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Test class which is used against the ArrayQueue class
 * @author yash
 *
 */
public class ArrayQueueTest {

	/**Object used to test methods and functionality of ArrayQueue*/
	private ArrayQueue<String> arrayQueue;
	
	
	/**
	 * Tests the enqueue method of ArrayQueue 
	 */
	@Test
	public void testEnqueue() {
		arrayQueue = new ArrayQueue<String>(3);
		assertEquals(0, arrayQueue.size());
		
		arrayQueue.enqueue("a");
		arrayQueue.enqueue("b");
		arrayQueue.enqueue("c");
		
		assertEquals(3, arrayQueue.size());
		assertThrows(IllegalArgumentException.class, () -> arrayQueue.enqueue("d"));
	}
	
	/**
	 * Tests the dequeue method of ArrayQueue 
	 */
	@Test
	public void testDequeueAndEmpty() {
		arrayQueue = new ArrayQueue<String>(3);
		assertEquals(0, arrayQueue.size());
		
		arrayQueue.enqueue("a");
		arrayQueue.enqueue("b");
		arrayQueue.enqueue("c");
		
		assertEquals("a", arrayQueue.dequeue());
		assertEquals("b", arrayQueue.dequeue());
		assertEquals("c", arrayQueue.dequeue());
		assertThrows(NoSuchElementException.class, () -> arrayQueue.dequeue());

		assertTrue(arrayQueue.isEmpty());
	}
	
	/**
	 * Test the setCapacity method of ArrayQueue
	 */
	@Test
	public void testSetCapacity() {
		arrayQueue = new ArrayQueue<String>(3);

		arrayQueue.setCapacity(arrayQueue.size());
		
		assertThrows(IllegalArgumentException.class, () -> arrayQueue.setCapacity(-1));
	}
	
}
