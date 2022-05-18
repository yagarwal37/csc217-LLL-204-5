/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * IO Class for the Faculty Records
 * @author Caleb Twigg
 *
 */
public class FacultyRecordIO {


	/**
	 * Writes a directory of faculty to a file
	 * @param fileName the name of the file that the directory will be added to
	 * @param facultyDirectory the directory of the faculty users
	 * @throws IOException if the file cannot be created
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		
		for (int i = 0; i < facultyDirectory.size(); i++) {
			fileWriter.println(facultyDirectory.get(i).toString());
		}
		
		fileWriter.close();
	}

	/**
	 * Reads a file of Faculty Records and returns a Linked List
	 * Duplicate checks are already handled by the LinkedList class
	 * @param filename the name of the file containing the faculty records
	 * @return null a linkedList of Faculty objects
	 * @throws FileNotFoundException if the File cannot be found
	 */
	public static LinkedList<Faculty> readFacultyRecords(String filename) throws FileNotFoundException {
		
		LinkedList<Faculty> facultyList = new LinkedList<Faculty>();
		Scanner fileReader = new Scanner(new FileInputStream(filename));
		
		while(fileReader.hasNextLine()) {
			try {
				Faculty faculty = processFaculty(fileReader.nextLine());
				facultyList.add(faculty);
			}
			
			catch (IllegalArgumentException e) {
				//	Do nothing as there is not a faculty object
			}
		}
			
		fileReader.close();
		return facultyList;
	}
	
	/**
	 * Processes a line from a file and returns a Faculty object
	 * @param line the String containing the Faculty information
	 * @return Faculty the created Faculty object
	 * @throws IllegalArgumentException if the Faculty object cannot be created
	 */
	private static Faculty processFaculty(String line) {
		
		Scanner facultyReader = new Scanner(line);
		facultyReader.useDelimiter(",");
		
		Faculty faculty;
		
		try {
			//	Each faculty object must have these strings
			String firstName = facultyReader.next();
			String lastName = facultyReader.next();
			String id = facultyReader.next();
			String email = facultyReader.next();
			String password = facultyReader.next();
			
			//	The maxCourses field is optional
			if (facultyReader.hasNextInt()) {
				int maxCourses = facultyReader.nextInt();
				
				faculty = new Faculty(firstName, lastName, id, email, password, maxCourses);
			}
			else {
				faculty = new Faculty(firstName, lastName, id, email, password);
			}
			
		}
		catch(NoSuchElementException e) {
			facultyReader.close();
			throw new IllegalArgumentException("Invalid faculty.");
		}
		
		facultyReader.close();
		return faculty;
		
	}
	
}
