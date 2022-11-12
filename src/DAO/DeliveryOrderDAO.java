/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.DeliveryOrder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class DeliveryOrderDAO {

    public static String getLatestCode() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        String latestCode = null;

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_DeliveryOrder_LatestID";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            if (rs.next()) {
                latestCode = rs.getString("DO_ID");
            }

            return latestCode;

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

    public static DeliveryOrder getDeliveryOrderByCode(String code) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        DeliveryOrder deliveryOrder = new DeliveryOrder();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_Retrieve_All_DeliveryOrder WHERE DO_DO_ID = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            if (rs.next()) {
                deliveryOrder.setCode(rs.getString("DO_DO_ID"));

                deliveryOrder.setSo(SalesOrderDAO.getSalesOrderByID(rs.getString("DO_Reference")));

                deliveryOrder.setDeliverFr(PlaceDAO.getPlaceByID(rs.getString("DO_Company_Address_ID")));
                deliveryOrder.setDeliveryDate(rs.getDate("DO_Delivery_Date"));
                deliveryOrder.setIssuedBy(StaffDAO.getStaffByID(rs.getString("DO_Issued_By")));
                deliveryOrder.setReleasedAVerifiedBy(StaffDAO.getStaffByID(rs.getString("DO_Released_And_Verified_By")));
                deliveryOrder.setDeliveryBy(StaffDAO.getStaffByID(rs.getString("DO_Delivery_By")));
                deliveryOrder.setItemReceivedBy(CollectAddressDAO.getCollectAddressByID(rs.getString("DO_Item_Received_By")));
                deliveryOrder.setStatus(rs.getString("DO_Status"));
                deliveryOrder.setCreatedDate(rs.getTimestamp("DO_Created_Date"));
                deliveryOrder.setActualCreatedDateTime(rs.getTimestamp("DO_Actual_Created_Date"));
                deliveryOrder.setSignedDocPic(rs.getString("DO_Signed_Doc_Pic"));
                deliveryOrder.setModifiedDateTime(rs.getTimestamp("DO_Modified_Date_Time"));
                return deliveryOrder;
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

    public static String saveNewDeliveryOrder(DeliveryOrder deliveryOrder) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "INSERT INTO DeliveryOrder ("
                    + ", "
                    + ") "
                    + "VALUES (?,)";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.execute();
            return deliveryOrder.getCode();
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
