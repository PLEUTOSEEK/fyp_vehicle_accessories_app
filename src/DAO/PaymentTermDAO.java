/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.PaymentTerm;
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
public class PaymentTermDAO {

    static PaymentTerm getPymtTermByID(String ID) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        PaymentTerm paymentTerm = new PaymentTerm();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT [Pymt_Term_ID] "
                    + "      ,[Pymt_Term_Name] "
                    + "      ,[Description] "
                    + "      ,[Baseline_Documet_Date] "
                    + "      ,[Days_Limit] "
                    + "      ,[Modified_Date_Time] "
                    + "      ,[Created_Date] "
                    + "  FROM [dbo].[PaymentTerm] "
                    + "  WHERE [Pymt_Term_ID] = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, ID);
            rs = ps.executeQuery();

            if (rs.next()) {
                paymentTerm.setPymtTermID(rs.getString("Pymt_Term_ID"));
                paymentTerm.setPymtTermName(rs.getString("Pymt_Term_Name"));
                paymentTerm.setDescription(rs.getString("Description"));
                paymentTerm.setBaseLineDocumentDate(rs.getString("Baseline_Documet_Date"));
                paymentTerm.setDaysLimit(rs.getInt("Days_Limit"));
                paymentTerm.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));
                paymentTerm.setCreatedDate(rs.getTimestamp("Created_Date"));

                return paymentTerm;
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

    public static List<PaymentTerm> getAllPaymentTerms() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        List<PaymentTerm> paymentTerms = new ArrayList<>();
        PaymentTerm pymtTerm = new PaymentTerm();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT   [Pymt_Term_ID] "
                    + "      ,[Pymt_Term_Name] "
                    + "      ,[Description] "
                    + "      ,[Baseline_Documet_Date] "
                    + "      ,[Days_Limit] "
                    + "      ,[Modified_Date_Time] "
                    + "      ,[Created_Date] "
                    + "  FROM [PaymentTerm]";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            while (rs.next()) {
                pymtTerm = new PaymentTerm();

                pymtTerm.setPymtTermID(rs.getString("Pymt_Term_ID"));
                pymtTerm.setPymtTermName(rs.getString("Pymt_Term_Name"));
                pymtTerm.setDescription(rs.getString("Description"));
                pymtTerm.setBaseLineDocumentDate(rs.getString("Baseline_Documet_Date"));
                pymtTerm.setDaysLimit(rs.getInt("Days_Limit"));
                pymtTerm.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));
                pymtTerm.setCreatedDate(rs.getTimestamp("Created_Date"));

                paymentTerms.add(pymtTerm);
            }

            //return object
            return paymentTerms;
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

    public static String updatePymtTerm(PaymentTerm pymtTerm) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        pymtTerm.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE [dbo].[PaymentTerm] "
                    + "   SET [Pymt_Term_Name] = ? "
                    + "      ,[Description] = ? "
                    + "      ,[Baseline_Documet_Date] = ? "
                    + "      ,[Days_Limit] = ? "
                    + "      ,[Modified_Date_Time] = ? "
                    + " WHERE  "
                    + " [Pymt_Term_ID] = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, pymtTerm.getPymtTermName());
            ps.setString(2, pymtTerm.getDescription());
            ps.setString(3, pymtTerm.getBaseLineDocumentDate());
            ps.setInt(4, pymtTerm.getDaysLimit());
            ps.setTimestamp(5, pymtTerm.getModifiedDateTime());
            ps.setString(6, pymtTerm.getPymtTermID());
            ps.execute();

            return pymtTerm.getPymtTermID();
        } catch (Exception e) {
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

    public static String insertPymtTerm(PaymentTerm pymtTerm) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        pymtTerm.setCreatedDate(timestamp);
        pymtTerm.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "INSERT INTO [dbo].[PaymentTerm] "
                    + "           ([Pymt_Term_ID] "
                    + "           ,[Pymt_Term_Name] "
                    + "           ,[Description] "
                    + "           ,[Baseline_Documet_Date] "
                    + "           ,[Days_Limit] "
                    + "           ,[Modified_Date_Time] "
                    + "           ,[Created_Date]) "
                    + "     VALUES "
                    + "           (?, ?, ?, ?, ?, ?, ?)";

            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, pymtTerm.getPymtTermID());
            ps.setString(2, pymtTerm.getPymtTermName());
            ps.setString(3, pymtTerm.getDescription());
            ps.setString(4, pymtTerm.getBaseLineDocumentDate());
            ps.setInt(5, pymtTerm.getDaysLimit());
            ps.setTimestamp(6, pymtTerm.getModifiedDateTime());
            ps.setTimestamp(7, pymtTerm.getCreatedDate());
            ps.execute();

            return pymtTerm.getPymtTermID();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    public static String getLatestCode() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        String latestID = "";

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_PymtTerm_LatestID";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            if (rs.next()) {
                latestID = rs.getString("Pymt_Term_ID");
            }

            return latestID;

            //return object
        } catch (Exception e) {
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
}
