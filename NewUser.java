package com.velocity.qmart;


import java.util.Scanner;

//required fields for the new user are declared here.
public class NewUser {
	String name;
	long mobileNo;
	String emailID;
	String password;
	String userName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public NewUser(String name, long mobileNo, String emailID, String password, String userName) {
		super();
		this.name = name;
		this.mobileNo = mobileNo;
		this.emailID = emailID;
		this.password = password;
		this.userName = userName;
	}

	public NewUser() {
		super();
	}

	// This process is for registering new user to our portal.
	public void signUp() {
		Scanner sc = new Scanner(System.in);
		Database obj = new Database();
		System.out.println("Lets start creating your account, please enter following details");
		System.out.println("Enter your Name");
		String name = sc.nextLine();
		System.out.println("Enter your Mobile No");
		long mobileNo = sc.nextLong();
		String a = sc.nextLine();
		System.out.println("Enter your EmailID");
		String emailID = sc.next();
		System.out.println("Select your unique userName");
		String userName = sc.next();
		boolean b = true;
		while (b) {
			System.out.println("Select password");
			String password1 = sc.next();
			System.out.println("Re-enter password");
			String password2 = sc.next();
			if (password1.equals(password2)) {
				Password g = new Password();
				// converting string into hex for saving onto database, refer method is Password
				// class
				String password = g.convertStringToHex(password2);
				NewUser user = new NewUser(name, mobileNo, emailID, password, userName);
				// Inserting object of new user to dataBase where all the details will be
				// stored.refer method InsertUserToDB() method from dbOPs class.
				obj.InsertUserToDB(user);
				System.out.println("Account created successfully...");
				System.out.println("Now login freshly to continue shopping");
				System.out.println(" ");
				break;
			} else {
				System.out.println("Wrong password input, please try again..");
			}
		}
	}

}

