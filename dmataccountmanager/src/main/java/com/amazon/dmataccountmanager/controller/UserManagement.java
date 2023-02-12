package com.amazon.dmataccountmanager.controller;

import java.util.List;
import java.util.Scanner;

import com.amazon.dmataccountmanager.db.passEncryption;
import com.amazon.dmataccountmanager.userSession;
import com.amazon.dmataccountmanager.db.TransactionDAO;
import com.amazon.dmataccountmanager.db.UserDAO;
import com.amazon.dmataccountmanager.model.Transactions;
import com.amazon.dmataccountmanager.model.Users;


public class UserManagement {
	
	Scanner scanner = new Scanner(System.in);
	Users user = new Users();
	UserDAO userdao = new UserDAO();
	TransactionDAO transactiondao = new TransactionDAO();
	Transactions transaction = new Transactions();
	passEncryption encrypt = passEncryption.getInstance();
	
	private static UserManagement manageUsers = new UserManagement();
	
	public static UserManagement getInstance() {
		return manageUsers;
	}
	
	private UserManagement() {
	}
	
	
/*	public void manageUser() {
		
		while(true) {
			try {
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("1: Activate/Deactivate User");
				System.out.println("2: Quit Managing User");
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("Enter Your Choice: ");
				int choice = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
				boolean quit = false;
				switch(choice) {
				case 1:

					break;
					
				case 2:
					quit = true;
					break;
					
				default:
					
				}
				
				if (quit)
					break;
			} catch (Exception e) {
				System.err.println("Invalid Input"+e);
			}
		}
	}*/
	

	public boolean login(Users user) {

		String sql = "SELECT * FROM Users WHERE accountNumber = '"+user.accountNumber+"' AND password = '"+encrypt.encryptor(user.password)+"'";
		List<Users> users = userdao.retrieve(sql);
		
		if(users.size() > 0) {
			Users u = users.get(0);
			user.userID = u.userID;
			user.userName = u.userName;
			user.accountNumber = u.accountNumber;
			user.password = u.password;
			user.accountBalance = u.accountBalance;
			user.lastUpdatedOn = u.lastUpdatedOn;
			return true;
		}
		return false;
	}
	
	public boolean register(Users user) {
		
		user.getDetails(user);
		
		if (userdao.insert(user)>0)
			return true;
		
		return false;	
	}
	
	//For User
	public void displayAccount() {

        //Fetch User Detail
        String sql = "SELECT * FROM Users WHERE accountNumber= '"+userSession.user.accountNumber+"'";
        List <Users> userDetail = userdao.retrieve(sql);

        //Display the Details
        user.prettyPrint(userDetail.get(0));
    }
	
	//For User
	public boolean update() {

        //Fetch User Detail
        String sql = "SELECT * FROM Users WHERE accountNumber= '"+userSession.user.accountNumber+"'";
        List <Users> userDetail = userdao.retrieve(sql);

        //Ask the user to update the details
        user.getDetails(userDetail.get(0));

        //Update the details in SQL.
        if (userdao.update(userDetail.get(0))>0) {
        	System.out.println("User Profile Updated Successfully");
        	return true;
        }
        else {
			System.err.println("User Profile Update Failed...");
			return false;
		}
    }
	
	//Deposit Money without parameter
	public boolean withdrawMoney() {
		
		System.out.println("Enter the Amount to be withdrawn: ");
		int amount = Integer.parseInt(scanner.nextLine());
		
		user.accountNumber = userSession.user.accountNumber;
		user.accountBalance = userSession.user.accountBalance;
        
		if(amount<=user.accountBalance) {
			user.accountBalance=user.accountBalance-amount;
			if(userdao.update(user)>0) {
				System.out.println("Money Withdrawn Successfully");
				return true;
			}
		}
		else {
			System.out.println("Transaction Failed");
			return false;
		}
		
		return false;
	}
	
	//Withdraw Money Overrided with parameter
	public boolean withdrawMoney(double amount) {
		
		user.accountNumber = userSession.user.accountNumber;
		user.accountBalance = userSession.user.accountBalance;
        
		if(amount<=user.accountBalance) {
			user.accountBalance=user.accountBalance-amount;
			if(userdao.update(user)>0) {
				System.out.println("Money Withdrawn Successfully");
				return true;
			}
		}
		else {
			System.out.println("Transaction Failed");
			return false;
		}
		
		return false;
	}
	
	//Deposit Money without parameter
	public boolean depositMoney() {
		
		System.out.println("Enter the Amount to be deposited: ");
		int amount = Integer.parseInt(scanner.nextLine());
		
		user.accountNumber = userSession.user.accountNumber;
		user.accountBalance = userSession.user.accountBalance;
        
		user.accountBalance=user.accountBalance+amount;
		
		if(userdao.update(user)>0) {
			System.out.println("Money Deposited Successfully");
			return true;
		}
		else {
			System.out.println("Transaction Failed");
			return false;
		}
	}
	
	//Deposit Money Overrided with parameter
	public boolean depositMoney(double amount) {
		
		user.accountNumber = userSession.user.accountNumber;
		user.accountBalance = userSession.user.accountBalance;
        
		user.accountBalance=user.accountBalance+amount;
		
		if(userdao.update(user)>0) {
			System.out.println("Money Deposited Successfully");
			return true;
		}
		else {
			System.out.println("Transaction Failed");
			return false;
		}
	}
	
	
	public void display() {
		
		List <Users> userDetail = userdao.retrieve();
		for (Users user : userDetail) {
			user.printTable(user);
		}
	}
	
	
	public void viewReport() {

        String sql = "Select t.* from Portfolios p Inner Join Transactions t on p.transactionID = t.transactionID where p.userID = '"+userSession.user.userID+"'";
        List <Transactions> transactionDetail = transactiondao.retrieve(sql);;
        transaction.prettyPrint(transactionDetail.get(0));

	}
}
