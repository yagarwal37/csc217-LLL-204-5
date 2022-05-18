package edu.ncsu.csc216.pack_scheduler.users;


import static org.junit.jupiter.api.Assertions.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;

/**
 * Tests the Student object.
 * @author SarahHeckman
 */
public class StudentTest {
	
	/** Test Student's first name. */
	private String firstName = "first";
	/** Test Student's last name */
	private String lastName = "last";
	/** Test Student's id */
	private String id = "flast";
	/** Test Student's email */
	private String email = "first_last@ncsu.edu";
	/** Test Student's hashed password */
	private String hashPW;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	//This is a block of code that is executed when the StudentTest object is
	//created by JUnit.  Since we only need to generate the hashed version
	//of the plaintext password once, we want to create it as the StudentTest object is
	//constructed.  By automating the hash of the plaintext password, we are
	//not tied to a specific hash implementation.  We can change the algorithm
	//easily.
	{
		try {
			String plaintextPW = "password";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(plaintextPW.getBytes());
			this.hashPW = new String(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			fail("An unexpected NoSuchAlgorithmException was thrown.");
		}
	}
	
	/**
	 * Test toString() method.
	 */
	@Test
	public void testToString() {
		User s1 = new Student(firstName, lastName, id, email, hashPW);
		assertEquals("first,last,flast,first_last@ncsu.edu," + hashPW + ",18", s1.toString());
	}

	/**
	 * 	Tests compareTo method in Student class
	 */
	@Test
	public void testCompareTo(){
		Student s1 = new Student(firstName, lastName, id, email, hashPW);
		Student s2 = new Student(firstName, lastName, id, email, hashPW);
		Student s3 = new Student(firstName, "a", id, email, hashPW);
		Student s4 = new Student("a", lastName, id, email, hashPW);
		Student s5 = new Student(firstName, lastName, "aa", email, hashPW);
		Student s6 = new Student(firstName, "z", id, email, hashPW);
		Student s7 = new Student("z", lastName, id, email, hashPW);
		Student s8 = new Student(firstName, lastName, "zz", email, hashPW);
		
		assertEquals(s1.compareTo(s2), 0);
		assertEquals(s2.compareTo(s1), 0);
		
		assertEquals(s1.compareTo(s3), 1);
		assertEquals(s3.compareTo(s1), -1);
		
		assertEquals(s2.compareTo(s3), 1);
		assertEquals(s3.compareTo(s2), -1);
		
		assertEquals(s1.compareTo(s4), 1);
		assertEquals(s4.compareTo(s1), -1);
		
		assertEquals(s2.compareTo(s4), 1);
		assertEquals(s4.compareTo(s2), -1);
		
		assertEquals(s1.compareTo(s5), 1);
		assertEquals(s5.compareTo(s1), -1);
		
		assertEquals(s2.compareTo(s5), 1);
		assertEquals(s5.compareTo(s2), -1);
		
		assertEquals(s1.compareTo(s6), -1);
		assertEquals(s6.compareTo(s1), 1);
		
		assertEquals(s2.compareTo(s6), -1);
		assertEquals(s6.compareTo(s2), 1);
		
		assertEquals(s1.compareTo(s7), -1);
		assertEquals(s7.compareTo(s1), 1);
		
		assertEquals(s2.compareTo(s7), -1);
		assertEquals(s7.compareTo(s2), 1);
		
		assertEquals(s1.compareTo(s8), -1);
		assertEquals(s8.compareTo(s1), 1);
		
		assertEquals(s2.compareTo(s8), -1);
		assertEquals(s8.compareTo(s2), 1);
		
		Exception e1 = assertThrows(NullPointerException.class,
				() -> s1.compareTo(null));
		assertEquals(null, e1.getMessage(), "Incorrect exception thrown with finding index of null element");
	}
	
}
