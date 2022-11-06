package com.velocity.qmart;


import java.sql.SQLException;
import java.util.Scanner;

public class Application extends Admin {
	static {
		System.out.println("Welcome to the Qmart");
	}

	// This is main entry point of program, where main menu of actions is written.
	public static void main(String[] args) throws SQLException, InterruptedException {
		Application app = new Application();
		// calling the menu method
		app.menu();
	}

	public void menu() throws SQLException, InterruptedException {
		System.out.println("Select your actions");
		Scanner sc = new Scanner(System.in);
		boolean b = true;
		while (b) {
			System.out.println("1.Userlogin\n2.Create new account\n3.Admin Login\n4.exit");
			int selection = sc.nextInt();
			NewUser user = new NewUser();
			ExistingLogin member = new ExistingLogin();
			Application app = new Application();

			switch (selection) {
			case 1:
				member.login();
				break;

			case 2:
				user.signUp();
				break;

			case 3:
				app.adminLogin();
				break;

			case 4:
				b = false;
				break;

			default:
				System.out.println("Wrong selection,try again....");
				break;

			}

		}

	}
}
