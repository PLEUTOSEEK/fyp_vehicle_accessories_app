USE [vehicle-accessories]
GO

INSERT INTO [dbo].[CollectAddress]
           ([Collect_Address_ID]
           ,[Address_ID]
           ,[Customer_ID]
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
           ,[Created_Date]
           ,[Modified_Date_Time])
     VALUES
           ('COLL2009-001'
           ,'ADDR2207-004'
           ,'CUST2003-001'
           ,null
           ,'Lai Sui Ru'
           ,'Female'
           ,'2001-12-28'
           ,'011228-10-0192'
           ,'Single'
           ,'Malaysia'
           ,'Ms.'
           ,'ADDR2207-005'
           ,'ADDR2207-006'
           ,'yokeri4120@galotv.com'
           ,'+60 10-221 1061'
           ,'1834'
           ,'0321451311'
           ,'0377835123'
           ,'Store Keeper'
           ,'Malay'
           ,'Islam'
           ,'2020-09-17'
           ,getdate())
GO


