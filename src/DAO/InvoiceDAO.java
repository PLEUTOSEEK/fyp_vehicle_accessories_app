/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Invoice;
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
public class InvoiceDAO {

    public static String saveNewInvoice(Invoice invoice) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        invoice.setActualCreatedDateTime(timestamp);
        invoice.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "INSERT INTO [dbo].[Invoice] "
                    + "           ([INV_ID] "
                    + "           ,[SO_ID] "
                    + "           ,[Reference_Type] "
                    + "           ,[Reference] "
                    + "           ,[Gross] "
                    + "           ,[Discount] "
                    + "           ,[Sub_Total] "
                    + "           ,[Total_Payable] "
                    + "           ,[Issued_By] "
                    + "           ,[Released_And_Verified_By] "
                    + "           ,[Customer_Signed] "
                    + "           ,[Status] "
                    + "           ,[Created_Date] "
                    + "           ,[Actual_Created_Date] "
                    + "           ,[Signed_Doc_Pic] "
                    + "           ,[Modified_Date_Time]) "
                    + "     VALUES "
                    + "           (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, invoice.getCode());
            ps.setString(2, invoice.getSO().getCode());
            ps.setString(3, invoice.getReferenceType());
            ps.setString(4, invoice.getReference());
            ps.setBigDecimal(5, invoice.getGross());
            ps.setBigDecimal(6, invoice.getDiscount());
            ps.setBigDecimal(7, invoice.getSubTotal());
            ps.setBigDecimal(8, invoice.getTtlPayable());
            ps.setString(9, invoice.getIssuedBy().getStaffID());
            ps.setString(10, invoice.getReleasedAVerifiedBy().getStaffID());
            ps.setString(11, invoice.getCustomerSignature().getCollectAddrID());
            ps.setString(12, invoice.getStatus());
            ps.setTimestamp(13, invoice.getCreatedDate());
            ps.setTimestamp(14, invoice.getActualCreatedDateTime());
            ps.setString(15, invoice.getSignedDocPic());
            ps.setTimestamp(16, invoice.getModifiedDateTime());

            ps.execute();

            return invoice.getCode();
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

    public static Invoice getInvoiceByID(String code) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        Invoice invoice = new Invoice();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM Invoice WHERE INV_ID = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, code);
            rs = ps.executeQuery();

            if (rs.next()) {
                invoice.setSO(SalesOrderDAO.getSalesOrderByID(rs.getString("INV_ID")));
                invoice.setReferenceType(rs.getString("Reference_Type"));
                invoice.setReference(rs.getString("Reference"));
                invoice.setGross(rs.getBigDecimal("Gross"));
                invoice.setDiscount(rs.getBigDecimal("Discount"));
                invoice.setSubTotal(rs.getBigDecimal("Sub_Total"));
                invoice.setTtlPayable(rs.getBigDecimal("Total_Payable"));
                invoice.setIssuedBy(StaffDAO.getStaffByID(rs.getString("Issued_By")));
                invoice.setReleasedAVerifiedBy(StaffDAO.getStaffByID(rs.getString("Released_And_Verified_By")));
                invoice.setCustomerSignature(CollectAddressDAO.getCollectAddressByID(rs.getString("Customer_Signed")));
                invoice.setStatus(rs.getString("Status"));
                invoice.setCreatedDate(rs.getTimestamp("Created_Date"));
                invoice.setActualCreatedDateTime(rs.getTimestamp("Actual_Created_Date"));
                invoice.setSignedDocPic(rs.getString("Signed_Doc_Pic"));
                invoice.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));
            }

            //return object
            return invoice;
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
        String latestCode = "";

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_Invoice_LatestID";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            if (rs.next()) {
                latestCode = rs.getString("INV_ID");
            }

            //return object
            return latestCode;
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

    public static String updateInvoice(Invoice invoice) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        invoice.setActualCreatedDateTime(timestamp);
        invoice.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE [dbo].[Invoice] "
                    + "SET "
                    + "           ,[SO_ID] = ? "
                    + "           ,[Reference_Type] = ? "
                    + "           ,[Reference] = ? "
                    + "           ,[Gross] = ? "
                    + "           ,[Discount] = ? "
                    + "           ,[Sub_Total] = ? "
                    + "           ,[Total_Payable] = ? "
                    + "           ,[Issued_By] = ? "
                    + "           ,[Released_And_Verified_By] = ? "
                    + "           ,[Customer_Signed] = ? "
                    + "           ,[Status] = ? "
                    + "           ,[Created_Date] = ? "
                    + "           ,[Signed_Doc_Pic] = ? "
                    + "           ,[Modified_Date_Time] = ? "
                    + "WHERE "
                    + "           [INV_ID] = ?  ";

            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, invoice.getSO().getCode());
            ps.setString(2, invoice.getReferenceType());
            ps.setString(3, invoice.getReference());
            ps.setBigDecimal(4, invoice.getGross());
            ps.setBigDecimal(5, invoice.getDiscount());
            ps.setBigDecimal(6, invoice.getSubTotal());
            ps.setBigDecimal(7, invoice.getTtlPayable());
            ps.setString(8, invoice.getIssuedBy().getStaffID());
            ps.setString(9, invoice.getReleasedAVerifiedBy().getStaffID());
            ps.setString(10, invoice.getCustomerSignature().getCollectAddrID());
            ps.setString(11, invoice.getStatus());
            ps.setTimestamp(12, invoice.getCreatedDate());
            ps.setString(13, invoice.getSignedDocPic());
            ps.setTimestamp(14, invoice.getModifiedDateTime());
            ps.setString(15, invoice.getCode());

            ps.execute();

            return invoice.getCode();
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

    public static String updateInvoiceStatus(Invoice invoice) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        invoice.setActualCreatedDateTime(timestamp);
        invoice.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE [dbo].[Invoice] "
                    + "SET "
                    + "[Status] = ? "
                    + "WHERE "
                    + "[INV_ID] = ?  ";

            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, invoice.getStatus());
            ps.setString(2, invoice.getCode());

            ps.execute();

            return invoice.getCode();
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

    public static List<Invoice> getAllInvoices() {

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        Invoice invoice = new Invoice();
        List<Invoice> invoices = new ArrayList<>();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM Invoice";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            while (rs.next()) {
                invoice = new Invoice();
                invoice.setCode(rs.getString("INV_ID"));
                invoice.setSO(SalesOrderDAO.getSalesOrderByID(rs.getString("SO_ID")));
                invoice.setReferenceType(rs.getString("Reference_Type"));
                invoice.setReference(rs.getString("Reference"));
                invoice.setGross(rs.getBigDecimal("Gross"));
                invoice.setDiscount(rs.getBigDecimal("Discount"));
                invoice.setSubTotal(rs.getBigDecimal("Sub_Total"));
                invoice.setTtlPayable(rs.getBigDecimal("Total_Payable"));
                invoice.setIssuedBy(StaffDAO.getStaffByID(rs.getString("Issued_By")));
                invoice.setReleasedAVerifiedBy(StaffDAO.getStaffByID(rs.getString("Released_And_Verified_By")));
                invoice.setCustomerSignature(CollectAddressDAO.getCollectAddressByID(rs.getString("Customer_Signed")));
                invoice.setStatus(rs.getString("Status"));
                invoice.setCreatedDate(rs.getTimestamp("Created_Date"));
                invoice.setActualCreatedDateTime(rs.getTimestamp("Actual_Created_Date"));
                invoice.setSignedDocPic(rs.getString("Signed_Doc_Pic"));
                invoice.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));

                invoices.add(invoice);
            }

            //return object
            return invoices;
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
