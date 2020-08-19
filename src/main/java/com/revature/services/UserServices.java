package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.daos.UserDAO;
import com.revature.models.Update;
import com.revature.models.User;

public class UserServices {
	private static final Logger log = LogManager.getLogger(UserServices.class);
	private static UserDAO udao = new UserDAO();
	private User u;
	
	public static boolean register(User user) {
		return udao.addUser(user, 0);
	}
	
	public static boolean isInteger(String input) {
		try { 
			Integer.parseInt(input);
			return true;
		} catch(NumberFormatException e) { 
			log.debug("Input is not an integer.");
			return false; 
		} catch(NullPointerException e) {
			log.debug("Input is blank.");
			return false;
		}
	}
	
	public static int stringToInt(String input) {
		if(isInteger(input)) {
			return Integer.parseInt(input);
		} else {
			return -1;
		}
	}
	
	public User returnUserByName(String userName) {
		u = udao.findByUserName(userName);
		return u;
	}
	
	public boolean isApproved(User u) {
		String status = u.getUserStatus();
		if(status.equals("Approved")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isDeleted(User u) {
		String status = u.getUserStatus();
		if(status.equals("Approved")) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean login(String password) {
		if(u.getPassword().equals(password)) {
			log.info("Successfully logged in.");
			return true;
		} else {
			log.info("Invalid password.");
			return false;
		}
	}
	
	public void logOut() {
		u = null;
	}
	
	public List<User> findAll() {
		return udao.findAll();
	}
	
	public String[][] convertToUserListArray(List<User> list) {
		String[][] array = new String[list.size()][9];
		for(int i = 0;  i < list.size(); i++) {
			array[i][0] = Integer.toString(list.get(i).getUserID());
			array[i][1] = list.get(i).getUserName();
			array[i][2] = list.get(i).getPassword();
			array[i][3] = typeToString(list.get(i).getUserType());
			array[i][4] = list.get(i).getUserStatus();
			array[i][5] = list.get(i).getFirstName();
			array[i][6] = list.get(i).getLastName();
			array[i][7] = list.get(i).getUpdate().getUpdateTime().toString();
			array[i][8] = Integer.toString(list.get(i).getUpdate().getUpdater());
		}
		return array;
	}
	
	public String[] getColumnNames() {
		String[] array = new String [9];
		array[0] = "User ID";
		array[1] = "Username";
		array[2] = "Password";
		array[3] = "User Type";
		array[4] = "User Status";
		array[5] = "First Name";
		array[6] = "Last Name";
		array[7] = "Last Update Time";
		array[8] = "Last Updater";
		return array;
	}
	
	public String typeToString(int userType) {
		if(userType == 0) {
			return "Admin";
		} else if(userType == 1) {
			return "Employee";
		} else {
			return "Customer";
		}
	}
	
	public User findByID(String userID) {
		if(isInteger(userID)) {
			log.info("Returning found user.");
			return udao.findByUserID(Integer.parseInt(userID));
		} else {
			log.warn("No such user found.");
			return null;
		}
	}
	
	public List<User> findPending() {
		return udao.findByStatus("Pending");
	}
	
	public boolean changePassword(User user, String newPassword, int updatingUserID) {
		return udao.changePassword(user.getUserID(), newPassword, updatingUserID);
	}
	
	public boolean changeStatus(int userID, String status, int updatingUserID) {
		return udao.changeStatus(userID, status, updatingUserID);
	}
	
	public boolean changeType(int userID, int userType, int updatingUserID) {
		return udao.changeType(userID, userType, updatingUserID);
	}
	
	public Update getUpdate(int userID) {
		return udao.getUpdate(userID);
	}
}