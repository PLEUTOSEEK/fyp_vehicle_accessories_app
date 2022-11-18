/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  Tee Zhuo Xuan
 * Created: Nov 15, 2022
 */

-- Detect Payment Exceed
SELECT
    FINAL_RESULT_A.SO_ID,
    FINAL_RESULT_A.Customer_ID,
    FINAL_RESULT_A.Name,
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
        SUM(Receipt.Paid_Amount) AS RECEIVED_AMOUNT,
        Invoice.Total_Payable - SUM(Receipt.Paid_Amount) AS BAL_UNPAID,
        Invoice.Total_Payable
    FROM
        Invoice
        LEFT JOIN Receipt
        ON Invoice.INV_ID = Receipt.INV_ID
    GROUP BY
        Invoice.SO_ID,
        Invoice.Total_Payable) AS FINAL_RESULT_B
ON FINAL_RESULT_A.SO_ID = FINAL_RESULT_B.SO_ID


