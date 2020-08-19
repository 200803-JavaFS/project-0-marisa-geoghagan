package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.Account;
import com.revature.models.Update;
import com.revature.utils.ConnectionUtility;

public class AccountDAO implements IAccountDAO {
	private static final Logger log = LogManager.getLogger(AccountDAO.class);
	
	@Override
	public List<Account> findAll() {
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "SELECT * FROM accounts;";
			Statement statement = conn.createStatement();
			List<Account> list = new ArrayList<>(); 
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()) {
				Account a = new Account(result.getInt("account_id"), new int[] {result.getInt("owner1"), result.getInt("owner2")}, result.getBoolean("account_type"), result.getDouble("amount"), result.getString("account_status"), new Update(result.getTimestamp("update_at"), result.getInt("updated_by")));
				list.add(a); 
			}
			if(list.isEmpty())
				log.warn("Something went horribly wrong with AccountDAO.findAll().");
			log.info("Returning all Accounts.");
			return list;
		}catch(SQLException e) {
			log.warn("Encountered an SQLException in AccountDAO.findAll().");
			e.printStackTrace();
		}
		log.error("Connection failed in UserDAO.findAll().");
		return null;
	}

	@Override
	public List<Account> findByOwner(int userID) {
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "SELECT * FROM accounts WHERE owner1 = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, userID);
			ResultSet result = statement.executeQuery();
			List<Account> list = new ArrayList<>();
			boolean found = false;
			
			while(result.next()) {
				found = true;
				Account a = new Account(result.getInt("account_id"), new int[] {result.getInt("owner1"), result.getInt("owner2")}, result.getBoolean("account_type"), result.getDouble("amount"), result.getString("account_status"), new Update(result.getTimestamp("update_at"), result.getInt("updated_by")));
				list.add(a);
			}
			if (list.isEmpty()){
				log.debug("No owners in owner1 match that userID.");
			}
			
			sql = "SELECT * FROM accounts WHERE owner2 = ?;";
			statement = conn.prepareStatement(sql);
			statement.setInt(1, userID);
			result = statement.executeQuery();
			
			if(result.next()) {
				found = true;
				Account a = new Account(result.getInt("account_id"), new int[] {result.getInt("owner1"), result.getInt("owner2")}, result.getBoolean("account_type"), result.getDouble("amount"), result.getString("account_status"), new Update(result.getTimestamp("update_at"), result.getInt("updated_by")));
				list.add(a);
			}
			if (list.isEmpty()){
				log.debug("No owners in owner2 match that userID.");
			}
			
			if(!found) {
				log.info("No owners match that userID.");
			}
			log.info("Returning all Accounts owned by " + Integer.toString(userID) + ".");
			return list;
		} catch (SQLException e) {
			log.warn("Encountered an SQLException in AccountDAO.findByOwner().");
			e.printStackTrace();
		}
		log.error("Connection failed in AccountDAO.findByOwner().");
		return null;
	}

	@Override
	public List<Account> findByStatus(String status) {
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "SELECT * FROM accounts WHERE account_status = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, status);
			ResultSet result = statement.executeQuery();
			List<Account> list = new ArrayList<>(); 
			
			while(result.next()) {
				Account a = new Account(result.getInt("account_id"), new int[] {result.getInt("owner1"), result.getInt("owner2")}, result.getBoolean("account_type"), result.getDouble("amount"), result.getString("account_status"), new Update(result.getTimestamp("update_at"), result.getInt("updated_by")));
				list.add(a); 
			}
			if(list.isEmpty())
				log.info("No Accounts exist with that status.");
			log.info("Returning all Accounts with accountStatus " + status + ".");
			return list;
		} catch (SQLException e) {
			log.warn("Encountered an SQLException in AccountDAO.findByStatus().");
			e.printStackTrace();
		}
		log.error("Connection failed in AccountDAO.findByStatus().");
		return null;
	}

	@Override
	public List<Account> findByType(boolean type) {
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "SELECT * FROM accounts WHERE account_type = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setBoolean(1, type);
			ResultSet result = statement.executeQuery();
			List<Account> list = new ArrayList<>(); 
			
			while(result.next()) {
				Account a = new Account(result.getInt("account_id"), new int[] {result.getInt("owner1"), result.getInt("owner2")}, result.getBoolean("account_type"), result.getDouble("amount"), result.getString("account_status"), new Update(result.getTimestamp("update_at"), result.getInt("updated_by")));
				list.add(a); 
			}
			if(list.isEmpty())
				log.info("No Accounts exist with that type.");
			log.info("Returning all Accounts with accountType " + Boolean.toString(type) + ".");
			return list;
		} catch (SQLException e) {
			log.warn("Encountered an SQLException in AccountDAO.findByType().");
			e.printStackTrace();
		}
		log.error("Connection failed in AccountDAO.findByType().");
		return null;
	}

	@Override
	public Account findByAccountID(int accountID) {
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "SELECT * FROM accounts WHERE account_id = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, accountID);
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				Account a = new Account(result.getInt("account_id"), new int[] {result.getInt("owner1"), result.getInt("owner2")}, result.getBoolean("account_type"), result.getDouble("amount"), result.getString("account_status"), new Update(result.getTimestamp("update_at"), result.getInt("updated_by")));
				log.info("Returning the found Account.");
				return a;
			}
			else {
				log.info("No Account with that accountID exists.");
				return null;
			}
		} catch (SQLException e) {
			log.warn("Encountered an SQLException in AccountDAO.findByAccountID().");
			e.printStackTrace();
		}
		log.error("Connection failed in AccountDAO.findByAccountID().");
		return null;
	}
	
	@Override
	public Update getUpdate(int accountID) {
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "SELECT update_at, updated_by FROM accounts WHERE account_id = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, accountID);
			ResultSet result = statement.executeQuery();
			Update update = new Update();
			
			if(result.next()) {
				update.setUpdateTime(result.getTimestamp("update_at"));
				update.setUpdater(result.getInt("updated_by"));
				log.info("Returning found Update.");
				return update;
			}
			log.info("No Account with that accountID exists.");
			return null;
		} catch (SQLException e) {
			log.warn("Encountered an SQLException in AccountDAO.getUpdate().");
			e.printStackTrace();
		}
		log.error("Connection failed in AccountDAO.getUpdate().");
		return null;
	}

	@Override
	public boolean addAccount(Account a, int updatingUserID) {
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "INSERT INTO accounts (account_id, owner1, owner2, account_type, amount, account_status, updated_by)" + "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?);";
				PreparedStatement statement = conn.prepareStatement(sql);
				
				int[] owners = a.getOwners();
				int index = 0;
				statement.setInt(++index, owners[0]);
				statement.setInt(++index, owners[1]);
				statement.setBoolean(++index, a.getAccountType());
				statement.setDouble(++index, a.getAmount());
				statement.setString(++index, a.getStatus());
				statement.setInt(++index, updatingUserID);
				
				statement.execute();
				log.info("Successfully added new Account.");
				return true; 
			}catch (SQLException e) {
				log.warn("Encountered an SQLException in AccountDAO.addAccount().");
				e.printStackTrace();
			}
		log.error("Connection failed in AccountDAO.addAccount().");
		return false;
	}

	@Override
	public boolean updateAmount(int accountID, double amount, int updatingUserID) {
		try(Connection conn = ConnectionUtility.getConnection()){
			double oldAmount;
			String sql = "SELECT amount FROM accounts WHERE account_id = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, accountID);
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				oldAmount = result.getDouble("amount");
			}
			else {
				log.info("No Account with that accountID exists.");
				return false;
			}			
			
			double newAmount = oldAmount + amount;
			sql = "UPDATE accounts SET amount = ?, updated_by = ? WHERE account_id = ?;";
			statement = conn.prepareStatement(sql);
			statement.setDouble(1, newAmount);
			statement.setInt(2, updatingUserID);
			statement.setInt(3, accountID);
			
			statement.execute();
			log.info("Successfully updated the amount.");
			return true;
		} catch (SQLException e) {
			log.warn("Encountered an SQLException in AccountDAO.updateAmount().");
			e.printStackTrace();
		}
		log.error("Connection failed in AccountDAO.updateAmount().");
		return false;
	}

	@Override
	public boolean transfer(int accountID1, int accountID2, double amount, int updatingUserID) {
		try(Connection conn = ConnectionUtility.getConnection()){
			updateAmount(accountID1, (amount * -1), updatingUserID);
			log.info("Successfully updated " + Integer.toString(accountID1) + ".");
			updateAmount(accountID2, amount, updatingUserID);
			log.info("Successfully updated " + Integer.toString(accountID2) + ".");
			return true;
		} catch (SQLException e) {
			log.warn("Encountered an SQLException in AccountDAO.transfer().");
			e.printStackTrace();
		}
		log.error("Connection failed in AccountDAO.transfer().");
		return false;
	}

	@Override
	public boolean changeType(int accountID, int updatingUserID) {
		try(Connection conn = ConnectionUtility.getConnection()){
			boolean newType = false;
			String sql = "SELECT account_type FROM accounts WHERE account_id = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, accountID);
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				newType = result.getBoolean("account_type");
			}
			else {
				log.info("No Account with that accountID exists.");
				return false;
			}

			sql = "UPDATE accounts SET account_type = ?, updated_by = ? WHERE account_id = ?;";
			statement = conn.prepareStatement(sql);
			statement.setBoolean(1, !newType);
			statement.setInt(2, updatingUserID);
			statement.setInt(3, accountID);
			
			statement.execute();
			log.info("accountType successfully converted to " + !newType + ".");
			return true;			
		} catch (SQLException e) {
			log.warn("Encountered an SQLException in AccountDAO.changeType().");
			e.printStackTrace();
		}
		log.error("Connection failed in AccountDAO.changeType().");
		return false;
	}

	@Override
	public boolean changeStatus(int accountID, String status, int updatingUserID) {
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "UPDATE accounts SET account_status = ?, updated_by = ? WHERE account_id = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			int index = 0;
			statement.setString(++index, status);
			statement.setInt(++index, updatingUserID);
			statement.setInt(++index, accountID);
			
			statement.execute();
			log.info("Successfully changed the accountStatus.");
			return true;
		} catch (SQLException e) {
			log.warn("Encountered an SQLException in AccountDAO.changeStatus().");
			e.printStackTrace();
		}
		log.error("Connection failed in AccountDAO.changeStatus().");
		return false;
	}
}