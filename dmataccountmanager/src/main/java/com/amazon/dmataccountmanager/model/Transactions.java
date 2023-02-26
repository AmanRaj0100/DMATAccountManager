package com.amazon.dmataccountmanager.model;


public class Transactions {

/*
 * MSSQL:
		create table Transactions(
			transactionID INT IDENTITY(1,1),
			shareID INT constraint transactions_shareID_fk references Shares(shareID),
			shareCount INT NOT NULL,
			pricePerShare INT NOT NULL,
			transactedOn DATETIME DEFAULT CURRENT_TIMESTAMP,
			transactionCharges INT NOT NULL,
			sttCharges INT NOT NULL,
			type BIT NOT NULL, --(1-Buy, 0-Sell)
			PRIMARY KEY(transactionID));
*/
	
	public int transactionID;
	public int shareID;
	public int userID;
	public int shareCount;
	public double pricePerShare;
	public String transactedOn;
	public double transactionCharges;
	public double sttCharges;
	public int type;
	
	public Transactions() {
	}

	
	public Transactions(int transactionID, int shareID, int userID, int shareCount, double pricePerShare,
			String transactedOn, double transactionCharges, double sttCharges, int type) {
		this.transactionID = transactionID;
		this.shareID = shareID;
		this.userID = userID;
		this.shareCount = shareCount;
		this.pricePerShare = pricePerShare;
		this.transactedOn = transactedOn;
		this.transactionCharges = transactionCharges;
		this.sttCharges = sttCharges;
		this.type = type;
	}


	public void prettyPrint(Transactions transaction) {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("TransactionID:\t\t"+transaction.transactionID);
		System.out.println("ShareID:\t\t"+transaction.shareID);
		System.out.println("UserID:\t\t\t"+transaction.userID);
		System.out.println("Share Count:\t\t"+transaction.shareCount);
		System.out.println("PricePerShare:\t\t"+transaction.pricePerShare);
		System.out.println("TransactedOn:\t\t"+transaction.transactedOn);
		System.out.println("Transaction Charges:\t"+transaction.transactionCharges);
		System.out.println("STT Charges:\t\t"+transaction.sttCharges);
		System.out.println("Transaction Type:\t"+((transaction.type==0)?"Sold":"Bought"));
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	@Override
	public String toString() {
		return "Transactions [transactionID=" + transactionID + ", shareID=" + shareID + ", userID=" + userID
				+ ", shareCount=" + shareCount + ", pricePerShare=" + pricePerShare + ", transactedOn=" + transactedOn
				+ ", transactionCharges=" + transactionCharges + ", sttCharges=" + sttCharges + ", type=" + type + "]";
	}
	
}
