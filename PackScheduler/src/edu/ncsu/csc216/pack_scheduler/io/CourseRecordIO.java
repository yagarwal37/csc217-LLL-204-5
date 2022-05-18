package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Reads input files of courses and outputs ones that pass criteria
 * @author tadeotheis
 *
 */
public class CourseRecordIO {

	/**
     * Reads course records from a file and generates a list of valid Courses.  Any invalid
     * Courses are ignored.  If the file to read cannot be found or the permissions are incorrect
     * a File NotFoundException is thrown.
     * @param fileName file to read Course records from
     * @return a list of valid Courses
     * @throws FileNotFoundException if the file cannot be found or read
     */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
	    Scanner fileReader = new Scanner(new FileInputStream(fileName));  //Create a file scanner to read the file
	    SortedList<Course> courses = new SortedList<Course>(); //Create an empty array of Course objects
	    while (fileReader.hasNextLine()) { //While we have more lines in the file
	        try { //Attempt to do the following
	            //Read the line, process it in readCourse, and get the object
	            //If trying to construct a Course in readCourse() results in an exception, flow of control will transfer to the catch block, below
	            Course course = readCourse(fileReader.nextLine()); 

	            //Create a flag to see if the newly created Course is a duplicate of something already in the list  
	            boolean duplicate = false;
	            //Look at all the courses in our list
	            for (int i = 0; i < courses.size(); i++) {
	                //Get the course at index i
	                Course current = courses.get(i);
	                //Check if the name and section are the same
	                if (course.getName().equals(current.getName()) &&
	                        course.getSection().equals(current.getSection())) {
	                    //It's a duplicate!
	                    duplicate = true;
	                    break; //We can break out of the loop, no need to continue searching
	                }
	            }
	            //If the course is NOT a duplicate
	            if (!duplicate) {
	                courses.add(course); //Add to the ArrayList!
	            } //Otherwise ignore
	        } catch (IllegalArgumentException e) {
	            //The line is invalid b/c we couldn't create a course, skip it!
	        }
	    }
	    //Close the Scanner b/c we're responsible with our file handles
	    fileReader.close();
	    //Return the ArrayList with all the courses we read!
	    return courses;
	}
	
	/**
	 * Reads a string representing a course for the readCourseRecords method and creates a course object
	 * with that information. If there is anything past the "A" for meeting days, anything past the end time, or any 
	 * noSuchElement exceptions an IAE is thrown. 
	 * @param course string from input file
	 * @return a new course object 
	 * @throws IllegalArgumentException if there is anything past the "A" for meeting days, anything past the end time, or any 
	 * noSuchElement exceptions 
	 */
    private static Course readCourse(String course) {
		Scanner courseReader = new Scanner(course);
		courseReader.useDelimiter(",");
		
		String name;
		String title;
		String section;
		int creditHours;
		String instructor = null;
		int enrollmentCap;
		String meetingDays;
		Course newCourse;
		
		Faculty tempFaculty = null;
		
		
		try {
			name = courseReader.next();
			title = courseReader.next();
			section = courseReader.next();
			creditHours = courseReader.nextInt();
			
			
			if (courseReader.hasNextInt()) {
				instructor = null;
				enrollmentCap = courseReader.nextInt();
				meetingDays = courseReader.next();
			}
			else {
				instructor = courseReader.next();				
				enrollmentCap = courseReader.nextInt();
				meetingDays = courseReader.next();
			}
			
			
			if("A".equals(meetingDays)) {
				if(courseReader.hasNext()) {
					courseReader.close();
					throw new IllegalArgumentException("Invalid course.");
				}
				else {
					newCourse = new Course(name, title, section, creditHours, null, enrollmentCap, meetingDays);				
				}
			}
			else {
				int startTime  = courseReader.nextInt();
				int endTime = courseReader.nextInt(); 
				if(courseReader.hasNext()) {
					courseReader.close();
					throw new IllegalArgumentException("Invalid course.");
				}
				newCourse = new Course(name, title, section, creditHours, null, enrollmentCap, meetingDays, startTime, endTime);
			}
			FacultyDirectory facultyDirectory = RegistrationManager.getInstance().getFacultyDirectory();
			tempFaculty =  facultyDirectory.getFacultyById(instructor);

			if (tempFaculty != null) {
				tempFaculty.getSchedule().addCourseToSchedule(newCourse);
			}
			
				
//			//	If the id is not null, check if the instructor is in the directory
//			if (newCourse.getInstructorId() != null) {
//				String id = newCourse.getInstructorId();
//				Boolean inDirectory = false;
//				RegistrationManager manager = RegistrationManager.getInstance();
//				
//				String[][] directory = manager.getFacultyDirectory().getFacultyDirectory();
//				
//				for (int i = 0; i < directory.length; i++) {
//					if (directory[i][3].equals(id)) {
//						inDirectory = true;
//					}
//				}
//				
//				if (inDirectory) {
//					Faculty faculty = manager.getFacultyDirectory().getFacultyById(id);
//					
//					FacultySchedule schd = faculty.getSchedule();
//					schd.addCourseToSchedule(newCourse);
//				}
//			}
			
			
			courseReader.close();
			return newCourse;
		}
		catch(NoSuchElementException exception){
			throw new IllegalArgumentException("Invalid course.");
		}
	}
    
    /**
	 * Prints the given list of courses to an output file
	 * @param fileName file to write schedule of activities on
	 * @param course list of courses to write
	 * @throws IOException if cannot write to file
	 */
	public static void writeCourseRecords(String fileName, SortedList<Course> course) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
	
		for (int i = 0; i < course.size(); i++) {
		    fileWriter.println(course.get(i).toString());
		}
	
		fileWriter.close();
	    
	}

}
