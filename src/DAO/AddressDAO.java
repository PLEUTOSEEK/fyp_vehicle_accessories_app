/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Address;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class AddressDAO {

    public static Address getAddressByID(String ID) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        Address addr = new Address();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_Retrieve_All_Address WHERE ADDR_Address_ID = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, ID);

            rs = ps.executeQuery();

            if (rs.next()) {
                addr = new Address(
                        rs.getTimestamp("ADDR_Created_Date"),
                        rs.getTimestamp("ADDR_Modified_Date_Time"),
                        rs.getString("ADDR_Address_ID"),
                        rs.getString("ADDR_Location_Name"),
                        rs.getString("ADDR_Address"),
                        rs.getString("ADDR_City"),
                        rs.getString("ADDR_Postal_Code"),
                        rs.getString("ADDR_State"),
                        rs.getString("ADDR_Country")
                );
            }

            //return object
            return addr;
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

    public static String saveNewAddress(Address address) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        address.setCreatedDate(timestamp);
        address.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "INSERT INTO Address ( "
                    + "Address_ID, "
                    + "Location_Name, "
                    + "Address, "
                    + "City, "
                    + "Postal_Code, "
                    + "State, "
                    + "Country, "
                    + "Created_Date, "
                    + "Modified_Date_Time) "
                    + "VALUES (?,?,?,?,?,?,?,?,?) ";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, address.getAddressID());
            ps.setString(2, address.getLocationName());
            ps.setString(3, address.getAddress());
            ps.setString(4, address.getCity());
            ps.setString(5, address.getPostalCode());
            ps.setString(6, address.getState());
            ps.setString(7, address.getCountry());
            ps.setTimestamp(8, address.getCreatedDate());
            ps.setTimestamp(9, address.getModifiedDateTime());
            ps.execute();

            return address.getAddressID();
        } catch (Exception e) {
            System.out.println("Address Insert" + e.getMessage());
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

    public static String getLatestID() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        String latestCode = null;

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_Address_LatestID";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            if (rs.next()) {
                latestCode = rs.getString("Address_ID");
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

    public static String updateAddress(Address addr) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        addr.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE Address SET "
                    + "Location_Name = ?, "
                    + "Address = ?, "
                    + "City = ?, "
                    + "Postal_Code = ?, "
                    + "State = ?, "
                    + "Country = ?, "
                    + "Modified_Date_Time = ? "
                    + "WHERE "
                    + "Address_ID = ?";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, addr.getLocationName());
            ps.setString(2, addr.getAddress());
            ps.setString(3, addr.getCity());
            ps.setString(4, addr.getPostalCode());
            ps.setString(5, addr.getState());
            ps.setString(6, addr.getCountry());
            ps.setTimestamp(7, addr.getModifiedDateTime());
            ps.setString(8, addr.getAddressID());

            ps.execute();
            return addr.getAddressID();
        } catch (Exception e) {
            System.out.println("Address Update" + e.getMessage());
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
