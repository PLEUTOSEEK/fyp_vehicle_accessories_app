/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

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

    public static List<PackingSlip> getPSsBySOID(String code) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        List<PackingSlip> packingSlips = new ArrayList<>();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT "
                    + "    PackingSlip.PS_ID,"
                    + "    TransferOrder.TO_ID,"
                    + "    PackingSlip.Status"
                    + "     "
                    + "FROM "
                    + "    PackingSlip INNER JOIN "
                    + "    TransferOrder "
                    + "    ON PackingSlip.TO_ID = TransferOrder.TO_ID INNER JOIN "
                    + "    SalesOrder "
                    + "    ON TransferOrder.Req_Type_Ref = SalesOrder.SO_ID "
                    + "WHERE "
                    + "    SalesOrder.SO_ID = ? "
                    + "    AND TransferOrder.[Status] = 'TRANFERRED' ";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, code);
            rs = ps.executeQuery();

            while (rs.next()) {

                PackingSlip packingSlip = new PackingSlip();
                packingSlip.setCode(rs.getString("PS_ID"));
                packingSlip.setTO(rs.getString("TO_ID").isEmpty() ? null : TransferOrderDAO.getTransferOrderByCode(rs.getString("TO_ID")));
                packingSlip.setStatus(rs.getString("Status"));

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
                ps.setString(1, packingSlip.getStatus());
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

    public static List<PackingSlip> getAllPackingSlips() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        PackingSlip p = new PackingSlip();
        List<PackingSlip> packingSlips = new ArrayList<>();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT [PS_ID] "
                    + "      ,[TO_ID] "
                    + "      ,[DO_ID] "
                    + "      ,[Status] "
                    + "      ,[Actual_Created_Date] "
                    + "      ,[Modified_Date_Time] "
                    + "  FROM [dbo].[PackingSlip]";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();
            while (rs.next()) {
                p = new PackingSlip();
                p.setCode(rs.getString("PS_ID"));
                p.setTO(TransferOrderDAO.getTransferOrderByCode(rs.getString("TO_ID")));
                p.setDO(DeliveryOrderDAO.getDeliveryOrderByCode(rs.getString("DO_ID")));
                p.setStatus(rs.getString("Status"));
                p.setActualCreatedDateTime(rs.getTimestamp("Actual_Created_Date"));
                p.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));

                packingSlips.add(p);
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

}
