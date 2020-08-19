package com.revature;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.text.MessageFormat;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.AccountServices;
import com.revature.services.UserServices;

public class GUI extends Frame {
	private static final Logger log = LogManager.getLogger(GUI.class);
	private static UserServices uServices = new UserServices();
	private static User user;
	private static Account account;
	private static AccountServices aServices = new AccountServices();
	
	public void topMenu() {
		Panel menuDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		Label label = new Label("Welcome to Java Bank!");
		label.setAlignment(Label.CENTER);
		menuDisplay.add(label, c);

		c.gridx = 0;
		c.gridy = 1;
		label = new Label("How may I help you?");
		label.setAlignment(Label.CENTER);
		menuDisplay.add(label, c);

		c.insets = new Insets(0,10,0,10);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 2;
		Button btnLogin = new Button("Login");
		menuDisplay.add(btnLogin, c);

		c.insets = new Insets(0,10,0,10);
		c.gridx = 1;
		c.gridy = 2;
		Button btnRegister = new Button("Register");
		menuDisplay.add(btnRegister, c);
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				loginFrame();
				menuDisplay.setVisible(false);
			}
		});
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				registerFrame();
				menuDisplay.setVisible(false);
			}
		});
		setLayout(new BorderLayout());
		add(menuDisplay);
		setVisible(true);
	}

	public void loginFrame() {
		Panel loginDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 0;
		Label label = new Label("Please enter your login credentials.");
		label.setAlignment(Label.CENTER);
		loginDisplay.add(label, c);

		c.insets = new Insets(5,5,5,5);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		loginDisplay.add(new Label("Username: "), c);

		c.gridx = 1;
		c.gridy = 1;
		TextField tfUserName = new TextField(30);
		loginDisplay.add(tfUserName, c);

		c.gridx = 2;
		c.gridy = 1;
		loginDisplay.add(new Label("Password: "), c);

		c.gridx = 3;
		c.gridy = 1;
		TextField tfPassword = new TextField(30);
		loginDisplay.add(tfPassword, c);

		c.gridwidth = 2;
		c.insets = new Insets(10,0,0,10);
		c.gridx = 0;
		c.gridy = 2;
		Button btnLogin = new Button("Login");
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				loginResult(tfUserName.getText(), tfPassword.getText());
				loginDisplay.setVisible(false);
			}
		});
		loginDisplay.add(btnLogin, c);

		c.insets = new Insets(10,10,0,0);
		c.gridx = 2;
		c.gridy = 2;
		Button btnBack = new Button("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				topMenu();
				loginDisplay.setVisible(false);
			}
		});
		loginDisplay.add(btnBack, c);

		add(loginDisplay);
		setVisible(true);
	}

	public void loginResult(String userName, String password) {
		user = uServices.returnUserByName(userName);
		if(user == null) {
			log.warn("Invalid userName.");
			JOptionPane.showMessageDialog(this, "No such username exists.", "Warning!", JOptionPane.WARNING_MESSAGE);
			loginFrame();
		} else if(!uServices.isApproved(user)) {
			log.warn("userStatus still pending.");
			JOptionPane.showMessageDialog(this, "This account is still pending approval. Thank you for your patience.", "Warning!", JOptionPane.WARNING_MESSAGE);
			topMenu();
		} else if(uServices.isDeleted(user)) {
			log.warn("userStatus is deleted.");
			JOptionPane.showMessageDialog(this, "This user account has been closed. Please contact a bank administrator for details.", "Warning!", JOptionPane.WARNING_MESSAGE);
			loginFrame();
		} else if(uServices.login(password)) {
			log.info("Successful login.");
			servicesFrame();
		} else {
			log.warn("Incorrect password.");
			JOptionPane.showMessageDialog(this, "That password doesn't match our records.", "Warning!", JOptionPane.WARNING_MESSAGE);
			loginFrame();
		}
	}

	public void registerFrame() {
		Panel registerDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 0;
		Label label = new Label("Please enter your login credentials and personal information.");
		label.setAlignment(Label.CENTER);
		registerDisplay.add(label, c);

		c.insets = new Insets(5,5,5,5);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		registerDisplay.add(new Label("Username: "), c);

		c.gridx = 1;
		c.gridy = 1;
		TextField tfUserName = new TextField(30);
		registerDisplay.add(tfUserName, c);

		c.gridx = 2;
		c.gridy = 1;
		registerDisplay.add(new Label("Password: "), c);

		c.gridx = 3;
		c.gridy = 1;
		TextField tfPassword = new TextField(30);
		registerDisplay.add(tfPassword, c);

		c.gridx = 0;
		c.gridy = 2;
		registerDisplay.add(new Label("First Name: "), c);

		c.gridx = 1;
		c.gridy = 2;
		TextField tfFirstName = new TextField(30);
		registerDisplay.add(tfFirstName, c);

		c.gridx = 2;
		c.gridy = 2;
		registerDisplay.add(new Label("Last Name: "), c);

		c.gridx = 3;
		c.gridy = 2;
		TextField tfLastName = new TextField(30);
		registerDisplay.add(tfLastName, c);

		c.gridwidth = 2;
		c.insets = new Insets(10,0,0,10);
		c.gridx = 0;
		c.gridy = 3;
		Button btnRegister = new Button("Register");
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				User u = uServices.returnUserByName(tfUserName.getText());
				if(u != null) {
					log.warn("Duplicate userName.");
					JOptionPane.showMessageDialog(registerDisplay, "This username is already in our system. Please contact a bank employee if you have forgotten your password.", "Warning!", JOptionPane.WARNING_MESSAGE);
					topMenu();
					registerDisplay.setVisible(false);
				} else {
					uServices.register(new User(tfUserName.getText(), tfPassword.getText(), 2, "Pending", tfFirstName.getText(), tfLastName.getText()));
					JOptionPane.showMessageDialog(registerDisplay, "Your User account must be approved before it can be used. Please wait patiently for approval.", "Message", JOptionPane.PLAIN_MESSAGE);
					topMenu();
					registerDisplay.setVisible(false);
				}
			}
		});
		registerDisplay.add(btnRegister, c);

		c.insets = new Insets(10,10,0,0);
		c.gridx = 2;
		c.gridy = 3;
		Button btnBack = new Button("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				topMenu();
				registerDisplay.setVisible(false);
			}
		});
		registerDisplay.add(btnBack, c);

		add(registerDisplay);
		setVisible(true);
	}

	public void servicesFrame() {
		Panel servicesDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;
		Label label = new Label("Welcome, " + user.getFirstName() + "! How may we serve you today?");
		label.setAlignment(Label.CENTER);
		servicesDisplay.add(label, c);

		c.gridwidth = 1;
		c.insets = new Insets(10,0,0,10);
		c.gridx = 0;
		c.gridy = 1;
		Button btnUserServices = new Button("User Services");
		btnUserServices.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if(user.getUserType() == 0 || user.getUserType() == 1) {
					log.info("Accessing employeeServices");
					employeeServicesFrame();
					servicesDisplay.setVisible(false);
				} else {
					log.info("Accessing customerServices");
					customerServicesFrame();
					servicesDisplay.setVisible(false);
				}
			}
		});
		servicesDisplay.add(btnUserServices, c);

		c.insets = new Insets(10,10,0,10);
		c.gridx = 1;
		c.gridy = 1;
		Button btnAccountServices = new Button("Account Services");
		btnAccountServices.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				log.info("Accessing account services.");
				accountServicesFrame();
				servicesDisplay.setVisible(false);
			}
		});
		servicesDisplay.add(btnAccountServices, c);

		c.insets = new Insets(10,10,0,0);
		c.gridx = 2;
		c.gridy = 1;
		Button btnLogout = new Button("Log Out");
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				log.info("Logging out...");
				uServices.logOut();
				user = null;
				JOptionPane.showMessageDialog(servicesDisplay, "Log out was successful.", "Message", JOptionPane.PLAIN_MESSAGE);
				topMenu();
				servicesDisplay.setVisible(false);
			}
		});
		servicesDisplay.add(btnLogout, c);

		add(servicesDisplay);
		setVisible(true);
	}

	public void customerServicesFrame() {
		Panel customerServicesDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		Label label = new Label("Welcome, " + user.getFirstName() + "! How may we serve you today?");
		label.setAlignment(Label.CENTER);
		customerServicesDisplay.add(label, c);

		c.gridwidth = 1;
		c.insets = new Insets(10,0,0,10);
		c.gridx = 0;
		c.gridy = 1;
		Button btnChangePassword = new Button("Change Password");
		btnChangePassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				log.info("Changing password.");
				changePasswordFrame();
				customerServicesDisplay.setVisible(false);
			}
		});
		customerServicesDisplay.add(btnChangePassword, c);

		c.insets = new Insets(10,10,0,0);
		c.gridx = 1;
		c.gridy = 1;
		Button btnPreviousMenu = new Button("Previous Menu");
		btnPreviousMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				log.info("Returning to Services Menu.");
				servicesFrame();
				customerServicesDisplay.setVisible(false);
			}
		});
		customerServicesDisplay.add(btnPreviousMenu, c);

		add(customerServicesDisplay);
		setVisible(true);
	}

	public void employeeServicesFrame() {
		Panel employeeServicesDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 6;
		c.gridx = 0;
		c.gridy = 0;
		Label label = new Label("Welcome, " + user.getFirstName() + "! How may we serve you today?");
		label.setAlignment(Label.CENTER);
		employeeServicesDisplay.add(label, c);

		c.gridwidth = 1;
		c.insets = new Insets(10,0,0,10);
		c.gridx = 0;
		c.gridy = 1;
		Button btnViewAll = new Button("View All Users");
		btnViewAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				log.info("Viewing all users.");
				viewAllFrame();
				employeeServicesDisplay.setVisible(false);
			}
		});
		employeeServicesDisplay.add(btnViewAll, c);

		c.gridx = 1;
		c.gridy = 1;
		Button btnFindUser = new Button("Find User by ID");
		btnFindUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				log.info("Accessing User search by ID.");
				findByUserIDFrame();
				employeeServicesDisplay.setVisible(false);
			}
		});
		employeeServicesDisplay.add(btnFindUser, c);

		c.gridx = 2;
		c.gridy = 1;
		Button btnViewPending = new Button("View Pending Users");
		btnViewPending.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				log.info("Viewing pending users.");
				viewPendingFrame();
				employeeServicesDisplay.setVisible(false);
			}
		});
		employeeServicesDisplay.add(btnViewPending, c);

		c.gridx = 3;
		c.gridy = 1;
		Button btnChangePassword = new Button("Change Password");
		btnChangePassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if(user.getUserType() == 0) {
					log.info("Changing Password as Admin.");
					changePasswordAdminFrame();
					employeeServicesDisplay.setVisible(false);
				} else {
					log.info("Changing password.");
					changePasswordFrame();
					employeeServicesDisplay.setVisible(false);
				}
			}
		});
		employeeServicesDisplay.add(btnChangePassword, c);

		c.gridx = 4;
		c.gridy = 1;
		Button btnChangeStatus = new Button("Change Status");
		btnChangeStatus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if(user.getUserType() == 0) {
					log.info("Changing Status as Admin.");
					changeStatusAdminFrame();
					employeeServicesDisplay.setVisible(false);
				} else {
					log.info("Changing Status.");
					changeStatusFrame();
					employeeServicesDisplay.setVisible(false);
				}
			}
		});
		employeeServicesDisplay.add(btnChangeStatus, c);

		c.insets = new Insets(10,10,0,0);
		c.gridx = 5;
		c.gridy = 1;
		Button btnPreviousMenu = new Button("Previous Menu");
		btnPreviousMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				log.info("Returning to Services Menu.");
				servicesFrame();
				employeeServicesDisplay.setVisible(false);
			}
		});
		employeeServicesDisplay.add(btnPreviousMenu, c);

		add(employeeServicesDisplay);
		setVisible(true);
	}

	public void viewAllFrame() {
		Panel viewAllDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,0,10,10);
		c.gridx = 0;
		c.gridy = 0;
		Label label = new Label("Here is a list of all users in our database: ");
		label.setAlignment(Label.CENTER);
		viewAllDisplay.add(label, c);

		c.insets = new Insets(10,10,10,0);
		c.gridx = 1;
		c.gridy = 0;
		Button btnPreviousMenu = new Button("Previous Menu");
		btnPreviousMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				log.info("Returning to Services Menu.");
				employeeServicesFrame();
				viewAllDisplay.setVisible(false);
			}
		});
		viewAllDisplay.add(btnPreviousMenu, c);

		JTable usersTable = new JTable(uServices.convertToUserListArray(uServices.findAll()), uServices.getColumnNames());
		usersTable.setFillsViewportHeight(true);
		usersTable.setRowHeight(24);

		JScrollPane scroll = new JScrollPane(usersTable);
		scroll.setSize(new Dimension(900, 450));
		scroll.setMinimumSize(new Dimension(900, 450));
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 1.0;
		viewAllDisplay.add(scroll, c);
		add(viewAllDisplay);
		setVisible(true);
	}

	public void findByUserIDFrame() {
		Panel findUserDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		findUserDisplay.add(new Label("User ID: "), c);

		c.gridx = 1;
		c.gridy = 0;
		TextField tfUserID = new TextField(30);
		findUserDisplay.add(tfUserID, c);

		c.gridwidth = 2;
		c.insets = new Insets(10,0,0,0);
		c.gridx = 0;
		c.gridy = 1;
		Button btnSearch = new Button("Search");
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				User found = uServices.findByID(tfUserID.getText());
				User u = uServices.findByID(tfUserID.getText());
				if(user == null) {
					log.warn("Invalid userName.");
					JOptionPane.showMessageDialog(findUserDisplay, "No such username exists.", "Warning!", JOptionPane.WARNING_MESSAGE);
					employeeServicesFrame();
				} else {
					findUserDisplay.setVisible(false);
					Panel foundUserDisplay = new Panel(new GridBagLayout());
					GridBagConstraints c = new GridBagConstraints();
					c.fill = GridBagConstraints.HORIZONTAL;
					c.gridwidth = 2;
					c.gridx = 0;
					c.gridy = 0;
					Label label = new Label("Found User: ");
					label.setAlignment(Label.CENTER);
					foundUserDisplay.add(label, c);
	
					c.gridwidth = 1;
					c.gridx = 0;
					c.gridy = 1;
					foundUserDisplay.add(new Label("Username: "), c);
	
					c.gridx = 1;
					foundUserDisplay.add(new Label(found.getUserName()), c);
	
					c.gridx = 0;
					c.gridy = 2;
					foundUserDisplay.add(new Label("Password: "), c);
	
					c.gridx = 1;
					foundUserDisplay.add(new Label(found.getPassword()), c);
	
					c.gridx = 0;
					c.gridy = 3;
					foundUserDisplay.add(new Label("User Type: "), c);
	
					c.gridx = 1;
					foundUserDisplay.add(new Label(uServices.typeToString(found.getUserType())), c);
	
					c.gridx = 0;
					c.gridy = 4;
					foundUserDisplay.add(new Label("User Status: "), c);
	
					c.gridx = 1;
					foundUserDisplay.add(new Label(found.getUserStatus()), c);
	
					c.gridx = 0;
					c.gridy = 5;
					foundUserDisplay.add(new Label("First Name: "), c);
	
					c.gridx = 1;
					foundUserDisplay.add(new Label(found.getFirstName()), c);
	
					c.gridx = 0;
					c.gridy = 6;
					foundUserDisplay.add(new Label("Last Name: "), c);
	
					c.gridx = 1;
					foundUserDisplay.add(new Label(found.getLastName()), c);
	
					c.gridx = 0;
					c.gridy = 7;
					foundUserDisplay.add(new Label("Last Updated: "), c);
	
					c.gridx = 1;
					foundUserDisplay.add(new Label(found.getUpdate().getUpdateTime().toString()), c);
	
					c.gridx = 0;
					c.gridy = 8;
					foundUserDisplay.add(new Label("Last Updated By: "), c);
	
					c.gridx = 1;
					foundUserDisplay.add(new Label(Integer.toString(found.getUpdate().getUpdater())), c);
	
					c.gridwidth = 2;
					c.gridx = 0;
					c.gridy = 0;
					Button btnPrevious = new Button("Previous Menu");
					btnPrevious.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent evt) {
							employeeServicesFrame();
							foundUserDisplay.setVisible(false);
						}
					});
					foundUserDisplay.add(btnPrevious);
	
					add(foundUserDisplay);
					setVisible(true);
				}
			}
		});
		findUserDisplay.add(btnSearch, c);

		c.gridx = 0;
		c.gridy = 2;
		Button btnBack = new Button("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				employeeServicesFrame();
				findUserDisplay.setVisible(false);
			}
		});
		findUserDisplay.add(btnBack, c);

		add(findUserDisplay);
		setVisible(true);
	}

	public void viewPendingFrame() {
		Panel pendingUsersDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,0,10,10);
		c.gridx = 0;
		c.gridy = 0;
		Label label = new Label("Here is a list of all users pending approval: ");
		label.setAlignment(Label.CENTER);
		pendingUsersDisplay.add(label, c);

		c.insets = new Insets(10,10,10,0);
		c.gridx = 1;
		c.gridy = 0;
		Button btnPreviousMenu = new Button("Previous Menu");
		btnPreviousMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				log.info("Returning to Services Menu.");
				employeeServicesFrame();
				pendingUsersDisplay.setVisible(false);
			}
		});
		pendingUsersDisplay.add(btnPreviousMenu, c);

		JTable usersTable = new JTable(uServices.convertToUserListArray(uServices.findPending()), uServices.getColumnNames());
		usersTable.setFillsViewportHeight(true);
		usersTable.setRowHeight(24);

		JScrollPane scroll = new JScrollPane(usersTable);
		scroll.setSize(new Dimension(900, 450));
		scroll.setMinimumSize(new Dimension(900, 450));
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 1.0;
		pendingUsersDisplay.add(scroll, c);
		add(pendingUsersDisplay);
		setVisible(true);
	}

	public void changePasswordFrame() {
		Panel changePasswordDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		changePasswordDisplay.add(new Label("New Password: "), c);

		c.gridx = 1;
		c.gridy = 0;
		TextField tfUserPW1 = new TextField(30);
		changePasswordDisplay.add(tfUserPW1, c);

		c.gridx = 0;
		c.gridy = 1;
		changePasswordDisplay.add(new Label("Retype New Password: "), c);

		c.gridx = 1;
		c.gridy = 1;
		TextField tfUserPW2 = new TextField(30);
		changePasswordDisplay.add(tfUserPW2, c);

		c.insets = new Insets(0,0,10,0);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 2;
		Button btnChange = new Button("Change Password");
		btnChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if(tfUserPW1.getText().equals(tfUserPW2.getText() )) {
					uServices.changePassword(user, tfUserPW1.getText(), user.getUserID());
					JOptionPane.showMessageDialog(changePasswordDisplay, "Password successfully changed.", "Message", JOptionPane.PLAIN_MESSAGE);
					if(user.getUserType() == 0 || user.getUserType() == 1) {
						employeeServicesFrame();
					} else {
						customerServicesFrame();
					}
					changePasswordDisplay.setVisible(false);
				}
			}
		});
		changePasswordDisplay.add(btnChange, c);

		c.insets = new Insets(0,0,0,0);
		c.gridx = 0;
		c.gridy = 3;
		Button btnBack = new Button("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if(user.getUserType() == 0 || user.getUserType() == 1) {
					employeeServicesFrame();
				} else {
					customerServicesFrame();
				}
				changePasswordDisplay.setVisible(false);
			}
		});
		changePasswordDisplay.add(btnBack, c);

		add(changePasswordDisplay);
		setVisible(true);
	}

	public void changePasswordAdminFrame() {
		Panel changePasswordAdminDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		changePasswordAdminDisplay.add(new Label("User ID: "), c);

		c.gridx = 1;
		c.gridy = 0;
		TextField tfUserID = new TextField(30);
		changePasswordAdminDisplay.add(tfUserID, c);

		c.gridx = 0;
		c.gridy = 1;
		changePasswordAdminDisplay.add(new Label("New Password: "), c);

		c.gridx = 1;
		c.gridy = 1;
		TextField tfUserPW1 = new TextField(30);
		changePasswordAdminDisplay.add(tfUserPW1, c);

		c.gridx = 0;
		c.gridy = 2;
		changePasswordAdminDisplay.add(new Label("Retype New Password: "), c);

		c.gridx = 1;
		c.gridy = 2;
		TextField tfUserPW2 = new TextField(30);
		changePasswordAdminDisplay.add(tfUserPW2, c);

		c.insets = new Insets(10,0,0,10);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 3;
		Button btnChange = new Button("Change Password");
		btnChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				User u = uServices.findByID(tfUserID.getText());
				if(user == null) {
					log.warn("Invalid userName.");
					JOptionPane.showMessageDialog(changePasswordAdminDisplay, "No such username exists.", "Warning!", JOptionPane.WARNING_MESSAGE);
					employeeServicesFrame();
				}
				if(tfUserPW1.getText().equals(tfUserPW2.getText() )) {
					uServices.changePassword(user, tfUserPW1.getText(), user.getUserID());
					JOptionPane.showMessageDialog(changePasswordAdminDisplay, "Password successfully changed.", "Message", JOptionPane.PLAIN_MESSAGE);
					employeeServicesFrame();
					changePasswordAdminDisplay.setVisible(false);
				}
			}
		});
		changePasswordAdminDisplay.add(btnChange, c);

		c.insets = new Insets(10,10,0,0);
		c.gridx = 2;
		c.gridy = 3;
		Button btnBack = new Button("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				employeeServicesFrame();
				changePasswordAdminDisplay.setVisible(false);
			}
		});
		changePasswordAdminDisplay.add(btnBack, c);

		add(changePasswordAdminDisplay);
		setVisible(true);
	}

	public void changeStatusFrame() {
		Panel changeStatusDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		changeStatusDisplay.add(new Label("User ID: "), c);

		c.gridx = 1;
		c.gridy = 0;
		TextField tfUserID = new TextField(30);
		changeStatusDisplay.add(tfUserID, c);

		c.insets = new Insets(0,0,0,0);
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 2;
		ButtonGroup bg = new ButtonGroup();
		Panel buttonPanel = new Panel();
		JRadioButton pending = new JRadioButton("Pending", true);
		buttonPanel.add(pending);
		bg.add(pending);
		JRadioButton approved = new JRadioButton("Approved");
		buttonPanel.add(approved);
		bg.add(approved);
		changeStatusDisplay.add(buttonPanel, c);

		c.insets = new Insets(10,0,0,10);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		Button btnChange = new Button("Change Status");
		btnChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				User found = uServices.findByID(tfUserID.getText());
				if (found == null) {
					JOptionPane.showMessageDialog(changeStatusDisplay, "No such User ID exists in our database..", "Warning!", JOptionPane.WARNING_MESSAGE);
					employeeServicesFrame();
					changeStatusDisplay.setVisible(false);
				} else {
					if(pending.isSelected()) {
						log.info("Changed to Pending status.");
						uServices.changeStatus(uServices.stringToInt(tfUserID.getText()), "Pending", user.getUserID());
						JOptionPane.showMessageDialog(changeStatusDisplay, "Status successfully changed.", "Message", JOptionPane.PLAIN_MESSAGE);
						employeeServicesFrame();
						changeStatusDisplay.setVisible(false);
					} else if(approved.isSelected()) {
						log.info("Changed to Approved status.");
						uServices.changeStatus(uServices.stringToInt(tfUserID.getText()), "Approved", user.getUserID());
						JOptionPane.showMessageDialog(changeStatusDisplay, "Status successfully changed.", "Message", JOptionPane.PLAIN_MESSAGE);
						employeeServicesFrame();
						changeStatusDisplay.setVisible(false);
					} else {
						log.warn("Something went horribly wrong.");
						employeeServicesFrame();
						changeStatusDisplay.setVisible(false);
					}
				}
			}
		});
		changeStatusDisplay.add(btnChange, c);

		c.insets = new Insets(10,10,0,0);
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 1;
		Button btnBack = new Button("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				employeeServicesFrame();
				changeStatusDisplay.setVisible(false);
			}
		});
		changeStatusDisplay.add(btnBack, c);

		add(changeStatusDisplay);
		setVisible(true);
	}

	public void changeStatusAdminFrame() {
		Panel changeStatusAdminDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		changeStatusAdminDisplay.add(new Label("User ID: "), c);

		c.gridx = 1;
		c.gridy = 0;
		TextField tfUserID = new TextField(30);
		changeStatusAdminDisplay.add(tfUserID, c);

		c.insets = new Insets(0,0,0,0);
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 2;
		ButtonGroup bg = new ButtonGroup();
		Panel buttonPanel = new Panel();
		JRadioButton pending = new JRadioButton("Pending", true);
		buttonPanel.add(pending);
		bg.add(pending);
		JRadioButton approved = new JRadioButton("Approved");
		buttonPanel.add(approved);
		bg.add(approved);
		JRadioButton deleted = new JRadioButton("Deleted");
		buttonPanel.add(deleted);
		bg.add(deleted);
		changeStatusAdminDisplay.add(buttonPanel, c);

		c.insets = new Insets(0,0,10,0);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		Button btnChange = new Button("Change Status");
		btnChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				User found = uServices.findByID(tfUserID.getText());
				if (found == null) {
					JOptionPane.showMessageDialog(changeStatusAdminDisplay, "No such User ID exists in our database..", "Warning!", JOptionPane.WARNING_MESSAGE);
					employeeServicesFrame();
					changeStatusAdminDisplay.setVisible(false);
				} else {
					if(pending.isSelected()) {
						log.info("Changed to Pending status.");
						uServices.changeStatus(uServices.stringToInt(tfUserID.getText()), "Pending", user.getUserID());
						JOptionPane.showMessageDialog(changeStatusAdminDisplay, "Status successfully changed.", "Message", JOptionPane.PLAIN_MESSAGE);
						employeeServicesFrame();
						changeStatusAdminDisplay.setVisible(false);
					} else if(approved.isSelected()) {
						log.info("Changed to Approved status.");
						uServices.changeStatus(uServices.stringToInt(tfUserID.getText()), "Approved", user.getUserID());
						JOptionPane.showMessageDialog(changeStatusAdminDisplay, "Status successfully changed.", "Message", JOptionPane.PLAIN_MESSAGE);
						employeeServicesFrame();
						changeStatusAdminDisplay.setVisible(false);
					} else if(deleted.isSelected()) {
						log.info("Changed to Deleted status.");
						uServices.changeStatus(uServices.stringToInt(tfUserID.getText()), "Deleted", user.getUserID());
						JOptionPane.showMessageDialog(changeStatusAdminDisplay, "Status successfully changed.", "Message", JOptionPane.PLAIN_MESSAGE);
						employeeServicesFrame();
						changeStatusAdminDisplay.setVisible(false);
					} else {
						log.warn("Something went horribly wrong.");
						employeeServicesFrame();
						changeStatusAdminDisplay.setVisible(false);
					}
				}
			}
		});
		changeStatusAdminDisplay.add(btnChange, c);

		c.insets = new Insets(0,0,0,0);
		c.gridx = 0;
		c.gridy = 3;
		Button btnBack = new Button("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				employeeServicesFrame();
				changeStatusAdminDisplay.setVisible(false);
			}
		});
		changeStatusAdminDisplay.add(btnBack, c);

		add(changeStatusAdminDisplay);
		setVisible(true);
	}

	public void accountServicesFrame() {
		if(user.getUserType() == 0) {
			log.info("Accessing adminAccountServices");
			Panel adminAccountServicesDisplay = new Panel(new GridBagLayout());
			adminAccountServicesDisplay.setVisible(true);
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth = 7;
			c.gridx = 0;
			c.gridy = 0;
			Label label = new Label("Welcome, " + user.getFirstName() + "! How may we serve you today?");
			label.setAlignment(Label.CENTER);
			adminAccountServicesDisplay.add(label, c);

			c.gridwidth = 1;
			c.insets = new Insets(10,0,0,10);
			c.gridx = 0;
			c.gridy = 1;
			Button btnViewAll = new Button("View All Accounts");
			btnViewAll.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					log.info("Viewing all accounts.");
					viewAllAccountsFrame();
					adminAccountServicesDisplay.setVisible(false);
				}
			});
			adminAccountServicesDisplay.add(btnViewAll, c);

			c.gridx = 1;
			c.gridy = 1;
			Button btnFindByOwner = new Button("Find Account by Owner");
			btnFindByOwner.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					log.info("Accessing User search by ID.");
					findByOwner();
					adminAccountServicesDisplay.setVisible(false);
				}
			});
			adminAccountServicesDisplay.add(btnFindByOwner, c);

			c.gridx = 2;
			c.gridy = 1;
			Button btnChangeStatus = new Button("Change Status");
			btnChangeStatus.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					log.info("Changing Status as Admin.");
					changeAccountStatusAdminFrame();
					adminAccountServicesDisplay.setVisible(false);
				}
			});
			adminAccountServicesDisplay.add(btnChangeStatus, c);
			
			c.gridx = 3;
			c.gridy = 1;
			Button btnWithdraw = new Button("Withdraw");
			btnWithdraw.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					log.info("Withdrawing as Admin.");
					withdrawFrame();
					adminAccountServicesDisplay.setVisible(false);
				}
			});
			adminAccountServicesDisplay.add(btnWithdraw, c);
			
			c.gridx = 4;
			c.gridy = 1;
			Button btnDeposit = new Button("Deposit");
			btnDeposit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					log.info("Depositing as Admin.");
					depositFrame();
					adminAccountServicesDisplay.setVisible(false);
				}
			});
			adminAccountServicesDisplay.add(btnDeposit, c);
			
			c.gridx = 5;
			c.gridy = 1;
			Button btnTransfer = new Button("Transfer");
			btnTransfer.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					log.info("Transferring as Admin.");
					transferFrame();
					adminAccountServicesDisplay.setVisible(false);
				}
			});
			adminAccountServicesDisplay.add(btnTransfer, c);

			c.insets = new Insets(10,10,0,0);
			c.gridx = 6;
			c.gridy = 1;
			Button btnPreviousMenu = new Button("Previous Menu");
			btnPreviousMenu.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					log.info("Returning to Services Menu.");
					servicesFrame();
					adminAccountServicesDisplay.setVisible(false);
				}
			});
			adminAccountServicesDisplay.add(btnPreviousMenu, c);

			add(adminAccountServicesDisplay);
			setVisible(true);
			
			
		} else if(user.getUserType() == 1) {
			log.info("Accessing employeeAccountServices");
			Panel employeeAccountServicesDisplay = new Panel(new GridBagLayout());
			employeeAccountServicesDisplay.setVisible(true);
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth = 4;
			c.gridx = 0;
			c.gridy = 0;
			Label label = new Label("Welcome, " + user.getFirstName() + "! How may we serve you today?");
			label.setAlignment(Label.CENTER);
			employeeAccountServicesDisplay.add(label, c);

			c.gridwidth = 1;
			c.insets = new Insets(10,0,0,10);
			c.gridx = 0;
			c.gridy = 1;
			Button btnViewAll = new Button("View All Accounts");
			btnViewAll.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					log.info("Viewing all accounts.");
					viewAllAccountsFrame();
					employeeAccountServicesDisplay.setVisible(false);
				}
			});
			employeeAccountServicesDisplay.add(btnViewAll, c);

			c.gridx = 1;
			c.gridy = 1;
			Button btnFindByOwner = new Button("Find Account by Owner");
			btnFindByOwner.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					log.info("Accessing User search by ID.");
					findByOwner();
					employeeAccountServicesDisplay.setVisible(false);
				}
			});
			employeeAccountServicesDisplay.add(btnFindByOwner, c);

			c.gridx = 2;
			c.gridy = 1;
			Button btnChangeStatus = new Button("Change Status");
			btnChangeStatus.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					log.info("Changing Status as an Employee.");
					changeAccountStatusFrame();
					employeeAccountServicesDisplay.setVisible(false);
				}
			});
			employeeAccountServicesDisplay.add(btnChangeStatus, c);

			c.insets = new Insets(10,10,0,0);
			c.gridx = 3;
			c.gridy = 1;
			Button btnPreviousMenu = new Button("Previous Menu");
			btnPreviousMenu.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					log.info("Returning to Services Menu.");
					servicesFrame();
					employeeAccountServicesDisplay.setVisible(false);
				}
			});
			employeeAccountServicesDisplay.add(btnPreviousMenu, c);

			add(employeeAccountServicesDisplay);
			setVisible(true);
			
			
		} else {
			Panel customerAccountServicesDisplay = new Panel(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			log.info("Accessing customerAccountServices");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth = 6;
			c.gridx = 0;
			c.gridy = 0;
			Label label = new Label("Welcome, " + user.getFirstName() + "! How may we serve you today?");
			label.setAlignment(Label.CENTER);
			customerAccountServicesDisplay.add(label, c);

			c.gridwidth = 1;
			c.insets = new Insets(10,0,0,10);
			c.gridx = 0;
			c.gridy = 1;
			Button btnNewAccount = new Button("Open New Account");
			btnNewAccount.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					log.info("Opening new account.");
					openAccountFrame();
					customerAccountServicesDisplay.setVisible(false);
				}
			});
			customerAccountServicesDisplay.add(btnNewAccount, c);

			c.gridx = 1;
			c.gridy = 1;
			Button btnNewJointAccount = new Button("Open New Joint Account");
			btnNewJointAccount.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					log.info("Opening new joint account.");
					openJointAccountFrame();
					customerAccountServicesDisplay.setVisible(false);
				}
			});
			customerAccountServicesDisplay.add(btnNewJointAccount, c);
			
			c.gridx = 2;
			c.gridy = 1;
			Button btnViewOwned = new Button("View Owned Accounts");
			btnViewOwned.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					log.info("Opening new joint account.");
					openJointAccountFrame();
					customerAccountServicesDisplay.setVisible(false);
				}
			});
			customerAccountServicesDisplay.add(btnViewOwned, c);
			
			c.gridx = 3;
			c.gridy = 1;
			Button btnWithdraw = new Button("Withdraw");
			btnWithdraw.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					log.info("Calling withdraw...");
					withdrawFrame();
					customerAccountServicesDisplay.setVisible(false);
				}
			});
			customerAccountServicesDisplay.add(btnWithdraw, c);
			
			add(customerAccountServicesDisplay);
			setVisible(true);
			
			c.gridx = 4;
			c.gridy = 1;
			Button btnDeposit = new Button("Deposit");
			btnDeposit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					log.info("Calling deposit...");
					depositFrame();
					customerAccountServicesDisplay.setVisible(false);
				}
			});
			customerAccountServicesDisplay.add(btnDeposit, c);
			
			c.insets = new Insets(10,10,0,0);
			c.gridx = 5;
			c.gridy = 1;
			Button btnTransfer = new Button("Transfer");
			btnTransfer.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					log.info("Calling transfer...");
					transferFrame();
					customerAccountServicesDisplay.setVisible(false);
				}
			});
			customerAccountServicesDisplay.add(btnTransfer, c);
			
			add(customerAccountServicesDisplay);
			setVisible(true);
		}
	}
	
	public void viewAllAccountsFrame() {
		Panel viewAllAccountsDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,0,10,10);
		c.gridx = 0;
		c.gridy = 0;
		Label label = new Label("Here is a list of all accounts in our database: ");
		label.setAlignment(Label.CENTER);
		viewAllAccountsDisplay.add(label, c);

		c.insets = new Insets(10,10,10,0);
		c.gridx = 1;
		c.gridy = 0;
		Button btnPreviousMenu = new Button("Previous Menu");
		btnPreviousMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				log.info("Returning to Account Services Menu.");
				accountServicesFrame();
				viewAllAccountsDisplay.setVisible(false);
			}
		});
		viewAllAccountsDisplay.add(btnPreviousMenu, c);

		JTable accountsTable = new JTable(aServices.convertToAccountListArray(aServices.displayAccounts()), aServices.getColumnNames());
		accountsTable.setFillsViewportHeight(true);
		accountsTable.setRowHeight(24);

		JScrollPane scroll = new JScrollPane(accountsTable);
		scroll.setSize(new Dimension(900, 450));
		scroll.setMinimumSize(new Dimension(900, 450));
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 1.0;
		viewAllAccountsDisplay.add(scroll, c);
		add(viewAllAccountsDisplay);
		setVisible(true);
	}
	
	public void viewOwnedAccountsFrame() {
		Panel viewOwnedAccountsDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,0,10,10);
		c.gridx = 0;
		c.gridy = 0;
		Label label = new Label("Here is a list of all accounts you own: ");
		label.setAlignment(Label.CENTER);
		viewOwnedAccountsDisplay.add(label, c);

		c.insets = new Insets(10,10,10,0);
		c.gridx = 1;
		c.gridy = 0;
		Button btnPreviousMenu = new Button("Previous Menu");
		btnPreviousMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				log.info("Returning to Account Services Menu.");
				accountServicesFrame();
				viewOwnedAccountsDisplay.setVisible(false);
			}
		});
		viewOwnedAccountsDisplay.add(btnPreviousMenu, c);

		JTable accountsTable = new JTable(aServices.convertToAccountListArray(aServices.findByOwner(user.getUserID())), aServices.getColumnNames());
		accountsTable.setFillsViewportHeight(true);
		accountsTable.setRowHeight(24);

		JScrollPane scroll = new JScrollPane(accountsTable);
		scroll.setSize(new Dimension(900, 450));
		scroll.setMinimumSize(new Dimension(900, 450));
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 1.0;
		viewOwnedAccountsDisplay.add(scroll, c);
		add(viewOwnedAccountsDisplay);
		setVisible(true);
	}
	
	public void findByOwner() {
		Panel findAccountDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		findAccountDisplay.add(new Label("User ID: "), c);

		c.gridx = 1;
		c.gridy = 0;
		TextField tfUserID = new TextField(30);
		findAccountDisplay.add(tfUserID, c);

		c.gridwidth = 2;
		c.insets = new Insets(10,0,0,0);
		c.gridx = 0;
		c.gridy = 1;
		Button btnSearch = new Button("Search");
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Panel viewByOwnerDisplay = new Panel(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(10,0,10,10);
				c.gridx = 0;
				c.gridy = 0;
				Label label = new Label("Here is a list of all accounts belonging to that User ID: ");
				label.setAlignment(Label.CENTER);
				viewByOwnerDisplay.add(label, c);

				c.insets = new Insets(10,10,10,0);
				c.gridx = 1;
				c.gridy = 0;
				Button btnPreviousMenu = new Button("Previous Menu");
				btnPreviousMenu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						log.info("Returning to Account Services Menu.");
						accountServicesFrame();
						viewByOwnerDisplay.setVisible(false);
					}
				});
				viewByOwnerDisplay.add(btnPreviousMenu, c);

				JTable accountsTable = new JTable(aServices.convertToAccountListArray(aServices.findByOwner(aServices.stringToInt(tfUserID.getText()))), aServices.getColumnNames());
				accountsTable.setFillsViewportHeight(true);
				accountsTable.setRowHeight(24);

				JScrollPane scroll = new JScrollPane(accountsTable);
				scroll.setSize(new Dimension(900, 450));
				scroll.setMinimumSize(new Dimension(900, 450));
				c.insets = new Insets(0,0,0,0);
				c.gridwidth = 2;
				c.gridx = 0;
				c.gridy = 1;
				c.weighty = 1.0;
				viewByOwnerDisplay.add(scroll, c);
				add(viewByOwnerDisplay);
				setVisible(true);
			}
		});
		findAccountDisplay.add(btnSearch, c);

		c.gridx = 0;
		c.gridy = 2;
		Button btnBack = new Button("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				accountServicesFrame();
				findAccountDisplay.setVisible(false);
			}
		});
		findAccountDisplay.add(btnBack, c);

		add(findAccountDisplay);
		setVisible(true);
	}
	
	public void openAccountFrame() {
		Panel openAccountDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;
		Label label = new Label("Please enter account information.");
		label.setAlignment(Label.CENTER);
		openAccountDisplay.add(label, c);

		c.insets = new Insets(5,5,5,5);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		openAccountDisplay.add(new Label("Account Type: "), c);

		c.insets = new Insets(0,0,0,0);
		c.gridx = 1;
		c.gridy = 1;
		ButtonGroup bg = new ButtonGroup();
		Panel buttonPanel = new Panel();
		JRadioButton checking = new JRadioButton("Checking", true);
		buttonPanel.add(checking);
		bg.add(checking);
		JRadioButton savings = new JRadioButton("Savings");
		buttonPanel.add(savings);
		bg.add(savings);
		openAccountDisplay.add(buttonPanel, c);

		c.gridwidth = 3;
		c.insets = new Insets(10,0,0,0);
		c.gridx = 0;
		c.gridy = 3;
		Button btnRegister = new Button("Register");
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if(checking.isSelected()) {
					log.info("New checking account selected.");
					if(aServices.openAccount(true, user.getUserID(), user.getUserID())) {
						log.info("New checking account created.");
						JOptionPane.showMessageDialog(openAccountDisplay, "Your new Checking account must be approved before it can be used. Please wait patiently for approval.", "Message", JOptionPane.PLAIN_MESSAGE);
						accountServicesFrame();
						openAccountDisplay.setVisible(false);
					} else {
						log.warn("New checking account NOT created.");
						JOptionPane.showMessageDialog(openAccountDisplay, "Your new Checking account was not created. Please contact an administrator for details.", "Message", JOptionPane.PLAIN_MESSAGE);
						accountServicesFrame();
						openAccountDisplay.setVisible(false);
					}
				} else if(savings.isSelected()) {
					log.info("New savings account selected.");
					if(aServices.openAccount(false, user.getUserID(), user.getUserID())) {
						log.info("New savings account created.");
						JOptionPane.showMessageDialog(openAccountDisplay, "Your new Savings account must be approved before it can be used. Please wait patiently for approval.", "Message", JOptionPane.PLAIN_MESSAGE);
						accountServicesFrame();
						openAccountDisplay.setVisible(false);
					} else {
						log.warn("New savings account NOT created.");
						JOptionPane.showMessageDialog(openAccountDisplay, "Your new Savings account was not created. Please contact an administrator for details.", "Message", JOptionPane.PLAIN_MESSAGE);
						accountServicesFrame();
						openAccountDisplay.setVisible(false);
					}
				} else {
					log.warn("Something went horribly wrong.");
					accountServicesFrame();
					openAccountDisplay.setVisible(false);
				}
			}
		});
		openAccountDisplay.add(btnRegister, c);

		c.insets = new Insets(10,0,0,0);
		c.gridx = 0;
		c.gridy = 4;
		Button btnBack = new Button("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				accountServicesFrame();
				openAccountDisplay.setVisible(false);
			}
		});
		openAccountDisplay.add(btnBack, c);

		add(openAccountDisplay);
		setVisible(true);
	}
	
	public void openJointAccountFrame() {
		Panel openJointAccountDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 0;
		Label label = new Label("Please enter account information.");
		label.setAlignment(Label.CENTER);
		openJointAccountDisplay.add(label, c);

		
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		openJointAccountDisplay.add(new Label("Co-owner: "), c);
		
		c.gridx = 1;
		c.gridy = 1;
		TextField tfOwner = new TextField(30);
		openJointAccountDisplay.add(tfOwner, c);
		
		
		c.insets = new Insets(5,5,5,5);
		c.gridx = 2;
		c.gridy = 1;
		openJointAccountDisplay.add(new Label("Account Type: "), c);

		c.insets = new Insets(0,0,0,0);
		c.gridx = 3;
		c.gridy = 1;
		ButtonGroup bg = new ButtonGroup();
		Panel buttonPanel = new Panel();
		JRadioButton checking = new JRadioButton("Checking", true);
		buttonPanel.add(checking);
		bg.add(checking);
		JRadioButton savings = new JRadioButton("Savings");
		buttonPanel.add(savings);
		bg.add(savings);
		openJointAccountDisplay.add(buttonPanel, c);

		c.gridwidth = 2;
		c.insets = new Insets(10,0,0,10);
		c.gridx = 0;
		c.gridy = 2;
		Button btnRegister = new Button("Register");
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				int[] owners = new int[] {user.getUserID(), aServices.stringToInt(tfOwner.getText())};
				if(checking.isSelected()) {
					log.info("New joint checking account selected.");
					if(aServices.openAccount(true, owners, user.getUserID())) {
						log.info("New joint checking account created.");
						JOptionPane.showMessageDialog(openJointAccountDisplay, "Your new join Checking account must be approved before it can be used. Please wait patiently for approval.", "Message", JOptionPane.PLAIN_MESSAGE);
						accountServicesFrame();
						openJointAccountDisplay.setVisible(false);
					} else {
						log.warn("New joint checking account NOT created.");
						JOptionPane.showMessageDialog(openJointAccountDisplay, "Your new joint Checking account was not created. Please contact an administrator for details.", "Message", JOptionPane.PLAIN_MESSAGE);
						accountServicesFrame();
						openJointAccountDisplay.setVisible(false);
					}
				} else if(savings.isSelected()) {
					log.info("New joint savings account selected.");
					if(aServices.openAccount(false, owners, user.getUserID())) {
						log.info("New joint savings account created.");
						JOptionPane.showMessageDialog(openJointAccountDisplay, "Your new joint Savings account must be approved before it can be used. Please wait patiently for approval.", "Message", JOptionPane.PLAIN_MESSAGE);
						accountServicesFrame();
						openJointAccountDisplay.setVisible(false);
					} else {
						log.warn("New joint savings account NOT created.");
						JOptionPane.showMessageDialog(openJointAccountDisplay, "Your new joint Savings account was not created. Please contact an administrator for details.", "Message", JOptionPane.PLAIN_MESSAGE);
						accountServicesFrame();
						openJointAccountDisplay.setVisible(false);
					}
				} else {
					log.warn("Something went horribly wrong.");
					accountServicesFrame();
					openJointAccountDisplay.setVisible(false);
				}
			}
		});
		openJointAccountDisplay.add(btnRegister, c);

		c.insets = new Insets(10,10,0,0);
		c.gridx = 2;
		c.gridy = 2;
		Button btnBack = new Button("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				accountServicesFrame();
				openJointAccountDisplay.setVisible(false);
			}
		});
		openJointAccountDisplay.add(btnBack, c);

		add(openJointAccountDisplay);
		setVisible(true);
	}

	public void changeAccountStatusAdminFrame() {
		Panel changeAccountStatusAdminDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		changeAccountStatusAdminDisplay.add(new Label("Account ID: "), c);

		c.gridx = 1;
		c.gridy = 0;
		TextField tfUserID = new TextField(30);
		changeAccountStatusAdminDisplay.add(tfUserID, c);

		c.insets = new Insets(0,0,0,0);
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 2;
		ButtonGroup bg = new ButtonGroup();
		Panel buttonPanel = new Panel();
		JRadioButton pending = new JRadioButton("Pending", true);
		buttonPanel.add(pending);
		bg.add(pending);
		JRadioButton approved = new JRadioButton("Approved");
		buttonPanel.add(approved);
		bg.add(approved);
		JRadioButton closed = new JRadioButton("Closed");
		buttonPanel.add(closed);
		bg.add(closed);
		changeAccountStatusAdminDisplay.add(buttonPanel, c);

		c.insets = new Insets(10,0,0,10);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		Button btnChange = new Button("Change Status");
		btnChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				int accountID = aServices.stringToInt(tfUserID.getText());
				Account found = aServices.findByID(accountID);
				if (found == null) {
					JOptionPane.showMessageDialog(changeAccountStatusAdminDisplay, "No such Account ID exists in our database.", "Warning!", JOptionPane.WARNING_MESSAGE);
					accountServicesFrame();
					changeAccountStatusAdminDisplay.setVisible(false);
				} else {
					if(pending.isSelected()) {
						log.info("Changed to Pending status.");
						uServices.changeStatus(accountID, "Pending", user.getUserID());
						JOptionPane.showMessageDialog(changeAccountStatusAdminDisplay, "Status successfully changed.", "Message", JOptionPane.PLAIN_MESSAGE);
						accountServicesFrame();
						changeAccountStatusAdminDisplay.setVisible(false);
					} else if(approved.isSelected()) {
						log.info("Changed to Approved status.");
						uServices.changeStatus(accountID, "Approved", user.getUserID());
						JOptionPane.showMessageDialog(changeAccountStatusAdminDisplay, "Status successfully changed.", "Message", JOptionPane.PLAIN_MESSAGE);
						accountServicesFrame();
						changeAccountStatusAdminDisplay.setVisible(false);
					} else if(closed.isSelected()) {
						log.info("Changed to Closed status.");
						uServices.changeStatus(accountID, "Deleted", user.getUserID());
						JOptionPane.showMessageDialog(changeAccountStatusAdminDisplay, "Status successfully changed.", "Message", JOptionPane.PLAIN_MESSAGE);
						accountServicesFrame();
						changeAccountStatusAdminDisplay.setVisible(false);
					} else {
						log.warn("Something went horribly wrong.");
						accountServicesFrame();
						changeAccountStatusAdminDisplay.setVisible(false);
					}
				}
			}
		});
		changeAccountStatusAdminDisplay.add(btnChange, c);

		c.insets = new Insets(10,10,0,0);
		c.gridx = 2;
		c.gridy = 1;
		Button btnBack = new Button("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				accountServicesFrame();
				changeAccountStatusAdminDisplay.setVisible(false);
			}
		});
		changeAccountStatusAdminDisplay.add(btnBack, c);

		add(changeAccountStatusAdminDisplay);
		setVisible(true);
	}
	
	public void changeAccountStatusFrame() {
		Panel changeAccountStatusDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		changeAccountStatusDisplay.add(new Label("Account ID: "), c);

		c.gridx = 1;
		c.gridy = 0;
		TextField tfUserID = new TextField(30);
		changeAccountStatusDisplay.add(tfUserID, c);

		c.insets = new Insets(0,0,0,0);
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 2;
		ButtonGroup bg = new ButtonGroup();
		Panel buttonPanel = new Panel();
		JRadioButton pending = new JRadioButton("Pending", true);
		buttonPanel.add(pending);
		bg.add(pending);
		JRadioButton approved = new JRadioButton("Approved");
		buttonPanel.add(approved);
		bg.add(approved);
		changeAccountStatusDisplay.add(buttonPanel, c);

		c.insets = new Insets(10,0,0,10);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		Button btnChange = new Button("Change Status");
		btnChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				int accountID = aServices.stringToInt(tfUserID.getText());
				Account found = aServices.findByID(accountID);
				if (found == null) {
					JOptionPane.showMessageDialog(changeAccountStatusDisplay, "No such Account ID exists in our database.", "Warning!", JOptionPane.WARNING_MESSAGE);
					accountServicesFrame();
					changeAccountStatusDisplay.setVisible(false);
				} else {
					if(pending.isSelected()) {
						log.info("Changed to Pending status.");
						uServices.changeStatus(accountID, "Pending", user.getUserID());
						JOptionPane.showMessageDialog(changeAccountStatusDisplay, "Status successfully changed.", "Message", JOptionPane.PLAIN_MESSAGE);
						accountServicesFrame();
						changeAccountStatusDisplay.setVisible(false);
					} else if(approved.isSelected()) {
						log.info("Changed to Approved status.");
						uServices.changeStatus(accountID, "Approved", user.getUserID());
						JOptionPane.showMessageDialog(changeAccountStatusDisplay, "Status successfully changed.", "Message", JOptionPane.PLAIN_MESSAGE);
						accountServicesFrame();
						changeAccountStatusDisplay.setVisible(false);
					} else {
						log.warn("Something went horribly wrong.");
						accountServicesFrame();
						changeAccountStatusDisplay.setVisible(false);
					}
				}
			}
		});
		changeAccountStatusDisplay.add(btnChange, c);

		c.insets = new Insets(10,10,0,0);
		c.gridx = 2;
		c.gridy = 1;
		Button btnBack = new Button("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				accountServicesFrame();
				changeAccountStatusDisplay.setVisible(false);
			}
		});
		changeAccountStatusDisplay.add(btnBack, c);

		add(changeAccountStatusDisplay);
		setVisible(true);
	}
	
	public void withdrawFrame() {
		Panel withdrawDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		withdrawDisplay.add(new Label("Account ID: "), c);

		c.gridx = 1;
		c.gridy = 0;
		TextField tfAccountID = new TextField(30);
		withdrawDisplay.add(tfAccountID, c);
		
		c.gridx = 2;
		c.gridy = 0;
		withdrawDisplay.add(new Label("Amount: "), c);

		c.gridx = 3;
		c.gridy = 0;
		TextField tfAmount = new TextField(30);
		withdrawDisplay.add(tfAmount, c);
		
		c.insets = new Insets(10,0,0,10);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		Button btnWithdraw = new Button("Withdraw");
		btnWithdraw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				double amount = aServices.stringToDouble(tfAmount.getText());
				account = aServices.findByID(aServices.stringToInt(tfAccountID.getText()));
				if (account == null) {
					JOptionPane.showMessageDialog(withdrawDisplay, "No such Account ID exists in our database.", "Warning!", JOptionPane.WARNING_MESSAGE);
					withdrawDisplay.setVisible(false);
					withdrawFrame();
				} else if(user.getUserType() == 2 && !aServices.isOwner(aServices.stringToInt(tfAccountID.getText()), user.getUserID())) {
					JOptionPane.showMessageDialog(withdrawDisplay, "That account doesn't belong to you!", "Warning!", JOptionPane.WARNING_MESSAGE);
					withdrawDisplay.setVisible(false);
					withdrawFrame();
				}
				if (amount < 0) {
					log.warn("Negative amount entered for withdraw.");
					JOptionPane.showMessageDialog(withdrawDisplay, "Please enter a positive amount.", "Warning!", JOptionPane.WARNING_MESSAGE);
					withdrawDisplay.setVisible(false);
					withdrawFrame();
				} else {
					log.info("Attempting to withdraw...");
					if(aServices.withdraw(account.getAccountID(), amount, user.getUserID())) {
						log.info("Withdraw successful!");
						JOptionPane.showMessageDialog(withdrawDisplay, "Amount successfully withdrawn.", "Message", JOptionPane.PLAIN_MESSAGE);
					} else {
						log.warn("Withdraw unsuccessful.");
						JOptionPane.showMessageDialog(withdrawDisplay, "Withdrawl was unsuccessful; please contact an administrator.", "Warning!", JOptionPane.WARNING_MESSAGE);
					}
					withdrawDisplay.setVisible(false);
					accountServicesFrame();
				}
			}
		});
		withdrawDisplay.add(btnWithdraw, c);

		c.insets = new Insets(10,10,0,0);
		c.gridx = 2;
		c.gridy = 1;
		Button btnBack = new Button("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				withdrawDisplay.setVisible(false);
				accountServicesFrame();
			}
		});
		withdrawDisplay.add(btnBack, c);
		
		add(withdrawDisplay);
		setVisible(true);
	}
	
	public void depositFrame() {
		Panel depositDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		depositDisplay.add(new Label("Account ID: "), c);

		c.gridx = 1;
		c.gridy = 0;
		TextField tfAccountID = new TextField(30);
		depositDisplay.add(tfAccountID, c);
		
		c.gridx = 2;
		c.gridy = 0;
		depositDisplay.add(new Label("Amount: "), c);

		c.gridx = 3;
		c.gridy = 0;
		TextField tfAmount = new TextField(30);
		depositDisplay.add(tfAmount, c);
		
		c.insets = new Insets(10,0,0,10);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		Button btnDeposit = new Button("Deposit");
		btnDeposit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				double amount = aServices.stringToDouble(tfAmount.getText());
				account = aServices.findByID(aServices.stringToInt(tfAccountID.getText()));
				if (account == null) {
					JOptionPane.showMessageDialog(depositDisplay, "No such Account ID exists in our database.", "Warning!", JOptionPane.WARNING_MESSAGE);
					depositDisplay.setVisible(false);
					depositFrame();
				} else if(user.getUserType() == 2 && !aServices.isOwner(aServices.stringToInt(tfAccountID.getText()), user.getUserID())) {
					JOptionPane.showMessageDialog(depositDisplay, "That account doesn't belong to you!", "Warning!", JOptionPane.WARNING_MESSAGE);
					depositDisplay.setVisible(false);
					depositFrame();
				}
				if (amount < 0) {
					log.warn("Negative amount entered for deposit.");
					JOptionPane.showMessageDialog(depositDisplay, "Please enter a positive amount.", "Warning!", JOptionPane.WARNING_MESSAGE);
					depositDisplay.setVisible(false);
					depositFrame();
				} else {
					log.info("Attempting to deposit...");
					if(aServices.deposit(account.getAccountID(), amount, user.getUserID())) {
						log.info("Deposit successful!");
						JOptionPane.showMessageDialog(depositDisplay, "Amount successfully deposited.", "Message", JOptionPane.PLAIN_MESSAGE);
					} else {
						log.warn("Deposit unsuccessful.");
						JOptionPane.showMessageDialog(depositDisplay, "Deposit was unsuccessful; please contact an administrator.", "Warning!", JOptionPane.WARNING_MESSAGE);
					}
					depositDisplay.setVisible(false);
					depositFrame();
				}
			}
		});
		depositDisplay.add(btnDeposit, c);

		c.insets = new Insets(10,10,0,0);
		c.gridx = 2;
		c.gridy = 1;
		Button btnBack = new Button("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				accountServicesFrame();
				depositDisplay.setVisible(false);
			}
		});
		depositDisplay.add(btnBack, c);
		
		add(depositDisplay);
		setVisible(true);
	}
	
	public void transferFrame() {
		Panel transferDisplay = new Panel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		transferDisplay.add(new Label("Giving Account ID: "), c);

		c.gridx = 1;
		c.gridy = 0;
		TextField tfGivingID = new TextField(20);
		transferDisplay.add(tfGivingID, c);

		c.gridx = 2;
		c.gridy = 0;
		transferDisplay.add(new Label("Receiving Account ID: "), c);

		c.gridx = 3;
		c.gridy = 0;
		TextField tfReceivingID = new TextField(20);
		transferDisplay.add(tfReceivingID, c);
		
		c.gridx = 4;
		c.gridy = 0;
		transferDisplay.add(new Label("Amount: "), c);

		c.gridx = 5;
		c.gridy = 0;
		TextField tfAmount = new TextField(20);
		transferDisplay.add(tfAmount, c);
		
		c.insets = new Insets(10,0,0,10);
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		Button btnChange = new Button("Change Status");
		btnChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Account giving = aServices.findByID(aServices.stringToInt(tfGivingID.getText()));
				Account receiving = aServices.findByID(aServices.stringToInt(tfReceivingID.getText()));
				if (giving == null || receiving == null) {
					JOptionPane.showMessageDialog(transferDisplay, "No such Account ID exists in our database.", "Warning!", JOptionPane.WARNING_MESSAGE);
					accountServicesFrame();
					transferDisplay.setVisible(false);
				} else if(user.getUserType() == 2 && !aServices.isOwner(aServices.stringToInt(tfGivingID.getText()), user.getUserID())) {
					JOptionPane.showMessageDialog(transferDisplay, "That account doesn't belong to you!", "Warning!", JOptionPane.WARNING_MESSAGE);
					transferDisplay.setVisible(false);
					transferFrame();
				} else {
					log.info("Transferring amount...");
					aServices.transfer(giving.getAccountID(), receiving.getAccountID(), aServices.stringToDouble(tfAmount.getText()), user.getUserID());
					JOptionPane.showMessageDialog(transferDisplay, "Amount successfully transferred.", "Message", JOptionPane.PLAIN_MESSAGE);
					transferDisplay.setVisible(false);
					accountServicesFrame();
				}
			}
		});
		transferDisplay.add(btnChange, c);

		c.insets = new Insets(10,10,0,0);
		c.gridx = 3;
		c.gridy = 1;
		Button btnBack = new Button("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				accountServicesFrame();
				transferDisplay.setVisible(false);
			}
		});
		transferDisplay.add(btnBack, c);
		
		add(transferDisplay);
		setVisible(true);
	}
}