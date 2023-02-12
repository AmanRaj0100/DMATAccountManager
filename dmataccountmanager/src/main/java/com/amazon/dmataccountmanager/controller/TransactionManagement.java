package com.amazon.dmataccountmanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.amazon.dmataccountmanager.db.PortfolioDAO;
import com.amazon.dmataccountmanager.db.ShareDAO;
import com.amazon.dmataccountmanager.db.TransactionDAO;
import com.amazon.dmataccountmanager.model.Portfolios;
import com.amazon.dmataccountmanager.model.Shares;
import com.amazon.dmataccountmanager.model.Transactions;

public class TransactionManagement {
	
	double transactionCharge = 0.5/100;
	double sttCharges = 0.1/100;
	int minTransCharge = 100;
	
	Scanner scanner = new Scanner(System.in);
	Shares share = new Shares();
	Transactions transaction = new Transactions();
	Portfolios portfolio = new Portfolios();
	ShareDAO sharedao = new ShareDAO();
	TransactionDAO transactiondao = new TransactionDAO();
	PortfolioDAO portfoliodao = new PortfolioDAO();
	UserManagement userService = UserManagement.getInstance();
	PortfolioManagement portfolioService = PortfolioManagement.getInstance();
	
	
	
	private static TransactionManagement manageTransactions = new TransactionManagement();
	
	public static TransactionManagement getInstance() {
		return manageTransactions;
	}
	
	private TransactionManagement() {
	}

	public void sellTransaction() {
		//displaying the Portfolio for the user..
		portfolioService.displayPortfolio(portfolio);
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Enter the Share ID: ");
		int shareID = Integer.parseInt(scanner.nextLine());
		
		System.out.println("Enter the No.of Shares");
		int numberOfShares = Integer.parseInt(scanner.nextLine());		
		
		double totalStockPrice = 0;
		
		
		String sql = "Select * from Portfolios where shareID="+shareID;
		List<Portfolios> portfolioToUpdate = new ArrayList<Portfolios>();
		
		portfolioToUpdate = portfoliodao.retrieve(sql);
		
		portfolio = portfolioToUpdate.get(0);
		
		if(portfolio.shareCount >=numberOfShares) {
			sql = "Select * from Shares where shareID="+shareID;
			List<Shares> shareDetail = new ArrayList<Shares>();
			
			
			shareDetail = sharedao.retrieve(sql);
			share = shareDetail.get(0);
			
			double pricePerShare = share.price;
			double tax = 0;
			
			double transactionValue = 0;
			
			transactionValue = pricePerShare*numberOfShares;
			
			//condition: minimum transaction charge is Rs.100.
			if((transactionValue*transactionCharge)<=100) {
				tax = 100;
			}
			else {
				tax = transactionValue*transactionCharge;
			}
			
			//total tax including transaction charges and sttCharges
			tax = tax + (sttCharges*transactionValue);
			
			totalStockPrice = transactionValue + tax;
			
			//amount credited into user account
			
			userService.depositMoney(totalStockPrice);
			
			portfolio.shareCount = portfolio.shareCount - numberOfShares;
			
			//Portfolio - update function - updating shareCount in Portfolio
			
			if(portfoliodao.update(portfolio)>0) {
				System.out.println("Portfolio updated");
			}
			else {
				System.out.println("Something went wrong in updating the portfolio");
			}
			
			
			//update transaction table
			
			transaction.shareID = shareID; //input from user
			transaction.shareCount = numberOfShares; //no.of shares sold
			transaction.pricePerShare = pricePerShare;
			transaction.transactionCharges = transactionCharge*transactionValue;
			transaction.sttCharges = sttCharges*transactionValue;
			transaction.type = 0;
			
			if(transactiondao.insert(transaction)>0) {
				System.out.println("Transaction details inserted");
			}
			else {
				System.out.println("Something went wrong while inserting the transaction details");
			}
			
			
		}else {
			System.out.println("Insufficient No.of shares to Sell");
		}
	}
	
	
	public void buyTransaction() {
		//displaying the Portfolio for the User..
		portfolioService.displayPortfolio(portfolio);
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Enter the Share ID: ");
		int shareID = Integer.parseInt(scanner.nextLine());
		
		System.out.println("Enter the No.of Shares");
		int numberOfShares = Integer.parseInt(scanner.nextLine());		
		
		double totalStockPrice = 0;
		
		
		String sql = "Select * from Portfolios where shareID="+shareID;
		List<Portfolios> portfolioToUpdate = new ArrayList<Portfolios>();
		
		portfolioToUpdate = portfoliodao.retrieve(sql);
		
		portfolio = portfolioToUpdate.get(0);
		
		if(portfolio.shareCount >=numberOfShares) {
			sql = "Select * from Shares where shareID="+shareID;
			List<Shares> shareDetail = new ArrayList<Shares>();
			
			
			shareDetail = sharedao.retrieve(sql);
			share = shareDetail.get(0);
			
			double pricePerShare = share.price;
			double tax = 0;
			
			double transactionValue = 0;
			
			transactionValue = pricePerShare*numberOfShares;
			
			//condition: minimum transaction charge is Rs.100.
			if((transactionValue*transactionCharge)<=100) {
				tax = 100;
			}
			else {
				tax = transactionValue*transactionCharge;
			}
			
			//total tax including transaction charges and sttCharges
			tax = tax + (sttCharges*transactionValue);
			
			totalStockPrice = transactionValue + tax;
			
			//amount credited into user account
			
			userService.withdrawMoney(totalStockPrice);
			
			portfolio.shareCount = portfolio.shareCount + numberOfShares;
			
			//Portfolio - update function - updating shareCount in Portfolio
			
			if(portfoliodao.update(portfolio)>0) {
				System.out.println("Portfolio updated");
			}
			else {
				System.out.println("Something went wrong in updating the portfolio");
			}
			
			
			//update transaction table
			
			transaction.shareID = shareID; //input from user
			transaction.shareCount = numberOfShares; //no.of shares sold
			transaction.pricePerShare = pricePerShare;
			transaction.transactionCharges = transactionCharge*transactionValue;
			transaction.sttCharges = sttCharges*transactionValue;
			transaction.type = 1;
			
			if(transactiondao.insert(transaction)>0) {
				System.out.println("Transaction details inserted");
			}
			else {
				System.out.println("Something went wrong while inserting the transaction details");
			}
			
			
		}else {
			System.out.println("Insufficient No.of shares to Sell");
		}
	}
}