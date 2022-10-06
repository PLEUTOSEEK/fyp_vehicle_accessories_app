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
import java.sql.Timestamp;
import java.util.List;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class CollectAddressDAO {

    public static List<CollectAddress> getCollectAddressByCustID(String ID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static CollectAddress getCollectAddressByID(String ID) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        CollectAddress collectAddr = new CollectAddress();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_Retrieve_All_CollectAddress WHERE COLL_Collect_Address_ID = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, ID);

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

    public static String saveNewCollectAddress(CollectAddress collectAddr) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        collectAddr.setCreatedDate(timestamp);
        collectAddr.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "INSERT INTO "
                    + "(Collect_Address_ID, "
                    + "Address_ID, "
                    + "Customer_ID, "
                    + "Avatar_Img, "
                    + "Name, "
                    + "Gender, "
                    + "DOB, "
                    + "IC, "
                    + "Marital_Status, "
                    + "Nationality, "
                    + "Honorifics, "
                    + "Residential_Address, "
                    + "Corresponding_Address, "
                    + "Email, "
                    + "Mobile_No, "
                    + "Extension_No, "
                    + "Office_Phone_No, "
                    + "Home_Phone_No, "
                    + "Occupation, "
                    + "Race, "
                    + "Religion, "
                    + "Created_Date, "
                    + "Modified_Date_Time) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, collectAddr.getCollectAddrID());
            ps.setString(2, collectAddr.getAddr().getAddressID());
            ps.setString(3, collectAddr.getCustomer().getCustID());
            ps.setString(4, Base64.encodeBase64String(collectAddr.getPerson().getAvatarImg()));
            ps.setString(5, collectAddr.getPerson().getName());
            ps.setString(6, collectAddr.getPerson().getGender());
            ps.setDate(7, collectAddr.getPerson().getDOB());
            ps.setString(8, collectAddr.getPerson().getIC());
            ps.setString(9, collectAddr.getPerson().getMaritalStatus());
            ps.setString(10, collectAddr.getPerson().getNationality());
            ps.setString(11, collectAddr.getPerson().getHonorifics());
            ps.setString(12, collectAddr.getPerson().getResidentialAddr().getAddressID());
            ps.setString(13, collectAddr.getPerson().getCorAddr().getAddressID());
            ps.setString(14, collectAddr.getPerson().getContact().getEmail());
            ps.setString(15, collectAddr.getPerson().getContact().getMobileNo());
            ps.setString(16, collectAddr.getPerson().getContact().getExt());
            ps.setString(17, collectAddr.getPerson().getContact().getOffPhNo());
            ps.setString(18, collectAddr.getPerson().getContact().getHomePhNo());
            ps.setString(19, collectAddr.getPerson().getOccupation());
            ps.setString(20, collectAddr.getPerson().getRace());
            ps.setString(21, collectAddr.getPerson().getReligion());
            ps.setTimestamp(22, collectAddr.getCreatedDate());
            ps.setTimestamp(23, collectAddr.getModifiedDateTime());

            ps.execute();
            return collectAddr.getCollectAddrID();
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

    public static String updateCollectAddress(CollectAddress collAddr) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        collAddr.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE CollectAddress SET "
                    + "Address_ID = ?, "
                    + "Customer_ID = ?, "
                    + "Avatar_Img = ?, "
                    + "Name = ?, "
                    + "Gender = ?, "
                    + "DOB = ?, "
                    + "IC = ?, "
                    + "Marital_Status = ?, "
                    + "Nationality = ?, "
                    + "Honorifics = ?, "
                    + "Residential_Address = ?, "
                    + "Corresponding_Address = ?, "
                    + "Email = ?, "
                    + "Mobile_No = ?, "
                    + "Extension_No = ?, "
                    + "Office_Phone_No = ?, "
                    + "Home_Phone_No = ?, "
                    + "Occupation = ?, "
                    + "Race = ?, "
                    + "Religion = ?, "
                    + "Created_Date = ?, "
                    + "Modified_Date_Time = ? "
                    + "WHERE "
                    + "Collect_Address_ID = ?";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, collAddr.getAddr().getAddressID());
            ps.setString(2, collAddr.getCustomer().getCustID());
            ps.setString(3, Base64.encodeBase64String(collAddr.getPerson().getAvatarImg()));
            ps.setString(4, collAddr.getPerson().getName());
            ps.setString(5, collAddr.getPerson().getGender());
            ps.setDate(6, collAddr.getPerson().getDOB());
            ps.setString(7, collAddr.getPerson().getIC());
            ps.setString(8, collAddr.getPerson().getMaritalStatus());
            ps.setString(9, collAddr.getPerson().getNationality());
            ps.setString(10, collAddr.getPerson().getHonorifics());
            ps.setString(11, collAddr.getPerson().getResidentialAddr().getAddressID());
            ps.setString(12, collAddr.getPerson().getCorAddr().getAddressID());
            ps.setString(13, collAddr.getPerson().getContact().getEmail());
            ps.setString(14, collAddr.getPerson().getContact().getMobileNo());
            ps.setString(15, collAddr.getPerson().getContact().getExt());
            ps.setString(16, collAddr.getPerson().getContact().getOffPhNo());
            ps.setString(17, collAddr.getPerson().getContact().getHomePhNo());
            ps.setString(18, collAddr.getPerson().getOccupation());
            ps.setString(19, collAddr.getPerson().getRace());
            ps.setString(20, collAddr.getPerson().getReligion());
            ps.setTimestamp(21, collAddr.getCreatedDate());
            ps.setTimestamp(22, collAddr.getModifiedDateTime());
            ps.setString(23, collAddr.getCollectAddrID());

            ps.execute();

            return collAddr.getCollectAddrID();
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
