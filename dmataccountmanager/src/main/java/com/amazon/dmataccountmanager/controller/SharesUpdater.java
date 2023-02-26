package com.amazon.dmataccountmanager.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.amazon.dmataccountmanager.db.DB;
import com.amazon.dmataccountmanager.db.ShareDAO;
import com.amazon.dmataccountmanager.model.Shares;

public class SharesUpdater implements Runnable {
	
	/*The volatile keyword in Java is used to indicate that a variable's value may be modified by multiple threads.
	When a variable is declared as volatile, the compiler and the runtime system ensure that all threads see a consistent value of the variable,
	and that changes made to the variable by one thread are immediately visible to all other threads.*/
	
	private volatile boolean stopFlag = false;

	DB db = DB.getInstance();
	ShareDAO sharedao = new ShareDAO();
	
	Connection conn;
	PreparedStatement preparedStatement;
	
	
    public void run() {
    	
    	String url = DB.URL+";user="+DB.USER+";password="+DB.PASSWORD+";trustServerCertificate=true";
    	try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.err.println("[Dynamic Share] DB Connection Not Established"+e);
		}
    	
    	List<Shares> shares = new ArrayList<Shares>();
		shares = sharedao.retrieve();
    	
        while (!stopFlag) {
        	
            preparedStatement = null;
            
            try {
                for (Shares shareDetails : shares) {
                	// generate random percentage change between -5% and +5%
                    double randomChange = new Random().nextDouble() * 0.1 - 0.05;
                    
                    // calculate new price based on percentage change
                    double newPrice = shareDetails.price * (1 + randomChange);
                    
                    preparedStatement = conn.prepareStatement("UPDATE Shares SET price = ? WHERE shareID = ?");
                    preparedStatement.setDouble(1, newPrice);
                    preparedStatement.setInt(2, shareDetails.shareID);
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
            	System.err.println("[Dynamic Share] Error in Updating Share Prices"+e);
            }

            try {
                // Added a delay to avoid consuming too much CPU time
                Thread.sleep(2000);; // Sleep for 2 second
            } catch (InterruptedException e) {
            	System.err.println("[Dynamic Share] Error in Thread Sleep"+e);
            }
        }
        
        try {
        	preparedStatement.close();
        	System.out.println("[Dynamic Share] PreparedStatement Closed...");
			conn.close();
			System.out.println("[Dynamic Share] Connection Closed...");
		} catch (SQLException e) {
			System.err.println("[Dynamic Share] Error Closing DB Connection"+e);
		}
    }
    
    public void stopThread() {
        stopFlag = true;
    }
}

