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

import com.revature.models.Update;
import com.revature.models.User;
import com.revature.utils.ConnectionUtility;

public class UserDAO implements IUserDAO {
	private static final Logger log = LogManager.getLogger(UserDAO.class);
	
	@Override
	public List<User> findAll() {
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "SELECT * FROM users;";
			Statement statement = conn.createStatement();
			List<User> list = new ArrayList<>(); 
			ResultSet result = statement.executeQuery(sql);

			while(result.next()) {
				User u = new User(result.getInt("user_id"), result.getString("user_name"), result.getString("pass_word"), result.getInt("user_type"), result.getString("user_status"), result.getString("first_name"), result.getString("last_name"), new Update(result.getTimestamp("update_at"), result.getInt("updated_by")));
				list.add(u); 
			}
			if(list.isEmpty())
				log.warn("Something went horribly wrong with UserDAO.findAll().");
			log.info("Returning all Users.");
			return list;
		}catch(SQLException e) {
			log.warn("Encountered an SQLException in UserDAO.findAll().");
			e.printStackTrace();
		}
		log.error("Connection failed in UserDAO.findAll().");
		return null;
	}
	
	@Override
	public List<User> findByUserType(int userType) {
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "SELECT * FROM users WHERE user_type = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, userType);
			ResultSet result = statement.executeQuery();
			List<User> list = new ArrayList<>(); 
			
			while(result.next()) {
				User u = new User(result.getInt("user_id"), result.getString("user_name"), result.getString("pass_word"), result.getInt("user_type"), result.getString("user_status"), result.getString("first_name"), result.getString("last_name"), new Update(result.getTimestamp("update_at"), result.getInt("updated_by")));
				list.add(u); 
			}
			if(list.isEmpty())
				log.warn("No Users with that userType exist.");
			log.info("Returning all Users with userType " + Integer.toString(userType) + ".");
			return list;
		} catch (SQLException e) {
			log.warn("Encountered an SQLException in UserDAO.findByUserType().");
			e.printStackTrace();
		}
		log.error("Connection failed in UserDAO.findByUserType().");
		return null;
	}
	
	@Override
	public User findByUserID(int userID) {
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "SELECT * FROM users WHERE user_id = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, userID);
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				User u = new User(result.getInt("user_id"), result.getString("user_name"), result.getString("pass_word"), result.getInt("user_type"), result.getString("user_status"), result.getString("first_name"), result.getString("last_name"), new Update(result.getTimestamp("update_at"), result.getInt("updated_by")));
				log.info("Returning found user.");
				return u;
			} else {
				log.info("No User with that userID exists.");
				return null;
			}			
		} catch (SQLException e) {
			log.warn("Encountered an SQLException in UserDAO.findByUserID().");
			e.printStackTrace();
		}
		log.error("Connection failed in UserDAO.findByUserID().");
		return null;
	}
	
	@Override
	public User findByUserName(String userName) {
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "SELECT * FROM users WHERE user_name = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userName);
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				User u = new User(result.getInt("user_id"), result.getString("user_name"), result.getString("pass_word"), result.getInt("user_type"), result.getString("user_status"), result.getString("first_name"), result.getString("last_name"), new Update(result.getTimestamp("update_at"), result.getInt("updated_by")));
				log.info("Returning found User.");
				return u;
			} else {
				log.info("No User with that userName exists.");
				return null;
			}			
		} catch (SQLException e) {
			log.warn("Encountered an SQLException in UserDAO.findByUserName().");
			e.printStackTrace();
		}
		log.error("Connection failed in UserDAO.findByUserName().");
		return null;
	}
	
	@Override
	public List<User> findByStatus(String status) {
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "SELECT * FROM users WHERE user_status = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, status);
			ResultSet result = statement.executeQuery();
			List<User> list = new ArrayList<>(); 
			
			while(result.next()) {
				User u = new User(result.getInt("user_id"), result.getString("user_name"), result.getString("pass_word"), result.getInt("user_type"), result.getString("user_status"), result.getString("first_name"), result.getString("last_name"), new Update(result.getTimestamp("update_at"), result.getInt("updated_by")));
				list.add(u); 
			}
			if(list.isEmpty())
				log.warn("No Users with that status exist.");
			log.info("Returning all Users with status " + status + ".");
			return list;
		} catch (SQLException e) {
			log.warn("Encountered an SQLException in UserDAO.findByUserStatus().");
			e.printStackTrace();
		}
		log.error("Connection failed in UserDAO.findByUserStatus().");
		return null;
	}

	@Override
	public Update getUpdate(int userID) {
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "SELECT update_at, updated_by FROM users WHERE user_id = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, userID);
			ResultSet result = statement.executeQuery();
			Update update = new Update();
			
			if(result.next()) {
				update.setUpdateTime(result.getTimestamp("update_at"));
				update.setUpdater(result.getInt("updated_by"));
				log.info("Returning found Update.");
				return update;
			}
			log.info("No User with that userID exists.");
			return null;
		} catch (SQLException e) {
			log.warn("Encountered an SQLException in UserDAO.getUpdate().");
			e.printStackTrace();
		}
		log.error("Connection failed in UserDAO.getUpdate().");
		return null;
	}
	
	@Override
	public boolean addUser(User u, int updatingUserID) {
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "INSERT INTO users (user_id, user_name, pass_word, user_type, user_status, first_name, last_name, update_at, updated_by)" + "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, DEFAULT, ?);";
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int index = 0;
			statement.setString(++index, u.getUserName());
			statement.setString(++index, u.getPassword());
			statement.setInt(++index, u.getUserType());
			statement.setString(++index, u.getUserStatus());
			statement.setString(++index, u.getFirstName());
			statement.setString(++index,  u.getLastName());
			statement.setInt(++index, updatingUserID);
			
			statement.execute();
			log.info("Successfully added the new User.");
			return true; 
		}catch (SQLException e) {
			log.warn("Encountered an SQLException in UserDAO.addUser().");
			e.printStackTrace();
		}
		log.error("Connection failed in UserDAO.addUser().");
		return false;
	}
	
	@Override
	public boolean changeStatus(int userID, String status, int updatingUserID) {
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "UPDATE users SET user_status = ?, updated_by = ? WHERE user_id = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			int index = 0;
			statement.setString(++index, status);
			statement.setInt(++index, updatingUserID);
			statement.setInt(++index, userID);
			
			statement.execute();
			log.info("Successfully changed the userStatus.");
			return true;
		} catch (SQLException e) {
			log.warn("Encountered an SQLException in UserDAO.changeStatus().");
			e.printStackTrace();
		}
		log.error("Connection failed in UserDAO.changeStatus().");
		return false;
	}

	@Override
	public boolean changePassword(int userID, String password, int updatingUserID) {
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "UPDATE users SET pass_word = ?, updated_by = ? WHERE user_id = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			int index = 0;
			statement.setString(++index, password);
			statement.setInt(++index, updatingUserID);
			statement.setInt(++index, userID);
			
			statement.execute();
			log.info("Successfully changed the password.");
			return true;
		} catch (SQLException e) {
			log.warn("Encountered an SQLException in UserDAO.changePassword().");
			e.printStackTrace();
		}
		log.error("Connection failed in UserDAO.changePassword().");
		return false;
	}
	
	@Override
	public boolean changeType(int userID, int userType, int updatingUserID) {
		try(Connection conn = ConnectionUtility.getConnection()){
			String sql = "UPDATE users SET user_type = ?, updated_by = ? WHERE user_id = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			int index = 0;
			statement.setInt(++index, userType);
			statement.setInt(++index, updatingUserID);
			statement.setInt(++index, userID);
			
			statement.execute();
			log.info("Successfully changed the userType.");
			return true;
		} catch (SQLException e) {
			log.warn("Encountered an SQLException in UserDAO.changeType().");
			e.printStackTrace();
		}
		log.error("Connection failed in UserDAO.changeType().");
		return false;
	}
}