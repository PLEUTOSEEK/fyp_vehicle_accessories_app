SELECT
    Customer.Customer_ID,
    Customer.Name AS BILL_TO_NAME,
    BILL_TO_ADDR.Location_Name AS BILL_TO_LOCATION_NAME,
    BILL_TO_ADDR.Address AS BILL_TO_ADDRESS,
    BILL_TO_ADDR.City AS BILL_TO_CITY,
    BILL_TO_ADDR.Postal_Code AS BILL_TO_POSTAL_CODE,
    BILL_TO_ADDR.[State] AS BILL_TO_STATE,
    BILL_TO_ADDR.Country AS BILL_TO_COUNTRY,
    DELIVER_TO.Name AS DELIVER_TO_NAME,
    DELIVER_TO_ADDR.Location_Name AS DELIVER_TO_LOCATION_NAME,
    DELIVER_TO_ADDR.Address AS DELIVER_TO_ADDRESS,
    DELIVER_TO_ADDR.City AS DELIVER_TO_CITY,
    DELIVER_TO_ADDR.Postal_Code AS DELIVER_TO_POSTAL_CODE,
    DELIVER_TO_ADDR.[State] AS DELIVER_TO_STATE,
    DELIVER_TO_ADDR.Country AS DELIVER_TO_COUNTRY,
    SalesOrder.SO_ID,
    FORMAT (SalesOrder.Created_Date, 'dd-MMM-yyyy') AS CREATED_DATE,
    SalesOrder.Cust_PO_Reference,
    SalesOrder.Quot_ID,
    SalesOrder.Reference,
    SalesOrder.Currency_Code,
    FORMAT (SalesOrder.Required_Delivery_Date, 'dd-MMM-yyyy') AS REQUIRED_DELIVERY_DATE,
    SalesOrder.Payment_Term,
    SalesOrder.Shipment_Term,
    SALES_PERSON.Name AS SALES_PERSON_NAME,
    Customer.Office_Phone_No,
    Customer.Mobile_No,
    Customer.Home_Phone_No,
    SalesOrder.Gross,
    SalesOrder.Discount,
    SalesOrder.Sub_Total,
    SalesOrder.Nett
FROM
    Customer
    INNER JOIN [Address] AS BILL_TO_ADDR
    ON BILL_TO_ADDR.Address_ID = Customer.Bill_To_Addr

    INNER JOIN SalesOrder
    ON Customer.Customer_ID = SalesOrder.Bill_To_Cust

    INNER JOIN [CollectAddress] AS DELIVER_TO
    ON SalesOrder.Deliver_To = DELIVER_TO.Collect_Address_ID

    INNER JOIN [Address] AS DELIVER_TO_ADDR
    ON DELIVER_TO.Address_ID = DELIVER_TO_ADDR.Address_ID

    INNER JOIN Staff AS SALES_PERSON
    ON SalesOrder.Sales_Person = SALES_PERSON.Staff_ID

WHERE
    SalesOrder.SO_ID = ?
    
SELECT
    Product.Prod_ID,
    CONCAT(Product.Part_No,' ',Product.[Description]) AS PART_NO_DESC,
    Item.Remark,
    Item.Ori_Qty,
    Product.UOM,
    Item.Unit_Price,
    Item.Delivery_Date,
    Item.Excl_Amount,
    Item.Discount_Amount,
    Item.Incl_Amount
FROM
    SalesOrder
    INNER JOIN Item
    ON SalesOrder.SO_ID = Item.Ref_Doc_ID

    INNER JOIN Product
    ON Item.Prod_ID = Product.Prod_ID
WHERE
    SalesOrder.SO_ID = ?
