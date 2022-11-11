/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  Tee Zhuo Xuan
 * Created: Nov 10, 2022
 */

SELECT
    Product_ID, SUM(Ready_Qty)
FROM
    Inventory
WHERE
    Product_ID = ''
GROUP BY
    Product_ID