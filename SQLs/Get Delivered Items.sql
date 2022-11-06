SELECT
    ITEM_DELIVERED.Prod_ID,
    ITEM_DELIVERED.TOTAL_DELIVERED_QTY,
    ITEM_RETURNED.TOTAL_RETURNED_QTY,
    (ITEM_DELIVERED.TOTAL_DELIVERED_QTY - ITEM_RETURNED.TOTAL_RETURNED_QTY) AS TOTAL_RETURNABLE_QTY
FROM
    (
        SELECT
            Prod_ID, SUM(Qty) AS TOTAL_DELIVERED_QTY
        FROM
            Item
        WHERE
            Item.Ref_Doc_ID
                    IN (
                        SELECT
                            PackingSlip.PS_ID
                        FROM
                            PackingSlip
                        WHERE
                            PackingSlip.DO_ID   IN(
                                                    SELECT
                                                        DeliveryOrder.DO_ID
                                                    FROM
                                                        DeliveryOrder
                                                        INNER JOIN TransferOrder
                                                        ON DeliveryOrder.Reference_Type_Ref = TransferOrder.TO_ID
                                                        INNER JOIN SalesOrder
                                                        ON TransferOrder.Req_Type_Ref = SalesOrder.SO_ID
                                                    WHERE
                                                        SalesOrder.SO_ID = ? AND
                                                        DeliveryOrder.Status = 'DELIVERED'
                                                )
                    )
        GROUP BY
            Prod_ID
    ) AS ITEM_DELIVERED
    INNER JOIN
    (
        SELECT
            Prod_ID, SUM(Qty) AS TOTAL_RETURNED_QTY
        FROM
            Item
        WHERE
            Item.Ref_Doc_ID
                    IN (
                        SELECT
                            RDN_ID
                        FROM
                            ReturnDeliveryNote
                        WHERE
                            SO_ID = ? AND
                            (Status IN ('RETURNED', 'APPROVED'))
                        )
        GROUP BY
            Prod_ID
    ) AS ITEM_RETURNED
    ON ITEM_DELIVERED.Prod_ID = ITEM_RETURNED.Prod_ID