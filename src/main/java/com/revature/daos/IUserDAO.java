package com.revature.daos;

import java.util.List;

import com.revature.models.Update;
import com.revature.models.User;

public interface IUserDAO {
	public List<User> findAll();
	public List<User> findByUserType(int userType);
	public User findByUserID(int userID);
	public User findByUserName(String userName);
	public List<User> findByStatus(String status);
	public Update getUpdate(int userID);
	public boolean addUser(User u, int updatingUserID); 
	public boolean changeStatus(int userID, String status, int updatingUserID);
	public boolean changePassword(int userID, String password, int updatingUserID);
	public boolean changeType(int userID, int userType, int updatingUserID);
}
