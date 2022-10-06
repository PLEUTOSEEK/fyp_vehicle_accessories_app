/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Entity.Place;
import Entity.Staff;
import PassObjs.BasicObjs;
import Service.PlaceService;
import Service.StaffService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tee Zhuo Xuan
 */
public class InnerEntitySelectCONTR implements Initializable {

    /**
     * Initializes the controller class.
     */
    private static List<String> rowSelected = new ArrayList<>();

    private Stage parentStage;
    @FXML
    private MFXButton btnCancel;
    private BasicObjs passObj;
    @FXML
    private MFXTableView<?> tblVw;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("before receive data");
                receiveData();
                setupTable();
                tblVw.autosizeColumnsOnInitialization();
            }
        }
        );

    }

    private void setupTable() {
        if (this.passObj.getObj() instanceof Place) {
            forPlace();
        } else if (this.passObj.getObj() instanceof Staff) {
            forStaff();
        }
    }

    private void forPlace() {
        // Place ID
        MFXTableColumn<Place> placeIDCol = new MFXTableColumn<>("Place ID", true, Comparator.comparing(place -> place.getPlaceID()));
        // Place Name
        MFXTableColumn<Place> placeNmCol = new MFXTableColumn<>("Place Name", true, Comparator.comparing(place -> place.getPlaceName()));
        // Location Name
        MFXTableColumn<Place> locationNmCol = new MFXTableColumn<>("Location Name", true, Comparator.comparing(place -> place.getPlaceAddr().getLocationName()));
        // Address
        MFXTableColumn<Place> addrCol = new MFXTableColumn<>("Address", true, Comparator.comparing(place -> place.getPlaceAddr().getAddress()));
        // City
        MFXTableColumn<Place> cityCol = new MFXTableColumn<>("City", true, Comparator.comparing(place -> place.getPlaceAddr().getCity()));

        // Place ID
        placeIDCol.setRowCellFactory(place -> new MFXTableRowCell<>(plc -> plc.getPlaceID()));
        // Place Name
        placeNmCol.setRowCellFactory(place -> new MFXTableRowCell<>(plc -> plc.getPlaceName()));
        // Location Name
        locationNmCol.setRowCellFactory(place -> new MFXTableRowCell<>(plc -> plc.getPlaceAddr().getLocationName()));
        // Address
        addrCol.setRowCellFactory(place -> new MFXTableRowCell<>(plc -> plc.getPlaceAddr().getAddress()));
        // City
        cityCol.setRowCellFactory(place -> new MFXTableRowCell<>(plc -> plc.getPlaceAddr().getCity()));

        ((MFXTableView<Place>) tblVw).getTableColumns().addAll(
                placeIDCol,
                placeNmCol,
                locationNmCol,
                addrCol,
                cityCol
        );

        ((MFXTableView<Place>) tblVw).getFilters().addAll(
                new StringFilter<>("Place ID", place -> place.getPlaceID()),
                new StringFilter<>("Place Name", place -> place.getPlaceName()),
                new StringFilter<>("Location Name", place -> place.getPlaceAddr().getLocationName()),
                new StringFilter<>("Address", place -> place.getPlaceAddr().getAddress()),
                new StringFilter<>("City", place -> place.getPlaceAddr().getCity())
        );

        List<Place> places = PlaceService.getAllPlaces();

        ((MFXTableView<Place>) tblVw).setItems(FXCollections.observableList(places));

        ((MFXTableView<Place>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<Place>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    Place place = (((MFXTableView<Place>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(place.getPlaceID());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            Stage stage = (Stage) btnCancel.getScene().getWindow();
                            BasicObjs passObj = new BasicObjs();
                            passObj.setObj(place);
                            stage.setUserData(passObj);
                            stage.close();
                        }
                        rowSelected.clear();
                    }
                }
            }
        });
    }

    private void forStaff() {
        // Staff ID
        MFXTableColumn<Staff> staffIDCol = new MFXTableColumn<>("Staff ID", true, Comparator.comparing(staff -> staff.getStaffID()));
        // Name
        MFXTableColumn<Staff> staffNmCol = new MFXTableColumn<>("Name", true, Comparator.comparing(staff -> staff.getName()));
        // Mobile No
        MFXTableColumn<Staff> mobNoCol = new MFXTableColumn<>("Mobile No.", true, Comparator.comparing(staff -> staff.getContact().getMobileNo()));
        // Office Phone Number
        MFXTableColumn<Staff> offPhNoCol = new MFXTableColumn<>("Office Phone No.", true, Comparator.comparing(staff -> staff.getContact().getOffPhNo()));
        // Occupation
        MFXTableColumn<Staff> occupationCol = new MFXTableColumn<>("Occupation", true, Comparator.comparing(staff -> staff.getOccupation()));
        // Role
        MFXTableColumn<Staff> roleCol = new MFXTableColumn<>("Role", true, Comparator.comparing(staff -> staff.getRole()));

        // Staff ID
        staffIDCol.setRowCellFactory(staff -> new MFXTableRowCell<>(s -> s.getStaffID()));
        // Name
        staffNmCol.setRowCellFactory(staff -> new MFXTableRowCell<>(s -> s.getName()));
        // Mobile No
        mobNoCol.setRowCellFactory(staff -> new MFXTableRowCell<>(s -> s.getContact().getMobileNo()));
        // Office Phone Number
        offPhNoCol.setRowCellFactory(staff -> new MFXTableRowCell<>(s -> s.getContact().getOffPhNo()));
        // Occupation
        occupationCol.setRowCellFactory(staff -> new MFXTableRowCell<>(s -> s.getOccupation()));
        // Role
        roleCol.setRowCellFactory(staff -> new MFXTableRowCell<>(s -> s.getRole()));

        ((MFXTableView<Staff>) tblVw).getTableColumns().addAll(
                staffIDCol,
                staffNmCol,
                mobNoCol,
                offPhNoCol,
                occupationCol,
                roleCol);

        ((MFXTableView<Staff>) tblVw).getFilters().addAll(
                new StringFilter<>("Staff ID", staff -> staff.getStaffID()),
                new StringFilter<>("Name", staff -> staff.getName()),
                new StringFilter<>("Mobile No.", staff -> staff.getContact().getMobileNo()),
                new StringFilter<>("Office Phone No.", staff -> staff.getContact().getOffPhNo()),
                new StringFilter<>("Occupation", staff -> staff.getOccupation()),
                new StringFilter<>("Role", staff -> staff.getRole())
        );

        List<Staff> staffs = StaffService.getAllStaff();

        ((MFXTableView<Staff>) tblVw).setItems(FXCollections.observableList(staffs));

        ((MFXTableView<Staff>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<Staff>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    Staff staff = (((MFXTableView<Staff>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(staff.getStaffID());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            Stage stage = (Stage) btnCancel.getScene().getWindow();
                            BasicObjs passObj = new BasicObjs();
                            passObj.setObj(staff);
                            stage.setUserData(passObj);
                            stage.close();
                        }
                        rowSelected.clear();
                    }
                }
            }
        });

        System.out.println("done go through function");
    }

    public void receiveData() {
        // Step 1
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // Step 2
        if (stage.getUserData() != null) {
            passObj = (BasicObjs) stage.getUserData();
        } else {
            passObj = new BasicObjs();
        }
    }

    @FXML
    private void closeModalWindow(MouseEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.setUserData(null);
        stage.close();
    }

}
