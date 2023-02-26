package com.amazon.dmataccountmanager;

import org.junit.Assert;
import org.junit.Test;

import com.amazon.dmataccountmanager.controller.UserManagement;
import com.amazon.dmataccountmanager.db.UserDAO;
import com.amazon.dmataccountmanager.db.passEncryption;
import com.amazon.dmataccountmanager.model.Users;

public class AppTest {
	
	UserManagement userService = UserManagement.getInstance();
	passEncryption encryptor = new passEncryption();
	UserDAO userdao = new UserDAO();
	
	@Test
	public void testUserLogin() {
		
		System.out.println("Commented for Creating Maven build as\r\n"
				+ " * Those Test Cases below because they were creating duplicates of Foreign Key\r\n"
				+ " * After running it for the second time.\r\n"
				+ " * But they are still working");
		
		Users user = new Users();
		user.accountNumber = "HA075857";
		user.password = "admin123";
		
		boolean result = userService.login(user);
		
		//Assertion -> Either Test Cases Passes or It will Fail
		Assert.assertEquals(true, result);
		
	}
	
	/*
	 * Commented for Creating Maven build as
	 * Those Test Cases below because they were creating duplicates of Foreign Key
	 * After running it for the second time.
	 * But they are still working
	 * 
	 * */
	
	@Test
	public void testUserRegister() {
		
		Users user = new Users();
		user.userName = "Ab de Villers";
		user.accountNumber = "AB1234";
		user.password = encryptor.encryptor("user123");
		user.accountBalance = 40000;
		
		int result = userdao.insert(user);
		
		//Assertion -> Either Test Cases Passes or It will Fail
		Assert.assertTrue(result>0);
		
	}
	
	@Test
	public void testDepositMoney() {
		
		Users user = new Users();
		user.accountNumber = "HA075857";
		user.password = "admin123";
		
		userService.login(user);
		
		userSession.user = user;
		
		double amount = 10260.00;
		boolean result = userService.depositMoney(amount);
		
		Assert.assertEquals(true, result);
	}
	
	@Test
	public void testWithdrawMoney() {
		
		Users user = new Users();
		user.accountNumber = "HA075857";
		user.password = "admin123";
		
		userService.login(user);
		
		userSession.user = user;
		
		double amount = 10222.00;
		boolean result = userService.withdrawMoney(amount);
		
		Assert.assertEquals(true, result);
	}
}
