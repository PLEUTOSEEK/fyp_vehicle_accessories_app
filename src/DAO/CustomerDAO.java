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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
                customer = new Customer();

                customer.setCustID(rs.getString("Customer_ID"));
                customer.setBillToAddr(AddressDAO.getAddressByID("Bill_To_Addr"));
                customer.setAvatarImg(rs.getString(rs.getString("Avatar_Img")));
                customer.setName(rs.getString("Name"));
                customer.setGender(rs.getString("Gender"));
                customer.setDOB(rs.getDate("DOB"));
                customer.setIC(rs.getString("IC"));
                customer.setMaritalStatus(rs.getString("Marital_Status"));
                customer.setNationality(rs.getString("Nationality"));
                customer.setHonorifics(rs.getString("Honorifics"));
                customer.setResidentialAddr(AddressDAO.getAddressByID(rs.getString("Residential_Address")));
                customer.setCorAddr(AddressDAO.getAddressByID(rs.getString("Corresponding_Address")));
                customer.setContact(new Contact());
                customer.getContact().setEmail(rs.getString("Email"));
                customer.getContact().setMobileNo(rs.getString("Mobile_No"));
                customer.getContact().setExt(rs.getString("Extension_No"));
                customer.getContact().setOffPhNo(rs.getString("Office_Phone_No"));
                customer.getContact().setHomePhNo(rs.getString("Home_Phone_No"));
                customer.setOccupation(rs.getString("Occupation"));
                customer.setRace(rs.getString("Race"));
                customer.setReligion(rs.getString("Religion"));
                customer.setBankAccProvider(rs.getString("Bank_Acc_Provider"));
                customer.setBankAccOwnerName(rs.getString("Bank_Acc_Owner_Name"));
                customer.setBankAccNo(rs.getString("Bank_Acc_No"));
                customer.setCustType(rs.getString("Customer_Type"));
                customer.setStatus(rs.getString("Status"));
                customer.setCreatedDate(rs.getTimestamp("Created_Date"));
                customer.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));

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

    public static String saveNewCustomer(Customer customer) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        customer.setCreatedDate(timestamp);
        customer.setModifiedDateTime(timestamp);

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
                    + "Bank_Acc_Owner_Name, "
                    + "Bank_Acc_No, "
                    + "Customer_Type, "
                    + "Status, "
                    + "Created_Date, "
                    + "Modified_Date_Time, "
                    + ") "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            ps.setString(1, customer.getCustID());
            ps.setString(2, customer.getBillToAddr().getAddressID());
            ps.setString(3, customer.getAvatarImg());
            ps.setString(4, customer.getName());
            ps.setString(5, customer.getGender());
            ps.setDate(6, customer.getDOB());
            ps.setString(7, customer.getIC());
            ps.setString(8, customer.getMaritalStatus());
            ps.setString(9, customer.getNationality());
            ps.setString(10, customer.getHonorifics());
            ps.setString(11, customer.getResidentialAddr().getAddressID());
            ps.setString(12, customer.getCorAddr().getAddressID());
            ps.setString(13, customer.getContact().getEmail());
            ps.setString(14, customer.getContact().getMobileNo());
            ps.setString(15, customer.getContact().getExt());
            ps.setString(16, customer.getContact().getOffPhNo());
            ps.setString(17, customer.getContact().getHomePhNo());
            ps.setString(18, customer.getOccupation());
            ps.setString(19, customer.getRace());
            ps.setString(20, customer.getReligion());
            ps.setString(21, customer.getBankAccProvider());
            ps.setString(22, customer.getBankAccOwnerName());
            ps.setString(23, customer.getBankAccNo());
            ps.setString(24, customer.getCustType());
            ps.setString(25, customer.getStatus());
            ps.setTimestamp(26, customer.getCreatedDate());
            ps.setTimestamp(27, customer.getModifiedDateTime());

            return customer.getCustID();

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

    public static String updateCustomer(Customer customer) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        customer.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE Customer SET"
                    + "Bill_To_Addr = ?, "
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
                    + "Bank_Acc_Provider = ?, "
                    + "Bank_Acc_No = ?, "
                    + "Customer_Type = ?, "
                    + "Status = ?, "
                    + "Created_Date = ?, "
                    + "Modified_Date_Time = ? "
                    + "WHERE Customer_ID = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            ps.setString(1, customer.getBillToAddr().getAddressID());
            ps.setString(2, customer.getAvatarImg());
            ps.setString(3, customer.getName());
            ps.setString(4, customer.getGender());
            ps.setDate(5, customer.getDOB());
            ps.setString(6, customer.getIC());
            ps.setString(7, customer.getMaritalStatus());
            ps.setString(8, customer.getNationality());
            ps.setString(9, customer.getHonorifics());
            ps.setString(10, customer.getResidentialAddr().getAddressID());
            ps.setString(11, customer.getCorAddr().getAddressID());
            ps.setString(12, customer.getContact().getEmail());
            ps.setString(13, customer.getContact().getMobileNo());
            ps.setString(14, customer.getContact().getExt());
            ps.setString(15, customer.getContact().getOffPhNo());
            ps.setString(16, customer.getContact().getHomePhNo());
            ps.setString(17, customer.getOccupation());
            ps.setString(18, customer.getRace());
            ps.setString(19, customer.getReligion());
            ps.setString(20, customer.getBankAccProvider());
            ps.setString(21, customer.getBankAccOwnerName());
            ps.setString(22, customer.getBankAccNo());
            ps.setString(23, customer.getCustType());
            ps.setString(24, customer.getStatus());
            ps.setTimestamp(25, customer.getCreatedDate());
            ps.setTimestamp(26, customer.getModifiedDateTime());
            ps.setString(27, customer.getCustID());

            return customer.getCustID();

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

    public static List<Customer> getAllCustomers() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        List<Customer> customers = new ArrayList<>();
        Customer c = new Customer();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT [Customer_ID] "
                    + "      ,[Bill_To_Addr] "
                    + "      ,[Avatar_Img] "
                    + "      ,[Name] "
                    + "      ,[Gender] "
                    + "      ,[DOB] "
                    + "      ,[IC] "
                    + "      ,[Marital_Status] "
                    + "      ,[Nationality] "
                    + "      ,[Honorifics] "
                    + "      ,[Residential_Address] "
                    + "      ,[Corresponding_Address] "
                    + "      ,[Email] "
                    + "      ,[Mobile_No] "
                    + "      ,[Extension_No] "
                    + "      ,[Office_Phone_No] "
                    + "      ,[Home_Phone_No] "
                    + "      ,[Occupation] "
                    + "      ,[Race] "
                    + "      ,[Religion] "
                    + "      ,[Bank_Acc_Provider] "
                    + "      ,[Bank_Acc_Owner_Name] "
                    + "      ,[Bank_Acc_No] "
                    + "      ,[Customer_Type] "
                    + "      ,[Status] "
                    + "      ,[Created_Date] "
                    + "      ,[Modified_Date_Time] "
                    + "  FROM [dbo].[Customer]";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            while (rs.next()) {
                c = new Customer();

                c.setCustID(rs.getString("Customer_ID"));
                c.setBillToAddr(AddressDAO.getAddressByID("Bill_To_Addr"));
                c.setAvatarImg(rs.getString(rs.getString("Avatar_Img")));
                c.setName(rs.getString("Name"));
                c.setGender(rs.getString("Gender"));
                c.setDOB(rs.getDate("DOB"));
                c.setIC(rs.getString("IC"));
                c.setMaritalStatus(rs.getString("Marital_Status"));
                c.setNationality(rs.getString("Nationality"));
                c.setHonorifics(rs.getString("Honorifics"));
                c.setResidentialAddr(new Address());
                c.getResidentialAddr().setAddressID(rs.getString("Residential_Address"));
                c.setCorAddr(new Address());
                c.getCorAddr().setAddressID(rs.getString("Corresponding_Address"));
                c.setContact(new Contact());
                c.getContact().setEmail(rs.getString("Email"));
                c.getContact().setMobileNo(rs.getString("Mobile_No"));
                c.getContact().setExt(rs.getString("Extension_No"));
                c.getContact().setOffPhNo(rs.getString("Office_Phone_No"));
                c.getContact().setHomePhNo(rs.getString("Home_Phone_No"));
                c.setOccupation(rs.getString("Occupation"));
                c.setRace(rs.getString("Race"));
                c.setReligion(rs.getString("Religion"));
                c.setBankAccProvider(rs.getString("Bank_Acc_Provider"));
                c.setBankAccOwnerName(rs.getString("Bank_Acc_Owner_Name"));
                c.setBankAccNo(rs.getString("Bank_Acc_No"));
                c.setCustType(rs.getString("Customer_Type"));
                c.setStatus(rs.getString("Status"));
                c.setCreatedDate(rs.getTimestamp("Created_Date"));
                c.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));

                customers.add(c);
            }

            //return object
            return customers;
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
