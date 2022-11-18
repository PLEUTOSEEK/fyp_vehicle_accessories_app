/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.PaymentTerm;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

}
