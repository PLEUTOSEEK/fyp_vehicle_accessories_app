/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.ShipmentTerm;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ShipmentTermDAO {

    static ShipmentTerm getShipmentTermByID(String ID) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        ShipmentTerm shipmentTerm = new ShipmentTerm();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT [Shipment_Term_ID] "
                    + "      ,[Shipment_Term_Name] "
                    + "      ,[Description] "
                    + "      ,[Modified_Date_Time] "
                    + "      ,[Created_Date] "
                    + "  FROM [dbo].[ShipmentTerm] "
                    + "  WHERE [Shipment_Term_ID] = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, ID);
            rs = ps.executeQuery();

            if (rs.next()) {
                shipmentTerm.setShipmentTermID(rs.getString("Shipment_Term_ID"));
                shipmentTerm.setShipmentTermName(rs.getString("Shipment_Term_Name"));
                shipmentTerm.setDescription(rs.getString("Description"));
                shipmentTerm.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));
                shipmentTerm.setCreatedDate(rs.getTimestamp("Created_Date"));

                return shipmentTerm;
            } else {
                return null;
            }

            //return object
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

    public static List<ShipmentTerm> getAllShipmentTerms() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        List<ShipmentTerm> shipmentTerms = new ArrayList<>();
        ShipmentTerm shipmentTerm = new ShipmentTerm();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT [Shipment_Term_ID] "
                    + "      ,[Shipment_Term_Name] "
                    + "      ,[Description] "
                    + "      ,[Modified_Date_Time] "
                    + "      ,[Created_Date] "
                    + "  FROM [ShipmentTerm]";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            while (rs.next()) {
                shipmentTerm = new ShipmentTerm();

                shipmentTerm.setShipmentTermID(rs.getString("Shipment_Term_ID"));
                shipmentTerm.setShipmentTermName(rs.getString("Shipment_Term_Name"));
                shipmentTerm.setDescription(rs.getString("Description"));
                shipmentTerm.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));
                shipmentTerm.setCreatedDate(rs.getTimestamp("Created_Date"));

                shipmentTerms.add(shipmentTerm);
            }

            //return object
            return shipmentTerms;
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
