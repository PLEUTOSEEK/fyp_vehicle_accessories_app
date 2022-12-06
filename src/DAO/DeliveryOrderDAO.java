/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.DeliveryOrder;
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
public class DeliveryOrderDAO {

    public static DeliveryOrder getDeliveryOrderByCode(String code) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        DeliveryOrder deliveryOrder = new DeliveryOrder();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT [DO_ID] "
                    + "      ,[SO_ID] "
                    + "      ,[Reference_Type] "
                    + "      ,[Reference] "
                    + "      ,[Deliver_From] "
                    + "      ,[Delivery_Date] "
                    + "      ,[Issued_By] "
                    + "      ,[Released_And_Verified_By] "
                    + "      ,[Delivery_By] "
                    + "      ,[Item_Received_By] "
                    + "      ,[Status] "
                    + "      ,[Created_Date] "
                    + "      ,[Actual_Created_Date] "
                    + "      ,[Signed_Doc_Pic] "
                    + "      ,[Modified_Date_Time] "
                    + "  FROM [dbo].[DeliveryOrder] "
                    + "  WHERE [DO_ID] = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, code);
            rs = ps.executeQuery();

            if (rs.next()) {
                deliveryOrder.setCode(rs.getString("DO_ID"));

                deliveryOrder.setSo(SalesOrderDAO.getSalesOrderByID(rs.getString("SO_ID")));
                deliveryOrder.setReferenceType(rs.getString("Reference_Type"));
                deliveryOrder.setReference(rs.getString("Reference"));
                deliveryOrder.setDeliverFr(PlaceDAO.getPlaceByID(rs.getString("Deliver_From")));
                deliveryOrder.setDeliveryDate(rs.getDate("Delivery_Date"));
                deliveryOrder.setIssuedBy(StaffDAO.getStaffByID(rs.getString("Issued_By")));
                deliveryOrder.setReleasedAVerifiedBy(StaffDAO.getStaffByID(rs.getString("Released_And_Verified_By")));
                deliveryOrder.setDeliveryBy(StaffDAO.getStaffByID(rs.getString("Delivery_By")));
                deliveryOrder.setItemReceivedBy(CollectAddressDAO.getCollectAddressByID(rs.getString("Item_Received_By")));
                deliveryOrder.setStatus(rs.getString("Status"));
                deliveryOrder.setCreatedDate(rs.getTimestamp("Created_Date"));
                deliveryOrder.setActualCreatedDateTime(rs.getTimestamp("Actual_Created_Date"));
                deliveryOrder.setSignedDocPic(rs.getString("Signed_Doc_Pic"));
                deliveryOrder.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));

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

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        deliveryOrder.setActualCreatedDateTime(timestamp);
        deliveryOrder.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "INSERT INTO [dbo].[DeliveryOrder] "
                    + "           ([DO_ID] "
                    + "           ,[SO_ID] "
                    + "           ,[Reference_Type] "
                    + "           ,[Reference] "
                    + "           ,[Deliver_From] "
                    + "           ,[Delivery_Date] "
                    + "           ,[Issued_By] "
                    + "           ,[Released_And_Verified_By] "
                    + "           ,[Delivery_By] "
                    + "           ,[Item_Received_By] "
                    + "           ,[Status] "
                    + "           ,[Created_Date] "
                    + "           ,[Actual_Created_Date] "
                    + "           ,[Signed_Doc_Pic] "
                    + "           ,[Modified_Date_Time]) "
                    + "     VALUES "
                    + "           (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, deliveryOrder.getCode());
            ps.setString(2, deliveryOrder.getSo().getCode());
            ps.setString(3, deliveryOrder.getReferenceType());
            ps.setString(4, deliveryOrder.getReference());
            ps.setString(5, deliveryOrder.getDeliverFr().getPlaceID());
            ps.setDate(6, deliveryOrder.getDeliveryDate());
            ps.setString(7, deliveryOrder.getIssuedBy().getStaffID());
            ps.setString(8, deliveryOrder.getReleasedAVerifiedBy().getStaffID());
            ps.setString(9, deliveryOrder.getDeliveryBy().getStaffID());
            ps.setString(10, deliveryOrder.getItemReceivedBy().getCollectAddrID());
            ps.setString(11, deliveryOrder.getStatus());
            ps.setTimestamp(12, deliveryOrder.getCreatedDate());
            ps.setTimestamp(13, deliveryOrder.getActualCreatedDateTime());
            ps.setString(14, deliveryOrder.getSignedDocPic());
            ps.setTimestamp(15, deliveryOrder.getModifiedDateTime());

            ps.execute();
            return deliveryOrder.getCode();
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

    public static String updateDeliveryOrder(DeliveryOrder deliveryOrder) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        deliveryOrder.setActualCreatedDateTime(timestamp);
        deliveryOrder.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE [dbo].[DeliveryOrder] "
                    + "   SET [SO_ID] = ? "
                    + "      ,[Reference_Type] = ? "
                    + "      ,[Reference] = ? "
                    + "      ,[Deliver_From] = ? "
                    + "      ,[Delivery_Date] = ? "
                    + "      ,[Issued_By] = ? "
                    + "      ,[Released_And_Verified_By] = ? "
                    + "      ,[Delivery_By] = ? "
                    + "      ,[Item_Received_By] = ? "
                    + "      ,[Status] = ? "
                    + "      ,[Created_Date] = ? "
                    + "      ,[Signed_Doc_Pic] = ? "
                    + "      ,[Modified_Date_Time] = ? "
                    + " WHERE [DO_ID] = ? ";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, deliveryOrder.getSo().getCode());
            ps.setString(2, deliveryOrder.getReferenceType());
            ps.setString(3, deliveryOrder.getReference());
            ps.setString(4, deliveryOrder.getDeliverFr().getPlaceID());
            ps.setDate(5, deliveryOrder.getDeliveryDate());
            ps.setString(6, deliveryOrder.getIssuedBy().getStaffID());
            ps.setString(7, deliveryOrder.getReleasedAVerifiedBy().getStaffID());
            ps.setString(8, deliveryOrder.getDeliveryBy().getStaffID());
            ps.setString(9, deliveryOrder.getItemReceivedBy().getCollectAddrID());
            ps.setString(10, deliveryOrder.getStatus());
            ps.setTimestamp(11, deliveryOrder.getCreatedDate());
            ps.setString(12, deliveryOrder.getSignedDocPic());
            ps.setTimestamp(13, deliveryOrder.getModifiedDateTime());
            ps.setString(14, deliveryOrder.getCode());

            ps.execute();
            return deliveryOrder.getCode();
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

    public static String getLatestCode() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        String latestID = "";

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_DeliveryOrder_LatestID";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            if (rs.next()) {
                latestID = rs.getString("DO_ID");
            }

            return latestID;

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

    public static List<DeliveryOrder> getAllDOs() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        DeliveryOrder deliveryOrder = new DeliveryOrder();
        List<DeliveryOrder> deliveryOrders = new ArrayList<>();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT [DO_ID] "
                    + "      ,[SO_ID] "
                    + "      ,[Reference_Type] "
                    + "      ,[Reference] "
                    + "      ,[Deliver_From] "
                    + "      ,[Delivery_Date] "
                    + "      ,[Issued_By] "
                    + "      ,[Released_And_Verified_By] "
                    + "      ,[Delivery_By] "
                    + "      ,[Item_Received_By] "
                    + "      ,[Status] "
                    + "      ,[Created_Date] "
                    + "      ,[Actual_Created_Date] "
                    + "      ,[Signed_Doc_Pic] "
                    + "      ,[Modified_Date_Time] "
                    + "  FROM [dbo].[DeliveryOrder] ";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            while (rs.next()) {
                deliveryOrder = new DeliveryOrder();
                deliveryOrder.setCode(rs.getString("DO_ID"));

                deliveryOrder.setSo(SalesOrderDAO.getSalesOrderByID(rs.getString("SO_ID")));
                deliveryOrder.setReferenceType(rs.getString("Reference_Type"));
                deliveryOrder.setReference(rs.getString("Reference"));
                deliveryOrder.setDeliverFr(PlaceDAO.getPlaceByID(rs.getString("Deliver_From")));
                deliveryOrder.setDeliveryDate(rs.getDate("Delivery_Date"));
                deliveryOrder.setIssuedBy(StaffDAO.getStaffByID(rs.getString("Issued_By")));
                deliveryOrder.setReleasedAVerifiedBy(StaffDAO.getStaffByID(rs.getString("Released_And_Verified_By")));
                deliveryOrder.setDeliveryBy(StaffDAO.getStaffByID(rs.getString("Delivery_By")));
                deliveryOrder.setItemReceivedBy(CollectAddressDAO.getCollectAddressByID(rs.getString("Item_Received_By")));
                deliveryOrder.setStatus(rs.getString("Status"));
                deliveryOrder.setCreatedDate(rs.getTimestamp("Created_Date"));
                deliveryOrder.setActualCreatedDateTime(rs.getTimestamp("Actual_Created_Date"));
                deliveryOrder.setSignedDocPic(rs.getString("Signed_Doc_Pic"));
                deliveryOrder.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));

                deliveryOrders.add(deliveryOrder);
            }

            //return object
            return deliveryOrders;
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
}
