package com.revature.daos;

import java.util.List;

import org.junit.Test;

import com.revature.models.Account;
import com.revature.models.Update;

public class AccountDAOTest {
	public static AccountDAO adao = new AccountDAO();
	public static List<Account> findAll;
	public static Account a;
	
	@Test
	public void testAdd() {
		a = new Account(new int[] {1, 2}, false, new Update(1));
		adao.addAccount(a, 1);
	}
	
	public void testFindAll() {
		findAll = adao.findAll();
		for(Account a : findAll) {
			System.out.println(a.toString());
		}
	}
}
