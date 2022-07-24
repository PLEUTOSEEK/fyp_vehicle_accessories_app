/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Address;
import Entity.Contact;
import Entity.Place;
import Entity.Staff;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class StaffDAO {

    private static Staff getReportToByID(String ID) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        Staff staff = new Staff();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_Retrieve_All_Staff WHERE STAFF_Staff_ID = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, ID);
            // bind parameter
            rs = ps.executeQuery();

            if (rs.next()) {
                staff.setStaffID(rs.getString("STAFF_Staff_ID"));
                staff.setAvatarImg(Base64.decodeBase64(rs.getString("STAFF_Avatar_Img")));
                staff.setName(rs.getString("STAFF_Name"));
                staff.setGender(rs.getString("STAFF_Gender"));
                staff.setDOB(rs.getDate("STAFF_DOB"));
                staff.setIC(rs.getString("STAFF_IC"));
                staff.setMaritalStatus(rs.getString("STAFF_Marital_Status"));
                staff.setNationality(rs.getString("STAFF_Nationality"));
                staff.setHonorifics(rs.getString("STAFF_Honorifics"));

                Address residentialAddr = new Address();
                residentialAddr.setAddressID(rs.getString("STAFF_ADDR_Address_ID"));
                residentialAddr.setLocationName(rs.getString("STAFF_ADDR_Location_Name"));
                residentialAddr.setAddress(rs.getString("STAFF_ADDR_Address"));
                residentialAddr.setCity(rs.getString("STAFF_ADDR_City"));
                residentialAddr.setPostalCode(rs.getString("STAFF_ADDR_Postal_Code"));
                residentialAddr.setState(rs.getString("STAFF_ADDR_State"));
                residentialAddr.setCountry(rs.getString("STAFF_ADDR_Country"));
                residentialAddr.setCreatedDate(rs.getTimestamp("STAFF_ADDR_Created_Date"));
                residentialAddr.setModifiedDateTime(rs.getTimestamp("STAFF_ADDR_Modified_Date_Time"));

                staff.setResidentialAddr(residentialAddr);

                Address corAddr = new Address();
                corAddr.setAddressID(rs.getString("STAFF_ADDR2_Address_ID"));
                corAddr.setLocationName(rs.getString("STAFF_ADDR2_Location_Name"));
                corAddr.setAddress(rs.getString("STAFF_ADDR2_Address"));
                corAddr.setCity(rs.getString("STAFF_ADDR2_City"));
                corAddr.setPostalCode(rs.getString("STAFF_ADDR2_Postal_Code"));
                corAddr.setState(rs.getString("STAFF_ADDR2_State"));
                corAddr.setCountry(rs.getString("STAFF_ADDR2_Country"));
                corAddr.setCreatedDate(rs.getTimestamp("STAFF_ADDR2_Created_Date"));
                corAddr.setModifiedDateTime(rs.getTimestamp("STAFF_ADDR2_Modified_Date_Time"));

                staff.setCorAddr(corAddr);

                Contact contact = new Contact();
                contact.setEmail(rs.getString("STAFF_Email"));
                contact.setExt(rs.getString("STAFF_Extension_No"));
                contact.setOffPhNo(rs.getString("STAFF_Office_Phone_No"));
                contact.setHomePhNo(rs.getString("STAFF_Home_Phone_No"));

                staff.setContact(contact);

                staff.setOccupation(rs.getString("STAFF_Occupation"));
                staff.setRace(rs.getString("STAFF_Race"));
                staff.setReligion(rs.getString("STAFF_Religion"));

                Place workPlace = new Place();
                workPlace.setPlaceID(rs.getString("STAFF_PLACE_Place_ID"));
                workPlace.setPlaceName(rs.getString("STAFF_PLACE_Place_Name"));
                Address workPlaceAddress = new Address();
                workPlaceAddress.setAddressID(rs.getString("STAFF_PLACE_ADDR_Address_ID"));
                workPlaceAddress.setLocationName(rs.getString("STAFF_PLACE_ADDR_Location_Name"));
                workPlaceAddress.setAddress(rs.getString("STAFF_PLACE_ADDR_Address"));
                workPlaceAddress.setCity(rs.getString("STAFF_PLACE_ADDR_City"));
                workPlaceAddress.setPostalCode(rs.getString("STAFF_PLACE_ADDR_Postal_Code"));
                workPlaceAddress.setState(rs.getString("STAFF_PLACE_ADDR_State"));
                workPlaceAddress.setCountry(rs.getString("STAFF_PLACE_ADDR_Country"));
                workPlaceAddress.setCreatedDate(rs.getTimestamp("STAFF_PLACE_ADDR_Created_Date"));
                workPlaceAddress.setModifiedDateTime(rs.getTimestamp("STAFF_PLACE_ADDR_Modified_Date_Time"));
                workPlace.setPlaceAddr(workPlaceAddress);
                workPlace.setDescription(rs.getString("STAFF_PLACE_Description"));
                workPlace.setCreatedDate(rs.getTimestamp("STAFF_PLACE_Created_Date"));
                workPlace.setModifiedDateTime(rs.getTimestamp("STAFF_PLACE_Modified_Date_Time"));

                staff.setWorkPlace(workPlace);

                staff.setEntryDate(rs.getDate("STAFF_Entry_Date"));
                staff.setEmpType(rs.getString("STAFF_Employee_Type"));
                staff.setPassword(rs.getString("STAFF_Password"));
                staff.setStatus(rs.getString("STAFF_Status"));
                staff.setCreatedDate(rs.getTimestamp("STAFF_Created_Date"));
                staff.setModifiedDateTime(rs.getTimestamp("STAFF_Modified_Date_Time"));

                return staff;
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

    public static Staff getStaffByID(String ID) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        Staff staff = new Staff();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_Retrieve_All_Staff WHERE STAFF_Staff_ID = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, ID);
            // bind parameter
            rs = ps.executeQuery();

            if (rs.next()) {
                staff.setStaffID(rs.getString("STAFF_Staff_ID"));
                staff.setAvatarImg(Base64.decodeBase64(rs.getString("STAFF_Avatar_Img")));
                staff.setName(rs.getString("STAFF_Name"));
                staff.setGender(rs.getString("STAFF_Gender"));
                staff.setDOB(rs.getDate("STAFF_DOB"));
                staff.setIC(rs.getString("STAFF_IC"));
                staff.setMaritalStatus(rs.getString("STAFF_Marital_Status"));
                staff.setNationality(rs.getString("STAFF_Nationality"));
                staff.setHonorifics(rs.getString("STAFF_Honorifics"));

                Address residentialAddr = new Address();
                residentialAddr.setAddressID(rs.getString("STAFF_ADDR_Address_ID"));
                residentialAddr.setLocationName(rs.getString("STAFF_ADDR_Location_Name"));
                residentialAddr.setAddress(rs.getString("STAFF_ADDR_Address"));
                residentialAddr.setCity(rs.getString("STAFF_ADDR_City"));
                residentialAddr.setPostalCode(rs.getString("STAFF_ADDR_Postal_Code"));
                residentialAddr.setState(rs.getString("STAFF_ADDR_State"));
                residentialAddr.setCountry(rs.getString("STAFF_ADDR_Country"));
                residentialAddr.setCreatedDate(rs.getTimestamp("STAFF_ADDR_Created_Date"));
                residentialAddr.setModifiedDateTime(rs.getTimestamp("STAFF_ADDR_Modified_Date_Time"));

                staff.setResidentialAddr(residentialAddr);

                Address corAddr = new Address();
                corAddr.setAddressID(rs.getString("STAFF_ADDR2_Address_ID"));
                corAddr.setLocationName(rs.getString("STAFF_ADDR2_Location_Name"));
                corAddr.setAddress(rs.getString("STAFF_ADDR2_Address"));
                corAddr.setCity(rs.getString("STAFF_ADDR2_City"));
                corAddr.setPostalCode(rs.getString("STAFF_ADDR2_Postal_Code"));
                corAddr.setState(rs.getString("STAFF_ADDR2_State"));
                corAddr.setCountry(rs.getString("STAFF_ADDR2_Country"));
                corAddr.setCreatedDate(rs.getTimestamp("STAFF_ADDR2_Created_Date"));
                corAddr.setModifiedDateTime(rs.getTimestamp("STAFF_ADDR2_Modified_Date_Time"));

                staff.setCorAddr(corAddr);

                Contact contact = new Contact();
                contact.setEmail(rs.getString("STAFF_Email"));
                contact.setExt(rs.getString("STAFF_Extension_No"));
                contact.setOffPhNo(rs.getString("STAFF_Office_Phone_No"));
                contact.setHomePhNo(rs.getString("STAFF_Home_Phone_No"));

                staff.setContact(contact);

                staff.setOccupation(rs.getString("STAFF_Occupation"));
                staff.setRace(rs.getString("STAFF_Race"));
                staff.setReligion(rs.getString("STAFF_Religion"));

                Place workPlace = new Place();
                workPlace.setPlaceID(rs.getString("STAFF_PLACE_Place_ID"));
                workPlace.setPlaceName(rs.getString("STAFF_PLACE_Place_Name"));
                Address workPlaceAddress = new Address();
                workPlaceAddress.setAddressID(rs.getString("STAFF_PLACE_ADDR_Address_ID"));
                workPlaceAddress.setLocationName(rs.getString("STAFF_PLACE_ADDR_Location_Name"));
                workPlaceAddress.setAddress(rs.getString("STAFF_PLACE_ADDR_Address"));
                workPlaceAddress.setCity(rs.getString("STAFF_PLACE_ADDR_City"));
                workPlaceAddress.setPostalCode(rs.getString("STAFF_PLACE_ADDR_Postal_Code"));
                workPlaceAddress.setState(rs.getString("STAFF_PLACE_ADDR_State"));
                workPlaceAddress.setCountry(rs.getString("STAFF_PLACE_ADDR_Country"));
                workPlaceAddress.setCreatedDate(rs.getTimestamp("STAFF_PLACE_ADDR_Created_Date"));
                workPlaceAddress.setModifiedDateTime(rs.getTimestamp("STAFF_PLACE_ADDR_Modified_Date_Time"));
                workPlace.setPlaceAddr(workPlaceAddress);
                workPlace.setDescription(rs.getString("STAFF_PLACE_Description"));
                workPlace.setCreatedDate(rs.getTimestamp("STAFF_PLACE_Created_Date"));
                workPlace.setModifiedDateTime(rs.getTimestamp("STAFF_PLACE_Modified_Date_Time"));

                staff.setWorkPlace(workPlace);

                staff.setEntryDate(rs.getDate("STAFF_Entry_Date"));
                staff.setEmpType(rs.getString("STAFF_Employee_Type"));
                staff.setPassword(rs.getString("STAFF_Password"));
                staff.setStatus(rs.getString("STAFF_Status"));
                staff.setCreatedDate(rs.getTimestamp("STAFF_Created_Date"));
                staff.setModifiedDateTime(rs.getTimestamp("STAFF_Modified_Date_Time"));
                staff.setReportTo(getReportToByID(rs.getString("STAFF_Report_To")));
                return staff;
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

    public static boolean saveNewStaff(Staff staff) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "INSERT INTO Staff("
                    + "Staff_ID, "
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
                    + "Officed_Phone_No, "
                    + "Home_Phone_No, "
                    + "Occupation, "
                    + "Race, "
                    + "Religion, "
                    + "Work_Place, "
                    + "Entry_Date, "
                    + "Report_To, "
                    + "Employee_Type, "
                    + "Password, "
                    + "Status, "
                    + "Created_Date, "
                    + "Modified_Date_Time "
                    + ")"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, staff.getStaffID());
            ps.setString(2, Base64.encodeBase64String(staff.getAvatarImg()));
            ps.setString(3, staff.getName());
            ps.setString(4, staff.getGender());
            ps.setDate(5, staff.getDOB());
            ps.setString(6, staff.getIC());
            ps.setString(7, staff.getMaritalStatus());
            ps.setString(8, staff.getNationality());
            ps.setString(9, staff.getHonorifics());
            ps.setString(10, staff.getResidentialAddr().getAddressID());
            ps.setString(11, staff.getCorAddr().getAddressID());
            ps.setString(12, staff.getContact().getEmail());
            ps.setString(13, staff.getContact().getMobileNo());
            ps.setString(14, staff.getContact().getExt());
            ps.setString(15, staff.getContact().getOffPhNo());
            ps.setString(16, staff.getContact().getHomePhNo());
            ps.setString(17, staff.getOccupation());
            ps.setString(18, staff.getRace());
            ps.setString(19, staff.getReligion());
            ps.setString(20, staff.getWorkPlace().getPlaceID());
            ps.setDate(21, staff.getEntryDate());
            ps.setString(22, staff.getReportTo().getStaffID());
            ps.setString(23, staff.getEmpType());
            ps.setString(24, staff.getPassword());
            ps.setString(25, staff.getStatus());
            ps.setTimestamp(26, staff.getCreatedDate());
            ps.setTimestamp(27, staff.getModifiedDateTime());

            ps.execute();
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

    public static String getLatestID() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        String latestCode = null;

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_Staff_LatestID";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            if (rs.next()) {
                latestCode = rs.getString("Staff_ID");
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

}
