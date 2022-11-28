/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Entity.CollectAddress;
import Entity.Customer;
import Entity.CustomerInquiry;
import Entity.DeliveryOrder;
import Entity.Invoice;
import Entity.PackingSlip;
import Entity.PaymentTerm;
import Entity.Place;
import Entity.Product;
import Entity.Quotation;
import Entity.ReturnDeliveryNote;
import Entity.SalesOrder;
import Entity.ShipmentTerm;
import Entity.Staff;
import Entity.TransferOrder;
import PassObjs.BasicObjs;
import Service.CollectAddressService;
import Service.CustomerInquiryService;
import Service.CustomerService;
import Service.DeliveryOrderService;
import Service.GeneralRulesService;
import Service.InvoiceService;
import Service.PackingSlipService;
import Service.PaymentTermService;
import Service.PlaceService;
import Service.ProductService;
import Service.QuotationService;
import Service.RDNService;
import Service.SalesOrderService;
import Service.ShipmentTermService;
import Service.StaffService;
import Service.TransferOrderService;
import Utils.DateFilter;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Tee Zhuo Xuan
 */
public class InnerEntitySelectCONTR implements Initializable {

    /**
     * Initializes the controller class.
     */
    //<editor-fold defaultstate="collapsed" desc="fields">
    @FXML
    private MFXButton btnCancel;
    @FXML
    private MFXTableView<?> tblVw;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="util declarations">
    private BasicObjs passObj;

