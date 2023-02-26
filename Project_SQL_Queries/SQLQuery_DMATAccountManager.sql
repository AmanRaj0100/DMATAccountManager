create database dmatdb;

-----------------------------------------------------------------------------------------

create table Shares(
	shareID INT IDENTITY(1,1),
	SYMBOL NVARCHAR(10) NOT NULL UNIQUE,
	companyName NVARCHAR(50) NOT NULL,
	price DECIMAL(10,2) NOT NULL, 
	lastUpdatedOn DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(shareID));

drop table Shares;

select * from Shares;

--Trigger to update "lastUpdatedOn" column on any updation.
CREATE TRIGGER tgrAfterUpdateShares ON Shares
AFTER UPDATE 
AS
  UPDATE Shares set lastUpdatedOn=CURRENT_TIMESTAMP 
  FROM 
  Shares
  INNER JOIN INSERTED  
  ON Shares.shareID = INSERTED.shareID;

DROP TRIGGER tgrAfterUpdateShares

-----------------------------------------------------------------------------------------

create table Users(
	userID INT IDENTITY(1,1),
	userName NVARCHAR(50) NOT NULL,
	accountNumber NVARCHAR(20) NOT NULL UNIQUE,
	password NVARCHAR(100) NOT NULL,
	accountBalance DECIMAL(30,2) NOT NULL, 
	lastUpdatedOn DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(userID));

drop table Users;

select * from Users;

--Trigger to update "lastUpdatedOn" column on any updation.
CREATE TRIGGER tgrAfterUpdateUsers ON Users
AFTER UPDATE 
AS
  UPDATE Users set lastUpdatedOn=CURRENT_TIMESTAMP 
  FROM 
  Users
  INNER JOIN INSERTED  
  ON Users.userID = INSERTED.userID;

DROP TRIGGER tgrAfterUpdateUsers

-----------------------------------------------------------------------------------------

create table Transactions(
	transactionID INT IDENTITY(1,1),
	shareID INT constraint transactions_shareID_fk references Shares(shareID),
	userID INT constraint transactions_userID_fk references Users(userID),
	shareCount INT NOT NULL,
	pricePerShare DECIMAL(30,2) NOT NULL,
	transactedOn DATETIME DEFAULT CURRENT_TIMESTAMP,
	transactionCharges DECIMAL(10,2) NOT NULL,
	sttCharges DECIMAL(10,2) NOT NULL,
	type BIT NOT NULL, --(1-Buy, 0-Sell)
	PRIMARY KEY(transactionID));

drop table Transactions;

select * from Transactions;

INSERT INTO Transactions (shareID,shareCount,pricePerShare,transactionCharges,sttCharges,type)
VALUES (1,200,151,0.77,0.10,1);
-----------------------------------------------------------------------------------------

create table Portfolios(
	portfolioID INT IDENTITY(1,1),
	userID INT constraint usershares_userID_fk references Users(userID),
	shareID INT constraint usershares_shareID_fk references Shares(shareID),
	transactionID INT constraint portfolios_transactionID_fk references Transactions(transactionID),
	companyName NVARCHAR(50) NOT NULL,
	shareCount INT NOT NULL, 
	PRIMARY KEY(portfolioID));

drop table Portfolios;

select * from Portfolios;

INSERT INTO Portfolios (userID,shareID,transactionID,companyName,shareCount)
VALUES (1,1,1,'Apple Inc.',100);

UPDATE Portfolios SET shareCount =200  Where portfolioID=1
-----------------------------------------------------------------------------------------

--HardCoded Shares inserted into Shares Table: 

INSERT INTO Shares (SYMBOL, companyName, price)
VALUES ('AAPL', 'Apple Inc.', 140.00),
       ('GOOG', 'Alphabet Inc.', 2200.00),
       ('MSFT', 'Microsoft Corporation', 200.00),
       ('AMZN', 'Amazon.com Inc.', 3000.00),
       ('FB', 'Facebook Inc.', 240.00),
       ('BABA', 'Alibaba Group Holding Limited', 220.00),
       ('V', 'Visa Inc.', 200.00),
       ('JPM', 'JP Morgan Chase & Co.', 100.00),
       ('PG', 'Procter & Gamble Co.', 120.00),
       ('BAC', 'Bank of America Corp.', 30.00),
       ('T', 'AT&T Inc.', 40.00),
       ('WFC', 'Wells Fargo & Co.', 50.00),
       ('KO', 'The Coca-Cola Co.', 60.00),
       ('PFE', 'Pfizer Inc.', 70.00),
       ('XOM', 'Exxon Mobil Corporation', 80.00),
       ('GE', 'General Electric Co.', 90.00),
       ('MCD', 'McDonald''s Corporation', 100.00),
       ('DIS', 'The Walt Disney Company', 110.00),
       ('JNJ', 'Johnson & Johnson', 120.00),
       ('VZ', 'Verizon Communications Inc.', 130.00);

-------------------------------------------------------------------------------------------
--Rough
create table StockPrices(
	shareID INT IDENTITY(1,1),
	StockSymbol NVARCHAR(20),
	Price DECIMAL(10,2)
	PRIMARY KEY(shareID));

INSERT INTO StockPrices (StockSymbol, Price)
VALUES ('AAPL', 140.00),
       ('GOOG', 2200.00),
       ('MSFT', 200.00),
       ('AMZN', 3000.00);

SELECT * FROM StockPrices;
--98.80
--97.84
--104.69
--102.99

DROP Table StockPrices
--------------------------------------------------------------------------------------------
--Rough
Select t.* from Portfolios p Inner Join Transactions t on p.transactionID = t.transactionID where p.userID = '1'

--Displaying DMAT Account details:
select U.userName, U.accountNumber, U.accountBalance, P.companyName, P.shareCount, (P.shareCount*S.price) AS shareValue
from Users U
Inner Join Portfolios P 
ON U.userID = P.userID
Inner Join Shares S
ON P.shareID = S.shareID

-------------------------------------------------------------------------------------------------------------------------------
--Rough
SELECT U.userName, U.accountNumber, U.accountBalance, P.companyName, P.shareCount, (P.shareCount*S.price) AS shareValue
FROM Users U LEFT JOIN Portfolios P ON U.userID = P.userID LEFT Join Shares S ON P.shareID = S.shareID WHERE U.userID = 5

-------------------------------------------------------------------------------------------------------------------------------
--Rough
select * from Users;
select * from Shares;
Select * from Transactions;
select * from Portfolios;
