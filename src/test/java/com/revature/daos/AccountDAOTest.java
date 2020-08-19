package com.revature.daos;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.revature.models.Account;
import com.revature.models.Update;

public class AccountDAOTest {
	public static AccountDAO adao = new AccountDAO();
	public static List<Account> list;
	public static Account a;
	
	@Test
	public void testFindAll() {
		list = adao.findAll();
		for(Account a : list) {
			System.out.println(a.toString());
		}
	}
	
	@Test
	public void testFindByOwner() {
		list = adao.findByOwner(0);
		for(Account a : list) {
			System.out.println(a.toString());
		}
	}
	
	@Test
	public void testFindByStatus() {
		list = adao.findByStatus("Pending");
		for(Account a : list) {
			System.out.println(a.toString());
		}
	}
	
	@Test
	public void testFindByType() {
		list = adao.findByType(false);
		for(Account a : list) {
			System.out.println(a.toString());
		}
	}
	
	@Test
	public void testFindByAccountID() {
		System.out.println(adao.findByAccountID(2));
	}
	
	@Test
	public void testgetUpdate() {
		System.out.println(adao.getUpdate(1).toString());
	}
	
	@Test
	public void testAddAccount() {
		a = new Account(new int[] {1, 0}, false);
		adao.addAccount(a, 1);
	}
	
	@Test
	public void testUpdateAmount() {
		assertTrue(adao.updateAmount(2, 3.50, 1));
	}
	
	@Test
	public void testTransfer() {
		assertTrue(adao.transfer(2, 3, 3.50, 1));
	}
	
	@Test
	public void testChangeType() {
		assertTrue(adao.changeType(2, 1));
	}
	
	@Test
	public void testChangeStatus() {
		assertTrue(adao.changeStatus(3, "Approved", 1));
	}
}