/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Receipt;
import java.math.BigDecimal;
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
public class ReceiptDAO {

    public static String updateReceipt(Receipt receipt) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        receipt.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE [dbo].[Receipt] "
                    + "   SET [INV_ID] =  ? "
                    + "      ,[Reference_Type] =  ? "
                    + "      ,[Reference] =  ? "
                    + "      ,[Total_Payable = ? "
                    + "      ,[Paid_Amount] = ? "
                    + "      ,[Prev_Paid_Amount] = ? "
                    + "      ,[Bal_Unpaid] = ? "
                    + "      ,[Created_Date] =  ? "
                    + "      ,[Signed_Doc_Pic] =  ? "
                    + "      ,[Modified_Date_Time] =  ? "
                    + " WHERE [RCPT_ID] =  ? ";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, receipt.getINV().getCode());
            ps.setString(2, receipt.getReferenceType());
            ps.setString(3, receipt.getReference());
            ps.setBigDecimal(4, receipt.getTtlPayable());
            ps.setBigDecimal(5, receipt.getPaidAmt());
            ps.setBigDecimal(6, receipt.getPaidAmtPrev());
            ps.setBigDecimal(7, receipt.getBalUnpaid());
            ps.setTimestamp(8, receipt.getCreatedDate());
            ps.setString(9, receipt.getSignedDocPic());
            ps.setTimestamp(10, receipt.getModifiedDateTime());
            ps.setString(11, receipt.getCode());

            ps.execute();
            return receipt.getCode();
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

    public static String saveNewReceipt(Receipt receipt) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        receipt.setActualCreatedDateTime(timestamp);
        receipt.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "INSERT INTO [dbo].[Receipt] "
                    + "           ([RCPT_ID] "
                    + "           ,[INV_ID] "
                    + "           ,[Reference_Type] "
                    + "           ,[Reference] "
                    + "           ,[Total_Payable] "
                    + "           ,[Paid_Amount] "
                    + "           ,[Prev_Paid_Amount] "
                    + "           ,[Bal_Unpaid] "
                    + "           ,[Created_Date] "
                    + "           ,[Actual_Created_Date] "
                    + "           ,[Signed_Doc_Pic] "
                    + "           ,[Modified_Date_Time]) "
                    + "     VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, receipt.getCode());
            ps.setString(2, receipt.getINV().getCode());
            ps.setString(3, receipt.getReferenceType());
            ps.setString(4, receipt.getReference());
            ps.setBigDecimal(5, receipt.getTtlPayable());
            ps.setBigDecimal(6, receipt.getPaidAmt());
            ps.setBigDecimal(7, receipt.getPaidAmtPrev());
            ps.setBigDecimal(8, receipt.getBalUnpaid());
            ps.setTimestamp(9, receipt.getCreatedDate());
            ps.setTimestamp(10, receipt.getActualCreatedDateTime());
            ps.setString(11, receipt.getSignedDocPic());
            ps.setTimestamp(12, receipt.getModifiedDateTime());
            ps.execute();
            return receipt.getCode();
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

    public static List<Receipt> getAllReceipt() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        Receipt r = new Receipt();
        List<Receipt> receipts = new ArrayList<>();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT [RCPT_ID] "
                    + "      ,[INV_ID] "
                    + "      ,[Reference_Type] "
                    + "      ,[Reference] "
                    + "      ,[Total_Payable] "
                    + "      ,[Paid_Amount] "
                    + "      ,[Prev_Paid_Amount] "
                    + "      ,[Bal_Unpaid] "
                    + "      ,[Created_Date] "
                    + "      ,[Actual_Created_Date] "
                    + "      ,[Signed_Doc_Pic] "
                    + "      ,[Modified_Date_Time] "
                    + "  FROM [dbo].[Receipt]";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            while (rs.next()) {
                r = new Receipt();

                r.setCode(rs.getString("RCPT_ID"));
                r.setINV(InvoiceDAO.getInvoiceByID(rs.getString("INV_ID")));
                r.setReferenceType(rs.getString("Reference_Type"));
                r.setReference(rs.getString("Reference"));
                r.setTtlPayable(rs.getBigDecimal("Total_Payable"));
                r.setPaidAmt(rs.getBigDecimal("Paid_Amount"));
                r.setPaidAmtPrev(rs.getBigDecimal("Prev_Paid_Amount"));
                r.setBalUnpaid(rs.getBigDecimal("Bal_Unpaid"));
                r.setCreatedDate(rs.getTimestamp("Created_Date"));
                r.setActualCreatedDateTime(rs.getTimestamp("Actual_Created_Date"));
                r.setSignedDocPic(rs.getString("Signed_Doc_Pic"));
                r.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));
                receipts.add(r);
            }

            //return object
            return receipts;
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
            query = "SELECT * FROM View_Receipt_LatestID";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                latestCode = rs.getString("RCPT_ID");
            }

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

    public static BigDecimal getPrevPaid(String invCode) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        BigDecimal ttlPrevPaid = new BigDecimal("0.00");

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT "
                    + "    ISNULL(SUM(Paid_Amount),0) AS TTL_PREV_PAID "
                    + "FROM "
                    + "    Receipt "
                    + "WHERE "
                    + "    INV_ID = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, invCode);
            rs = ps.executeQuery();

            if (rs.next()) {
                ttlPrevPaid = rs.getBigDecimal("TTL_PREV_PAID");
            }

            //return object
            return ttlPrevPaid;
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
