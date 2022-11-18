SELECT
    Customer.Customer_ID,
    Customer.Name AS BILL_TO_NAME,
    BILL_TO_ADDR.Location_Name,
    BILL_TO_ADDR.Address,
    BILL_TO_ADDR.City,
    BILL_TO_ADDR.Postal_Code,
    BILL_TO_ADDR.[State],
    BILL_TO_ADDR.Country,
    DELIVER_TO.Name AS DELIVER_TO_NAME,
    DELIVER_TO_ADDR.Location_Name,
    DELIVER_TO_ADDR.Address,
    DELIVER_TO_ADDR.City,
    DELIVER_TO_ADDR.Postal_Code,
    DELIVER_TO_ADDR.[State],
    DELIVER_TO_ADDR.Country,
    Quotation.QUOT_ID,
    FORMAT (Quotation.Created_Date, 'dd-MMM-yyyy') AS CREATED_DATE,
    Quotation.CI_ID,
    Quotation.Reference,
    Quotation.Currency_Code,
    FORMAT (Quotation.Quot_Validity_Date, 'dd-MMM-yyyy') AS QUOT_VALIDITY_DATE,
    FORMAT (Quotation.Required_Delivery_Date, 'dd-MMM-yyyy') AS REQUIRED_DELIVERY_DATE,
    Quotation.Payment_Term,
    Quotation.Shipment_Term,
    SALES_PERSON.Name AS SALES_PERSON_NAME,
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
    Quotation.Gross,
    Quotation.Discount,
    Quotation.Sub_Total,
    Quotation.Nett
FROM
    Customer
    INNER JOIN [Address] AS BILL_TO_ADDR
    ON Customer.Bill_To_Addr = BILL_TO_ADDR.Address_ID

    INNER JOIN Quotation
    ON Customer.Customer_ID = Quotation.Bill_To_Cust

    INNER JOIN [CollectAddress] AS DELIVER_TO
    ON Quotation.Deliver_To = DELIVER_TO.Collect_Address_ID

    INNER JOIN [Address] AS DELIVER_TO_ADDR
    ON DELIVER_TO.Address_ID = DELIVER_TO_ADDR.Address_ID

    INNER JOIN Staff AS SALES_PERSON
    ON Quotation.Sales_Person = SALES_PERSON.Staff_ID

    INNER JOIN Item
    ON Quotation.QUOT_ID = Item.Ref_Doc_ID

    INNER JOIN Product
    ON Item.Prod_ID = Product.Prod_ID
WHERE
    Quotation.QUOT_ID = ''