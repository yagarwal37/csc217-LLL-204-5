package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Test class to be put against LinkedQueue 
 * @author yash
 *
 */
public class LinkedQueueTest {

	/**Object used to test methods and functionality of ArrayQueue*/
	private LinkedQueue<String> linkedQueue;
	
	/**
	 * Tests the enqueue method of LinkedQueue
	 */
	@Test
	public void testEnqueue() {
		linkedQueue = new LinkedQueue<String>(3);
		assertEquals(0, linkedQueue.size());
		
		linkedQueue.enqueue("a");
		linkedQueue.enqueue("b");
		linkedQueue.enqueue("c");
		
		assertEquals(3, linkedQueue.size());
		assertThrows(IllegalArgumentException.class, () -> linkedQueue.enqueue("d"));
	}
	
	/**
	 * Tests the dequeue method of ArrayQueue 
	 */
	@Test
	public void testDequeueAndEmpty() {
		linkedQueue = new LinkedQueue<String>(3);
		assertEquals(0, linkedQueue.size());
		
		linkedQueue.enqueue("a");
		linkedQueue.enqueue("b");
		linkedQueue.enqueue("c");
		
		assertEquals("a", linkedQueue.dequeue());
		assertEquals("b", linkedQueue.dequeue());
		assertEquals("c", linkedQueue.dequeue());
		assertThrows(NoSuchElementException.class, () -> linkedQueue.dequeue());

		assertTrue(linkedQueue.isEmpty());
	}
	
	/**
	 * Test the setCapacity method of ArrayQueue
	 */
	@Test
	public void testSetCapacity() {
		linkedQueue = new LinkedQueue<String>(3);

		linkedQueue.setCapacity(linkedQueue.size());
		
		assertThrows(IllegalArgumentException.class, () -> linkedQueue.setCapacity(-1));
	}
}
