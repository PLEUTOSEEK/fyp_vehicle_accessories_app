USE [vehicle-accessories]
GO

INSERT INTO [dbo].[Customer]
           ([Customer_ID]
           ,[Bill_To_Addr]
           ,[Avatar_Img]
           ,[Name]
           ,[Gender]
           ,[DOB]
           ,[IC]
           ,[Marital_Status]
           ,[Nationality]
           ,[Honorifics]
           ,[Residential_Address]
           ,[Corresponding_Address]
           ,[Email]
           ,[Mobile_No]
           ,[Extension_No]
           ,[Office_Phone_No]
           ,[Home_Phone_No]
           ,[Occupation]
           ,[Race]
           ,[Religion]
           ,[Bank_Acc_Provider]
           ,[Bank_Acc_No]
           ,[Customer_Type]
           ,[Status]
           ,[Created_Date]
           ,[Modified_Date_Time])
     VALUES
           ('CUST2003-001'
           ,'ADDR2207-001'
           ,null
           ,'Tee Zhuo Xuan'
           ,'Male'
           ,'2001-12-27'
           ,'011227101185'
           ,'Single'
           ,'Malaysia'
           ,'Mr.'
           ,'ADDR2207-002'
           ,'ADDR2207-003'
           ,'xuanvanquish@gmail.com'
           ,'012-799-1227'
           ,'2365'
           ,'0321485721'
           ,'0321424903'
           ,'Purchasing'
           ,'Chinese'
           ,'Christian'
           ,'Public Bank'
           ,'65244793'
           ,'Organization'
           ,'Active'
           ,'2020-03-12 15:30:10'
           ,GETDATE())
GO

/*

		   (<Customer_ID, varchar(20),>
           ,<Bill_To_Addr, varchar(20),>
           ,<Avatar_Img, varchar(max),>
           ,<Name, varchar(200),>
           ,<Gender, varchar(30),>
           ,<DOB, date,>
           ,<IC, varchar(100),>
           ,<Marital_Status, varchar(50),>
           ,<Nationality, varchar(100),>
           ,<Honorifics, varchar(50),>
           ,<Residential_Address, varchar(20),>
           ,<Corresponding_Address, varchar(20),>
           ,<Email, varchar(50),>
           ,<Mobile_No, varchar(50),>
           ,<Extension_No, varchar(50),>
           ,<Office_Phone_No, varchar(50),>
           ,<Home_Phone_No, varchar(50),>
           ,<Occupation, varchar(100),>
           ,<Race, varchar(50),>
           ,<Religion, varchar(50),>
           ,<Bank_Acc_Provider, varchar(100),>
           ,<Bank_Acc_No, varchar(100),>
           ,<Customer_Type, varchar(50),>
           ,<Status, varchar(30),>
           ,<Created_Date, datetime2(7),>
           ,<Modified_Date_Time, datetime2(7),>)
*/


