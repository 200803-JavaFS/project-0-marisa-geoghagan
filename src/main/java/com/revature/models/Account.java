package com.revature.models;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Account {
	private static final Logger log = LogManager.getLogger(Account.class);
	private int accountID;
	private int[] owners = new int[2];
	private boolean accountType;
	private double amount;
	private String status;
	private Update update;
	

	public Account() {
		super();
		log.debug("Blank Account object created.");
	}

	public Account(int[] owners, boolean accountType, Update update) {
		super();
		this.owners = owners;
		this.accountType = accountType;
		amount = 0;
		status = "Pending";
		this.update = update;
		log.debug("New Account object created.");
	}
	
	public Account(int accountID, int[] owners, boolean accountType, double amount, String status, Update update) {
		super();
		this.accountID = accountID;
		this.owners = owners;
		this.accountType = accountType;
		this.amount = amount;
		this.status = status;
		this.update = update;
		log.debug("Full Account object created.");
	}
	
	public int getAccountID() {
		log.debug("accountID returned.");
		return accountID;
	}
	
	public void setAccountID(int accountID) {
		log.debug("accountID set.");
		this.accountID = accountID;
	}
	
	public int[] getOwners() {
		log.debug("owners returned.");
		return owners;
	}
	
	public void setOwners(int[] owners) {
		log.debug("owners set.");
		this.owners = owners;
	}
	
	public boolean getAccountType() {
		log.debug("accountType returned.");
		return accountType;
	}
	
	public void setAccountType(boolean accountType) {
		log.debug("accountType set.");
		this.accountType = accountType;
	}
	
	public double getAmount() {
		log.debug("amount returned.");
		return amount;
	}
	
	public void setAmount(double amount) {
		log.debug("amount set.");
		this.amount = amount;
	}
	
	public String getStatus() {
		log.debug("status returned.");
		return status;
	}
	
	public void setStatus(String status) {
		log.debug("status set.");
		this.status = status;
	}
	
	public Update getUpdate() {
		log.debug("update returned.");
		return update;
	}

	public void setUpdate(Update update) {
		log.debug("update set.");
		this.update = update;
	}

	@Override
	public String toString() {
		return "Account [accountID=" + accountID + ", owners=" + Arrays.toString(owners) + ", accountType="
				+ accountType + ", amount=" + amount + ", status=" + status + ", update=" + update + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountID;
		result = prime * result + (accountType ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Arrays.hashCode(owners);
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((update == null) ? 0 : update.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			log.debug("These are the same Accounts.");
			return true;
		}
		if (obj == null) {
			log.debug("UserB is a null Account.");
			return false;
		}
		if (getClass() != obj.getClass()) {
			log.debug("One of these isn't a Account!");
			return false;
		}
		Account other = (Account) obj;
		if (accountID != other.accountID) {
			log.debug("These Accounts have different accountIDs.");
			return false;
		}
		if (accountType != other.accountType) {
			log.debug("These Accounts have different accountTypes.");
			return false;
		}
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount)) {
			log.debug("These Accounts have different amounts.");
			return false;
		}
		if (!Arrays.equals(owners, other.owners)) {
			log.debug("These Accounts have owners.");
			return false;
		}
		if (status == null) {
			log.debug("AccountA has a null status.");
			if (other.status != null) {
				log.debug("... but AccountB doesn't.");
				return false;
			}
		} else if (!status.equals(other.status)) {
			log.debug("These Accounts have different statuses.");
			return false;
		}
		if (update == null) {
			log.debug("AccountA has a null update.");
			if (other.update != null) {
				log.debug("... but AccountB doesn't.");
				return false;
			}
		} else if (!update.equals(other.update)) {
			log.debug("These Accounts have different updates.");
			return false;
		}
		return true;
	}
}