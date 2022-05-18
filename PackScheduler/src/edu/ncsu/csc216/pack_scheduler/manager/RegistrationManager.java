package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Manages the PackScheduler program by taking requests from StudentDirectoryPanel and
 * deciding how to implement them using the other classes in the program.
 * Implements the Singleton design pattern so that only one instance of this object
 * can be created.
 * 
 * @author Sarah Heckman (Believed creator of given code)
 * @author John Nolan
 * @author Hunter Pruitt
 * @author Caleb Twigg
 * 
 */ 
public class RegistrationManager {
	/**
	 * Instance of the RegistrationManager object,
	 * represents the class so that it cannot be recreated 
	 * as per the Singleton design pattern.
	 */
	private static RegistrationManager instance;
	
	/** Collection of faculty members associated with the PackScheduler program */
	private FacultyDirectory facultyDirectory;
	
	/** Sorted list object holding Courses */
	private CourseCatalog courseCatalog;

	/** Sorted list object containing Students in the system */
	private StudentDirectory studentDirectory;
	
	/** University records manager user */
	private User registrar;

	/** Person currently running the PackScheduler program */
	private User currentUser;

	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	/** Name of file holding registrar information */
	private static final String PROP_FILE = "registrar.properties";

	/**
	 * Initializes the necessary objects for the RegistrationManager to run:
	 * studentDirectory, courseCatalog, and registrar.
	 */
	private RegistrationManager() {
		studentDirectory = new StudentDirectory();
		courseCatalog = new CourseCatalog();
		createRegistrar();
		facultyDirectory = new FacultyDirectory();
	}
	
	/**
	 * Outlines the User subclass, Registrar, a university official who
	 * oversees and manages university records.
	 * 
	 * @author Sarah Heckman (on behalf of the NCSU CSC lab program)
	 *
	 */
	private static class Registrar extends User {
		/**
		 * Creates a registrar user.
		 * @param firstName the official registrar's first name
		 * @param lastName the official registrar's last name
		 * @param id the official registrar's unity id
		 * @param email the official registrar's university email
		 * @param hashPW the official registrar's password, hashed for security
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}
	
	/**
	 * Creates the Registrar User object to be used in the system.
	 * @throws IllegalArgumentException with the message "Cannot create registrar."
	 * if any IO exceptions occur in this process.
	 */
	private void createRegistrar() {
		Properties prop = new Properties();
		
		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);
			
			String hashPW = hashPW(prop.getProperty("pw"));
			
			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"), prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}
	
	/**
	 * Hashes a User's password for security reasons using a specific hashing algorithm.
	 * @param pw the password in its untouched state
	 * @return the password transformed by the hashing algorithm.
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}
	
	/**
	 * Returns the used instance of the RegistrationManager object.
	 * There is only one as per the Singleton pattern of design.
	 * @return the instance of the RegistrationManager
	 */
	public static RegistrationManager getInstance() {
		  if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}
	
	/**
	 * Returns the catalog of courses available to be used in the PackScheduler program.
	 * @return courseCatalog object associated with this class
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	/**
	 * Returns the directory of student users in the PackScheduler program.
	 * @return studentDirectory object associated with this class
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}
	
	/**
	 * Returns the directory of faculty users in the PackScheduler program.
	 * @return facultyDirectory object associated with this class
	 */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}

