/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Entity.CollectAddress;
import Entity.CustomerInquiry;
import Entity.Place;
import Entity.Quotation;
import Entity.Staff;
import PassObjs.BasicObjs;
import Service.CollectAddressService;
import Service.CustomerInquiryService;
import Service.PlaceService;
import Service.QuotationService;
import Service.StaffService;
import Utils.DateFilter;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
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
        } else if (this.passObj.getObj() instanceof CollectAddress) {
            forCollectAddress();
        } else if (this.passObj.getObj() instanceof Quotation) {
            forQuotation();
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

    }

    private void forCollectAddress() {
        // Collect Address ID
        // Collector Name
        // Collector Email
        // Collector Mobile Number
        // Collector Office Number
        // Customer Name
        // Address

        // Collect Address ID
        MFXTableColumn<CollectAddress> cllctAddrIDCol = new MFXTableColumn<>("Collect Address ID", true, Comparator.comparing(cllct -> cllct.getCollectAddrID()));
        // Collector Name
        MFXTableColumn<CollectAddress> cllctAddrNameCol = new MFXTableColumn<>("Collector Name", true, Comparator.comparing(cllct -> cllct.getPerson().getName()));
        // Collector Email
        MFXTableColumn<CollectAddress> cllctAddrEmailCol = new MFXTableColumn<>("Email", true, Comparator.comparing(cllct -> cllct.getPerson().getContact().getEmail()));
        // Collector Mobile Number
        MFXTableColumn<CollectAddress> cllctAddrMobNoCol = new MFXTableColumn<>("Mobile No.", true, Comparator.comparing(cllct -> cllct.getPerson().getContact().getMobileNo()));
        // Collector Office Number
        MFXTableColumn<CollectAddress> cllctAddrOffNoCol = new MFXTableColumn<>("Office No.", true, Comparator.comparing(cllct -> cllct.getPerson().getContact().getOffPhNo()));
        // Customer Name
        MFXTableColumn<CollectAddress> cllctAddrCustNmCol = new MFXTableColumn<>("Customer Name", true, Comparator.comparing(cllct -> cllct.getCustomer().getName()));
        // Address
        MFXTableColumn<CollectAddress> cllctAddrAddrCol = new MFXTableColumn<>("Collect Address", true, Comparator.comparing(cllct -> cllct.getAddr().getAddress()));

        // Collect Address ID
        cllctAddrIDCol.setRowCellFactory(cllct -> new MFXTableRowCell<>(c -> c.getCollectAddrID()));
        // Collector Name
        cllctAddrNameCol.setRowCellFactory(cllct -> new MFXTableRowCell<>(c -> c.getPerson().getName()));
        // Collector Email
        cllctAddrEmailCol.setRowCellFactory(cllct -> new MFXTableRowCell<>(c -> c.getPerson().getContact().getEmail()));
        // Collector Mobile Number
        cllctAddrMobNoCol.setRowCellFactory(cllct -> new MFXTableRowCell<>(c -> c.getPerson().getContact().getMobileNo()));
        // Collector Office Number
        cllctAddrOffNoCol.setRowCellFactory(cllct -> new MFXTableRowCell<>(c -> c.getPerson().getContact().getOffPhNo()));
        // Customer Name
        cllctAddrCustNmCol.setRowCellFactory(cllct -> new MFXTableRowCell<>(c -> c.getCustomer().getName()));
        // Address
        cllctAddrAddrCol.setRowCellFactory(cllct -> new MFXTableRowCell<>(c -> c.getAddr().getAddress()));

        ((MFXTableView<CollectAddress>) tblVw).getTableColumns().addAll(
                cllctAddrIDCol,
                cllctAddrNameCol,
                cllctAddrEmailCol,
                cllctAddrMobNoCol,
                cllctAddrOffNoCol,
                cllctAddrCustNmCol,
                cllctAddrAddrCol);

        ((MFXTableView<CollectAddress>) tblVw).getFilters().addAll(
                new StringFilter<>("Collect Address ID", c -> c.getCollectAddrID()),
                new StringFilter<>("Collector Name", c -> c.getPerson().getName()),
                new StringFilter<>("Email", c -> c.getPerson().getContact().getEmail()),
                new StringFilter<>("Mobile No.", c -> c.getPerson().getContact().getMobileNo()),
                new StringFilter<>("Office Phone No.", c -> c.getPerson().getContact().getOffPhNo()),
                new StringFilter<>("Customer Name", c -> c.getCustomer().getName()),
                new StringFilter<>("Collect Address", c -> c.getAddr().getAddress())
        );

        List<CollectAddress> collectAddresses;

        if (((CollectAddress) passObj.getObj()).getCustomer() != null) {
            collectAddresses = CollectAddressService.getCollectAddressByCustID(((CollectAddress) passObj.getObj()).getCustomer().getCustID());
        } else {
            collectAddresses = CollectAddressService.getAllCollectAddress();
        }

        ((MFXTableView<CollectAddress>) tblVw).setItems(FXCollections.observableList(collectAddresses));

        ((MFXTableView<CollectAddress>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<CollectAddress>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    CollectAddress cllctAddr = (((MFXTableView<CollectAddress>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(cllctAddr.getCollectAddrID());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            Stage stage = (Stage) btnCancel.getScene().getWindow();
                            BasicObjs passObj = new BasicObjs();
                            passObj.setObj(cllctAddr);
                            stage.setUserData(passObj);
                            stage.close();
                        }
                        rowSelected.clear();
                    }
                }
            }
        });
    }

    private void forCustomerInquiry() {
        // CI ID
        // Bill To Customer Name
        // Deliver To Address
        // Sales Person Name
        // Nett
        // Status
        // Created Date

        // CI ID
        MFXTableColumn<CustomerInquiry> ciIDCol = new MFXTableColumn<>("Customer Inquiry ID", true, Comparator.comparing(ci -> ci.getCode()));
        // Bill To Customer Name
        MFXTableColumn<CustomerInquiry> billToCol = new MFXTableColumn<>("Bill To Customer Name", true, Comparator.comparing(ci -> ci.getBillToCust().getName()));
        // Deliver To Address
        MFXTableColumn<CustomerInquiry> deliverToCol = new MFXTableColumn<>("Deliver To Address", true, Comparator.comparing(ci -> ci.getDeliverToCust().getAddr().getAddress()));
        // Sales Person Name
        MFXTableColumn<CustomerInquiry> salesPersonCol = new MFXTableColumn<>("Sales Person Name", true, Comparator.comparing(ci -> ci.getSalesPerson().getName()));
        // Nett
        MFXTableColumn<CustomerInquiry> nettCol = new MFXTableColumn<>("Nett", true, Comparator.comparing(ci -> ci.getNett()));
        // Status
        MFXTableColumn<CustomerInquiry> statusCol = new MFXTableColumn<>("Status", true, Comparator.comparing(ci -> ci.getStatus()));
        // Created Date
        MFXTableColumn<CustomerInquiry> createdDtCol = new MFXTableColumn<>("Reference Date", true, Comparator.comparing(ci -> ci.getCreatedDate()));

        // CI ID
        ciIDCol.setRowCellFactory(cllct -> new MFXTableRowCell<>(c -> c.getCode()));
        // Bill To Customer Name
        billToCol.setRowCellFactory(cllct -> new MFXTableRowCell<>(c -> c.getBillToCust().getName()));
        // Deliver To Address
        deliverToCol.setRowCellFactory(cllct -> new MFXTableRowCell<>(c -> c.getDeliverToCust().getAddr().getAddress()));
        // Sales Person Name
        salesPersonCol.setRowCellFactory(cllct -> new MFXTableRowCell<>(c -> c.getSalesPerson().getName()));
        // Nett
        nettCol.setRowCellFactory(cllct -> new MFXTableRowCell<>(c -> c.getNett()));
        // Status
        statusCol.setRowCellFactory(cllct -> new MFXTableRowCell<>(c -> c.getStatus()));
        // Created Date
        createdDtCol.setRowCellFactory(cllct -> new MFXTableRowCell<>(c -> c.getCreatedDate()));

        ((MFXTableView<CustomerInquiry>) tblVw).getTableColumns().addAll(
                ciIDCol,
                billToCol,
                deliverToCol,
                salesPersonCol,
                nettCol,
                statusCol,
                createdDtCol);

        ((MFXTableView<CustomerInquiry>) tblVw).getFilters().addAll(
                new StringFilter<>("Customer Inquiry ID", c -> c.getCode()),
                new StringFilter<>("Bill To Customer Name", c -> c.getBillToCust().getName()),
                new StringFilter<>("Deliver To Address", c -> c.getDeliverToCust().getAddr().getAddress()),
                new StringFilter<>("Sales Person Name", c -> c.getSalesPerson().getName()),
                new DoubleFilter<>("Nett", c -> c.getNett().doubleValue()),
                new StringFilter<>("Status", c -> c.getStatus()),
                new DateFilter<>("Reference Date", c -> c.getCreatedDate())
        );

        //5
        List<CustomerInquiry> customerInquiries = CustomerInquiryService.getAllCustomerInquiry();

        //6
        ((MFXTableView<CustomerInquiry>) tblVw).setItems(FXCollections.observableList(customerInquiries));
        //7

        ((MFXTableView<CustomerInquiry>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<CustomerInquiry>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    CustomerInquiry customerInquiry = (((MFXTableView<CustomerInquiry>) tblVw).getSelectionModel().getSelectedValues().get(0));

                    rowSelected.add(customerInquiry.getCode());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            Stage stage = (Stage) btnCancel.getScene().getWindow();
                            BasicObjs passObj = new BasicObjs();
                            passObj.setObj(customerInquiry);
                            stage.setUserData(passObj);
                            stage.close();
                        }
                        rowSelected.clear();
                    }
                }
            }
        });

    }

    private void forQuotation() {
        //1

        MFXTableColumn<Quotation> quotIDCol = new MFXTableColumn<>("Quotation ID", true, Comparator.comparing(quot -> quot.getCode()));
        MFXTableColumn<Quotation> ciCol = new MFXTableColumn<>("Cusrtomer Inquiry ID", true, Comparator.comparing(quot -> quot.getCI() == null ? "" : quot.getCI().getCode()));
        MFXTableColumn<Quotation> refTypeCol = new MFXTableColumn<>("Reference Type", true, Comparator.comparing(quot -> quot.getReferenceType()));
        MFXTableColumn<Quotation> refCol = new MFXTableColumn<>("Reference", true, Comparator.comparing(quot -> quot.getReference()));
        MFXTableColumn<Quotation> billToIDCol = new MFXTableColumn<>("Bill To Customer ID", true, Comparator.comparing(quot -> quot.getBillToCust().getCustID()));
        MFXTableColumn<Quotation> billToNmCol = new MFXTableColumn<>("Bill To Customer Name", true, Comparator.comparing(quot -> quot.getBillToCust().getName()));
        MFXTableColumn<Quotation> deliverToIDCol = new MFXTableColumn<>("Deliver To Customer ID", true, Comparator.comparing(quot -> quot.getDeliverToCust().getCollectAddrID()));
        MFXTableColumn<Quotation> deliverToNmCol = new MFXTableColumn<>("Deliver To Customer Name", true, Comparator.comparing(quot -> quot.getDeliverToCust().getPerson().getName()));
        MFXTableColumn<Quotation> salesPersonIDCol = new MFXTableColumn<>("Sales Person ID", true, Comparator.comparing(quot -> quot.getSalesPerson().getStaffID()));
        MFXTableColumn<Quotation> salesPersonNmCol = new MFXTableColumn<>("Sales Person Name", true, Comparator.comparing(quot -> quot.getSalesPerson().getName()));
        MFXTableColumn<Quotation> quotValDtCol = new MFXTableColumn<>("Quotation Validity Date", true, Comparator.comparing(quot -> quot.getQuotValidityDate()));
        MFXTableColumn<Quotation> reqDlvrDtCol = new MFXTableColumn<>("Required Delivery Date", true, Comparator.comparing(quot -> quot.getRequiredDeliveryDate()));
        MFXTableColumn<Quotation> grossDtCol = new MFXTableColumn<>("Gross", true, Comparator.comparing(quot -> quot.getGross()));
        MFXTableColumn<Quotation> discDtCol = new MFXTableColumn<>("Discount", true, Comparator.comparing(quot -> quot.getDiscount()));
        MFXTableColumn<Quotation> subTtlDtCol = new MFXTableColumn<>("Sub Total", true, Comparator.comparing(quot -> quot.getSubTotal()));
        MFXTableColumn<Quotation> nettDtCol = new MFXTableColumn<>("Nett", true, Comparator.comparing(quot -> quot.getNett()));

        //2
        quotIDCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getCode()));
        ciCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getCI() == null ? "" : quot.getCI().getCode()));
        refTypeCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getReferenceType()));
        refCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getReference()));
        billToIDCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getBillToCust().getCustID()));
        billToNmCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getBillToCust().getName()));
        deliverToIDCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getDeliverToCust().getCollectAddrID()));
        deliverToNmCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getDeliverToCust().getPerson().getName()));
        salesPersonIDCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getSalesPerson().getStaffID()));
        salesPersonNmCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getSalesPerson().getName()));
        quotValDtCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getQuotValidityDate()));
        reqDlvrDtCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getRequiredDeliveryDate()));
        grossDtCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getGross()));
        discDtCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getDiscount()));
        subTtlDtCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getSubTotal()));
        nettDtCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getNett()));

        //3
        ((MFXTableView<Quotation>) tblVw).getTableColumns().addAll(quotIDCol,
                ciCol,
                refTypeCol,
                refCol,
                billToIDCol,
                billToNmCol,
                deliverToIDCol,
                deliverToNmCol,
                salesPersonIDCol,
                salesPersonNmCol,
                quotValDtCol,
                reqDlvrDtCol,
                grossDtCol,
                discDtCol,
                subTtlDtCol,
                nettDtCol);

        //4
        ((MFXTableView<Quotation>) tblVw).getFilters().addAll(
                new StringFilter<>("Quotation ID", quot -> quot.getCode()),
                new StringFilter<>("Customer Inquiry ID", quot -> quot.getCI().getCode()),
                new StringFilter<>("Reference Type", quot -> quot.getReferenceType()),
                new StringFilter<>("Reference", quot -> quot.getReference()),
                new StringFilter<>("Bill To Customer ID", quot -> quot.getBillToCust().getCustID()),
                new StringFilter<>("Bill To Customer Name", quot -> quot.getBillToCust().getName()),
                new StringFilter<>("Deliver To Customer ID", quot -> quot.getDeliverToCust().getCollectAddrID()),
                new StringFilter<>("Deliver To Customer Name", quot -> quot.getDeliverToCust().getPerson().getName()),
                new StringFilter<>("Sales Person ID", quot -> quot.getSalesPerson().getStaffID()),
                new StringFilter<>("Sales Person Name", quot -> quot.getSalesPerson().getName()),
                new StringFilter<>("Quotation Validity Date", quot -> quot.getQuotValidityDate().toString()),
                new DateFilter<>("Required Delivery Date", quot -> quot.getRequiredDeliveryDate()),
                new DoubleFilter<>("Gross", quot -> quot.getGross().doubleValue()),
                new DoubleFilter<>("Discount", quot -> quot.getDiscount().doubleValue()),
                new DoubleFilter<>("Sub Total", quot -> quot.getSubTotal().doubleValue()),
                new DoubleFilter<>("Nett", quot -> quot.getNett().doubleValue())
        );

        //5
        List<Quotation> quots = QuotationService.getAllQuotation();

        //6
        ((MFXTableView<Quotation>) tblVw).setItems(FXCollections.observableList(quots));
        //7

        ((MFXTableView<Quotation>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<Quotation>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    Quotation quotation = (((MFXTableView<Quotation>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(quotation.getCode());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            Stage stage = (Stage) btnCancel.getScene().getWindow();
                            BasicObjs passObj = new BasicObjs();
                            passObj.setObj(quotation);
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
