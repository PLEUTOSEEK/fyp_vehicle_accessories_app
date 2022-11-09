SELECT
    SO_SO_ID,
    SO_ITEM_PROD_ID,
    SO_ITEM_INVENTORY_ID,
    SO_ITEM_DELIVERY_DATE,
    SO_ITEM_UNIT_PRICE,
    SO_ITEM_QTY,
    INV_ITEM_QTY,
    (SO_ITEM_QTY - INV_ITEM_QTY) AS ITEM_NOT_YET_BILL
FROM
(
    SELECT
        SO_ID AS SO_SO_ID,
        Item.Prod_ID AS SO_ITEM_PROD_ID,
        Item.Inventory_ID AS SO_ITEM_INVENTORY_ID,
        Item.Delivery_Date AS SO_ITEM_DELIVERY_DATE,
        Item.Unit_Price AS SO_ITEM_UNIT_PRICE,
        Item.Qty AS SO_ITEM_QTY
    FROM
        SalesOrder
        INNER JOIN Item
        ON SalesOrder.SO_ID = Item.Ref_Doc_ID
) AS SO_ITEMS
INNER JOIN
(
    SELECT
        Item.Prod_ID AS INV_ITEM_PROD_ID,
        Item.Inventory_ID AS INV_ITEM_INVENTORY_ID,
        Item.Delivery_Date AS INV_ITEM_DELIVERY_DATE,
        SUM(Item.Qty) AS INV_ITEM_QTY
    FROM
        Invoice
        INNER JOIN Item
        ON Invoice.INV_ID = Item.Ref_Doc_ID
    WHERE 
        Invoice.SO_ID = ?
    GROUP BY
        Item.Prod_ID,
        Item.Inventory_ID,
        Item.Delivery_Date
) AS INV_ITEMS
ON
    SO_ITEMS.SO_ITEM_PROD_ID = INV_ITEMS.INV_ITEM_PROD_ID AND
    SO_ITEMS.SO_ITEM_INVENTORY_ID = INV_ITEMS.INV_ITEM_INVENTORY_ID AND
    SO_ITEMS.SO_ITEM_DELIVERY_DATE = INV_ITEMS.INV_ITEM_DELIVERY_DATE