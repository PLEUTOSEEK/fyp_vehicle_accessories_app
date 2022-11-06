/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import BizRulesConfiguration.WarehouseRules;
import Entity.PackingSlip;
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
public class PackingSlipDAO {

    public static String saveNewPackingSlip(PackingSlip packingSlip) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        packingSlip.setCreatedDate(timestamp);
        packingSlip.setActualCreatedDateTime(timestamp);
        packingSlip.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "INSERT INTO PackingSlip( "
                    + "PS_ID,"
                    + "TO_ID,"
                    + "Actual_Created_Date, "
                    + "Modified_Date_Time "
                    + ") "
                    + "VALUES (?,?,?,?);";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, packingSlip.getCode());
            ps.setString(2, packingSlip.getTO().getCode());
            ps.setTimestamp(3, packingSlip.getActualCreatedDateTime());
            ps.setTimestamp(4, packingSlip.getModifiedDateTime());
            ps.execute();

            return packingSlip.getCode();
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

    public static String updatePackingSlip(PackingSlip packingSlip) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        packingSlip.setCreatedDate(timestamp);
        packingSlip.setActualCreatedDateTime(timestamp);
        packingSlip.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE PackingSlip SET "
                    + "TO_ID = ?, "
                    + "Actual_Created_Date = ?, "
                    + "Modified_Date_Time = ? "
                    + "WHERE "
                    + "PS_ID = ? ";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, packingSlip.getTO().getCode());
            ps.setTimestamp(2, packingSlip.getActualCreatedDateTime());
            ps.setTimestamp(3, packingSlip.getModifiedDateTime());
            ps.setString(4, packingSlip.getCode());

            ps.execute();

            return packingSlip.getCode();
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

    public static List<PackingSlip> getPSsByDOID(String code) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        List<PackingSlip> packingSlips = new ArrayList<>();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM PackingSlip WHERE DO_ID = ?;";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, code);
            rs = ps.executeQuery();

            while (rs.next()) {

                PackingSlip packingSlip = new PackingSlip();
                packingSlip.setCode(rs.getString("PS_ID"));
                packingSlip.setTO(rs.getString("TO_ID").isEmpty() ? null : TransferOrderDAO.getTransferOrderByCode(rs.getString("TO_ID")));
                packingSlip.setDO(rs.getString("DO_ID").isEmpty() ? null : DeliveryOrderDAO.getDeliveryOrderByCode(rs.getString("DO_ID")));
                packingSlip.setStatus(rs.getString("Status"));
                packingSlip.setActualCreatedDateTime(rs.getTimestamp("Actual_Created_Date"));
                packingSlip.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));

                packingSlips.add(packingSlip);
            }

            //return object
            return packingSlips;
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

    public static List<PackingSlip> getPSsByTOID(String code) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        List<PackingSlip> packingSlips = new ArrayList<>();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM PackingSlip WHERE TO_ID = ?;";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, code);
            rs = ps.executeQuery();

            while (rs.next()) {

                PackingSlip packingSlip = new PackingSlip();
                packingSlip.setCode(rs.getString("PS_ID"));
                packingSlip.setTO(rs.getString("TO_ID").isEmpty() ? null : TransferOrderDAO.getTransferOrderByCode(rs.getString("TO_ID")));
                packingSlip.setDO(rs.getString("DO_ID").isEmpty() ? null : DeliveryOrderDAO.getDeliveryOrderByCode(rs.getString("DO_ID")));
                packingSlip.setStatus(rs.getString("Status"));
                packingSlip.setActualCreatedDateTime(rs.getTimestamp("Actual_Created_Date"));
                packingSlip.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));

                packingSlips.add(packingSlip);
            }

            //return object
            return packingSlips;
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

    public static boolean updatePackingSlipsStatus(List<PackingSlip> packingSlips) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        try {
            conn = SQLDatabaseConnection.openConn();
            conn.setAutoCommit(false);

            query = "UPDATE PackingSlip SET "
                    + "Status = ?, "
                    + "DO_ID = ? "
                    + "WHERE "
                    + "PS_ID = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            int i = 0;

            for (PackingSlip packingSlip : packingSlips) {
                ps.setString(1, WarehouseRules.PSStatus.REFERED.toString());
                ps.setString(2, packingSlip.getDO().getCode());
                ps.setString(3, packingSlip.getCode());

                ps.addBatch();

                i++;
                if (i % 1000 == 0 || i == packingSlips.size()) {
                    ps.executeBatch(); // Execute every 1000 items.
                }
            }

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