/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Maintains a list of Faculty
 * 
 * @author Caleb Twigg
 *
 */
public class FacultyDirectory {

	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/** Linked List of faculty in the directory */
	private LinkedList<Faculty> facultyDirectory;

	/**
	 * Constructs the Faculty Directory
	 */
	public FacultyDirectory() {
		newFacultyDirectory();
	}

	/**
	 * Resets the Faculty Directory
	 */
	public void newFacultyDirectory() {
		facultyDirectory = new LinkedList<Faculty>();
	}

	/**
	 * Loads a Faculty Directory from a file
	 * 
	 * @param fileName the file name
	 * @throws IllegalArgumentException if the file cannot be read or found
	 */
	public void loadFacultyFromFile(String fileName) {

		try {
			facultyDirectory = FacultyRecordIO.readFacultyRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);

		}
	}

	/**
	 * Adds a Faculty object to the directory
	 * 
	 * @param firstName      the first name of the Faculty
	 * @param lastName       the last name of the Faculty
	 * @param id             the id of the Faculty
	 * @param email          the Faculty email
	 * @param password       the Faculty's password
	 * @param repeatPassword the Faculty's password (repeated)
	 * @param maxCourses     the max courses for the Faculty
	 * @return true if the Faculty object is added
	 * @throws IllegalArgumentException if the password is invalid
	 */
	public boolean addFaculty(String firstName, String lastName, String id, String email, String password,
			String repeatPassword, int maxCourses) {

		if (password == null || repeatPassword == null || "".equals(password) || "".equals(repeatPassword)) {
			throw new IllegalArgumentException("Invalid password");
		}

		String hashPW = hashString(password);
		String repeatHashPW = hashString(repeatPassword);

		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}

		Faculty faculty = new Faculty(firstName, lastName, id, email, hashPW, maxCourses);

		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty current = (Faculty) facultyDirectory.get(i);

			if (id.equals(current.getId())) {
				return false;
			}
		}

		return facultyDirectory.add(faculty);
	}

	/**
	 * Hashes a String according to the SHA-256 algorithm, and outputs the digest in
	 * base64 encoding. This allows the encoded digest to be safely copied, as it
	 * only uses [a-zA-Z0-9+/=].
	 * 
	 * @param toHash the String to hash
	 * @return the encoded digest of the hash algorithm in base64
	 * @throws IllegalArgumentException if the password cannot be hashed
	 */
	private static String hashString(String toHash) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(toHash.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	/**
	 * Removes a student with a given id from the directory of faculty
	 * 
	 * @param id the id of the faculty
	 * @return true if the faculty object is removed from the directory
	 */
	public boolean removeFaculty(String id) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty current = (Faculty) facultyDirectory.get(i);

			if (id.equals(current.getId())) {
				facultyDirectory.remove(i);
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns the faculty directory as a String array
	 * 
	 * @return directoryArray the array of Strings
	 */
	public String[][] getFacultyDirectory() {

		String[][] directoryArray = new String[facultyDirectory.size()][3];

		for (int i = 0; i < facultyDirectory.size(); i++) {
			User current = facultyDirectory.get(i);
			directoryArray[i][0] = current.getFirstName();
			directoryArray[i][1] = current.getLastName();
			directoryArray[i][2] = current.getId();
		}

		return directoryArray;
	}
 
	/**
	 * Saves the FacultyDirectory to a given file
	 * 
	 * @param fileName the name of the file
	 * @throws IllegalArgumentException if the directory cannot be saved
	 */
	public void saveFacultyDirectory(String fileName) {

		try {
			FacultyRecordIO.writeFacultyRecords(fileName, facultyDirectory);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}

	/**
	 * Returns the faculty object with the given Faculty id
	 * 
	 * @param id the given faculty id
	 * @return faculty the Faculty object with the given id
	 */
	public Faculty getFacultyById(String id) {

		Faculty faculty = null;
		boolean found = false;

		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty current = (Faculty) facultyDirectory.get(i);

			if (id.equals(current.getId()) && !found) {
				found = true;
				faculty = current;
			}
		}

		if (found) {
			return faculty;
		}

		else {
			return null;
		}
	}
}
