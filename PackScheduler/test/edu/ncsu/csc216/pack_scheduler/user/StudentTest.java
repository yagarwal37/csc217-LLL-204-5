package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Tests the Student class through setters, getters, and constructors 
 * 
 * @author yash
 * @author tadeo
 * @author tanmay
 *
 */
class StudentTest {
	
	/** student's first name */
	private static final String FIRST_NAME = "Tadeo";
	
	/** student's last name */
	private static final String LAST_NAME = "Theis";
	
	/** student's id */
	private static final String ID = "ttheis2";
	
	/** student's email */
	private static final String EMAIL = "ttheis2@ncsu.edu";
	
	/** student's password */
	private static final String PASSWORD = "CSC216&CSC217";
	
	/** maximum possible credits any student can have */
	private static final int MAX_CREDITS = 18;
	
	/**
	 * Tests hashCode if working correctly 
	 */
	@Test
	void testHashCode() {
		// Construct a valid Student
		User s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s3 = new Student("First", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s4 = new Student(FIRST_NAME, "Last", ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s5 = new Student(FIRST_NAME, LAST_NAME, "flast", EMAIL, PASSWORD, MAX_CREDITS);
		User s6 = new Student(FIRST_NAME, LAST_NAME, ID, "flast@ncsu.edu", PASSWORD, MAX_CREDITS);
		User s7 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "firstlastname", MAX_CREDITS);
		User s8 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 14);
		
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
	 * Tests constructing a student with max credits
	 */
	@Test
	void testStudentWithCredits() {
		Student s1 = assertDoesNotThrow( 
				() -> new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS), "Should not throw exception");
		
		assertEquals(FIRST_NAME, s1.getFirstName(), "Invalid First Name");
		assertEquals(LAST_NAME, s1.getLastName(), "Invalid Last Name");
		assertEquals(ID, s1.getId(), "Invalid ID");
		assertEquals(EMAIL, s1.getEmail(), "Invalid Email");
		assertEquals(PASSWORD, s1.getPassword(), "Invalid Password");
		assertEquals(MAX_CREDITS, s1.getMaxCredits(), "Invalid Credits");
		
		Exception e1 = assertThrows(IllegalArgumentException.class, 
				() -> new Student(FIRST_NAME, LAST_NAME, ID, "", PASSWORD, MAX_CREDITS));
		assertEquals("Invalid email", e1.getMessage(), "Incorrect exception thrown with invalid student object");
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, LAST_NAME, null, EMAIL, PASSWORD, MAX_CREDITS));
		assertEquals("Invalid id", e2.getMessage(), "Incorrect exception thrown with invalid student object");
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, LAST_NAME, "", EMAIL, PASSWORD, MAX_CREDITS));
		assertEquals("Invalid id", e3.getMessage(), "Incorrect exception thrown with invalid student object");

	}

	/**
	 * Tests constructing a student with default max credits
	 */
	@Test
	void testStudentConstantCredits() {
		Student s1 = assertDoesNotThrow( 
				() -> new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD), "Should not throw exception");
		
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(null, LAST_NAME, ID, null, PASSWORD));
		assertEquals("Invalid first name", e1.getMessage(), "Incorrect exception thrown with invalid student object");
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, null, ID, EMAIL, null));
		assertEquals("Invalid last name", e2.getMessage(), "Incorrect exception thrown with invalid student object");
	
		assertEquals(FIRST_NAME, s1.getFirstName(), "Invalid First Name");
		assertEquals(LAST_NAME, s1.getLastName(), "Invalid Last Name");
		assertEquals(ID, s1.getId(), "Invalid ID");
		assertEquals(EMAIL, s1.getEmail(), "Invalid Email");
		assertEquals(PASSWORD, s1.getPassword(), "Invalid Password");
		assertEquals(18, s1.getMaxCredits(), "Invalid Credits");
	}

	/*
	 * Test first name with valid and invalid inputs
	 */
	@Test
	void testSetFirstName() {
		//Construct a valid Student
		User s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> s.setFirstName(null));
		assertEquals("Invalid first name", e1.getMessage()); //Check correct exception message
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> s.setFirstName(""));
		assertEquals("Invalid first name", e2.getMessage()); //Check correct exception message
		
		assertEquals(FIRST_NAME, s.getFirstName()); //Check that first name didn't change

	}
	
	/*
	 * Test last name with valid and invalid inputs
	 */
	@Test
	void testSetLastName() {
		// Construct a valid Student
		User s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);

		Exception e1 = assertThrows(IllegalArgumentException.class, 
				() -> s.setLastName(null));
		assertEquals("Invalid last name", e1.getMessage()); // Check correct exception message

		Exception e2 = assertThrows(IllegalArgumentException.class, 
				() -> s.setLastName(""));
		assertEquals("Invalid last name", e2.getMessage()); // Check correct exception message

		assertEquals(LAST_NAME, s.getLastName()); // Check that last name didn't change

	}

	/*
	 * Test email with valid and invalid inputs
	 */
	@Test
	void testSetEmail() {
		// Construct a valid Student
		User s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		
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

	/*
	 * Test password with valid and invalid inputs
	 */
	@Test
	void testSetPassword() {
		// Construct a valid Student
		User s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);

		Exception e1 = assertThrows(IllegalArgumentException.class, () -> s.setPassword(null));
		assertEquals("Invalid password", e1.getMessage()); // Check correct exception message

		Exception e2 = assertThrows(IllegalArgumentException.class, () -> s.setPassword(""));
		assertEquals("Invalid password", e2.getMessage()); // Check correct exception message

		assertEquals(PASSWORD, s.getPassword()); // Check that password didn't change

	}

	/*
	 * Test max credits with valid and invalid inputs
	 */
	@Test
	void testSetMaxCredits() {
		// Construct a valid Student
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);

		Exception e1 = assertThrows(IllegalArgumentException.class, () -> s.setMaxCredits(2));
		assertEquals("Invalid max credits", e1.getMessage()); // Check correct exception message

		Exception e2 = assertThrows(IllegalArgumentException.class, () -> s.setMaxCredits(19));
		assertEquals("Invalid max credits", e2.getMessage()); // Check correct exception message

		assertEquals(MAX_CREDITS, s.getMaxCredits()); // Check that max credits didn't change

	}

	/**
	 * Tests that the equals method works for all Student fields.
	 */
	@Test
	void testEqualsObject() {
		// Construct a valid Student
		User s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s3 = new Student("First", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s4 = new Student(FIRST_NAME, "Last", ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s5 = new Student(FIRST_NAME, LAST_NAME, "flast", EMAIL, PASSWORD, MAX_CREDITS);
		User s6 = new Student(FIRST_NAME, LAST_NAME, ID, "flast@ncsu.edu", PASSWORD, MAX_CREDITS);
		User s7 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "firstlastname", MAX_CREDITS);
		User s8 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 14);

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

	/**
	 * Tests that toString returns the correct comma-separated value.
	 */
	@Test
	void testToString() {
		User s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		assertEquals("Tadeo,Theis,ttheis2,ttheis2@ncsu.edu," + PASSWORD + ",18", s1.toString());	}

}
