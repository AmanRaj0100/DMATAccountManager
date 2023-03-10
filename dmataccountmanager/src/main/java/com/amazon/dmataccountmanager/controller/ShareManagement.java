package com.amazon.dmataccountmanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.amazon.dmataccountmanager.userSession;
import com.amazon.dmataccountmanager.db.ShareDAO;
import com.amazon.dmataccountmanager.model.Shares;

public class ShareManagement {
	
	Scanner scanner = new Scanner(System.in);
	Shares share = new Shares();
	ShareDAO sharedao = new ShareDAO();
	
	private static ShareManagement manageShares = new ShareManagement();
	
	public static ShareManagement getInstance() {
		return manageShares;
	}
	
	private ShareManagement() {
	}

	public void displayShares() {
		
		List<Shares> shareDetails = new ArrayList<Shares>();
		shareDetails = sharedao.retrieve();
		
		share.printSharesTable(shareDetails);
	}

	public void displaySharesForReport(int shareID) {
		
		String sql = "SELECT * FROM Shares WHERE shareID="+shareID;
		
		List<Shares> shareDetails = new ArrayList<Shares>();
		shareDetails = sharedao.retrieve(sql);
		 
		System.out.println("----------------------------------------------");
		share.printSharesTable(shareDetails);
		System.out.println("----------------------------------------------");
	}
}
