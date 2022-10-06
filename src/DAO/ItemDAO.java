/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

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
public class ItemDAO {

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

}
