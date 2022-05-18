package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

class CourseCatalogTest {
	
	/** Valid course records */
	private final String validTestFile = "test-files/course_records.txt";
	
	/** Course name */
	private static final String NAME = "CSC216";
	/** Course title */
	private static final String TITLE = "Software Development Fundamentals";
	/** Course section */
	private static final String SECTION = "001";
	/** Course credits */
	private static final int CREDITS = 3;
	/** Course instructor id */
	private static final String INSTRUCTOR_ID = "sesmith5";
	/** Course meeting days */
	private static final String MEETING_DAYS = "TH";
	/** Course start time */
	private static final int START_TIME = 1330;
	/** Course end time */
	private static final int END_TIME = 1445;
	/** Maximum Enrollment Capacity */
	private static final int ENROLLMENT_CAP = 10;

	/**
	 * Tests the CourseCatalog Constructor.
	 */
	@Test
	void testCourseCatalog() {
		CourseCatalog cc1 = new CourseCatalog();
		assertEquals(0, cc1.getCourseCatalog().length);
	}

	/**
	 * Tests the newCourseCatalog method in CourseCatalog.
	 */
	@Test
	void testNewCourseCatalog() {
		CourseCatalog cc1 = new CourseCatalog();
		cc1.loadCoursesFromFile(validTestFile);
		assertEquals(13, cc1.getCourseCatalog().length);
		cc1.newCourseCatalog();
		assertEquals(0, cc1.getCourseCatalog().length);
	}

	/**
	 * Tests the loadCourseFromFile method in CourseCatalog.
	 */
	@Test
	void testLoadCourseFromFile() {
		CourseCatalog cc1 = new CourseCatalog();
		cc1.loadCoursesFromFile(validTestFile);
		assertEquals(13, cc1.getCourseCatalog().length);
	}

	/**
	 * Tests the addCourseToCatalog method in CourseCatalog.
	 */
	@Test
	void testAddCourseToCatalog() {
		CourseCatalog cc1 = new CourseCatalog();
		cc1.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals(1, cc1.getCourseCatalog().length);
		String [] course = cc1.getCourseCatalog()[0];
		assertEquals(NAME, course[0]);
		assertEquals(SECTION, course[1]);
		assertEquals(TITLE, course[2]);
		assertEquals("TH 1:30PM-2:45PM", course[3]);
		assertFalse(cc1.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
	}

	/**
	 * Tests the removeCourseFromCatalog method in CourseCatalog.
	 */
	@Test
	void testRemoveCourseFromCatalog() {
		CourseCatalog cc1 = new CourseCatalog();
		cc1.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals(1, cc1.getCourseCatalog().length);
		cc1.removeCourseFromCatalog(NAME, SECTION);
		assertEquals(0, cc1.getCourseCatalog().length);
		assertFalse(cc1.removeCourseFromCatalog(NAME, SECTION));
	}

	/**
	 * Tests the getCourseFromCatalog method in CourseCatalog.
	 */
	@Test
	void testGetCourseFromCatalog() {
		CourseCatalog cc1 = new CourseCatalog();
		cc1.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		Course course = cc1.getCourseFromCatalog(NAME, SECTION);
		assertEquals(NAME, course.getName());
		assertEquals(SECTION, course.getSection());
		assertEquals(TITLE, course.getTitle());
		assertEquals("TH 1:30PM-2:45PM", course.getMeetingString());
	}

	/**
	 * Tests the getCourseCatalog method in CourseCatalog.
	 */
	@Test
	void testGetCourseCatalog() {
		CourseCatalog cc1 = new CourseCatalog();
		cc1.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals(1, cc1.getCourseCatalog().length);
		String [] course = cc1.getCourseCatalog()[0];
		assertEquals(NAME, course[0]);
		assertEquals(SECTION, course[1]);
		assertEquals(TITLE, course[2]);
		assertEquals("TH 1:30PM-2:45PM", course[3]);
	}

	/**
	 * Tests the saveCourseCatalog method in CourseCatalog.
	 */
	@Test
	void testSaveCourseCatalog() {
		CourseCatalog cc1 = new CourseCatalog();
		cc1.saveCourseCatalog("test-files/actual_empty_export.txt");
		checkFiles("test-files/expected_empty_export.txt", "test-files/actual_empty_export.txt");
		
		cc1.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals(1, cc1.getCourseCatalog().length);
		cc1.saveCourseCatalog("test-files/actual_schedule_export.txt");
		checkFiles("test-files/course_catalog_records.txt", "test-files/actual_schedule_export.txt");
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
			 Scanner actScanner = new Scanner(new File(actFile));) {
			
			while (actScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			if (expScanner.hasNextLine()) {
				fail();
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
