/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Address;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

            query = "";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            while (rs.next()) {
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
}
