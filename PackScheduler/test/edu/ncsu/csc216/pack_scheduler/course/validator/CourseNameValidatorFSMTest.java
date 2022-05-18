package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CourseNameValidatorFSMTest {

	/**
	 * Tests valid course names to ensure they return true
	 */
	@Test
	void testIsValidTrue() {
		CourseNameValidatorFSM cnv = new CourseNameValidatorFSM();
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
		CourseNameValidatorFSM cnv = new CourseNameValidatorFSM();
		//Test all unique error messages
		try {
			assertFalse(cnv.isValid("E11"), "Awaiting next state so false");
		} catch (InvalidTransitionException e) {
			//Should not throw anything
			fail();
		}

		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("E101102"));
		assertEquals(e1.getMessage(), "Course name can only have 3 digits.");

		Exception e2 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("ABCDE"));
		assertEquals(e2.getMessage(), "Course name cannot start with more than 4 letters.");

		Exception e3 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("!101"));
		assertEquals(e3.getMessage(), "Course name can only contain letters and digits.");

		Exception e4 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("E101AS"));
		assertEquals(e4.getMessage(), "Course name can only have a 1 letter suffix.");

		Exception e5 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("E101A0"));
		assertEquals(e5.getMessage(), "Course name cannot contain digits after the suffix.");

		Exception e6 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("217CSC"));
		assertEquals(e6.getMessage(), "Course name must start with a letter.");	

		Exception e7 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("E1A"));
		assertEquals(e7.getMessage(), "Course name must have 3 digits.");

		Exception e8 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("E10A"));
		assertEquals(e8.getMessage(), "Course name must have 3 digits.");	

	}
	
	
}
