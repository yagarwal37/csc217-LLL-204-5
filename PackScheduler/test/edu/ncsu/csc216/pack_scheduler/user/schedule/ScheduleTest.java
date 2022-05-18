package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * This class tests the methods of the Schedule class
 * 
 * @author tanmaysoni
 * @author johnnolan
 * @author hunterpruitt
 */
class ScheduleTest {
	
	/** Schedule s */
	private static Schedule s;
	
	@BeforeEach
	void setUp() throws Exception {
		s = new Schedule();
	}
	
	/**
	 * Tests the Schedule Method
	 */
	@Test
	void testSchedule() {
		
		assertEquals(s.getTitle(), "My Schedule");
		assertEquals(s.getScheduledCourses().length, 0);

	}
	
	/**
	 * Tests the SetTitle Method
	 */
	@Test
	void testSetTitle() {
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> s.setTitle(null) );
		assertEquals(e.getMessage(), "Title cannot be null.");
		
		s.setTitle("My screwed up schedule");
		assertEquals(s.getTitle(), "My screwed up schedule");
	}
	
	/**
	 * Tests the AddCourseToSchedule Method
	 */
	@Test
	void testAddCourseToSchedule() {
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "003", 3, "spbalik", 10, "MW", 1250, 1440);
		Course c2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		Course c3 = new Course("CSC116", "Intro to Programming - Java", "003", 3, "spbalik", 10, "MW", 1250, 1440);
		
		assertTrue(s.addCourseToSchedule(c1));
		assertEquals(s.getScheduledCourses().length, 1);
		
		Exception e = assertThrows(IllegalArgumentException.class, 
				() -> s.addCourseToSchedule(c2));
		assertEquals(e.getMessage(), "The course cannot be added due to a conflict.");
		
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.addCourseToSchedule(c3) );
		assertEquals(e1.getMessage(), "You are already enrolled in CSC116");
		
		assertThrows(NullPointerException.class,
				() -> s.addCourseToSchedule(null) );
		
		assertEquals(3, s.getScheduleCredits());
	}

	/**
	 * Tests the RemoveCourseFromSchedule Method
	 */
	@Test
	void testRemoveCourseFromSchedule() {
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "003", 3, "spbalik", 10, "MW", 1250, 1440);
		Course c2 = new Course("CSC216", "Software Development Fundamentals", "601", 3, "jctetter", 10, "A");

		assertEquals(s.getScheduledCourses().length, 0);

		s.addCourseToSchedule(c1);
		s.addCourseToSchedule(c2);
		assertEquals(s.getScheduledCourses().length, 2);
		
		assertTrue(s.removeCourseFromSchedule(c1));
		assertEquals(s.getScheduledCourses().length, 1);

		assertFalse(s.removeCourseFromSchedule(c1));
		assertEquals(s.getScheduledCourses().length, 1);
	
		assertTrue(s.removeCourseFromSchedule(c2));
		assertEquals(s.getScheduledCourses().length, 0);
		
		assertFalse(s.removeCourseFromSchedule(null));
		assertEquals(s.getScheduledCourses().length, 0);
	}
	
	/**
	 * Tests the ResetSchedule Method
	 */
	@Test
	void testResetSchedule() {
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "003", 3, "spbalik", 10, "MW", 1250, 1440);
		Course c2 = new Course("CSC216", "Software Development Fundamentals", "601", 3, "jctetter", 10, "A");

		s.addCourseToSchedule(c1);
		s.addCourseToSchedule(c2);

		assertDoesNotThrow(() -> s.resetSchedule());
		assertEquals(0, s.getScheduledCourses().length);
		
		assertDoesNotThrow(() -> s.resetSchedule());
		assertEquals(0, s.getScheduledCourses().length);
		
		s.setTitle("Not a schedule");
		assertEquals("Not a schedule", s.getTitle());

		assertDoesNotThrow(() -> s.resetSchedule());
		assertEquals(s.getTitle(), "My Schedule");
	}

	/**
	 * Tests the GetScheduledCourses Method
	 */
	@Test
	void testGetScheduledCourses() {
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "003", 3, "spbalik", 10, "MW", 1250, 1440);
		Course c2 = new Course("CSC216", "Software Development Fundamentals", "601", 3, "jctetter", 10, "A");

		s.addCourseToSchedule(c1);
		s.addCourseToSchedule(c2);
		
		String[][] sArray = s.getScheduledCourses();
		assertEquals("CSC116", sArray[0][0]);
		assertEquals("003", sArray[0][1]); //name, section, title, meetingScreen
		assertEquals("Intro to Programming - Java", sArray[0][2]);
		assertEquals("MW 12:50PM-2:40PM", sArray[0][3]);
		
		assertEquals("CSC216", sArray[1][0]);
		assertEquals("601", sArray[1][1]);
		assertEquals("Software Development Fundamentals", sArray[1][2]);
		assertEquals("Arranged", sArray[1][3]);
	}

	/**
	 * Tests the canAdd method
	 */
	@Test 
	void testCanAdd() {
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "003", 3, "spbalik", 10, "MW", 1250, 1440);
		Course c2 = new Course("CSC216", "Software Development Fundamentals", "601", 3, "jctetter", 10, "A");
		
		assertFalse(s.canAdd(null));
		assertTrue(s.canAdd(c1));
		assertTrue(s.canAdd(c2));
		
		
	}
}
