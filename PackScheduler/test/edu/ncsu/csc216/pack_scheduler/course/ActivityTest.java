/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class for Activity
 * @author tadeotheis
 *
 */
class ActivityTest {

	/**
	 * Test method for checkConflict without conflicting activities
	 */
	@Test
	void testCheckConflict() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "TH", 1330, 1445);
		Activity a3 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "A");
		Activity a4 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "A");
		assertDoesNotThrow(() -> a1.checkConflict(a2));
		assertDoesNotThrow(() -> a2.checkConflict(a1));
		assertDoesNotThrow(() -> a3.checkConflict(a4));
		assertDoesNotThrow(() -> a4.checkConflict(a3));

	}
	
	/**
	 * Test method for checkConflict with conflicting activities
	 */
	@Test
	public void testCheckConflictWithConflict() {
	    Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
	    Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "M", 1330, 1445);
	    Activity a3 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "W", 1445, 1600);
	    Activity a4 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "W", 1530, 1700);
	    Activity a5 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "W", 1100, 1900);
		
	    Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
	    assertEquals("Schedule conflict.", e1.getMessage());
		
	    Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
	    assertEquals("Schedule conflict.", e2.getMessage());
	    
	    Exception e3 = assertThrows(ConflictException.class, () -> a3.checkConflict(a1));
	    assertEquals("Schedule conflict.", e3.getMessage());
	    
	    Exception e4 = assertThrows(ConflictException.class, () -> a1.checkConflict(a3));
	    assertEquals("Schedule conflict.", e4.getMessage());
	    
	    Exception e5 = assertThrows(ConflictException.class, () -> a4.checkConflict(a3));
	    assertEquals("Schedule conflict.", e5.getMessage());
	    
	    Exception e6 = assertThrows(ConflictException.class, () -> a3.checkConflict(a4));
	    assertEquals("Schedule conflict.", e6.getMessage());
	    
	    Exception e7 = assertThrows(ConflictException.class, () -> a5.checkConflict(a4));
	    assertEquals("Schedule conflict.", e7.getMessage());
	    
	    Exception e8 = assertThrows(ConflictException.class, () -> a4.checkConflict(a5));
	    assertEquals("Schedule conflict.", e8.getMessage());
	}

}
