/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ItemDAO {

    public static boolean updateItemsByDoc(List<Item> newItems, String code) throws SQLException {
        deleteItemsByDoc(code);
        return insertItemsByDoc(newItems, code);
    }

    public static boolean deleteItemsByDoc(String code) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "DELETE FROM "
                    + "Item "
                    + "WHERE "
                    + "Ref_Doc_ID = ? ;";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, code);
            ps.execute();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                /* ignored */
            }
            try {
                conn.close();
            } catch (Exception e) {
                /* ignored */
            }
        }

    }

    public static boolean insertItemsByDoc(List<Item> items, String code) throws SQLException {
        //delete all item from that doc
        //replace all items with new items
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        try {
            conn = SQLDatabaseConnection.openConn();

            conn.setAutoCommit(false);

            query = "INSERT INTO Item ( "
                    + "Prod_ID, "
                    + "Ref_Doc_ID, "
                    + "Inventory_ID, "
                    + "Reason, "
                    + "Remark, "
                    + "PackingSlipNo, "
                    + "Qty, "
                    + "Ori_Qty, "
                    + "Unit_Price, "
                    + "Delivery_Date, "
                    + "Excl_Amount, "
                    + "Discount_Amount, "
                    + "Incl_Amount "
                    + ") "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(query);
            // bind parameter

            int i = 0;

            for (Item item : items) {

                ps.setString(1, item.getProduct().getProdID());
                ps.setString(2, code);
                ps.setString(3, item.getInventory().getInventoryID());
                ps.setString(4, item.getReason());
                ps.setString(5, item.getRemark());
                ps.setInt(7, item.getQty());
                ps.setInt(8, item.getOriQty());
                ps.setBigDecimal(9, item.getUnitPrice());
                ps.setDate(10, item.getDlvrDate());
                ps.setBigDecimal(11, item.getExclTaxAmt());
                ps.setBigDecimal(12, item.getDiscAmt());
                ps.setBigDecimal(13, item.getInclTaxAmt());

                ps.addBatch();
                i++;

                if (i % 1000 == 0 || i == items.size()) {
                    ps.executeBatch(); // Execute every 1000 items.
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                /* ignored */
            }
            try {
                conn.close();
            } catch (Exception e) {
                /* ignored */
            }
        }
    }

    public static List<Item> getItemsByCode(String code) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        List<Item> items = new ArrayList<Item>();
        Item item = new Item();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM Item WHERE Ref_Doc_ID = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, code);
            rs = ps.executeQuery();

            while (rs.next()) {
                item = new Item();

                item.setProduct(ProductDAO.getProductByID(rs.getString("Prod_ID")));
                item.setInventory(InventoryDAO.getInventoryByID(rs.getString("Inventory_ID")));
                item.setRemark(rs.getString("Remark"));
                item.setQty(rs.getInt("Qty"));
                item.setUnitPrice(rs.getBigDecimal("Unit_Price"));
                item.setDlvrDate(rs.getDate("Delivery_Date"));
                item.setExclTaxAmt(rs.getBigDecimal("Excl_Amount"));
                item.setDiscAmt(rs.getBigDecimal("Discount_Amount"));
                item.setInclTaxAmt(rs.getBigDecimal("Incl_Amount"));
                items.add(item);
            }

            //return object
            return items;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                /* ignored */
            }
            try {
                conn.close();
            } catch (Exception e) {
                /* ignored */
            }
        }
    }

    public static List<Item> getReturnableItemsBySO(String code) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        List<Item> items = new ArrayList<>();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT "
                    + "    ITEM_DELIVERED.Prod_ID, "
                    + "    ITEM_DELIVERED.TOTAL_DELIVERED_QTY, "
                    + "    ITEM_RETURNED.TOTAL_RETURNED_QTY, "
                    + "    (ITEM_DELIVERED.TOTAL_DELIVERED_QTY - ITEM_RETURNED.TOTAL_RETURNED_QTY) AS TOTAL_RETURNABLE_QTY "
                    + "FROM "
                    + "    ( "
                    + "        SELECT "
                    + "            Prod_ID, SUM(Qty) AS TOTAL_DELIVERED_QTY "
                    + "        FROM "
                    + "            Item "
                    + "        WHERE "
                    + "            Item.Ref_Doc_ID "
                    + "                    IN ( "
                    + "                        SELECT "
                    + "                            PackingSlip.PS_ID "
                    + "                        FROM "
                    + "                            PackingSlip "
                    + "                        WHERE "
                    + "                            PackingSlip.DO_ID   IN( "
                    + "                                                    SELECT "
                    + "                                                        DeliveryOrder.DO_ID "
                    + "                                                    FROM "
                    + "                                                        DeliveryOrder "
                    + "                                                        INNER JOIN TransferOrder "
                    + "                                                        ON DeliveryOrder.Reference_Type_Ref = TransferOrder.TO_ID "
                    + "                                                        INNER JOIN SalesOrder "
                    + "                                                        ON TransferOrder.Req_Type_Ref = SalesOrder.SO_ID "
                    + "                                                    WHERE "
                    + "                                                        SalesOrder.SO_ID = ? AND "
                    + "                                                        DeliveryOrder.Status = 'DELIVERED' "
                    + "                                                ) "
                    + "                    ) "
                    + "        GROUP BY "
                    + "            Prod_ID "
                    + "    ) AS ITEM_DELIVERED "
                    + "    INNER JOIN "
                    + "    ( "
                    + "        SELECT "
                    + "            Prod_ID, SUM(Qty) AS TOTAL_RETURNED_QTY "
                    + "        FROM "
                    + "            Item "
                    + "        WHERE "
                    + "            Item.Ref_Doc_ID "
                    + "                    IN ( "
                    + "                        SELECT "
                    + "                            RDN_ID "
                    + "                        FROM "
                    + "                            ReturnDeliveryNote "
                    + "                        WHERE "
                    + "                            SO_ID = ? AND "
                    + "                            (Status IN ('RETURNED', 'APPROVED')) "
                    + "                        ) "
                    + "        GROUP BY "
                    + "            Prod_ID "
                    + "    ) AS ITEM_RETURNED "
                    + "    ON ITEM_DELIVERED.Prod_ID = ITEM_RETURNED.Prod_ID";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, code);
            ps.setString(2, code);
            rs = ps.executeQuery();

            while (rs.next()) {
                Item item = new Item();

                item.setProduct(ProductDAO.getProductByID(rs.getString("Prod_ID")));
                item.setQty(rs.getInt("TOTAL_RETURNABLE_QTY"));
                item.setOriQty(item.getQty());

                items.add(item);
            }

            //return object
            return items;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                /* ignored */
            }
            try {
                conn.close();
            } catch (Exception e) {
                /* ignored */
            }
        }
    }
}
