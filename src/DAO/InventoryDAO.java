/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Inventory;
import Entity.Item;
import Entity.PackingSlip;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
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
                    + "    Product_ID, SUM(Ready_Qty) AS TTL_READY_QTY "
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
                //return object
                return reservedStock;
            } else {
                return null;
            }

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

    public static boolean postGoodsIssues(PackingSlip packingSlip) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        try {
            conn = SQLDatabaseConnection.openConn();

            query
                    = "UPDATE "
                    + "	INVT "
                    + "SET "
                    + "	INVT.Reserved_Qty = INVT.Reserved_Qty - PS_TABLE.Ori_Qty, "
                    + "	INVT.Total_Qty = INVT.Total_Qty - PS_TABLE.Ori_Qty "
                    + "FROM  "
                    + "	Inventory AS INVT "
                    + "	INNER JOIN  "
                    + "	( "
                    + "	SELECT "
                    + "		Item.Ori_Qty, "
                    + "		Item.Inventory_ID "
                    + "	FROM "
                    + "		PackingSlip "
                    + "		INNER JOIN Item "
                    + "		ON PackingSlip.PS_ID = Item.Ref_Doc_ID "
                    + "	WHERE "
                    + "		PackingSlip.PS_ID = ? "
                    + "	) AS PS_TABLE "
                    + "	ON  INVT.Inventory_ID = PS_TABLE.Inventory_ID";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, packingSlip.getCode());
            ps.execute();
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
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
            ps.setInt(2, qty);
            ps.setString(3, inventory.getInventoryID());

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

    public static List<Inventory> getInventoryByProdID(String prodID) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        Inventory inventory = new Inventory();
        List<Inventory> inventories = new ArrayList<>();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT [Inventory_ID] "
                    + "      ,[Place_ID] "
                    + "      ,[Product_ID] "
                    + "      ,[Reserved_Qty] "
                    + "      ,[Ready_Qty] "
                    + "      ,[Modified_Date_Time] "
                    + "  FROM [dbo].[Inventory]"
                    + "  WHERE [Product_ID] = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, prodID);
            rs = ps.executeQuery();

            while (rs.next()) {
                inventory = new Inventory();
                inventory.setInventoryID(rs.getString("Inventory_ID"));
                inventory.setStorePlace(PlaceDAO.getPlaceByID(rs.getString("Place_ID")));
                inventory.setProduct(ProductDAO.getProductByID(rs.getString("Product_ID")));
                inventory.setReservedQty(rs.getInt("Reserved_Qty"));
                inventory.setReadyQty(rs.getInt("Ready_Qty"));
                inventories.add(inventory);
            }

            //return object
            return inventories;
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    public static void putBackInventory(Item item, String destination, String newInventoryID) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        try {
            conn = SQLDatabaseConnection.openConn();

            if (getInventoryByPlaceProd(destination, item.getProduct().getProdID()) == true) {
                query = "UPDATE "
                        + "    Inventory "
                        + "SET "
                        + "    Ready_Qty = Ready_Qty + ?, "
                        + "    Total_Qty = Total_Qty  + ? "
                        + "WHERE  "
                        + "    Place_ID = ? AND "
                        + "    Product_ID = ?";
                ps = conn.prepareStatement(query);
                // bind parameter
                ps.setInt(1, item.getQty());
                ps.setInt(2, item.getQty());
                ps.setString(3, destination);
                ps.setString(4, item.getProduct().getProdID());
            } else {
                query = "INSERT INTO [dbo].[Inventory] "
                        + "           ([Inventory_ID] "
                        + "           ,[Place_ID] "
                        + "           ,[Product_ID] "
                        + "           ,[Reserved_Qty] "
                        + "           ,[Ready_Qty] "
                        + "           ,[Total_Qty] "
                        + "           ,[Modified_Date_Time]) "
                        + "     VALUES "
                        + "           (? "
                        + "           ,? "
                        + "           ,? "
                        + "           ,0 "
                        + "           ,? "
                        + "           ,? "
                        + "           ,?)";
                ps = conn.prepareStatement(query);
                // bind parameter
                ps.setString(1, newInventoryID);
                ps.setString(2, destination);
                ps.setString(3, item.getProduct().getProdID());
                ps.setInt(4, item.getQty());
                ps.setInt(5, item.getQty());
                ps.setTimestamp(6, timestamp);
            }
            ps.execute();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
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

    private static boolean getInventoryByPlaceProd(String placeCode, String prodCode) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT "
                    + "    Inventory_ID "
                    + "FROM "
                    + "    Inventory "
                    + "WHERE "
                    + "    Place_ID = ? AND "
                    + "    Product_ID = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, placeCode);
            ps.setString(2, prodCode);
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }

            //return object
        } catch (Exception e) {
            throw new Exception(e.getMessage());
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

    public static String getLatestCode() {
        //
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        String latestCode = null;

        try {
            conn = SQLDatabaseConnection.openConn();
            query = "SELECT * FROM View_INVT_LatestID";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                latestCode = rs.getString("Inventory_ID");
                return latestCode;
            } else {
                return "";
            }

        } catch (Exception e) {
            return "";
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
