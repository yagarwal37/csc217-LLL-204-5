package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Student;

class CourseRollTest {
	
	@Test
	void testCourseRoll() {
		
		StudentDirectory studentDirectory = new StudentDirectory();
		
		studentDirectory.loadStudentsFromFile("test-files/student_records.txt");
		
		Course course = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll c = course.getCourseRoll();
		
		
		//	Test Exceptions
		assertThrows(IllegalArgumentException.class, () -> c.setEnrollmentCap(0));
		assertThrows(IllegalArgumentException.class, () -> c.setEnrollmentCap(251));
		assertThrows(IllegalArgumentException.class, () -> c.setEnrollmentCap(5));
		
		
		//Test good constructor and open seats / enrollment cap
		assertEquals(10, c.getEnrollmentCap());
		assertEquals(10, c.getOpenSeats());

		//Test canEnroll and enrolling student, verifying that fields change
		assertThrows(IllegalArgumentException.class, () -> c.enroll(null));
		
		Student student = studentDirectory.getStudentById("zking");
		assertTrue(c.canEnroll(student));
		
		c.enroll(studentDirectory.getStudentById("zking"));
		assertEquals(9, c.getOpenSeats());
		c.enroll(studentDirectory.getStudentById("cschwartz"));
		assertEquals(8, c.getOpenSeats());
		c.enroll(studentDirectory.getStudentById("shansen"));
		c.enroll(studentDirectory.getStudentById("daustin"));
		c.enroll(studentDirectory.getStudentById("rbrennan"));
		c.enroll(studentDirectory.getStudentById("efrost"));
		c.enroll(studentDirectory.getStudentById("lberg"));
		c.enroll(studentDirectory.getStudentById("gstone"));
		c.enroll(studentDirectory.getStudentById("ahicks"));
		c.enroll(studentDirectory.getStudentById("dnolan"));
		assertEquals(0, c.getOpenSeats());

		//	Add a new student to the waitlist
		Student studentA = new Student("Caleb", "Twigg", "wctwigg", "wctwigg@ncsu.edu", "pw", 15);
		assertEquals(0, c.getNumberOnWaitlist());
		assertTrue(c.canEnroll(studentA));
		
		c.enroll(studentA);
		
		assertEquals(1, c.getNumberOnWaitlist());
		
		//Try to add a duplicate student
		assertFalse(c.canEnroll(studentDirectory.getStudentById("zking")));
		assertThrows(IllegalArgumentException.class, () -> c.enroll(studentDirectory.getStudentById("zking")));
		
	
		//Test drop and the waitlist add
		assertThrows(IllegalArgumentException.class, () -> c.drop(null));
		try {
			c.drop(studentDirectory.getStudentById("zking"));
			assertEquals(0, c.getOpenSeats());
			assertEquals(0, c.getNumberOnWaitlist());
		} catch (Exception e) {
			fail("Exception thrown but not expected");
		}
		
		
	}

}
