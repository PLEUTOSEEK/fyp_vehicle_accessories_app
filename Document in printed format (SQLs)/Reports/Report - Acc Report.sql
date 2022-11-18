-- Report Body
SELECT
    FINAL_RESULT_A.SO_ID,
    FINAL_RESULT_A.Customer_ID,
    FINAL_RESULT_A.Name,
    FINAL_RESULT_B.INV_ID,
    FINAL_RESULT_B.RECEIVED_AMOUNT,
    FINAL_RESULT_B.BAL_UNPAID,
    FINAL_RESULT_B.Total_Payable,
    FINAL_RESULT_A.IS_EXCEED_PAYMENT_TERM
FROM
    (SELECT
        SO_INFORMATION.SO_ID,
        SO_INFORMATION.Customer_ID,
        SO_INFORMATION.Name,
        CASE 
            WHEN DATEDIFF(DAY,SO_INFORMATION.Baseline_Documet_Date, SO_INFORMATION.DOC_DATE_TIME) > Days_Limit THEN 'YES'
            ELSE 'NO'
        END AS IS_EXCEED_PAYMENT_TERM
    FROM
        (SELECT
            SalesOrder.SO_ID,
            Customer.Customer_ID,
            Customer.Name,
            PaymentTerm.Baseline_Documet_Date,
            PaymentTerm.Days_Limit,
            CASE SalesOrder.Payment_Term
                WHEN 'SO' THEN (SELECT  
                                    SalesOrder.Created_Date
                                FROM
                                    SalesOrder
                                WHERE
                                    SalesOrder.SO_ID = Invoice.SO_ID)
                WHEN 'DO' THEN (SELECT  
                                    TOP(1)
                                    DeliveryOrder.Created_Date
                                FROM
                                    DeliveryOrder
                                WHERE
                                    DeliveryOrder.SO_ID = Invoice.SO_ID
                                ORDER BY 
                                    DeliveryOrder.Created_Date DESC)
                ELSE
                    Invoice.Created_Date
            END AS DOC_DATE_TIME
        FROM
            Invoice 
            INNER JOIN SalesOrder
            ON Invoice.SO_ID = SalesOrder.SO_ID
            
            INNER JOIN PaymentTerm
            ON PaymentTerm.Pymt_Term_ID = SalesOrder.Payment_Term

            INNER JOIN Customer
            ON Customer.Customer_ID = SalesOrder.Bill_To_Cust
        ) AS SO_INFORMATION
    ) AS FINAL_RESULT_A

    INNER JOIN

    (SELECT
        Invoice.SO_ID,
        Invoice.INV_ID,
        SUM(Receipt.Paid_Amount) AS RECEIVED_AMOUNT, 
        Invoice.Total_Payable - SUM(Receipt.Paid_Amount) AS BAL_UNPAID,
        Invoice.Total_Payable
    FROM
        Invoice 
        LEFT JOIN Receipt
        ON Invoice.INV_ID = Receipt.INV_ID
    GROUP BY
        Invoice.SO_ID,
        Invoice.INV_ID,
        Invoice.Total_Payable
    ) AS FINAL_RESULT_B

    ON FINAL_RESULT_A.SO_ID = FINAL_RESULT_B.SO_ID


-- Pie Chart
SELECT
    IS_EXCEED_PAYMENT_TERM,
    COUNT(IS_EXCEED_PAYMENT_TERM) AS COUNT_IS_EXCEED_PAYMENT_TERM
FROM
    (SELECT
        CASE 
            WHEN DATEDIFF(DAY,Baseline_Documet_Date, DOC_DATE_TIME) > Days_Limit THEN 'YES'
            ELSE 'NO'
        END AS IS_EXCEED_PAYMENT_TERM
    FROM
        (SELECT
            PaymentTerm.Baseline_Documet_Date,
            PaymentTerm.Days_Limit,
            CASE SalesOrder.Payment_Term
                WHEN 'SO' THEN (SELECT  
                                    SalesOrder.Created_Date
                                FROM
                                    SalesOrder
                                WHERE
                                    SalesOrder.SO_ID = Invoice.SO_ID)
                WHEN 'DO' THEN (SELECT  
                                    TOP(1)
                                    DeliveryOrder.Created_Date
                                FROM
                                    DeliveryOrder
                                WHERE
                                    DeliveryOrder.SO_ID = Invoice.SO_ID
                                ORDER BY 
                                    DeliveryOrder.Created_Date DESC)
                ELSE
                    Invoice.Created_Date
            END AS DOC_DATE_TIME
        FROM
            Invoice 
            INNER JOIN SalesOrder
            ON Invoice.SO_ID = SalesOrder.SO_ID
            
            INNER JOIN PaymentTerm
            ON PaymentTerm.Pymt_Term_ID = SalesOrder.Payment_Term

            INNER JOIN Customer
            ON Customer.Customer_ID = SalesOrder.Bill_To_Cust
        ) AS SO_INFORMATION
    ) AS FINAL_RESULT_PYMT_TERM
GROUP BY    
    IS_EXCEED_PAYMENT_TERM