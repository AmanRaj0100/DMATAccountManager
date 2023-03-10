package com.amazon.dmataccountmanager.model;

import java.util.List;

public class Shares {

/*
 MSSQL:
 create table Shares(
	shareID INT IDENTITY(1,1),
	SYMBOL NVARCHAR(10) NOT NULL UNIQUE,
	companyName NVARCHAR(50) NOT NULL,
	price INT NOT NULL, 
	lastUpdatedOn DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(shareID));
*/
	public int shareID;
	public String symbol;
	public String companyName;
	public double price; 
	public String lastUpdatedOn;
	
	public Shares() {
	}

	public Shares(int shareID, String symbol, String companyName, double price, String lastUpdatedOn) {
		this.shareID = shareID;
		this.symbol = symbol;
		this.companyName = companyName;
		this.price = price;
		this.lastUpdatedOn = lastUpdatedOn;
	}
	
	public void prettyPrint(Shares share) {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("ShareID:\t"+share.shareID);
		System.out.println("SYMBOL:\t\t"+share.symbol);
		System.out.println("Company Name:\t"+share.companyName);
		System.out.println("Share Price:\t"+share.price);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
    public void printSharesTable(List<Shares> list) {
        System.out.println("shareID\tsymbol\tprice\tcompanyName");
        for (Shares obj : list) {
            System.out.println(obj.shareID + "\t" + obj.symbol + "\t" + obj.price + "\t" + obj.companyName);
        }
    }
	
	@Override
	public String toString() {
		return "Shares [shareID=" + shareID + ", SYMBOL=" + symbol + ", companyName=" + companyName + ", price=" + price
				+ ", lastUpdatedOn=" + lastUpdatedOn + "]";
	}
}
