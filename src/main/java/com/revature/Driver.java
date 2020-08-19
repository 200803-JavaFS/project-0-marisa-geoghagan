package com.revature;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Driver {
	private static final Logger log = LogManager.getLogger(Driver.class);
	
	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.setLayout(new FlowLayout());
		gui.setTitle("Java Bank");
		gui.setSize(1000, 500);
		gui.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				log.info("Exiting the program.");
				System.exit(0);
			}
		});
		gui.setLocationRelativeTo(null);
		gui.setVisible(true);
		gui.topMenu();
	}
}
