/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.SalesOrder;
import Entity.TransferOrder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class TransferOrderDAO {

    public static boolean saveNewTransferOrder(TransferOrder transferOrder) {

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "INSERT INTO TransferOrder ("
                    + "TO_TO_ID, "
                    + "TO_Req_Type, "
                    + "TO_Req_Type_Ref, "
                    + "TO_Person_In_Charge, "
                    + "TO_Destination, "
                    + "TO_Issued_By, "
                    + "TO_Transfer_By, "
                    + "TO_Item_Received_By, "
                    + "TO_Status, "
                    + "TO_Created_Date, "
                    + "TO_Actual_Created_Date, "
                    + "TO_Signed_Doc_Pic, "
                    + "TO_Modified_Date_Time "
                    + ") "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, transferOrder.getCode());
            ps.setString(2, transferOrder.getReqType());
            ps.setString(3, ((SalesOrder) transferOrder.getReqTypeRef()).getCode());
            ps.setString(4, transferOrder.getPIC().getStaffID());
            ps.setString(5, transferOrder.getDestination().getPlaceID());
            ps.setString(6, transferOrder.getIssuedBy().getStaffID());
            ps.setString(7, transferOrder.getTransferBy().getStaffID());
            ps.setString(8, transferOrder.getItemReceivedBy().getCustID());
            ps.setString(9, transferOrder.getStatus());
            ps.setTimestamp(10, transferOrder.getCreatedDate());
            ps.setTimestamp(11, transferOrder.getActualCreatedDateTime());
            ps.setString(12, Base64.encodeBase64String(transferOrder.getSignedDocPic()));
            ps.setTimestamp(13, transferOrder.getModifiedDateTime());

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

    public static TransferOrder getTransferOrderByCode(String code) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        TransferOrder<SalesOrder> transferOrder = new TransferOrder<>();

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
                transferOrder.setReqTypeRef(SalesOrderDAO.getSalesOrderByCode(rs.getString("TO_Req_Type_Ref")));
                transferOrder.setPIC(StaffDAO.getStaffByID(rs.getString("TO_Person_In_Charge")));
                transferOrder.setDestination(PlaceDAO.getPlaceByID(rs.getString("TO_Destination")));
                transferOrder.setIssuedBy(StaffDAO.getStaffByID(rs.getString("TO_Issued_By")));
                transferOrder.setTransferBy(StaffDAO.getStaffByID(rs.getString("TO_Transfer_By")));
                transferOrder.setItemReceivedBy(CustomerDAO.getCustomerByID(rs.getString("TO_Item_Received_By")));
                transferOrder.setStatus(rs.getString("TO_Status"));
                transferOrder.setCreatedDate(rs.getTimestamp("TO_Created_Date"));
                transferOrder.setActualCreatedDateTime(rs.getTimestamp(rs.getString("TO_Actual_Created_Date")));
                transferOrder.setSignedDocPic(Base64.decodeBase64(rs.getString("TO_Signed_Doc_Pic")));
                transferOrder.setModifiedDateTime(rs.getTimestamp("TO_Modified_Date_Time"));
                return transferOrder;
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

    public static String getLatestCode() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        String latestCode = null;

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
