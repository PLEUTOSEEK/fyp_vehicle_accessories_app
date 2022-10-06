/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.PlaceDAO;
import Entity.Place;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class PlaceService {

    public static List<Place> getAllPlaces() {
        return PlaceDAO.getAllPlaces();
    }
}
