-- TOTAL CASES
SELECT
    Staff.Staff_ID,
    COUNT(SalesOrder.SO_ID) AS SO_TTL_CASES,
    SUM(SalesOrder.Gross) AS SO_GROSS,
    SUM(SalesOrder.Discount) AS SO_DISC,
    SUM(SalesOrder.Sub_Total) AS SO_SUB_TTL,
    SUM(SalesOrder.Nett ) AS SO_NETT
FROM
    Staff 
    INNER JOIN SalesOrder
    ON Staff.Staff_ID = SalesOrder.Sales_Person
WHERE
    Staff.Staff_ID = '' AND
    SalesOrder.Actual_Created_Date BETWEEN '' AND ''
GROUP BY
    Staff.Staff_ID

-- NEW CASES
SELECT
    Staff.Staff_ID,
    COUNT(SalesOrder.SO_ID) AS SO_TTL_CASES,
    SUM(SalesOrder.Gross) AS SO_GROSS,
    SUM(SalesOrder.Discount) AS SO_DISC,
    SUM(SalesOrder.Sub_Total) AS SO_SUB_TTL,
    SUM(SalesOrder.Nett ) AS SO_NETT
FROM
    Staff 
    INNER JOIN SalesOrder
    ON Staff.Staff_ID = SalesOrder.Sales_Person
WHERE
    Staff.Staff_ID = '' AND
    SalesOrder.[Status] = 'NEW' AND
    SalesOrder.Actual_Created_Date BETWEEN '' AND ''
GROUP BY
    Staff.Staff_ID

-- ON HOLD CASES
SELECT
    Staff.Staff_ID,
    COUNT(SalesOrder.SO_ID) AS SO_TTL_CASES,
    SUM(SalesOrder.Gross) AS SO_GROSS,
    SUM(SalesOrder.Discount) AS SO_DISC,
    SUM(SalesOrder.Sub_Total) AS SO_SUB_TTL,
    SUM(SalesOrder.Nett ) AS SO_NETT
FROM
    Staff 
    INNER JOIN SalesOrder
    ON Staff.Staff_ID = SalesOrder.Sales_Person
WHERE
    Staff.Staff_ID = '' AND
    SalesOrder.[Status] = 'ON_HOLD' AND
    SalesOrder.Actual_Created_Date BETWEEN '' AND ''
GROUP BY
    Staff.Staff_ID
    
-- IN PROGRESS CASES
SELECT
    Staff.Staff_ID,
    COUNT(SalesOrder.SO_ID) AS SO_TTL_CASES,
    SUM(SalesOrder.Gross) AS SO_GROSS,
    SUM(SalesOrder.Discount) AS SO_DISC,
    SUM(SalesOrder.Sub_Total) AS SO_SUB_TTL,
    SUM(SalesOrder.Nett ) AS SO_NETT
FROM
    Staff 
    INNER JOIN SalesOrder
    ON Staff.Staff_ID = SalesOrder.Sales_Person
WHERE
    Staff.Staff_ID = '' AND
    SalesOrder.[Status] = 'IN_PROGRESS' AND
    SalesOrder.Actual_Created_Date BETWEEN '' AND ''
GROUP BY
    Staff.Staff_ID

-- PARTIALLY COMPLETED CASES
SELECT
    Staff.Staff_ID,
    COUNT(SalesOrder.SO_ID) AS SO_TTL_CASES,
    SUM(SalesOrder.Gross) AS SO_GROSS,
    SUM(SalesOrder.Discount) AS SO_DISC,
    SUM(SalesOrder.Sub_Total) AS SO_SUB_TTL,
    SUM(SalesOrder.Nett ) AS SO_NETT
FROM
    Staff 
    INNER JOIN SalesOrder
    ON Staff.Staff_ID = SalesOrder.Sales_Person
WHERE
    Staff.Staff_ID = '' AND
    SalesOrder.[Status] = 'PARTIALLY_COMPLETED' AND
    SalesOrder.Actual_Created_Date BETWEEN '' AND ''
GROUP BY
    Staff.Staff_ID

-- COMPLETED CASES
SELECT
    Staff.Staff_ID,
    COUNT(SalesOrder.SO_ID) AS SO_TTL_CASES,
    SUM(SalesOrder.Gross) AS SO_GROSS,
    SUM(SalesOrder.Discount) AS SO_DISC,
    SUM(SalesOrder.Sub_Total) AS SO_SUB_TTL,
    SUM(SalesOrder.Nett ) AS SO_NETT
FROM
    Staff 
    INNER JOIN SalesOrder
    ON Staff.Staff_ID = SalesOrder.Sales_Person
WHERE
    Staff.Staff_ID = '' AND
    SalesOrder.[Status] = 'COMPLETED' AND
    SalesOrder.Actual_Created_Date BETWEEN '' AND ''
GROUP BY
    Staff.Staff_ID

-- Report Body
SELECT
    SalesOrder.Bill_To_Cust,
    SalesOrder.SO_ID,
    SalesOrder.Gross,
    SalesOrder.Discount,
    SalesOrder.Sub_Total,
    SalesOrder.Nett,
    SalesOrder.[Status]
FROM 
    Staff 
    INNER JOIN SalesOrder
    ON Staff.Staff_ID = SalesOrder.Sales_Person
WHERE
    Staff.Staff_ID = '' AND
    SalesOrder.Actual_Created_Date BETWEEN '' AND ''