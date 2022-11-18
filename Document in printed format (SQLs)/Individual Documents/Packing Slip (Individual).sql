SELECT
    PackingSlip.PS_ID,
    DELIVER_TO.Name AS DELIVER_TO_NAME,
    DELIVER_TO_ADDR.Location_Name,
    DELIVER_TO_ADDR.Address,
    DELIVER_TO_ADDR.City,
    DELIVER_TO_ADDR.Postal_Code,
    DELIVER_TO_ADDR.[State],
    DELIVER_TO_ADDR.Country,
    Staff.Name AS PIC_NAME,
    Staff.Mobile_No AS PIC_MOBILE_NO,
    Staff.Extension_No,
    FORMAT (PackingSlip.Actual_Created_Date, 'dd-MMM-yyyy') AS ACTUAL_CREATED_DATE,
    SalesOrder.SO_ID,
    FORMAT (SalesOrder.Created_Date, 'dd-MMM-yyyy') AS CREATED_DATE,
    TransferOrder.TO_ID,
    DELIVER_TO.Mobile_No AS DELIVER_TO_MOBILE_NO,
    DELIVER_TO.Email,
    Item.Prod_ID,
    CONCAT(Product.Part_No,' ',Product.[Description]) AS PART_NO_DESC,
    item.Ori_Qty
FROM 
    PackingSlip
    INNER JOIN TransferOrder
    ON [PackingSlip].[TO_ID] = TransferOrder.TO_ID

    INNER JOIN SalesOrder
    ON TransferOrder.So_ID = SalesOrder.SO_ID

    INNER JOIN Staff
    ON Staff.Staff_ID = SalesOrder.Sales_Person

    INNER JOIN [CollectAddress] AS DELIVER_TO
    ON SalesOrder.Deliver_To = DELIVER_TO.Collect_Address_ID

    INNER JOIN [Address] AS DELIVER_TO_ADDR
    ON DELIVER_TO.Address_ID = DELIVER_TO_ADDR.Address_ID

    INNER JOIN Item
    ON PackingSlip.PS_ID = Item.Ref_Doc_ID

    INNER JOIN Product
    ON Item.Prod_ID = Product.Prod_ID
WHERE   
    PackingSlip.PS_ID = ''