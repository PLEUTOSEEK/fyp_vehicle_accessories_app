/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  Tee Zhuo Xuan
 * Created: Nov 8, 2022
 */

SELECT
    PackingSlip.PS_ID
FROM
    PackingSlip INNER JOIN
    TransferOrder
    ON PackingSlip.TO_ID = TransferOrder.TO_ID INNER JOIN
    SalesOrder
    ON TransferOrder.Req_Type_Ref = SalesOrder.SO_ID
WHERE
    SalesOrder.SO_ID = ?
    AND TransferOrder.[Status] = 'TRANFERRED'
