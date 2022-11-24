/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class SalesRulesDAO {

    public static Integer getMaxQuotationValidityPeriod() {
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
                    + "    [Key] = 'Maximum_Quotation_Validity_Period'";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            if (rs.next()) {
                return Integer.parseInt(rs.getString("Value"));
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

    public static Double getUpperLimitPercentageDiscount() {
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
                    + "    [Key] = 'Upper_Limit_Percent_Discount'";
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

    public static BigDecimal getMaxOrderAmtperSO() {
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
                    + "    [Key] = 'Maximum_Order_Amt_per_SO'";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            if (rs.next()) {
                return new BigDecimal(rs.getString("Value"));
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

    public static boolean updateMaxQuotationValidityPeriod(String value) {
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
            ps.setString(2, "Maximum_Quotation_Validity_Period");

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

    public static boolean updateUpperLimitPercentageDiscount(String value) {
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
            ps.setString(2, "Upper_Limit_Percent_Discount");

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

    public static boolean updateMaxOrderAmtperSO(String value) {
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
            ps.setString(2, "Maximum_Order_Amt_per_SO");

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
