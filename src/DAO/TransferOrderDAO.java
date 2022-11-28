/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.ReturnDeliveryNote;
import Entity.SalesOrder;
import Entity.TransferOrder;
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
public class TransferOrderDAO {

    public static String saveNewTransferOrder(TransferOrder transferOrder) {

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        transferOrder.setActualCreatedDateTime(timestamp);
        transferOrder.setModifiedDateTime(timestamp);
        try {
            conn = SQLDatabaseConnection.openConn();

            query = "INSERT INTO TransferOrder ("
                    + "TO_ID, "
                    + "Req_Type, "
                    + "Req_Type_Ref, "
                    + "SO_ID, "
                    + "RDN_ID, "
                    + "Person_In_Charge, "
                    + "Destination, "
                    + "Issued_By, "
                    + "Transfer_By, "
                    + "Item_Received_By, "
                    + "Status, "
                    + "Created_Date, "
                    + "Actual_Created_Date, "
                    + "Signed_Doc_Pic, "
                    + "Modified_Date_Time "
                    + ") "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, transferOrder.getCode());
            ps.setString(2, transferOrder.getReqType());
            if (transferOrder.getReqType().equals("Sales Order (SO)")) {
                ps.setString(3, ((SalesOrder) transferOrder.getReqTypeRef()).getCode());
                ps.setString(4, ((SalesOrder) transferOrder.getReqTypeRef()).getCode());
                ps.setString(5, null);

            } else if (transferOrder.getReqType().equals("Return Delivery Note (RDN)")) {
                ps.setString(3, ((ReturnDeliveryNote) transferOrder.getReqTypeRef()).getCode());
                ps.setString(4, (ReturnDeliveryNoteDAO.getReturnDeliveryNoteByCode(((ReturnDeliveryNote) transferOrder.getReqTypeRef()).getCode())).getSO().getCode());
                ps.setString(5, ((ReturnDeliveryNote) transferOrder.getReqTypeRef()).getCode());
            }
            ps.setString(6, transferOrder.getPIC().getStaffID());
            ps.setString(7, transferOrder.getDestination().getPlaceID());
            ps.setString(8, transferOrder.getIssuedBy().getStaffID());
            ps.setString(9, transferOrder.getTransferBy().getStaffID());
            ps.setString(10, transferOrder.getItemReceivedBy().getStaffID());
            ps.setString(11, transferOrder.getStatus());
            ps.setTimestamp(12, transferOrder.getCreatedDate());
            ps.setTimestamp(13, transferOrder.getActualCreatedDateTime());
            ps.setString(14, transferOrder.getSignedDocPic());
            ps.setTimestamp(15, transferOrder.getModifiedDateTime());

            ps.execute();

            return transferOrder.getCode();
        } catch (Exception e) {
            System.out.println("TO INSERT " + e.getMessage());

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

    public static TransferOrder getTransferOrderByCode(String code) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        TransferOrder transferOrder = new TransferOrder<>();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_Retrieve_All_TransferOrder WHERE TO_TO_ID = ?";

            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, code);

            rs = ps.executeQuery();

            if (rs.next()) {
                transferOrder.setCode(rs.getString("TO_TO_ID"));
                transferOrder.setReqType(rs.getString("TO_Req_Type"));

                if (rs.getString("TO_Req_Type").equals("SO")) {
                    transferOrder.setReqTypeRef(SalesOrderDAO.getSalesOrderByID(rs.getString("TO_Req_Type_Ref")));
                } else if (rs.getString("TO_Req_Type").equals("RDN")) {
                    transferOrder.setReqTypeRef(ReturnDeliveryNoteDAO.getReturnDeliveryNoteByCode(rs.getString("TO_Req_Type_Ref")));
                }

                transferOrder.setPIC(StaffDAO.getStaffByID(rs.getString("TO_Person_In_Charge")));
                transferOrder.setDestination(PlaceDAO.getPlaceByID(rs.getString("TO_Destination")));
                transferOrder.setIssuedBy(StaffDAO.getStaffByID(rs.getString("TO_Issued_By")));
                transferOrder.setTransferBy(StaffDAO.getStaffByID(rs.getString("TO_Transfer_By")));
                transferOrder.setItemReceivedBy(StaffDAO.getStaffByID(rs.getString("TO_Item_Received_By")));
                transferOrder.setStatus(rs.getString("TO_Status"));
                transferOrder.setCreatedDate(rs.getTimestamp("TO_Created_Date"));
                transferOrder.setActualCreatedDateTime(rs.getTimestamp("TO_Actual_Created_Date"));
                transferOrder.setSignedDocPic(rs.getString("TO_Signed_Doc_Pic"));
                transferOrder.setModifiedDateTime(rs.getTimestamp("TO_Modified_Date_Time"));
                return transferOrder;
            } else {
                return null;
            }

