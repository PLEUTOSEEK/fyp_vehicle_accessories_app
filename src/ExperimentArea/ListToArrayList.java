/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExperimentArea;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ListToArrayList {

    public static void main(String[] args) throws SQLException {
        PreparedStatement ps = null;
        List<String> listOfSomething = new ArrayList<>();
        ResultSet rs = ps.executeQuery("");

        while (rs.next()) {
            String current = rs.getString("whatever");
            listOfSomething.add(current);
        }
        ObservableList<String> observableListOfSomething = FXCollections.observableArrayList(listOfSomething);
    }
}
