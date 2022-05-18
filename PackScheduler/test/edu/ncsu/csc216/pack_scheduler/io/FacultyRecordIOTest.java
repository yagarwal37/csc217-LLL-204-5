/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Tests the FacultyRecordIO Class
 * @author Caleb Twigg
 *
 */
public class FacultyRecordIOTest {

	/**
	 * Tests the readFacultyRecords method
	 * @throws IOException  exception thrown if file is not found
	 */
	@Test
	public void testReader() throws IOException {
		try{
			FacultyRecordIO.readFacultyRecords("test-files/expected_faculty_records.txt");
		} catch(IOException e){
			fail();
		}
		
		try {
			FacultyRecordIO.readFacultyRecords("test-files/invalid_faculty_records.txt");
		} catch(Exception e) {
			fail();
		}
	}
	
	/**
	 * Tests the writeFacultyRecords method
	 */
	@Test
	public void testWriter() {
		
		//	Try writing an empty directory
		LinkedList<Faculty> fDirectory = new LinkedList<Faculty>();
		
		try {
			FacultyRecordIO.writeFacultyRecords("test-files/writer_test_file", fDirectory);
		} catch(IOException e) {
			fail();
		}
		
		LinkedList<Faculty> fDirectory2 = new LinkedList<Faculty>();
		
		//	Try writing a full directory
		Faculty f3 = new Faculty("Ashely", "Witt", "awitt",
				"mollis@Fuscealiquetmagna.net", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=");
		Faculty f4 = new Faculty("Fiona", "Meadows", "fmeadow",
				"pharetra.sed@et.org", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 3);
		
		fDirectory2.add(f3);
		fDirectory2.add(f4);
		
		try {
			FacultyRecordIO.writeFacultyRecords("test-files/writer_test_file", fDirectory2);
		} catch(IOException e) {
			fail();
		}
	}
}
