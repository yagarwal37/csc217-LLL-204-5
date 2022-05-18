package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidatorFSM;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * This class creates objects of type Course with parameters to describe/distinguish different courses.
 * @author tadeotheis
 *
 */
public class Course extends Activity implements Comparable<Course> {
	/** length of the section number for a course */
	private static final int SECTION_LENGTH = 3;
	
	/** max credits for a course */
	private static final int MAX_CREDITS = 5;
	
	/** minimum credits for a course */
	private static final int MIN_CREDITS = 1;
	
	/** Course's name. */
	private String name;
	
	/** Course's section. */
	private String section;
	
	/** Course's credit hours */
	private int credits;
	
	/** Course's instructor */
	private String instructorId;
	
	/** Course name validator FSM */
	private CourseNameValidatorFSM validator = new CourseNameValidatorFSM();
	
	/** Course's enrollment */
	private CourseRoll roll;
	
	/**
	 * Constructs a Course object with values for all fields by calling the super class and setter methods. 
	 * @param name name of course
	 * @param title title of course
	 * @param section section of course
	 * @param credits credit hours for course
	 * @param instructorId Instructor's unity ID
	 * @param enrollmentCap The maximum number of students in the class
	 * @param meetingDays meeting days for course as a series of chars
	 * @param startTime start time for course
	 * @param endTime end time for course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays,
            int startTime, int endTime) {
        super(title, meetingDays, startTime, endTime);
        setName(name);
        setSection(section);
        setCredits(credits);
        setInstructorId(instructorId);
        
        roll = new CourseRoll(this, enrollmentCap);
    }
	
	/**
	 * Creates a Course with the given name, title, section, credits, instructorId, enrollmentCap, and meetingDays for 
	 * courses that are arranged by calling the larger method with 0's for times. 
	* @param name name of course
	 * @param title title of course
	 * @param section section of course
	 * @param credits credit hours for course
	 * @param instructorId Instructor's unity ID
	 * @param enrollmentCap The maximum number of students in the class
	 * @param meetingDays meeting days for course as a series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}

	/**
	 * Returns the Course's name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the Course's name.  If the name is null, has a length less than 5 or more than 8,
     * contains a space between letter characters and number characters, has less than 1
     * or more than 4 letter characters, and not exactly three trailing digit characters, an
     * IllegalArgumentException is thrown.
	 * @param name the name to set
	 * @throws IllegalArgumentException if the name parameter is invalid
	 */
	private void setName(String name) {
		try {
			if (name != null && validator.isValid(name)) {
				this.name = name;
			} else {
				//In case it fails the FSM or is null
				throw new IllegalArgumentException("Invalid course name.");
			}
		} catch (InvalidTransitionException e) {
			// Exception caught -> name is invalid
			throw new IllegalArgumentException("Invalid course name.");
		}
	}
	
	/**
	 * Returns the Course's section.
	 * @return the section
	 */
	public String getSection() {
		return section;
	}
	
	/**
	 * Sets the Course's section. If the section is null, does not have a length of 3, or have any characters
	 * other than digits throws IllegalArgumentException. 
	 * @param section the section to set
	 * @throws IllegalArgumentException if the section parameter is invalid
	 */
	public void setSection(String section) {
		if(section == null || section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}
		for(int i = 0; i < section.length(); i++) {
			if(!(Character.isDigit(section.charAt(i)))) {
				throw new IllegalArgumentException("Invalid section.");
			}
		}
		this.section = section;
	}
	
	/**
	 * Returns the Course's credits.
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}
	
	/**
	 * Sets the Course's credits. If credits is less than 1 or more than 5 an IllegalArgumentException is thrown.
	 * @param credits the credits to set
	 * @throws IllegalArgumentException if the credits parameter is invalid
	 * 
	 */
	public void setCredits(int credits) {
		if(credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}
		this.credits = credits;
	}
	
	/**
	 * Returns the Course's instructor.
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}
	
	/**
	 * Sets the Course's instructor. If instructor id is an empty string, throw an exception
	 * @param instructorId the instructorId to set
	 */
	public void setInstructorId(String instructorId) {
		if (instructorId != null && "".equals(instructorId)) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
		this.instructorId = instructorId;
	}
	
