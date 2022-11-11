/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Inventory;
import Entity.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class InventoryDAO {

    public static Inventory getInventoryByID(String inventoryID) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        Inventory inventory = new Inventory();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM Inventory WHERE Inventory_ID = ?";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, inventoryID);
            rs = ps.executeQuery();

            if (rs.next()) {
                inventory.setInventoryID(rs.getString("Inventory_ID"));
                inventory.setStorePlace(PlaceDAO.getPlaceByID(rs.getString("Place_ID")));
                inventory.setProduct(ProductDAO.getProductByID(rs.getString("Product_ID")));
                inventory.setReservedQty(rs.getInt("Reserved_Qty"));
                inventory.setReadyQty(rs.getInt("Ready_Qty"));
                inventory.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));
            }

            //return object
            return inventory;
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

    public static boolean reserveQtyForSalesDoc(Item item) {
        /*

         */

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE "
                    + "    Inventory "
                    + "SET "
                    + "    Reserved_Qty = Reserved_Qty + ?, "
                    + "    Ready_Qty = Ready_Qty - ? "
                    + "WHERE "
                    + "    Inventory_ID = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setInt(1, item.getQty());
            ps.setInt(2, item.getQty());
            ps.setString(3, item.getInventory().getInventoryID());

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

    public static List<Inventory> getReadyInventoriesByProdID(String code, Integer ttlDemandQty) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        List<Inventory> inventories = new ArrayList<>();

        Integer leftDemandQty = ttlDemandQty;

        try {
            conn = SQLDatabaseConnection.openConn();
            int offset = 0;
            while (leftDemandQty > 0) {

                query = "SELECT "
                        + "    Inventory_ID, Ready_Qty "
                        + "FROM  "
                        + "    Inventory "
                        + "WHERE "
                        + "    Ready_Qty > 0 AND "
                        + "    Product_ID = ? "
                        + "ORDER BY  "
                        + "    Ready_Qty DESC "
                        + "OFFSET ? ROWS "
                        + "FETCH NEXT 1 ROWS ONLY;";
                ps = conn.prepareStatement(query);

                // bind parameter
                ps.setString(1, code);
                ps.setInt(2, offset);
                rs = ps.executeQuery();

                if (rs.next()) {
                    Inventory inventory = new Inventory();

                    inventory.setInventoryID(rs.getString("Inventory_ID"));
                    inventory.setReadyQty(rs.getInt("Ready_Qty"));

                    leftDemandQty -= inventory.getReadyQty();

                    inventories.add(inventory);
                }

                offset++;
            }

            //return object
            return inventories;
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

    public static Integer getInventoryReadyQtyByProdID(String code) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        Integer readyStock = 0;
        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT "
                    + "    Product_ID, SUM(Ready_Qty) AS TTL_READY_QTY"
                    + "FROM "
                    + "    Inventory "
                    + "WHERE "
                    + "    Product_ID = ? "
                    + "GROUP BY "
                    + "    Product_ID";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, code);
            rs = ps.executeQuery();

            if (rs.next()) {
                readyStock = rs.getInt("TTL_READY_QTY");
            }

            //return object
            return readyStock;
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

    public static Integer getOriginalReservedQtyByProdID(String code) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        Integer reservedStock = 0;
        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT "
                    + "    Prod_ID, SUM(Qty) AS TTL_ORI_RESERVED_QTY"
                    + "FROM "
                    + "    Item INNER JOIN "
                    + "    Inventory "
                    + "    ON Item.Inventory_ID = Inventory.Inventory_ID "
                    + "WHERE "
                    + "    Prod_ID = ? "
                    + "GROUP BY     "
                    + "    Prod_ID";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, code);
            rs = ps.executeQuery();

            if (rs.next()) {
                reservedStock = rs.getInt("TTL_ORI_RESERVED_QTY");
            }

            //return object
            return reservedStock;
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

    public static boolean freeUpReservedQtyByInventoryID(Inventory inventory, int qty) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE "
                    + "    Inventory "
                    + "SET "
                    + "    Reserved_Qty = Reserved_Qty - ?, "
                    + "    Ready_Qty = Ready_Qty + ? "
                    + "WHERE    "
                    + "    Inventory_ID = ? ";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setInt(1, qty);
            ps.setString(2, inventory.getInventoryID());

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

}
