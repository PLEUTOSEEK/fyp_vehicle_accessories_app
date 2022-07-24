/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Address;
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

            query = "SELECT * FROM ";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            if (rs.next()) {
                customer.setCustID(rs.getString("CUST_Customer_ID"));

                Address billToAddr = new Address();
                billToAddr.setAddressID(rs.getString("CUST_ADDR_Address_ID"));
                billToAddr.setLocationName(rs.getString("CUST_ADDR_Location_Name"));
                billToAddr.setAddress(rs.getString("CUST_ADDR_Address"));
                billToAddr.setCity(rs.getString("CUST_ADDR_City"));
                billToAddr.setPostalCode(rs.getString("CUST_ADDR_Postal_Code"));
                billToAddr.setState(rs.getString("CUST_ADDR_State"));
                billToAddr.setCountry(rs.getString("CUST_ADDR_Country"));
                billToAddr.setCreatedDate(rs.getTimestamp("CUST_ADDR_Created_Date"));
                billToAddr.setModifiedDateTime(rs.getTimestamp("CUST_ADDR_Modified_Date_Time"));
                customer.setBillToAddr(billToAddr);

                customer.setAvatarImg(Base64.decodeBase64(rs.getString("CUST_Avatar_Img")));
                customer.setName(rs.getString("CUST_Name"));
                customer.setGender(rs.getString("CUST_Gender"));
                customer.setDOB(rs.getDate("CUST_DOB"));
                customer.setIC(rs.getString("CUST_IC"));
                customer.setMaritalStatus(rs.getString("CUST_Marital_Status"));
                customer.setNationality(rs.getString("CUST_Nationality"));
                customer.setHonorifics(rs.getString("CUST_Honorifics"));

                Address residentialAddr = new Address();
                residentialAddr.setAddressID(rs.getString("CUST_ADDR2_Address_ID"));
                residentialAddr.setLocationName(rs.getString("CUST_ADDR2_Location_Name"));
                residentialAddr.setAddress(rs.getString("CUST_ADDR2_Address"));
                residentialAddr.setCity(rs.getString("CUST_ADDR2_City"));
                residentialAddr.setPostalCode(rs.getString("CUST_ADDR2_Postal_Code"));
                residentialAddr.setState(rs.getString("CUST_ADDR2_State"));
                residentialAddr.setCountry(rs.getString("CUST_ADDR2_Country"));
                residentialAddr.setCreatedDate(rs.getTimestamp("CUST_ADDR2_Created_Date"));
                residentialAddr.setModifiedDateTime(rs.getTimestamp("CUST_ADDR2_Modified_Date_Time"));
                customer.setResidentialAddr(residentialAddr);

                Address corAddr = new Address();
                corAddr.setAddressID(rs.getString("CUST_ADDR3_Address_ID"));
                corAddr.setLocationName(rs.getString("CUST_ADDR3_Location_Name"));
                corAddr.setAddress(rs.getString("CUST_ADDR3_Address"));
                corAddr.setCity(rs.getString("CUST_ADDR3_City"));
                corAddr.setPostalCode(rs.getString("CUST_ADDR3_Postal_Code"));
                corAddr.setState(rs.getString("CUST_ADDR3_State"));
                corAddr.setCountry(rs.getString("CUST_ADDR3_Country"));
                corAddr.setCreatedDate(rs.getTimestamp("CUST_ADDR3_Created_Date"));
                corAddr.setModifiedDateTime(rs.getTimestamp("CUST_ADDR3_Modified_Date_Time"));
                customer.setCorAddr(corAddr);

                Contact contact = new Contact();
                contact.setEmail(rs.getString("CUST_Email"));
                contact.setMobileNo(rs.getString("CUST_Mobile_No"));
                contact.setExt(rs.getString("CUST_Extension_No"));
                contact.setOffPhNo(rs.getString("CUST_Office_Phone_No"));
                contact.setHomePhNo(rs.getString("CUST_Home_Phone_No"));
                customer.setContact(contact);

                customer.setOccupation(rs.getString("CUST_Occupation"));
                customer.setRace(rs.getString("CUST_Race"));
                customer.setReligion(rs.getString("CUST_Religion"));
                customer.setBankAccProvider(rs.getString("CUST_Bank_Acc_Provider"));
                customer.setBankAccNo(rs.getString("CUST_Bank_Acc_No"));
                customer.setCustType(rs.getString("CUST_Customer_Type"));
                customer.setStatus(rs.getString("CUST_Status"));
                customer.setCreatedDate(rs.getTimestamp("CUST_Created_Date"));
                customer.setModifiedDateTime(rs.getTimestamp("CUST_Modified_Date_Time"));
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
