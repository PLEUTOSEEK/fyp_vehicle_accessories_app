SELECT
    Customer.Customer_ID,
    Customer.Name,
    BILL_TO_ADDR.Location_Name,
    BILL_TO_ADDR.Address,
    BILL_TO_ADDR.City,
    BILL_TO_ADDR.Postal_Code,
    BILL_TO_ADDR.[State],
    BILL_TO_ADDR.Country,
    DELIVER_TO.Name,
    DELIVER_TO_ADDR.Location_Name,
    DELIVER_TO_ADDR.Address,
    DELIVER_TO_ADDR.City,
    DELIVER_TO_ADDR.Postal_Code,
    DELIVER_TO_ADDR.[State],
    DELIVER_TO_ADDR.Country,
    SalesOrder.SO_ID,
    FORMAT (SalesOrder.Created_Date, 'dd-MMM-yyyy') AS CREATED_DATE,
    SalesOrder.Cust_PO_Reference,
    SalesOrder.Quot_ID,
    SalesOrder.Reference,
    SalesOrder.Currency_Code,
    FORMAT (SalesOrder.Required_Delivery_Date, 'dd-MMM-yyyy') AS REQUIRED_DELIVERY_DATE,
    SalesOrder.Payment_Term,
    SalesOrder.Shipment_Term,
    SALES_PERSON.Name,
    Customer.Office_Phone_No,
    Customer.Mobile_No,
    Customer.Home_Phone_No,
    Product.Prod_ID,
    CONCAT(Product.Part_No,' ',Product.[Description]) AS PART_NO_DESC,
    Item.Remark,
    Item.Ori_Qty,
    Product.UOM,
    Item.Unit_Price,
    Item.Delivery_Date,
    Item.Excl_Amount,
    Item.Discount_Amount,
    Item.Incl_Amount,
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

    INNER JOIN Item
    ON SalesOrder.SO_ID = Item.Ref_Doc_ID

    INNER JOIN Product
    ON Item.Prod_ID = Product.Prod_ID