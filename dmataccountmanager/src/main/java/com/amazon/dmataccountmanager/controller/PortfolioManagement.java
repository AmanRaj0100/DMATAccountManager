package com.amazon.dmataccountmanager.controller;

import java.util.List;
import java.util.Scanner;

import com.amazon.dmataccountmanager.userSession;
import com.amazon.dmataccountmanager.db.PortfolioDAO;
import com.amazon.dmataccountmanager.model.Portfolios;

public class PortfolioManagement {
	
	Scanner scanner = new Scanner(System.in);
	Portfolios portfolio = new Portfolios();
	PortfolioDAO portfoliodao = new PortfolioDAO();
	
	private static PortfolioManagement managePortfolios = new PortfolioManagement();
	
	public static PortfolioManagement getInstance() {
		return managePortfolios;
	}
	
	private PortfolioManagement() {
	}
	
	public boolean displayPortfolio() {
        String sql = "SELECT * FROM Portfolios WHERE userID= '"+userSession.user.userID+"'";
        List <Portfolios> portfolios = portfoliodao.retrieve(sql);
        
        if(portfolios.isEmpty()) {
        	System.out.println("No Shares in the User Portfolio");
        	return false;
        } else {
        	//display the details
            for(Portfolios portfolioDetails: portfolios) {
                portfolio.prettyPrint(portfolioDetails);
            }
            return true;
        }
    }
	
	public boolean updatePortfolio(Portfolios portfolios) {
		
		return false;
	}

}
