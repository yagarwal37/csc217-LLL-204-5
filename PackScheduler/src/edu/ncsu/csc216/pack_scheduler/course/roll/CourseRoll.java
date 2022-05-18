package edu.ncsu.csc216.pack_scheduler.course.roll;

import java.util.NoSuchElementException;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.ArrayQueue;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;

/**
 * Class defining the course roll collection of students for each course 
 * in the PackScheduler program.
 * 
 * @author Hunter Pruitt
 * @author jrnolan2
 * @author tsoni2
 */
public class CourseRoll {
	
	/** Total number of seats in the course */
	private int enrollmentCap;

	/** Minimum possible number of seats in the course */
	private static final int MIN_ENROLLMENT = 10;

	/** Maximum possible number of seats in the course */
	private static final int MAX_ENROLLMENT = 250;

	/** Collection of students enrolled */
	private LinkedAbstractList<Student> roll;
	
	/** The waitlist for the course */
	private ArrayQueue<Student> waitlist;
	
	/** Course corresponding to this roll */
	private Course course;

	/**
	 * Constructor for CourseRoll object, sets the enrollment cap
	 * @param course is the course linked to the courseroll
	 * @param enrollmentCap proposed enrollment cap
	 */
	public CourseRoll(Course course, int enrollmentCap) {
		
		//	Check that the passed course is valid
		if (course == null) {
			throw new IllegalArgumentException();
		}
		this.course = course;
		
		setEnrollmentCap(enrollmentCap);
		roll = new LinkedAbstractList<Student>(enrollmentCap);
		
		//	Constructs and sets the capacity of the waitlist
		waitlist = new ArrayQueue<Student>(10);
		
	}
	
	/**
	 * Sets the total number of seats in the course
	 * @param enrollmentCap proposed number of seats
	 * @throws IllegalArgumentException if passed invalid enrollment cap
	 */
	public void setEnrollmentCap(int enrollmentCap) {
		if(enrollmentCap > MAX_ENROLLMENT || enrollmentCap < MIN_ENROLLMENT) {
			throw new IllegalArgumentException();
		}
		
		if(roll != null && enrollmentCap < roll.size()) {
			throw new IllegalArgumentException();
		}
		
		if(roll != null && enrollmentCap >= roll.size()) {
			roll.setCapacity(enrollmentCap);
		}
		this.enrollmentCap = enrollmentCap;
	
	}
	
	/**
	 * Returns the number of seats in the course
	 * @return number of seats
	 */
	public int getEnrollmentCap() {
		return this.enrollmentCap;
	}
	
	/**
	 * Returns the number of open seats in the course
	 * @return number of open seats
	 */
	public int getOpenSeats() {
		return this.enrollmentCap - roll.size();
	}
	
	/**
	 * Determines if a student can enroll in this course
	 * @param s student to enroll
	 * @return boolean indicator of enrollment ability
	 */
	public boolean canEnroll(Student s) {	
		
		boolean canEnroll;
		
		boolean onWaitlist = false;
		boolean inCourseRoll = false;
		
		for(int i = 0; i < roll.size(); i++) {
			
			Student courseStudent = roll.get(i);
			
			//	If the student is already in the course
			if(s.equals(courseStudent)) {
				inCourseRoll = true;
			}
			
			try {
					Student waitlisted = waitlist.dequeue();
			
					//	Check if the student is on the waitlist
					if (s.equals(waitlisted)) {
						onWaitlist = true;
						waitlist.enqueue(waitlisted);
					}
					else {
						waitlist.enqueue(waitlisted);
					}
			}
			catch(NoSuchElementException e) {
				//	Do nothing as there is not a student to pull from the waitlist
			}

		}
		
		//	If the student is already in the course or on the waitlist
		if (onWaitlist || inCourseRoll) {
			canEnroll = false;
		}
		
		//	Check that there are open seats in the Course or Waitlist
		else if (getOpenSeats() > 0 || waitlist.size() < 10){
			canEnroll = true;
		}
		
		else {
			canEnroll = false;
		}
		
		return canEnroll;
	}
	
	/**
	 * Removes a student from the course roll
	 * @param s student to remove
	 * @throws IllegalArgumentException if the student is null
	 */
	public void drop(Student s) {
		

		if(s == null) {
			throw new IllegalArgumentException();
		}
		
		//	Remove the student from the roll if they are in the roll
		for(int i = 0; i < roll.size(); i++) {
			
			if(s.equals(roll.get(i))) {
				roll.remove(i);
				
				try {
					//	Adds the first student from the queue
					Student newStudent = waitlist.dequeue();
					enroll(newStudent);
					newStudent.getSchedule().addCourseToSchedule(course);
				}
				catch(NoSuchElementException e) {
					//	Do nothing, as we have no waitlist
				}
			}
		}
		
		//	If the student is on the waitlist, remove them
		for (int i = 0; i < waitlist.size(); i++) {
			
			Student waitlisted = waitlist.dequeue();
			
			if(!s.equals(waitlisted)) {
				//	Add the student back to the queue
				waitlist.enqueue(waitlisted);
			}
			
			//	If it does equal the student, do nothing, b/c it will be removed
			
		}
	}

	/**
	 * Enrolls a student parameter into the course
	 * @param s student to enroll
	 * @throws IllegalArgumentException if the student cannot enroll or is null
	 */
	public void enroll(Student s) {
		
		if(s == null) {
			throw new IllegalArgumentException();
		} 
		
		else if(canEnroll(s) && getOpenSeats() > 0) {
			roll.add(s);
		}
		
		else if(canEnroll(s) && getOpenSeats() == 0) {
			
			if (waitlist.size() < 10) {
				waitlist.enqueue(s);
			}
			else {
				throw new IllegalArgumentException();
			}
		}
		
		else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Returns the number of students on the waitlist
	 * @return the size of the waitlist Queue
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}
}
