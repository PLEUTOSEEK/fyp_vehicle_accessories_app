/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.CollectAddress;
import Entity.Contact;
import Entity.Person;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class CollectAddressDAO {

    public static CollectAddress getCollectAddressByID(String ID) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        CollectAddress collectAddr = new CollectAddress();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            while (rs.next()) {
                collectAddr = new CollectAddress(
                        rs.getTimestamp("COLL_CreatedDate"),
                        rs.getTimestamp("COLL_Modified_Date_Time"),
                        rs.getString("COLL_Collect_Address_ID"),
                        CustomerDAO.getCustomerByID(rs.getString("COLL_Customer_ID")),
                        new Person(
                                null,
                                null,
                                Base64.decodeBase64(rs.getString("COLL_Avatar_Img")),
                                rs.getString("COLL_Name"),
                                rs.getString("COLL_Gender"),
                                rs.getDate("COLL_DOB"),
                                rs.getString("COLL_IC"),
                                rs.getString("COLL_Marital_Status"),
                                rs.getString("COLL_Nationality"),
                                rs.getString("COLL_Honorifics"),
                                AddressDAO.getAddressByID(rs.getString("COLL_Residential_Address")),
                                AddressDAO.getAddressByID(rs.getString("COLL_Corresponding_Address")),
                                new Contact(
                                        rs.getString("COLL_Email"),
                                        rs.getString("COLL_Mobile_No"),
                                        rs.getString("COLL_Extension_No"),
                                        rs.getString("COLL_Office_Phone_No"),
                                        rs.getString("COLL_Home_Phone_No")
                                ),
                                rs.getString("COLL_Occupation"),
                                rs.getString("COLL_Race"),
                                rs.getString("COLL_Religion"),
                                null
                        ),
                        AddressDAO.getAddressByID(rs.getString("COLL_Address_ID"))
                );
            }

            //return object
            return collectAddr;
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
