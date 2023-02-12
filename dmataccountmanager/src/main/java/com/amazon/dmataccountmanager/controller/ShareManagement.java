package com.amazon.dmataccountmanager.controller;

import java.util.Scanner;

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

}
