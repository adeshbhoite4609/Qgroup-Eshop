package com.velocity.qmart;



import java.sql.SQLException;
import java.util.Scanner;

public class Shopping extends Database {

//This method calls the method showproductlist from dbOPs against particular userName and if user wants then he can enter the srNo of the product and add it to kart
	public void showList(String userName) {
		Shopping sp = new Shopping();
		sp.showProductList();
		System.out.println("   ");
		System.out.println("Select product which you want to Buy with respect to SrNo, enter the number");
		// if user wants to buy the product the will enter srNo of product
		Scanner sc = new Scanner(System.in);
		int sel = sc.nextInt();
		// with username and srNo of product addToKart() method from dbOps will be
		// called
		sp.addToKart(userName, sel);
	}

	// this method call userKart method from dbOps to show the kart items against
	// the particular user
	public void showKart(String userName) throws SQLException {
		System.out.println("   ");
		System.out.println("------------------------------------------------------------");
		// calling the method from dbOps with respect to userName
		Shopping sp = new Shopping();
		sp.userKart(userName);
	}

}

