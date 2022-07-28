/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Contact;
import Entity.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class CustomerDAO {

    public static Customer getCustomerByID(String code) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        Customer customer = new Customer();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_Retrieve_All_Customer WHERE CUST_Customer_ID = ? ";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, code);

            rs = ps.executeQuery();

            if (rs.next()) {
                customer = new Customer(
                        rs.getTimestamp("CUST_Created_Date"),
                        rs.getTimestamp("CUST_Modified_Date_Time"),
                        Base64.decodeBase64(rs.getString("CUST_Avatar_Img")),
                        rs.getString("CUST_Name"),
                        rs.getString("CUST_Gender"),
                        rs.getDate("CUST_DOB"),
                        rs.getString("CUST_IC"),
                        rs.getString("CUST_Marital_Status"),
                        rs.getString("CUST_Nationality"),
                        rs.getString("CUST_Honorifics"),
                        AddressDAO.getAddressByID(rs.getString("CUST_Residential_Address")),
                        AddressDAO.getAddressByID(rs.getString("CUST_Corresponding_Address")),
                        new Contact(
                                rs.getString("CUST_Email"),
                                rs.getString("CUST_Mobile_No"),
                                rs.getString("CUST_Extension_No"),
                                rs.getString("CUST_Office_Phone_No"),
                                rs.getString("CUST_Home_Phone_No")
                        ),
                        rs.getString("CUST_Occupation"),
                        rs.getString("CUST_Race"),
                        rs.getString("CUST_Religion"),
                        rs.getString("CUST_Status"),
                        rs.getString("CUST_Customer_ID"),
                        rs.getString("CUST_Bank_Acc_Provider"),
                        rs.getString("CUST_Bank_Acc_No"),
                        AddressDAO.getAddressByID(rs.getString("CUST_Bill_To_Addr")),
                        null,
                        rs.getString("CUST_Customer_Type")
                );
                return customer;
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

    public static boolean saveNewCustomer(Customer customer) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "INSERT INTO Customer("
                    + "Customer_ID, "
                    + "Bill_To_Addr, "
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
                    + "Bank_Acc_Provider, "
                    + "Bank_Acc_No, "
                    + "Customer_Type, "
                    + "Status, "
                    + "Created_Date, "
                    + "Modified_Date_Time, "
                    + ") "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            ps.setString(1, customer.getCustID());
            ps.setString(2, customer.getBillToAddr().getAddressID());
            ps.setString(3, Base64.encodeBase64String(customer.getAvatarImg()));
            ps.setString(4, customer.getName());
            ps.setString(5, customer.getGender());
            ps.setDate(6, customer.getDOB());
            ps.setString(7, customer.getIC());
            ps.setString(8, customer.getMaritalStatus());
            ps.setString(9, customer.getNationality());
            ps.setString(10, customer.getHonorifics());
            ps.setString(11, customer.getResidentialAddr().getAddressID());
            ps.setString(12, customer.getCorAddr().getAddress());
            ps.setString(13, customer.getContact().getEmail());
            ps.setString(14, customer.getContact().getMobileNo());
            ps.setString(15, customer.getContact().getExt());
            ps.setString(16, customer.getContact().getOffPhNo());
            ps.setString(17, customer.getContact().getHomePhNo());
            ps.setString(18, customer.getOccupation());
            ps.setString(19, customer.getRace());
            ps.setString(20, customer.getReligion());
            ps.setString(21, customer.getBankAccProvider());
            ps.setString(22, customer.getBankAccNo());
            ps.setString(23, customer.getCustType());
            ps.setString(24, customer.getStatus());
            ps.setTimestamp(25, customer.getCreatedDate());
            ps.setTimestamp(26, customer.getModifiedDateTime());

            return true;

            //return object
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

    public static String getLatestID() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        String latestID = null;

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_Customer_LatestID";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            if (rs.next()) {
                latestID = rs.getString("Customer_ID");
            }

            return latestID;

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
