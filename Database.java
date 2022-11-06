package com.velocity.qmart;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Database {
//This method is to insert the new user on the dB called from newUser class
	public static boolean InsertUserToDB(NewUser user) {
		boolean f = false;
		try {
			// jdbc code to store value in database...
			ConnectionProvider obj = new ConnectionProvider();

			Connection con = obj.createC();
			// create querry, give table name in database and column name, in values put '?'
			// for dynamic querry
			String q = "Insert into userlist (name,mobileNo,Password,userName,mailID) values(?,?,?,?,?)";

			// prepared statement
			PreparedStatement pstmt = con.prepareStatement(q);

			// set the values of parameters
			pstmt.setString(1, user.getName());
			pstmt.setLong(2, user.getMobileNo());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getUserName());
			pstmt.setString(5, user.getEmailID());

			// execute
			pstmt.executeUpdate();
			f = true;
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	public static boolean deleteUser(String userName) {

		boolean f = false;
		try {

			Connection con = ConnectionProvider.createC();
			// create querry, gibe table name in database and column name, in values put '?'
			// for dynamic querry
			String q = "Delete from userlist where userName=?";

			// prepared statement
			PreparedStatement pstmt = con.prepareStatement(q);

			// set the values of parameters
			pstmt.setString(1, userName);

			// execute
			pstmt.executeUpdate();
			f = true;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Account deleted successfully");
		return f;
	}

//for userLogin, returns password from dB for particular userName, called from existingLogin class
	public static String userLogin(String userName) throws SQLException {
		ConnectionProvider obj = new ConnectionProvider();
		// create connection
		Connection con = obj.createC();
		String Password = null;
		// query
		String q = "select * from userlist where userName=? ";
		// prep statement
		PreparedStatement pstmt = con.prepareStatement(q);
		// set parameter of userename in where clause
		pstmt.setString(1, userName);
		pstmt.executeQuery();
		// get the password from dB
		ResultSet set = pstmt.executeQuery();
		while (set.next()) {
			Password = set.getString(4);
		}
		// return the password
		return Password;
	}

//this method shows the available product list to users 	
	public static boolean showProductList() {
		boolean f = false;
		try {
			// jdbc code to store value in database...
			ConnectionProvider obj = new ConnectionProvider();
			Connection con = obj.createC();
			// create query
			String q = "Select description,price,name,srNo from productlist;";
			// create statement
			Statement stmt = con.createStatement();
			// get the productlist details from dB
			ResultSet set = stmt.executeQuery(q);
			while (set.next()) {
				String desc = set.getString(1);
				int price = set.getInt(2);
				String name = set.getString(3);
				int no = set.getInt(4);
				// formating for the list display to user
				System.out.println("SrNo: " + no + "  " + name + "   " + desc);
				// System.out.println("Product Description: " +desc);
				// System.out.println("Name: " +name);
				System.out.println("Price: " + price);
				System.out.println("+++++++++++++++++++");
				f = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

//This method defined code for adding the required products in the kart of particular user when selected
	public static boolean addToKart(String userName, int srNo) {
		boolean f = false;
		try {
			// jdbc code to store value in database...
			ConnectionProvider obj = new ConnectionProvider();
			Connection con = obj.createC();
			// create query
			String q = "Select productid,price,name from productlist where srNo=?;";
			// prepared statement
			PreparedStatement pstmt = con.prepareStatement(q);
			// set the value of srNo of product in the query
			pstmt.setInt(1, srNo);
			pstmt.executeQuery();
			// get the details of product against entered srNO
			ResultSet set = pstmt.executeQuery();
			while (set.next()) {
				String name = set.getString(3);
				int id = set.getInt(1);
				int price = set.getInt(2);
				// This query adds the details of the product to a new table in dB against
				// userName
				String q1 = "insert into kart values(?,?,?,?)";
				pstmt = con.prepareStatement(q1);
				pstmt.setString(1, userName);
				pstmt.setString(2, name);
				pstmt.setInt(3, id);
				pstmt.setInt(4, price);

				// This process is to check if qty is available in the list or not
				// This query will find out the qty against particular product id
				String q2 = "select qty from productlist where productid=?";
				PreparedStatement stmt = con.prepareStatement(q2);
				// setting entered productId in query
				stmt.setInt(1, id);
				stmt.executeQuery();
				ResultSet set1 = stmt.executeQuery();
				// get the qty of product from dB
				while (set1.next()) {
					int qty = set1.getInt(1);
					// if qty>0 then only above insert into kart query(q1) will be fired
					if (qty > 0) {
						pstmt.executeUpdate();
						System.out.println("product successfully added to kart");
						// after adding kart we need to substract that qty from dB against particulat
						// produt id
						String q3 = "update productlist set qty=? where productid=?";
						stmt = con.prepareStatement(q3);
						// substracting qty from qty
						int uqty = qty - 1;
						// set updated qty in query q3
						stmt.setInt(1, uqty);
						stmt.setInt(2, id);
						// fire the query and update the qty
						stmt.executeUpdate();
					} else {
						System.out.println("product not avaialable");
					}
				}
				f = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	// This will show the kart list for particular user against userName
	public static void userKart(String userName) throws SQLException {
		ConnectionProvider obj = new ConnectionProvider();
		// create Connection
		Connection con = obj.createC();
		// query
		String q = "select productname,price from kart where userName=? order by price ";
		PreparedStatement pstmt = con.prepareStatement(q);
		// setting username is query
		pstmt.setString(1, userName);
		pstmt.executeQuery();
		ResultSet set = pstmt.executeQuery();
		// getting the details of the product in kart for the user
		while (set.next()) {
			String name = set.getString(1);
			int price = set.getInt(2);
			// printing the elements from dB in format
			System.out.println(name);
			System.out.println("Price: " + price);
			System.out.println("------------------------------------------------------------");
		}
		System.out.println("                 ");
		con.close();
		pstmt.close();
	}

	// This will show the total value of the price of the kart
	public void totalValue(String userName) {
		ConnectionProvider obj = new ConnectionProvider();
		// create connection
		Connection con = obj.createC();
		// query
		String q2 = "select SUM(price) from kart where userName=? ";
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(q2);
			// set username in query
			pstmt.setString(1, userName);
			pstmt.executeQuery();
			ResultSet set1 = pstmt.executeQuery();
			// get the total sum of the price in kart
			while (set1.next()) {
				int price = set1.getInt(1);
				// print the total price
				System.out.println("Total price of the kart is: " + price);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("              ");

	}

	// This will show productlist and remaining qty in shop for each product against
	// product id
	public void adminProductList() throws SQLException {
		ConnectionProvider obj = new ConnectionProvider();
		// create connection
		Connection con = obj.createC();
		// query
		String q = "select productid,name,qty from productlist";
		PreparedStatement pstmt = con.prepareStatement(q);
		pstmt.executeQuery();
		ResultSet set = pstmt.executeQuery();
		System.out.println("The remaining quantities of each product in store");
		System.out.println("     ");
		System.out.println("..........................................................");
		// get the id,name and qty from dB
		while (set.next()) {
			int id = set.getInt(1);
			String name = set.getString(2);
			int qty = set.getInt(3);
			// print in formatting
			System.out.println("PruductId: " + id + "   Remaining Qty: " + qty);
		}
		System.out.println("..........................................................");
		System.out.println("    ");
	}

	// This will show the admin registered user list on portal and admin can see
	// purchase history of any user against uniqueID of the user
	public void adminUserList() throws SQLException {
		ConnectionProvider obj = new ConnectionProvider();
		// create connection
		Connection con = obj.createC();
		// query
		String q = "select UniqueId,name,mobileNo,userName,mailID from userlist";
		PreparedStatement pstmt = con.prepareStatement(q);
		pstmt.executeQuery();
		ResultSet set = pstmt.executeQuery();
		System.out.println("Registered users on Portal: ");
		System.out.println("     ");
		System.out.println("..........................................................");
		// get the details of users: uniqueId,name,mobile,uname,mailID
		while (set.next()) {
			int id = set.getInt(1);
			String name = set.getString(2);
			long mobile = set.getLong(3);
			String uname = set.getString(4);
			String mail = set.getString(5);
			// print the details for admin
			System.out.println("UniqueID: " + id);
			System.out.println("UserName: " + uname);
			System.out.println("Name: " + name);
			System.out.println("MobileNo: " + mobile);
			System.out.println("MailId: " + mail);
			System.out.println("......................................................");
		}
		System.out.println("    ");
		System.out.println("Do you want to check purchase history for any user?");
		System.out.println("If yes press 1");
		// if admin wants to user history for any user then take input form admin
		Scanner sc = new Scanner(System.in);
		int b = sc.nextInt();
		if (b == 1) {
			System.out.println("Enter UniqueID of the user ");
			// admin will enter uniqueID of the user
			int a = sc.nextInt();
			// query
			String q1 = "select userName from userlist where UniqueID=?";
			PreparedStatement stmt = con.prepareStatement(q1);
			// set the entered id by admin in query
			stmt.setInt(1, a);
			pstmt.executeQuery();
			ResultSet set1 = stmt.executeQuery();
			// get the username from dB for particular user
			while (set1.next()) {
				String uname = set1.getString(1);
				// query to see purchase history of username
				String q2 = "select productName,productId from kart where userName=?";
				PreparedStatement stmt1 = con.prepareStatement(q2);
				// set the userName in query
				stmt1.setString(1, uname);
				stmt1.executeQuery();
				ResultSet set11 = stmt1.executeQuery();
				System.out.println("     ");
				System.out.println("Purchase History for " + uname);
				System.out.println("_____________________________");
				// get the product purchase history details for userName
				while (set11.next()) {
					String pname = set11.getString(1);
					int pId = set11.getInt(2);
					// print the details
					System.out.println(pId + "    " + pname);
				}
				System.out.println("_____________________________");
				System.out.println("     ");
			}

		}

	}
}

