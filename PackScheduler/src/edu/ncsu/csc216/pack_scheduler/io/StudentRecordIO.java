package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Reads Student's information from text files. Writes a set of StudentRecords to a file.
 * 
 * @author yashagarwal
 * @author tanmaysoni
 * @author tadeotheis
 *
 */
public class StudentRecordIO {

	/**
	 * Processes lines from readStudentRecords and converts them into Student objects  
	 * 
	 * @param line a line from the file 
	 * @return temp A Student object with the fields taken from String line
	 * @throws IllegalArgumentException if a Student object can not be made from line, throw exception
	 * @throws NoSuchElementException if any of the fields from line do not exist
	 */
	private static Student processStudent(String line) {
		Scanner studentReader = new Scanner(line);
		studentReader.useDelimiter(",");
				
		try {
			String firstName = studentReader.next();
			String lastName = studentReader.next();
			String id = studentReader.next();
			String email = studentReader.next();
			String hashedPassword = studentReader.next();
			int maxCredits = studentReader.nextInt();
			
			Student temp = new Student(firstName, lastName, id, email, hashedPassword, maxCredits);
			studentReader.close();
			return temp; 
			
			/*if(studentReader.hasNextInt()) {
				maxCredits = studentReader.nextInt();
				Student temp = new Student(firstName, lastName, id, email, hashedPassword, maxCredits);
				
				if(studentReader.hasNext()) {
					studentReader.close();
					throw new IllegalArgumentException("Invalid student.");
				}
				
				studentReader.close();
				return temp;
			}
			else {
				Student temp = new Student(firstName, lastName, id, email, hashedPassword);
				
				if(studentReader.hasNext()) {
					studentReader.close();
					throw new IllegalArgumentException("Invalid student.");
				}
				
				studentReader.close();
				return temp;
			}
*/

		} catch(NoSuchElementException e) {
			studentReader.close();
			throw new IllegalArgumentException("Invalid student.");
		}
	}
	
	
	/**
	 * Reads a file inputed by the user and placed into an ArrayList of Students
	 * 
	 * @param fileName name of the file passed in by the user
	 * @return studentsList returns an SortedList of students from the input file
	 * @throws FileNotFoundException thrown if file does not exist in the file system
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		
		SortedList<Student> studentsList = new SortedList<Student>();
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		
		while(fileReader.hasNextLine()) {
			try {
				Student student = processStudent(fileReader.nextLine());
				boolean dupe = false;
	            for (int i = 0; i < studentsList.size(); i++) {
	                User current = studentsList.get(i);
	                if (student.getId().equals(current.getId())) {
	                    dupe = true;
	                    break; 
	                }
	            }
	            if (!dupe) {
	                studentsList.add(student);
	            } 
	        } catch (IllegalArgumentException e) {
	            //The line is invalid since we could not create a student
	        }
		}
		
		fileReader.close();
		return studentsList;
	}
	/**
	 * Creates a file with all the students from studentDirectory
	 * 
	 * @param fileName name of the file passed in by the user 
	 * @param studentDirectory SortedList of Students to write
	 * @throws IOException if the file cannot be written
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < studentDirectory.size(); i++) {
			fileWriter.println(studentDirectory.get(i).toString());
		}

		fileWriter.close();
	}

}
