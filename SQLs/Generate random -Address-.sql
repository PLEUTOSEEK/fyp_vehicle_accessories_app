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
           ('ADDR2207-001'
           ,'Amj Industrial Park'
           ,'10 Jln Khoo Teik Ee'
           ,'Shah Alam'
           ,'10980'
           ,'Selangor'
           ,'Malaysia'
           ,'2022-07-20 12:30:20'
           ,GETDATE()),

		   ('ADDR2207-002'
           , 'Khoo Teik Ee'
           , '10 Jln Khoo Teik Ee'
           ,'Kuala Lumpur'
           ,'55100'
           ,'Wilayah Persekutuan'
           ,'Malaysia'
           ,'2022-07-20 12:30:20'
           ,GETDATE()),

		   ('ADDR2207-003'
           ,'Melawati Hulu'
           ,'No. 320 Lrg Taman Melawati Hulu'
           ,'Klang'
           ,'53100'
           ,'Selangor'
           ,'Malaysia'
           ,'2022-07-20 12:30:20'
           ,GETDATE()),

		   ('ADDR2207-004'
           ,'Sobena'
           ,'28 Jln Selat Selatan 22 Kaw Perindustrian Sobena Jaya Pelabuhan Pelabuhan'
           ,'Klang'
           ,'42000'
           ,'Selangor'
           ,'Malaysia'
           ,'2022-07-20 12:30:20'
           ,GETDATE()),

		   ('ADDR2207-005'
           ,'Desa Melawati'
           ,'No. 20 2Nd Floor Jln 4/4C Desa Melawati'
           ,'Kuala Lumpur'
           ,'53100'
           ,'Wilayah Persekutuan'
           ,'Malaysia'
           ,'2022-07-20 12:30:20'
           ,GETDATE()),

		   ('ADDR2207-006'
           ,'Pandan'
           ,'2A 5 Jln Pandan 2/3 Taman Pandan Jaya'
           ,'Kuala Lumpur'
           ,'55100'
           ,'Wilayah Persekutuan'
           ,'Malaysia'
           ,'2022-07-20 12:30:20'
           ,GETDATE())

GO


