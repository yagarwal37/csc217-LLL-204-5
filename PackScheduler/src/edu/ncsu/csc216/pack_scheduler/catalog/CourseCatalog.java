package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;

import edu.ncsu.csc217.collections.list.SortedList;

/**
 * This class uses the Course class and the CourseRecordIO class to create
 * and edit course catalogs and access their information
 * 
 * @author tadeotheis
 *
 */
public class CourseCatalog {

		/** Sorted list of courses. */
		private SortedList<Course> catalog; 
		
		/**
		 * Constructor for CourseCatalog
		 */
		public CourseCatalog() {
			catalog = new SortedList<Course>(); 
		}
		
		/**
		 * Creates a new empty sorted list of courses
		 */
		public void newCourseCatalog() {
			catalog = new SortedList<Course>();
		}
		
		/**
		 * Loads courses into the course catalog from an input file
		 * 
		 * @param fileName name of the file with courses. 
		 */
		public void loadCoursesFromFile(String fileName) {
			try {
				catalog = CourseRecordIO.readCourseRecords(fileName);
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException("Unable to read file " + fileName);
			}
		}
		
		/**
		 * Loops through the course catalog and checks if the given course is a duplicate of 
		 * an existing course in the catalog. If it is not in the  catalog it adds the course and 
		 * returns true, returns false otherwise. 
		 * 
		 * @param name name of the course
		 * @param title title of the course
		 * @param section section of the course
		 * @param credits credits of the course
		 * @param instructorId courses instructor ID
		 * @param enrollmentCap the max number of students allowed to enroll
		 * @param meetingDays courses meeting days
		 * @param startTime courses start time
		 * @param endTime courses end time 
		 * @return true if the course was added to catalog, false otherwise. 
		 */
		public boolean addCourseToCatalog(String name, String title, String section, int credits,
				String instructorId, int enrollmentCap, String meetingDays, int startTime, int endTime) {
			Course temp = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime);
			for(int i = 0; i < catalog.size(); i++) {
				if(catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
					return false;
				}
			}
			catalog.add(temp);
			return true;
		}
		
		/**
		 * Loops through the course catalog and removes a course if it exists in the catalog. 
		 * Returns true if the course is found and removed, false otherwise. 
		 * @param name name of the course
		 * @param section section of the course
		 * @return true if the course is found and removed, false otherwise.
		 */
		public boolean removeCourseFromCatalog(String name, String section) {
			for(int i = 0; i < catalog.size(); i++) {
				if(catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
					catalog.remove(i);
					return true;
				}
			}
			return false;
		}
		
		/**
		 * Returns the Course from the catalog with the given name and section. Returns null if the Course isn’t in the catalog.
		 * 
		 * @param name name of the course
		 * @param section name of the section
		 * @return course in the catalog if it exists in the catalog, null of the course does not exist. 
		 */
		public Course getCourseFromCatalog(String name, String section) {
			for(int i = 0; i < catalog.size(); i++) {
				if(catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
					return catalog.get(i);
				}
			}
			return null;
		}
		
		/**
		 * Returns the name, section, title, and meeting information for all courses in the catalog.
		 * 
		 * @return 2D array of course information the name, section, title, and meeting information
		 */
		public String[][] getCourseCatalog() {
			String[][] catalogTable = new String[0][0];
			if(catalog.size() < 1) {
				return catalogTable;
			}
			else {
				catalogTable = new String [catalog.size()][4];
				for(int i = 0; i < catalog.size(); i++) {
					Course c = catalog.get(i);
		            catalogTable[i] = c.getShortDisplayArray();
				}
				return catalogTable;
			}
		}
		
		/**
		 * Writes the course information from the catalog into a file
		 * 
		 * @param fileName name of the file to export to
		 */
		public void saveCourseCatalog(String fileName) {
			try {
				CourseRecordIO.writeCourseRecords(fileName, catalog);
			}
			catch(IOException e) {
				throw new IllegalArgumentException("The file cannot be saved.");
			}
		}
}
