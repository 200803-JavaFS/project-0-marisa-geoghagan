package com.revature.daos;

import static org.junit.Assert.assertTrue;

import java.util.List;

import com.revature.models.Update;
import com.revature.models.User;

public class UserDAOTest {
	public static UserDAO udao = new UserDAO();
	public static List<User> list;
	public static User u;
	
	public void testFindAll() {
		list = udao.findAll();
		for(User u : list) {
			System.out.println(u.toString());
		}
	}
	
	public void testFindByUserType() {
		list = udao.findByUserType(0);
		for(User u : list) {
			System.out.println(u.toString());
		}
	}
	
	public void testFindByUserID() {
		u = udao.findByUserID(0);
		System.out.println(u);
	}
	
	public void testFindByUserName() {
		u = udao.findByUserName("admin");
		System.out.println(u);
	}
	
	public void testFindByStatus() {
		list = udao.findByStatus("Approved");
		for(User u : list) {
			System.out.println(u.toString());
		}
	}
	
	public void testGetUpdate() {
		Update u = udao.getUpdate(0);
		System.out.println(u.toString());
	}
	
	public void testAddUser() {
		u = new User("admin", "password", 0, "Approved");
		assertTrue(udao.addUser(u, 1));
	}
	
	public void testChangeStatus() {
		assertTrue(udao.changeStatus(0, "The Boss", 0));
	}
	
	public void testChangePassword() {
		assertTrue(udao.changePassword(0, "BigMoney!"));
	}
}