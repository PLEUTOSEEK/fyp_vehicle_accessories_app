SELECT 
    CollectAddress.Name AS COLLECTOR_NAME,
    CLLCT_BACK_FR.Location_Name AS CLLCT_BACK_FR_LOCATION_NAME,
    CLLCT_BACK_FR.Address AS CLLCT_BACK_FR_ADDRESS,
    CLLCT_BACK_FR.City AS CLLCT_BACK_FR_CITY,
    CLLCT_BACK_FR.Postal_Code AS CLLCT_BACK_FR_POSTAL_CODE,
    CLLCT_BACK_FR.[State] AS CLLCT_BACK_FR_STATE,
    CLLCT_BACK_FR.Country AS CLLCT_BACK_FR_COUNTRY,
    CLLCT_BCK_TO.Location_Name AS CLLCT_BCK_TO_LOCATION_NAME,
    CLLCT_BCK_TO.Address AS CLLCT_BCK_TO_ADDRESS,
    CLLCT_BCK_TO.City AS CLLCT_BCK_TO_CITY,
    CLLCT_BCK_TO.Postal_Code AS CLLCT_BCK_TO_POSTAL_CODE,
    CLLCT_BCK_TO.[State] AS CLLCT_BCK_TO_STATE,
    CLLCT_BCK_TO.Country AS CLLCT_BCK_TO_COUNTRY,
    Staff.Name AS SALES_PERSON_NAME,
    ReturnDeliveryNote.Collect_Date,
    ReturnDeliveryNote.Created_Date AS RDN_CREATED_DATE,
    SalesOrder.SO_ID,
    SalesOrder.Created_Date AS SO_CREATED_DATE,
    CollectAddress.Mobile_No,
    CollectAddress.Email,
    Product.Prod_ID,
    CONCAT(Product.Part_No,' ',Product.[Description]) AS PART_NO_DESC,
    Item.Ori_Qty,
    Item.Reason,
    Item.Remark
FROM
    ReturnDeliveryNote
    INNER JOIN CollectAddress
    ON ReturnDeliveryNote.Collect_Back_From = CollectAddress.Collect_Address_ID

    INNER JOIN [Address] AS CLLCT_BACK_FR
    ON CollectAddress.Collect_Address_ID = CLLCT_BACK_FR.Address_ID

    INNER JOIN Place
    ON ReturnDeliveryNote.Collect_Back_To = Place.Place_ID

    INNER JOIN Address AS CLLCT_BCK_TO
    ON Place.Address_ID = CLLCT_BCK_TO.Address_ID

    INNER JOIN SalesOrder
    ON  ReturnDeliveryNote.SO_ID = SalesOrder.SO_ID

    INNER JOIN Staff
    ON SalesOrder.Sales_Person = Staff.Staff_ID

    INNER JOIN Item
    ON ReturnDeliveryNote.RDN_ID = Item.Ref_Doc_ID

    INNER JOIN Product
    ON Item.Prod_ID = Product.Prod_ID
WHERE
    ReturnDeliveryNote.RDN_ID = ''

