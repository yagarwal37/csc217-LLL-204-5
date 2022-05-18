package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Superclass for course and event classes, used for titles, meeting days, start time,
 * and end time for both types of activities
 * @author tadeotheis
 *
 */
public abstract class Activity implements Conflict {

	/** last hour of the day */
	private static final int UPPER_HOUR = 24;
	/** last minute in an hour */
	private static final int UPPER_MINUTE = 60;
	/** Activity's title. */
	private String title;
	/** Activity's meeting days */
	private String meetingDays;
	/** Activity's starting time */
	private int startTime;
	/** Activity's ending time */
	private int endTime;

	/**
	 * Constructs an activity with values for all fields.
	 * @param title title of activity
	 * @param meetingDays meeting days for the activity as a series of chars
	 * @param startTime start time for activity
	 * @param endTime end time for activity
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
        super();
        setTitle(title);
        setMeetingDaysAndTime(meetingDays, startTime, endTime);
    }
	
	/**
	 * Checks if two activities happen at a conflicting time. If the two activities have at least one day with 
	 * at least one minute conflicting they count as conflicting activities. First checks if the given activity has a day
	 * equal to this activity. If it does it then checks the start time and end time to see if they overlap with this activity. 
	 */
	@Override
	public void checkConflict(Activity activity) throws ConflictException {
		String days = this.getMeetingDays();
		int start = this.getStartTime();
		int end = this.getEndTime();
		if(!(activity.getMeetingDays().equals("A"))) {
			for(int i = 0; i < days.length(); i++) {
				if(activity.getMeetingDays().indexOf(days.charAt(i)) != -1) {
					if(activity.getStartTime() == end || activity.getStartTime() == start) {
						throw new ConflictException();
					}
					else if(activity.getEndTime() == end || activity.getEndTime() == start) {
						throw new ConflictException();
					}
					else if(activity.getEndTime() < end && activity.getEndTime() > start) {
						throw new ConflictException();
					}
					else if(activity.getStartTime() < end && activity.getStartTime() > start) {
						throw new ConflictException();
					}
					else if(activity.getStartTime() < start && activity.getEndTime() > end) {
						throw new ConflictException();
					}
				}
			}
		}
		
	}

	/**
	 * Returns the activity's title.
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the activity's title. If the title is null or an empty string an IllegalArgumentException is thrown
	 * @param title the title to set
	 * @throws IllegalArgumentException if the title parameter is invalid
	 */
	public void setTitle(String title) {
		if(title == null || "".equals(title)) {
			throw new IllegalArgumentException("Invalid title.");
		}
		if(title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid title.");
		}
		this.title = title;
	}

	/**
	 * Returns the activity's meeting days.
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Sets the activity's meeting days as well as start and end time if applicable. 
	 * If meeting days is null, empty, or contains invalid characters, an arranged class has non-zero start or end times,
	 * start time is an incorrect time, end time is an incorrect time, or end time is less than start time IAE is thrown.
	 * @param meetingDays the meetingDays to set
	 * @param startTime start time of activity
	 * @param endTime end time of activity
	 * @throws IllegalArgumentException if the meeting days or times are invalid
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if(meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
			int startHour = startTime / 100;
			int startMin = startTime % 100;
			int endHour = endTime / 100;
			int endMin = endTime % 100;
			
			if(startHour < 0 || startHour >= UPPER_HOUR) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			if(startMin < 0 || startMin >= UPPER_MINUTE) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			if(endHour < 0 || endHour >= UPPER_HOUR) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			if(endMin < 0 || endMin >= UPPER_MINUTE) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			if(startTime > endTime) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			this.meetingDays = meetingDays;
			this.startTime = startTime;
			this.endTime = endTime;
	}

	/**
	 * Returns the activity's meeting days and times as a string, or Arranged if meeting days is A.
	 * @return the activity's meeting days and times as a string, or Arranged if meeting days is A.
	 */
	public String getMeetingString() {
		String s = "";
		if("A".equals(meetingDays)) {
			s += "Arranged";
			return s;
		}
		else {
			int startHour = startTime / 100;
			int startMin = startTime % 100;
			int endHour = endTime / 100;
			int endMin = endTime % 100;
			s += meetingDays + " ";
			if(startHour > 12) {
				s += startHour - 12 + ":";
			}
			else {
				s += startHour + ":";
			}
			if(startMin < 10) {
				s += "0" + startMin;
			}
			else {
				s += startMin; 
			}
			if(startHour > 12 || startHour == 12) {
				s += "PM-";
			}
			else {
				s += "AM-";
			}
			if(endHour > 12) {
				s += endHour - 12 + ":";
			}
			else {
				s += endHour + ":";
			}
			if(endMin < 10) {
				s += "0" + endMin;
			}
			else {
				s += endMin; 
			}
			if(endHour >= 12) {
				s += "PM";
			}
			else {
				s += "AM";
			}
			return s;
		}	
	}

	/**
	 * Returns the activity's start time.
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the activity's end time.
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}
	/**
	 * Used to populate the rows of the course catalog and student schedule
	 * @return string of course and schedule information
	 */
	public abstract String[] getShortDisplayArray();
	
	/**
	 * Used to display the final schedule
	 * @return string full schedule information
	 */
	public abstract String[] getLongDisplayArray();
	
	
	/**
	 * Checks if activities are duplicates of each other or not
	 * @param activity the given activity that needs to be checked for being a duplicate or not
	 * @return true if they are, false otherwise
	 */
	public abstract boolean isDuplicate(Activity activity);
	
	/**
	 * Generates hash code for activity fields
	 * @return hash code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Checks if two objects are the same using activity fields
	 * @return true if two objects are the same, false if not
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}