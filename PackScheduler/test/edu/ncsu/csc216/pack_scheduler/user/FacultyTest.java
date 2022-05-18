package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/***
 * Tests the Faculty class
 * @author Hunter Pruitt
 *
 */
class FacultyTest {


	
	/*** Generic faculty member's first name */
	private static final String FIRST_NAME = "Francis";
	
	/*** Generic faculty member's last name */
	private static final String LAST_NAME = "Faculty";
	
	/*** Generic faculty member's id */
	private static final String ID = "ffaculty";
	
	/*** Generic faculty member's email */
	private static final String EMAIL = "ffaculty@ncsu.edu";
	
	/*** Generic faculty member's password */
	private static final String PASSWORD = "pw";
	
	/*** maxCourses fields used by the the constructors in these tests */
	private static final int MAX_COURSES = 3;
	
	/***
	 * Tests hashCode if working correctly 
	 */
	@Test
	void testHashCode() {
		// Construct a valid Faculty
		User s1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		User s2 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		User s3 = new Faculty("First", LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		User s4 = new Faculty(FIRST_NAME, "Last", ID, EMAIL, PASSWORD, MAX_COURSES);
		User s5 = new Faculty(FIRST_NAME, LAST_NAME, "flast", EMAIL, PASSWORD, MAX_COURSES);
		User s6 = new Faculty(FIRST_NAME, LAST_NAME, ID, "flast@ncsu.edu", PASSWORD, MAX_COURSES);
		User s7 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "firstlastname", MAX_COURSES);
		User s8 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 1);
		
		// Test for the same hash code for the same values
		assertEquals(s1.hashCode(), s2.hashCode());

