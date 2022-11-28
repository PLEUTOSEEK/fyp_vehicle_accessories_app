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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class CollectAddressDAO {

    public static List<CollectAddress> getCollectAddressByCustID(String ID) {
        /*

         */
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        CollectAddress collectAddress = new CollectAddress();
        List<CollectAddress> collectAddrs = new ArrayList<>();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT [Collect_Address_ID] "
                    + "      ,[Address_ID] "
                    + "      ,[Customer_ID] "
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
                    + "      ,[Created_Date] "
                    + "      ,[Modified_Date_Time] "
                    + "  FROM [dbo].[CollectAddress] "
                    + "  WHERE [Customer_ID] = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, ID);

            rs = ps.executeQuery();

            while (rs.next()) {
                collectAddress = new CollectAddress();

                collectAddress.setCollectAddrID(rs.getString("Collect_Address_ID"));
                collectAddress.setAddr(AddressDAO.getAddressByID(rs.getString("Address_ID")));
                collectAddress.setCustomer(CustomerDAO.getCustomerByID(rs.getString("Customer_ID")));
                collectAddress.setPerson(new Person());
                collectAddress.getPerson().setAvatarImg(rs.getString("Avatar_Img"));
                collectAddress.getPerson().setName(rs.getString("Name"));
                collectAddress.getPerson().setGender(rs.getString("Gender"));
                collectAddress.getPerson().setDOB(rs.getDate("DOB"));
                collectAddress.getPerson().setIC(rs.getString("IC"));
                collectAddress.getPerson().setMaritalStatus(rs.getString("Marital_Status"));
                collectAddress.getPerson().setNationality(rs.getString("Nationality"));
                collectAddress.getPerson().setHonorifics(rs.getString("Honorifics"));
                collectAddress.getPerson().setResidentialAddr(AddressDAO.getAddressByID(rs.getString("Residential_Address")));
                collectAddress.getPerson().setCorAddr(AddressDAO.getAddressByID(rs.getString("Corresponding_Address")));
                collectAddress.getPerson().setContact(new Contact());
                collectAddress.getPerson().getContact().setEmail(rs.getString("Email"));
                collectAddress.getPerson().getContact().setMobileNo(rs.getString("Mobile_No"));
                collectAddress.getPerson().getContact().setExt(rs.getString("Extension_No"));
                collectAddress.getPerson().getContact().setOffPhNo(rs.getString("Office_Phone_No"));
                collectAddress.getPerson().getContact().setHomePhNo(rs.getString("Home_Phone_No"));
                collectAddress.getPerson().setOccupation(rs.getString("Occupation"));
                collectAddress.getPerson().setRace(rs.getString("Race"));
                collectAddress.getPerson().setReligion(rs.getString("Religion"));
                collectAddress.setCreatedDate(rs.getTimestamp("Created_Date"));
                collectAddress.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));

                collectAddrs.add(collectAddress);
            }

            //return object
            return collectAddrs;
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

    public static CollectAddress getCollectAddressByID(String ID) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        CollectAddress collectAddress = new CollectAddress();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT [Collect_Address_ID] "
                    + "      ,[Address_ID] "
                    + "      ,[Customer_ID] "
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
                    + "      ,[Created_Date] "
                    + "      ,[Modified_Date_Time] "
                    + "  FROM [dbo].[CollectAddress] "
                    + "  WHERE [Collect_Address_ID] = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, ID);

            rs = ps.executeQuery();

            if (rs.next()) {
                collectAddress = new CollectAddress();

                collectAddress.setCollectAddrID(rs.getString("Collect_Address_ID"));
                collectAddress.setAddr(AddressDAO.getAddressByID(rs.getString("Address_ID")));
                collectAddress.setCustomer(CustomerDAO.getCustomerByID(rs.getString("Customer_ID")));
                collectAddress.setPerson(new Person());
                collectAddress.getPerson().setAvatarImg(rs.getString("Avatar_Img"));
                collectAddress.getPerson().setName(rs.getString("Name"));
                collectAddress.getPerson().setGender(rs.getString("Gender"));
                collectAddress.getPerson().setDOB(rs.getDate("DOB"));
                collectAddress.getPerson().setIC(rs.getString("IC"));
                collectAddress.getPerson().setMaritalStatus(rs.getString("Marital_Status"));
                collectAddress.getPerson().setNationality(rs.getString("Nationality"));
                collectAddress.getPerson().setHonorifics(rs.getString("Honorifics"));
                collectAddress.getPerson().setResidentialAddr(AddressDAO.getAddressByID(rs.getString("Residential_Address")));
                collectAddress.getPerson().setCorAddr(AddressDAO.getAddressByID(rs.getString("Corresponding_Address")));
                collectAddress.getPerson().setContact(new Contact());
                collectAddress.getPerson().getContact().setEmail(rs.getString("Email"));
                collectAddress.getPerson().getContact().setMobileNo(rs.getString("Mobile_No"));
                collectAddress.getPerson().getContact().setExt(rs.getString("Extension_No"));
                collectAddress.getPerson().getContact().setOffPhNo(rs.getString("Office_Phone_No"));
                collectAddress.getPerson().getContact().setHomePhNo(rs.getString("Home_Phone_No"));
                collectAddress.getPerson().setOccupation(rs.getString("Occupation"));
                collectAddress.getPerson().setRace(rs.getString("Race"));
                collectAddress.getPerson().setReligion(rs.getString("Religion"));
                collectAddress.setCreatedDate(rs.getTimestamp("Created_Date"));
                collectAddress.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));

                //return object
                return collectAddress;
            } else {
                return null;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
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

            query = "INSERT INTO CollectAddress "
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
            ps.setString(4, collectAddr.getPerson().getAvatarImg());
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
            System.out.println("Collector Insert" + e.getMessage());
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
                    + "Modified_Date_Time = ? "
                    + "WHERE "
                    + "Collect_Address_ID = ?";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, collAddr.getAddr().getAddressID());
            ps.setString(2, collAddr.getCustomer().getCustID());
            ps.setString(3, collAddr.getPerson().getAvatarImg());
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
            ps.setTimestamp(21, collAddr.getModifiedDateTime());
            ps.setString(22, collAddr.getCollectAddrID());

            ps.execute();

            return collAddr.getCollectAddrID();
        } catch (Exception e) {
            System.out.println("Collector Update" + e.getMessage());

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

    public static List<CollectAddress> getAllCollectAddress() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        CollectAddress collectAddress = new CollectAddress();
        List<CollectAddress> collectAddrs = new ArrayList<>();

        try {
            conn = SQLDatabaseConnection.openConn();
            query = "SELECT [Collect_Address_ID] "
                    + "      ,[Address_ID] "
                    + "      ,[Customer_ID] "
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
                    + "      ,[Created_Date] "
                    + "      ,[Modified_Date_Time] "
                    + "  FROM [dbo].[CollectAddress] ";
            ps = conn.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                collectAddress = new CollectAddress();

                collectAddress.setCollectAddrID(rs.getString("Collect_Address_ID"));
                collectAddress.setAddr(AddressDAO.getAddressByID(rs.getString("Address_ID")));
                collectAddress.setCustomer(CustomerDAO.getCustomerByID(rs.getString("Customer_ID")));
                collectAddress.setPerson(new Person());
                collectAddress.getPerson().setAvatarImg(rs.getString("Avatar_Img"));
                collectAddress.getPerson().setName(rs.getString("Name"));
                collectAddress.getPerson().setGender(rs.getString("Gender"));
                collectAddress.getPerson().setDOB(rs.getDate("DOB"));
                collectAddress.getPerson().setIC(rs.getString("IC"));
                collectAddress.getPerson().setMaritalStatus(rs.getString("Marital_Status"));
                collectAddress.getPerson().setNationality(rs.getString("Nationality"));
                collectAddress.getPerson().setHonorifics(rs.getString("Honorifics"));
                collectAddress.getPerson().setResidentialAddr(AddressDAO.getAddressByID(rs.getString("Residential_Address")));
                collectAddress.getPerson().setCorAddr(AddressDAO.getAddressByID(rs.getString("Corresponding_Address")));
                collectAddress.getPerson().setContact(new Contact());
                collectAddress.getPerson().getContact().setEmail(rs.getString("Email"));
                collectAddress.getPerson().getContact().setMobileNo(rs.getString("Mobile_No"));
                collectAddress.getPerson().getContact().setExt(rs.getString("Extension_No"));
                collectAddress.getPerson().getContact().setOffPhNo(rs.getString("Office_Phone_No"));
                collectAddress.getPerson().getContact().setHomePhNo(rs.getString("Home_Phone_No"));
                collectAddress.getPerson().setOccupation(rs.getString("Occupation"));
                collectAddress.getPerson().setRace(rs.getString("Race"));
                collectAddress.getPerson().setReligion(rs.getString("Religion"));
                collectAddress.setCreatedDate(rs.getTimestamp("Created_Date"));
                collectAddress.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));

                collectAddrs.add(collectAddress);
            }

            //return object
            return collectAddrs;
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

            query = "SELECT * FROM View_CollectAddress_LatestID";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            if (rs.next()) {
                latestID = rs.getString("Collect_Address_ID");
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
