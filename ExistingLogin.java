package com.velocity.qmart;


import java.sql.SQLException;
import java.util.Scanner;

public class ExistingLogin {

//This class and particular method is defined for the registered users to enter in system.
	public void login() throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		Database obj = new Database();
		Password pw = new Password();
		System.out.println("Enter username");
		String name = sc.nextLine();
		System.out.println("Enter Password");
		String password = sc.next();
		try {
			// This will find the password in dB for entered username, password is returned
			// from method userLogin() in dbOps class
			String a = obj.userLogin(name);
			// converting hex password in the String again for matching
			String match = pw.convertHexToString(a);
			// checking if entered password and password on dB are equal or not
			if (match.equals(password)) {
				System.out.println("Login Successful");
				System.out.println("You can start shopping now");
				System.out.println("  ");
				boolean c = true;
				Application app = new Application();
				while (c) {
					// if passwords are matched user will login and see the portal menu for user.
					System.out.println("Select your action");
					System.out.println("1.Start shopping\n2.See kart\n3.Delete my account\n4.logout");
					int b = sc.nextInt();
					Shopping sp = new Shopping();
					switch (b) {
					case 1:
						// if user wants to start shopping this will show the items available on portal
						// to user
						sp.showList(name);
						break;
					case 2:
						// This will show items added into the kart by user
						sp.showKart(name);
						// if user wants to see the total price of the kart, then on the willingness
						// this method will be called.
						System.out.println("Do you want to see total value of your kart? If yes press 1");
						int d = sc.nextInt();
						if (d == 1) {
							// see method totalValue() in dbOPs class
							sp.totalValue(name);
						}
						break;
					case 3:
						System.out.println("Enter Password");
						String password1 = sc.next();
						String a1 = obj.userLogin(name);
						// converting hex password in the String again for matching
						String match1 = pw.convertHexToString(a);
						// checking if entered password and password on dB are equal or not
						if (match1.equals(password)) {
							obj.deleteUser(name);
						}
						app.menu();
						break;

					case 4:
						System.out.println("Logging you out......");
						Thread.sleep(1000);
						System.out.println("Successfully logged out");
						System.out.println("      ");
						app.menu();
						c=false;
						break;
					}

				}

			} else {
				System.out.println("Wrong login credentials, please try again...");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
