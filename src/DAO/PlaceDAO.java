/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Contact;
import Entity.Place;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class PlaceDAO {

    public static List<Place> getAllPlaces() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        List<Place> places = new ArrayList<>();
        Place place = new Place();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT   [Place_ID] "
                    + "      ,[Place_Name] "
                    + "      ,[Address_ID] "
                    + "      ,[Description] "
                    + "      ,[Email] "
                    + "      ,[Office_Phone_No] "
                    + "      ,[Created_Date] "
                    + "      ,[Modified_Date_Time] "
                    + "  FROM Place";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            while (rs.next()) {
                place = new Place();

                place.setPlaceID(rs.getString("Place_ID"));
                place.setPlaceName(rs.getString("Place_Name"));
                place.setPlaceAddr(AddressDAO.getAddressByID(rs.getString("Address_ID")));
                place.setDescription(rs.getString("Description"));
                place.setContact(new Contact());
                place.getContact().setEmail(rs.getString("Email"));
                place.getContact().setOffPhNo(rs.getString("Office_Phone_No"));
                place.setCreatedDate(rs.getTimestamp("Created_Date"));
                place.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));
                places.add(place);
            }

            //return object
            return places;
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

    public static Place getPlaceByID(String ID) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        Place place = new Place();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_Retrieve_All_Place WHERE PLACE_Place_ID = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, ID);

            rs = ps.executeQuery();

            if (rs.next()) {
                place = new Place(
                        rs.getTimestamp("PLACE_Created_Date"),
                        rs.getTimestamp("PLACE_Modified_Date_Time"),
                        rs.getString("PLACE_Place_ID"),
                        rs.getString("PLACE_Place_Name"),
                        AddressDAO.getAddressByID(rs.getString("PLACE_Address_ID")),
                        rs.getString("PLACE_Description"),
                        new Contact(
                                rs.getString("PLACE_Email"),
                                null,
                                null,
                                rs.getString("PLACE_Office_Phone_No"),
                                null
                        )
                );
                return place;
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
}
