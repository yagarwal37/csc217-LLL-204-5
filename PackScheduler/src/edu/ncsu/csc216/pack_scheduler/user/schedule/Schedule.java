package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * This class is a scheduler for the courses selected by a student
 * 
 * @author tanmaysoni
 * @author johnnolan
 * @author hunterpruitt
 *
 */
public class Schedule {
	
	/**An ArrayList of Course*/
	private ArrayList<Course> schedule;
	/**The schedule's title*/
	private String title;
	/** Variable that a new schedule or reset schedule's title is set to */
	private static final String DEFAULT_TITLE = "My Schedule";
	/**
	 * This constructor creates the Schedule object that holds the courses
	 */
	public Schedule() {
		this.title = DEFAULT_TITLE;
		this.schedule = new ArrayList<Course>();
	}
	
	/**
	 * Is used to access title
	 * @return title
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Is used to modify the title
	 * @param title is the schedule's title
	 * @throws IllegalArgumentException is thrown if title is null
	 */
	public void setTitle(String title) {
		if(title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = title;
	}
	
	/** 
	 * addCourseToSchedule method adds the course to schedule
	 * 
	 * @param newCourse is the new course added to the schedule
	 * @return results
	 * @throws IllegalArgumentException if the course is equal to 
	 * an already added one or if there is a conflict
	 * @throws NullPointerException if newCourse is null
	 */
	public boolean addCourseToSchedule(Course newCourse) {
		//Check for issues
		for (Course c : schedule) {
			//Check if duplicate
			if (newCourse.isDuplicate(c)) {
				throw new IllegalArgumentException("You are already enrolled in " + newCourse.getName());
			}
			//Check for conflicts
			try {
				newCourse.checkConflict(c);
			} catch (ConflictException e) {
				throw new IllegalArgumentException("The course cannot be added due to a conflict.");				
			}
		}
		
		boolean results = schedule.add(newCourse);
		return results;
		
	}

	/**
	 * removeCourseFromSchedule removes a course from schedule
	 * 
	 * @param removeCourse the course being removed
	 * @return the course being removed
	 */
	public boolean removeCourseFromSchedule(Course removeCourse) {
		return schedule.remove(removeCourse);
	}
	
	/**
	 * resestSchedule resets the schedule to a new empty schedule with the defualt title
	 * 
	 */
	public void resetSchedule() {
		this.title = DEFAULT_TITLE;
		this.schedule = new ArrayList<Course>();
	}
	
	/**
	 * getScheduledCourse is a 2D array of schedule
	 * 
	 * @return course2DArray is the 2D array of schedule
	 */
	public String[][] getScheduledCourses() {
		String[][] course2DArray = new String[schedule.size()][4];
		for (int i = 0; i < schedule.size(); i++) {
			Course currentCourse = schedule.get(i);
			course2DArray[i] = currentCourse.getShortDisplayArray();
		}
		return course2DArray;
	}
	
	/**
	 * Returns the sum of credits in the entire schedule by adding each course's credits.
	 * @return The total number of credits in the schedule.
	 */
	public int getScheduleCredits() {
		int sumCredits = 0;
		for(int i = 0; i < schedule.size(); i++) {
			
			sumCredits += schedule.get(i).getCredits();
			
		}
		return sumCredits;
	}
	
	/**
	 * Method returns a boolean for whether the provided course is capable of being added to the schedule.
	 * Will return false if the course is a duplicate or conflicts with any other course's time.
	 * @param courseToAdd The course that will be checked if it can be added.
	 * @return True if it can be added false if it conflicts or is a duplicate
	 */
	public boolean canAdd(Course courseToAdd) {
		
		if(courseToAdd == null) {
			return false;
		}
		
		//Check for issues
		for (Course c : schedule) {
			//Check if duplicate
			if (courseToAdd.isDuplicate(c)) {
			return false;				
			}
			
			try {
				courseToAdd.checkConflict(c);
			} catch (ConflictException e){
				return false;
			}
		}
		
		return true;
			
	}

}
