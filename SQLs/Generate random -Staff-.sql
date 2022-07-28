USE [vehicle-accessories]
GO

INSERT INTO [dbo].[Staff]
           ([Staff_ID]
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
           ,[Officed_Phone_No]
           ,[Home_Phone_No]
           ,[Occupation]
           ,[Race]
           ,[Religion]
           ,[Work_Place]
           ,[Entry_Date]
           ,[Report_To]
           ,[Employee_Type]
           ,[Password]
           ,[Role]
           ,[Status]
           ,[Account_Status]
           ,[Created_Date]
           ,[Modified_Date_Time])
     VALUES
           ('S1902-001'
           ,null
           ,'Gan Wei Han'
           ,'Male'
           ,'2001-04-25'
           ,'010425-10-8927'
           ,'Single'
           ,'Malaysia'
           ,'Mr.'
           ,'ADDR2207-008'
           ,'ADDR2207-007'
           ,'yokeri4120@galotv.com'
           ,'+60 10-564 1407'
           ,'0382'
           ,'0320938773'
           ,'0379521002'
           ,'Salesman'
           ,'Chinese'
           ,'Buddhist'
           ,'PLC1901-001'
           ,'2019-02-17'
           ,null
           ,'Full-Time'
           ,'324kjbiui'
           ,'Salesman'
           ,'Active'
           ,'Active'
           ,'2019-02-17 09:12:52'
           ,getdate())
GO