            //return object
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

    public static List<TransferOrder> getAllTO() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        TransferOrder transferOrder = new TransferOrder<>();
        List<TransferOrder> transferOrders = new ArrayList<>();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_Retrieve_All_TransferOrder ";

            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            while (rs.next()) {

                transferOrder.setCode(rs.getString("TO_TO_ID"));
                transferOrder.setReqType(rs.getString("TO_Req_Type"));

                if (transferOrder.getReqType().equals("Sales Order (SO)")) {
                    transferOrder.setReqTypeRef(new SalesOrder());
                    ((SalesOrder) transferOrder.getReqTypeRef()).setCode(rs.getString("TO_Req_Type_Ref"));
                } else if (transferOrder.getReqType().equals("Return Delivery Note (RDN)")) {
                    transferOrder.setReqTypeRef(new ReturnDeliveryNote());
                    ((ReturnDeliveryNote) transferOrder.getReqTypeRef()).setCode(rs.getString("TO_Req_Type_Ref"));
                }

                transferOrder.setPIC(StaffDAO.getStaffByID(rs.getString("TO_Person_In_Charge")));
                transferOrder.setDestination(PlaceDAO.getPlaceByID(rs.getString("TO_Destination")));
                transferOrder.setIssuedBy(StaffDAO.getStaffByID(rs.getString("TO_Issued_By")));
                transferOrder.setTransferBy(StaffDAO.getStaffByID(rs.getString("TO_Transfer_By")));
                transferOrder.setItemReceivedBy(StaffDAO.getStaffByID(rs.getString("TO_Item_Received_By")));
                transferOrder.setStatus(rs.getString("TO_Status"));
                transferOrder.setCreatedDate(rs.getTimestamp("TO_Created_Date"));
                transferOrder.setActualCreatedDateTime(rs.getTimestamp("TO_Actual_Created_Date"));
                transferOrder.setSignedDocPic(rs.getString("TO_Signed_Doc_Pic"));
                transferOrder.setModifiedDateTime(rs.getTimestamp("TO_Modified_Date_Time"));
                transferOrders.add(transferOrder);

            }

            //return object
            return transferOrders;
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
        String latestCode = "";

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_TransferOrder_LatestID";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            if (rs.next()) {
                latestCode = rs.getString("TO_ID");
            }

            return latestCode;

            //return object
        } catch (Exception e) {
            System.out.println("TO Code generator " + e.getMessage());
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

    public static String updateTO(TransferOrder transferOrder) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        transferOrder.setModifiedDateTime(timestamp);
        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE TransferOrder SET "
                    + "Req_Type = ?, "
                    + "Req_Type_Ref = ?, "
                    + "Person_In_Charge = ?, "
                    + "Destination = ?, "
                    + "Issued_By = ?, "
                    + "Transfer_By = ?, "
                    + "Item_Received_By = ?, "
                    + "Status = ?, "
                    + "Created_Date = ?, "
                    + "Signed_Doc_Pic = ?, "
                    + "Modified_Date_Time = ? "
                    + "WHERE "
                    + "TO_ID = ? ; ";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, transferOrder.getReqType());
            if (transferOrder.getReqType().equals("Sales Order (SO)")) {
                ps.setString(2, ((SalesOrder) transferOrder.getReqTypeRef()).getCode());
            } else if (transferOrder.getReqType().equals("Return Delivery Note (RDN)")) {
                ps.setString(2, ((ReturnDeliveryNote) transferOrder.getReqTypeRef()).getCode());
            }
            ps.setString(3, transferOrder.getPIC().getStaffID());
            ps.setString(4, transferOrder.getDestination().getPlaceID());
            ps.setString(5, transferOrder.getIssuedBy().getStaffID());
            ps.setString(6, transferOrder.getTransferBy().getStaffID());
            ps.setString(7, transferOrder.getItemReceivedBy().getStaffID());
            ps.setString(8, transferOrder.getStatus());
            ps.setTimestamp(9, transferOrder.getCreatedDate());
            ps.setString(10, transferOrder.getSignedDocPic());
            ps.setTimestamp(11, transferOrder.getModifiedDateTime());
            ps.setString(12, transferOrder.getCode());
            ps.execute();

            return transferOrder.getCode();
        } catch (Exception e) {
            System.out.println("TO updatre " + e.getMessage());

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

    public static String updateTOStatus(TransferOrder transferOrder) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE TransferOrder SET "
                    + "TO_Status = ? "
                    + "WHERE "
                    + "TO_TO_ID = ? ; ";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, transferOrder.getStatus());
            ps.setString(2, transferOrder.getCode());
            ps.execute();

            return transferOrder.getCode();
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
