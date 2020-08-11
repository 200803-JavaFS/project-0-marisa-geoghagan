package com.revature.models;

import java.util.ArrayList;

public class Account {
	private static int accountNum = 0;
	private int accountID;
	private ArrayList<Integer> owners = new ArrayList<Integer>();
	private String accountType;
	private double amount;
	private String status;
	
	public Account() {
		super();
	}
	
	public Account(ArrayList<Integer> owners, String accountType) {
		super();
		accountID = accountNum;
		accountNum++;
		this.owners = owners;
		this.accountType = accountType;
		amount = 0;
		status = "Pending";
	}
	
	public int getAccountID() {
		return accountID;
	}
	
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	
	public ArrayList<Integer> getOwners() {
		return owners;
	}
	
	public void setOwners(ArrayList<Integer> owners) {
		this.owners = owners;
	}
	
	public String getAccountType() {
		return accountType;
	}
	
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}
