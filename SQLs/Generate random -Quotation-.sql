USE [vehicle-accessories]
GO

INSERT INTO [dbo].[Quotation]
           ([QUOT_ID]
           ,[CI_ID]
           ,[Reference_Type]
           ,[Reference]
           ,[Bill_To_Cust]
           ,[Deliver_To]
           ,[Sales_Person]
           ,[Currency_Code]
           ,[Quot_Validity_Date]
           ,[Required_Delivery_Date]
           ,[Payment_Term]
           ,[Shipment_Term]
           ,[Gross]
           ,[Discount]
           ,[Sub_Total]
           ,[Nett]
           ,[Issued_By]
           ,[Released_And_Verified_By]
           ,[Customer_Signed]
           ,[Status]
           ,[Created_Date]
           ,[Actual_Created_Date]
           ,[Signed_Doc_Pic]
           ,[Modified_Date_Time])
     VALUES
           ('QUOT2207-001'
           ,null
           ,null
           ,null
           ,'CUST2003-001'
           ,'COLL2009-001'
           ,'S1902-001'
           ,'MYR'
           ,'2022-08-31'
           ,'2022-09-03'
           ,'Net Due 30'
           ,'EXW'
           ,900.00
           ,30.00
           ,870.00
           ,922.20
           ,'S1902-001'
           ,'S1902-001'
           ,'COLL2009-001'
           ,'New'
           ,'2022-07-28'
           ,'2022-07-28'
           ,null
           ,GETDATE())
GO


