SELECT
    DeliveryOrder.DO_ID,
    Staff.Name AS STAFF_NAME,
    SalesOrder.Shipment_Term,
    DELIVER_TO.Name AS DELIVER_TO_NAME,
    DELIVER_TO_ADDR.Location_Name,
    DELIVER_TO_ADDR.Address,
    DELIVER_TO_ADDR.City,
    DELIVER_TO_ADDR.Postal_Code,
    DELIVER_TO_ADDR.[State],
    DELIVER_TO_ADDR.Country,
    FORMAT (DeliveryOrder.Delivery_Date , 'dd-MMM-yyyy')AS DO_DLVR_DATE,
    FORMAT (DeliveryOrder.Created_Date , 'dd-MMM-yyyy') AS DO_CREATED_DATE,
    SalesOrder.SO_ID,
    FORMAT (SalesOrder.Created_Date, 'dd-MMM-yyyy') AS SO_CREATED_DATE,
    DeliveryOrder.Reference,
    DELIVER_TO.Mobile_No,
    DELIVER_TO.Email,
    Item.Prod_ID,
    CONCAT(Product.Part_No,' ',Product.[Description]) AS PART_NO_DESC,
    Item.Ori_Qty

FROM 
    DeliveryOrder
    INNER JOIN SalesOrder
    ON SalesOrder.SO_ID = DeliveryOrder.SO_ID

    INNER JOIN Staff
    ON SalesOrder.Sales_Person = Staff.Staff_ID

    INNER JOIN CollectAddress AS DELIVER_TO
    ON SalesOrder.Deliver_To = DELIVER_TO.Collect_Address_ID

    INNER JOIN [Address] AS DELIVER_TO_ADDR
    ON DELIVER_TO.Address_ID = DELIVER_TO_ADDR.Address_ID
 
    INNER JOIN Item
    ON DeliveryOrder.DO_ID = Item.Ref_Doc_ID

    INNER JOIN Product
    ON Item.Prod_ID = Product.Prod_ID
WHERE
    DeliveryOrder.DO_ID = ''