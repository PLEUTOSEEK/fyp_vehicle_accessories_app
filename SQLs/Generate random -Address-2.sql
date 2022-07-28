USE [vehicle-accessories]
GO

INSERT INTO [dbo].[Address]
           ([Address_ID]
           ,[Location_Name]
           ,[Address]
           ,[City]
           ,[Postal_Code]
           ,[State]
           ,[Country]
           ,[Created_Date]
           ,[Modified_Date_Time])
     VALUES
           ('ADDR2207-007'
           ,'G Wisma Wai Kuan'
           ,'G Wisma Wai Kuan 23 Jln Petaling'
           ,'Kuala Lumpur'
           ,'50000'
           ,'Wilayah Persekutuan'
           ,'Malaysia'
           ,'2022-07-22 12:30:58'
           ,GETDATE()),

		   ('ADDR2207-008'
           ,'Hunza Complex'
           ,'5-5-1'
           ,'Hunza Complex'
           ,'11600'
           ,'Jalan Gangsa'
           ,'Malaysia'
           ,'2022-07-22 12:30:58'
           ,GETDATE()),

		   ('ADDR2207-009'
           ,'Malcop'
           ,'1 Jalan Kledang Raya 23 Taman Malcop'
           ,'Ipoh'
           ,'30100'
           ,'Perak'
           ,'Malaysia'
           ,'2022-07-22 12:30:58'
           ,GETDATE()),

		   ('ADDR2207-010'
           ,'batang berjuntai'
           ,'pusat bandar batang berjuntai, batang berjuntai'
           ,'Batang Berjuntai'
           ,'45600'
           ,'Selangor'
           ,'Malaysia'
           ,'2022-07-22 12:30:58'
           ,GETDATE()),

		   ('ADDR2207-011'
           ,'U Thant'
           ,'No. 109 Jln U Thant'
           ,'Kuala Lumpur'
           ,'55000'
           ,'Wilayah Persekutuan'
           ,'Malaysia'
           ,'2022-07-22 12:30:58'
           ,GETDATE()),

		   ('ADDR2207-012'
           ,'Muda Musa'
           ,'72C Persiaran Raja Muda Musa'
           ,'Klang'
           ,'41100'
           ,'Selangor'
           ,'Malaysia'
           ,'2022-07-22 12:30:58'
           ,GETDATE())
		   
		   ('ADDR2207-013'
           ,'Thir Shen Sdn. Bhd'
           ,'Lot.8538, Jalan Klang Banting'
           ,'Banting'
           ,'42700'
           ,'Selangor'
           ,'Malaysia'
           ,'2022-07-22 12:30:58'
           ,GETDATE())

GO

