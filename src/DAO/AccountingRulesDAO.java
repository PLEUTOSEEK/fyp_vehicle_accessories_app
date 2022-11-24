/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class AccountingRulesDAO {

    public static Double getTaxRate() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT "
                    + "    [Value] "
                    + "FROM  "
                    + "    ConfigurationParameters "
                    + "WHERE "
                    + "    [Key] = 'Tax_Rate'";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            if (rs.next()) {
                return Double.parseDouble(rs.getString("Value"));
            } else {
                //return object
                return null;
            }

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

    public static boolean updateTaxRate(String value) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE [ConfigurationParameters] "
                    + "   SET  [Value] = ? "
                    + " WHERE [Key] = ?";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, value);
            ps.setString(2, "Tax_Rate");

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

}
