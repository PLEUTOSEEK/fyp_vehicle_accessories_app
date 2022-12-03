/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  Tee Zhuo Xuan
 * Created: Nov 30, 2022
 */

UPDATE
	INVT
SET
	INVT.Reserved_Qty = INVT.Reserved_Qty - PS_TABLE.Ori_Qty,
	INVT.Total_Qty = INVT.Total_Qty - PS_TABLE.Ori_Qty
FROM
	Inventory AS INVT
	INNER JOIN
	(
	SELECT
		Item.Ori_Qty,
		Item.Inventory_ID
	FROM
		PackingSlip
		INNER JOIN Item
		ON PackingSlip.PS_ID = Item.Ref_Doc_ID
	) AS PS_TABLE

	ON  INVT.Inventory_ID = PS_TABLE.Inventory_ID