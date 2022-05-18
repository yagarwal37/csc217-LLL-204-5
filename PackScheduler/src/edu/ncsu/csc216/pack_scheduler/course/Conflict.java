/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Used to ensure no conflicting activities occur
 * @author tadeotheis
 *
 */
public interface Conflict {
	/**
	 * Takes in an activity and checks if it occurs at the same time on the same day as other activities on the schedule
	 * if it does a ConflictException is thrown
	 * 
	 * @param activity activity to be checked
	 * @throws ConflictException when the checked activity conflicts with one already in the schedule
	 */
	void checkConflict(Activity activity) throws ConflictException;

}
