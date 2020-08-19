package com.revature.daos;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.revature.models.Update;
import com.revature.models.User;

public class UserDAOTest {
	public static UserDAO udao = new UserDAO();
	public static List<User> list;
	public static User u;
	
	@Test
	public void testFindAll() {
		list = udao.findAll();
		for(User u : list) {
			System.out.println(u.toString());
		}
	}
	
	@Test
	public void testFindByUserType() {
		list = udao.findByUserType(1);
		for(User u : list) {
			System.out.println(u.toString());
		}
	}
	
	@Test
	public void testFindByUserID() {
		u = udao.findByUserID(0);
		System.out.println(u);
	}

	@Test
	public void testFindByUserName() {
		u = udao.findByUserName("moderator");
		System.out.println(u);
	}

	@Test
	public void testFindByStatus() {
		list = udao.findByStatus("Approved");
		for(User u : list) {
			System.out.println(u.toString());
		}
	}

	@Test
	public void testGetUpdate() {
		Update u = udao.getUpdate(2);
		System.out.println(u.toString());
	}

	@Test
	public void testAddUser() {
		u = new User("moderator", "password", 1, "Approved", "Mary", "Jane");
		assertTrue(udao.addUser(u, 0));
	}

	@Test
	public void testChangeStatus() {
		assertTrue(udao.changeStatus(2, "The New Boss", 1));
	}

	@Test
	public void testChangePassword() {
		assertTrue(udao.changePassword(1, "BigMoney!", 1));
	}
}