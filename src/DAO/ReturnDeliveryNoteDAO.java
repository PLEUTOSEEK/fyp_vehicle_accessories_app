/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.CollectAddress;
import Entity.Place;
import Entity.ReturnDeliveryNote;
import Entity.SalesOrder;
import Entity.Staff;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ReturnDeliveryNoteDAO {

    public static ReturnDeliveryNote getReturnDeliveryNoteByCode(String code) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        ReturnDeliveryNote returnDeliveryNote = new ReturnDeliveryNote();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM ReturnDeliveryNote WHERE RDN_ID = ?; ";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, code);
            rs = ps.executeQuery();

            if (rs.next()) {
                returnDeliveryNote.setCode(rs.getString("RDN_ID"));
                returnDeliveryNote.setSO(SalesOrderDAO.getSalesOrderByID(rs.getString("SO_ID")));
                returnDeliveryNote.setCollBackTo(PlaceDAO.getPlaceByID(rs.getString("Collect_Back_To")));
                returnDeliveryNote.setCollBckFr(CollectAddressDAO.getCollectAddressByID(rs.getString("Collect_Back_From")));
                returnDeliveryNote.setCollectDate(rs.getDate("Collect_Date"));
                returnDeliveryNote.setInspectorMsg(rs.getString("Inspector_Msg"));
                returnDeliveryNote.setIssuedBy(StaffDAO.getStaffByID(rs.getString("Issued_By")));
                returnDeliveryNote.setInspectedBy(StaffDAO.getStaffByID(rs.getString("Inspected_By")));
                returnDeliveryNote.setCollectBackBy(StaffDAO.getStaffByID(rs.getString("Collect_Back_By")));
                returnDeliveryNote.setItemPassedBackBy(CollectAddressDAO.getCollectAddressByID(rs.getString("Item_Passed_Back_by")));
                returnDeliveryNote.setItemReceivedBy(StaffDAO.getStaffByID(rs.getString("Item_Received_By")));
                returnDeliveryNote.setStatus(rs.getString("Status"));
                returnDeliveryNote.setCreatedDate(rs.getTimestamp("Created_Date"));
                returnDeliveryNote.setActualCreatedDateTime(rs.getTimestamp("Actual_Created_Date"));
                returnDeliveryNote.setSignedDocPic(rs.getString("Signed_Doc_Pic"));
                returnDeliveryNote.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));
            }

            //return object
            return returnDeliveryNote;
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

    public static String updateRDNStatus(ReturnDeliveryNote returnDeliveryNote) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        returnDeliveryNote.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE ReturnDeliveryNote SET "
                    + "Status = ? "
                    + "WHERE "
                    + "RDN_ID = ? ";

            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, returnDeliveryNote.getStatus());
            ps.setString(2, returnDeliveryNote.getCode());

            ps.execute();
            return returnDeliveryNote.getCode();
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

    public static String saveNewRDN(ReturnDeliveryNote returnDeliveryNote) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        returnDeliveryNote.setActualCreatedDateTime(timestamp);
        returnDeliveryNote.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "INSERT INTO ReturnDeliveryNote( "
                    + "RDN_ID, "
                    + "SO_ID, "
                    + "Collect_Back_To, "
                    + "Collect_Back_From, "
                    + "Collect_Date, "
                    + "Inspector_Msg, "
                    + "Issued_By, "
                    + "Inspected_By, "
                    + "Collect_Back_By, "
                    + "Item_Passed_Back_by, "
                    + "Item_Received_By, "
                    + "Status, "
                    + "Created_Date, "
                    + "Actual_Created_Date, "
                    + "Signed_Doc_Pic, "
                    + "Modified_Date_Time "
                    + ") "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, returnDeliveryNote.getCode());
            ps.setString(2, returnDeliveryNote.getSO().getCode());
            ps.setString(3, returnDeliveryNote.getCollBackTo().getPlaceID());
            ps.setString(4, returnDeliveryNote.getCollBckFr().getCollectAddrID());
            ps.setDate(5, returnDeliveryNote.getCollectDate());
            ps.setString(6, returnDeliveryNote.getInspectorMsg());
            ps.setString(7, returnDeliveryNote.getIssuedBy().getStaffID());
            ps.setString(8, returnDeliveryNote.getInspectedBy().getStaffID());
            ps.setString(9, returnDeliveryNote.getCollectBackBy().getStaffID());
            ps.setString(10, returnDeliveryNote.getItemPassedBackBy().getCollectAddrID());
            ps.setString(11, returnDeliveryNote.getItemReceivedBy().getStaffID());
            ps.setString(12, returnDeliveryNote.getStatus());
            ps.setTimestamp(13, returnDeliveryNote.getCreatedDate());
            ps.setTimestamp(14, returnDeliveryNote.getActualCreatedDateTime());
            ps.setString(15, returnDeliveryNote.getSignedDocPic());
            ps.setTimestamp(16, returnDeliveryNote.getModifiedDateTime());

            ps.execute();
            return returnDeliveryNote.getCode();
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

    public static String updateRDN(ReturnDeliveryNote returnDeliveryNote) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        returnDeliveryNote.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE ReturnDeliveryNote SET "
                    + "SO_ID = ?, "
                    + "Collect_Back_To = ?, "
                    + "Collect_Back_From = ?, "
                    + "Collect_Date = ?, "
                    + "Inspector_Msg = ?, "
                    + "Issued_By = ?, "
                    + "Inspected_By = ?, "
                    + "Collect_Back_By = ?, "
                    + "Item_Passed_Back_by = ?, "
                    + "Item_Received_By = ?, "
                    + "Status = ?, "
                    + "Created_Date = ?, "
                    + "Signed_Doc_Pic = ?, "
                    + "Modified_Date_Time = ? "
                    + "WHERE "
                    + "RDN_ID = ? ";

            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, returnDeliveryNote.getSO().getCode());
            ps.setString(2, returnDeliveryNote.getCollBackTo().getPlaceID());
            ps.setString(3, returnDeliveryNote.getCollBckFr().getCollectAddrID());
            ps.setDate(4, returnDeliveryNote.getCollectDate());
            ps.setString(5, returnDeliveryNote.getInspectorMsg());
            ps.setString(6, returnDeliveryNote.getIssuedBy().getStaffID());
            ps.setString(7, returnDeliveryNote.getInspectedBy().getStaffID());
            ps.setString(8, returnDeliveryNote.getCollectBackBy().getStaffID());
            ps.setString(9, returnDeliveryNote.getItemPassedBackBy().getCollectAddrID());
            ps.setString(10, returnDeliveryNote.getItemReceivedBy().getStaffID());
            ps.setString(11, returnDeliveryNote.getStatus());
            ps.setTimestamp(12, returnDeliveryNote.getCreatedDate());
            ps.setString(13, returnDeliveryNote.getSignedDocPic());
            ps.setTimestamp(14, returnDeliveryNote.getModifiedDateTime());
            ps.setString(15, returnDeliveryNote.getCode());

            ps.execute();
            return returnDeliveryNote.getCode();
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

    public static String getLatestCode() throws SQLException, Exception {
        //View_RDN_LatestID
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        String latestCode = "";

        try {
            conn = SQLDatabaseConnection.openConn();
            query = "SELECT * FROM View_RDN_LatestID";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                latestCode = rs.getString("RDN_ID");
            }

            return latestCode;

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

    public static List<ReturnDeliveryNote> getAllRDN() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        List<ReturnDeliveryNote> returnDeliveryNotes = new ArrayList<>();
        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT [RDN_ID] "
                    + "      ,[SO_ID] "
                    + "      ,[Collect_Back_To] "
                    + "      ,[Collect_Back_From] "
                    + "      ,[Collect_Date] "
                    + "      ,[Inspector_Msg] "
                    + "      ,[Issued_By] "
                    + "      ,[Inspected_By] "
                    + "      ,[Collect_Back_By] "
                    + "      ,[Item_Passed_Back_by] "
                    + "      ,[Item_Received_By] "
                    + "      ,[Status] "
                    + "      ,[Created_Date] "
                    + "      ,[Actual_Created_Date] "
                    + "      ,[Signed_Doc_Pic] "
                    + "      ,[Modified_Date_Time] "
                    + "  FROM ReturnDeliveryNote";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            while (rs.next()) {
                ReturnDeliveryNote r = new ReturnDeliveryNote();
                // RDN ID
                r.setCode(rs.getString("RDN_ID"));
                // So Ref.
                r.setSO(new SalesOrder());
                r.getSO().setCode(rs.getString("SO_ID"));
                r.setCollBackTo(new Place());
                r.getCollBackTo().setPlaceID(rs.getString("Collect_Back_To"));
                // Collect Back From Location Name
                r.setCollBckFr(CollectAddressDAO.getCollectAddressByID(rs.getString("Collect_Back_From")));
                r.setCollectDate(rs.getDate("Collect_Date"));
                r.setInspectorMsg(rs.getString("Inspector_Msg"));
                r.setIssuedBy(new Staff());
                r.getIssuedBy().setStaffID(rs.getString("Issued_By"));
                r.setInspectedBy(new Staff());
                r.getInspectedBy().setStaffID(rs.getString("Inspected_By"));
                r.setCollectBackBy(new Staff());
                r.getCollectBackBy().setStaffID(rs.getString("Collect_Back_By"));
                r.setItemPassedBackBy(new CollectAddress());
                r.getItemPassedBackBy().setCollectAddrID(rs.getString("Item_Passed_Back_by"));
                r.setItemReceivedBy(new Staff());
                r.getItemReceivedBy().setStaffID(rs.getString("Item_Received_By"));
                // Status(""));
                r.setStatus(rs.getString("Status"));
                r.setCreatedDate(rs.getTimestamp("Created_Date"));
                r.setActualCreatedDateTime(rs.getTimestamp("Actual_Created_Date"));
                r.setSignedDocPic(rs.getString("Signed_Doc_Pic"));
                r.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));

                returnDeliveryNotes.add(r);
            }

            //return object
            return returnDeliveryNotes;
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

}
