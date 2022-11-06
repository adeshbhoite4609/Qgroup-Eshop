package com.velocity.qmart;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Admin {

	// this method is used to login the admin in system with credentials
	protected void adminLogin() throws SQLException {
		Scanner sc = new Scanner(System.in);
		// get the login credentials from the user
		System.out.println("Enter Admin ID");
		String id = sc.next();
		System.out.println("Enter Password");
		String Password = sc.next();
		try {
			// load the properties file from system
	FileInputStream fis = new FileInputStream("C:\\Users\\lenovo\\OneDrive\\Desktop\\properties.txt");
			Properties p = new Properties();
			p.load(fis);
			// retrieve id and password from properties file
			String uid = p.getProperty("id");
			String pass = p.getProperty("password");
			System.out.println(uid + "    " + pass);
			// comparing the entered details and details from properties file
			if (id.equals(uid) && Password.equals(pass)) {
				Admin ad = new Admin();
				try {
					// if details matched then call admin specific private method in this class
					ad.adminAccess();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("Incorrect id or password.. please try later");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void adminAccess() throws InterruptedException, SQLException {
		System.out.println("Hello Admin, welcome back........");
		boolean b = true;
		Database obj = new Database();
		while (b) {
			// display the admin specific menu
System.out.println("Whats in your mind\n1.Check qty of products\n2.See registered user list on portal\n3.Check user History\n4.logout");
Scanner sc = new Scanner(System.in);
			int a = sc.nextInt();
			switch (a) {
			case 1:
				// calling the method from dbOps
				obj.adminProductList();
				break;
			case 2:
				// calling the method from dbOps
				obj.adminUserList();
				break;
			case 4:
				System.out.println("Logging you out......");
				b = false;
				Thread.sleep(1000);
				System.out.println("Successfully logged out");
				Application app = new Application();
				System.out.println("     ");
				app.menu();
				b=false;
				break;
			}
		}

	}

}

