/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Tests the FacultyDirectory class
 * @author Caleb Twigg
 *
 */
public class FacultyDirectoryTest {

	
	/** Valid faculty records */
	private final String validTestFile = "test-files/faculty_records.txt";
	/** Test first name */
	private static final String FIRST_NAME = "Doctor";
	/** Test last name */
	private static final String LAST_NAME = "Strange";
	/** Test id */
	private static final String ID = "docstr";
	/** Test email */
	private static final String EMAIL = "docstr@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max courses */
	private static final int MAX_COURSES = 3;
	
	/**
	 * Resets faculty_records.txt for use in other tests.
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {		
		//Reset faculty_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_faculty_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}
	
	/**
	 * Tests FacultyDirectory's constructor
	 */
	@Test
	public void testFacultyDirectory() {
		//	Test an empty Faculty Directory
		FacultyDirectory fd = new FacultyDirectory();
		assertFalse(fd.removeFaculty(ID));
		assertEquals(0, fd.getFacultyDirectory().length);
	}
	
	
	/**
	 * Tests getStudentById() with first, middle, and final students in the directory.
	 */
	@Test
	public void testGetStudentById() {
		//Test adding students to the directory and trying to retrieve them
		FacultyDirectory fd = new FacultyDirectory();
		fd.loadFacultyFromFile(validTestFile);
		
		//Construct an expected faculty and compare it with the return
		Faculty exp1 = new Faculty("Ashely", "Witt", "awitt",
				"mollis@Fuscealiquetmagna.net", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 2);
		Faculty act1 = fd.getFacultyById("awitt");
		assertTrue(exp1.equals(act1));
		
		Faculty exp2 = new Faculty("Fiona", "Meadows", "fmeadow",
				"pharetra.sed@et.org", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 3);
		Faculty act2 = fd.getFacultyById("fmeadow");
		assertTrue(exp2.equals(act2));
		
		Faculty exp3 = new Faculty("Brent", "Brewer", "bbrewer",
				"sem.semper@orcisem.co.uk", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 1);
		Faculty act3 = fd.getFacultyById("bbrewer");
		assertTrue(exp3.equals(act3));
		
	}
	
	/**
	 * Tests creating a new Faculty Directory.
	 */
	@Test
	public void testNewFacultyDirectory() {
		//Test that if there are faculty in the directory, they 
		//are removed after calling newFacultyDirectory().
		FacultyDirectory fd = new FacultyDirectory();
		
		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);
		
		fd.newFacultyDirectory();
		assertEquals(0, fd.getFacultyDirectory().length);
	}

	/**
	 * Tests StudentDirectory.addStudent().
	 */
	@Test
	public void testAddFaculty() {
		FacultyDirectory fd = new FacultyDirectory();
		
		//Test valid Student
		fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		
		String [][] facultyDirectory = fd.getFacultyDirectory();
		assertEquals(1, facultyDirectory.length);
		assertTrue(FIRST_NAME.equals(facultyDirectory[0][0]));
		assertTrue(LAST_NAME.equals(facultyDirectory[0][1]));
		assertSame(ID, facultyDirectory[0][2]);
		
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, null, PASSWORD, MAX_COURSES));
		assertTrue("Invalid password".equals(e1.getMessage()));
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "", PASSWORD, MAX_COURSES));
		assertTrue("Invalid password".equals(e2.getMessage()));
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, null, MAX_COURSES));
		assertTrue("Invalid password".equals(e3.getMessage()));
		
		Exception e4 = assertThrows(IllegalArgumentException.class,
				() -> fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, "", MAX_COURSES));
		assertTrue("Invalid password".equals(e4.getMessage()));
		
		Exception e5 = assertThrows(IllegalArgumentException.class,
				() -> fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, "Password", MAX_COURSES));
		assertTrue("Passwords do not match".equals(e5.getMessage()));
	}

	/**
	 * Tests removing and saving a faculty directory
	 */
	@Test
	public void testSaveStudentDirectory() {
		FacultyDirectory fd = new FacultyDirectory();
		
		fd.loadFacultyFromFile(validTestFile);
		
		//Remove faculty
		fd.removeFaculty("lwalls");
		fd.removeFaculty("nbrady");
		fd.removeFaculty("ebriggs");
		fd.removeFaculty("kpatel");
		fd.removeFaculty("haguirr");
		
		assertEquals(3, fd.getFacultyDirectory().length);
		fd.saveFacultyDirectory("test-files/actual_faculty_records.txt");
		checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));
			
			while (expScanner.hasNextLine()) {
				String expString = expScanner.nextLine();
				String actString = actScanner.nextLine();
				
				assertTrue(expString.equals(actString));
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
}
