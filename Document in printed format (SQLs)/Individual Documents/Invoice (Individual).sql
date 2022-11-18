SELECT
    Invoice.INV_ID,
    Customer.Customer_ID,
    Customer.Name,
    BILL_TO_ADDR.Location_Name,
    BILL_TO_ADDR.Address,
    BILL_TO_ADDR.City,
    BILL_TO_ADDR.Postal_Code,
    BILL_TO_ADDR.[State],
    BILL_TO_ADDR.Country,
    Invoice.INV_ID,
    FORMAT (Invoice.Created_Date, 'dd-MMM-yyyy') AS INV_CREATED_DATE,
    SalesOrder.SO_ID,
    FORMAT (SalesOrder.Created_Date, 'dd-MMM-yyyy') AS SO_CREATED_DATE,
    SalesOrder.Cust_PO_Reference,
    Invoice.Reference,
    SalesOrder.Currency_Code,
    SalesOrder.Payment_Term,
    SalesOrder.Shipment_Term,
    SalesOrder.Sales_Person,
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
    Invoice.Gross,
    Invoice.Discount,
    invoice.Sub_Total,
    Invoice.Total_Payable
FROM 
    Customer
    INNER JOIN [Address] AS BILL_TO_ADDR
    ON Customer.Bill_To_Addr = BILL_TO_ADDR.[Address_ID]

    INNER JOIN SalesOrder
    ON SalesOrder.Bill_To_Cust = Customer.Customer_ID

    INNER JOIN Invoice
    ON Invoice.SO_ID = SalesOrder.SO_ID

    INNER JOIN Item
    ON Invoice.INV_ID = Item.Ref_Doc_ID

    INNER JOIN Product
    ON Item.Prod_ID = Product.Prod_ID
WHERE
    Invoice.INV_ID = ''