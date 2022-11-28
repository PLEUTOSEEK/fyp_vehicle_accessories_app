/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ProductDAO {

    public static Product getProductByID(String prodID) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        Product prod = new Product();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM Product WHERE Prod_ID = ? ";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, prodID);
            rs = ps.executeQuery();

            if (rs.next()) {
                prod.setProdID(rs.getString("Prod_ID"));
                prod.setProdName(rs.getString("Prod_Name"));
                prod.setPartNo(rs.getString("Part_No"));
                prod.setUom(rs.getString("UOM"));
                prod.setDescription(rs.getString("Description"));
                prod.setColor(rs.getString("Color"));
                prod.setSellPrice(rs.getBigDecimal("Sell_Price"));
                prod.setMSRP(rs.getBigDecimal("MSRP"));
                prod.setMaxLvl(rs.getInt("Maximum_Level"));
                prod.setAvgLvl(rs.getInt("Average_Level"));
                prod.setMinLvl(rs.getInt("Minimum_Level"));
                prod.setReorderLvl(rs.getInt("Reorder_Level"));
                prod.setCreatedDate(rs.getTimestamp("Created_Date"));
                prod.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));
            }

            //return object
            return prod;
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

    public static List<Product> getAllProducts() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        Product prod = new Product();
        List<Product> products = new ArrayList<>();
        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM Product";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            while (rs.next()) {
                prod = new Product();

                prod.setProdID(rs.getString("Prod_ID"));
                prod.setProdName(rs.getString("Prod_Name"));
                prod.setPartNo(rs.getString("Part_No"));
                prod.setDescription(rs.getString("Description"));
                prod.setColor(rs.getString("Color"));
                prod.setSellPrice(rs.getBigDecimal("Sell_Price"));
                prod.setMSRP(rs.getBigDecimal("MSRP"));
                prod.setMaxLvl(rs.getInt("Maximum_Level"));
                prod.setAvgLvl(rs.getInt("Average_Level"));
                prod.setMinLvl(rs.getInt("Minimum_Level"));
                prod.setReorderLvl(rs.getInt("Reorder_Level"));
                prod.setCreatedDate(rs.getTimestamp("Created_Date"));
                prod.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));

                products.add(prod);
            }

            //return object
            return products;
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