	/**
	 * Returns the collection of students in this course
	 * @return roll field for the Course
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}

	/**
	 * Overrides toString method to return a comma separated value String of all Course fields.
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if ("A".equals(getMeetingDays())) {
	        return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + getMeetingDays();
	    }
	    return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime(); 
	}

	/**
	 * Generates hash code for course fields, Overrides the original method to include course specific fields. 
	 * @return an integer hash code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Checks if two objects are the same by first checking if the given object is a course then checking if all course fields are the same
	 * @return true if two objects, courses, are the same and false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Used to populate the rows of the course catalog with course information, only limited information on 4 of the course fields.
	 * Overrides the original method to give information on all 4 fields name, section, title, and meetingString. 
	 * @return string of course information
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] s = new String[5];
		s[0] = name;
		s[1] = section;
		s[2] = getTitle();
		s[3] = getMeetingString();
		s[4] = "" + roll.getOpenSeats();
		
		return s;
	}

	/**
	 * Used to populate the rows of the course catalog with all course information for final schedule, Uses all course fields since the final
	 * schedule has information on all applicable fields. 
	 * @return string of all course information
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] s = new String[7];
		s[0] = name;
		s[1] = section;
		s[2] = getTitle();
		String c = "" + credits;
		s[3] = c;
		s[4] = instructorId;
		s[5] = getMeetingString();
		s[6] = "";
		
		return s;
	}
	
	/**
	 * Overrides the isDuplicate method to check if an activity is a course and then check if it is a duplicate of another course
	 * already on the schedule. This is done by checking if the name of the course is the same as another. 
	 * @param activity the activity to be checked 
	 * @return true if it is a duplicate, false otherwise
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		boolean duplicate;
		if (activity instanceof Course) {
			Course course = (Course)activity;
			String courseName = course.getName();
			if (this.getName().equals(courseName)) {
				duplicate = true;
			} else {
				duplicate = false;
			}
		} else {
			throw new IllegalArgumentException("Parameter not a course");
		}
		return duplicate;
	}

	/**
	 * Overrides activity's method to check for A days and to validate all days are either
	 * M, T, W, H, or F and checks for no repeating days, then calls the superclass to do the rest of the checking.
	 * @throws IllegalArgumentException if meeting days or times are invalid
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if(meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if("A".equals(meetingDays)) {
			if(startTime != 0 || endTime != 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
		}
		else {
			char[] days = {'M', 'T', 'W', 'H', 'F'};
			int[] cnt = {0, 0, 0, 0, 0};
			for(int i = 0; i < meetingDays.length(); i++) {
				int k = -1;
				for(int j = 0; j < days.length; j++) {
					if(meetingDays.charAt(i) == days[j]) {
						cnt[j]++;
						k++;
					}
					if(j == days.length - 1 && k != 0) {
						throw new IllegalArgumentException("Invalid meeting days and times.");
					}
				}
			}
			for(int w = 0; w < cnt.length; w++) {
				if(cnt[w] > 1) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}
			super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
		}
	}

	/**
	 * Takes a course c and compares it to this course. Checks ascii values for the name and then section of the courses if applicable
	 * and returns a value to show if this course comes before, after, or is equal to course c.  
	 * 
	 * @return integer value -1 if this is before, 1 if this is after, and 0 if this and c are the same
	 * @throws NullPointerException when c is null
	 */
	@Override
	public int compareTo(Course c) {
		if(c == null) {
			throw new NullPointerException();
		}
		
		//Course name
		
		if(c.getName().length() == this.getName().length()) {
			for(int i = 0; i < c.getName().length(); i++) {
				
				int ascii1 = (int) c.getName().charAt(i);
				int ascii2 = (int) this.getName().charAt(i);
				
				if (ascii1 > ascii2) {
					return -1;
				} else if (ascii2 > ascii1) {
					return 1;
				}
			}
		} else if (c.getName().length() > this.getName().length()) {
			for(int i = 0; i < this.getName().length(); i++) {
				
				int ascii1 = (int) c.getName().charAt(i);
				int ascii2 = (int) this.getName().charAt(i);
				
				if (ascii1 > ascii2) {
					return -1;
				} else if (ascii2 > ascii1) {
					return 1;
				}
			}
			return -1;
		} else if (c.getName().length() < this.getName().length()) {
			for(int i = 0; i < c.getName().length(); i++) {
				
				int ascii1 = (int) c.getName().charAt(i);
				int ascii2 = (int) this.getName().charAt(i);
				
				if (ascii1 > ascii2) {
					return -1;
				} else if (ascii2 > ascii1) {
					return 1;
				}
			
			}
			return 1;
		}
		//Course section
		if(c.getSection().length() == this.getSection().length()) {
			for(int i = 0; i < c.getSection().length(); i++) {
				
				int ascii1 = (int) c.getSection().charAt(i);
				int ascii2 = (int) this.getSection().charAt(i);
				
				if (ascii1 > ascii2) {
					return -1;
				} else if (ascii2 > ascii1) {
					return 1;
				}
			}
		} 
		return 0;
	}
}