	/**
	 * Allows a Registrar, Student or Faculty user to login to the system, 
	 * in that priority order, and provided they have the correct login information.
	 * 
	 * Because of the priority order, if a student and faculty share the same id, 
	 * the student is logged in by default.
	 * 
	 * @param id unity id associated with a user in the system
	 * @param password unhashed login password to be hashed and compared with the expected password
	 * @return boolean indicator as to if the login was successful
	 */
	public boolean login(String id, String password) {
		String localHashPW = hashPW(password);

		//If there is currently someone logged in:
		if (this.getCurrentUser() != null) {
			return false;
		}
		
		//Check if registrar user
		if (registrar.getId().equals(id)) {
			if (registrar.getPassword().equals(localHashPW)) {
				//Login the registrar
				currentUser = registrar;
				return true;
			} else {
				return false;
			}
		}
		
		
		// See if the user is in directory fields
		Student s = studentDirectory.getStudentById(id);
		Faculty f = facultyDirectory.getFacultyById(id);
		
		//Does user exist?
		if (s == null && f == null) {
			throw new IllegalArgumentException("User doesn't exist.");
		} else if (s != null) {
			//Check if student
			if (s.getPassword().equals(localHashPW)) {
				currentUser = s;
				return true;
			}
		} else {
			//Check if faculty member
			if (f.getPassword().equals(localHashPW)) {
				currentUser = f;
				return true;
			}
		}
		
		//By this point, the login has failed
		return false;

	}

	/**
	 * Sets the current user to the null, therefore removing
	 * any other user from the currentUser field.
	 */
	public void logout() {
		currentUser = null; 
	}
	
	/**
	 * Provides the caller with a reference to the User currently
	 * logged in to the PackScheduler system.
	 * @return the User currently using the system
	 */
	public User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * Resets the Course catalog and Student directory objects used in the program,
	 * replacing the references to newly constructed empty objects.
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		facultyDirectory.newFacultyDirectory();
	}
	
	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * @param c Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        CourseRoll roll = c.getCourseRoll();
	        
	        if (s.canAdd(c) && roll.canEnroll(s)) {
	            schedule.addCourseToSchedule(c);
	            roll.enroll(s);
	            return true;
	        }
	        
	    } catch (IllegalArgumentException e) {
	        return false;
	    }
	    return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * @param c Course to drop
	 * @return true if dropped
	 */
	public boolean dropStudentFromCourse(Course c) {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        c.getCourseRoll().drop(s);
	        return s.getSchedule().removeCourseFromSchedule(c);
	    } catch (IllegalArgumentException e) {
	        return false; 
	    }
	}

	/**
	 * Resets the logged in student's schedule by dropping them
	 * from every course and then resetting the schedule.
	 */
	public void resetSchedule() {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        String [][] scheduleArray = schedule.getScheduledCourses();
	        for (int i = 0; i < scheduleArray.length; i++) {
	            Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
	            c.getCourseRoll().drop(s);
	        }
	        schedule.resetSchedule();
	    } catch (IllegalArgumentException e) {
	        //do nothing 
	    }
	}
	
	/**
	 * Adds a course to the faculty objects schedule
	 * @param course the course to be added
	 * @param faculty the faculty object that will have the object added to it
	 * @return true if the faculty is added to the course
	 * @throws IllegalArgumentException if the current user is not a registrar
	 */
	public boolean addFacultyToCourse(Course course, Faculty faculty) {
		
		if (registrar.equals(currentUser)) {
			
			return faculty.getSchedule().addCourseToSchedule(course);
			
		}
		
		else {
			throw new IllegalArgumentException();
		}
		
	}
	
	/**
	 * Removes a specific course from the faculty objects schedule
	 * @param course the course to be removed
	 * @param faculty the faculty object with the course in their schedule
	 * @return true if the faculty is removed from the course
	 * @throws IllegalArgumentException if the current user is not a registrar
	 */
	public boolean removeFacultyFromCourse(Course course, Faculty faculty) {
		
		if (registrar.equals(currentUser)) {
			
			return faculty.getSchedule().removeCourseFromSchedule(course);
		}
		
		else {
			throw new IllegalArgumentException();
		}
		
	}
	
	/**
	 * Resets a faculty objects schedule
	 * @param faculty the faculty whose schedule will be reset
	 * @throws IllegalArgumentException if the current user is not a registrar
	 */
	public void resetFacultySchedule(Faculty faculty) {
	
		if (registrar.equals(currentUser)) {
			
			faculty.getSchedule().resetSchedule();
			
		}
		else {
			throw new IllegalArgumentException();
		}
		
	}
}