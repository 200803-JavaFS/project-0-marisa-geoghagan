package com.revature.models;

public class User {
	private static int userNum = 0;
	private int userID;
	private String userName;
	private String password;
	
	public User() {
		super();
	}
	
	public User(String userName, String password) {
		super();
		userID = userNum;
		userNum++;
		this.userName = userName;
		this.password = password;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
