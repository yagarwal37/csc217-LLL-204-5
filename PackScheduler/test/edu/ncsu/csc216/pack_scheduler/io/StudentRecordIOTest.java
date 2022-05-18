package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

class StudentRecordIOTest {
	
	/**
	 * Valid test file of students
	 */
	private final String validTestFile = "test-files/student_records.txt";
	
	/**
	 * Invalid test file of students
	 */
	private final String invalidTestFile = "test-files/invalid_student_records.txt";
	
	
	/** Expected results for valid students in student_records.txt - line 1 */	
	private String validStudent0 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
	/** Expected results for valid students in student_records.txt - line 2 */
	private String validStudent1 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
	/** Expected results for valid students in student_records.txt - line 3 */
	private String validStudent2 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
	/** Expected results for valid students in student_records.txt - line 4 */
	private String validStudent3 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
	/** Expected results for valid students in student_records.txt - line 5 */
	private String validStudent4 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
	/** Expected results for valid students in student_records.txt - line 6 */
	private String validStudent5 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	/** Expected results for valid students in student_records.txt - line 7 */
	private String validStudent6 = "Lane,Berg,lberg,sociis@non.org,pw,14";
	/** Expected results for valid students in student_records.txt - line 8 */
	private String validStudent7 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";
	/** Expected results for valid students in student_records.txt - line 9 */
	private String validStudent8 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
	/** Expected results for valid students in student_records.txt - line 10 */
	private String validStudent9 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";
	/** Array to hold expected results */
	private String [] validStudents = {validStudent3, validStudent6, validStudent4, validStudent5, validStudent2, validStudent8, 
			validStudent0, validStudent9, validStudent1, validStudent7};
	/** Hash student passwords */
	private String hashPW;
	/** Constant set Hash student passwords */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Sets up files to be tested. 
	 */
	@BeforeEach
	public void setUp() {
	    try {
	        String password = "pw";
	        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
	        digest.update(password.getBytes());
	        hashPW = Base64.getEncoder().encodeToString(digest.digest());
	        
	        for (int i = 0; i < validStudents.length; i++) {
	            validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
	        }
	    } catch (NoSuchAlgorithmException e) {
	        fail("Unable to create hash during setup");
	    }
	}
	
	
	
	
	/**
	 * Tests readStudentRecords().
	 */
	@Test
	void testReadStudentRecords() { 
			try {
				SortedList<Student> students = StudentRecordIO.readStudentRecords(validTestFile);
				assertEquals(10, students.size());
				
				for (int i = 0; i < validStudents.length; i++) {
					assertEquals(validStudents[i], students.get(i).toString());
				}
			} catch (FileNotFoundException e) {
				fail("Unexpected error reading " + validTestFile);
			}
			
			SortedList<Student> students;
			try {
				students = StudentRecordIO.readStudentRecords(invalidTestFile);
				assertEquals(0, students.size());
			} catch (FileNotFoundException e) {
				fail("Unexpected FileNotFoundException");
			}
		}
	
	
	/**
	 * Test the writeStudentRecord method.
	 */
	@Test
	void testWriteStudentRecords() {
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 15));
		
		try {
			StudentRecordIO.writeStudentRecords("test-files/actual_student_records.txt", students);
		} catch (IOException e) {
			fail("Cannot write to student records file");
		}
		
		checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");	
	}
	
//	@Test
//	public void testWriteStudentRecordsNoPermissions() {
//		ArrayList<Student> students = new ArrayList<Student>();
//		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
//		
//		Exception exception = assertThrows(IOException.class, 
//				() -> StudentRecordIO.writeStudentRecords("/home/sesmith5/actual_student_records.txt", students));
//		assertEquals("/home/sesmith5/actual_student_records.txt (Permission denied)", exception.getMessage());
//	}
	
	
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
			 Scanner actScanner = new Scanner(new FileInputStream(actFile));) {
			
			while (expScanner.hasNextLine()  && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals(exp, act); 
				//The third argument helps with debugging!
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
}
