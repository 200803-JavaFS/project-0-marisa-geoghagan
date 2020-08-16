package com.revature.daos;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.Update;

public interface IAccountDAO {
	public List<Account> findAll();
	public List<Account> findByOwner(int userID);
	public List<Account> findByStatus(String status);
	public List<Account> findByType(boolean type);
	public Account findByAccountID(int accountID);
	public Update getUpdate(int accountID);
	public boolean addAccount(Account a, int updatingUserID);
	public boolean updateAmount(int accountID, double amount, int updatingUserID);
	public boolean transfer(int accountID1, int accountID2, double amount, int updatingUserID);
	public boolean changeType(int accountID, int updatingUserID);
	public boolean changeStatus(int accountID, String status, int updatingUserID);
}
