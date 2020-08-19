package com.revature.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.daos.AccountDAO;
import com.revature.models.Account;

public class AccountServices {
	private static final Logger log = LogManager.getLogger(UserServices.class);
	private AccountDAO adao = new AccountDAO();
	
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

	public static boolean isDouble(String input) {
		try { 
			Double.parseDouble(input);
			return true;
		} catch(NumberFormatException e) { 
			log.debug("Input is not a double.");
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
			log.warn("Something has gone seriously wrong.");
			return -1;
		}
	}
	
	public static double stringToDouble(String input) {
		if(isDouble(input)) {
			return Double.parseDouble(input);
		} else {
			log.warn("Something has gone seriously wrong.");
			return -1;
		}
	}
	
	public List<Account> displayAccounts() {
		return adao.findAll();
	}
	
	public Account findByID(int accountID) {
		return adao.findByAccountID(accountID);
	}
	
	public String[][] convertToAccountListArray(List<Account> list) {
		String[][] array = new String[list.size()][8];
		for(int i = 0;  i < list.size(); i++) {
			int[] owners = list.get(i).getOwners();
			array[i][0] = Integer.toString(list.get(i).getAccountID());
			array[i][1] = Integer.toString(owners[0]);
			array[i][2] = Integer.toString(owners[1]);
			array[i][3] = typeToString(list.get(i).getAccountType());
			array[i][4] = list.get(i).getStatus();
			array[i][5] = Double.toString(list.get(i).getAmount());
			array[i][6] = list.get(i).getUpdate().getUpdateTime().toString();
			array[i][7] = Integer.toString(list.get(i).getUpdate().getUpdater());
		}
		return array;
	}
	
	public String[] getColumnNames() {
		String[] array = new String [8];
		array[0] = "Account ID";
		array[1] = "First Owner";
		array[2] = "Second Owner";
		array[3] = "Account Type";
		array[4] = "Account Status";
		array[5] = "Amount";
		array[6] = "Last Update Time";
		array[7] = "Last Updater";
		return array;
	}
	
	public String typeToString(boolean type) {
		if(type) {
			log.debug("This is a checking account.");
			return "Checking";
		} else {
			log.debug("This is a savings account.");
			return "Savings";
		}
	}
	
	public double checkAmount(int accountID) {
		return adao.findByAccountID(accountID).getAmount();
	}
	
	public List<Account> findByOwner(int userID) {
		return adao.findByOwner(userID);
	}
	
	public boolean isOwner(int accountID, int userID) {
		Account account = findByID(accountID);
		int[] owners = account.getOwners();
		if(userID == owners[0]) {
			log.debug("Owner found.");
			return true;
		} else if(userID == owners[1]) {
			log.debug("Owner found.");
			return true;
		} else {
			log.debug("Owner not found.");
			return false;
		}
	}
	
	public boolean openAccount(boolean type, int[] owners, int updatingUserID) {
		log.info("UserID " + updatingUserID + " created a new " + typeToString(type) + " account owned by " + owners[0] + " and " + owners[1] + ".");
		return adao.addAccount(new Account(owners, type), updatingUserID);
	}
	
	public boolean withdraw(int accountID, double amount, int updatingUserID) {
		log.info("UserID " + updatingUserID + "withdrew $" + amount + " from accountID " + accountID + ".");
		double updated = checkAmount(accountID) - amount;
		return adao.updateAmount(accountID, updated, updatingUserID);
	}
	
	public boolean deposit(int accountID, double amount, int updatingUserID) {
		log.info("UserID " + updatingUserID + "deposited $" + amount + " into accountID " + accountID + ".");
		double updated = checkAmount(accountID) + amount;
		return adao.updateAmount(accountID, updated, updatingUserID);
	}
	
	public boolean transfer(int accountID1, int accountID2, double amount, int updatingUserID) {
		log.info("Transferred $" + amount + " from accountID " + accountID1 + " to accountID " + accountID2 + ".");
		return adao.transfer(accountID1, accountID2, amount, updatingUserID);
	}
}