		// Test for each of the fields
		assertNotEquals(s1.hashCode(), s3.hashCode());
		assertNotEquals(s1.hashCode(), s4.hashCode());
		assertNotEquals(s1.hashCode(), s5.hashCode());
		assertNotEquals(s1.hashCode(), s6.hashCode());
		assertNotEquals(s1.hashCode(), s7.hashCode());
		assertNotEquals(s1.hashCode(), s8.hashCode());
	}

	/**
	 * Tests the isOverloaded method for a Faculty object
	 */
	@Test
	void testFacultyIsOverloaded() {
		Faculty f = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		FacultySchedule schedule = f.getSchedule();
		Course c1 = new Course("CSC101", "Fake course 1", "001", 4, null, 50, "A");
		Course c2 = new Course("CSC102", "Fake course 2", "001", 4, null, 50, "A");
		Course c3 = new Course("CSC103", "Fake course 3", "001", 4, null, 50, "A");
		Course c4 = new Course("CSC104", "Fake course 4", "001", 4, null, 50, "A");

		
		assertFalse(f.isOverloaded());
		
		schedule.addCourseToSchedule(c1);
		assertFalse(f.isOverloaded());
		
		schedule.addCourseToSchedule(c2);
		schedule.addCourseToSchedule(c3);
		assertFalse(f.isOverloaded());
		
		schedule.addCourseToSchedule(c4);
		assertTrue(f.isOverloaded());
		
	}
	
	/***
	 * Tests constructing a Faculty with max courses
	 */
	@Test
	void testFacultyWithcourses() {
		Faculty s1 = assertDoesNotThrow( 
				() -> new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES), "Should not throw exception");
		
		assertEquals(FIRST_NAME, s1.getFirstName(), "Invalid First Name");
		assertEquals(LAST_NAME, s1.getLastName(), "Invalid Last Name");
		assertEquals(ID, s1.getId(), "Invalid ID");
		assertEquals(EMAIL, s1.getEmail(), "Invalid Email");
		assertEquals(PASSWORD, s1.getPassword(), "Invalid Password");
		assertEquals(MAX_COURSES, s1.getMaxCourses(), "Invalid courses");
		
		Exception e1 = assertThrows(IllegalArgumentException.class, 
				() -> new Faculty(FIRST_NAME, LAST_NAME, ID, "", PASSWORD, MAX_COURSES));
		assertEquals("Invalid email", e1.getMessage(), "Incorrect exception thrown with invalid Faculty object");
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(FIRST_NAME, LAST_NAME, null, EMAIL, PASSWORD, MAX_COURSES));
		assertEquals("Invalid id", e2.getMessage(), "Incorrect exception thrown with invalid Faculty object");
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(FIRST_NAME, LAST_NAME, "", EMAIL, PASSWORD, MAX_COURSES));
		assertEquals("Invalid id", e3.getMessage(), "Incorrect exception thrown with invalid Faculty object");

	}

	/***
	 * Tests constructing a Faculty with default max courses
	 */
	@Test
	void testFacultyConstantcourses() {
		Faculty s1 = assertDoesNotThrow( 
				() -> new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD), "Should not throw exception");
		
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(null, LAST_NAME, ID, null, PASSWORD));
		assertEquals("Invalid first name", e1.getMessage(), "Incorrect exception thrown with invalid Faculty object");
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(FIRST_NAME, null, ID, EMAIL, null));
		assertEquals("Invalid last name", e2.getMessage(), "Incorrect exception thrown with invalid Faculty object");
	
		assertEquals(FIRST_NAME, s1.getFirstName(), "Invalid First Name");
		assertEquals(LAST_NAME, s1.getLastName(), "Invalid Last Name");
		assertEquals(ID, s1.getId(), "Invalid ID");
		assertEquals(EMAIL, s1.getEmail(), "Invalid Email");
		assertEquals(PASSWORD, s1.getPassword(), "Invalid Password");
		assertEquals(3, s1.getMaxCourses(), "Invalid courses");
	}

	/**
	 * Test first name with valid and invalid inputs
	 */
	@Test
	void testSetFirstName() {
		//Construct a valid Faculty
		User s = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> s.setFirstName(null));
		assertEquals("Invalid first name", e1.getMessage()); //Check correct exception message
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> s.setFirstName(""));
		assertEquals("Invalid first name", e2.getMessage()); //Check correct exception message
		
		assertEquals(FIRST_NAME, s.getFirstName()); //Check that first name didn't change

	}
	
	/***
	 * Test last name with valid and invalid inputs
	 */
	@Test
	void testSetLastName() {
		// Construct a valid Faculty
		User s = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);

		Exception e1 = assertThrows(IllegalArgumentException.class, 
				() -> s.setLastName(null));
		assertEquals("Invalid last name", e1.getMessage()); // Check correct exception message

		Exception e2 = assertThrows(IllegalArgumentException.class, 
				() -> s.setLastName(""));
		assertEquals("Invalid last name", e2.getMessage()); // Check correct exception message

		assertEquals(LAST_NAME, s.getLastName()); // Check that last name didn't change

	}

	/**
	 * Test email with valid and invalid inputs
	 */
	@Test
	void testSetEmail() {
		// Construct a valid Faculty
		User s = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> s.setEmail(null));
		assertEquals("Invalid email", e1.getMessage()); // Check correct exception message

		Exception e2 = assertThrows(IllegalArgumentException.class, () -> s.setEmail(""));
		assertEquals("Invalid email", e2.getMessage()); // Check correct exception message
		
		Exception e3 = assertThrows(IllegalArgumentException.class, () -> s.setEmail("yagarwa2_ncsu.edu"));
		assertEquals("Invalid email", e3.getMessage()); //Check correct exception message
		
		Exception e4 = assertThrows(IllegalArgumentException.class, () -> s.setEmail("yagarwa2@ncsuedu"));
		assertEquals("Invalid email", e4.getMessage()); //Check correct exception message
		
		Exception e5 = assertThrows(IllegalArgumentException.class, () -> s.setEmail("yagarwa2.@ncsuedu"));
		assertEquals("Invalid email", e5.getMessage()); //Check correct exception message
	}

	/**
	 * Test password with valid and invalid inputs
	 */
	@Test
	void testSetPassword() {
		// Construct a valid Faculty
		User s = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);

		Exception e1 = assertThrows(IllegalArgumentException.class, () -> s.setPassword(null));
		assertEquals("Invalid password", e1.getMessage()); // Check correct exception message

		Exception e2 = assertThrows(IllegalArgumentException.class, () -> s.setPassword(""));
		assertEquals("Invalid password", e2.getMessage()); // Check correct exception message

		assertEquals(PASSWORD, s.getPassword()); // Check that password didn't change

	}

	/**
	 * Test max courses with valid and invalid inputs
	 */
	@Test
	void testsetMaxCourses() {
		// Construct a valid Faculty
		Faculty s = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);

		Exception e1 = assertThrows(IllegalArgumentException.class, () -> s.setMaxCourses(0));
		assertEquals("Invalid max courses", e1.getMessage()); // Check correct exception message

		Exception e2 = assertThrows(IllegalArgumentException.class, () -> s.setMaxCourses(4));
		assertEquals("Invalid max courses", e2.getMessage()); // Check correct exception message

		assertEquals(MAX_COURSES, s.getMaxCourses()); // Check that max courses didn't change

	}

	/***
	 * Tests that the equals method works for all Faculty fields.
	 */
	@Test
	void testEqualsObject() {
		// Construct a valid Faculty
		User s1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		User s2 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		User s3 = new Faculty("First", LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		User s4 = new Faculty(FIRST_NAME, "Last", ID, EMAIL, PASSWORD, MAX_COURSES);
		User s5 = new Faculty(FIRST_NAME, LAST_NAME, "flast", EMAIL, PASSWORD, MAX_COURSES);
		User s6 = new Faculty(FIRST_NAME, LAST_NAME, ID, "flast@ncsu.edu", PASSWORD, MAX_COURSES);
		User s7 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "firstlastname", MAX_COURSES);
		User s8 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 1);

		// Test for equality in both directions
		assertTrue(s1.equals(s2));
		assertTrue(s2.equals(s1));

		// Test for each of the fields
		assertFalse(s1.equals(s3));
		assertFalse(s1.equals(s4));
		assertFalse(s1.equals(s5));
		assertFalse(s1.equals(s6));
		assertFalse(s1.equals(s7));
		assertFalse(s1.equals(s8));
	}

	/***
	 * Tests that toString returns the correct comma-separated value.
	 */
	@Test
	void testToString() {
		User s1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		assertEquals("Francis,Faculty,ffaculty,ffaculty@ncsu.edu," + PASSWORD + ",3", s1.toString());	}
	
	
	
	
}
