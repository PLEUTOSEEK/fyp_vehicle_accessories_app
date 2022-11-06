/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Inventory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

}
