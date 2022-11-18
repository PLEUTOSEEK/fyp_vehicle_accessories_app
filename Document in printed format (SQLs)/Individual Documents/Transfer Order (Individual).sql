SELECT
    Staff.Name,
    Staff.Mobile_No,
    Staff.Extension_No,
    TransferOrder.TO_ID,
    FORMAT (TransferOrder.Actual_Created_Date, 'dd-MMM-yyyy') AS ACTUAL_CREATED_DATE,
    Place.Address_ID,
    TransferOrder.Req_Type,
    TransferOrder.Req_Type_Ref,
    Item.Prod_ID,
    CONCAT(Product.Part_No,' ',Product.[Description]) AS PART_NO_DESC,
    item.Ori_Qty,
    Product.UOM,
    item.Inventory_ID
FROM
    Staff
    INNER JOIN TransferOrder
    ON Staff.Staff_ID = TransferOrder.Person_In_Charge

    INNER JOIN Place
    ON TransferOrder.Destination = Place.Place_ID

    INNER JOIN Item
    ON TransferOrder.TO_ID = Item.Ref_Doc_ID

    INNER JOIN Product
    ON Item.Prod_ID = Product.Prod_ID
WHERE
    TransferOrder.TO_ID = ''