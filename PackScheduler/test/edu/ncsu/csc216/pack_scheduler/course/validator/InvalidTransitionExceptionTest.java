package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InvalidTransitionExceptionTest {

	/**
	 * Test method for InvalidTransitionException
	 */
	@Test
	void testInvalidTransitionException() {
		InvalidTransitionException ite = new InvalidTransitionException("Custom exception message");
	    assertEquals("Custom exception message", ite.getMessage());
	}

	/**
	 * Test method for InvalidTransitionException
	 */
	@Test
	void testInvalidTransitionExceptionDefault() {
		InvalidTransitionException ite = new InvalidTransitionException();
	    assertEquals("Invalid FSM Transition.", ite.getMessage());
	}
}