SELECT
    ITEM_DELIVERED.Prod_ID,
    ITEM_DELIVERED.TOTAL_DELIVERED_QTY,
    ISNULL(ITEM_RETURNED.TOTAL_RETURNED_QTY,0) AS TOTAL_RETURNED_QTY,
    (ITEM_DELIVERED.TOTAL_DELIVERED_QTY - ISNULL(ITEM_RETURNED.TOTAL_RETURNED_QTY,0)) AS TOTAL_RETURNABLE_QTY
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
                                                        INNER JOIN SalesOrder
                                                        ON DeliveryOrder.SO_ID = SalesOrder.SO_ID
                                                    WHERE
                                                        SalesOrder.SO_ID = ? AND
                                                        DeliveryOrder.Status = 'DELIVERED'
                                                )
                    )
        GROUP BY
            Prod_ID
    ) AS ITEM_DELIVERED
    LEFT JOIN
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
                            (Status NOT IN ('REJECTED'))
                        )
        GROUP BY
            Prod_ID
    ) AS ITEM_RETURNED
    ON ITEM_DELIVERED.Prod_ID = ITEM_RETURNED.Prod_ID