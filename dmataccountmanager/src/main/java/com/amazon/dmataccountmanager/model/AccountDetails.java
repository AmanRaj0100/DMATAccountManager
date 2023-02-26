package com.amazon.dmataccountmanager.model;

import java.util.List;

public class AccountDetails {

	public String userName;
	public String accountNumber;
	public double accountBalance;
	public String companyName;
	public int shareCount;
	public double shareValue;
	
	public AccountDetails() {
	}

	public AccountDetails(String userName, String accountNumber, double accountBalance, String companyName,
			int shareCount, double shareValue) {
		this.userName = userName;
		this.accountNumber = accountNumber;
		this.accountBalance = accountBalance;
		this.companyName = companyName;
		this.shareCount = shareCount;
		this.shareValue = shareValue;
	}
	
	
    public void printTable(List<AccountDetails> list) {
        System.out.println("userName\taccountNumber\taccountBalance\tshareCount\tshareValue\tcompanyName");
        for (AccountDetails obj : list) {
            System.out.println(obj.userName + "\t" + obj.accountNumber + "\t" + obj.accountBalance + "\t" + obj.shareCount +  "\t" + obj.shareValue +  "\t" + obj.companyName);
        }
    }

    
	@Override
	public String toString() {
		return "AccountDetails [userName=" + userName + ", accountNumber=" + accountNumber + ", accountBalance="
				+ accountBalance + ", companyName=" + companyName + ", shareCount=" + shareCount + ", shareValue="
				+ shareValue + "]";
	}
	
}
