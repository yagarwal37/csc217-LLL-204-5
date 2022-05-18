package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

/**
 * This is the User Class. It incorporates each user's essential identity details,
 * being the abstract parent class. 
 * Details include their first name, last name, id, email, and password
 * 
 * @author Sarah Heckman
 * 
 */
public abstract class User {

	/** student's first name */
	private String firstName;
	/** student's last name */
	private String lastName;
	/** student's id */
	private String id;
	/** student's email */
	private String email;
	/** student's password */
	private String password;

	/**
	 * Constructor for user class, consisting of the local fields 
	 * @param firstName student's first name
	 * @param lastName student's last name
	 * @param id student's id
	 * @param email student's email
	 * @param password student's password
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(password);
	}

	/**
	 * Returns a student's first name.
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Checks if a student's first name is null or an empty string then sets it to first name. 
	 * @param firstName the firstName to set
	 * @throws IllegalArgumentException if first name is null or an empty string
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || "".equals(firstName)) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Returns a students last name. 
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Checks if a student's last name is null or an empty string then sets it to last name. 
	 * @param lastName the lastName to set
	 * @throws IllegalArgumentException if last name is null or an empty string.
	 */
	public void setLastName(String lastName) {
		if (lastName == null || "".equals(lastName)) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * Returns a student's id.
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Checks if a student's id is null or an empty string then sets it to id. 
	 * @param id the id to set
	 * @throws IllegalArgumentException if if is null or an empty string.
	 */
	protected void setId(String id) {
		if (id == null || "".equals(id)) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	/**
	 * Returns a student's email
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Checks if a student's email is null, an empty string, makes sure it has an @, a period, and makes sure the last period
	 * is not after the last @ character, then sets it to email. 
	 * @param email the email to set
	 * @throws IllegalArgumentException if email is null, an empty string, does not have a period, @, or if the last period comes before 
	 * the first @ character
	 */
	public void setEmail(String email) {
		if (email == null || "".equals(email)) {
			throw new IllegalArgumentException("Invalid email");
		}
		boolean atExists = false;
		boolean periodExists = false;
		for (int i = 0; i < email.length(); i++) {
			if(email.charAt(i) == '@') {
				atExists = true;
			} 
		}
		if (!atExists) {
			throw new IllegalArgumentException("Invalid email");
		}
		for (int i = 0; i < email.length(); i++) {
			if(email.charAt(i) == '.') {
				periodExists = true;
			} 
		}
		if (!periodExists) {
			throw new IllegalArgumentException("Invalid email");
		}
		if(email.lastIndexOf('.') < email.indexOf('@')) {
			throw new IllegalArgumentException("Invalid email");
		}
		
		this.email = email;
	}

	/**
	 * returns a student's password. 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Checks if a student's password is null or an empty string then sets it to password. 
	 * @param password the password to set
	 * @throws IllegalArgumentException if password is null or an empty string
	 */
	public void setPassword(String password) {
		if(password == null || "".equals(password)) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = password;
	}

	/**
	 * Overriding the hashCode method of the User class 
	 */
	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, id, lastName, password);
	}

	/**
	 * Overriding the equals method of the User class 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password);
	}
	
	
}