    private static List<String> rowSelected = new ArrayList<>();
    //</editor-fold>

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                receiveData();
                setupTable();
                autoClose();
                tblVw.autosizeColumnsOnInitialization();
            }
        });

    }

    public void autoClose() {
        Duration delay1 = Duration.seconds(GeneralRulesService.getSessionTimeOut());
        PauseTransition transitionAlert = new PauseTransition(delay1);
        this.passObj.setLoginStaff(new Staff());
        transitionAlert.setOnFinished(evt -> switchScene("View/Login_UI.fxml", passObj, BasicObjs.back));
        transitionAlert.setCycleCount(1);

        this.btnCancel.getScene().addEventFilter(InputEvent.ANY, evt -> transitionAlert.playFromStart());

    }

    public void switchScene(String fxmlPath, BasicObjs passObj, String direction) {
        // Step 3
        Stage stage = (Stage) this.btnCancel.getScene().getWindow();
        //stage.close();
        try {
            // Step 4
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(fxmlPath)); // Example: "View/HomePage_UI.fxml"
            // Step 5
            stage.setUserData(sendData(passObj, direction));
            // Step 6
            Scene scene = new Scene(root);

            stage.setScene(scene);
            // Step 7
            stage.show();

        } catch (IOException e) {
            System.err.println(String.format("Error: %s", e.getMessage()));
        }
    }

    public BasicObjs sendData(BasicObjs passObj, String direction) {
        switch (direction) {
            case BasicObjs.forward:
                passObj.getFxmlPaths().addLast("View/InnerEntitySelectCONTR_UI.fxml");
                break;
        }
        passObj.setPassDirection(direction);
        passObj.setLoginStaff(this.passObj.getLoginStaff());
        return passObj;
    }

    private void setupTable() {

        Object entity = this.passObj.getObj();

        if (entity instanceof Place) {
            forPlace();
        } else if (entity instanceof Product) {
            forProduct();
        } else if (entity instanceof Customer) {
            forCustomer();
        } else if (entity instanceof Staff) {
            forStaff();
        } else if (entity instanceof CollectAddress) {
            forCollectAddress();
        } else if (entity instanceof CustomerInquiry) {
            forCustomerInquiry();
        } else if (entity instanceof Quotation) {
            forQuotation();
        } else if (entity instanceof SalesOrder) {
            forSalesOrder();
        } else if (entity instanceof TransferOrder) {
            forTransferOrder();
        } else if (entity instanceof ReturnDeliveryNote) {
            forReturnDeliveryNote();
        } else if (entity instanceof Invoice) {
            forInvoice();
        } else if (entity instanceof PaymentTerm) {
            forPaymentTerm();
        } else if (entity instanceof ShipmentTerm) {
            forShipmentTerm();
        }
    }

    private void forPlace() {
        // Place ID
        MFXTableColumn<Place> placeIDCol = new MFXTableColumn<>("Place ID", true, Comparator.comparing(place -> place.getPlaceID()));
        // Place Name
        MFXTableColumn<Place> placeNmCol = new MFXTableColumn<>("Place Name", true, Comparator.comparing(place -> place.getPlaceName()));
        // Location Name
        MFXTableColumn<Place> locationNmCol = new MFXTableColumn<>("Location Name", true, Comparator.comparing(place -> place.getPlaceAddr() == null ? "" : place.getPlaceAddr().getLocationName()));
        // Address
        MFXTableColumn<Place> addrCol = new MFXTableColumn<>("Address", true, Comparator.comparing(place -> place.getPlaceAddr() == null ? "" : place.getPlaceAddr().getAddress()));
        // City
        MFXTableColumn<Place> cityCol = new MFXTableColumn<>("City", true, Comparator.comparing(place -> place.getPlaceAddr() == null ? "" : place.getPlaceAddr().getCity()));

        // Place ID
        placeIDCol.setRowCellFactory(place -> new MFXTableRowCell<>(plc -> plc.getPlaceID()));
        // Place Name
        placeNmCol.setRowCellFactory(place -> new MFXTableRowCell<>(plc -> plc.getPlaceName()));
        // Location Name
        locationNmCol.setRowCellFactory(place -> new MFXTableRowCell<>(plc -> plc.getPlaceAddr() == null ? "" : plc.getPlaceAddr().getLocationName()));
        // Address
        addrCol.setRowCellFactory(place -> new MFXTableRowCell<>(plc -> plc.getPlaceAddr() == null ? "" : plc.getPlaceAddr().getAddress()));
        // City
        cityCol.setRowCellFactory(place -> new MFXTableRowCell<>(plc -> plc.getPlaceAddr() == null ? "" : plc.getPlaceAddr().getCity()));

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
                new StringFilter<>("Location Name", place -> place.getPlaceAddr() == null ? "" : place.getPlaceAddr().getLocationName()),
                new StringFilter<>("Address", place -> place.getPlaceAddr() == null ? "" : place.getPlaceAddr().getAddress()),
                new StringFilter<>("City", place -> place.getPlaceAddr() == null ? "" : place.getPlaceAddr().getCity())
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

    private void forProduct() {
        // Product ID
        // Product Name
        // Part Number
        // Sell price
        // MSRP
        // Product ID
        MFXTableColumn<Product> prodIDCol = new MFXTableColumn<>("Product ID", true, Comparator.comparing(product -> product.getProdID()));
        // Product Name
        MFXTableColumn<Product> prodNmCol = new MFXTableColumn<>("Product Name", true, Comparator.comparing(product -> product.getProdName()));
        // Part Number
        MFXTableColumn<Product> partNoCol = new MFXTableColumn<>("Part No.", true, Comparator.comparing(product -> product.getPartNo()));
        // Sell price
        MFXTableColumn<Product> sellPriceCol = new MFXTableColumn<>("Sell Price", true, Comparator.comparing(product -> product.getSellPrice()));
        // MSRP
        MFXTableColumn<Product> msrpCol = new MFXTableColumn<>("MSRP", true, Comparator.comparing(product -> product.getMSRP()));

        // Product ID
        prodIDCol.setRowCellFactory(prod -> new MFXTableRowCell<>(product -> product.getProdID()));
        // Product Name
        prodNmCol.setRowCellFactory(prod -> new MFXTableRowCell<>(product -> product.getProdName()));
        // Part Number
        partNoCol.setRowCellFactory(prod -> new MFXTableRowCell<>(product -> product.getPartNo()));
        // Sell price
        sellPriceCol.setRowCellFactory(prod -> new MFXTableRowCell<>(product -> product.getSellPrice()));
        // MSRP
        msrpCol.setRowCellFactory(prod -> new MFXTableRowCell<>(product -> product.getMSRP()));

        ((MFXTableView<Product>) tblVw).getTableColumns().addAll(
                prodIDCol,
                prodNmCol,
                partNoCol,
                sellPriceCol,
                msrpCol
        );

        ((MFXTableView<Product>) tblVw).getFilters().addAll(
                new StringFilter<>("Product ID", product -> product.getProdID()),
                new StringFilter<>("Product Name", product -> product.getProdName()),
                new StringFilter<>("Part No.", product -> product.getPartNo()),
                new DoubleFilter<>("Sell Price", product -> product.getSellPrice().doubleValue()),
                new DoubleFilter<>("MSRP", product -> product.getMSRP().doubleValue())
        );

        List<Product> products = ProductService.getAllProducts();

        ((MFXTableView<Product>) tblVw).setItems(FXCollections.observableList(products));

        ((MFXTableView<Product>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<Product>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    Product product = (((MFXTableView<Product>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(product.getProdID());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            Stage stage = (Stage) btnCancel.getScene().getWindow();
                            BasicObjs passObj = new BasicObjs();
                            passObj.setObj(product);
                            stage.setUserData(passObj);
                            stage.close();
                        }
                        rowSelected.clear();
                    }
                }
            }
        });
    }

    private void forCustomer() {

        // Customer ID
        // Name
        // Email
        // Mobile Number
        // Customer Type
        // Location Name
        // Customer ID
        MFXTableColumn<Customer> custIDCol = new MFXTableColumn<>("Customer ID", true, Comparator.comparing(cust -> cust.getCustID()));
        // Name
        MFXTableColumn<Customer> custNmCol = new MFXTableColumn<>("Customer Name", true, Comparator.comparing(cust -> cust.getName()));
        // Email
        MFXTableColumn<Customer> custEmailCol = new MFXTableColumn<>("Email", true, Comparator.comparing(cust -> cust.getContact().getEmail()));
        // Mobile Number
        MFXTableColumn<Customer> custMOBCol = new MFXTableColumn<>("Mobile No.", true, Comparator.comparing(cust -> cust.getContact().getMobileNo()));
        // Customer Type
        MFXTableColumn<Customer> custTypeCol = new MFXTableColumn<>("Type", true, Comparator.comparing(cust -> cust.getCustType()));
        // Location Name
        MFXTableColumn<Customer> custLocationNmCol = new MFXTableColumn<>("Location", true, Comparator.comparing(cust -> cust.getBillToAddr() == null ? "" : cust.getBillToAddr().getLocationName()));

        // Customer ID
        custIDCol.setRowCellFactory(customer -> new MFXTableRowCell<>(cust -> cust.getCustID()));
        // Name
        custNmCol.setRowCellFactory(customer -> new MFXTableRowCell<>(cust -> cust.getName()));
        // Email
        custEmailCol.setRowCellFactory(customer -> new MFXTableRowCell<>(cust -> cust.getContact().getEmail()));
        // Mobile Number
        custMOBCol.setRowCellFactory(customer -> new MFXTableRowCell<>(cust -> cust.getContact().getMobileNo()));
        // Customer Type
        custTypeCol.setRowCellFactory(customer -> new MFXTableRowCell<>(cust -> cust.getCustType()));
        // Location Name
        custLocationNmCol.setRowCellFactory(customer -> new MFXTableRowCell<>(cust -> cust.getBillToAddr() == null ? "" : cust.getBillToAddr().getLocationName()));

        ((MFXTableView<Customer>) tblVw).getTableColumns().addAll(
                custIDCol,
                custNmCol,
                custEmailCol,
                custMOBCol,
                custTypeCol,
                custLocationNmCol
        );

        ((MFXTableView<Customer>) tblVw).getFilters().addAll(
                new StringFilter<>("Customer ID", cust -> cust.getCustID()),
                new StringFilter<>("Customer Name", cust -> cust.getName()),
                new StringFilter<>("Email", cust -> cust.getContact().getEmail()),
                new StringFilter<>("Mobile No.", cust -> cust.getContact().getMobileNo()),
                new StringFilter<>("Type", cust -> cust.getCustType()),
                new StringFilter<>("Location", cust -> cust.getBillToAddr() == null ? "" : cust.getBillToAddr().getLocationName())
        );

        List<Customer> custs = CustomerService.getAllCustomers();

        ((MFXTableView<Customer>) tblVw).setItems(FXCollections.observableList(custs));

        ((MFXTableView<Quotation>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<Quotation>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    Customer customer = (((MFXTableView<Customer>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(customer.getCustID());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            Stage stage = (Stage) btnCancel.getScene().getWindow();
                            BasicObjs passObj = new BasicObjs();
                            passObj.setObj(customer);
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
        // Location

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
        MFXTableColumn<CollectAddress> cllctAddrCustNmCol = new MFXTableColumn<>("Customer Name", true, Comparator.comparing(cllct -> cllct.getCustomer() == null ? "" : cllct.getCustomer().getName()));
        // Location
        MFXTableColumn<CollectAddress> cllctAddrAddrCol = new MFXTableColumn<>("Location", true, Comparator.comparing(cllct -> cllct.getAddr() == null ? "" : cllct.getAddr().getLocationName()));

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
        cllctAddrCustNmCol.setRowCellFactory(cllct -> new MFXTableRowCell<>(c -> c.getCustomer() == null ? "" : c.getCustomer().getName()));
        // Location
        cllctAddrAddrCol.setRowCellFactory(cllct -> new MFXTableRowCell<>(c -> c.getAddr() == null ? "" : c.getAddr().getLocationName()));

        ((MFXTableView<CollectAddress>) tblVw).getTableColumns().addAll(
                cllctAddrIDCol,
                cllctAddrNameCol,
                cllctAddrEmailCol,
                cllctAddrMobNoCol,
                cllctAddrOffNoCol,
                cllctAddrCustNmCol,
                cllctAddrAddrCol);

        ((MFXTableView<CollectAddress>) tblVw).getFilters().addAll(
                new StringFilter<>("Collect Address ID", cllct -> cllct.getCollectAddrID()),
                new StringFilter<>("Collector Name", cllct -> cllct.getPerson().getName()),
                new StringFilter<>("Email", cllct -> cllct.getPerson().getContact().getEmail()),
                new StringFilter<>("Mobile No.", cllct -> cllct.getPerson().getContact().getMobileNo()),
                new StringFilter<>("Office Phone No.", cllct -> cllct.getPerson().getContact().getOffPhNo()),
                new StringFilter<>("Customer Name", cllct -> cllct.getCustomer() == null ? "" : cllct.getCustomer().getName()),
                new StringFilter<>("Location", cllct -> cllct.getAddr() == null ? "" : cllct.getAddr().getLocationName())
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

        MFXTableColumn<CustomerInquiry> ciIDCol = new MFXTableColumn<>("CustomerInquiry ID", true, Comparator.comparing(customerInquiry -> customerInquiry.getCode()));
        MFXTableColumn<CustomerInquiry> refCol = new MFXTableColumn<>("Reference", true, Comparator.comparing(customerInquiry -> customerInquiry.getReference()));
        MFXTableColumn<CustomerInquiry> billToIDCol = new MFXTableColumn<>("Bill To customer ID", true, Comparator.comparing(customerInquiry -> customerInquiry.getBillToCust() == null ? "" : customerInquiry.getBillToCust().getCustID()));
        MFXTableColumn<CustomerInquiry> billToNmCol = new MFXTableColumn<>("Bill To customer Name", true, Comparator.comparing(customerInquiry -> customerInquiry.getBillToCust() == null ? "" : customerInquiry.getBillToCust().getName()));
        MFXTableColumn<CustomerInquiry> deliverToIDCol = new MFXTableColumn<>("Deliver To ID", true, Comparator.comparing(customerInquiry -> customerInquiry.getDeliverToCust() == null ? "" : customerInquiry.getDeliverToCust().getCollectAddrID()));
        MFXTableColumn<CustomerInquiry> deliverToNmCol = new MFXTableColumn<>("Deliver To Collector Name", true, Comparator.comparing(customerInquiry -> customerInquiry.getDeliverToCust() == null ? "" : customerInquiry.getDeliverToCust().getPerson().getName()));
        MFXTableColumn<CustomerInquiry> salesPersonIDCol = new MFXTableColumn<>("Sales Person ID", true, Comparator.comparing(customerInquiry -> customerInquiry.getSalesPerson() == null ? "" : customerInquiry.getSalesPerson().getStaffID()));
        MFXTableColumn<CustomerInquiry> salesPersonNmCol = new MFXTableColumn<>("Sales Person Name", true, Comparator.comparing(customerInquiry -> customerInquiry.getSalesPerson() == null ? "" : customerInquiry.getSalesPerson().getName()));
        MFXTableColumn<CustomerInquiry> nettDtCol = new MFXTableColumn<>("Nett", true, Comparator.comparing(customerInquiry -> customerInquiry.getNett()));
        MFXTableColumn<CustomerInquiry> statusCol = new MFXTableColumn<>("Status", true, Comparator.comparing(customerInquiry -> customerInquiry.getStatus()));

        ciIDCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getCode()));
        refCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getReference()));
        billToIDCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getBillToCust() == null ? "" : c.getBillToCust().getCustID()));
        billToNmCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getBillToCust() == null ? "" : c.getBillToCust().getName()));
        deliverToIDCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getDeliverToCust() == null ? "" : c.getDeliverToCust().getCollectAddrID()));
        deliverToNmCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getDeliverToCust() == null ? "" : c.getDeliverToCust().getPerson().getName()));
        salesPersonIDCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getSalesPerson() == null ? "" : c.getSalesPerson().getStaffID()));
        salesPersonNmCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getSalesPerson() == null ? "" : c.getSalesPerson().getName()));
        nettDtCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getNett()));
        statusCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getStatus()));

        ((MFXTableView<CustomerInquiry>) tblVw).getTableColumns().addAll(
                ciIDCol,
                refCol,
                billToIDCol,
                billToNmCol,
                deliverToIDCol,
                deliverToNmCol,
                salesPersonIDCol,
                salesPersonNmCol,
                nettDtCol,
                statusCol);

        ((MFXTableView<CustomerInquiry>) tblVw).getFilters().addAll(
                new StringFilter<>("Customer Inquiry ID", customerInquiry -> customerInquiry.getCode()),
                new StringFilter<>("Reference", customerInquiry -> customerInquiry.getReference()),
                new StringFilter<>("Bill To Customer ID", customerInquiry -> customerInquiry.getBillToCust() == null ? "" : customerInquiry.getBillToCust().getCustID()),
                new StringFilter<>("Bill To Customer Name", customerInquiry -> customerInquiry.getBillToCust() == null ? "" : customerInquiry.getBillToCust().getName()),
                new StringFilter<>("Deliver To ID", customerInquiry -> customerInquiry.getDeliverToCust() == null ? "" : customerInquiry.getDeliverToCust().getCollectAddrID()),
                new StringFilter<>("Deliver To Collector Name", customerInquiry -> customerInquiry.getDeliverToCust() == null ? "" : customerInquiry.getDeliverToCust().getPerson().getName()),
                new StringFilter<>("Sales Person ID", customerInquiry -> customerInquiry.getSalesPerson() == null ? "" : customerInquiry.getSalesPerson().getStaffID()),
                new StringFilter<>("Sales Person Name", customerInquiry -> customerInquiry.getSalesPerson() == null ? "" : customerInquiry.getSalesPerson().getName()),
                new DoubleFilter<>("Nett", customerInquiry -> customerInquiry.getNett().doubleValue()),
                new StringFilter<>("Status", customerInquiry -> customerInquiry.getStatus())
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
        MFXTableColumn<Quotation> refCol = new MFXTableColumn<>("Reference", true, Comparator.comparing(quot -> quot.getReference()));
        MFXTableColumn<Quotation> billToIDCol = new MFXTableColumn<>("Bill To Customer ID", true, Comparator.comparing(quot -> quot.getBillToCust() == null ? "" : quot.getBillToCust().getCustID()));
        MFXTableColumn<Quotation> billToNmCol = new MFXTableColumn<>("Bill To Customer Name", true, Comparator.comparing(quot -> quot.getBillToCust() == null ? "" : quot.getBillToCust().getName()));
        MFXTableColumn<Quotation> deliverToIDCol = new MFXTableColumn<>("Deliver To ID", true, Comparator.comparing(quot -> quot.getDeliverToCust() == null ? "" : quot.getDeliverToCust().getCollectAddrID()));
        MFXTableColumn<Quotation> deliverToNmCol = new MFXTableColumn<>("Deliver To Collector Name", true, Comparator.comparing(quot -> quot.getDeliverToCust() == null ? "" : quot.getDeliverToCust().getPerson().getName()));
        MFXTableColumn<Quotation> salesPersonIDCol = new MFXTableColumn<>("Sales Person ID", true, Comparator.comparing(quot -> quot.getSalesPerson() == null ? "" : quot.getSalesPerson().getStaffID()));
        MFXTableColumn<Quotation> salesPersonNmCol = new MFXTableColumn<>("Sales Person Name", true, Comparator.comparing(quot -> quot.getSalesPerson() == null ? "" : quot.getSalesPerson().getName()));
        MFXTableColumn<Quotation> quotValDtCol = new MFXTableColumn<>("Quotation Validity Date", true, Comparator.comparing(quot -> quot.getQuotValidityDate()));
        MFXTableColumn<Quotation> nettDtCol = new MFXTableColumn<>("Nett", true, Comparator.comparing(quot -> quot.getNett()));
        MFXTableColumn<Quotation> statusCol = new MFXTableColumn<>("Status", true, Comparator.comparing(quot -> quot.getStatus()));

        //2
        quotIDCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getCode()));
        refCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getReference()));
        billToIDCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getBillToCust() == null ? "" : quot.getBillToCust().getCustID()));
        billToNmCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getBillToCust() == null ? "" : quot.getBillToCust().getName()));
        deliverToIDCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getDeliverToCust() == null ? "" : quot.getDeliverToCust().getCollectAddrID()));
        deliverToNmCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getDeliverToCust() == null ? "" : quot.getDeliverToCust().getPerson().getName()));
        salesPersonIDCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getSalesPerson() == null ? "" : quot.getSalesPerson().getStaffID()));
        salesPersonNmCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getSalesPerson() == null ? "" : quot.getSalesPerson().getName()));
        quotValDtCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getQuotValidityDate()));
        nettDtCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getNett()));
        statusCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getStatus()));

        //3
        ((MFXTableView<Quotation>) tblVw).getTableColumns().addAll(quotIDCol,
                refCol,
                billToIDCol,
                billToNmCol,
                deliverToIDCol,
                deliverToNmCol,
                salesPersonIDCol,
                salesPersonNmCol,
                quotValDtCol,
                nettDtCol,
                statusCol);

        //4
        ((MFXTableView<Quotation>) tblVw).getFilters().addAll(
                new StringFilter<>("Quotation ID", quot -> quot.getCode()),
                new StringFilter<>("Reference", quot -> quot.getReference()),
                new StringFilter<>("Bill To Customer ID", quot -> quot.getBillToCust() == null ? "" : quot.getBillToCust().getCustID()),
                new StringFilter<>("Bill To Customer Name", quot -> quot.getBillToCust() == null ? "" : quot.getBillToCust().getName()),
                new StringFilter<>("Deliver To ID", quot -> quot.getDeliverToCust() == null ? "" : quot.getDeliverToCust().getCollectAddrID()),
                new StringFilter<>("Deliver To Customer Name", quot -> quot.getDeliverToCust() == null ? "" : quot.getDeliverToCust().getPerson().getName()),
                new StringFilter<>("Sales Person ID", quot -> quot.getSalesPerson() == null ? "" : quot.getSalesPerson().getStaffID()),
                new StringFilter<>("Sales Person Name", quot -> quot.getSalesPerson() == null ? "" : quot.getSalesPerson().getName()),
                new StringFilter<>("Quotation Validity Date", quot -> quot.getQuotValidityDate().toString()),
                new DoubleFilter<>("Nett", quot -> quot.getNett().doubleValue()),
                new StringFilter<>("Status", quot -> quot.getStatus())
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

    }

    private void forSalesOrder() {
        //1

        MFXTableColumn<SalesOrder> soIDCol = new MFXTableColumn<>("Sales Order ID", true, Comparator.comparing(so -> so.getCode()));
        MFXTableColumn<SalesOrder> refCol = new MFXTableColumn<>("Reference", true, Comparator.comparing(so -> so.getReference()));
        MFXTableColumn<SalesOrder> billToIDCol = new MFXTableColumn<>("Bill To Customer ID", true, Comparator.comparing(so -> so.getBillToCust() == null ? "" : so.getBillToCust().getCustID()));
        MFXTableColumn<SalesOrder> billToNmCol = new MFXTableColumn<>("Bill To Customer Name", true, Comparator.comparing(so -> so.getBillToCust() == null ? "" : so.getBillToCust().getName()));
        MFXTableColumn<SalesOrder> deliverToIDCol = new MFXTableColumn<>("Deliver To ID", true, Comparator.comparing(so -> so.getDeliverToCust() == null ? "" : so.getDeliverToCust().getCollectAddrID()));
        MFXTableColumn<SalesOrder> deliverToNmCol = new MFXTableColumn<>("Deliver To Collector Name", true, Comparator.comparing(so -> so.getDeliverToCust() == null ? "" : so.getDeliverToCust().getPerson().getName()));
        MFXTableColumn<SalesOrder> salesPersonIDCol = new MFXTableColumn<>("Sales Person ID", true, Comparator.comparing(so -> so.getSalesPerson() == null ? "" : so.getSalesPerson().getStaffID()));
        MFXTableColumn<SalesOrder> salesPersonNmCol = new MFXTableColumn<>("Sales Person Name", true, Comparator.comparing(so -> so.getSalesPerson() == null ? "" : so.getSalesPerson().getName()));
        MFXTableColumn<SalesOrder> nettDtCol = new MFXTableColumn<>("Nett", true, Comparator.comparing(so -> so.getNett()));
        MFXTableColumn<SalesOrder> statusCol = new MFXTableColumn<>("Status", true, Comparator.comparing(so -> so.getStatus()));

        //2
        soIDCol.setRowCellFactory(salesOrder -> new MFXTableRowCell<>(so -> so.getCode()));
        refCol.setRowCellFactory(salesOrder -> new MFXTableRowCell<>(so -> so.getReference()));
        billToIDCol.setRowCellFactory(salesOrder -> new MFXTableRowCell<>(so -> so.getBillToCust() == null ? "" : so.getBillToCust().getCustID()));
        billToNmCol.setRowCellFactory(salesOrder -> new MFXTableRowCell<>(so -> so.getBillToCust() == null ? "" : so.getBillToCust().getName()));
        deliverToIDCol.setRowCellFactory(salesOrder -> new MFXTableRowCell<>(so -> so.getDeliverToCust() == null ? "" : so.getDeliverToCust().getCollectAddrID()));
        deliverToNmCol.setRowCellFactory(salesOrder -> new MFXTableRowCell<>(so -> so.getDeliverToCust() == null ? "" : so.getDeliverToCust().getPerson().getName()));
        salesPersonIDCol.setRowCellFactory(salesOrder -> new MFXTableRowCell<>(so -> so.getSalesPerson() == null ? "" : so.getSalesPerson().getStaffID()));
        salesPersonNmCol.setRowCellFactory(salesOrder -> new MFXTableRowCell<>(so -> so.getSalesPerson() == null ? "" : so.getSalesPerson().getName()));
        nettDtCol.setRowCellFactory(salesOrder -> new MFXTableRowCell<>(so -> so.getNett()));
        statusCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(so -> so.getStatus()));

        //3
        ((MFXTableView<SalesOrder>) tblVw).getTableColumns().addAll(
                soIDCol,
                refCol,
                billToIDCol,
                billToNmCol,
                deliverToIDCol,
                deliverToNmCol,
                salesPersonIDCol,
                salesPersonNmCol,
                nettDtCol,
                statusCol
        );

        //4
        ((MFXTableView<SalesOrder>) tblVw).getFilters().addAll(
                new StringFilter<>("Sales Order ID", so -> so.getCode()),
                new StringFilter<>("Reference", so -> so.getReference()),
                new StringFilter<>("Bill To Customer ID", so -> so.getBillToCust() == null ? "" : so.getBillToCust().getCustID()),
                new StringFilter<>("Bill To Customer Name", so -> so.getBillToCust() == null ? "" : so.getBillToCust().getName()),
                new StringFilter<>("Deliver To ID", so -> so.getDeliverToCust() == null ? "" : so.getDeliverToCust().getCollectAddrID()),
                new StringFilter<>("Deliver To Collector Name", so -> so.getDeliverToCust() == null ? "" : so.getDeliverToCust().getPerson().getName()),
                new StringFilter<>("Sales Person ID", so -> so.getSalesPerson() == null ? "" : so.getSalesPerson().getStaffID()),
                new StringFilter<>("Sales Person Name", so -> so.getSalesPerson() == null ? "" : so.getSalesPerson().getName()),
                new DoubleFilter<>("Nett", so -> so.getNett().doubleValue()),
                new StringFilter<>("Status", so -> so.getStatus())
        );

        //5
        List<SalesOrder> quots = SalesOrderService.getAllSalesOrders();

        //6
        ((MFXTableView<SalesOrder>) tblVw).setItems(FXCollections.observableList(quots));
        //7

        ((MFXTableView<SalesOrder>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<SalesOrder>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    SalesOrder salesOrder = (((MFXTableView<SalesOrder>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(salesOrder.getCode());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            Stage stage = (Stage) btnCancel.getScene().getWindow();
                            BasicObjs passObj = new BasicObjs();
                            passObj.setObj(salesOrder);
                            stage.setUserData(passObj);
                            stage.close();
                        }
                        rowSelected.clear();
                    }
                }
            }
        });
    }

    private void forTransferOrder() {
        // TO ID
        // PIC ID
        // PIC Name
        // Status

        // TO ID
        MFXTableColumn<TransferOrder> toIDCol = new MFXTableColumn<>("Transfer Order ID", true, Comparator.comparing(to -> to.getCode()));
        // PIC ID
        MFXTableColumn<TransferOrder> picIDCol = new MFXTableColumn<>("PIC ID", true, Comparator.comparing(to -> to.getPIC() == null ? "" : to.getPIC().getStaffID()));
        // PIC Name
        MFXTableColumn<TransferOrder> picNmCol = new MFXTableColumn<>("PIC Name", true, Comparator.comparing(to -> to.getPIC() == null ? "" : to.getPIC().getName()));
        // Doc. Ref. ID
        MFXTableColumn<TransferOrder> docRefIDCol = new MFXTableColumn<>("Doc. Ref. ID", true, Comparator.comparing(to -> to.getReqTypeRef() == null ? "" : to.getReqTypeRef() instanceof SalesOrder ? ((SalesOrder) to.getReqTypeRef()).getCode() : ((ReturnDeliveryNote) to.getReqTypeRef()).getCode()));
        // Status
        MFXTableColumn<TransferOrder> statusCol = new MFXTableColumn<>("Status", true, Comparator.comparing(to -> to.getStatus()));

        // TO ID
        toIDCol.setRowCellFactory(transferOrder -> new MFXTableRowCell<>(to -> to.getCode()));
        // PIC ID
        picIDCol.setRowCellFactory(transferOrder -> new MFXTableRowCell<>(to -> to.getPIC() == null ? "" : to.getPIC().getStaffID()));
        // PIC Name
        picNmCol.setRowCellFactory(transferOrder -> new MFXTableRowCell<>(to -> to.getPIC() == null ? "" : to.getPIC().getName()));
        // Doc. Ref. ID
        docRefIDCol.setRowCellFactory(transferOrder -> new MFXTableRowCell<>(to -> to.getReqTypeRef() == null ? "" : to.getReqTypeRef() instanceof SalesOrder ? ((SalesOrder) to.getReqTypeRef()).getCode() : ((ReturnDeliveryNote) to.getReqTypeRef()).getCode()));
        // Status
        statusCol.setRowCellFactory(transferOrder -> new MFXTableRowCell<>(to -> to.getStatus()));

        ((MFXTableView<TransferOrder>) tblVw).getTableColumns().addAll(
                toIDCol,
                picIDCol,
                picNmCol,
                statusCol
        );

        ((MFXTableView<TransferOrder>) tblVw).getFilters().addAll(
                new StringFilter<>("Transfer Order ID", to -> to.getCode()),
                new StringFilter<>("PIC ID", to -> to.getPIC() == null ? "" : to.getPIC().getStaffID()),
                new StringFilter<>("PIC Name", to -> to.getPIC() == null ? "" : to.getPIC().getName()),
                new StringFilter<>("Doc. Ref. ID", to -> to.getReqTypeRef() == null ? "" : to.getReqTypeRef() instanceof SalesOrder ? ((SalesOrder) to.getReqTypeRef()).getCode() : ((ReturnDeliveryNote) to.getReqTypeRef()).getCode()),
                new StringFilter<>("Status", to -> to.getStatus())
        );

        List<TransferOrder> transferOrders = TransferOrderService.getAllTO();

        //6
        ((MFXTableView<TransferOrder>) tblVw).setItems(FXCollections.observableList(transferOrders));

        ((MFXTableView<TransferOrder>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<TransferOrder>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    TransferOrder transferOrder = (((MFXTableView<TransferOrder>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(transferOrder.getCode());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            Stage stage = (Stage) btnCancel.getScene().getWindow();
                            BasicObjs passObj = new BasicObjs();
                            passObj.setObj(transferOrder);
                            stage.setUserData(passObj);
                            stage.close();
                        }
                        rowSelected.clear();
                    }
                }
            }
        });
    }

    private void forPackingSlip() {
        // PS ID
        // TO ID
        // Status
        // Created Date

        // PS ID
        MFXTableColumn<PackingSlip> psCol = new MFXTableColumn<>("Packing Slip ID", true, Comparator.comparing(ps -> ps.getCode()));
        // TO ID
        MFXTableColumn<PackingSlip> toCol = new MFXTableColumn<>("Transfer Order ID", true, Comparator.comparing(ps -> ps.getTO() == null ? "" : ps.getTO().getCode()));
        // Status
        MFXTableColumn<PackingSlip> statusCol = new MFXTableColumn<>("Status", true, Comparator.comparing(ps -> ps.getStatus()));
        // Created Date
        MFXTableColumn<PackingSlip> createdDtCol = new MFXTableColumn<>("Created Date", true, Comparator.comparing(ps -> ps.getCreatedDate()));

        // PS ID
        psCol.setRowCellFactory(dOrder -> new MFXTableRowCell<>(ps -> ps.getCode()));
        // TO ID
        toCol.setRowCellFactory(dOrder -> new MFXTableRowCell<>(ps -> ps.getTO() == null ? "" : ps.getTO().getCode()));
        // Status
        statusCol.setRowCellFactory(dOrder -> new MFXTableRowCell<>(ps -> ps.getStatus()));
        // Created Date
        createdDtCol.setRowCellFactory(dOrder -> new MFXTableRowCell<>(ps -> ps.getCreatedDate()));

        ((MFXTableView<PackingSlip>) tblVw).getTableColumns().addAll(
                psCol,
                toCol,
                statusCol,
                createdDtCol
        );

        ((MFXTableView<PackingSlip>) tblVw).getFilters().addAll(
                new StringFilter<>("Packing Slip ID", ps -> ps.getCode()),
                new StringFilter<>("Transfer Order ID", ps -> ps.getTO() == null ? "" : ps.getTO().getCode()),
                new StringFilter<>("Status", ps -> ps.getStatus()),
                new DateFilter<>("Created Date", ps -> ps.getCreatedDate())
        );

        List<PackingSlip> packingSlips = PackingSlipService.getAllPackingSlips();

        ((MFXTableView<PackingSlip>) tblVw).setItems(FXCollections.observableList(packingSlips));

        ((MFXTableView<PackingSlip>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<PackingSlip>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    PackingSlip packingSlip = (((MFXTableView<PackingSlip>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(packingSlip.getCode());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            Stage stage = (Stage) btnCancel.getScene().getWindow();
                            BasicObjs passObj = new BasicObjs();
                            passObj.setObj(packingSlip);
                            stage.setUserData(passObj);
                            stage.close();
                        }
                        rowSelected.clear();
                    }
                }
            }
        });
    }

    private void forReturnDeliveryNote() {
        // RDN ID
        // So Ref.
        // Collect Back From Location Name
        // Status

        // RDN ID
        MFXTableColumn<ReturnDeliveryNote> rdnIDCol = new MFXTableColumn<>("RDN ID", true, Comparator.comparing(rdn -> rdn.getCode()));
        // So Ref.
        MFXTableColumn<ReturnDeliveryNote> soRefCol = new MFXTableColumn<>("So Ref.", true, Comparator.comparing(rdn -> rdn.getSO() == null ? "" : rdn.getSO().getCode()));
        // Collect Back From Location Name
        MFXTableColumn<ReturnDeliveryNote> cllctBckFrLocationCol = new MFXTableColumn<>("Collect Back From Location", true, Comparator.comparing(rdn -> rdn.getCollBckFr() == null ? "" : rdn.getCollBckFr().getAddr().getLocationName()));
        // Status
        MFXTableColumn<ReturnDeliveryNote> statusCol = new MFXTableColumn<>("Status", true, Comparator.comparing(rdn -> rdn.getStatus()));

        // RDN ID
        rdnIDCol.setRowCellFactory(returnDeliveryNote -> new MFXTableRowCell<>(rdn -> rdn.getCode()));
        // So Ref.
        soRefCol.setRowCellFactory(returnDeliveryNote -> new MFXTableRowCell<>(rdn -> rdn.getSO() == null ? "" : rdn.getSO().getCode()));
        // Collect Back From Location Name
        cllctBckFrLocationCol.setRowCellFactory(returnDeliveryNote -> new MFXTableRowCell<>(rdn -> rdn.getCollBckFr() == null ? "" : rdn.getCollBckFr().getAddr().getLocationName()));
        // Status
        statusCol.setRowCellFactory(returnDeliveryNote -> new MFXTableRowCell<>(rdn -> rdn.getStatus()));

        ((MFXTableView<ReturnDeliveryNote>) tblVw).getTableColumns().addAll(
                rdnIDCol,
                soRefCol,
                cllctBckFrLocationCol,
                statusCol
        );

        ((MFXTableView<ReturnDeliveryNote>) tblVw).getFilters().addAll(
                new StringFilter<>("RDN ID", rdn -> rdn.getCode()),
                new StringFilter<>("So Ref.", rdn -> rdn.getSO() == null ? "" : rdn.getSO().getCode()),
                new StringFilter<>("Collect Back From Location", rdn -> rdn.getCollBckFr() == null ? "" : rdn.getCollBckFr().getAddr().getLocationName()),
                new StringFilter<>("Status", rdn -> rdn.getStatus())
        );

        List<ReturnDeliveryNote> returnDeliveryNotes = RDNService.getAllRDN();

        //6
        ((MFXTableView<ReturnDeliveryNote>) tblVw).setItems(FXCollections.observableList(returnDeliveryNotes));

        ((MFXTableView<ReturnDeliveryNote>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<ReturnDeliveryNote>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    ReturnDeliveryNote returnDeliveryNote = (((MFXTableView<ReturnDeliveryNote>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(returnDeliveryNote.getCode());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            Stage stage = (Stage) btnCancel.getScene().getWindow();
                            BasicObjs passObj = new BasicObjs();
                            passObj.setObj(returnDeliveryNote);
                            stage.setUserData(passObj);
                            stage.close();
                        }
                        rowSelected.clear();
                    }
                }
            }
        });
    }

    private void forDeliveryOrder() {
        //DO ID
        //SO ID
        //Delivery By
        //Delivery Date
        //Status

        //DO ID
        MFXTableColumn<DeliveryOrder> doIDCol = new MFXTableColumn<>("Delivery Order ID", true, Comparator.comparing(deliveryOrder -> deliveryOrder.getCode()));
        //SO ID
        MFXTableColumn<DeliveryOrder> soIDCol = new MFXTableColumn<>("Sales Order ID", true, Comparator.comparing(deliveryOrder -> deliveryOrder.getSo() == null ? "" : deliveryOrder.getSo().getCode()));
        //Delivery By
        MFXTableColumn<DeliveryOrder> deliveryByCol = new MFXTableColumn<>("Deliver By", true, Comparator.comparing(deliveryOrder -> deliveryOrder.getDeliveryBy() == null ? "" : deliveryOrder.getDeliveryBy().getStaffID()));
        //Delivery Date
        MFXTableColumn<DeliveryOrder> deliveryDtCol = new MFXTableColumn<>("Delivery Date", true, Comparator.comparing(deliveryOrder -> deliveryOrder.getDeliveryDate()));
        //Status
        MFXTableColumn<DeliveryOrder> statusCol = new MFXTableColumn<>("Status", true, Comparator.comparing(delveryOrder -> delveryOrder.getStatus()));

        //DO ID
        doIDCol.setRowCellFactory(dOrder -> new MFXTableRowCell<>(deliveryOrder -> deliveryOrder.getCode()));
        //SO ID
        soIDCol.setRowCellFactory(dOrder -> new MFXTableRowCell<>(deliveryOrder -> deliveryOrder.getSo() == null ? "" : deliveryOrder.getSo().getCode()));
        //Delivery By
        deliveryByCol.setRowCellFactory(dOrder -> new MFXTableRowCell<>(deliveryOrder -> deliveryOrder.getDeliveryBy() == null ? "" : deliveryOrder.getDeliveryBy().getStaffID()));
        //Delivery Date
        deliveryDtCol.setRowCellFactory(dOrder -> new MFXTableRowCell<>(deliveryOrder -> deliveryOrder.getDeliveryDate()));
        //Status
        statusCol.setRowCellFactory(dOrder -> new MFXTableRowCell<>(deliveryOrder -> deliveryOrder.getStatus()));

        ((MFXTableView<DeliveryOrder>) tblVw).getTableColumns().addAll(
                doIDCol,
                soIDCol,
                deliveryByCol,
                deliveryDtCol,
                statusCol
        );

        ((MFXTableView<DeliveryOrder>) tblVw).getFilters().addAll(
                new StringFilter<>("Delivery Order ID", deliveryOrder -> deliveryOrder.getCode()),
                new StringFilter<>("Sales Order ID", deliveryOrder -> deliveryOrder.getSo() == null ? "" : deliveryOrder.getSo().getCode()),
                new StringFilter<>("Deliver By", deliveryOrder -> deliveryOrder.getDeliveryBy() == null ? "" : deliveryOrder.getDeliveryBy().getStaffID()),
                new DateFilter<>("Delivery Date", deliveryOrder -> deliveryOrder.getDeliveryDate()),
                new StringFilter<>("Status", deliveryOrder -> deliveryOrder.getStatus())
        );

        List<DeliveryOrder> deliveryOrders = DeliveryOrderService.getAllDOs();

        ((MFXTableView<DeliveryOrder>) tblVw).setItems(FXCollections.observableList(deliveryOrders));

        ((MFXTableView<DeliveryOrder>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<DeliveryOrder>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    DeliveryOrder deliveryOrder = (((MFXTableView<DeliveryOrder>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(deliveryOrder.getCode());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            Stage stage = (Stage) btnCancel.getScene().getWindow();
                            BasicObjs passObj = new BasicObjs();
                            passObj.setObj(deliveryOrder);
                            stage.setUserData(passObj);
                            stage.close();
                        }
                        rowSelected.clear();
                    }
                }
            }
        });
    }

    private void forInvoice() {
        // INV_ID
        // SO ID
        // Total Payable
        // Status
        //Created Date

        // INV_ID
        MFXTableColumn<Invoice> invIDCol = new MFXTableColumn<>("Invoice ID", true, Comparator.comparing(invoice -> invoice.getCode()));
        // SO ID
        MFXTableColumn<Invoice> soIDCol = new MFXTableColumn<>("Sales Order ID", true, Comparator.comparing(invoice -> invoice.getSO() == null ? "" : invoice.getSO().getCode()));
        // Total Payable
        MFXTableColumn<Invoice> ttlPybleCol = new MFXTableColumn<>("Total Payable", true, Comparator.comparing(invoice -> invoice.getTtlPayable()));
        // Status
        MFXTableColumn<Invoice> statusCol = new MFXTableColumn<>("Status", true, Comparator.comparing(invoice -> invoice.getStatus()));
        //Created Date
        MFXTableColumn<Invoice> createdDtCol = new MFXTableColumn<>("Created Date", true, Comparator.comparing(invoice -> invoice.getCreatedDate()));

        // INV_ID
        invIDCol.setRowCellFactory(invoice -> new MFXTableRowCell<>(inv -> inv.getCode()));
        // SO ID
        soIDCol.setRowCellFactory(invoice -> new MFXTableRowCell<>(inv -> inv.getSO() == null ? "" : inv.getSO().getCode()));
        // Total Payable
        ttlPybleCol.setRowCellFactory(invoice -> new MFXTableRowCell<>(inv -> inv.getTtlPayable()));
        // Status
        statusCol.setRowCellFactory(invoice -> new MFXTableRowCell<>(inv -> inv.getStatus()));
        //Created Date
        createdDtCol.setRowCellFactory(invoice -> new MFXTableRowCell<>(inv -> inv.getCreatedDate()));

        ((MFXTableView<Invoice>) tblVw).getTableColumns().addAll(
                invIDCol,
                soIDCol,
                ttlPybleCol,
                statusCol,
                createdDtCol
        );

        ((MFXTableView<Invoice>) tblVw).getFilters().addAll(
                new StringFilter<>("Invoice ID", invoice -> invoice.getCode()),
                new StringFilter<>("Sales Order ID", invoice -> invoice.getSO() == null ? "" : invoice.getSO().getCode()),
                new DoubleFilter<>("Total Payable", invoice -> invoice.getTtlPayable().doubleValue()),
                new StringFilter<>("Status", invoice -> invoice.getStatus()),
                new DateFilter<>("Created Date", invoice -> invoice.getCreatedDate())
        );

        List<Invoice> invoices = InvoiceService.getAllInvoices();

        //6
        ((MFXTableView<Invoice>) tblVw).setItems(FXCollections.observableList(invoices));

        ((MFXTableView<Invoice>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<Invoice>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    Invoice invoice = (((MFXTableView<Invoice>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(invoice.getCode());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            Stage stage = (Stage) btnCancel.getScene().getWindow();
                            BasicObjs passObj = new BasicObjs();
                            passObj.setObj(invoice);
                            stage.setUserData(passObj);
                            stage.close();
                        }
                        rowSelected.clear();
                    }
                }
            }
        });
    }

    private void forPaymentTerm() {
        // payment term ID
        // patment term name
        // baseline documen date
        // day limits
        // description

        // payment term ID
        MFXTableColumn<PaymentTerm> pymtTermIDCol = new MFXTableColumn<>("Payment Term ID", true, Comparator.comparing(paymentTerm -> paymentTerm.getPymtTermID()));
        // patment term name
        MFXTableColumn<PaymentTerm> pymtTermNmCol = new MFXTableColumn<>("Name", true, Comparator.comparing(paymentTerm -> paymentTerm.getPymtTermName()));
        // baseline documen date
        MFXTableColumn<PaymentTerm> baselineDocDateCol = new MFXTableColumn<>("Baseline Document Date", true, Comparator.comparing(paymentTerm -> paymentTerm.getBaseLineDocumentDate()));
        // day limits
        MFXTableColumn<PaymentTerm> dayLimitCol = new MFXTableColumn<>("Day Limits", true, Comparator.comparing(paymentTerm -> paymentTerm.getDaysLimit()));
        // description
        MFXTableColumn<PaymentTerm> descCol = new MFXTableColumn<>("Description", true, Comparator.comparing(paymentTerm -> paymentTerm.getDescription()));

        // payment term ID
        pymtTermIDCol.setRowCellFactory(pymtTerm -> new MFXTableRowCell<>(paymentTerm -> paymentTerm.getPymtTermID()));
        // patment term name
        pymtTermNmCol.setRowCellFactory(pymtTerm -> new MFXTableRowCell<>(paymentTerm -> paymentTerm.getPymtTermName()));
        // baseline documen date
        baselineDocDateCol.setRowCellFactory(pymtTerm -> new MFXTableRowCell<>(paymentTerm -> paymentTerm.getBaseLineDocumentDate()));
        // day limits
        dayLimitCol.setRowCellFactory(pymtTerm -> new MFXTableRowCell<>(paymentTerm -> paymentTerm.getDaysLimit()));
        // description
        descCol.setRowCellFactory(pymtTerm -> new MFXTableRowCell<>(paymentTerm -> paymentTerm.getDescription()));

        ((MFXTableView<PaymentTerm>) tblVw).getTableColumns().addAll(
                pymtTermIDCol,
                pymtTermNmCol,
                baselineDocDateCol,
                dayLimitCol,
                descCol
        );

        ((MFXTableView<PaymentTerm>) tblVw).getFilters().addAll(
                new StringFilter<>("Payment Term ID", paymentTerm -> paymentTerm.getPymtTermID()),
                new StringFilter<>("Name", paymentTerm -> paymentTerm.getPymtTermName()),
                new StringFilter<>("Baseline Document Date", paymentTerm -> paymentTerm.getBaseLineDocumentDate()),
                new IntegerFilter<>("Day Limits", paymentTerm -> paymentTerm.getDaysLimit()),
                new StringFilter<>("Description", paymentTerm -> paymentTerm.getDescription())
        );

        List<PaymentTerm> pymtTerms = PaymentTermService.getAllPaymentTerms();

        //6
        ((MFXTableView<PaymentTerm>) tblVw).setItems(FXCollections.observableList(pymtTerms));

        ((MFXTableView<PaymentTerm>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<Invoice>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    PaymentTerm pymtTerm = (((MFXTableView<PaymentTerm>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(pymtTerm.getPymtTermID());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            Stage stage = (Stage) btnCancel.getScene().getWindow();
                            BasicObjs passObj = new BasicObjs();
                            passObj.setObj(pymtTerm);
                            stage.setUserData(passObj);
                            stage.close();
                        }
                        rowSelected.clear();
                    }
                }
            }
        });
    }

    private void forShipmentTerm() {

        // Shipment ID
        // Shipment Name
        // Description
        //
        // Shipment ID
        MFXTableColumn<ShipmentTerm> shipmentTermIDCol = new MFXTableColumn<>("Shipment Term ID", true, Comparator.comparing(shipmentTerm -> shipmentTerm.getShipmentTermID()));
        // Shipment Name
        MFXTableColumn<ShipmentTerm> shipmentTermNmCol = new MFXTableColumn<>("Name", true, Comparator.comparing(shipmentTerm -> shipmentTerm.getShipmentTermName()));
        // Description
        MFXTableColumn<ShipmentTerm> descCol = new MFXTableColumn<>("Description", true, Comparator.comparing(shipmentTerm -> shipmentTerm.getDescription()));

        // Shipment ID
        shipmentTermIDCol.setRowCellFactory(shipTerm -> new MFXTableRowCell<>(shipmentTerm -> shipmentTerm.getShipmentTermID()));
        // Shipment Name
        shipmentTermNmCol.setRowCellFactory(shipTerm -> new MFXTableRowCell<>(shipmentTerm -> shipmentTerm.getShipmentTermName()));
        // Description
        descCol.setRowCellFactory(shipTerm -> new MFXTableRowCell<>(shipmentTerm -> shipmentTerm.getDescription()));

        ((MFXTableView<ShipmentTerm>) tblVw).getTableColumns().addAll(
                shipmentTermIDCol,
                shipmentTermNmCol,
                descCol
        );

        ((MFXTableView<ShipmentTerm>) tblVw).getFilters().addAll(
                new StringFilter<>("Shipment Term ID", shipmentTerm -> shipmentTerm.getShipmentTermID()),
                new StringFilter<>("Name", shipmentTerm -> shipmentTerm.getShipmentTermName()),
                new StringFilter<>("Description", shipmentTerm -> shipmentTerm.getDescription())
        );

        List<ShipmentTerm> shipmentTerms = ShipmentTermService.getAllShipmentTerms();

        //6
        ((MFXTableView<ShipmentTerm>) tblVw).setItems(FXCollections.observableList(shipmentTerms));

        ((MFXTableView<ShipmentTerm>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<Invoice>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    ShipmentTerm shipmentTerm = (((MFXTableView<ShipmentTerm>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(shipmentTerm.getShipmentTermID());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            Stage stage = (Stage) btnCancel.getScene().getWindow();
                            BasicObjs passObj = new BasicObjs();
                            passObj.setObj(shipmentTerm);
                            stage.setUserData(passObj);
                            stage.close();
                        }
                        rowSelected.clear();
                    }
                }
            }
        });
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
