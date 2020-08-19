package com.revature.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class User {
	private static final Logger log = LogManager.getLogger(User.class);
	private int userID;
	private String userName;
	private String password;
	private int userType;
	private String userStatus;
	private String firstName;
	private String lastName;
	private Update update;

	public User() {
		super();
		log.debug("Blank User object created.");
	}
	
	public User(String userName, String password, int userType, String userStatus, String firstName, String lastName) {
		super();
		this.userName = userName;
		this.password = password;
		this.userType = userType;
		this.userStatus = userStatus;
		this.firstName = firstName;
		this.lastName = lastName;
		log.debug("New User object created.");
	}
	
	public User(int userID, String userName, String password, int userType, String userStatus, String firstName, String lastName, Update update) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		this.userType = userType;
		this.userStatus = userStatus;
		this.firstName = firstName;
		this.lastName = lastName;
		this.update = update;
		log.debug("Full User object created.");
	}

	public int getUserID() {
		log.debug("userID returned.");
		return userID;
	}
	
	public void setUserID(int userID) {
		log.debug("userID set.");
		this.userID = userID;
	}
	
	public String getUserName() {
		log.debug("userName returned.");
		return userName;
	}
	
	public void setUserName(String userName) {
		log.debug("userName set.");
		this.userName = userName;
	}
	
	public String getPassword() {
		log.debug("password returned.");
		return password;
	}
	
	public void setPassword(String password) {
		log.debug("password set.");
		this.password = password;
	}
	
	public int getUserType() {
		log.debug("userType returned.");
		return userType;
	}

	public void setUserType(int userType) {
		log.debug("userType set.");
		this.userType = userType;
	}
	
	public String getUserStatus() {
		log.debug("userStatus returned.");
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		log.debug("userStatus set.");
		this.userStatus = userStatus;
	}
	
	public String getFirstName() {
		log.debug("firstName returned.");
		return firstName;
	}

	public void setFirstName(String firstName) {
		log.debug("firstName set.");
		this.firstName = firstName;
	}

	public String getLastName() {
		log.debug("lastName returned.");
		return lastName;
	}

	public void setLastName(String lastName) {
		log.debug("lastName set.");
		this.lastName = lastName;
	}
	
	public Update getUpdate() {
		log.debug("update returned.");
		return update;
	}

	public void setUpdate(Update update) {
		log.debug("update set.");
		this.update = update;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", userName=" + userName + ", password=" + password + ", userType=" + userType
				+ ", userStatus=" + userStatus + ", firstName=" + firstName + ", lastName=" + lastName + ", "
				+ update + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((update == null) ? 0 : update.hashCode());
		result = prime * result + userID;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((userStatus == null) ? 0 : userStatus.hashCode());
		result = prime * result + userType;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			log.debug("These are the same Users.");
			return true;
		}
		if (obj == null) {
			log.debug("UserB is a null User.");
			return false;
		}
		if (getClass() != obj.getClass()) {
			log.debug("One of these isn't a User!");
			return false;
		}
		User other = (User) obj;
		if (firstName == null) {
			log.debug("UserA has a null firstName.");
			if (other.firstName != null) {
				log.debug("... but UserB doesn't.");
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			log.debug("These Users have different firstNames.");
			return false;
		}
		if (lastName == null) {
			log.debug("UserA has a null lastName.");
			if (other.lastName != null) {
				log.debug("... but UserB doesn't.");
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			log.debug("These Users have different lastNames.");
			return false;
		}
		if (password == null) {
			log.debug("UserA has a null password.");
			if (other.password != null) {
				log.debug("... but UserB doesn't.");
				return false;
			}
		} else if (!password.equals(other.password)) {
			log.debug("These Users have different passwords.");
			return false;
		}
		if (update == null) {
			log.debug("UserA has a null update.");
			if (other.update != null) {
				log.debug("... but UserB doesn't.");
				return false;
			}
		} else if (!update.equals(other.update)) {
			log.debug("These Users have different updates.");
			return false;
		}
		if (userID != other.userID) {
			log.debug("These Users have different userIDs.");
			return false;
		}
		if (userName == null) {
			log.debug("UserA has a null userName.");
			if (other.userName != null) {
				log.debug("... but UserB doesn't.");
				return false;
			}
		} else if (!userName.equals(other.userName)) {
			log.debug("These Users have different userNames.");
			return false;
		}
		if (userStatus == null) {
			log.debug("UserA has a null userStatus.");
			if (other.userStatus != null) {
				log.debug("... but UserB doesn't.");
				return false;
			}
		} else if (!userStatus.equals(other.userStatus)) {
			log.debug("These Users have different userStatuses.");
			return false;
		}
		if (userType != other.userType) {
			log.debug("These Users have different userTypes.");
			return false;
		}
		log.debug("These Users are the same!");
		return true;
	}
}