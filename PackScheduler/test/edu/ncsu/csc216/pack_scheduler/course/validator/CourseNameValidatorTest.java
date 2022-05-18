package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CourseNameValidatorTest {

	/**
	 * Tests valid course names to ensure they return true
	 */
	@Test
	void testIsValidTrue() {
		CourseNameValidator cnv = new CourseNameValidator();
		try {
			assertTrue(cnv.isValid("CSC216"), "CSC216 should be valid");
			assertTrue(cnv.isValid("HESA215"), "Valid upper bound");
			assertTrue(cnv.isValid("E115"), "Valid lower bound");
			assertTrue(cnv.isValid("ENG101A"), "With suffix");
			assertTrue(cnv.isValid("CH101A"), "Two letters, valid");
		} catch (InvalidTransitionException e) {
			//Should not throw an exception
			fail();
		}
	}

	/**
	 * Tests invalid course names to throw exceptions with all possible messages
	 */
	@Test
	void testIsValidFalse() {
		CourseNameValidator cnv = new CourseNameValidator();
		//Test all unique error messages
		try {
			assertFalse(cnv.isValid("E11"), "Awaiting next state so false");
		} catch (InvalidTransitionException e) {
			//Should not throw anything
			fail();
		}

		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("E101102"));
		assertEquals("Course name can only have 3 digits.", e1.getMessage());

		Exception e2 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("ABCDE"));
		assertEquals("Course name cannot start with more than 4 letters.", e2.getMessage());

		Exception e3 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("!101"));
		assertEquals("Course name can only contain letters and digits.", e3.getMessage());

		Exception e4 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("E101AS"));
		assertEquals("Course name can only have a 1 letter suffix.", e4.getMessage());

		Exception e5 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("E101A0"));
		assertEquals("Course name cannot contain digits after the suffix.", e5.getMessage());

		Exception e6 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("217CSC"));
		assertEquals("Course name must start with a letter.", e6.getMessage());	

		Exception e7 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("E1A"));
		assertEquals("Course name must have 3 digits.", e7.getMessage());

		Exception e8 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("E10A"));
		assertEquals("Course name must have 3 digits.", e8.getMessage());	

	}
	
	
}
