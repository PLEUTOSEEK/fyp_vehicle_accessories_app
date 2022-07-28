USE [vehicle-accessories]
GO

INSERT INTO [dbo].[Place]
           ([Place_ID]
           ,[Place_Name]
           ,[Address_ID]
           ,[Description]
           ,[Email]
           ,[Office_Phone_No]
           ,[Created_Date]
           ,[Modified_Date_Time])
     VALUES
           ('PLC1901-001'
           ,'Purchasing Department'
           ,'ADDR2207-013'
           ,'Place that in cahrge purchase raw material'
           ,'procurement@thirshen.com'
           ,'0390594778'
           ,'2019-01-12 14:09:03'
           ,GETDATE())
GO

