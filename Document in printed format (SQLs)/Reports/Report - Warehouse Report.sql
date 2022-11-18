-- Ready, Reserved, Total Quantities
SELECT
    SUM(Inventory.Ready_Qty) AS TTL_READY_QTY,
    SUM(Inventory.Reserved_Qty) AS TTL_RESERVED_QTY,
    SUM(Inventory.Reserved_Qty) + SUM(Inventory.Ready_Qty) AS TTL_QTY
FROM
    Inventory

-- Products In Levels
SELECT
    Prod_ID,
    TTL_INVENTORY,
    CASE
        WHEN TTL_INVENTORY >= Maximum_Level THEN 'MAXIMUM_LEVEL'
        WHEN TTL_INVENTORY >= Average_Level THEN 'AVERAGE_LEVEL'
        WHEN TTL_INVENTORY >= Minimum_Level THEN 'MINIMUM_LEVEL'
        WHEN TTL_INVENTORY >= Reorder_Level THEN 'REORDER_LEVEL'
        ELSE 'DANGER_LEVEL'
    END AS LEVEL_STATUS
FROM (
    SELECT
        Product.Prod_ID,
        Product.Maximum_Level,
        Product.Average_Level,
        Product.Minimum_Level,
        Product.Reorder_Level,
        Product.Danger_Level,
        SUM(Inventory.Reserved_Qty) + SUM(Inventory.Ready_Qty) AS TTL_INVENTORY
    FROM 
        Product 
        LEFT JOIN Inventory
        ON Product.Prod_ID = Inventory.Inventory_ID
    GROUP BY
        Product.Prod_ID,        
        Product.Maximum_Level,
        Product.Average_Level,
        Product.Minimum_Level,
        Product.Reorder_Level,
        Product.Danger_Level
) AS PROD_TTL_INVENTORY

-- Report Body
SELECT
    PRODUCT_STOCK_LEVEL_STATUS.Prod_ID,
    PRODUCT_STOCK_LEVEL_STATUS.Part_No,
    PRODUCT_STOCK_LEVEL_STATUS.Reorder_Level,
    PRODUCT_STOCK_LEVEL_STATUS.LEVEL_STATUS,
    PRODUCT_STORED_PLACES.Place_ID,
    PRODUCT_STORED_PLACES.Place_Name,
    PRODUCT_STORED_PLACES.Ready_Qty,
    PRODUCT_STORED_PLACES.Reserved_Qty
FROM
(SELECT
    PRODUCT_TTL_INVENTORY.Prod_ID,
    PRODUCT_TTL_INVENTORY.TTL_INVENTORY,
    PRODUCT_TTL_INVENTORY.Part_No,
    PRODUCT_TTL_INVENTORY.Reorder_Level,
    CASE
        WHEN PRODUCT_TTL_INVENTORY.TTL_INVENTORY >= Maximum_Level THEN 'MAXIMUM_LEVEL'
        WHEN PRODUCT_TTL_INVENTORY.TTL_INVENTORY >= Average_Level THEN 'AVERAGE_LEVEL'
        WHEN PRODUCT_TTL_INVENTORY.TTL_INVENTORY >= Minimum_Level THEN 'MINIMUM_LEVEL'
        WHEN PRODUCT_TTL_INVENTORY.TTL_INVENTORY >= Reorder_Level THEN 'REORDER_LEVEL'
        ELSE 'DANGER_LEVEL'
    END AS LEVEL_STATUS
FROM 
    (SELECT
        Product.Prod_ID,
        Product.Part_No,
        Product.Maximum_Level,
        Product.Average_Level,
        Product.Minimum_Level,
        Product.Reorder_Level,
        Product.Danger_Level,
        SUM(Inventory.Reserved_Qty + Inventory.Ready_Qty) AS TTL_INVENTORY
    FROM 
        Product 
        LEFT JOIN Inventory
        ON Product.Prod_ID = Inventory.Inventory_ID
    GROUP BY
        Product.Prod_ID, 
        Product.Part_No,       
        Product.Maximum_Level,
        Product.Average_Level,
        Product.Minimum_Level,
        Product.Reorder_Level,
        Product.Danger_Level
    ) AS PRODUCT_TTL_INVENTORY
) AS PRODUCT_STOCK_LEVEL_STATUS
INNER JOIN (SELECT
                Inventory.Product_ID,
                Place.Place_ID,
                Place.Place_Name,
                Inventory.Ready_Qty,
                Inventory.Reserved_Qty
            FROM 
                Inventory
                INNER JOIN Place
                ON Inventory.Inventory_ID = Place.Place_ID
            ) AS PRODUCT_STORED_PLACES
ON PRODUCT_STOCK_LEVEL_STATUS.Prod_ID = PRODUCT_STORED_PLACES.Product_ID
ORDER BY    
    PRODUCT_STOCK_LEVEL_STATUS.Prod_ID ASC

-- Pie Chart
SELECT
    LEVEL_STATUS,
    COUNT(Prod_ID) AS COUNT_PRODUCTS
FROM(
    SELECT
        Prod_ID,
        TTL_INVENTORY,
        CASE
            WHEN TTL_INVENTORY >= Maximum_Level THEN 'MAXIMUM_LEVEL'
            WHEN TTL_INVENTORY >= Average_Level THEN 'AVERAGE_LEVEL'
            WHEN TTL_INVENTORY >= Minimum_Level THEN 'MINIMUM_LEVEL'
            WHEN TTL_INVENTORY >= Reorder_Level THEN 'REORDER_LEVEL'
            ELSE 'DANGER_LEVEL'
        END AS LEVEL_STATUS
    FROM (
        SELECT
            Product.Prod_ID,
            Product.Maximum_Level,
            Product.Average_Level,
            Product.Minimum_Level,
            Product.Reorder_Level,
            Product.Danger_Level,
            SUM(Inventory.Reserved_Qty) + SUM(Inventory.Ready_Qty) AS TTL_INVENTORY
        FROM 
            Product 
            LEFT JOIN Inventory
            ON Product.Prod_ID = Inventory.Inventory_ID
        GROUP BY
            Product.Prod_ID,        
            Product.Maximum_Level,
            Product.Average_Level,
            Product.Minimum_Level,
            Product.Reorder_Level,
            Product.Danger_Level
    ) AS PROD_TTL_INVENTORY
) AS COUNT_PRODUCT_BASED_ON_LEVEL_STATUS
GROUP BY
    LEVEL_STATUS
